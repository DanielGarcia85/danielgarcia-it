// web/menu.js
// danielgarcia.it — Marks the menu entry of the section being read with
// aria-current="location" (home page only). An entry that was clicked stays
// marked as long as its section is on screen, even a short one. Only the
// entries of the first level: Career stays marked in all three of its parts.

const sections = new Map();
for (const link of document.querySelectorAll("#menu > ul > li > a")) {
  const address = new URL(link.href);
  const section = document.getElementById(address.hash.slice(1));
  if (address.pathname === location.pathname && section) {
    sections.set(section, link);
  }
}

let clicked = document.getElementById(location.hash.slice(1));
if (!sections.has(clicked)) {
  clicked = null;
}

function markCurrentSection() {
  let current = null;
  for (const section of sections.keys()) {
    if (section.getBoundingClientRect().top <= window.innerHeight * 0.3) {
      current = section;
    }
  }
  if (clicked) {
    const box = clicked.getBoundingClientRect();
    if (box.bottom > 0 && box.top < window.innerHeight) {
      current = clicked;
    } else {
      clicked = null;
    }
  }
  for (const [section, link] of sections) {
    if (section === current) {
      link.setAttribute("aria-current", "location");
    } else {
      link.removeAttribute("aria-current");
    }
  }
}

for (const [section, link] of sections) {
  link.addEventListener("click", () => {
    clicked = section;
    requestAnimationFrame(markCurrentSection);
  });
}

if (sections.size > 0) {
  window.addEventListener("scroll", markCurrentSection, { passive: true });
  window.addEventListener("load", markCurrentSection);
}
