/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Interfaces.servidor;
import java.io.*;
import java.net.Socket;
import java.util.Vector;
import javax.swing.DefaultListModel;

/**
 *
 * @author ARCANGEL
 */
public class HiloServidor extends Thread{
    
    private DataInputStream entrada;
    private DataOutputStream salida;
    private servidor server;
    private Socket cliente;
    public static Vector<HiloServidor> userActivo = new Vector();
    private String nombre;
    private ObjectOutputStream salidaObjetos;

        
    public HiloServidor(Socket sc, String name,servidor srv)throws Exception{
        this.cliente = sc;
        this.server = srv;
        this.nombre = name;       
        userActivo.add(this);
        
        for(int i =0; i<userActivo.size();i++){
            userActivo.get(i).envioMensaje(nombre + " se ha conectado");
        }
    }
    
    
    public void run(){
        String mensaje;
        while(true){
            try {
                entrada = new DataInputStream(cliente.getInputStream());
                mensaje = entrada.readUTF();
                
                for(int i =0;i<userActivo.size();i++){
                    userActivo.get(i).envioMensaje(mensaje);
                    server.mensajeria("Mensaje Enviado");
                    
                }
                
            } catch (Exception e) {
                break;
            }
        }
        
        userActivo.removeElement(this);
        server.mensajeria("El usuario se ha desconectado");
        
        try {
            cliente.close();
        } catch (Exception e) {
            
        }
        
    }

    private void envioMensaje(String string) throws Exception {
        salida = new DataOutputStream( cliente.getOutputStream());
        salida.writeUTF(string);
        DefaultListModel modelo = new DefaultListModel();
        
        for(int i =0;i<userActivo.size();i++){
                modelo.addElement(userActivo.get(i).nombre);
        }
        
        salidaObjetos = new ObjectOutputStream(cliente.getOutputStream());
        salidaObjetos.writeObject(modelo);
    }
    
    
    
    
    
}
