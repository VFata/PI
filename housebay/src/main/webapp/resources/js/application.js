window.addEventListener("load", () => {
    // Chama tela de confirmção antes de deletar registro.
    [... document.querySelectorAll("form.delete-action")].forEach(form => {
        form.querySelector("button.send").addEventListener('click', event => {
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
    
    // Remove blocos de uma 'lista de blocos' 
    // Usado em forms com relacionamento 1-para-muitos
    [... document.querySelectorAll(".list-block")].forEach( blk => {
        blk.querySelector(".delete").addEventListener('click', () => {
            //blk.classList.add("is-hidden");
            blk.parentNode.removeChild(blk);
        });
    });
    
    
});