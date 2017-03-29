/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

public class Cuadruplo {
    public String op,arg1,arg2,resultado;
    public boolean lider;
    public int bloqueBasico;
    public int arg1SigUso;
    public int arg2SigUso;
    public int resSigUso;
    
    public Cuadruplo( ){
    op = arg1 = arg2 = resultado =  "";
    lider = false;
    bloqueBasico = 0;
   arg1SigUso = arg1SigUso = resSigUso = 0;
  
    }
    
    public Cuadruplo ( String op,   String arg1, 
                       String arg2, String resultado ) {
        
        this.op   = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.resultado = resultado;       
        
    }
   
}
