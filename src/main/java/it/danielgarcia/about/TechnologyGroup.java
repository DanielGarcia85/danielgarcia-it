// src/main/java/it/danielgarcia/about/TechnologyGroup.java

package it.danielgarcia.about;

import java.util.List;
import java.util.Objects;

/**
 * A group of technologies of the same layer, shown under its own title on the About page.
 *
 * @param title        the name of the layer
 * @param technologies the technologies of this layer, in display order
 */
public record TechnologyGroup(String title, List<Technology> technologies) {
    /**
     * Checks that the group has a title and at least one technology.
     */
    public TechnologyGroup {
        Objects.requireNonNull(title, "about: a group of technologies has no title");
        if (technologies == null || technologies.isEmpty()) {
            throw new IllegalArgumentException("about: group " + title + " has no technology");
        }
    }
}
