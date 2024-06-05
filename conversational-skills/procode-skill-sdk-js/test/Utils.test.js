import { fillTemplate } from '../src/sdk/Utils.js';

describe('fillTemplate', () => {
    test('should replace placeholders with values from context', () => {
        const template = 'Hello, ${name}!';
        const values = { name: 'John' };
        const result = fillTemplate(template, values);
        expect(result).toBe('Hello, John!');
    });

    test('should return template string if template evaluation throws an error', () => {
        const template = 'Invalid ';
        const values = {};
        const result = fillTemplate(template + '${}', values);
        expect(result).toBe(template);
    });
});
