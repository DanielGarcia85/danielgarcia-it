// src/test/java/it/danielgarcia/SiteTest.java
package it.danielgarcia;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import io.quarkiverse.roq.testing.RoqAndRoll;
import io.quarkiverse.roq.testing.RoqLinks;
import io.quarkus.test.junit.QuarkusTest;

/**
 * SiteTest — tests of the generated site, as Nginx will serve it.
 * <p>
 * ───────────────────────────────────────────────────────────────────────────
 * <p>
 * {@code @RoqAndRoll} generates the site into target/roq, serves those files on
 * port 8082 and points RestAssured at that server: every test reads the real
 * output, not the running Quarkus application.
 * <p>
 * The tests check the structure of the pages, by the identifiers of their
 * sections and the addresses of their links, never the texts of the data: the
 * data have their own tests. Each test runs once per language.
 */
@QuarkusTest
@RoqAndRoll
class SiteTest {

    @ParameterizedTest
    @CsvSource({
            "/,    fr, /cv/daniel-garcia-cv-fr.pdf, /projets/",
            "/en/, en, /cv/daniel-garcia-cv-en.pdf, /en/projects/"
    })
    void homePage(String path, String language, String cv, String projectsPage) {
        given().get(path).then()
                .statusCode(200)
                .body(containsString("<html lang=\"" + language + "\">"))
                .body(containsString("id=\"profile-name\""))
                .body(containsString("id=\"compact-profile\" hidden"))
                .body(containsString("href=\"" + cv + "\""))
                .body(containsString("id=\"projects\""))
                .body(containsString("id=\"details-marketech\""))
                .body(not(containsString("id=\"project-projet-bi-covid\"")))
                .body(containsString("<a href=\"" + projectsPage + "\">"))
                .body(containsString("id=\"career\""))
                .body(containsString("id=\"education\""))
                .body(containsString("id=\"certifications\""))
                .body(containsString("id=\"skills\""))
                .body(containsString("id=\"languages\""))
                .body(containsString("id=\"contact\""))
                .body(containsString("href=\"mailto:"))
                .body(containsString("/static/bundle/app-"));
    }

    @ParameterizedTest
    @CsvSource({
            "/projets/,     fr",
            "/en/projects/, en"
    })
    void projectsPage(String path, String language) {
        given().get(path).then()
                .statusCode(200)
                .body(containsString("<html lang=\"" + language + "\">"))
                .body(not(containsString("id=\"compact-profile\" hidden")))
                .body(containsString("id=\"featured-projects\""))
                .body(containsString("id=\"project-marketech\""))
                .body(containsString("id=\"other-projects\""))
                .body(containsString("id=\"project-projet-bi-covid\""));
    }

    @ParameterizedTest
    @CsvSource({
            "/404.html,    fr",
            "/en/404.html, en"
    })
    void notFoundPage(String path, String language) {
        given().get(path).then()
                .statusCode(200)
                .body(containsString("<html lang=\"" + language + "\">"));
    }

    @ParameterizedTest
    @CsvSource({
            "/,             /",
            "/projets/,     /",
            "/404.html,     /",
            "/en/,          /en/",
            "/en/projects/, /en/",
            "/en/404.html,  /en/"
    })
    void header(String path, String home) {
        given().get(path).then()
                .statusCode(200)
                .body(containsString("<a href=\"#content\">"))
                .body(containsString("<main id=\"content\">"))
                .body(containsString("id=\"compact-profile\""))
                .body(containsString("id=\"menu\""))
                .body(containsString("href=\"" + home + "#projects\""));
    }

    @Test
    void noBrokenInternalLink() {
        // The link checker builds file URIs without encoding '#', so under a path that
        // contains '#' every link looks broken: the check is skipped there.
        assumeFalse(System.getProperty("user.dir").contains("#"),
                "Project path contains '#': the link checker cannot read it");
        assertTrue(RoqLinks.checkInternal().isEmpty(), "Broken internal links: see the test log");
    }
}
