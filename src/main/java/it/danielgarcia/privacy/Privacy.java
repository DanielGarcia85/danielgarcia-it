// src/main/java/it/danielgarcia/privacy/Privacy.java

package it.danielgarcia.privacy;

import java.util.Map;

import io.quarkiverse.roq.data.runtime.annotations.DataMapping;

/**
 * The texts of the privacy page, in every language, read from the data/privacy directory.
 *
 * @param byLanguage the texts of each language, keyed by the language code taken from the file name (fr, en)
 */
@DataMapping(value = "privacy", type = DataMapping.Type.OBJECT_DIR, required = true)
public record Privacy(Map<String, PrivacyContent> byLanguage) {

    /**
     * Returns the texts in the given language.
     *
     * @param language the language code, as used in the front matter of the pages (fr, en)
     * @return the texts in this language, or {@code null} if there is no file for it
     */
    public PrivacyContent get(String language) {
        return byLanguage.get(language);
    }
}
