// src/main/java/it/danielgarcia/contact/Contact.java

package it.danielgarcia.contact;

import java.util.Map;

import io.quarkiverse.roq.data.runtime.annotations.DataMapping;

/**
 * The public contact details, in every language, read from the data/contact directory.
 *
 * @param byLanguage the contact details of each language, keyed by the language code taken from the file name (fr, en)
 */
@DataMapping(value = "contact", type = DataMapping.Type.OBJECT_DIR, required = true)
public record Contact(Map<String, ContactContent> byLanguage) {

    /**
     * Returns the contact details in the given language.
     *
     * @param language the language code, as used in the front matter of the pages (fr, en)
     * @return the contact details in this language, or {@code null} if there is no file for it
     */
    public ContactContent get(String language) {
        return byLanguage.get(language);
    }
}
