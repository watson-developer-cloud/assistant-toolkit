import { SkillResponse } from '../sdk/SkillResponse.js';
import { SkillState } from '../sdk/SkillState.js';
import { SlotsInFlight } from '../sdk/SlotsInFlight.js';
import { ConfirmationType } from '../sdk/Constants.js';

/**
 * Class representing a Skill.
 */
export class Skill {
    /**
     * Create a Skill.
     * 
     * @param {Object} config - The configuration object.
     * @param {SlotsInFlight} config.slotsInFlight - Information slots that skill needs to get filled or track for repairs.
     * @param {Object} config.strings - Language strings.
     * @param {Function} config.onSlotStateChange - Callback for slot state changes.
     * @param {Function} config.onConfirm - Callback for confirmation events.
     * @param {Function} config.onCancel - Callback for cancellation events.
     * @param {Function} config.onLLMPassThru - Callback for LLM pass-through events.
     * @param {Function} config.otherwise - Default callback.
     */
    constructor({ slotsInFlight, strings, onSlotStateChange, onConfirm, onCancel, onLLMPassThru, otherwise }) {
        /**
         * @property {SlotsInFlight} slotsInFlight - Slots currently being processed.
         */
        this.slotsInFlight = slotsInFlight;

        /**
         * @property {Object} strings - Language strings.
         */
        this.strings = strings;

        /**
         * @property {Function} onConfirm - Callback for confirmation events.
         */
        this.onConfirm = onConfirm;

        /**
         * @property {Function} onCancel - Callback for cancellation events.
         */
        this.onCancel = onCancel;

        /**
         * @property {Function} onSlotStateChange - Callback for slot state changes.
         */
        this.onSlotStateChange = onSlotStateChange;

        /**
         * @property {Function} onLLMPassThru - Callback for LLM pass-through events.
         */
        this.onLLMPassThru = onLLMPassThru;

        /**
         * @property {Function} otherwise - Default callback.
         */
        this.otherwise = otherwise;
    }

    /**
     * Orchestrate the skill's response.
     * 
     * @param {Object} params - The parameters for orchestration.
     * @param {Object} params.input - The input (same as WxA /message API input)
     * @param {Object} params.context - The context data. (a filtered version of WxA /message API context)
     *                                  Contains following properties of interest:
     *                                  - global.session_id: WxA session ID
     *                                  - global.assistant_id: WxA assistant ID
     *                                  - global.environment_id: WxA environment ID
     *                                  - global.language: WxA language
     *                                  - global.session_history: WxA tracked session history
     *                                  - integrations.chat.private.jwt: WxA existing auth support token
     * @param {Array} params.slots - Slots for which Assistant has values: (name, value, and event (if any))
     * @param {Object} params.state - The current state, including (local_variables, session_variables, current_slot (that was prompted))
     * @param {string} [params.confirmation_event] - The confirmation event type (user_confirmed, or user_cancelled (only for final confirmation))
     * @returns {Promise<Object>} The orchestrated skill response. {output, state, resolver}
     * @throws Will throw an error if orchestration fails.
     */
    async orchestrate({ input, context, slots, state, confirmation_event = undefined }) {
        const skillState = new SkillState({ slots, state, confirmation_event });
        let skillResponse = new SkillResponse({ state, slotsInFlight: this.slotsInFlight });

        try {
            if (confirmation_event) {
                if (confirmation_event === ConfirmationType.CONFIRM) {
                    skillResponse = await this.onConfirm({ input, context, skillState, skillResponse });
                } else if (confirmation_event === ConfirmationType.REJECT) {
                    skillResponse = await this.onCancel({ input, context, skillState, skillResponse });
                }
            } else if (this.onSlotStateChange) {
                skillResponse = await this.onSlotStateChange({ input, context, skillState, skillResponse });
            } else if (this.onLLMPassThru) {
                skillResponse = await this.onLLMPassThru({ input, context, skillState, skillResponse, conversation_memory });
            } else if (this.otherwise) {
                skillResponse = await this.otherwise({ input, context, skillState, skillResponse });
            }
        } catch (error) {
            console.error('Error in orchestration:', error);
            throw error;
        }

        return skillResponse.toJson();
    }
}
