/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author Susana
 */
public class ElemBuffer {
    public String elemento;
    public Boolean operador;
    public Integer jerarquia;
    
    public ElemBuffer (String elem, Boolean oper, Integer jerq){
        elemento = elem;
        operador = oper;
        jerarquia = jerq;
    }
}
