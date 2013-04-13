<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML lang=zh xml:lang="zh" xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>互联网关注订阅平台</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<LINK rel=stylesheet type=text/css
	href="/personalfocus/css/userindexcss/nav.css">
<LINK rel=stylesheet type=text/css
	href="/personalfocus/css/userindexcss/contentstyle.css">
<SCRIPT type=text/javascript
	src="/personalfocus/js/userindexjs/olpfjquery-1.3.2.min.js"></SCRIPT>
<script type="text/javascript">
	jQuery(document).ready(function() {
		//alert(jQuery(window).width());
			var w = window.screen.width / 2 - 400;
			jQuery('#tableBox').css( {
				width : '794px',
				height : '401px',
				position : 'absolute',
				left : w
			});
			jQuery('#pwd').css( {
				left : '38px',
				top : '52px',
				position : 'absolute',
				width : '224px',
				height : '273px'
			});
			jQuery('#pwd').mouseover(function() {
				jQuery(this).addClass('picShow');
			});
			jQuery('#pwd').mouseout(function() {
				jQuery(this).removeClass('picShow');
			});
		});
</script>
<style type="text/css">
#tableBox {
	background-image:
		url("/personalfocus/images/activeSuccess/activesuccessTable.jpg");
}

.picShow {
	background-image: url("/personalfocus/images/activeSuccess/bluebg.png");
	cursor: pointer;
}
</style>
</HEAD>
<BODY>

<DIV id=lhpopciones><img
	style="vertical-align: middle; margin-left: 20px; margin-right: 10px"
	src="/personalfocus/images/portrait.jpg"></img>用户：<s:property
	value="%{#session.username}" /> &nbsp;&nbsp;&nbsp;&nbsp;

<div id="tools"></DIV>
</div>


<DIV class=g-doc>
<DIV class=g-hd>
<H1 class=m-logo><A href=""></A></H1>
</DIV>

<%--内容--%>

<div id="tableBox">
<div id="pwd"></div>
</div>

</DIV>
<DIV class=g-ft>
<DIV class=inner>
<DIV class=copy-wrap>
<DIV class=copyright><A href="mailto:357370736@qq.com">联系方法:357370736@qq.com</A> 


</DIV>
</DIV>
</DIV>
</DIV>



</BODY>
</HTML>
