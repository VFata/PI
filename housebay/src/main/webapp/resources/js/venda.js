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
    });
    
    document.querySelector("#search-cliente").addEventListener('click', (evt) => {
        evt.preventDefault();
        
        getClientes(document.querySelector("input[name=cliente-q]").value);
    });
    
    document.querySelector("#search-produto").addEventListener('click', (evt) => {
        evt.preventDefault();
                
        getProdutos(document.querySelector("input[name=produto-q]").value);
    });
    
});

function adicionaVendavel (el) {
    el.addEventListener('click', (evt) => {
        evt.preventDefault();
        
        let carrinho = document.querySelector("#carrinho");
        
        let id = el.getAttribute("produto-id");
        let nome = el.getAttribute("produto-nome");
        let valor = el.getAttribute("produto-valor");
        let estoque = el.getAttribute("produto-estoque");
        
        if (document.querySelector(`#relacao_linha_${id}`)) {
            alert("Produto já inserido!");
        } else {
            let gambi = `<td>${nome}</td>
                <td>${formatDinheiro(valor)}</td>
                <td>
                    <div class="field">
                        <div class="control">
                            <input class="input" type="number" name="relacao_qtd_${id}" placeholder="Quantidade" value="1" min="1" max="${estoque}" >
                            <input class="input" type="hidden" value="${valor}">
                        </div>
                    </div>
                </td>
                <td id="relacao_total_${id}">${formatDinheiro(valor)}</td>`;
            
            let tr = document.createElement('tr');
            tr.id = `relacao_linha_${id}`; 
            tr.insertAdjacentHTML('beforeend', gambi);
            
            let input = document.createElement('input');
            input.classList.add("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("name", `relacao_id_${id}`);
            input.setAttribute("value", id);
            
            let del = document.createElement('a');
            del.classList.add('delete');
            del.addEventListener('click', (evt) => {
                evt.preventDefault();
                
               tr.parentNode.removeChild(tr);
            });
            
            
            let deleteTd = document.createElement('td');
            deleteTd.appendChild(input);
            deleteTd.appendChild(del);
            
            tr.appendChild(deleteTd);
            carrinho.appendChild(tr);
        }
    });
}

function selecionaCliente(el) {
    el.addEventListener('click', (evt) => {
        evt.preventDefault();

        document.querySelector("#selected-cliente").textContent = el.getAttribute("cliente-nome"); 
        document.querySelector("input[name=cliente]").value = el.getAttribute("cliente-id");
    });
}

function getClientes(query) {
    let xhr = new XMLHttpRequest();
    let url = "/housebay/vendasJson/cliente";
    let params = `q=${query}`;
    xhr.open("POST", url, true);

    //Send the proper header information along with the request
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {//Call a function when the state changes.
        if(xhr.readyState === 4 && xhr.status === 200) {
            let tbody = document.querySelector('#cliente-table tbody');
            tbody.innerHTML = '';
            let clientes = JSON.parse(xhr.responseText);

            clientes.forEach( cli => {
                let td1 = document.createElement('td');
                td1.textContent = cli.nome;

                let td2 = document.createElement('td');
                td2.textContent = cli.telefone;

                let td3 = document.createElement('td');
                td3.textContent = cli.email;

                let a = document.createElement('a');
                a.classList.add("button", "is-success", "is-outlined", "get-cliente");
                a.setAttribute("cliente-id", cli.id);
                a.setAttribute("cliente-nome", cli.nome);
                selecionaCliente(a);
                //a.appendChild(i);
                a.textContent = "Selecionar";
                a.insertAdjacentHTML('afterbegin', '<i class="fa fa-check" aria-hidden="true"></i>&nbsp;');

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
    xhr.send(params);
}

function getProdutos(query) {
    let xhr = new XMLHttpRequest();
    let url = "/housebay/vendasJson/produto";
    let params = `q=${query}`;
    xhr.open("POST", url, true);

    //Send the proper header information along with the request
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {//Call a function when the state changes.
        if(xhr.readyState === 4 && xhr.status === 200) {
            let tbody = document.querySelector('#produto-table tbody');
            tbody.innerHTML = '';
            let produtos = JSON.parse(xhr.responseText);

            produtos.forEach( prod => {
                let td1 = document.createElement('td');
                td1.textContent = prod.nome;

                let td2 = document.createElement('td');
                td2.textContent = prod.formatValor;

                let td3 = document.createElement('td');
                td3.textContent = prod.estoque;

                let a = document.createElement('a');
                a.classList.add("button", "is-success", "is-outlined", "get-cliente");
                a.setAttribute("produto-id", prod.id);
                a.setAttribute("produto-nome", prod.nome);
                a.setAttribute("produto-valor", prod.valor);
                a.setAttribute("produto-estoque", prod.estoque);
                adicionaVendavel(a);
                a.textContent = "Selecionar";
                a.insertAdjacentHTML('afterbegin', '<i class="fa fa-check" aria-hidden="true"></i>&nbsp;');

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
    xhr.send(params);
}

function formatDinheiro(n) {   
    return Number(n).toLocaleString('pt-br', {
        style: 'currency',
        currency: 'BRL'
    });
}