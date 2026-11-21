// src/main/java/it/danielgarcia/career/CareerContent.java

package it.danielgarcia.career;

import java.util.List;

/**
 * The whole content of the career section of the site, in one language.
 *
 * @param profile        the paragraphs of the profile, in display order
 * @param experience     the positions held, the current position first, then by end date from the most recent to the oldest
 * @param education      the diplomas and completed trainings, from the most recent to the oldest
 * @param certifications the certifications, from the most recent to the oldest
 * @param projects       the projects, from the most recent to the oldest
 * @param skillGroups    the groups of skills, in display order
 * @param languages      the spoken languages, in display order
 */
public record CareerContent(List<String> profile, List<Experience> experience, List<Education> education, List<Certification> certifications, List<Project> projects, List<SkillGroup> skillGroups, List<Language> languages) {
}
