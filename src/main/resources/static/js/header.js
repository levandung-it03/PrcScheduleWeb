(function toggleHideCategory() {
    document.querySelector("div#header_left i").addEventListener("click", (e) =>
        document.querySelector("div#category").classList.toggle("hide")
    );
})();