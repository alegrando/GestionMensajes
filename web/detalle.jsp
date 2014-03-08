<%-- 
    Document   : detalle
    Created on : 19-feb-2014, 12:15:17
    Author     : alejandro
--%>
<%@page import="com.example.app2.data.GuardarMensajes"%>
<%@page import="com.example.app2.data.Mensajes"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    //En request se reciben los datos enviados desde Main
    Mensajes mensajes = (Mensajes)request.getAttribute("mensajes");    
    String action = request.getParameter("action");    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Datos del Mensaje</h1>
        <form method="post" action="GuardarMensajes">
            <input type="hidden" name="id" value="<%= mensajes.getId() %>">
            Asunto: <input type="text" name="asunto" value="<%=mensajes.getAsunto() %>"><br>
            Contenido: <input type="text" name="contenido" value="<%=mensajes.getContenido() %>"><br>
            Remitente: <input type="text" name="remitente" value="<%=mensajes.getRemitente() %>"><br>
            Grupo: <input type="text" name="grupo" value="<%=mensajes.getGrupo() %>"><br>
            <%
                if(action.equals(GuardarMensajes.ACTION_EDIT_REQUEST)) {
                    out.print("<input type='submit' value='Guardar'>");
                    out.print("<input type='hidden' name='action' value='"+GuardarMensajes.ACTION_EDIT_RESPONSE+"'>");
                } else if(action.equals(GuardarMensajes.ACTION_INSERT_REQUEST)) {
                    out.print("<input type='submit' value='Añadir'>");
                    out.print("<input type='hidden' name='action' value='"+GuardarMensajes.ACTION_INSERT_RESPONSE+"'>");
                }
            %>
            <%-- Para que se muestre de nuevo la lista no hay que indicar 
                ninguna acción y volver a cargar Main --%>
        </form>
        <form method="post" action="GuardarMensajes">
            <input type="hidden" name="action" value="">
            <input type="submit" value="Cancelar">
        </form>
    </body>
</html>

