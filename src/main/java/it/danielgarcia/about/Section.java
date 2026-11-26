// src/main/java/it/danielgarcia/about/Section.java

package it.danielgarcia.about;

import java.util.List;
import java.util.Objects;

/**
 * One section of a page of text (the About page, the privacy page): a title, then
 * paragraphs, a list, or groups of technologies, in that order. A section has at
 * least one of the three.
 *
 * @param id         the anchor of the section, in lower case, words joined by hyphens
 * @param title      the title of the section
 * @param paragraphs the paragraphs (Markdown), or none
 * @param items      the items of a list (plain text), or none
 * @param groups     the groups of technologies, or none
 */
public record Section(String id, String title, List<String> paragraphs, List<String> items, List<TechnologyGroup> groups) {
    /**
     * Checks the anchor and the title, replaces a missing list by an empty one, and
     * rejects an empty section.
     */
    public Section {
        if (id == null || !id.matches("[a-z]+(-[a-z]+)*")) {
            throw new IllegalArgumentException("about: section id must be lower case words joined by hyphens (" + id + ")");
        }
        Objects.requireNonNull(title, "about: section " + id + " has no title");
        paragraphs = paragraphs == null ? List.of() : paragraphs;
        items = items == null ? List.of() : items;
        groups = groups == null ? List.of() : groups;
        if (paragraphs.isEmpty() && items.isEmpty() && groups.isEmpty()) {
            throw new IllegalArgumentException("about: section " + id + " has no content");
        }
    }
}
