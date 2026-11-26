// src/test/java/it/danielgarcia/ColorTokensTest.java
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
 * ColorTokensTest — no color value outside web/_tokens.scss.
 * <p>
 * ───────────────────────────────────────────────────────────────────────────
 * <p>
 * Rule of docs/visual-direction.md ("Colors"): the styles, the scripts and the
 * templates use the roles of the colors, var(--accent), never a color code.
 * Changing the palette then means changing one file. The brand logos of
 * templates/partials/icons/ keep their own colors, so they are skipped.
 * Reads the files of the project: no application to start.
 */
class ColorTokensTest {

    /** A color written as a value: #rgb to #rrggbbaa, or a color function such as rgb(. */
    private static final Pattern COLOR = Pattern.compile(
            "#[0-9a-fA-F]{3,8}\\b|\\b(?:rgba?|hsla?|hwb|lab|lch|oklab|oklch|color)\\(");

    private static final Path TOKENS = Path.of("web", "_tokens.scss");
    private static final Path ICONS = Path.of("templates", "partials", "icons");

    @Test
    void colorValuesAreOnlyInTheTokensFile() throws IOException {
        assertTrue(Files.isRegularFile(TOKENS), TOKENS + " is missing");

        List<String> found = new ArrayList<>();
        for (Path folder : List.of(Path.of("web"), Path.of("templates"))) {
            try (Stream<Path> files = Files.walk(folder)) {
                for (Path file : files
                        .filter(f -> f.toString().matches(".*\\.(scss|css|js|html)"))
                        .filter(f -> !f.equals(TOKENS) && !f.startsWith(ICONS))
                        .toList()) {
                    List<String> lines = Files.readAllLines(file);
                    for (int i = 0; i < lines.size(); i++) {
                        if (COLOR.matcher(lines.get(i)).find()) {
                            found.add(file + ":" + (i + 1) + ": " + lines.get(i).strip());
                        }
                    }
                }
            }
        }
        assertTrue(found.isEmpty(), "Color values outside " + TOKENS + ":\n" + String.join("\n", found));
    }
}
