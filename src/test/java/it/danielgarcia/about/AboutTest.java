// src/test/java/it/danielgarcia/about/AboutTest.java
package it.danielgarcia.about;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

/**
 * AboutTest — checks of the texts of the About page in data/about, in every language.
 * <p>
 * ───────────────────────────────────────────────────────────────────────────
 * <p>
 * Each record already checks its own fields when Roq builds it. These tests check
 * what a single file cannot see: both languages, the same sections in the same
 * order, the same shape in each section, and anchors that appear only once.
 */
@QuarkusTest
class AboutTest {

    @Inject
    About about;

    @Test
    void bothLanguagesArePresent() {
        assertEquals(Set.of("fr", "en"), about.byLanguage().keySet());
    }

    @Test
    void bothLanguagesHaveTheSameShape() {
        AboutContent fr = about.get("fr");
        AboutContent en = about.get("en");

        assertEquals(fr.intro().size(), en.intro().size(), "intro");
        assertEquals(ids(fr.sections()), ids(en.sections()), "sections");

        for (int i = 0; i < fr.sections().size(); i++) {
            Section a = fr.sections().get(i);
            Section b = en.sections().get(i);
            assertEquals(a.paragraphs().size(), b.paragraphs().size(), a.id() + ": paragraphs");
            assertEquals(a.items().size(), b.items().size(), a.id() + ": items");
            assertEquals(a.groups().size(), b.groups().size(), a.id() + ": groups");
            for (int j = 0; j < a.groups().size(); j++) {
                assertEquals(a.groups().get(j).technologies().size(), b.groups().get(j).technologies().size(),
                        a.id() + ": group " + j);
            }
        }
    }

    @Test
    void sectionIdsAreUnique() {
        List<String> ids = ids(about.get("fr").sections());
        assertEquals(ids.size(), new HashSet<>(ids).size(), "section ids: " + ids);
    }

    /**
     * Returns the anchors of the sections, in display order.
     */
    private static List<String> ids(List<Section> sections) {
        return sections.stream().map(Section::id).toList();
    }
}
