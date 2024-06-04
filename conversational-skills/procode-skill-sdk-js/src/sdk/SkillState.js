import { Slot } from "../sdk/Slot.js";

/**
 * Class representing the state of a skill.
 */
export class SkillState {
    /**
     * Create a SkillState.
     * @param {Object} config - The configuration object.
     * @param {Array} config.slots - The slots data.
     * @param {Object} config.state - The state data. (local_variables, session_variables, etc.)
     * @param {string} config.confirmation_event - The confirmation event. (user_confirmed, user_cancelled, etc.)
     */
    constructor({ slots, state, confirmation_event }) {
        /**
         * @property {Array<Slot>} slots - The slots data, converted from JSON.
         */
        this.slots = slots?.map(slot => Slot.fromJson(slot));

        /**
         * @property {Object} state - The state data.
         */
        this.state = state;

        /**
         * @property {string} confirmation_event - The confirmation event.
         */
        this.confirmation_event = confirmation_event;
    }

    /**
     * Get a variable from the local or session state.
     * @param {string} varname - The name of the variable.
     * @returns {any} The value of the variable.
     */
    getVariable(varname) {
        let varvalue = this.getLocalVariable(varname);
        if (!varvalue) {
            varvalue = this.getSessionVariable(varname);
        }
        return varvalue;
    }

    /**
     * Get a local variable from the state.
     * @param {string} varname - The name of the variable.
     * @returns {any} The value of the variable, or undefined if not found.
     */
    getLocalVariable(varname) {
        if (this.state?.local_variables && varname in this.state.local_variables) {
            return this.state.local_variables[varname];
        }
        return undefined;
    }

    /**
     * Get a session variable from the state.
     * @param {string} varname - The name of the variable.
     * @returns {any} The value of the variable, or undefined if not found.
     */
    getSessionVariable(varname) {
        if (this.state?.session_variables && varname in this.state.session_variables) {
            return this.state.session_variables[varname];
        }
        return undefined;
    }

    /**
     * Get a slot by name.
     * @param {string} slotName - The name of the slot.
     * @returns {Slot} The slot, or undefined if not found.
     */
    getSlot(slotName) {
        return this.slots.find(slot => slot.name === slotName);
    }
}