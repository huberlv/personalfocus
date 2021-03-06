
var olpfPaths; // 特征
var pathsArray = new Array();
var olpfModuleId; // 模块ID
var paths; // 特征数组
// 开始取更新时间，由action程序改变
var olpfFlashtime = 4000;
var mid;
var unpaths = new Array();
//window.onbeforeunload=olpftooltipsFree;
onerror = olpfHandleErr
// 思路：首先由olpfPaths，olpfModuleId得到组特征数组，模块数组，再由组特征数组得到组特征的每一条单特征
var sourceArray = new Array();
var failPathNumArray = new Array();
/**
 * 取得特征文字
 * 
 * @return
 */
function monitor() {
	if (olpfModuleId != '-1') // 如果有要更新的网页
	{
		var source = "";
		var currentTag;
		var failPathNum = 0.0;
		var temps=null;
		var tempsource=null;
		for ( var j = 0; j < pathsArray.length; j++) {
			source = new String();
			paths = pathsArray[j];			
			for ( var i = 0; i < paths.length; i++) {
				try {
					currentTag = jQuery(paths[i]);
					
					// 如果取到DOM对象，则将其源码加入到source
					if (typeof (currentTag) != "undefined") {
						//将相对地址变成绝对地址
						currentTag.find("a").each(function(e){
							this.href=getURL("",this.href);
						});
						currentTag.find('form').each(
								function(i) {
									var temp=jQuery(this).find("input:first");//恢复网址
									this.action = jQuery(temp).attr("value");
									jQuery(temp).remove();
								});

						// 改变跳转菜单的地址
						currentTag.find('option').each(function(i) {
							if (this.value.indexOf(showWebPageURL) >= 0) {
								this.value = this.value.replace(showWebPageURL,"");
							}
						});
						///////
						tempsource = new String(currentTag.html());
						temps="<" + currentTag.attr("tagName");//+" style='width:"+tag.width()+"px; height:"+tag.height()+"px'";
						if(currentTag.attr("href")!=null&&currentTag.attr("href").length>0)
						{
							temps = temps+" href="+getURL("",currentTag.attr("href"));
						}
						if(currentTag.attr("id")!=null&&currentTag.attr("id").length>0)
						{
							temps = temps+" id='"+currentTag.attr("id")+"'";
						}
						if(currentTag.attr("class")!=null&&currentTag.attr("class").length>0)
						{
							temps = temps+" class='"+currentTag.attr("class")+"'";
						}
						temps =temps + ">" + tempsource + "</"
								+ currentTag.attr("tagName") + ">";
							tempsource=temps;
						/////再次替换成平台的网址
						currentTag.find('form').each(
								function(i) {
									temp = "<input id='olpfs" + i
											+ "' type=hidden name=url value="
											+ this.action + "?>";
									this.action = showWebPage;
									//this.method="get";
									jQuery('body').append(temp);
									jQuery("#olpfs" + i).prependTo(jQuery(this));
								});

						// 改变跳转菜单的地址
						currentTag.find('option').each(function(i) {
							if (this.value.indexOf("http:") >= 0) {
								this.value = showWebPageURL + this.value;
							}
						});
						//////						
					} else {
				
						failPathNum++; // 失败特征次数加一
					}
				} catch (e) {
					failPathNum++;
				}
				source=tempsource+source;
			}

			sourceArray.push(source);
			failPathNumArray.push(failPathNum / paths.length);
		}
		try {		
			monitorByJs.submitNewContent(sourceArray, mid, failPathNumArray,
					function(data) {
						if (data == true)// 提交成功
					{
						// 未处理
							window.open(olpfBase + '/personalfocus/user/definePage', '_self');
					// '_self');
					return;
				}// 不成功情况未处理			
			});
		} catch (e) {
			myAlert(e);
			window.open(olpfBase + '/personalfocus/user/definePage', '_self');
			//window.setTimeout(monitor, 20000); // 如果出错，则20秒后再提交		
			return;
		}
	}else{
	    window.open(olpfBase + '/personalfocus/user/definePage', '_self');
	}
}

jQuery(document).ready(function() {
	try {
		
		if (document.all) {
		//	window.attachEvent('onbeforeunload', olpftooltipsFree); // onbeforeunload
																	// =																		// olpftooltipsFree;
		} else {
			
	 	//   window.addEventListener('beforeunload', olpftooltipsFree, true);
		}
		if (olpfModuleId == '-1') // 如果无更新，则30秒后再连接平台，再请求要更新的网页
		{
			window.setTimeout(monitor, 30000);
		}
		// 取得特征数组，并组织成js的特征规范
		var reg = new RegExp("html\\[0\\]", "g");
		olpfPaths = olpfPaths.replace(reg, 'html');
		reg = new RegExp("body\\[0\\]", "g");
		olpfPaths = olpfPaths.replace(reg, 'body');
		// jQuery('body').append(olpfPaths);
		reg = new RegExp("/", "g");
		olpfPaths = olpfPaths.replace(reg, '>');
		reg = new RegExp("\\[", "g"); // 注意正则表达式转义
		olpfPaths = olpfPaths.replace(reg, ':eq(');
		reg = new RegExp("\\]", "g");
		olpfPaths = olpfPaths.replace(reg, ')');
		// jQuery('body').append('替换完毕');
		paths = new Array();
		var begin = 0
		var end = 0;
		end = olpfPaths.indexOf('%', begin);
		var spath = '';
		//jQuery('body').append(olpfPaths + '<br>');
		while (end >= 0) {
			spath = olpfPaths.substring(begin, end); // 不要第一个>
			begin = end + 1;
			end = olpfPaths.indexOf('%', begin);
			unpaths.push(spath);
		}
		for ( var i = 0; i < unpaths.length; i++) {
			paths = new Array();
			begin = 0;
			end = unpaths[i].indexOf(',', begin);
			while (end >= 0) {
				paths[paths.length] = unpaths[i].substring(begin + 1, end); // 不要第一个>
				begin = end + 1;
				end = unpaths[i].indexOf(',', begin);
			}
			paths[paths.length] = unpaths[i].substring(begin + 1,
					unpaths[i].length);
			pathsArray.push(paths);
		}
		//jQuery('body').append(pathsArray.length + '<br>');
		mid = new Array();
		begin = 0;
		end = olpfModuleId.indexOf('%', begin);
		while (end >= 0) {
			spath = olpfModuleId.substring(begin, end);
			begin = end + 1;
			end = olpfModuleId.indexOf('%', begin);
			mid.push(spath);
			if (end == olpfPaths.length - 1)
				break;
		}
		// window.setTimeout(monitor, 4000); //
		// 页面载入完成，60秒后开始取得特征最新信息（视具体网页和网速而定）

	} catch (e) { // 出错处理，完善后无警告
		jQuery('body').append('出错' + e.message);
	} finally {
		window.setTimeout(monitor, olpfFlashtime);
	}
});

