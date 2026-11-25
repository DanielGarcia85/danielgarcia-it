// src/main/java/it/danielgarcia/career/Project.java

package it.danielgarcia.career;

import java.time.YearMonth;
import java.util.List;

/**
 * A project, shown in the projects section of the site and, for every project, in the archive.
 *
 * @param id          the identifier of the project, in lower case with hyphens (ge-trouve)
 * @param title       the name of the project
 * @param frame       the frame of the project: client, Bachelor thesis or school project, and the team if there is one
 * @param client      the client of the project, or {@code null} if there is none
 * @param role        my role in the project; for a team project, my exact part in it
 * @param completed   the month the project was completed, in ISO format (yyyy-MM)
 * @param grade       the grade obtained, or {@code null} if there is none
 * @param featured    {@code true} for a featured project, {@code false} for a project shown only in the archive
 * @param summary     one sentence, shown on the project card and in the archive
 * @param description the full text of the project, empty for a project shown only in the archive
 * @param highlights  the main points of the project, empty for a project shown only in the archive
 * @param skills      the skills used in the project
 * @param stack       the technologies of the project, as shown on its card
 * @param site        the address of the project online, or {@code null} if there is none
 * @param repository  the address of the public source code, or {@code null} if it is not public
 * @param download    the address of the installer or executable to download, or {@code null} if there is none
 */
public record Project(String id, String title, String frame, String client, String role, String completed, String grade, boolean featured, String summary, String description, List<String> highlights, List<String> skills, List<String> stack, String site, String repository, String download) {
    /**
     * Checks that the month is a valid ISO month and that every address starts with https://,
     * so that a wrong date or a broken link fails the build.
     */
    public Project {
        YearMonth.parse(completed);
        checkAddress(id, "site", site);
        checkAddress(id, "repository", repository);
        checkAddress(id, "download", download);
    }

    /**
     * Returns the month the project was completed, as a date that can be sorted or compared.
     *
     * @return the month the project was completed
     */
    public YearMonth completedMonth() {
        return YearMonth.parse(completed);
    }

    /**
     * Returns the month the project was completed, as shown on the site and the CV (11/2026).
     *
     * @return the month and the year, separated by a slash
     */
    public String completedLabel() {
        return Months.label(completedMonth());
    }

    /**
     * Rejects an address that does not start with https://; a missing address is allowed.
     *
     * @param id      the identifier of the project, for the error message
     * @param field   the name of the field, for the error message
     * @param address the address to check, or {@code null}
     */
    private static void checkAddress(String id, String field, String address) {
        if (address != null && !address.startsWith("https://")) {
            throw new IllegalArgumentException(id + ": " + field + " must start with https:// (" + address + ")");
        }
    }
}
