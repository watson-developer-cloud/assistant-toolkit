/**
 * Enum for Slot Types.
 * @enum {string}
 */
export const SlotType = {
    STRING: 'string',
    NUMBER: 'number',
    DATE: 'date',
    TIME: 'time',
    CONFIRMATION: 'confirmation',
    ENTITY: 'entity'
};

/**
 * Class representing a slot value.
 */
export class SlotValue {
    /**
     * Create a SlotValue.
     * @param {string} literal - The literal value as mentioned by the user.
     * @param {string} normalized - The normalized value.
     */
    constructor(literal, normalized) {
        /**
         * @property {string} _literal - The literal value.
         */
        this._literal = literal;

        /**
         * @property {string} _normalized - The normalized value.
         */
        this._normalized = normalized;
    }

    /**
     * Get the normalized value.
     * @returns {string} The normalized value.
     */
    get normalized() {
        return this._normalized;
    }

    /**
     * Get the literal value.
     * @returns {string} The literal value.
     */
    get literal() {
        return this._literal;
    }

    /**
     * Create a SlotValue from a JSON string.
     * @param {string} jsonString - The JSON string.
     * @returns {SlotValue|undefined} The SlotValue object or undefined.
     */
    static fromJsonString(jsonString) {
        if (jsonString === undefined || jsonString === null) {
            return undefined;
        }
        const jsonObject = JSON.parse(jsonString);
        return SlotValue.fromJson(jsonObject);
    }

    /**
     * Create a SlotValue from a JSON object.
     * @param {Object} jsonObject - The JSON object.
     * @returns {SlotValue|undefined} The SlotValue object or undefined.
     */
    static fromJson(jsonObject) {
        if (jsonObject === undefined || jsonObject === null || Object.keys(jsonObject).length === 0) {
            return undefined;
        }
        return new SlotValue(jsonObject.literal, jsonObject.normalized);
    }

    /**
     * Convert the SlotValue to a JSON object.
     * @returns {Object} The JSON representation of the SlotValue.
     */
    toJson() {
        return {
            literal: this._literal,
            normalized: this._normalized
        };
    }

    /**
     * Convert the SlotValue to a JSON string.
     * @returns {string} The JSON string representation of the SlotValue.
     */
    toJsonString() {
        return JSON.stringify(this.toJson(), null, 2);
    }
}

/**
 * Class representing an entity value.
 */
export class EntityValue {
    /**
     * Create an EntityValue.
     * @param {Object} config - The configuration object.
     * @param {string} config.label - The label to be shown to the user for value selection.
     * @param {string} config.value - The normalized value.
     * @param {Array<string>} config.synonyms - The list of synonyms.
     */
    constructor({ label, value, synonyms }) {
        /**
         * @property {string} label - The label to be shown to the user for value selection.
         */
        this.label = label;

        /**
         * @property {string} value - The normalized value.
         */
        this.value = value;

        /**
         * @property {Array<string>} synonyms - The list of synonyms.
         */
        this.synonyms = synonyms;

        /**
         * @property {Object} input - The input text.
         */
        this.input = { text: value };
    }

    /**
     * Convert the EntityValue to a JSON object.
     * @returns {Object} The JSON representation of the EntityValue.
     */
    toJson() {
        return {
            label: this.label,
            value: this.value,
            synonyms: this.synonyms,
            input: this.input
        };
    }

    /**
     * Convert the EntityValue to a JSON string.
     * @returns {string} The JSON string representation of the EntityValue.
     */
    toJsonString() {
        return JSON.stringify(this.toJson(), null, 2);
    }
}

/**
 * Class representing an entity.
 */
export class Entity {
    /**
     * Create an Entity.
     * @param {string} entity - The entity name.
     * @param {Array<EntityValue>} entityValues - The entity values.
     */
    constructor(entity, entityValues) {
        /**
         * @property {string} entity - The entity name.
         */
        this.entity = entity;

        /**
         * @property {Array<EntityValue>} values - The entity values.
         */
        this.values = entityValues;
    }

