// src/main/java/it/danielgarcia/site/Build.java

package it.danielgarcia.site;

import java.time.Year;
import java.time.ZoneId;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

/**
 * Facts about the build of the site, for the templates: {cdi:build.year}.
 * The site is static, so they are the facts of the moment the pages are
 * generated; the weekly rebuild of the image keeps them current.
 */
@Named("build")
@ApplicationScoped
public class Build {

    /** The time zone of the site: Geneva. */
    private static final ZoneId ZONE = ZoneId.of("Europe/Zurich");

    /**
     * Returns the current year, for the copyright of the footer.
     *
     * @return the year of the build, in Geneva
     */
    public int year() {
        return Year.now(ZONE).getValue();
    }
}
