export default {
    testEnvironment: 'node',
    roots: ['<rootDir>/test'],
    transform: {},
    moduleNameMapper: {
        '^@src/(.*)$': '<rootDir>/src/$1',
    },
    moduleFileExtensions: ['js', 'mjs', 'json', 'node']
};
