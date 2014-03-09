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
                    <FONT size="6" color="white" face="Verdana, Geneva, sans-serif">Datos del Mensaje</FONT>&nbsp
                </TH>
            </TR>
         </TABLE> 
        
        <center><form method="post" action="GuardarMensajes"><table><tr>
            <input type="hidden" name="id" value="<%= mensajes.getId() %>">
            <td class='campo'>Asunto:</td><td> <input  class='input' type="text" name="asunto" value="<%=mensajes.getAsunto() %>"></td></tr>
            <td class='campo'>Contenido:</td><td> <input class='input' type="text" name="contenido" value="<%=mensajes.getContenido() %>"></td></tr>
            <td class='campo'>Remitente:</td><td> <input class='input' type="text" name="remitente" value="<%=mensajes.getRemitente() %>"></td></tr>
            <td class='campo'>Grupo:</td><td> <input class='input' type="text" name="grupo" value="<%=mensajes.getGrupo() %>"></td></tr></tr></table>
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
        </form></center>
    </body>
</html>

