/**
 * @author ZERO
 */
function save(){
    try {
		var subgroupId = document.getElementById("subgroup").value;
        var divId = document.getElementById("divId");
        var moduleContent = divId.innerHTML = KE.html("myKE");
        divId.style.display = "block";
        var name = DWRUtil.getValue("userModuleName");
        var height = divId.clientHeight;
        var width = divId.clientWidth;
        var style = "position:static;height:" + height + ";width:" + width + ";top:3000px;left:0px;display:none;";
        customModule.saveCustomModule(name, KE.html("myKE"), style, subgroupId, function(data){
            alert(data);
            document.getElementById("divId").innerHTML = "";
            document.getElementById("divId").style.display = "none";
        });
    } 
    catch (e) {
    	myAlert(e);
    }
}

function update(){
    try {
		var subgroupId = document.getElementById("subgroup").value;
        var divId = document.getElementById("divId");
        var moduleContent = divId.innerHTML = KE.html("myKE");
        divId.style.display = "block";
        var name = DWRUtil.getValue("userModuleName");
        var height = divId.clientHeight;
        var width = divId.clientWidth;
        var style = "position:static;height:" + height + ";width:" + width + ";top:3000px;left:0px;display:none;";
        customModule.updateCustomModule(name, KE.html("myKE"), style, moduleId, userModuleId, subgroupId, function(data){
            alert(data);
            document.getElementById("divId").innerHTML = "";
            document.getElementById("divId").style.display = "none";
        });
    } 
    catch (e) {
    	myAlert(e);
    }
}

function preview(id){
    var dialog = new KE.dialog({
        id: id,
        cmd: 'preview',
        html: KE.util.getData(id),
        width: 600,
        height: 400,
        useFrameCSS: true,
        title: KE.lang['preview'],
        noButton: KE.lang['close']
    });
    dialog.show();
}