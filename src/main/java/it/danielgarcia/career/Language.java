// src/main/java/it/danielgarcia/career/Language.java

package it.danielgarcia.career;

/**
 * A spoken language and its level, shown in the career section of the site.
 *
 * @param name  the name of the language
 * @param level the level reached in this language
 */
public record Language(String name, String level) {
}
