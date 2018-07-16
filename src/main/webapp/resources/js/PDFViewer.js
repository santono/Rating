function getPDF(wantedURL,ifrm) {
    var blob;
    var xhr = new XMLHttpRequest();
    xhr.open("GET", wantedURL);
    xhr.responseType = "blob";//force the HTTP response, response-type header to be blob
    xhr.onload = function() {
        var type=xhr.responseType;
        var pdfBLOB=xhr.response;
        var blob_url=URL.createObjectURL(pdfBLOB);
//         document.getElementsByTagName("body")[0].innerHTML = xhr.response;//xhr.response is now a blob object
//         var blob_iframe = document.querySelector('#blob-src-test');

        ifrm.src = blob_url;
    }
    xhr.send();
}
function viewPDF (url) {
//    var url="http://127.0.0.1:8080/r/util/ntrdoc/doc/"+id;
    var link  = encodeURI(url);
    var ifrm  = document.createElement('iframe');
//    var width = 800;
//    var left  = 100;
    var width = screen.availWidth*0.9;
    var left  = screen.availWidth*0.02;
    var height  = screen.availHeight*0.95;
    var top  = screen.availHeight*0.02;
    getPDF(url,ifrm);
//    ifrm.setAttribute('style', 'z-index: 9999;width: '+width+'px; height: 500px; position: fixed; top: 100px; left: '+left+'px;');
    ifrm.setAttribute('style', 'z-index: 9999;width: '+width+'px; height: '+height+'px; position: fixed; top: '+top+'px; left: '+left+'px;');
    var close = document.createElement('button');
    close.setAttribute('type', 'button');
    close.innerHTML = 'Закрыть';
    close.onclick   = function() {
        document.body.removeChild(ifrm);
        document.body.removeChild(close);
    }
    close.setAttribute('style', 'z-index: 9999;position: fixed; top: '+top+'px; left: '+  (left+width+20) +'px;');
    document.body.appendChild(ifrm);
    document.body.appendChild(close);
}
