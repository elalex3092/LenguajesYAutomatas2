/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author Alan
 */
import java.util.Stack;
public class Popila {
    String nombre;
    Stack <String>pila;
    public Popila(String nom)
    {
        nombre=nom;
        pila=new Stack<>();
    }
}
