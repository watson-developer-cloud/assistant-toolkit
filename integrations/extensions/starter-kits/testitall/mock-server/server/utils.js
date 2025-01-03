/**
 * High entropy string generator, used to generate the body of the response for the contextTooLargeTest and contextAlmostTooLargeTest endpoints
 * to avoid being compressed by the server for more accurate testing results
 * @param {number} length The length of the string to generate
 * @returns {string} The generated string
 */
export function generateHighEntropyString(length) {
    const charset = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:",.<>?/~`'; // Large character set
    let result = '';
    for (let i = 0; i < length; i++) {
        result += charset[Math.floor(Math.random() * charset.length)];
    }
    return result;
}