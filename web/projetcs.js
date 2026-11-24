// web/projects.js
// danielgarcia.it — Opens the dialog of a featured project
// ─────────────────────────────────────────────────────────────────────────────
// The dialog and all its content are already in the HTML: this script only
// opens it, from the Open button or from a click anywhere on the card, except
// on a link or a button. The browser closes it with Escape or the Close button.

for (const button of document.querySelectorAll("button[aria-controls^='details-']")) {
  const dialog = document.getElementById(button.getAttribute("aria-controls"));
  const card = button.closest("article");

  button.addEventListener("click", () => dialog.showModal());

  card.addEventListener("click", (event) => {
    if (event.target.closest("a, button, dialog")) {
      return;
    }
    dialog.showModal();
  });
}
