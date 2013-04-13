/* jquery-fn-accordion v0
* Based on jQuery JavaScript Library v3
* http://jquery.com/
*
* The author of the following code: miqi2214 , wbpbest
* Blog:eycbest.cnblogs.com , miqi2214.cnblogs.com
* Date: 2010-3-10
*/
//ע�⣺������Գ������������õ�jquery�汾�ţ���ǰ���ð汾Ϊ1.3
//����˵����
//tabList:����ѡ��ĸ�����
//tabTxt :�������ݲ�ĸ�����
//options.currentTab:����ѡ������к�
//options.defaultClass:��ǰѡ�����״̬��ʽ����Ĭ������Ϊ��current��
//isAutoPlay:�Ƿ��Զ��л�
//stepTime:�л����ʱ��
//switchingMode:�л���ʽ��'c'��ʾclick�л�;'o'��ʾmouseover�л���
//���÷�ʽ �籾ҳ���·�����
jQuery.fn.tabs = function(tabList, tabTxt, options) {
    var _tabList = jQuery(this).find(tabList);
    var _tabTxt = jQuery(this).find(tabTxt);

    //Ϊ�˼򻯲�����ǿ�ƹ涨ѡ�������li��ǩʵ��

    var tabListLi = _tabList.find("li");
    var defaults = { currentTab: 0, defaultClass: "current", isAutoPlay: false, stepTime: 2000, switchingMode: "c" };
    var o = jQuery.extend({}, defaults, options);
    var _isAutoPlay = o.isAutoPlay;
    var _stepTime = o.stepTime;
    var _switchingMode = o.switchingMode;
    _tabList.find("li:eq(" + o.currentTab + ")").addClass(o.defaultClass);

    //ǿ�ƹ涨���ݲ������div��ʵ��
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
				        _tabTxt.children("div").eq(i).css({ "display": "block" }).siblings().css({ "display": "none" }); //��׼��ʽ
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








