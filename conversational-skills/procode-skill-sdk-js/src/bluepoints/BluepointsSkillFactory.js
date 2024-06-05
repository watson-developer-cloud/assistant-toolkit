import { LanguageManager } from '../sdk/LanguageManager.js';
import { Skill } from '../sdk/Skill.js';
import { Entity, EntityValue, Slot, SlotType } from '../sdk/Slot.js';
import { SlotsInFlight } from '../sdk/SlotsInFlight.js';
import { fillTemplate } from '../sdk/Utils.js';

// Define slot names for the Bluepoints skill
export const BluepointsSlot = {
    RECIPIENT: 'recipient',
    RECIPIENT_SELECTOR: 'recipientSelector',
    NUMBER_OF_BLUEPOINTS: 'numberOfBluepoints'
};

// Function to create the Bluepoints skill with a given language
export const createBluepointsSkill = async (language) => {
    // Initialize language manager and load strings for the specified language
    const languageManager = new LanguageManager("../bluepoints/i18n");
    const strings = await languageManager.getStrings('.', language);

    // Define slots and their attributes for the skill
    const slotsInFlight = new SlotsInFlight({
        slots: [
            new Slot({
                name: BluepointsSlot.RECIPIENT,
                type: SlotType.STRING,
                description: strings.recipient.description,
                errorTemplate: strings.recipient.validationErrorTemplate,
                prompt: strings.recipient.prompt
            }),
            new Slot({
                name: BluepointsSlot.RECIPIENT_SELECTOR,
                type: SlotType.ENTITY,
                description: strings.recipientSelector.description,
                errorTemplate: strings.recipientSelector.validationErrorTemplate,
                prompt: strings.recipientSelector.prompt,
                selectorFor: BluepointsSlot.RECIPIENT,
                hidden: true
            }),
            new Slot({
                name: BluepointsSlot.NUMBER_OF_BLUEPOINTS,
                type: SlotType.NUMBER,
                description: strings.numberOfBluepoints.description,
                errorTemplate: strings.numberOfBluepoints.validationErrorTemplate,
                prompt: strings.numberOfBluepoints.prompt
            })
        ],
        confirmation: "required"
    });

    // Mock function to simulate transferring Bluepoints
    const transferBluepoints = async (recipient, numberOfBluepoints) => {
        return { success: true, message: strings.transferSuccessful };
    };

    // Mock function to simulate looking up a recipient by name
    const recipientLookup = async (recipientName) => {
        const lowerCaseRecipient = recipientName.toLowerCase();
        if (lowerCaseRecipient.includes("kim")) {
            return {
                success: true,
                result: [{
                    name: "Kim Blatt",
                    profile_photo: "https://...ibm.com/.../IBM978123.jpg",
                    email: "kim.blatt@ibm.com",
                    employee_id: "IBM978123"
                }, {
                    name: "Kimberley Clark",
                    profile_photo: "https://...ibm.com/.../IBM843409.jpg",
                    email: "kimberley.clark@ibm.com",
                    employee_id: "IBM843409"
                }]
            };
        } else if (lowerCaseRecipient.includes("jim")) {
            return {
                success: true,
                result: [{
                    email: "jim.blatt@ibm.com",
                    employee_id: "IBM978124",
                    name: "Jim Blatt",
                    profile_photo: "https://...ibm.com/.../IBM978124.jpg",
                }, {
                    email: "jimbaugh.clark@ibm.com",
                    employee_id: "IBM843410",
                    name: "Jimbaugh Clark",
                    profile_photo: "https://...ibm.com/.../IBM843410.jpg",
                }]
            };
        }
        return {
            success: false,
            result: []
        };
    };

    // Mock function to simulate checking the available Bluepoints balance
    const availableBluepointsBalance = async (context) => {
        return 1500;
    };

    // Handler for changes in slot states
    const onSlotStateChange = async ({ input, context, skillState, skillResponse }) => {
        for (const slot of skillState.slots) {
            // Add value to slotsInFlight
            let slotInFlight = skillResponse.getSlot(slot.name);
            slotInFlight.value = slot.value;

            if (slot.hasChanged) {
                if (slot.name === BluepointsSlot.RECIPIENT) {
                    const recipientLookupResponse = await recipientLookup(slot.value.normalized); // Lookup recipient
                    if (recipientLookupResponse.success) { // On successful lookup
                        let options = []; // Create recipient selection options from the lookup result
                        for (const recipientRecord of recipientLookupResponse.result) { // For each recipient record
                            let selectionKey = `${recipientRecord.name} (${recipientRecord.email})`; // Create selection key
                            recipientRecord.selectionKey = selectionKey; // Add selection key to recipient record
                            options.push(new EntityValue({ // Add an option for selection
                                label: selectionKey,
                                value: selectionKey,
                                synonyms: [recipientRecord.name, recipientRecord.email, recipientRecord.employee_id]
                            }));
                        }
                        let selectorSlot = skillResponse.getSelectorFor(BluepointsSlot.RECIPIENT); // Get selector slot
                        selectorSlot.schema = new Entity(selectorSlot.name, options); // Set options for selection
                        selectorSlot.show(); // Show selector slot
                        skillResponse.setLocalVariable('recipientLookupResult', recipientLookupResponse.result); // Store results in state
                    } else { // Lookup failed
                        slotInFlight.setError = slotInFlight.errorTemplate; // Set error
                        slotInFlight.value = undefined; // Clear value
                    }
                } else if (slot.name === BluepointsSlot.RECIPIENT_SELECTOR) {
                    let recipientLookupResult = skillState.getLocalVariable('recipientLookupResult');
                    let recipient = recipientLookupResult.find(r => r.selectionKey === slot.value.normalized);
                    skillResponse.setLocalVariable('recipient', recipient); // Store recipient in state
                    skillResponse.deleteLocalVariable('recipientLookupResult'); // Clear lookup results
                } else if (slot.name === BluepointsSlot.NUMBER_OF_BLUEPOINTS) {
                    let bluepointsBalance = await availableBluepointsBalance(context); // Get available balance
                    if (slot.value.normalized > bluepointsBalance) { // If transfer amount exceeds balance
                        slotInFlight.setError = fillTemplate(slotInFlight.errorTemplate, { bluepointsBalance }); // Set error
                        slotInFlight.value = undefined; // Clear value
                    }
                }
            }
        }

        return skillResponse;
    };

    // Handler for confirm events
    const onConfirm = async ({ input, context, skillState, skillResponse }) => {
        const result = await transferBluepoints(
            skillState.getVariable('recipient').employee_id,
            skillState.getSlot('numberOfBluepoints').value.normalized
        );
        skillResponse.addTextResponse(result.message);
        skillResponse.clearSlotsInFlight();
        skillResponse.markSkillComplete();
        return skillResponse;
    };

    // Handler for cancel events
    const onCancel = async ({ input, context, skillState, skillResponse }) => {
        skillResponse.addTextResponse(strings.transferCancelled);
        skillResponse.clearSlotsInFlight();
        skillResponse.markSkillCancelled();
        return skillResponse;
    };

    // Return a new Skill instance with the defined configurations
    return new Skill({
        slotsInFlight,
        strings,
        onSlotStateChange,
        onConfirm,
        onCancel
    });
};