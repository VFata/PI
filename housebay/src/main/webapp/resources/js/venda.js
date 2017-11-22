/* global undefine */

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
    
    document.querySelector("#search-servico").addEventListener('click', (evt) => {
        evt.preventDefault();
                
        getServicos(document.querySelector("input[name=servico-q]").value);
    });
    
    atualizaTotal();
});

function adicionaVendavel(el) {
    el.addEventListener('click', (evt) => {
        evt.preventDefault();
        
        let carrinho = document.querySelector("#carrinho tbody");
        
        let id = el.getAttribute("produto-id");
        let nome = el.getAttribute("produto-nome");
        let valor = el.getAttribute("produto-valor");
        let estoque = el.getAttribute("produto-estoque");
        
        if (document.querySelector(`#relacao_linha_${id}`)) {
            alert("Produto já inserido!");
        } else {
            /*
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
            */
           
            let nomeTd = document.createElement('td');
            nomeTd.textContent = nome;
            
            let valorTd = document.createElement('td');
            valorTd.textContent = formatDinheiro(valor);
            
            let qtdInput = document.createElement('input');
            qtdInput.classList.add('input');
            qtdInput.type = 'number';
            qtdInput.name = `relacao_qtd_${id}`;
            qtdInput.value = 1;
            qtdInput.min = 1;
            if (estoque != undefined) {
                qtdInput.max = estoque;
            }
            qtdInput.setAttribute("prod-valor", valor);
            qtdInput.addEventListener('change', ()=> {
                totalTd.textContent = (formatDinheiro(qtdInput.value * qtdInput.getAttribute("prod-valor")));
                atualizaTotal();
            });
            
            let div = document.createElement('div');
            div.classList.add('field');
            div.appendChild(qtdInput);
            
            let qtdTd = document.createElement('td');
            qtdTd.appendChild(div);
    
            let totalTd = document.createElement('td');
            totalTd.textContent = (formatDinheiro(qtdInput.value * qtdInput.getAttribute("prod-valor")));
    
            let idInput = document.createElement('input');
            idInput.classList.add("input");
            idInput.setAttribute("type", "hidden");
            idInput.setAttribute("name", `relacao_id_${id}`);
            idInput.setAttribute("value", id);
            
            let del = document.createElement('a');
            del.classList.add('delete');
            del.addEventListener('click', (evt) => {
                evt.preventDefault();
                tr.parentNode.removeChild(tr);
                atualizaTotal();
            });
            
            let deleteTd = document.createElement('td');
            deleteTd.appendChild(idInput);
            deleteTd.appendChild(del);
            
            let tr = document.createElement('tr');
            tr.id = `relacao_linha_${id}`; 
            tr.appendChild(nomeTd);
            tr.appendChild(valorTd);
            tr.appendChild(qtdTd);
            tr.appendChild(totalTd);
            tr.appendChild(deleteTd);
            carrinho.appendChild(tr);
        }
        atualizaTotal();
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
    let url = "/housebay/vendasJson/clientes";
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
    let url = "/housebay/vendasJson/produtos";
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

function getServicos(query) {
    let xhr = new XMLHttpRequest();
    let url = "/housebay/vendasJson/servicos";
    let params = `q=${query}`;
    xhr.open("POST", url, true);

    //Send the proper header information along with the request
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if(xhr.readyState === 4 && xhr.status === 200) {
            let tbody = document.querySelector('#servico-table tbody');
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



function atualizaTotal() {
    let carrinho = document.querySelector("#carrinho tbody");
    let total = [... carrinho.children]
            .map(e => { 
                let q = e.querySelector("input[prod-valor]"); 
                return q.value * q.getAttribute("prod-valor"); 
            })
            .reduce((s,e) => s+e, 0);

    document.querySelector("#valorTotal").textContent = formatDinheiro(total);
}

function formatDinheiro(n) {   
    return Number(n).toLocaleString('pt-br', {
        style: 'currency',
        currency: 'BRL'
    });
}