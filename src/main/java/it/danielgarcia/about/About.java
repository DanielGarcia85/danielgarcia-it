// src/main/java/it/danielgarcia/about/About.java

package it.danielgarcia.about;

import java.util.Map;

import io.quarkiverse.roq.data.runtime.annotations.DataMapping;

/**
 * The texts of the About page and of its panel on the home page, in every language,
 * read from the data/about directory.
 *
 * @param byLanguage the texts of each language, keyed by the language code taken from the file name (fr, en)
 */
@DataMapping(value = "about", type = DataMapping.Type.OBJECT_DIR, required = true)
public record About(Map<String, AboutContent> byLanguage) {

    /**
     * Returns the texts in the given language.
     *
     * @param language the language code, as used in the front matter of the pages (fr, en)
     * @return the texts in this language, or {@code null} if there is no file for it
     */
    public AboutContent get(String language) {
        return byLanguage.get(language);
    }
}
