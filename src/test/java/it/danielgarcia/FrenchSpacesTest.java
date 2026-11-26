// src/test/java/it/danielgarcia/FrenchSpacesTest.java
package it.danielgarcia;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * FrenchSpacesTest — no ordinary space before : ; ! ? » or after « in the French texts.
 * <p>
 * ───────────────────────────────────────────────────────────────────────────
 * <p>
 * French typography puts a space before these signs, and it must be a
 * non-breaking space (U+00A0): with an ordinary one, the browser can put the
 * sign alone at the start of the next line. The narrow non-breaking space
 * (U+202F) is not used: the fonts of the site do not have it. Reads the fr.yml
 * files of the data/ folder: no application to start.
 */
class FrenchSpacesTest {

    /** An ordinary space before : ; ! ? », or after «. */
    private static final Pattern ORDINARY_SPACE = Pattern.compile(" [:;!?»]|« ");

    @Test
    void frenchTextsUseNonBreakingSpaces() throws IOException {
        List<String> found = new ArrayList<>();
        try (Stream<Path> files = Files.walk(Path.of("data"))) {
            for (Path file : files.filter(f -> f.getFileName().toString().equals("fr.yml")).toList()) {
                List<String> lines = Files.readAllLines(file);
                for (int i = 0; i < lines.size(); i++) {
                    if (ORDINARY_SPACE.matcher(lines.get(i)).find()) {
                        found.add(file + ":" + (i + 1) + ": " + lines.get(i).strip());
                    }
                }
            }
        }
        assertTrue(found.isEmpty(), "Ordinary spaces in French punctuation:\n" + String.join("\n", found));
    }
}
