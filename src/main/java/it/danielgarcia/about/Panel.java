// src/main/java/it/danielgarcia/about/Panel.java

package it.danielgarcia.about;

import java.util.Objects;

/**
 * The panel "About this site" of the home page, which leads to the About page.
 *
 * @param title  the title of the panel
 * @param text   the delivery pipeline in a few sentences (Markdown)
 * @param button the label of the button to the About page
 */
public record Panel(String title, String text, String button) {
    /**
     * Checks that every field is present.
     */
    public Panel {
        Objects.requireNonNull(title, "about panel: title is missing");
        Objects.requireNonNull(text, "about panel: text is missing");
        Objects.requireNonNull(button, "about panel: button is missing");
    }
}
