<%-- 
    Document   : mensajes_list
    Created on : 26-feb-2014, 17:15:14
    Author     : alejandro
--%>

<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="com.example.app2.data.Mensajes"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Lista de Mensajes</h1>
        <table border="1">
            <tr>
                <th>Asunto</th>
                <th>Remitente</th>
                <th>Grupo</th>
                <th colspan="2">Acciones</th>
            </tr>
        <% 
            ArrayList<Mensajes> mensajesList = (ArrayList)request.getAttribute("mensajesList"); 
            for(Mensajes mensajes: mensajesList) {
                out.println("<tr>");
                out.println("<td>"+mensajes.getAsunto()+"</td>");
                out.println("<td>"+mensajes.getRemitente()+"</td>");
                out.println("<td>"+mensajes.getGrupo()+"</td>");
                String editLink = "GuardarMensajes?action=E&id="+mensajes.getId();
                out.println("<td><a href='"+editLink+"'>Editar</td>");
                String deleteLink = "GuardarMensajes?action=D&id="+mensajes.getId();
                out.println("<td><a href='"+deleteLink+"'>Suprimir</td>");
                out.println("</tr>");
            }
        %>
        </table>
        <br>
        <form method="get" action="GuardarMensajes">
            <input type="hidden" name="action" value="I">
            <input type="submit" value="Nuevo Mensaje">
        </form>
        <form method="get" action="GuardarMensajes" target="_blank">
            <input type="hidden" name="action" value="X">
            <input type="submit" value="Exportar XML">
        </form>
        <%
            out.println("Aplicación ejecutada en la máquina: ");
            try {
                BufferedReader br = new BufferedReader(new FileReader("/etc/hostname"));
                out.println("<b>" + br.readLine() + "</b>");
            } catch(FileNotFoundException ex) {
                out.println("<b>Nombre de servidor no encontrado</b><br>");
                out.println("Sólo se detectarán los nombres de servidores Ubuntu");
            }
        %>
    </body>
</html>

