window.addEventListener("DOMContentLoaded", function () {
    [... document.querySelectorAll(".droppanel")].forEach( dp => {
        [... dp.querySelectorAll(".droppanel-trigger button, .droppanel-trigger a")].forEach( trigger => {
            trigger.addEventListener('click', (evt) => {
                evt.preventDefault();
                
                dp.classList.toggle('is-active');
           });
        });
    });
    
    [... document.querySelectorAll(".get-cliente")].forEach( a => {
        selecionaCliente(a);
        /*
        a.addEventListener('click', (evt) => {
            evt.preventDefault();
            
            document.querySelector("#selected-cliente").textContent = a.getAttribute("cliente-nome"); 
            document.querySelector("input[name=cliente]").value = a.getAttribute("cliente-id");
        });
        */
    });
    
    document.querySelector("#search-cliente").addEventListener('click', (evt) => {
        evt.preventDefault();
        
        getClientes(document.querySelector("input[name=cliente-q]").value);
    });
    
    
});



function selecionaCliente(el) {
    el.addEventListener('click', (evt) => {
        evt.preventDefault();

        document.querySelector("#selected-cliente").textContent = el.getAttribute("cliente-nome"); 
        document.querySelector("input[name=cliente]").value = el.getAttribute("cliente-id");
    });
}

function getClientes(query) {
    let http = new XMLHttpRequest();
    let url = "/housebay/vendasJson/cliente";
    let params = `q=${query}`;
    http.open("POST", url, true);

    //Send the proper header information along with the request
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    http.onreadystatechange = function() {//Call a function when the state changes.
        if(http.readyState === 4 && http.status === 200) {
            let tbody = document.querySelector('#cliente-table tbody');
            tbody.innerHTML = '';
            let clientes = JSON.parse(http.responseText);

            clientes.forEach( cli => {
                let td1 = document.createElement('td');
                td1.textContent = cli.nome;

                let td2 = document.createElement('td');
                td2.textContent = cli.telefone;

                let td3 = document.createElement('td');
                td3.textContent = cli.email;

                let i = document.createElement('i');
                i.classList.add("fa", "fa-check");

                let a = document.createElement('a');
                a.classList.add("button", "is-success", "is-outlined", "get-cliente");
                a.setAttribute("cliente-id", cli.id);
                a.setAttribute("cliente-nome", cli.nome);
                selecionaCliente(a);
                a.appendChild(i);
                a.textContent = "Selecionar";

                let td4 = document.createElement('td');
                td4.appendChild(a);

                let tr = document.createElement('tr');
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);

                tbody.appendChild(tr);
            });

        }
    };    
    http.send(params);
}