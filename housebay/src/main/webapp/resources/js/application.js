window.addEventListener("load", function () {
   [... document.querySelectorAll("a[data-method]")].forEach(el => { 
	el.addEventListener('click', function() { 
            if(confirm('Are you sure?')) {
                let method = el.attributes["data-method"].value;
                let url = el.attributes["data-href"].value;
                
                let oReq = new XMLHttpRequest();
                oReq.onload = function () {
                    el.parentNode.parentNode.outerHTML = "";
                };
                oReq.open(method, url, true);
                oReq.send();
            }
 	}); 
    });
    
    let jsform = document.querySelector("form[data-method]");
    if (jsform) { 
        let jsbtn = jsform.querySelector("button[type=button].send");
        if (jsbtn) {
            jsbtn.addEventListener('click', function () {
                var formData = new FormData(jsform);
                var request = new XMLHttpRequest();
                request.onreadystatechange = function() {
                    if(request.readyState === XMLHttpRequest.DONE && request.status === 200) {
                        if (request.responseType === "json") {
                            let response = JSON.parse(request.responseText);
                            if (response.redirect) {
                                window.location.replace(response.redirect);
                            }
                        }
                    }
                };      
                request.open(jsform.attributes["data-method"].value, 
                        jsform.attributes["data-action"].value);
                request.send(formData);
            });
        }
    }
});