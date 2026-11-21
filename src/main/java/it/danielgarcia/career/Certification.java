// src/main/java/it/danielgarcia/career/Certification.java

package it.danielgarcia.career;

import java.time.YearMonth;
import java.util.List;

/**
 * A certification or certified course, shown in the career section of the site.
 *
 * @param obtained    the month it was obtained, in ISO format (yyyy-MM)
 * @param title       the title of the certification
 * @param issuer      the organisation that issued it
 * @param summary     a one-line summary of the certification, shown in lists
 * @param description a paragraph that describes the course and how it was validated
 * @param highlights  the main skills covered, in display order
 * @param skills      the skills acquired, in display order
 * @param documents   the documents available on request, such as a certificate, or {@code null} if there are none
 */
public record Certification(String obtained, String title, String issuer, String summary, String description, List<String> highlights, List<String> skills, String documents) {

    /**
     * Checks that the month is a valid ISO month, so that a wrong date fails the build.
     */
    public Certification {
        YearMonth.parse(obtained);
    }

    /**
     * Returns the month it was obtained, as a date that can be sorted or compared.
     *
     * @return the month it was obtained
     */
    public YearMonth obtainedMonth() {
        return YearMonth.parse(obtained);
    }
}
