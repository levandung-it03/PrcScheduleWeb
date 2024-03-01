(function toggleHideCategory() {
    const menuBtn = document.querySelector("div#header_left i");
    const category = document.querySelector("div#category");
    const centerPage = document.querySelector("div#center-page");
    menuBtn.addEventListener("click", (e) => {
        if (category.classList.contains("hide")) {
            category.classList.remove("hide");
            centerPage.style.width = "calc(100vw - var(--category-width))";
        } else {
            category.classList.add("hide");
            centerPage.style.width = "100vw";
        }
    })
})();