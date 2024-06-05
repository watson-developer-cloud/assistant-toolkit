/**
 * Enum for Resolver Types.
 * @enum {string}
 */
export const ResolverType = {
    /** Skill completed successfully */
    COMPLETE: 'skill_complete',
    /** Skill was cancelled */
    CANCEL: 'skill_cancel',
    /** User interaction is required */
    USER_INTERACTION: 'user_interaction'
};

/**
 * Enum for Slot Events.
 * @enum {string}
 */
export const SlotEvent = {
    /** Slot has been just filled */
    FILL: 'fill',
    /** Slot has been repaired */
    REPAIR: 'repair'
};

/**
 * Enum for Response Types.
 * @enum {string}
 */
export const ResponseType = {
    /** Response contains slots in flight */
    SLOTS: 'slots',
    /** Response contains text */
    TEXT: 'text',
    /** Response contains options */
    OPTION: 'option',
    /** Response contains an image */
    IMAGE: 'image',
    /** Response contains a video */
    VIDEO: 'video',
    /** Response contains audio */
    AUDIO: 'audio',
    /** Response contains a custom user-defined type */
    CUSTOM: 'user_defined'
};

/**
 * Enum for Confirmation Types.
 * @enum {string}
 */
export const ConfirmationType = {
    /** User confirmed final skill execution */
    CONFIRM: 'user_confirmed',
    /** User rejected execution. Overloaded for cancel, or possible slot repair */
    REJECT: 'user_cancelled'
};
