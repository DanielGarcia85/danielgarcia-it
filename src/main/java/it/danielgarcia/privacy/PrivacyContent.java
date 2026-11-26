// src/main/java/it/danielgarcia/privacy/PrivacyContent.java

package it.danielgarcia.privacy;

import java.util.List;
import java.util.Objects;

import it.danielgarcia.about.Section;

/**
 * The texts of the privacy page, in one language. The sections have the same shape
 * as those of the About page, so they use the same record.
 *
 * @param updated  the date of the last update, shown under the title
 * @param sections the sections of the page, in display order
 */
public record PrivacyContent(String updated, List<Section> sections) {
    /**
     * Checks that the date and at least one section are present, so that a missing
     * key fails the build.
     */
    public PrivacyContent {
        Objects.requireNonNull(updated, "privacy: updated is missing");
        if (sections == null || sections.isEmpty()) {
            throw new IllegalArgumentException("privacy: sections must list at least one section");
        }
    }
}
