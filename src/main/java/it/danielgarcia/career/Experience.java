// src/main/java/it/danielgarcia/career/Experience.java

package it.danielgarcia.career;

import java.time.YearMonth;
import java.util.List;

/**
 * A position held, shown in the experience part of the career section of the site.
 *
 * @param employer    the company or organisation
 * @param role        the title of the position
 * @param place       the city where the position was held
 * @param started     the month the position started, in ISO format (yyyy-MM)
 * @param ended       the month the position ended, in ISO format (yyyy-MM), or {@code null} if it is the current position
 * @param mention     a short note about the position, such as a temporary assignment, or {@code null} if there is none
 * @param summary     a one-line summary of the position, shown in lists
 * @param description a paragraph that describes the position
 * @param tasks       the main tasks and achievements, in display order; empty if the summary is enough
 * @param skills      the skills used in this position, in display order
 * @param reference   the name and position of the person who can give a reference, or {@code null} if there is none; not shown on the site
 * @param documents   the documents available on request, such as a work certificate, or {@code null} if there are none
 */
public record Experience(String employer, String role, String place, String started, String ended, String mention, String summary, String description, List<String> tasks, List<String> skills, String reference, String documents) {

    /**
     * Checks the months, so that a wrong date fails the build.
     */
    public Experience {
        YearMonth start = YearMonth.parse(started);
        if (ended != null && YearMonth.parse(ended).isBefore(start)) {
            throw new IllegalArgumentException(
                    "Experience '" + role + "' ends before it starts: " + started + " > " + ended);
        }
    }

    /**
     * Returns the month the position started, as a date.
     *
     * @return the month the position started
     */
    public YearMonth startedMonth() {
        return YearMonth.parse(started);
    }

    /**
     * Returns the month the position ended, as a date.
     *
     * @return the month the position ended, or {@code null} if it is the current position
     */
    public YearMonth endedMonth() {
        return ended == null ? null : YearMonth.parse(ended);
    }

    /**
     * Tells whether this is the current position.
     *
     * @return {@code true} if the position has not ended
     */
    public boolean current() {
        return ended == null;
    }

    /**
     * Tells whether a reference is available on request for this position.
     *
     * @return {@code true} if a reference is known
     */
    public boolean hasReference() {
        return reference != null;
    }

    /**
     * Returns the month the position started, as shown on the site and the CV (09/2023).
     *
     * @return the month and the year, separated by a slash
     */
    public String startedLabel() {
        return Months.label(startedMonth());
    }

    /**
     * Returns the month the position ended, as shown on the site and the CV, or null for the current position.
     *
     * @return the month and the year, separated by a slash, or {@code null}
     */
    public String endedLabel() {
        return ended == null ? null : Months.label(endedMonth());
    }
}
