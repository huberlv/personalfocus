var olpfBase = "http://localhost:8082";
//document.firstChild.nodeValue="doctype html";
var thispaths=null;
/**
 * 标记已关注的区域
 */
var thispaths=null;
var exeitsPaths=null;
function markModules(border,v_exeitsPaths){
	if(arguments.length>1){
		exeitsPaths=v_exeitsPaths;
	}
	
	if(exeitsPaths!=null){
		if(exeitsPaths==''){
			return;
		}
	}else{
		return;
	}
	try {
		if (thispaths == null) {
			if (arguments.length > 0) {
				thispaths = exeitsPaths;
			}
			else {		
				return;
			}
		}
		
		if ($olpfjQuery(exeitsPaths).length==0) {
			
			return;
		}
		var div;
		var tag;	
		var paths=exeitsPaths.split(",");
		for(var h=0;h<paths.length;h++){			
		    $olpfjQuery(paths[h]).each(function(i){
		    	$olpfjQuery(this).css({
		    		border:border
		    	}
		    			);
		    });
	}
	}catch (e) {
		myAlert(e,"错误代码：001");
	}
}




function markUpdate(pathstr){
	var paths=pathstr.split(",");
    for(var i=0;i<paths.length;i++){
		try {
            
			if (paths[i].indexOf("font") < 0) {
				
				jQuery(paths[i]).addClass("updateStyle");
			}
			else {
				var dom;
				var html;
				var arr;
				var reg = new RegExp(">([^<^\\s]+)<[^/]{1}", "g");
				var temp=paths[i].substring(0,paths[i].lastIndexOf(">"));
				while (true) {
					 dom = jQuery(temp);
					if(dom==null)
					   break;
				    if(dom.html()==null){
					   	break;
					   }
				    html = "<html>"+dom.html()+"</html>";				
					 arr = reg.exec(html);
					
					if (arr != null) {
						html=html.replace(arr[1], "<font class=\"updateStyle\">" + arr[1] + "</font>");
						dom.empty();
						dom.append(html.substring(6,html.length-7));
					
					}else{
						break;
					}
				}	
			   
				//alert(dom.html());
			}
		}catch(e){
			myAlert(e,"错误代码：002");
		}
	}
	//jQuery(""+this.updatePaths+"").addClass("updateStyle");
}

function removeUpdate(){	
			jQuery(".updateStyle").each(function(i){
				jQuery(this).removeClass("updateStyle");
			}
			);		
}
