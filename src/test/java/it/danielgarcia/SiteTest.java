// src/test/java/it/danielgarcia/SiteTest.java
package it.danielgarcia;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import org.junit.jupiter.api.Test;

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
 */
@QuarkusTest
@RoqAndRoll
class SiteTest {

    @Test
    void frenchHomePage() {
        given().get("/").then()
                .statusCode(200)
                .body(containsString("<html lang=\"fr\">"))
                .body(containsString("<title>Daniel Garcia — Développeur Full Stack &amp; DevOps</title>"));
    }

    @Test
    void englishHomePage() {
        given().get("/en/").then()
                .statusCode(200)
                .body(containsString("<html lang=\"en\">"))
                .body(containsString("<title>Daniel Garcia — Full Stack &amp; DevOps Developer</title>"));
    }

    @Test
    void frenchNotFoundPage() {
        given().get("/404.html").then()
                .statusCode(200)
                .body(containsString("<html lang=\"fr\">"))
                .body(containsString("<title>Page introuvable — Daniel Garcia</title>"));
    }

    @Test
    void englishNotFoundPage() {
        given().get("/en/404.html").then()
                .statusCode(200)
                .body(containsString("<html lang=\"en\">"))
                .body(containsString("<title>Page not found — Daniel Garcia</title>"));
    }

    @Test
    void noBrokenInternalLink() {
        // The link checker builds file URIs without encoding '#', so under a folder whose
        // path contains '#' (the OneDrive folder) every link looks broken: skipped there,
        // run in Docker and in CI.
        assumeFalse(System.getProperty("user.dir").contains("#"),
                "Project path contains '#': the link checker cannot read it");
        assertTrue(RoqLinks.checkInternal().isEmpty(), "Broken internal links: see the test log");
    }
}
