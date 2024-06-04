import { ResolverType, ResponseType } from "../sdk/Constants.js";
import { SkillState } from '../sdk/SkillState.js';
import { SlotsInFlight } from '../sdk/SlotsInFlight.js';

/**
 * Class representing a skill response.
 */
export class SkillResponse {
    /**
     * Create a SkillResponse.
     * @param {Object} config - The configuration object.
     * @param {SkillState} config.state - The state of the skill.
     * @param {SlotsInFlight} config.slotsInFlight - Slots currently being processed.
     */
    constructor({ state, slotsInFlight }) {
        /**
         * @property {SkillState} state - The state of the skill.
         */
        this.state = state;

        /**
         * @property {Object} output - The output responses.
         */
        this.output = { generic: [] };

        /**
         * @property {Object} resolver - The resolver type.
         */
        this.resolver = { type: ResolverType.USER_INTERACTION }; // default resolver

        /**
         * @property {SlotsInFlight} slotsInFlight - Slots currently being processed.
         */
        this.slotsInFlight = slotsInFlight;
    }

    /**
     * Add a text response to the output.
     * @param {string} text - The text response.
     */
    addTextResponse(text) {
        this.addResponseItem({ response_type: ResponseType.TEXT, text });
    }

    /**
     * Mark the skill as complete.
     */
    markSkillComplete() {
        this.setResolver(ResolverType.COMPLETE);
    }

    /**
     * Mark the skill as cancelled.
     */
    markSkillCancelled() {
        this.setResolver(ResolverType.CANCEL);
    }

    /**
     * Set the resolver to user interaction.
     */
    interactWithUser() {
        this.setResolver(ResolverType.USER_INTERACTION);
    }

    /**
     * Set the resolver type.
     * @param {string} resolverType - The resolver type.
     */
    setResolver(resolverType) {
        this.resolver = { type: resolverType };
    }

    /**
     * Get a slot by name.
     * @param {string} slotName - The name of the slot.
     * @returns {Object} The slot.
     */
    getSlot(slotName) {
        return this.slotsInFlight?.find(slotName);
    }

    /**
     * Get the selector for a slot by name.
     * @param {string} slotName - The name of the slot.
     * @returns {Object} The slot selector.
     */
    getSelectorFor(slotName) {
        return this.slotsInFlight?.findSelectorFor(slotName);
    }

    /**
     * Add a response item to the output.
     * @param {Object} response - The response item.
     */
    addResponseItem(response) {
        this.output.generic.push(response);
    }

    /**
     * Clear the slots currently in flight.
     */
    clearSlotsInFlight() {
        this.slotsInFlight = undefined;
    }

    /**
     * Set a local variable in the state.
     * @param {string} varname - The name of the variable.
     * @param {any} varvalue - The value of the variable.
     */
    setLocalVariable(varname, varvalue) {
        if (!this.state?.local_variables) {
            this.state.local_variables = {};
        }
        this.state.local_variables[varname] = varvalue;
    }

    /**
     * Set a session variable in the state.
     * @param {string} varname - The name of the variable.
     * @param {any} varvalue - The value of the variable.
     */
    setSessionVariable(varname, varvalue) {
        if (!this.state?.session_variables) {
            this.state.session_variables = {};
        }
        this.state.session_variables[varname] = varvalue;
    }

    /**
     * Delete a local variable from the state.
     * @param {string} varname - The name of the variable.
     */
    deleteLocalVariable(varname) {
        if (this.state?.local_variables) {
            delete this.state.local_variables[varname];
        }
    }

    /**
     * Convert the skill response to JSON.
     * @returns {Object} The JSON representation of the skill response.
     */
    toJson() {
        let jsonObject = {
            state: this.state,
            output: this.output,
            resolver: this.resolver
        };

        if (this.slotsInFlight?.hasSlots) {
            jsonObject.output.generic.push(this.slotsInFlight.toJson());
        }

        return jsonObject;
    }
}