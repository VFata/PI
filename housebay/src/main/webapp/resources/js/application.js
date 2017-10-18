window.addEventListener("load", function () {
    [... document.querySelectorAll("form.delete-action")].forEach(form => {
        form.querySelector("button.send").addEventListener('click', (event) => {
            event.preventDefault();
            if( confirm(form.attributes["confirm"].value) ) {
                form.submit();
            }
        });
    });
});