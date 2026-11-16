# danielgarcia.it

Source code of my CV and portfolio site, in French and English, generated as static
HTML by [Roq](https://iamroq.dev/), a static site generator built on Quarkus.

**Status:** work in progress. The site is online at <https://danielgarcia.it>, still as a
skeleton: a shared layout, the home page and the "page not found" page in both languages,
and the tests of the generated site. Every merge into `main` is tested, built into a Docker
image and deployed automatically.

## Stack

- Java 25 (Eclipse Temurin)
- Quarkus and Roq, with Qute templates (versions in `pom.xml`)
- Maven, through the Maven Wrapper (version in `.mvn/wrapper/maven-wrapper.properties`)
- Tests: JUnit, RestAssured and the Roq testing library

## How it works

- `content/` holds the pages: one file per address, French at the root, English under `/en/`.
  Each page gives its title, description, language (`lang`) and the address of its
  translation (`translation`) in its front matter, then only its own content.
- `templates/layouts/page.html` is the one page skeleton: `<head>`, language switch and
  `<main>`, written once for every page.
- `data/i18n/fr.yml` and `data/i18n/en.yml` hold the interface texts, with the same keys.

Diagrams:

- [From the command to the HTML pages](docs/diagrams/build-and-run.svg)
- [How a page is rendered, and how its language is chosen](docs/diagrams/page-rendering-and-i18n.svg)
- [From a commit to the site in production](docs/diagrams/ci-cd-and-deployment.svg)

## Run it locally

Requirements: a JDK 25. Maven itself is downloaded by the wrapper.

```bash
# Development mode, with live reload, on http://localhost:8080
./mvnw quarkus:dev

# Generate the static site into target/roq
QUARKUS_ROQ_GENERATOR_BATCH=true ./mvnw package quarkus:run

# Run the tests
./mvnw test
```

On Windows, use `.\mvnw.cmd` and set the variable with
`$env:QUARKUS_ROQ_GENERATOR_BATCH = "true"`.

## Tests

The tests generate the site, serve the generated files and check them as a visitor would:
the home page and the "page not found" page in both languages (status, language, title),
then every internal link. The link check is skipped when the project path contains `#`,
which the link checker cannot read.

## Use of AI

I built this project with the help of Claude (Anthropic) as a coding assistant.

## License

- Code: [MIT](LICENSE).
- Content: the texts of the site (`content/`, `data/`), my photo and the images of my
  projects are all rights reserved, and are not covered by the MIT license.
