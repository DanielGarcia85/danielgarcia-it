// src/test/java/it/danielgarcia/career/CareerTest.java
package it.danielgarcia.career;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

/**
 * CareerTest — checks of the career data in data/career, in every language.
 * <p>
 * ───────────────────────────────────────────────────────────────────────────
 * <p>
 * Each record already checks its own dates, and a project its own addresses, when
 * Roq builds it. These tests check what a single record cannot see: both languages,
 * the same entries in each, the length of the summaries, the order of the entries
 * (for the projects, only the featured ones) and the identifiers of the projects.
 * One test also makes sure that the address check of a project really rejects a
 * wrong address.
 */
@QuarkusTest
class CareerTest {

    private static final int SUMMARY_MAX_LENGTH = 180;

    @Inject
    Career career;

    @Test
    void bothLanguagesArePresent() {
        assertEquals(Set.of("fr", "en"), career.byLanguage().keySet());
    }

    @Test
    void bothLanguagesHaveTheSameEntries() {
        CareerContent fr = career.get("fr");
        CareerContent en = career.get("en");

        assertEquals(fr.profile().size(), en.profile().size(), "profile");
        assertEquals(fr.experience().size(), en.experience().size(), "experience");
        assertEquals(fr.education().size(), en.education().size(), "education");
        assertEquals(fr.certifications().size(), en.certifications().size(), "certifications");
        assertEquals(fr.projects().size(), en.projects().size(), "projects");
        assertEquals(fr.skillGroups().size(), en.skillGroups().size(), "skill groups");
        assertEquals(fr.languages().size(), en.languages().size(), "languages");

        for (int i = 0; i < fr.experience().size(); i++) {
            Experience a = fr.experience().get(i);
            Experience b = en.experience().get(i);
            assertEquals(a.started(), b.started(), "experience " + i + ": started");
            assertEquals(a.ended(), b.ended(), "experience " + i + ": ended");
            assertEquals(a.tasks().size(), b.tasks().size(), "experience " + i + ": tasks");
            assertEquals(a.skills().size(), b.skills().size(), "experience " + i + ": skills");
        }
        for (int i = 0; i < fr.education().size(); i++) {
            Education a = fr.education().get(i);
            Education b = en.education().get(i);
            assertEquals(a.completed(), b.completed(), "education " + i + ": completed");
            assertEquals(a.highlights().size(), b.highlights().size(), "education " + i + ": highlights");
            assertEquals(a.skills().size(), b.skills().size(), "education " + i + ": skills");
        }
        for (int i = 0; i < fr.certifications().size(); i++) {
            Certification a = fr.certifications().get(i);
            Certification b = en.certifications().get(i);
            assertEquals(a.obtained(), b.obtained(), "certification " + i + ": obtained");
            assertEquals(a.highlights().size(), b.highlights().size(), "certification " + i + ": highlights");
            assertEquals(a.skills().size(), b.skills().size(), "certification " + i + ": skills");
        }
        for (int i = 0; i < fr.projects().size(); i++) {
            Project a = fr.projects().get(i);
            Project b = en.projects().get(i);
            assertEquals(a.id(), b.id(), "project " + i + ": id");
            assertEquals(a.completed(), b.completed(), "project " + a.id() + ": completed");
            assertEquals(a.featured(), b.featured(), "project " + a.id() + ": featured");
            assertEquals(a.grade() == null, b.grade() == null, "project " + a.id() + ": grade in one language only");
            assertEquals(a.client() == null, b.client() == null, "project " + a.id() + ": client in one language only");
            assertEquals(a.site(), b.site(), "project " + a.id() + ": site");
            assertEquals(a.repository(), b.repository(), "project " + a.id() + ": repository");
            assertEquals(a.download(), b.download(), "project " + a.id() + ": download");
            assertEquals(a.highlights().size(), b.highlights().size(), "project " + a.id() + ": highlights");
            assertEquals(a.skills().size(), b.skills().size(), "project " + a.id() + ": skills");
            assertEquals(a.stack(), b.stack(), "project " + a.id() + ": stack");
        }
        for (int i = 0; i < fr.skillGroups().size(); i++) {
            assertEquals(fr.skillGroups().get(i).skills().size(), en.skillGroups().get(i).skills().size(),
                    "skill group " + i + ": skills");
        }
    }

