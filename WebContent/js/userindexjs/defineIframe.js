function defineIframe() {
	/*
	try {
		jQuery('iframe,frame').each(
				function(i) {
					if(this.src.length<1){
						return;
					}
					var div=document.createElement('div');
					var tempDiv=jQuery(div);
					tempDiv.html("<div style='background-color: #0066CC;border: 2px outset #003399;z-index:214748367'><a href='"+this.src+"' name='iframeLink' style='color: #FFFFFF'>定义本模块</a></div>");
					getExtraDiv().append(tempDiv);
					var iframe=jQuery(this);
					tempDiv.css({
						position:'absolute',
						left : iframe.offset().left,
						top : iframe.offset().top,
						width :200,
						height :100,
						'z-index' : 214748366,
						'background-color' : '#66CCFF',
						opacity : 0.8
					});
					
				});
		jQuery("a[name=iframeLink]").click(function(e) {
			e.stopPropagation();
			window.open(showWebPageURL + e.currentTarget, "_blank");
			return false;
		});
		jQuery("a[name=iframeLink]").mousedown(function(e) {
			e.stopPropagation();
			return false;
		});
	} catch (e) {

		myAlert(e);
	}
	*/
}
