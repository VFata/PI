var contadorPet = -1;


window.addEventListener("DOMContentLoaded", function () {
    
    
    document.querySelector("#add-form-pets").addEventListener('click', event => {
        event.preventDefault();
        
        contadorPet++;
        let petForm = `<input type="hidden" name="pet_id_novo_${contadorPet}" value="-1">
                    <div class="field is-horizontal">
                        <div class="field-label is-normal">
                            <label class="label">Nome</label>
                        </div>
                        <div class="field-body">
                            <div class="field">
                                <div class="control">
                                    <input class="input" type="text" name="pet_nome_novo_${contadorPet}" placeholder="Nome">
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="field is-horizontal">
                        <div class="field-label is-normal">
                            <label class="label">Descrição</label>
                        </div>
                        <div class="field-body">
                            <div class="field">
                                <div class="control">
                                    <textarea class="textarea" name="pet_descricao_novo_${contadorPet}" placeholder="Descrição"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>`;
        
        let listParent = document.querySelector("#list-form-pets");
        let div = document.createElement('div');
        div.classList.add('list-block');
        div.innerHTML = petForm;
        let del = document.createElement('a');
        del.classList.add('delete');
        div.insertAdjacentElement('afterbegin' ,del);
        listParent.appendChild(div);
        
        div.querySelector(".delete").addEventListener('click', () => {
            div.parentNode.removeChild(div);
        });        
    });
});

