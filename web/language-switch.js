// web/language-switch.js
// danielgarcia.it — Keeps the place in the page when the language changes.
// The other language is another page, which would open at the top. Before
// leaving, this script notes the last element with an id that has passed
// under the header (a section, a part of the career, a project) and how far
// below its top the reader was; the same script on the new page scrolls back
// to the same element, which has the same id in both languages. The texts do
// not have the same length in both languages, so the place is the same
// element, not the same pixel. Without JavaScript or session storage, the new
// page simply opens at the top.

const KEY = "language-switch-place";
const header = document.querySelector("header");

// Elements that can mark the place: visible, with an id, outside the closed large cards
const markers = () =>
  [...document.querySelectorAll("main [id]")].filter(
    (element) => !element.closest("dialog") && element.getClientRects().length > 0
  );

const top = () => (header ? header.getBoundingClientRect().bottom : 0);

for (const link of document.querySelectorAll(".language-switch a:not([aria-current])")) {
  link.addEventListener("click", () => {
    const line = top();
    let place = null;
    for (const element of markers()) {
      if (element.getBoundingClientRect().top <= line + 1) {
        place = element;
      }
    }
    if (!place) {
      return;
    }
    try {
      sessionStorage.setItem(KEY, JSON.stringify({
        id: place.id,
        offset: line - place.getBoundingClientRect().top
      }));
    } catch {
      // Storage blocked: the new page opens at the top
    }
  });
}

let saved = null;
try {
  saved = JSON.parse(sessionStorage.getItem(KEY));
  sessionStorage.removeItem(KEY);
} catch {
  // Storage blocked: stay at the top
}

const place = saved && document.getElementById(saved.id);
if (place) {
  const scrollBack = () => window.scrollTo({
    top: place.getBoundingClientRect().top + window.scrollY - top() + saved.offset,
    behavior: "instant"
  });
  scrollBack();
  // The fonts arrive after the first layout and change the height of the text above: once more when they are there
  if (document.readyState === "complete") {
    document.fonts.ready.then(scrollBack);
  } else {
    window.addEventListener("load", () => document.fonts.ready.then(scrollBack), { once: true });
  }
}
