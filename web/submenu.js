// web/submenu.js
// danielgarcia.it — The submenu of Career in the header: a click on Career
// opens its three parts (Professional experience, Education, Certifications)
// instead of going to the section. Its state lives in aria-expanded on the
// Career link, which the CSS reads. It closes after a choice, with Escape (the
// focus goes back to Career), with a click outside, or when the focus leaves
// it. Without JavaScript, Career is a plain link to the section.

for (const submenu of document.querySelectorAll("#menu .submenu")) {
  const toggle = submenu.querySelector(":scope > a");
  const setOpen = (open) => toggle.setAttribute("aria-expanded", String(open));
  const isOpen = () => toggle.getAttribute("aria-expanded") === "true";

  setOpen(false);

  toggle.addEventListener("click", (event) => {
    event.preventDefault();
    setOpen(!isOpen());
  });

  submenu.querySelector(":scope > ul").addEventListener("click", (event) => {
    if (event.target.closest("a")) {
      setOpen(false);
    }
  });

  // Capture phase: Escape closes the submenu first, before the Menu button of small screens
  document.addEventListener("keydown", (event) => {
    if (event.key === "Escape" && isOpen()) {
      event.stopPropagation();
      setOpen(false);
      toggle.focus();
    }
  }, { capture: true });

  document.addEventListener("click", (event) => {
    if (isOpen() && !submenu.contains(event.target)) {
      setOpen(false);
    }
  });

  submenu.addEventListener("focusout", (event) => {
    if (isOpen() && !submenu.contains(event.relatedTarget)) {
      setOpen(false);
    }
  });
}
