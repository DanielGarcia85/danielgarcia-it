// web/menu-button.js
// danielgarcia.it — The "Menu" button of small screens (under 910 px), which
// opens the entries of the menu as a list under the header. Its state lives in
// aria-expanded, which the CSS reads. The list closes after a choice, with
// Escape (the focus goes back to the button) or with a click outside.

const menuButton = document.getElementById("menu-button");
const menu = document.getElementById("menu");

if (menuButton && menu) {
  const setOpen = (open) => menuButton.setAttribute("aria-expanded", String(open));
  const isOpen = () => menuButton.getAttribute("aria-expanded") === "true";

  menuButton.addEventListener("click", () => setOpen(!isOpen()));

  menu.addEventListener("click", (event) => {
    if (event.target.closest("a")) {
      setOpen(false);
    }
  });

  document.addEventListener("keydown", (event) => {
    if (event.key === "Escape" && isOpen()) {
      setOpen(false);
      menuButton.focus();
    }
  });

  document.addEventListener("click", (event) => {
    if (isOpen() && !event.target.closest("#menu, #menu-button")) {
      setOpen(false);
    }
  });
}
