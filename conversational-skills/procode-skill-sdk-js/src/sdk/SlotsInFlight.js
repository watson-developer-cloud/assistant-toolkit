import { ResponseType } from "../sdk/Constants.js";
import { Slot } from "./Slot.js";

/**
 * Represents the slots currently in flight.
 */
export class SlotsInFlight {
    /**
     * Constructs a new SlotsInFlight instance.
     * @param {Object} args - The parameters for the constructor.
     * @param {Array<Slot>} args.slots - The list of Slot objects.
     * @param {string} [args.confirmation] - Optional confirmation string.
     */
    constructor({ slots, confirmation }) {
        /**
         * @type {Slot[]}
         * @private
         */
        this._slots = slots;

        /**
         * @type {string|undefined}
         * @private
         */
        this._confirmation = confirmation;
    }

    /**
     * Checks if there is a confirmation.
     * @returns {boolean} True if there is a confirmation, otherwise false.
     */
    get hasConfirmation() {
        return this.confirmation !== undefined;
    }

    /**
     * Gets the confirmation.
     * @returns {string|undefined} The confirmation string, if any.
     */
    get confirmation() {
        return this._confirmation;
    }

    /**
     * Checks if there are any non-hidden slots.
     * @returns {boolean} True if there are non-hidden slots, otherwise false.
     */
    get hasSlots() {
        return this._slots.filter(slot => !slot.isHidden).length > 0;
    }

    /**
     * Finds a slot by its name.
     * @param {string} slotName - The name of the slot to find.
     * @returns {Slot|undefined} The slot if found, otherwise undefined.
     */
    find(slotName) {
        return this._slots.find(slot => slot.name === slotName);
    }

    /**
     * Finds the slot that is a selector for the specified slot.
     * @param {string} slotName - The name of the slot to find the selector for.
     * @returns {Slot|undefined} The slot if found, otherwise undefined.
     */
    findSelectorFor(slotName) {
        return this._slots.find(slot => slot.selectorFor === slotName);
    }

    /**
     * Converts the SlotsInFlight instance to a JSON object.
     * @returns {Object} The JSON representation of the SlotsInFlight instance.
     */
    toJson() {
        const jsonObject = {
            response_type: ResponseType.SLOTS,
            slots: this._slots
                .filter(slot => !slot.isHidden) // Filter out hidden slots
                .map(slot => slot.toJson())     // Map the remaining slots to their JSON representation
        };

        if (this.hasConfirmation) {
            jsonObject.confirmation = this.confirmation;
        }

        return jsonObject;
    }
}