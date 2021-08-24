

const mrPurple1 = document.getElementById("mr_purple1");
const joinOthersBlockObserver = new IntersectionObserver(items => {
    items.forEach(item => {
        if (item.isIntersecting) {
            mrPurple1.classList.add("slide-right--40");
            return;
        }
        mrPurple1.classList.remove("slide-right--40");
    })
}, { threshold: 1.0 });

const joinOthersBlock = document.querySelector(".join-others");
joinOthersBlockObserver.observe(joinOthersBlock);


const mrPurple2 = document.getElementById("mr_purple2");
const mrPink2 = document.getElementById("mr_pink2");
const inviteToYouBlockObserver = new IntersectionObserver(items => {
    items.forEach(item => {
        if (item.isIntersecting) {
            mrPurple2.classList.add("slide-right--40");
            mrPink2.classList.add("slide-left--30");
            return;
        }
        mrPurple2.classList.remove("slide-right--40");
        mrPink2.classList.remove("slide-left--30");
    })
}, { threshold: 1.0 });

const inviteToYouBlock = document.querySelector(".invite-to-you");
inviteToYouBlockObserver.observe(inviteToYouBlock);