    /**
     * Create an Entity from a JSON string.
     * @param {string} jsonString - The JSON string.
     * @returns {Entity|undefined} The Entity object or undefined.
     */
    static fromJsonString(jsonString) {
        if (jsonString === undefined || jsonString === null) {
            return undefined;
        }
        const jsonObject = JSON.parse(jsonString);
        return Entity.fromJson(jsonObject);
    }

    /**
     * Create an Entity from a JSON object.
     * @param {Object} jsonObject - The JSON object.
     * @returns {Entity|undefined} The Entity object or undefined.
     */
    static fromJson(jsonObject) {
        if (jsonObject === undefined || jsonObject === null || Object.keys(jsonObject).length === 0) {
            return undefined;
        }
        const entityValues = jsonObject.values.map(e => new EntityValue({ label: e.label, value: e.value, synonyms: e.synonyms }));
        return new Entity(jsonObject.entity, entityValues);
    }

    /**
     * Convert the Entity to a JSON object.
     * @returns {Object} The JSON representation of the Entity.
     */
    toJson() {
        return {
            entity: this.entity,
            values: this.values.map(v => v.toJson())
        };
    }

    /**
     * Convert the Entity to a JSON string.
     * @returns {string} The JSON string representation of the Entity.
     */
    toJsonString() {
        return JSON.stringify(this.toJson(), null, 2);
    }
}

/**
 * Class representing a slot.
 */
export class Slot {
    /**
     * Create a Slot.
     * @param {Object} config - The configuration object.
     * @param {string} config.name - The name of the slot.
     * @param {string} config.type - The type of the slot.
     * @param {string} config.description - The description of the slot.
     * @param {string} config.errorTemplate - The error template for the slot.
     * @param {string} config.prompt - The prompt for the slot.
     * @param {Object} config.value - The value of the slot.
     * @param {Object} config.event - The event for the slot.
     * @param {Object} config.schema - The schema for the slot.
     * @param {string} config.selectorFor - The parent slot name, if this is a selector for another slot.
     * @param {boolean} config.hidden - Whether the slot is hidden.
     */
    constructor({ name, type, description, errorTemplate, prompt, value, event, schema, selectorFor, hidden = false }) {
        /**
         * @property {string} _name - The name of the slot.
         */
        this._name = name;

        /**
         * @property {string} _type - The type of the slot.
         */
        this._type = type;

        /**
         * @property {string} _description - The description of the slot.
         */
        this._description = description;

        /**
         * @property {string} _errorTemplate - The error template for the slot.
         */
        this._errorTemplate = errorTemplate || "Incorrect value for slot ${name}";

        /**
         * @property {string} _prompt - The prompt for the slot.
         */
        this._prompt = prompt;

        /**
         * @property {SlotValue} _value - The value of the slot.
         */
        if (value) {
            this._value = SlotValue.fromJson(value);
        }

        /**
         * @property {Object} _event - The event for the slot.
         */
        this._event = event;

        /**
         * @property {Entity} _schema - The schema for the slot.
         */
        if (schema) {
            this._schema = Entity.fromJson(schema);
        }

        /**
         * @property {string} _selectorFor - The parent slot name, if this is a selector for another slot.
         */
        this._selectorFor = selectorFor;

        /**
         * @property {boolean} _hidden - Whether the slot is hidden.
         */
        this._hidden = hidden;
    }

    /**
     * Create a Slot from a JSON string.
     * @param {string} jsonString - The JSON string.
     * @returns {Slot} The Slot object.
     */
    static fromJsonString(jsonString) {
        const jsonObject = JSON.parse(jsonString);
        return Slot.fromJson(jsonObject);
    }

    /**
     * Create a Slot from a JSON object.
     * @param {Object} jsonObject - The JSON object.
     * @returns {Slot} The Slot object.
     */
    static fromJson(jsonObject) {
        let value = jsonObject.value ? new SlotValue(jsonObject.value.literal, jsonObject.value.normalized) : undefined;
        let schema = jsonObject.schema ? Entity.fromJson(jsonObject.schema) : undefined;

        return new Slot({
            name: jsonObject.name,
            type: jsonObject.type,
            description: jsonObject.description,
            errorTemplate: jsonObject.errorTemplate,
            prompt: jsonObject.prompt,
            value: value,
            event: jsonObject.event,
            schema: schema,
            selectorFor: jsonObject.selectorFor
        });
    }

