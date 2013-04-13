/* jquery-fn-accordion v0
* Based on jQuery JavaScript Library v3
* http://jquery.com/
*
* The author of the following code: miqi2214 , wbpbest
* Blog:eycbest.cnblogs.com , miqi2214.cnblogs.com
* Date: 2010-3-10
*/
//注意：如果调试出错，请检查您引用的jquery版本号，当前引用版本为1.3
//参数说明：
//tabList:包裹选项卡的父级层
//tabTxt :包裹内容层的父级层
//options.currentTab:激活选项卡的序列号
//options.defaultClass:当前选项卡激活状态样式名，默认名字为“current”
//isAutoPlay:是否自动切换
//stepTime:切换间隔时间
//switchingMode:切换方式（'c'表示click切换;'o'表示mouseover切换）
//调用方式 如本页最下方代码
jQuery.fn.tabs = function(tabList, tabTxt, options) {
    var _tabList = jQuery(this).find(tabList);
    var _tabTxt = jQuery(this).find(tabTxt);

    //为了简化操作，强制规定选项卡必须用li标签实现

    var tabListLi = _tabList.find("li");
    var defaults = { currentTab: 0, defaultClass: "current", isAutoPlay: false, stepTime: 2000, switchingMode: "c" };
    var o = jQuery.extend({}, defaults, options);
    var _isAutoPlay = o.isAutoPlay;
    var _stepTime = o.stepTime;
    var _switchingMode = o.switchingMode;
    _tabList.find("li:eq(" + o.currentTab + ")").addClass(o.defaultClass);

    //强制规定内容层必须以div来实现
    _tabTxt.children("div").each(function(i) {
    	jQuery(this).attr("id", "wp_div" + i);
    }).eq(o.currentTab).css({ "display": "block" });


    tabListLi.each(
		function(i) {
			jQuery(tabListLi[i]).mouseover(
				function() {
				    if (_switchingMode == "o") {
				    	jQuery(this).click();
				    }
				    _isAutoPlay = false;
				}
			);
			jQuery(tabListLi[i]).mouseout(
				function() {
				    _isAutoPlay = true;
				}
			);
		}
	);

    _tabTxt.each(
		function(i) {
			jQuery(_tabTxt[i]).mouseover(
				function() {
				    _isAutoPlay = false;
				}
			);
			jQuery(_tabTxt[i]).mouseout(
				function() {
				    _isAutoPlay = true;
				}
			)
		});

    // }
    //    else {
    tabListLi.each(
		function(i) {
			jQuery(tabListLi[i]).click(
				function() {
				    if (jQuery(this).className != o.defaultClass) {
				    	jQuery(this).addClass(o.defaultClass).siblings().removeClass(o.defaultClass);
				    }
				    if (jQuery.browser.msie) {
				        _tabTxt.children("div").eq(i).siblings().css({ "display": "none" });
				        _tabTxt.children("div").eq(i).fadeIn(600);
				    } else {
				        _tabTxt.children("div").eq(i).css({ "display": "block" }).siblings().css({ "display": "none" }); //标准样式
				    }


				}
			)
		}
	);

    // }
    function selectMe(oo) {

        if (oo != null && oo.html() != null && _isAutoPlay) {
            oo.click();
        }
        if (oo.html() == null) {
            selectMe(_tabList.find("li").eq(0));

        } else {
            window.setTimeout(selectMe, _stepTime, oo.next());
        }
    }
    if (_isAutoPlay) {
        //alert("_isAutoPlay:" + _isAutoPlay);
        selectMe(_tabList.find("li").eq(o.currentTab));
    }
    //alert(_isAutoPlay);
    return this;
};








