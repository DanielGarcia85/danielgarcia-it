// public/theme.js
// danielgarcia.it — Light or dark theme, and the button of the header that
// switches it. Loaded in <head>, before the page is drawn, so that a theme
// chosen earlier applies at once, without a flash of the other one; this is
// why it is not in web/, whose bundle only runs once the page is loaded.
// Without a choice, the CSS follows the theme of the system. The choice is
// kept in the local storage of the browser (see the privacy page) and never
// leaves it. Without JavaScript, the button stays hidden.

(() => {
  const root = document.documentElement;

  // Tells the CSS that JavaScript runs, before the page is drawn: the "Menu"
  // button of small screens only exists with it (web/_header.scss)
  root.classList.add("js");

  const systemDark = matchMedia("(prefers-color-scheme: dark)");

  let choice = null;
  try {
    choice = localStorage.getItem("theme");
  } catch {
    // Storage blocked by the browser: the theme of the system applies
  }
  if (choice === "light" || choice === "dark") {
    root.dataset.theme = choice;
  }

  const isDark = () => (root.dataset.theme ? root.dataset.theme === "dark" : systemDark.matches);

  document.addEventListener("DOMContentLoaded", () => {
    const button = document.getElementById("theme-switch");
    if (!button) {
      return;
    }

    // The button names the theme it switches to: "Dark" in the light theme
    const update = () => {
      const target = isDark() ? "light" : "dark";
      button.textContent = button.dataset[target];
      button.setAttribute("aria-label", button.dataset[target + "Label"]);
    };

    button.addEventListener("click", () => {
      root.dataset.theme = isDark() ? "light" : "dark";
      try {
        localStorage.setItem("theme", root.dataset.theme);
      } catch {
        // Storage blocked: the choice lasts until the page changes
      }
      update();
    });
    systemDark.addEventListener("change", update);

    update();
    button.hidden = false;
  });
})();
