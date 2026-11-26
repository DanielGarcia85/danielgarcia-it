// web/header.js
// danielgarcia.it — Shows the compact profile of the header once the links
// of the large profile have left the screen (home page only). The header
// covers the top of the screen, so a link under it counts as gone.

const header = document.querySelector("header");
const compactProfile = document.getElementById("compact-profile");
const profileLinks = document.querySelector("#profile > ul");

if (header && compactProfile && profileLinks) {
  new IntersectionObserver(([entry]) => {
    compactProfile.hidden = entry.isIntersecting;
  }, { rootMargin: `-${header.offsetHeight}px 0px 0px 0px` }).observe(profileLinks);
}