    /**
     * Convert the Slot to a JSON object.
     * @returns {Object} The JSON representation of the Slot.
     */
    toJson() {
        const obj = {
            name: this._name,
            type: this._type,
            description: this._description,
            prompt: this._prompt
        };

        if (this.isFilled) {
            obj.value = this._value.toJson();
        }

        if (this.hasError) {
            obj.validation_error = this._error;
        }

        if (this.schema) {
            obj.schema = this._schema.toJson();
        }

        return obj;
    }

    /**
     * Convert the Slot to a JSON string.
     * @returns {string} The JSON string representation of the Slot.
     */
    toJsonString() {
        return JSON.stringify(this.toJson(), null, 2);
    }

    /**
     * Check if the slot is filled.
     * @returns {boolean} True if the slot is filled, false otherwise.
     */
    get isFilled() {
        return this._value?.normalized !== null && this._value?.normalized !== undefined;
    }

    /**
     * Check if the slot has changed.
     * @returns {boolean} True if the slot has changed, false otherwise.
     */
    get hasChanged() {
        return this._event !== undefined;
    }

    /**
     * Check if the slot has an error.
     * @returns {boolean} True if the slot has an error, false otherwise.
     */
    get hasError() {
        return this._error !== undefined;
    }

    /**
     * Get the error template.
     * @returns {string} The error template.
     */
    get errorTemplate() {
        return this._errorTemplate;
    }

    /**
     * Get the error.
     * @returns {string} The error.
     */
    get error() {
        return this._error;
    }

    /**
     * Set the error.
     * @param {string} error - The error.
     */
    set setError(error) {
        this._error = error;
    }

    /**
     * Get the name of the slot.
     * @returns {string} The name of the slot.
     */
    get name() {
        return this._name;
    }

    /**
     * Get the type of the slot.
     * @returns {string} The type of the slot.
     */
    get type() {
        return this._type;
    }

    /**
     * Get the prompt for the slot.
     * @returns {string} The prompt for the slot.
     */
    get prompt() {
        return this._prompt;
    }

    /**
     * Get the description of the slot.
     * @returns {string} The description of the slot.
     */
    get description() {
        return this._description;
    }

    /**
     * Get the value of the slot.
     * @returns {SlotValue} The value of the slot.
     */
    get value() {
        return this._value;
    }

    /**
     * Set the value of the slot.
     * @param {SlotValue|Object} value - The value of the slot.
     */
    set value(value) {
        if (value === undefined || value === null) {
            this._value = undefined;
        } else if (value instanceof SlotValue) {
            this._value = value;
        } else {
            this._value = SlotValue.fromJson(value);
        }
    }

    /**
     * Get the schema of the slot.
     * @returns {Entity} The schema of the slot.
     */
    get schema() {
        return this._schema;
    }

    /**
     * Set the schema of the slot.
     * @param {Entity|Object} entity - The schema of the slot.
     */
    set schema(entity) {
        if (entity === undefined || entity === null) {
            this._schema = undefined;
        } else if (entity instanceof Entity) {
            this._schema = entity;
        } else {
            this._schema = Entity.fromJson(entity);
        }
    }

    /**
     * Check if this slot is a selector for another slot.
     * @returns {boolean} True if the slot is a selector for another slot, false otherwise.
     */
    get isSelector() {
        return this._selectorFor !== undefined;
    }

    /**
     * Get the parent slot name if this is a selector for another slot.
     * @returns {string} The parent slot name.
     */
    get selectorFor() {
        return this._selectorFor;
    }

    /**
     * Check if the slot is hidden.
     * @returns {boolean} True if the slot is hidden, false otherwise.
     */
    get isHidden() {
        return this._hidden;
    }

    /**
     * Show the slot.
     */
    show() {
        this._hidden = false;
    }

    /**
     * Hide the slot.
     */
    hide() {
        this._hidden = true;
    }
}
