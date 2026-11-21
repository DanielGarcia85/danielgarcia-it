// src/main/java/it/danielgarcia/career/SkillGroup.java

package it.danielgarcia.career;

import java.util.List;

/**
 * A group of related skills, shown as one line in the career section of the site.
 *
 * @param label  the name of the group
 * @param skills the skills in this group, in display order
 */
public record SkillGroup(String label, List<String> skills) {
}
