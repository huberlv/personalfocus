/**
 * 如果存在，取得该dom的id
 * 
 * @param dom
 * @param jquery
 * @return
 */
function getIdifExits(dom, jquery, pbody) {

	var tag = jquery(dom);
	var id = tag.attr("id");
	if (typeof pbody != 'undefined') {
		if ((id != null) && ((jquery.trim(id)) != '')
				&& (jquery(pbody).find("#" + id).length == 1)) { // id不为空或空串且唯一
			return "#" + id;
		} else {
			return null;
		}
	} else {
		if ((id != null) && ((jquery.trim(id)) != '')
				&& (jquery.find("#" + id).length == 1)) { // id不为空或空串且唯一
			return "#" + id;
		} else {
			return null;
		}
	}

}

/**
 * 如果存在，取得该dom的name
 * 
 * @param dom
 * @param jquery
 * @return
 */
function getNameifExits(dom, jquery, pbody) {

	var tag = jquery(dom);
	var name = jquery.trim(tag.attr("name"));

	if (typeof pbody != 'undefined') {
		if ((name != null)&& ((jquery.trim(name)) != '')){
			var tagss=jquery(pbody).find(tag.attr("tagName") + "[name=" + name + "]");
			if(tagss.length == 1) { // id不为空或空串且唯一
			      return tag.attr("tagName").toLowerCase() + "[name=" + name + "]";
			}else{
				var temp="";
				//var dompath=getAPath(dom, jquery).hpath;
				tagss.each(function(i){
					if(this==dom){
						
						temp= tag.attr("tagName").toLowerCase() + "[name=" + name + "]"+":eq("+i+")";
					}
				});
				if(temp!=""){
					return temp;
				}
			}
			return null;
		} else {
			return null;
		}
	} else {

		if ((name != null)
				&& ((jquery.trim(name)) != '')){

			var tagss=jquery(tag.attr("tagName") + "[name=" + name + "]");
			if(tagss.length == 1) { // id不为空或空串且唯一
			      return tag.attr("tagName").toLowerCase() + "[name=" + name + "]";
			}else{
				var temp="";
				//var dompath=getAPath(dom, jquery).hpath;
				tagss.each(function(i){
					if(this==dom){
						
						temp= tag.attr("tagName").toLowerCase() + "[name=" + name + "]"+":eq("+i+")";
					}
				});
				if(temp!=""){
					return temp;
				}
			}
			return null;
		} else {
			return null;
		}

	}
}

var defineModuleClass="defineModuleClass";
/**
 * 如果存在，取得该dom的classes
 * 
 * @param dom
 * @param jquery
 * @return
 */
function getClassifExits(dom, jquery, pbody) {
    
	var tag = jquery(dom);
	var classes = jquery.trim(tag.attr("class"));
    
	if ((classes != null) && ((jquery.trim(classes)) != '')) {
		classes = jquery.trim(classes.replace(defineModuleClass, ""));
		var classArr=classes.split(/\s+/);
		for(var h=0;h<classArr.length;h++){
			//寻找class唯一的标识
		if (typeof pbody != 'undefined') {
			if (classArr[h] != ''){
				var tagss=jquery(pbody).find("." + classArr[h]);
				if(tagss.length == 1) { // classes不为空或空串且唯一){
					   return "." + classArr[h];
			    }		
			} 
		} else {
			if (classArr[h] != ''){
				var tagss=jquery("." + classArr[h]);
				if(tagss.length == 1) { // classArr[h]不为空或空串且唯一){
				   return "." + classArr[h];
				}		
			}
		}
		}
		
		for(var h=0;h<classArr.length;h++){
			if (typeof pbody != 'undefined') {
				if (classArr[h] != ''){
					var tagss=jquery(pbody).find("." + classArr[h]);
							var temp="";
							// var dompath=getAPath(dom, jquery).hpath;
							tagss.each(function(i){
								
								if(this==dom){
									
									temp= "."+classArr[h]+":eq("+i+")";
								}
							});
							if(temp!=""){
								return temp;
							}
									
				} 
			} else {
				if (classArr[h] != ''){
					var tagss=jquery("." + classArr[h]);
						var temp="";
						tagss.each(function(i){
						
							if(this==dom){					
								temp= "."+classArr[h]+":eq("+i+")";
							}
						});
						if(temp!=""){
							return temp;
						}
										
					}		
				}
			}
	}
	return null;
}
/**
 * 如果存在，遍历族树递归取得该dom的特征，优先级为id,name,class
 * 
 * @return
 */
