// web/dialogs.js
// danielgarcia.it — Opens the dialog of a card: a project or a career entry
// ─────────────────────────────────────────────────────────────────────────────
// The dialog and all its content are already in the HTML: this script only
// opens it, from the Open button or from a click anywhere on the card, except
// on a link or a button. The browser closes it with Escape or the cross; a
// click on the dimmed page around it closes it too. It also tells the CSS
// where the facts column must stop: once its last button is in view, at the
// bottom of the card, or under the header if the whole column fits there.

// Where the facts column stops, measured in the dialog, which is what scrolls
const placeFacts = (dialog) => {
  const head = dialog.querySelector(".large-card-head");
  const aside = dialog.querySelector(".large-card-body aside");
  if (!head || !aside) {
    return;
  }
  const underHead = head.offsetHeight + 16;
  const lastInView = dialog.clientHeight - aside.offsetHeight - 16;
  dialog.style.setProperty("--aside-top", `${Math.min(underHead, lastInView)}px`);
};

// The visible height changes with the window
window.addEventListener("resize", () => {
  const dialog = document.querySelector("dialog[open]");
  if (dialog) {
    placeFacts(dialog);
  }
});

for (const button of document.querySelectorAll("button[aria-controls^='details-']")) {
  const dialog = document.getElementById(button.getAttribute("aria-controls"));
  const card = button.closest("article");

  const open = () => {
    dialog.showModal();
    placeFacts(dialog);
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
