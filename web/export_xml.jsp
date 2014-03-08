<?xml version="1.0" encoding="UTF-8"?>
<%-- La línea anterior debe ir siempre la primera si se genera un XML --%>
<%-- 
    Document   : export_xml
    Created on : 26-feb-2014, 17:28:35
    Author     : alejandro
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.example.app2.data.Mensajes"%>

<%-- Se informa que el contenido va a ser XML --%>
<%@page contentType="text/xml" pageEncoding="UTF-8"%>

<MENSAJES>
<% 
    ArrayList<Mensajes> mensajesList = (ArrayList)request.getAttribute("mensajesList"); 
    for(Mensajes mensajes: mensajesList) {
        out.println("<MENSAJE>");
        out.println("<ID>"+mensajes.getId()+"</ID>");
        out.println("<ASUNTO>"+mensajes.getAsunto()+"</ASUNTO>");
        out.println("<CONTENIDO>"+mensajes.getContenido()+"</CONTENIDO>");
        out.println("<REMITENTE>"+mensajes.getRemitente()+"</REMITENTE>");
        out.println("<GRUPO>"+mensajes.getGrupo()+"</GRUPO>");
        out.println("</MENSAJE>");
    }
%>
</MENSAJES>
