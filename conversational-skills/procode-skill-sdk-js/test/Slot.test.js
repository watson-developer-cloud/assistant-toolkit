import { Entity, Slot } from '../src/sdk/Slot.js';

describe('Slot', () => {
    it('should create Entity from JSON input', () => {
        const testInput = `{
            "entity": "employeeSelector",
            "values": [{
                "value": "Jim Blatt (jim.blatt@ibm.com)",
                "synonyms": ["IBM901231", "Jim Blatt", "jim.blatt@ibm.com"],
                "label": "Jim Blatt (jim.blatt@ibm.com)"
            }, { 
                "value": "Jim Vaughn (jim.vaugh@ibm.com)",
                "synonyms": ["IBM941635", "Jim Vaughn", "jim.vaugh@ibm.com"],
                "label": "Jim Vaughn (jim.vaugh@ibm.com)"
            }]
        }`;
        const expectedOutput = {
            "entity": "employeeSelector",
            "values": [{
                "value": "Jim Blatt (jim.blatt@ibm.com)",
                "synonyms": ["IBM901231", "Jim Blatt", "jim.blatt@ibm.com" ],
                "label": "Jim Blatt (jim.blatt@ibm.com)",
                "input": { "text": "Jim Blatt (jim.blatt@ibm.com)" }
            }, {
                "value": "Jim Vaughn (jim.vaugh@ibm.com)",
                "synonyms": ["IBM941635", "Jim Vaughn", "jim.vaugh@ibm.com"],
                "label": "Jim Vaughn (jim.vaugh@ibm.com)",
                "input": { "text": "Jim Vaughn (jim.vaugh@ibm.com)" }
            }]
        }
        const result = Entity.fromJsonString(testInput);
        expect(result).toEqual(expectedOutput);
    });

    describe('Slot', () => {
        it('should create Slot from JSON input', () => {
            const testInput = `{
                "name": "employeeSelector",
                "type": "entity",
                "description": "Select an employee",
                "prompt": "Select the employee",
                "schema": {
                    "entity": "employeeSelector",
                    "values": [{
                        "value": "Jim Blatt (jim.blatt@ibm.com)",
                        "synonyms": ["IBM901231", "Jim Blatt", "jim.blatt@ibm.com"],
                        "label": "Jim Blatt (jim.blatt@ibm.com)"
                    }, { 
                        "value": "Jim Vaughn (jim.vaugh@ibm.com)",
                        "synonyms": ["IBM941635", "Jim Vaughn", "jim.vaugh@ibm.com"],
                        "label": "Jim Vaughn (jim.vaugh@ibm.com)"
                    }]
                },
                "selectorFor": "employee"
            }`;
            const expectedOutput = {
                "name": "employeeSelector",
                "type": "entity",
                "description": "Select an employee",
                "prompt": "Select the employee",
                "schema": {
                    "entity": "employeeSelector",
                    "values": [{
                        "value": "Jim Blatt (jim.blatt@ibm.com)",
                        "synonyms": ["IBM901231", "Jim Blatt", "jim.blatt@ibm.com"],
                        "label": "Jim Blatt (jim.blatt@ibm.com)",
                        "input": { "text": "Jim Blatt (jim.blatt@ibm.com)" }
                    }, { 
                        "value": "Jim Vaughn (jim.vaugh@ibm.com)",
                        "synonyms": ["IBM941635", "Jim Vaughn", "jim.vaugh@ibm.com"],
                        "label": "Jim Vaughn (jim.vaugh@ibm.com)",
                        "input": { "text": "Jim Vaughn (jim.vaugh@ibm.com)" }
                    }]
                },

            }
            const result = Slot.fromJsonString(testInput).toJson();
            expect(result).toEqual(expectedOutput);
        });
    });
});
