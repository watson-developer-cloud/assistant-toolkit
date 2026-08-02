/**
 * Class representing a manager for loading and caching language bundles.
 */
export class LanguageManager {
    /**
     * Create a LanguageManager.
     * @param {string} basePath - The base path for language bundles.
     */
    constructor(basePath) {
        /** 
         * @property {string} basePath - The base path for language bundles.
         */
        this.basePath = basePath;

        /**
         * @property {Object} cache - The cache for loaded language bundles.
         */
        this.cache = {};
    }

    /**
     * Load a language bundle for a given scenario and language.
     * 
     * @param {string} scenario - The scenario for which to load the language bundle. 
     *                            If there is only one scenario, and the bundles are in the basePath use '.'.
     * @param {string} lang - The language code.
     * @returns {Promise<Object>} The language bundle.
     * @throws Will throw an error if the language bundle is not found and fallback to English also fails.
     */
    async loadLanguageBundle(scenario, lang) {
        // Check if the bundle is already loaded
        if (this.cache[scenario] && this.cache[scenario][lang]) {
            // Return the cached bundle
            return this.cache[scenario][lang];
        }

        // Try to dynamically import the language bundle
        try {
            const bundle = (await import(`${this.basePath}/${scenario}/${lang}.json`, { with: { type: 'json' } })).default;

            // Cache the loaded bundle
            if (!this.cache[scenario]) {
                this.cache[scenario] = {};
            }
            this.cache[scenario][lang] = bundle;

            return bundle;
        } catch (error) {
            // If the requested language bundle is not found, fall back to English
            if (lang !== 'en') {
                return this.loadLanguageBundle(scenario, 'en');
            }
            // Keep the original error as the cause. Anything can land here -- a syntax
            // error in the JSON, an unsupported import attribute -- and reporting all of
            // them as "not found" sends you looking for a file that is right there.
            throw new Error(`Language bundle not found for scenario: ${scenario}, language: ${lang}`, { cause: error });
        }
    }

    /**
     * Get the strings for a given scenario and language.
     * 
     * @param {string} scenario - The scenario for which to get the strings. 
     *                            If there is only one scenario, and the bundles are in the basePath, use '.'.
     * @param {string} lang - The language code.
     * @returns {Promise<Object>} The language strings.
     */
    async getStrings(scenario, lang) {
        return await this.loadLanguageBundle(scenario, lang);
    }
}