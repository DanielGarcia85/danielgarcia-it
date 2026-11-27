// src/main/java/it/danielgarcia/about/Terminal.java

package it.danielgarcia.about;

import java.util.List;

/**
 * A few lines of terminal under the text of a section of the About page: the
 * commands and what they print. A line that starts with "$ " is a command, a line
 * that starts with "# " a comment, any other line what the command prints; a line
 * that reports a successful build gets the color of a success. A command can end
 * with a comment, after at least two spaces: "$ ./mvnw quarkus:dev   # Linux".
 * The commands are the same in every language; only the comments are translated.
 *
 * @param folded true to show the terminal closed, under a line that opens it
 * @param lines  the lines, in display order (plain text)
 */
public record Terminal(boolean folded, List<String> lines) {

    /** The prompt that starts a command. */
    static final String PROMPT = "$ ";

    /** The sign that starts a comment. */
    static final String COMMENT = "# ";

    /** What separates a command from the comment at the end of its line. */
    static final String END_COMMENT = "  # ";

    /**
     * Rejects a terminal without lines or with an empty line.
     */
    public Terminal {
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("about: a terminal has no line");
        }
        for (String line : lines) {
            if (line == null || line.isBlank()) {
                throw new IllegalArgumentException("about: a terminal has an empty line");
            }
        }
    }

    /**
     * Returns the commands, without their prompt and their comment, in display order.
     *
     * @return the commands of the lines that start with the prompt
     */
    public List<String> commands() {
        return output().stream().filter(line -> line.kind().equals("command")).map(Line::text).toList();
    }

    /**
     * Returns the lines, each with its kind, for the template.
     *
     * @return the lines in display order
     */
    public List<Line> output() {
        return lines.stream().map(Line::of).toList();
    }

    /**
     * One line of a terminal.
     *
     * @param kind    command, comment, success (a build that passed) or output
     * @param text    the text of the line, without its prompt and its comment
     * @param comment the comment at the end of a command, with the spaces before it, or {@code null}
     */
    public record Line(String kind, String text, String comment) {

        static Line of(String line) {
            if (line.startsWith(PROMPT)) {
                String command = line.substring(PROMPT.length());
                int end = command.indexOf(END_COMMENT);
                if (end < 0) {
                    return new Line("command", command, null);
                }
                String text = command.substring(0, end).stripTrailing();
                return new Line("command", text, command.substring(text.length()));
            }
            if (line.startsWith(COMMENT)) {
                return new Line("comment", line, null);
            }
            if (line.contains("BUILD SUCCESS")) {
                return new Line("success", line, null);
            }
            return new Line("output", line, null);
        }
    }
}
