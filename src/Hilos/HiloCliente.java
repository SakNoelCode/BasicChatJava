/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Interfaces.cliente;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.DefaultListModel;

/**
 *
 * @author ARCANGEL
 */
public class HiloCliente extends Thread {

    private Socket SocketCliente;
    private DataInputStream entrada;
    private cliente cliente;
    private ObjectInputStream entradaObjetos;

    public HiloCliente(Socket cliente, cliente aThis) {
        this.SocketCliente = cliente;
        this.cliente = aThis;
    }

    @Override
    public void run() {
        while (true) {
            try {
                entrada = new DataInputStream(SocketCliente.getInputStream());
                cliente.mensajeria(entrada.readUTF());

                entradaObjetos = new ObjectInputStream(SocketCliente.getInputStream());
                cliente.ActualizarLista((DefaultListModel) entradaObjetos.readObject());

            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e);
            }
        }
    }

}
