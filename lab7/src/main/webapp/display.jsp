<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.net.URLDecoder" %>
<%!
private String esc(Object v){return v==null?"—":v.toString().replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;").replace("'","&#39;");}
%>
<%
String name="",color="white";
if(request.getCookies()!=null)for(Cookie c:request.getCookies()){
 if("name".equals(c.getName())){try{name=URLDecoder.decode(c.getValue(),"UTF-8");}catch(IllegalArgumentException e){name="";}}
 if("color".equals(c.getName()) && ("white".equals(c.getValue())||"lightblue".equals(c.getValue())||"lightgreen".equals(c.getValue())||"pink".equals(c.getValue())))color=c.getValue();
}
%>
<!doctype html><html lang="ru"><meta charset="UTF-8"><title>Данные Cookie и сессии</title><body style="background-color:<%=color%>">
<h1>Сохранённые данные</h1><h2>Cookie</h2><p>Имя: <%=esc(name)%></p><p>Цвет: <%=esc(color)%></p>
<h2>Сессия</h2><p>Количество отправок формы: <%= session.getAttribute("count")==null?0:session.getAttribute("count") %></p>
<p>Предыдущее обращение: <%=esc(session.getAttribute("previousVisit"))%></p><p>Последнее обращение (UTC): <%=esc(session.getAttribute("lastVisit"))%></p>
<a href="index.jsp">Изменить настройки</a></body></html>