function getFeatureRe(dom, jquery,pbody) {
	var id = getIdifExits(dom, jquery,pbody);
	if (id != null) {
		return id;
	}	
	var name = getNameifExits(dom, jquery,pbody);
	if (name != null) {
		return name;
	}
	var classes = getClassifExits(dom, jquery,pbody);
	if (classes != null) {
		return classes;
	}
	var parent = jquery(dom).parent();
	if ((parent != null) && (parent.children().length == 1)) {// 如果父亲不为空且只有一个儿子，即本dom
		return getFeatureRe(parent[0], jquery,pbody); //
	} else {
		return null;
	}
}

/**
 * 得到一个结点的路径
 * 
 * @return
 */
function getAPath(dom, jquery) {
	try {
		var tagName; // 节点名
		var parents;
		var cParent;
		var inde = 0; // 节点在父节点和其同名儿子集合中的偏移位置
		var hpath;
		var referencePath;

		hpath = new String();
		referencePath = new String();
		cParent = dom;
		parents = jquery(dom).parents(); // 当前节点的祖父辈集合，是一个数组
		var current;
		for ( var i = 0; i < parents.size(); i++) {
			tagName = jquery(cParent).attr("tagName").toLowerCase();
			inde = jquery(parents[i]).children(tagName).index(cParent);// 节点在父节点和其同名儿子集合中的偏移位置

			current = jquery(cParent);
			if (current.attr('id') != null && current.attr('id').length > 0) {
				referencePath = "[id='" + current.attr('id') + "']"
						+ referencePath;
			}
			if (current.attr('class') != null
					&& current.attr('class').length > 0) {
				var temp = current.attr('class');
				while (temp.indexOf(defineModuleClass) >= 0) {
					temp = temp.replace(defineModuleClass, "");
				}

				if (temp.length > 0) {
					referencePath = "[class='" + temp + "']" + referencePath;
				}
			}
			if (current.attr('name') != null && current.attr('name').length > 0) {
				referencePath = "[name='" + current.attr('name') + "']"
						+ referencePath;
			}
			hpath = ":eq(" + inde + ")" + hpath;
			referencePath = ":eq(" + inde + ")" + referencePath;
			hpath = tagName + hpath; // 加入节点名
			referencePath = tagName + referencePath;
			cParent = parents[i];
			hpath = ">" + hpath;
			referencePath = ">" + referencePath;
		}
		hpath = "html" + hpath;
		referencePath = "html" + referencePath;
		return {
			hpath : hpath,
			referencePath : referencePath
		};
	} catch (e) {
		myAlert(e);
		return null;
	}
}


function getFeature(olpfElement, jquery, pbody){

	try{
	var paths = new Array();
	var referencePaths = new Array();
	var pathAndRe=null;
	for ( var j = 0; j < olpfElement.length; j++) {
		pathAndRe=getAPath(olpfElement[j],jquery);
		var afeature=getFeatureRe(olpfElement[j],jquery,pbody);
		
		if(afeature!=null){ //如果存在除路径外的其它唯一标识
			paths.push(afeature);
			if(pathAndRe!=null){
				referencePaths.push(pathAndRe.referencePath);
			}else{
			  referencePaths.push(afeature);
			}
		}else{  //否则以路径为标识		
			if(pathAndRe!=null){
				paths.push(pathAndRe.hpath);
				referencePaths.push(pathAndRe.referencePath);
			}else{
				myAlert("特征为空");
			}
		}
	}
		// 合并子特征
		for ( var i = 0; i < olpfElement.length; i++) {
			for ( var j = 0; j < olpfElement.length; j++) {
				if (paths[i].indexOf(paths[j], 0) >= 0 && j != i) {
					olpfChangeStyle(olpfElement[i], 3);
					olpfElement.splice(i, 1);
					jquery(olpfElement[i]).removeClass(defineModuleClass);
					
					paths.splice(i, 1);
					referencePaths.splice(i, 1);
					i = -1; // 重新开始
					break;
				}
			}
		}
		var mpath = "";
		var tempReferencePath = "";
		var h = 0;
		for (h = 0; h < paths.length - 1; h++) {
			mpath = mpath + paths[h] + ",";
			tempReferencePath=tempReferencePath+ referencePaths[h] + ",";
		}
		mpath = mpath + paths[h];
		tempReferencePath=tempReferencePath + referencePaths[h]
		jquery("#reference").text(tempReferencePath);
		return mpath;
	}catch(e){
		myAlert(e);
	}

	
}