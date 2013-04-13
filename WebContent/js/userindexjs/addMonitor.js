var showWebPageUrl = "showWebPage?url=";
function addMonitor(){
    var url = document.getElementById("url").value;
    if(url.length<4){
    	alert("请输入正确的网址！");
    	return;
    }
    url = getLegalURL(url);
    window.open(showWebPageUrl + url, "_blank");
    return false;
}

function getLegalURL(url){
    if (url.indexOf("http://") != 0) {
        url = "http://" + url;
    }
    return url;
}
