// src/main/java/it/danielgarcia/contact/ContactContent.java

package it.danielgarcia.contact;

import java.util.List;
import java.util.Objects;

/**
 * The public contact details and the positions sought, in one language.
 *
 * @param name        the full name, as on the CV
 * @param location    the city, never the postal address
 * @param nationality the nationality, as on the CV
 * @param email       the public e-mail address
 * @param github      the address of the GitHub profile
 * @param linkedin    the address of the LinkedIn profile
 * @param cv          the path of the CV in PDF in this language, served from public/cv/
 * @param roles       the positions sought, from the most wanted to the least
 * @param workMode    the way of working sought
 */
public record ContactContent(String name, String location, String nationality, String email, String github, String linkedin, String cv, List<String> roles, String workMode) {
    /**
     * Checks that every field is present, that the profiles are https addresses, that the CV is served
     * from /cv/ and that at least one position is sought, so that a missing or wrong value fails the build.
     */
    public ContactContent {
        Objects.requireNonNull(name, "contact: name is missing");
        Objects.requireNonNull(location, "contact: location is missing");
        Objects.requireNonNull(nationality, "contact: nationality is missing");
        Objects.requireNonNull(email, "contact: email is missing");
        Objects.requireNonNull(workMode, "contact: workMode is missing");
        if (!email.contains("@")) {
            throw new IllegalArgumentException("contact: email is not an e-mail address (" + email + ")");
        }
        checkAddress("github", github);
        checkAddress("linkedin", linkedin);
        if (cv == null || !cv.startsWith("/cv/") || !cv.endsWith(".pdf")) {
            throw new IllegalArgumentException("contact: cv must be a PDF path under /cv/ (" + cv + ")");
        }
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("contact: roles must list at least one position");
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
