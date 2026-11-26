// src/test/java/it/danielgarcia/privacy/PrivacyTest.java
package it.danielgarcia.privacy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import it.danielgarcia.about.Section;

/**
 * PrivacyTest — checks of the texts of the privacy page in data/privacy, in every language.
 * <p>
 * ───────────────────────────────────────────────────────────────────────────
 * <p>
 * Each record already checks its own fields when Roq builds it. These tests check
 * what a single file cannot see: both languages, the same sections in the same
 * order with the same number of paragraphs, and anchors that appear only once.
 */
@QuarkusTest
class PrivacyTest {

    @Inject
    Privacy privacy;

    @Test
    void bothLanguagesArePresent() {
        assertEquals(Set.of("fr", "en"), privacy.byLanguage().keySet());
    }

    @Test
    void bothLanguagesHaveTheSameShape() {
        List<Section> fr = privacy.get("fr").sections();
        List<Section> en = privacy.get("en").sections();

        assertEquals(ids(fr), ids(en), "sections");
        for (int i = 0; i < fr.size(); i++) {
            assertEquals(fr.get(i).paragraphs().size(), en.get(i).paragraphs().size(), fr.get(i).id() + ": paragraphs");
        }
    }

    @Test
    void sectionIdsAreUnique() {
        List<String> ids = ids(privacy.get("fr").sections());
        assertEquals(ids.size(), new HashSet<>(ids).size(), "section ids: " + ids);
    }

    /**
     * Returns the anchors of the sections, in display order.
     */
    private static List<String> ids(List<Section> sections) {
        return sections.stream().map(Section::id).toList();
    }
}
