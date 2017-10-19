window.addEventListener("load", function () {
    // Chama tela de confirmção antes de deletar registro.
    [... document.querySelectorAll("form.delete-action")].forEach(form => {
        form.querySelector("button.send").addEventListener('click', (event) => {
            event.preventDefault();
            if( confirm(form.attributes["confirm"].value) ) {
                form.submit();
            }
        });
    });
    
    // Esconde notificações quando se clica no X.
    [... document.querySelectorAll(".notification")].forEach( note => {
        note.querySelector(".delete").addEventListener('click', () => {
            note.classList.add("is-hidden");
        });
    });
    
});