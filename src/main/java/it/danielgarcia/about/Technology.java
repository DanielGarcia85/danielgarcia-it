// src/main/java/it/danielgarcia/about/Technology.java

package it.danielgarcia.about;

import java.util.Objects;

/**
 * A technology used by the site, and what it does there.
 *
 * @param name the name of the technology
 * @param role what it does for the site
 */
public record Technology(String name, String role) {
    /**
     * Checks that both fields are present.
     */
    public Technology {
        Objects.requireNonNull(name, "about: a technology has no name");
        Objects.requireNonNull(role, "about: technology " + name + " has no role");
    }
}
