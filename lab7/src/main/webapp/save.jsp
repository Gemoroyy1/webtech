<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.net.URLEncoder,java.time.Instant" %>
<%
request.setCharacterEncoding("UTF-8");
if (!"POST".equals(request.getMethod())) { response.sendRedirect("index.jsp"); return; }
String name=request.getParameter("name"), color=request.getParameter("color");
if(name==null || name.trim().isEmpty() || name.length()>80 || !("white".equals(color)||"lightblue".equals(color)||"lightgreen".equals(color)||"pink".equals(color))){response.sendError(400,"Invalid name or color");return;}
String path=request.getContextPath();
Cookie n=new Cookie("name",URLEncoder.encode(name.trim(),"UTF-8"));
Cookie c=new Cookie("color",color);
for(Cookie ck:new Cookie[]{n,c}){ck.setPath(path);ck.setMaxAge(604800);ck.setHttpOnly(true);ck.setSecure(request.isSecure());response.addCookie(ck);}
Integer count=(Integer)session.getAttribute("count");
session.setAttribute("count",count==null?1:count+1);
session.setAttribute("previousVisit",session.getAttribute("lastVisit"));
session.setAttribute("lastVisit",Instant.now().toString());
response.sendRedirect("display.jsp");
%>
