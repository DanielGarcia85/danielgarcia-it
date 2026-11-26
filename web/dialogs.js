// web/dialogs.js
// danielgarcia.it — Opens the dialog of a card: a project or a career entry
// ─────────────────────────────────────────────────────────────────────────────
// The dialog and all its content are already in the HTML: this script only
// opens it, from the Open button or from a click anywhere on the card, except
// on a link or a button. The browser closes it with Escape, the cross or the
// Close button; a click on the dimmed page around it closes it too.

for (const button of document.querySelectorAll("button[aria-controls^='details-']")) {
  const dialog = document.getElementById(button.getAttribute("aria-controls"));
  const card = button.closest("article");

  // The facts column stays in view under the header: it needs the height of the header
  const open = () => {
    dialog.showModal();
    const head = dialog.querySelector(".large-card-head");
    if (head) {
      dialog.style.setProperty("--head-height", `${head.offsetHeight}px`);
    }
  };

  button.addEventListener("click", open);

  // A click outside the box lands on the dialog element itself (its backdrop)
  dialog.addEventListener("click", (event) => {
    if (event.target === dialog) {
      dialog.close();
    }
  });

  card.addEventListener("click", (event) => {
    if (event.target.closest("a, button, dialog")) {
      return;
    }
    open();
  });
}
