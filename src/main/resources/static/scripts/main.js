const docBody = document.body;
window.addEventListener("load", e => {
    docBody.classList.remove("prevent-animation");
});


const collapsibles = document.querySelectorAll(".collapsible");
collapsibles.forEach(item => {
    item.addEventListener("click", e => {
        item.classList.toggle("collapsible--expanded");
    })
});


const sideMenuTogglers = document.querySelectorAll(".toggler");
const sideMenu = document.querySelector(".side-menu");
sideMenuTogglers.forEach(toggler => {
    toggler.addEventListener("click", e => {
        sideMenu.classList.toggle("collapsible-side-menu--expanded");
        docBody.classList.toggle("inactive");
    });
});