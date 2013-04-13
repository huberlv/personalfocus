<%@ page language="java" import="java.util.Vector"%><%response.setCharacterEncoding((String)request.getAttribute("charset"));
Vector<String>  vs=(Vector<String>)request.getAttribute("html");
for(int i=0;i<vs.size();i++)
{
	out.print(vs.get(i));
}
%>