// src/main/java/it/danielgarcia/career/Months.java

package it.danielgarcia.career;

import java.time.YearMonth;

/**
 * Writes the months of the career as they are shown on the site (11/2026).
 */
final class Months {

    private Months() {
    }

    /**
     * Returns the month and the year, separated by a slash, the month on two digits.
     *
     * @param month the month to write
     * @return the month as shown on the site, for example 11/2026
     */
    static String label(YearMonth month) {
        return String.format("%02d/%d", month.getMonthValue(), month.getYear());
    }
}
