// src/main/java/it/danielgarcia/about/AboutContent.java

package it.danielgarcia.about;

import java.util.List;
import java.util.Objects;

/**
 * The texts of the About page and of its panel on the home page, in one language.
 *
 * @param panel          the panel "How this site is built" of the home page
 * @param intro          the paragraphs under the title of the About page
 * @param sections       the sections of the About page, in display order
 * @param repositoryLink the label of the link to the repository, at the end of the page
 */
public record AboutContent(Panel panel, List<String> intro, List<Section> sections, String repositoryLink) {
    /**
     * Checks that every part is present, so that a missing key fails the build.
     */
    public AboutContent {
        Objects.requireNonNull(panel, "about: panel is missing");
        if (intro == null || intro.isEmpty()) {
            throw new IllegalArgumentException("about: intro must have at least one paragraph");
        }
        if (sections == null || sections.isEmpty()) {
            throw new IllegalArgumentException("about: sections must list at least one section");
        }
        Objects.requireNonNull(repositoryLink, "about: repositoryLink is missing");
    }
}
