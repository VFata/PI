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
    
    
    
});