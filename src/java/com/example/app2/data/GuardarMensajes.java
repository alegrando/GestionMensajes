package com.example.app2.data;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alejandro
 */
@WebServlet(urlPatterns = {"/GuardarMensajes"})
public class GuardarMensajes extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(BDMensajes.class.getName());
    public static final String ACTION_EDIT_REQUEST = "E";
    public static final String ACTION_EDIT_RESPONSE = "S";
    public static final String ACTION_INSERT_REQUEST = "I";
    public static final String ACTION_INSERT_RESPONSE = "A";
    public static final String ACTION_DELETE = "D";
    public static final String ACTION_EXPORT_XML = "X";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        BDMensajes.connect("192.168.200.101", "bd_mensajes", "root", "root");
        
        String action = request.getParameter("action");
        logger.fine("action = " + action);
        if (action != null && !action.isEmpty()) {
            //Se ha llamado a Main tras pulsar el enlace de Editar en la lista
            if (action.equals(ACTION_EDIT_REQUEST)) {
                //Obtener el id de la persona a partir del parámetro is que se
                //  ha debido utilizar al realizar la llamada a esta página Main
                int id = Integer.valueOf(request.getParameter("id"));
                //Lee de la BD los datos de la persona con el id solicitado
                Mensajes mensajes = BDMensajes.getMensasjesByID(id);
                //Se prepara el objeto Person generado para pasarlo a otra página
                request.setAttribute("mensajes", mensajes);
                //Se redirige a otra página que muestra el detalle de la persona,
                //  pasando en request la persona
                redirectTo("detalle.jsp", request, response);
            //Se ha llamado a Main tras pulsar el botón Guardar en la página de 
            //  detalle cuando se estaba editando una persona existente
            } else if (action.equals(ACTION_EDIT_RESPONSE)) {
                int id = Integer.valueOf(request.getParameter("id"));
                Mensajes mensajes = BDMensajes.getMensasjesByID(id);
                //Se modifican los datos que había en la BD, asignado los datos
                //  que se han introducido en la página de detalle. Esos datos
                //  se reciben como parámetros de la llamada (request) a esta página Main 
                updatePersonWithRequestData(mensajes, request);
                //Se actualiza en la BD la persona
                BDMensajes.updateMensajes(mensajes);
                //Volvemos a recargar la página Main con lista (no se indica 
                //  ninguna acción si se quiere mostrar la lista)
                response.sendRedirect("GuardarMensajes?action=");
            //Se ha llamado a Main tras pulsar el botón Insertar
            } else if (action.equals(ACTION_INSERT_REQUEST)) {
                Mensajes mensajes = new Mensajes();
                request.setAttribute("mensajes", mensajes);
                redirectTo("detalle.jsp", request, response);
            //Se ha llamado a Main tras pulsar el botón Guardar en la página de 
            //  detalle cuando se estaba insertando una nueva persona
            } else if (action.equals(ACTION_INSERT_RESPONSE)) {
                Mensajes mensajes = new Mensajes();
                updatePersonWithRequestData(mensajes, request);
                BDMensajes.insertar(mensajes);
                response.sendRedirect("GuardarMensajes?action=");
            //Se ha llamado a Main tras pulsar el enlace de Eliminar en la lista
            } else if (action.equals(ACTION_DELETE)) {
                String id = request.getParameter("id");
                //Se borra de la BD la persona con el ID indicado
                BDMensajes.deleteMensajesById(id);
                response.sendRedirect("GuardarMensajes?action=");
            //Se ha llamado a Main tras pulsar el botón Exportar XML
            } else if (action.equals(ACTION_EXPORT_XML)) {
                //Se obtiene desde la BD una lista con todas las personas
                ArrayList<Mensajes> mensajesList = BDMensajes.list();
                //Se prepara la lista obtenida para pasarla a otra página
                request.setAttribute("mensajesList", mensajesList);
                //Se redirige a otra página que genera el XML pasando en request
                //  la lista de personas
                redirectTo("export_xml.jsp", request, response);
            }
        //Si no se indica ninguna acción se entiende que se quiere mostrar la lista
        } else {            
            ArrayList<Mensajes> mensajesList = BDMensajes.list();
            request.setAttribute("mensajesList", mensajesList);
            redirectTo("mensajes_list.jsp", request, response);
        }
    }

    /**
     * Redirige la navegación web a la página indicada en newUrl, pasándole
     * en request los datos que necesite
     * @param newUrl
     * @param request
     * @param response 
     */
    private static void redirectTo(String newUrl, HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestDispatcher dispatcher = null;
            dispatcher = request.getRequestDispatcher(newUrl);
            dispatcher.forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(GuardarMensajes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GuardarMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        private void updatePersonWithRequestData(Mensajes mensajes, HttpServletRequest request) {
        mensajes.setAsunto(request.getParameter("asunto"));
        mensajes.setContenido(request.getParameter("contenido"));
        mensajes.setRemitente(request.getParameter("remitente"));
        mensajes.setGrupo(request.getParameter("grupo"));
        
        
                
    }
        
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        try {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet GuardarMensajes</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println(mensajes.getAsunto()+"<br>");
//            out.println(mensajes.getContenido()+"<br>");
//            out.println(mensajes.getRemitente()+"<br>");
//            out.println(mensajes.getGrupo()+"<br>");
//            out.println("</body>");
//            out.println("</html>");
//        } finally {
//            out.close();
//        }
   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
