import { createBluepointsSkill, BluepointsSlot } from '@src/bluepoints/BluepointsSkillFactory.js';
import { LanguageManager } from '@src/sdk/LanguageManager.js';
import { Entity, Slot, SlotType } from '@src/sdk/Slot.js';
import { SlotsInFlight } from '@src/sdk/SlotsInFlight.js';
import { ResolverType, SlotEvent } from '@src/sdk/Constants.js';

describe('BluepointsSkill', () => {
    const languages = ['de', 'en', 'es', 'fr', 'ja', 'pt_br'];
    languages.forEach(language => {
        describe(`language: ${language}`, () => {
            let skill;
            let slotsInFlight;
            let strings;

            beforeEach(async () => {
                skill = await createBluepointsSkill(language);
                const languageManager = new LanguageManager("../bluepoints/i18n");
                strings = await languageManager.getStrings('.', language);
                slotsInFlight = new SlotsInFlight({
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
            });

            test('Valid recipient change', async () => {
                const recipientLookupResult = [
                    {
                        selectionKey: "Jim Blatt (jim.blatt@ibm.com)",
                        email: "jim.blatt@ibm.com",
                        employee_id: "IBM978124",
                        name: "Jim Blatt",
                        profile_photo: "https://...ibm.com/.../IBM978124.jpg",
                    }, {
                        selectionKey: "Jimbaugh Clark (jimbaugh.clark@ibm.com)",
                        email: "jimbaugh.clark@ibm.com",
                        employee_id: "IBM843410",
                        name: "Jimbaugh Clark",
                        profile_photo: "https://...ibm.com/.../IBM843410.jpg",
                    }
                ];
                const recipientSlotValue = {normalized: "Jim", literal: "Jim"};
                const testInput = {
                    input: {},
                    context: {},
                    slots: [
                        {name: BluepointsSlot.RECIPIENT, value: recipientSlotValue, event: SlotEvent.FILL}
                    ],
                    state: {
                        local_variables: {},
                        session_variables: {}
                    }
                };
                slotsInFlight.find(BluepointsSlot.RECIPIENT).value = recipientSlotValue;
                let selectorSlot = slotsInFlight.find(BluepointsSlot.RECIPIENT_SELECTOR);
                selectorSlot.schema = Entity.fromJsonString(`{
                    "entity": "${BluepointsSlot.RECIPIENT_SELECTOR}",
                    "values": [{
                        "label": "Jim Blatt (jim.blatt@ibm.com)",
                        "value": "Jim Blatt (jim.blatt@ibm.com)",
                        "synonyms": ["Jim Blatt", "jim.blatt@ibm.com", "IBM978124"]
                    }, { 
                        "label": "Jimbaugh Clark (jimbaugh.clark@ibm.com)",
                        "value": "Jimbaugh Clark (jimbaugh.clark@ibm.com)",
                        "synonyms": ["Jimbaugh Clark", "jimbaugh.clark@ibm.com", "IBM843410"]
                    }]
                }`);
                selectorSlot.show();

                const expectedOutput = {
                    state: {
                        local_variables: {recipientLookupResult},
                        session_variables: {}
                    },
                    output: {
                        generic: [slotsInFlight.toJson()]
                    },
                    resolver: {
                        type: ResolverType.USER_INTERACTION,
                    }
                };
                const result = await skill.orchestrate(testInput);
                expect(result).toEqual(expectedOutput);
            })

            test('Invalid recipient change', async () => {
                const testInput = {
                    input: {},
                    context: {},
                    slots: [
                        {name: BluepointsSlot.RECIPIENT, value: {normalized: "invalid name", literal: "invalid name"}, event: SlotEvent.FILL}
                    ],
                    state: {
                        local_variables: {},
                        session_variables: {}
                    }
                };
                let recipientSlot = slotsInFlight.find(BluepointsSlot.RECIPIENT)
                recipientSlot.setError = recipientSlot.errorTemplate;
                const expectedOutput = {
                    state: {
                        local_variables: {},
                        session_variables: {}
                    },
                    output: {
                        generic: [slotsInFlight.toJson()]
                    },
                    resolver: {
                        type: ResolverType.USER_INTERACTION,
                    }
                };
                const result = await skill.orchestrate(testInput);
                expect(result).toEqual(expectedOutput);
            })

            test('Valid recipient selection', async () => {
                const recipientLookupResult = [
                    {
                        selectionKey: "Jim Blatt (jim.blatt@ibm.com)",
                        email: "jim.blatt@ibm.com",
                        employee_id: "IBM978124",
                        name: "Jim Blatt",
                        profile_photo: "https://...ibm.com/.../IBM978124.jpg",
                    }, {
                        selectionKey: "Jimbaugh Clark (jimbaugh.clark@ibm.com)",
                        email: "jimbaugh.clark@ibm.com",
                        employee_id: "IBM843410",
                        name: "Jimbaugh Clark",
                        profile_photo: "https://...ibm.com/.../IBM843410.jpg",
                    }
                ];
                const recipientSlotValue = {normalized: "Jim", literal: "Jim"};
                const recipientSelectorSlotValue = {normalized: "Jim Blatt (jim.blatt@ibm.com)", literal: "Jim Blatt (jim.blatt@ibm.com)"};
                const recipient = recipientLookupResult.find(r => r.selectionKey === recipientSelectorSlotValue.normalized);

                const testInput = {
                    input: {},
                    context: {},
                    slots: [
                        {name: BluepointsSlot.RECIPIENT, value: recipientSlotValue},
                        {name: BluepointsSlot.RECIPIENT_SELECTOR, value: recipientSelectorSlotValue, event: SlotEvent.FILL}
                    ],
                    state: {
                        local_variables: {recipientLookupResult},
                        session_variables: {}
                    }
                };

                let recipientSlot = slotsInFlight.find(BluepointsSlot.RECIPIENT);
                recipientSlot.value = recipientSlotValue;

                const expectedOutput = {
                    state: {
                        local_variables: {recipient},
                        session_variables: {}
                    },
                    output: {
                        generic: [slotsInFlight.toJson()]
                    },
                    resolver: {
                        type: ResolverType.USER_INTERACTION,
                    }
                };
                const result = await skill.orchestrate(testInput);
                expect(result).toEqual(expectedOutput);
            })

            test('Recipient change during selection', async () => {
                const oldRecipientLookupResult = [{
                    selectionKey: "Jim Blatt (jim.blatt@ibm.com)",
                    email: "jim.blatt@ibm.com",
                    employee_id: "IBM978124",
                    name: "Jim Blatt",
                    profile_photo: "https://...ibm.com/.../IBM978124.jpg",
                }, {
                    selectionKey: "Jimbaugh Clark (jimbaugh.clark@ibm.com)",
                    email: "jimbaugh.clark@ibm.com",
                    employee_id: "IBM843410",
                    name: "Jimbaugh Clark",
                    profile_photo: "https://...ibm.com/.../IBM843410.jpg",
                }];
                const newRecipientLookupResult = [{
                    selectionKey: "Kim Blatt (kim.blatt@ibm.com)",
                    email: "kim.blatt@ibm.com",
                    employee_id: "IBM978123",
                    name: "Kim Blatt",
                    profile_photo: "https://...ibm.com/.../IBM978123.jpg",
                }, {
                    selectionKey: "Kimberley Clark (kimberley.clark@ibm.com)",
                    email: "kimberley.clark@ibm.com",
                    employee_id: "IBM843409",
                    name: "Kimberley Clark",
                    profile_photo: "https://...ibm.com/.../IBM843409.jpg",
                }];
                const recipientSlotValue = {normalized: "Kim", literal: "Kim"};

                const testInput = {
                    input: {},
                    context: {},
                    slots: [
                        {name: BluepointsSlot.RECIPIENT, value: recipientSlotValue, event: SlotEvent.REPAIR},
                    ],
                    state: {
                        local_variables: {recipientLookupResult: oldRecipientLookupResult},
                        session_variables: {}
                    }
                };

                slotsInFlight.find(BluepointsSlot.RECIPIENT).value = recipientSlotValue;
                let selectorSlot = slotsInFlight.find(BluepointsSlot.RECIPIENT_SELECTOR);
                selectorSlot.schema = Entity.fromJsonString(`{
                    "entity": "${BluepointsSlot.RECIPIENT_SELECTOR}",
                    "values": [{
                        "label": "Kim Blatt (kim.blatt@ibm.com)",
                        "value": "Kim Blatt (kim.blatt@ibm.com)",
                        "synonyms": ["Kim Blatt", "kim.blatt@ibm.com", "IBM978123"]
                    }, { 
                        "label": "Kimberley Clark (kimberley.clark@ibm.com)",
                        "value": "Kimberley Clark (kimberley.clark@ibm.com)",
                        "synonyms": ["Kimberley Clark", "kimberley.clark@ibm.com", "IBM843409"]
                    }]
                }`);
                selectorSlot.show();

                const expectedOutput = {
                    state: {
                        local_variables: {recipientLookupResult: newRecipientLookupResult},
                        session_variables: {}
                    },
                    output: {
                        generic: [slotsInFlight.toJson()]
                    },
                    resolver: {
                        type: ResolverType.USER_INTERACTION,
                    }
                };
                const result = await skill.orchestrate(testInput);
                expect(result).toEqual(expectedOutput);
            })

            test('Valid numberOfBluepoints change', async () => {                
                const recipient = {
                    selectionKey: "Kim Blatt (kim.blatt@ibm.com)",
                    email: "kim.blatt@ibm.com",
                    employee_id: "IBM978123",
                    name: "Kim Blatt",
                    profile_photo: "https://...ibm.com/.../IBM978123.jpg",
                };
                const recipientSlotValue = {normalized: "Kim", literal: "Kim"};
                const numberOfBluepointsSlotValue = {normalized: 1000, literal: "1000"};

                const testInput = {
                    input: {},
                    context: {},
                    slots: [
                        {name: BluepointsSlot.RECIPIENT, value: recipientSlotValue },
                        {name: BluepointsSlot.NUMBER_OF_BLUEPOINTS, value: numberOfBluepointsSlotValue, event: SlotEvent.FILL}
                    ],
                    state: {
                        local_variables: {recipient},
                        session_variables: {}
                    }
                };

                slotsInFlight.find(BluepointsSlot.RECIPIENT).value = recipientSlotValue;
                slotsInFlight.find(BluepointsSlot.NUMBER_OF_BLUEPOINTS).value = numberOfBluepointsSlotValue;

                const expectedOutput = {
                    state: {
                        local_variables: {recipient},
                        session_variables: {}
                    },
                    output: {
                        generic: [slotsInFlight.toJson()]
                    },
                    resolver: {
                        type: ResolverType.USER_INTERACTION,
                    }
                };
                const result = await skill.orchestrate(testInput);
                expect(result).toEqual(expectedOutput);
            })

            test('User confirms skill', async () => {
                const numBluepoints = 1000;
                const recipient = {
                    email: "kim.blatt@ibm.com",
                    employee_id: "IBM978123",
                    name: "Kim Blatt",
                    profile_photo: "https://...ibm.com/.../IBM978123.jpg",
                };
                const testInput = {
                    input: {},
                    context: {},
                    slots: [
                        {name: "recipient", value: {normalized: recipient.name, literal: recipient.name}},
                        {name: "numberOfBluepoints", value: {normalized: numBluepoints, literal: `${numBluepoints}`}},
                    ],
                    state: {
                        local_variables: {bluepoints_balance: 1500, recipient: recipient},
                        session_variables: {}
                    },
                    confirmation_event: "user_confirmed"
                };
                const expectedOutput = {
                    state: {
                        local_variables: {bluepoints_balance: 1500, recipient: recipient},
                        session_variables: {}
                    },
                    output: {
                        generic: [
                            {
                                response_type: "text",
                                text: skill.strings.transferSuccessful
                            }
                        ]
                    },
                    resolver: {
                        type: ResolverType.COMPLETE,
                    }
                };
                const result = await skill.orchestrate(testInput);
                expect(result).toEqual(expectedOutput);
            });

            test('User cancels skill', async () => {
                const numBluepoints = 1000;
                const recipient = {
                    email: "kim.blatt@ibm.com",
                    employee_id: "IBM978123",
                    name: "Kim Blatt",
                    profile_photo: "https://...ibm.com/.../IBM978123.jpg",
                };
                const testInput = {
                    input: {},
                    context: {},
                    slots: [
                        {name: "recipient", value: {normalized: recipient.name, literal: recipient.name}},
                        {name: "numberOfBluepoints", value: {normalized: numBluepoints, literal: `${numBluepoints}`}}
                    ],
                    state: {
                        local_variables: {bluepoints_balance: 1500, recipient: recipient},
                        session_variables: {}
                    },
                    confirmation_event: "user_cancelled"
                };
                const expectedOutput = {
                    state: {
                        local_variables: {bluepoints_balance: 1500, recipient: recipient},
                        session_variables: {}
                    },
                    output: {
                        generic: [
                            {
                                response_type: "text",
                                text: skill.strings.transferCancelled
                            }
                        ]
                    },
                    resolver: {
                        type: ResolverType.CANCEL,
                    }
                };
                const result = await skill.orchestrate(testInput);
                expect(result).toEqual(expectedOutput);
            });
        });
    });
});
