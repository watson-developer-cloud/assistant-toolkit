/**
 * Replaces placeholders in a template string with corresponding values.
 * @param {string} template - The template string containing placeholders in the format ${variable}.
 * @param {Object} values - An object containing key-value pairs where keys are variable names in the template.
 * @returns {string} The template string with placeholders replaced by corresponding values.
 */
export const fillTemplate = (template, values) => {
    return template.replace(/\${(.*?)}/g, (match, variable) => values[variable] || '');
}

/**
 * Transforms WxA conversation memory into a format compatible with OpenAI.
 * @param {Array<Object>} conversation_memory - An array of user/assistant message objects.
 * @returns {Array<Object>} An array of objects formatted for OpenAI, with roles 'user' and 'assistant'.
 */
export const transformToOpenAIFormat = (conversation_memory) => {
    let output = [];

    conversation_memory.forEach(item => {
        for (let role in item) {
            if (role === 'u') {
                output.push({ role: 'user', content: item[role] });
            } else if (role === 'a') {
                output.push({ role: 'assistant', content: item[role] });
            }
        }
    });

    return output;
}