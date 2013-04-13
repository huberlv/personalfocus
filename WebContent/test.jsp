<%@page contentType="text/html; charset=GB2312" language="java"
	import="java.util.*,java.*" session="true" buffer="8kb"
	autoFlush="true" info="this is test" errorPage="index.jsp"
	isThreadSafe="true"%>
<html>
	<head>
	<script>
	  window.location.replace('http://www.baidu.com');
	</script>
		<style type="text/css">
.test:hover {
	font-size: x-large;
}
</style>

	</head>
	<body>
		<div class="test">
			<a href="#">111</a>
		</div>
		<%
			int a = 9;
			out.println(application.getMajorVersion() + "<BR>");
			out.println(application.getMinorVersion() + "<BR>");
			System.out
					.println(application
							.getMimeType("F:\\project\\MyEclipse\\personalfocus\\WebContent\\index.jsp")
							+ "<BR>");
			out.println(application.getRealPath("index.jsp") + "<BR>");
			session.setMaxInactiveInterval(200);
			out.println(session.getMaxInactiveInterval() + "<BR>");
			Enumeration enu = session.getAttributeNames();
			System.out.println(enu.hasMoreElements());
			while (enu.hasMoreElements()) {
				System.out.println(enu.nextElement() + "111");
			}
			out.println(request.getRequestURI());
			out.println("<br>");
			out.println(request.getAuthType());
			out.println("<br>");
			out.println(request.getCharacterEncoding());
			out.println("<br>");
			out.println(request.getContentLength());
			out.println("<br>");
			out.println(request.getContentType());
			out.println("<br>");
			out.println(request.getContextPath());
			out.println("<br>");
			out.println(request.getServerName());
			out.println("<br>");
			out.println(request.getRemoteHost());
			out.println("<br>");
			out.println(request.getRemoteAddr());
			out.println("<br>");
			out.println(request.getRemotePort());
			out.println("<br>getProtocol:");
			out.println(request.getProtocol());
			out.println("<br>getDateHeader:");
			out.println(request.getDateHeader("yyyy"));
			out.println("<br>getCookies:");
			out.println(request.getCookies()[0].getComment());
			out.println("<br>isRequestedSessionIdFromURL:");
			out.println(request.isRequestedSessionIdFromURL());
		%>
	</body>
	
	
</html>