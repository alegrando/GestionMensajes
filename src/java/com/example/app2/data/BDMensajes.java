/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.app2.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejandro
 */
public class BDMensajes {
    
    private static Connection conexion;
    
    public BDMensajes(){

    }
    public static void connect(String databaseServer, String databaseName, String databaseUser, String databasePassword) {
        String strConection = "jdbc:mysql://" + databaseServer + "/" + databaseName;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(strConection, databaseUser, databasePassword);
            createTables();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BDMensajes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1049) {
                Logger.getLogger(BDMensajes.class.getName()).log(Level.FINE,
                        "Database not found: " + strConection + " - " + databaseUser + "/" + databasePassword);
                createDatabase(databaseServer, databaseName, databaseUser, databasePassword);
                Logger.getLogger(BDMensajes.class.getName()).log(Level.FINE,
                        "Database created");
                try {
                    conexion = DriverManager.getConnection(strConection, databaseUser, databasePassword); 
                    createTables();
                } catch (SQLException ex1) {
                    Logger.getLogger(BDMensajes.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            Logger.getLogger(BDMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void createDatabase(String databaseServer, String databaseName, String databaseUser, String databasePassword) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String strConecction = "jdbc:mysql://" + databaseServer;
            conexion = DriverManager.getConnection(strConecction, databaseUser, databasePassword);
            Statement statement = conexion.createStatement();
            String sql = "CREATE DATABASE " + databaseName;
            Logger.getLogger(BDMensajes.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(BDMensajes.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BDMensajes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BDMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createTables() {
        try {
            String sqlCreate = "CREATE TABLE IF NOT EXISTS mensajes (" +
                "id INTEGER PRIMARY KEY AUTO_INCREMENT" +
                ", asunto VARCHAR(50)" +
                ", contenido TEXT" +
                ", remitente VARCHAR(50)" +
                ", grupo VARCHAR(50)" +
                ")";
            Statement statement = conexion.createStatement();
            statement.execute(sqlCreate);
        } catch (SQLException ex) {
            Logger.getLogger(BDMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void insertar(Mensajes mensajes){
       
        try {
            String sqlCreate = "INSERT INTO mensajes (asunto, contenido, remitente, grupo) " +
                                    "VALUES ('"+mensajes.getAsunto()
                                    +"', '"+mensajes.getContenido()
                                    +"', '"+mensajes.getRemitente()
                                    +"', '"+mensajes.getGrupo()
                                    +"')";
            Statement statement = conexion.createStatement();
            statement.execute(sqlCreate);
        } catch (SQLException ex) {
            Logger.getLogger(BDMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static ArrayList<Mensajes> list(){
        ArrayList<Mensajes> lista = new ArrayList<Mensajes>();
        try {
            String sqlCreate = "SELECT * FROM mensajes";
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery(sqlCreate);
            while(rs.next()){
                String id =rs.getString("id");
                String asunto =rs.getString("asunto");
                String contenido =rs.getString("contenido");
                String remitente =rs.getString("remitente");
                String grupo =rs.getString("grupo");
                Mensajes mensaje = new Mensajes(id, asunto, contenido, remitente, grupo);
                lista.add(mensaje);
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(BDMensajes.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static Mensajes getMensasjesByID(int mensajesId) {
        Mensajes mensajes = null;
        try {
            String sql = "SELECT * FROM mensajes WHERE id=" + mensajesId;
            Statement statement = conexion.createStatement();
            Logger.getLogger(BDMensajes.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            ResultSet rs = statement.executeQuery(sql);
            boolean result = rs.isBeforeFirst();
            Logger.getLogger(BDMensajes.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
            if (rs.next()) {
                String id =rs.getString("id");
                String asunto =rs.getString("asunto");
                String contenido =rs.getString("contenido");
                String remitente =rs.getString("remitente");
                String grupo =rs.getString("grupo");
                mensajes = new Mensajes(id, asunto, contenido, remitente, grupo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BDMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensajes;
    }

    public static void updateMensajes(Mensajes mensajes) {
        try {
            String sql = "UPDATE mensajes SET "
                    + "asunto='" + mensajes.getAsunto() + "', "
                    + "contenido='" + mensajes.getContenido() + "', "
                    + "remitente='" + mensajes.getRemitente() + "', "
                    + "grupo='" + mensajes.getGrupo() + "' "
                    + "WHERE id=" + mensajes.getId();
            Statement statement = conexion.createStatement();
            Logger.getLogger(BDMensajes.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(BDMensajes.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(BDMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteMensajesById(String id) {
        try {
            String sql = "DELETE FROM mensajes WHERE id=" + id;
            Statement statement = conexion.createStatement();
            Logger.getLogger(BDMensajes.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(BDMensajes.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(BDMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
