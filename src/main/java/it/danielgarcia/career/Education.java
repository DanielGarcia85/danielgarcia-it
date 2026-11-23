// src/main/java/it/danielgarcia/career/Education.java

package it.danielgarcia.career;

import java.time.YearMonth;
import java.util.List;

/**
 * A diploma or completed training, shown in the career section of the site.
 *
 * @param institution the school or institution
 * @param place       the city where the training took place
 * @param degree      the title of the diploma
 * @param started     the month the training started, in ISO format (yyyy-MM), or {@code null} if it is not known
 * @param completed   the month the diploma was obtained, in ISO format (yyyy-MM)
 * @param mention     a short note about the training, such as part-time study, or {@code null} if there is none
 * @param grade       the grade obtained, or {@code null} if there is none to show
 * @param summary     a one-line summary of the training, shown in lists
 * @param description a paragraph that describes the training
 * @param highlights  the main projects, modules or results, in display order
 * @param skills      the skills acquired during this training, in display order; empty if there are none
 * @param documents   the documents available on request, such as a diploma, or {@code null} if there are none
 */
public record Education(String institution, String place, String degree, String started, String completed, String mention, String grade, String summary, String description, List<String> highlights, List<String> skills, String documents) {

    /**
     * Checks the months, so that a wrong date fails the build.
     */
    public Education {
        YearMonth end = YearMonth.parse(completed);
        if (started != null && YearMonth.parse(started).isAfter(end)) {
            throw new IllegalArgumentException(
                    "Education '" + degree + "' starts after it ends: " + started + " > " + completed);
        }
    }

    /**
     * Returns the month the training started, as a date.
     *
     * @return the month the training started, or {@code null} if it is not known
     */
    public YearMonth startedMonth() {
        return started == null ? null : YearMonth.parse(started);
    }

    /**
     * Returns the month the diploma was obtained, as a date.
     *
     * @return the month the diploma was obtained
     */
    public YearMonth completedMonth() {
        return YearMonth.parse(completed);
    }
}
