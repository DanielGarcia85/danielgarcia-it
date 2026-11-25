// web/header.js
// danielgarcia.it — Shows the compact profile of the header once the links
// of the large profile have left the screen (home page only).

const compactProfile = document.getElementById("compact-profile");
const profileLinks = document.querySelector("#profile > ul");

if (compactProfile && profileLinks) {
  new IntersectionObserver(([entry]) => {
    compactProfile.hidden = entry.isIntersecting;
  }).observe(profileLinks);
}
