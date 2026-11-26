// web/external-links.js
// danielgarcia.it — Opens in a new tab the links to other sites written in
// Markdown in the data (the HEG links of the profile, for example), like the
// links of the templates. Markdown cannot set target="_blank", hence this
// script; without JavaScript, these links simply open in the same tab.
// Download links (with the download attribute) are left alone. The notice for
// screen readers is the one of the footer, already in the right language
// (templates/partials/new-tab.html).

const notice = document.querySelector("footer .visually-hidden");

for (const link of document.querySelectorAll("a[href^='http']:not([target]):not([download])")) {
  if (link.host === location.host) {
    continue;
  }
  link.target = "_blank";
  link.rel = "noopener";
  if (notice) {
    link.append(notice.cloneNode(true));
  }
}
