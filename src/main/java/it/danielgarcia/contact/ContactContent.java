// src/main/java/it/danielgarcia/contact/ContactContent.java

package it.danielgarcia.contact;

import java.util.List;
import java.util.Objects;

/**
 * The public contact details and the positions sought, in one language.
 *
 * @param name        the full name, as on the CV
 * @param title       the job title, shown after the name in the browser tab and at the top of the home page
 * @param degree      the degree, shown under the title on the home page, followed by the location
 * @param roles       the positions sought, from the most wanted to the least
 * @param workMode    the way of working sought
 * @param location    the city, never the postal address
 * @param nationality the nationality, as on the CV
 * @param email       the public e-mail address
 * @param github      the address of the GitHub profile
 * @param linkedin    the address of the LinkedIn profile
 * @param cv          the path of the CV in PDF in this language, served from public/cv/
 */
public record ContactContent(String name, String title, String degree, List<String> roles, String workMode, String location, String nationality, String email, String github, String linkedin, String cv) {
    /**
     * Checks that every field is present, that at least one position is sought, that the profiles are
     * https addresses and that the CV is served from /cv/, so that a missing or wrong value fails the build.
     */
    public ContactContent {
        Objects.requireNonNull(name, "contact: name is missing");
        Objects.requireNonNull(title, "contact: title is missing");
        Objects.requireNonNull(degree, "contact: degree is missing");
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("contact: roles must list at least one position");
        }
        Objects.requireNonNull(workMode, "contact: workMode is missing");
        Objects.requireNonNull(location, "contact: location is missing");
        Objects.requireNonNull(nationality, "contact: nationality is missing");
        Objects.requireNonNull(email, "contact: email is missing");
        if (!email.contains("@")) {
            throw new IllegalArgumentException("contact: email is not an e-mail address (" + email + ")");
        }
        checkAddress("github", github);
        checkAddress("linkedin", linkedin);
        if (cv == null || !cv.startsWith("/cv/") || !cv.endsWith(".pdf")) {
            throw new IllegalArgumentException("contact: cv must be a PDF path under /cv/ (" + cv + ")");
        }
    }

    /**
     * Rejects a missing address or an address that does not start with https://.
     *
     * @param field   the name of the field, for the error message
     * @param address the address to check
     */
    private static void checkAddress(String field, String address) {
        if (address == null || !address.startsWith("https://")) {
            throw new IllegalArgumentException("contact: " + field + " must start with https:// (" + address + ")");
        }
    }
}
