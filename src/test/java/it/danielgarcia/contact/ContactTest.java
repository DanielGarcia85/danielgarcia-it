// src/test/java/it/danielgarcia/contact/ContactTest.java
package it.danielgarcia.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

/**
 * ContactTest — checks of the contact details in data/contact, in every language.
 * <p>
 * ───────────────────────────────────────────────────────────────────────────
 * <p>
 * Each language file already checks its own fields when Roq builds it. These tests
 * check what a single file cannot see: both languages, the same details in each
 * where they do not depend on the language, and the CV of each language.
 */
@QuarkusTest
class ContactTest {

    @Inject
    Contact contact;

    @Test
    void bothLanguagesArePresent() {
        assertEquals(Set.of("fr", "en"), contact.byLanguage().keySet());
    }

    @Test
    void sharedDetailsAreTheSameInBothLanguages() {
        ContactContent fr = contact.get("fr");
        ContactContent en = contact.get("en");

        assertEquals(fr.name(), en.name(), "name");
        assertEquals(fr.email(), en.email(), "email");
        assertEquals(fr.github(), en.github(), "github");
        assertEquals(fr.linkedin(), en.linkedin(), "linkedin");
    }

    @Test
    void eachLanguageLinksToItsOwnCv() {
        for (String language : contact.byLanguage().keySet()) {
            assertEquals("/cv/daniel-garcia-cv-" + language + ".pdf", contact.get(language).cv(), language);
        }
    }
}
