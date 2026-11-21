// src/main/java/it/danielgarcia/career/Career.java

package it.danielgarcia.career;

import java.util.Map;

import io.quarkiverse.roq.data.runtime.annotations.DataMapping;

/**
 * The content of the career section of the site, in every language, read from the data/career directory.
 *
 * @param byLanguage the content of each language, keyed by the language code taken from the file name (fr, en)
 */
@DataMapping(value = "career", type = DataMapping.Type.OBJECT_DIR, required = true)
public record Career(Map<String, CareerContent> byLanguage) {

    /**
     * Returns the content in the given language.
     *
     * @param language the language code, as used in the front matter of the pages (fr, en)
     * @return the content in this language, or {@code null} if there is no file for it
     */
    public CareerContent get(String language) {
        return byLanguage.get(language);
    }
}
