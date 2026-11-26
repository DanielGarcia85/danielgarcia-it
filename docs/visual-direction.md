# Visual direction

The rules of the look of danielgarcia.it. Every style of the site follows them; a change of the
look starts here, then goes into the CSS.

The mockups of `docs/mockups/` show these rules applied: `home.html` for the home page, and the
two ideas kept for later (see [Kept for later](#kept-for-later)). Their texts are examples: the
content of the site comes from `data/contact/` and `data/career/`, which are authoritative.

## Direction

**Accessibility first.** Every choice is made so that the site is easy to read, to use with a
keyboard or a screen reader, and on a phone, and can be justified by that. On top of it, a
style with energy: a large name, a petrol gradient, colored marks, and a few nods to the
terminal (my DevOps side), never as decoration alone.

## Structure

### Grid

- A 12-column grid, 1320 px wide at most, with 24 px gutters and 24 px side margins.
- Running text is never wider than 80 characters.
- The reading order is always the order of the HTML. Under 760 px wide, the projects go to one
  per row; under 900 px, the skills go to two columns.

### Header

- One header for the whole site, always at the top of the screen, 68 px high, separated from
  the page by a thin line (`--line`) across the whole width. It always stays on one line.
- **On the right, the menu, which never moves**: Profile, Projects, Career, Skills, About,
  Contact, then the FR | EN switch and the light / dark button.
  - Profile goes back to the top of the page. About goes to the section "How this site is
    built", which leads to the About page.
  - The menu marks the section being read: underline in `--accent` and bold, with
    `aria-current="location"`. An entry that was clicked stays marked as long as its section is
    on screen, even when the section is too short to reach the top.
  - The FR | EN switch is a pair of buttons in one frame; the current language is filled
    (`--accent-fill`, white text, `--accent-edge` border) and bold. It leads to the same page in
    the other language (the French and English pages, each with its own `lang`). In the mockup,
    it swaps the texts in place.
  - The light / dark button names the theme it switches to ("Dark" in the light theme, "Light"
    in the dark one). The buttons have a fixed width, so that nothing moves when the language or
    the theme changes.
- **On the left, the compact profile**: small round photo, name, title, then GitHub, LinkedIn and
  CV. It is hidden while the buttons of the large profile are on screen, and appears as soon as
  they leave it. While hidden, its links are out of the keyboard order (they are already in the
  large profile).
- When the room gets short, the compact profile gives up its parts in this order, and never
  the menu:
  1. under 1280 px wide, the title;
  2. under 1190 px, the GitHub, LinkedIn and CV buttons;
  3. under 910 px, the menu entries fold into a "Menu" button, which opens them as a list under
     the header (`aria-expanded`).

  The photo and the name always stay.
- The appearance of the compact profile is **the only animation of the site** (a fade of
  0.2 s), turned off when the visitor asks for reduced motion (`prefers-reduced-motion`).
- Without JavaScript, nothing breaks: the compact profile stays hidden and the menu stays
  reachable.
- The header never hides the element that has the keyboard focus (WCAG 2.4.11):
  `scroll-padding-top` keeps it below. On a low screen or under strong zoom, the header stops
  being fixed.
- A "Skip to content" link is the first element of the page, visible when it gets the focus.

### Footer

Above a thin line, in small text: the version in production ("Live version: commit `ef92dfc`",
the commit in the mono font, linked to that commit on GitHub), its build date, and a link to the
source code of the site. No LinkedIn there: the footer is about the site itself; the profiles
are in the header and in Contact.

### Home page, in this order

Every text of the home page comes from `data/contact/<lang>.yml` and `data/career/<lang>.yml`.

1. **Profile**, visible without scrolling, on a band with the gradient (see [Style](#style)):
   - on the left, the round photo (156 px, `--accent-fill` border), and next to it the name in
     large type;
   - under them, the title in `--accent`, the three paragraphs of the profile (with their
     links), and three buttons: GitHub, LinkedIn, CV (PDF) in yellow;
   - on the right, a console window, as high as the whole profile: a title bar
     `daniel@vps: ~`, then these commands and their results:

     ```
     $ whoami                          daniel-garcia
     $ cat role.txt                    the title
     $ ls -la projets/                 total, then one line per featured project
     $ cat stack.txt                   java quarkus python django / vue docker linux nginx
     $ git log -1 --oneline            the commit in production
     $ curl -sI https://danielgarcia.it   HTTP/2 200
     $ _
     ```

     The commit and the answer of the server are real data of the site, built last. The console
     is a picture of the site, not a terminal: it is read as text, and its title bar is hidden
     from screen readers.
   - Under 940 px wide, the console goes away, so that it never covers the name; the photo, the
     name and the text stay. Under 640 px, the photo shrinks to 96 px.
2. **Projects**, the largest section: the five featured projects as cards, **two per row**, the
   last one alone on its row. Each card has its social preview image (1280 × 640, from
   `public/images/projects/<id>.png`, once it exists), its name, its summary, its stack as tags,
   and its links (Code, Site), only those that exist. On hover, the card lifts slightly; hovering
   never opens anything. A click anywhere on the card, or on its "Open" button, opens a **large
   card** (a native modal `<dialog>`) on the same page: the description first, then the context
   (team projects always give the size of the team and my exact role), the client, the date
   ("Built"), the grade, the highlights, the skills and the links (Code, Site, Download). It
   closes with Escape or its "Close" button. SkyBurst gets a filled "Play" button that opens its
   player only once its Web build exists. Under the cards, a link to the projects page.
3. **Career**, in three parts with their own titles: **Professional experience** first, then
   **Education**, then **Certifications**. Each part is a list, most recent first; each line has
   its dates (as on the CV, 11/2026) in `--accent` and in bold, the position, the degree or the
   certification in bold, the employer, the school or the issuer in `--muted`. A click on a line
   opens the same large card as the projects: the mention, the description, the tasks or the
   highlights, the skills, the documents available on request and, for a certification, a link
   to the training. The name of a referee is never shown. One menu entry (Career) leads to all
   three.
4. **Skills**: the groups of skills, in their order, as a named list in four columns (two under
   900 px), then the spoken languages with their level. No levels for the skills, no bars, no
   percentages.
5. **How this site is built**: a panel on `--surface`, with the rule of the cards at the top,
   the pipeline in one sentence, and a filled button to the About page.
6. **Contact**: the positions sought and the way of working, the city and the nationality, then
   the e-mail address, LinkedIn, GitHub and the CV (PDF). No phone number, no postal address.

### Projects page

`/projets/` and `/en/projects/`, in two groups:

- **Featured projects**: the same cards and large cards as on the home page;
- **Other projects**, in their order of importance: compact cards, without a large card, with
  the context, the role, the date, then the skills and the stack under their own headings.

Later, after the CSS: a filter by technology at the top of the page. Later still: one detailed
page per project (screenshots, videos, quotes), opened from a "More" button in its large card.

## Style

One style, two themes, switched by the button in the header. Both themes share every shape and
every layout; the button only changes the values of the color tokens.

- **The gradient of the profile is horizontal only**: from `--paper` to the dark petrol halo
  (`#12313A`) at the right edge, the same halo in both themes. It ends on a straight line at the
  bottom of the band, with no vertical fade. The halo is 512 px wide at most, 40 % of the screen
  at most, so that it narrows smoothly with the screen; on large screens, it is where the
  console goes. The gradient starts a little before the end of the text (an eighth of the width
  of the halo) and overlaps its last words lightly; the text and the links keep a contrast of
  at least 4.5:1 there. Under 640 px, the text takes the whole width and runs over the halo:
  a deliberate exception to the contrast rule, accepted for the look (the end of some lines
  falls to about 2:1, the links to about 1.5:1).

Shapes common to both themes:

- Square corners everywhere, except the round photos.
- Buttons with a 2 px border: outlined in `--ink` (GitHub, LinkedIn, theme, language), filled in
  yellow (CV), or filled in `--accent-fill` with white text and an `--accent-edge` border (main
  action of a section, current language).
- Icons before the text of the GitHub, LinkedIn and CV buttons, 20 px: the official GitHub mark
  and the LinkedIn "In" icon, untouched except for their size, and a document icon drawn for the
  site. They are decoration (`aria-hidden`): the text of the button gives its name. The GitHub
  mark and the document icon take the color of the text (`currentColor`).
- Project cards and the "How this site is built" panel on `--surface`, with a thin `--line`
  border and a 6 px `--accent-fill` rule at the top.
- Technologies as square tags (14 px, `--line` border), in the normal font.
- Section titles preceded by `##`, in the mono font and in `--accent`, as a Markdown heading (a
  nod to the terminal). The `##` is decoration: hidden from screen readers (`aria-hidden`), so
  that they do not read "hash hash". No section numbers.
- The mono font only where there is real code: the console, commands, the commit, the `##`.
- Links underlined, with the underline 3 px below the text.

Default theme: the one of the visitor's system (`prefers-color-scheme`); the button overrides it.

## Accessibility

WCAG 2.2, level AA, in both themes:

- Text: contrast of at least 4.5:1 with its background; large text (24 px, or about 18.5 px
  bold): 3:1 (1.4.3).
- Interface components, their states and the focus indicator: 3:1 (1.4.11). The focus is always
  visible: a 3 px outline in `--accent`, 3 px away from the element.
- No information by color alone (1.4.1): **links are always underlined**, the current menu entry
  and the current language are also bold, errors are said in words.
- The focus is never hidden by the header (2.4.11).
- Real text everywhere, never an image of text; correct heading structure; alternative text for
  every meaningful image; usable with a keyboard, with a screen reader and on a phone.
- Nothing moves by itself: no slideshow, no carousel, no video that plays alone, no smooth
  scrolling, no parallax.

Lighthouse bases its accessibility scoring on axe (Deque) assessments; the target is a score of
100 in both themes.

## Typography

**Atkinson Hyperlegible Next**, with **Atkinson Hyperlegible Mono** for code. Designed by the
Braille Institute for legibility: every character differs from the others, even for low vision.

- Version 2.001, SIL Open Font License 1.1, no Reserved Font Name. Sources:
  `googlefonts/atkinson-hyperlegible-next` and `googlefonts/atkinson-hyperlegible-next-mono` on
  GitHub.
- Self-hosted, as variable WOFF2 files (weights 200 to 800), in `web/_fonts/`: the files
  `AtkinsonHyperlegibleNext[wght]` and `AtkinsonHyperlegibleMono[wght]` of the sources, renamed
  `AtkinsonHyperlegibleNext-Variable.woff2` and `AtkinsonHyperlegibleMono-Variable.woff2` (no
  brackets in the names), plus their italics only if the content needs them.
- The license travels with the files: `OFL.txt` next to them in the repository. The fonts are
  credited on the About page and in the License section of the README. The name of the Braille
  Institute is never used to promote the site, only to credit its work.
- **Slashed zero**: the zero of the font is slashed, with no alternative. Kept on purpose: it
  tells 0 from O. Option kept open: take the zero alone from another font with `unicode-range`,
  if the real pages show that it disturbs.
- Missing characters: the narrow no-break space (U+202F), replaced by the no-break space
  (U+00A0), and the arrow (U+2192), not used.
- Scale:

  | Element | Size |
  |---|---|
  | Body | 18 px, line height 1.6 |
  | Name in the profile | 40 to 72 px, following the width of the screen |
  | Title in the profile | 26 px, bold |
  | Section titles | 40 px |
  | Card titles | 22 px |
  | Menu | 16 px |
  | Buttons of the header, footer | 15 px |
  | Console | 15 px, line height 1.7 |
  | Tags, title of the console, compact title | 14 px |

  Small text is never under 14 px.

## Colors

Each color is a token named by its role. Palette **Leman**: petrol blue, cool neutrals.

| Token | Role | Light | Dark |
|---|---|---|---|
| `--paper` | page | `#F7F9FA` | `#0E1418` |
| `--surface` | cards, panels | `#ECF0F2` | `#162027` |
| `--ink` | text | `#0F1B22` | `#E4ECF0` |
| `--muted` | secondary text | `#4E5A61` | `#A7B4BC` |
| `--line` | borders, separators | `#77858E` | `#6B7B85` |
| `--accent` | colored text and marks | `#0B6878` | `#4BA0AF` |
| `--accent-fill` | filled backgrounds | `#0B6878` | `#0B6878` |
| `--on-accent-fill` | text on `--accent-fill` | `#FFFFFF` | `#FFFFFF` |
| `--accent-edge` | border of filled elements | `#4BA0AF` | `#4BA0AF` |

So between the two themes, the accent changes only for the text and the marks
(`--accent`); filled elements are exactly the same in both.

Where each accent token is used, and nowhere else:

- `--accent`: links, the profile title, the `##`, the years of the career, the underline of the
  current menu entry, the focus indicator.
- `--accent-fill`: filled buttons (main action of a section, current language), the rule at the
  top of the cards and panels, the border of the large photo.
- `--accent-edge`: the border of filled buttons and of the current language.

Lowest contrast, light / dark:

| Pair | Light | Dark |
|---|---|---|
| Text on page and surface | 6.2 | 7.8 |
| `--accent` on page and surface | 5.6 | 5.5 |
| `--line` against page and surface | 3.3 | 3.8 |
| White on `--accent-fill` | 6.4 | 6.4 |
| Filled element against the page | 5.6 (fill) | 5.5 (edge) |

Colors of the style that are not part of the palette, checked against the same thresholds:

| Use | Color | Contrast |
|---|---|---|
| Halo of the profile, both themes | `#12313A` | the text of the profile keeps its contrast at every width |
| CV button, text on it | `#F2C14E`, `#0F1B22` | 10.4 |
| Console: background, text, results | `#0E1418`, `#E4ECF0`, `#C9D4DA` | 15.5, 12.3 |
| Console: prompt `$`, commands | `#4BA0AF`, `#F2C14E` | 6.1, 11.1 |
| Console: success, secondary results | `#7FE0A0`, `#A7B4BC` | 11.5, 8.7 |
| Console: title bar, separators | `#2E3D47` | decoration |

The console looks the same in both themes.

### Rules

- One exception: the colors of a brand logo stay those of the brand, written in the logo itself
  (the LinkedIn blue `#0A66C2` and white). The test of the color values skips
  `templates/partials/icons/`.
- **No color value outside the file of the color tokens, `web/_tokens.scss`.** The rest of the
  CSS uses only the roles (`var(--accent)`), never a code such as `#0B6878`. A test
  (`ColorTokensTest`) fails if a color value appears anywhere else in `web/` or `templates/`, so
  that the CI enforces it on every push.
- Changing the palette means changing the values of this file only, in both themes, after
  checking their contrasts. Then, by hand, the elements that do not read the CSS:
  - the diagrams of `docs/diagrams/`;
  - the social preview images of the projects;
  - the favicon;
  - the `theme-color` meta tag.

### Alternatives

Six other palettes, already checked against the same thresholds. Any of them can replace Leman
by changing the token values, then checking again the colors that are not part of the palette
(halo, yellow, console). For each of them, `--accent-fill` takes the light accent in both
themes, `--on-accent-fill` is white, and `--accent-edge` takes the dark accent in both themes.

| Palette | White on fill | Fill against light page | Edge against dark page |
|---|---|---|---|
| Swiss red | 5.9 | 5.1 | 6.1 |
| Fir | 6.5 | 5.5 | 9.0 |
| Blue | 7.2 | 6.2 | 8.2 |
| Signal | 5.7 | 5.0 | 8.2 |
| Plum | 8.5 | 7.4 | 7.7 |
| Graphite | 8.9 | 7.3 | 6.9 |

#### Swiss red (warm neutrals, Swiss red accent)

| Token | Light | Dark |
|---|---|---|
| `--paper` | `#FAFAF7` | `#121212` |
| `--surface` | `#F0EFEA` | `#1C1C1B` |
| `--ink` | `#141414` | `#EDEDE8` |
| `--muted` | `#55534E` | `#B5B3AC` |
| `--line` | `#8A8780` | `#77746D` |
| `--accent` | `#C8102E` | `#FF6B6B` |

Lowest contrast, light / dark: text 6.7 / 8.1; accent 5.1 / 6.1; borders 3.1 / 3.7.

#### Fir (warm neutrals, deep green accent)

| Token | Light | Dark |
|---|---|---|
| `--paper` | `#F8F7F2` | `#111412` |
| `--surface` | `#EEECE4` | `#1A1E1B` |
| `--ink` | `#161A17` | `#E8ECE7` |
| `--muted` | `#545750` | `#AEB4AD` |
| `--line` | `#7F827A` | `#727A73` |
| `--accent` | `#1D6B47` | `#5FD39A` |

Lowest contrast, light / dark: text 6.2 / 8.0; accent 5.5 / 9.0; borders 3.3 / 3.8.

#### Blue (warm neutrals, blue accent)

| Token | Light | Dark |
|---|---|---|
| `--paper` | `#FAFAF7` | `#121212` |
| `--surface` | `#F0EFEA` | `#1C1C1B` |
| `--ink` | `#141414` | `#EDEDE8` |
| `--muted` | `#55534E` | `#B5B3AC` |
| `--line` | `#8A8780` | `#77746D` |
| `--accent` | `#1F4FBF` | `#8DB4FF` |

Lowest contrast, light / dark: text 6.7 / 8.1; accent 6.2 / 8.2; borders 3.1 / 3.7.

#### Signal (warm neutrals, orange of Swiss posters)

| Token | Light | Dark |
|---|---|---|
| `--paper` | `#FBFAF8` | `#131211` |
| `--surface` | `#F1EFEB` | `#1D1B19` |
| `--ink` | `#121212` | `#EFECE8` |
| `--muted` | `#56524D` | `#B8B2AA` |
| `--line` | `#8B8680` | `#7A746D` |
| `--accent` | `#B4400A` | `#FF9A52` |

Lowest contrast, light / dark: text 6.7 / 8.2; accent 5.0 / 8.2; borders 3.1 / 3.7.

#### Plum (grey neutrals, dark violet accent)

| Token | Light | Dark |
|---|---|---|
| `--paper` | `#FAF9FA` | `#131215` |
| `--surface` | `#F0EEF1` | `#1C1A1F` |
| `--ink` | `#17141A` | `#ECE9EF` |
| `--muted` | `#56515B` | `#B4AEBA` |
| `--line` | `#8A8590` | `#77717E` |
| `--accent` | `#6A2C9E` | `#C59BF0` |

Lowest contrast, light / dark: text 6.7 / 8.0; accent 7.4 / 7.7; borders 3.1 / 3.7.

#### Graphite (near-monochrome, burgundy accent)

| Token | Light | Dark |
|---|---|---|
| `--paper` | `#F5F5F4` | `#161616` |
| `--surface` | `#E9E9E7` | `#202020` |
| `--ink` | `#1C1C1C` | `#E6E6E4` |
| `--muted` | `#4F4F4D` | `#ABABA8` |
| `--line` | `#7E7E7B` | `#727270` |
| `--accent` | `#8C1D40` | `#F08AA8` |

Lowest contrast, light / dark: text 6.8 / 7.1; accent 7.3 / 6.9; borders 3.3 / 3.4.

## Kept for later

Two alternative versions of the site, not planned, that a button on the CV could open one day.
Their mockups already use the fonts and the color tokens of the site, and both would follow its
accessibility rules:

- **The pull request** (`docs/mockups/idea-pull-request.html`): the CV written as a merge
  request addressed to the recruiting team, with its description, its changes and its checks.
  It takes the idea of a code review, without copying the look of any product.
- **The commit game** (`docs/mockups/idea-commit-game.html`, a still picture, not playable): a
  commit to move through the steps of the pipeline (commit, tests, build, deploy), each step
  revealing a part of the CV, with a direct link to the CV for those who do not want to play.

## Mockups

`docs/mockups/` holds the mockups, as self-contained HTML pages: open them in a browser. They
embed the two fonts, so the license of the fonts (`OFL.txt`) is next to them. They show the rules
of this file applied, with placeholder images and example data; they are not the code of the
site.