    @Test
    void everyEntryIsComplete() {
        for (CareerContent content : career.byLanguage().values()) {
            for (Experience experience : content.experience()) {
                assertSummary(experience.summary(), experience.role());
                assertNotNull(experience.description(), experience.role() + ": no description");
                assertNotNull(experience.tasks(), experience.role() + ": no task list");
                assertNotNull(experience.skills(), experience.role() + ": no skill list");
            }
            for (Education education : content.education()) {
                assertSummary(education.summary(), education.degree());
                assertNotNull(education.description(), education.degree() + ": no description");
                assertNotNull(education.highlights(), education.degree() + ": no highlight list");
                assertNotNull(education.skills(), education.degree() + ": no skill list");
            }
            for (Certification certification : content.certifications()) {
                assertSummary(certification.summary(), certification.title());
                assertNotNull(certification.description(), certification.title() + ": no description");
                assertNotNull(certification.highlights(), certification.title() + ": no highlight list");
                assertNotNull(certification.skills(), certification.title() + ": no skill list");
            }
            for (Project project : content.projects()) {
                assertSummary(project.summary(), project.id());
                assertNotNull(project.role(), project.id() + ": no role");
                assertFalse(project.role().isBlank(), project.id() + ": empty role");
                assertNotNull(project.description(), project.id() + ": no description");
                assertNotNull(project.highlights(), project.id() + ": no highlight list");
                assertNotNull(project.skills(), project.id() + ": no skill list");
                assertNotNull(project.stack(), project.id() + ": no stack list");
                if (project.featured()) {
                    assertFalse(project.description().isBlank(), project.id() + ": featured without a description");
                    assertFalse(project.highlights().isEmpty(), project.id() + ": featured without highlights");
                }
            }
        }
    }

    @Test
    void entriesAreNewestFirst() {
        for (CareerContent content : career.byLanguage().values()) {
            List<YearMonth> experienceEnds = content.experience().stream()
                    .map(experience -> experience.current() ? YearMonth.of(9999, 12) : experience.endedMonth())
                    .toList();
            assertNewestFirst(experienceEnds, "experience");
            assertNewestFirst(content.education().stream().map(Education::completedMonth).toList(), "education");
            assertNewestFirst(content.certifications().stream().map(Certification::obtainedMonth).toList(),
                    "certifications");
            assertNewestFirst(content.projects().stream().filter(Project::featured).map(Project::completedMonth).toList(),
                    "featured projects");
        }
    }

    @Test
    void projectIdentifiersAreUnique() {
        Set<String> seen = new HashSet<>();
        for (Project project : career.get("fr").projects()) {
            assertTrue(seen.add(project.id()), "project id used twice: " + project.id());
        }
    }

    @Test
    void projectAddressWithoutHttpsIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Project("test", "Test", "frame", null, "role",
                "2026-01", null, false, "summary", "", List.of(), List.of(), List.of(),
                null, "http://github.com/DanielGarcia85/test", null));
    }

    private static void assertSummary(String summary, String entry) {
        assertNotNull(summary, entry + ": no summary");
        assertFalse(summary.isBlank(), entry + ": empty summary");
        assertTrue(summary.length() <= SUMMARY_MAX_LENGTH,
                entry + ": summary longer than " + SUMMARY_MAX_LENGTH + " characters");
    }

    private static void assertNewestFirst(List<YearMonth> months, String section) {
        for (int i = 1; i < months.size(); i++) {
            assertFalse(months.get(i).isAfter(months.get(i - 1)),
                    section + ": entry " + i + " is newer than the one before it");
        }
    }
}
