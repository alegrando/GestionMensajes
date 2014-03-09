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
        <style>
	.campo{
		background-color:teal;
		width:130px;
		height:11px;
		color:white;
		text-align:center;
		border-radius:5px;
	}
	.campo2{
		background-color:white;
		padding-right:18px;
		height:11px;
		padding-left:18px;
		border-radius:5px;
	}
	.input{
		background-color:white;
		padding-right:18px;
		height:17px;
		padding-left:18px;
		border-radius:5px;
		width:inherit;
	}
	.tabla{
		border:outset teal 2px;
		border-radius:5px;
	}
	.td{
		border:outset black 2px;	
	}
</style>
    </head>
    <body bgcolor="#C0C0C0" link="teal" vlink="teal" alink="teal">
        <BASEFONT face="arial, helvetica">
         <TABLE border="0" align="center" cellspacing="3" cellpadding="3" width="650">
            <TR>
                <TH colspan="2" width="100%" height="80px" bgcolor="teal" style="border-radius:8px">&nbsp;
                    <FONT size="6" color="white" face="Verdana, Geneva, sans-serif">Lista de Mensajes</FONT>&nbsp
                </TH>
            </TR>
         </TABLE>   
        
        <table border="1" class='tabla'  align="center">
            <tr style="background-color:teal;color:white; text-align:center;">
                <th class='td'>Asunto</th>
                <th class='td'>Remitente</th>
                <th class='td'>Grupo</th>
                <th colspan="2" class='td'>Acciones</th>
            </tr>
        <% 
            ArrayList<Mensajes> mensajesList = (ArrayList)request.getAttribute("mensajesList"); 
            for(Mensajes mensajes: mensajesList) {
                out.println("<tr>");
                out.println("<td class='td'>"+mensajes.getAsunto()+"</td>");
                out.println("<td class='td'>"+mensajes.getRemitente()+"</td>");
                out.println("<td class='td'>"+mensajes.getGrupo()+"</td>");
                String editLink = "GuardarMensajes?action=E&id="+mensajes.getId();
                out.println("<td class='td'><a href='"+editLink+"'>Editar</td>");
                String deleteLink = "GuardarMensajes?action=D&id="+mensajes.getId();
                out.println("<td class='td'><a href='"+deleteLink+"'>Suprimir</td>");
                out.println("</tr>");
            }
        %>
        </table>
        <br>
        <center><form method="get" action="GuardarMensajes">
            <input type="hidden" name="action" value="I">
            <input type="submit" value="Nuevo Mensaje">
        </form>
        <form method="get" action="GuardarMensajes" target="_blank">
            <input type="hidden" name="action" value="X">
            <input type="submit" value="Exportar XML">
        </form></center>
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

