<%
response.setHeader("Cache-Control", "no-cache");
response.setCharacterEncoding((String)request.getAttribute("charset"));
%>
${html}
