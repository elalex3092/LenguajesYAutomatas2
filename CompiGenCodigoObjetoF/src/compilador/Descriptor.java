/*:-----------------------------------------------------------------------------
 *:                       INSTITUTO TECNOLOGICO DE LA LAGUNA
 *:                     INGENIERIA EN SISTEMAS COMPUTACIONALES
 *:                         LENGUAJES Y AUTOMATAS II           
 *: 
 *:                  SEMESTRE: ENE-JUN/2016     HORA: 18-19 HRS
 *:                                   
 *:               
 *:    # Clase Descriptor donde se guarda el nombre y el registro en el que se
 *:            una variable
 *:                           
 *: Archivo       : Descriptor.java
 *: Autores       : Equipo de Nuria Martínez                     
 *: Fecha         : 05/Jun/2016
 *: Compilador    : Java JDK 7
 *: Descripción   :  
 *:                  
 *:           	     
 *: Ult.Modif.    :
 *:  Fecha      Modificó            Modificacion
 *:=============================================================================
 *:-----------------------------------------------------------------------------
 */



package compilador;

import java.util.ArrayList;

public class Descriptor {
   //varialbes locales
   private ArrayList<String> datos;
   private ArrayList<String> pos;   
    private Compilador cmp;
   private boolean           ax = true;
   private boolean           bx = true;
   private boolean           cx = true;
   private boolean           dx = true;
   public boolean           vienedepop=false;
   /* private void emite ( String codObj ) {
      cmp.erroresListener.mostrarCodObj(codObj );
    }*/
   //-------------------------------------------------------------------------
   //Constructor por default
   public Descriptor () {
       datos = new ArrayList<String> ();
       pos = new ArrayList<String> ();
       
   }
   //-------------------------------------------------------------------------
   //metodo que inicializa los datos
   public void inicializar(){
       datos.clear();
       pos.clear();       
       ax = bx = cx = dx = true;
   }
   
   //-------------------------------------------------------------------------
   //metodo que inserta las variables
   public void insertar ( String nombre ) {
       datos.add ( nombre );
       pos.add ( nombre );
   }
   
   //-------------------------------------------------------------------------
   //metodo que remueve una variable
   public void remover ( String nombre ) {
       for (int i = 0; i < datos.size(); i++) {
          if ( datos.get(i).equals(nombre)){
              datos.remove(i);
              pos.remove(i);             
          }    
       }       
   }
   
   //-------------------------------------------------------------------------
   //mueve los registros a sus variables y libera los registros utilizados
   public String Default ( ){
       String moves = "";
       for (int i = 0; i < datos.size(); i++) {
           
                if (! datos.get(i).equals ( pos.get(i) ) ){
                     if ( noNumerosTemp ( datos.get(i) ) )
                    moves += "mov " + datos.get(i) + ", " + pos.get(i) +"\n"; 
                    if ( pos.get(i).equals("eax")){
                        ax = true;
                    }
                    else if ( pos.get(i).equals("ebx") ){
                        bx = true;
                    }
                    else if ( pos.get(i).equals("ecx") ){
                        cx = true;
                    }
                    else if ( pos.get(i).equals("edx") ){
                        dx = true;
                    }
                    pos.set( i, datos.get(i) );             
                }
                
       }     
       return moves;
   }
   
    //-------------------------------------------------------------------------
    //Metodo que libera el registro dado
    public void DefaultSinMov ( String nombre ){
        
       for (int i = 0; i < datos.size(); i++) {
           if ( datos.get(i).equals(nombre)){
          
               if ( pos.get(i).equals("eax") ){
                        ax = true;
                    }
                    else if ( pos.get(i).equals("ebx") ){
                        bx = true;
                    }
                    else if ( pos.get(i).equals("ecx") ){
                        cx = true;
                    }
                    else if ( pos.get(i).equals("edx") ){
                        dx = true;
                    } 
                 pos.set( i, datos.get(i) );                  
           }
       }     
       
   }
    
   //-------------------------------------------------------------------------
   //Si los registros estan llenos los libera
   public String lleno(){
       String silleno = "";
       if ( !ax && !bx && !cx && !dx)
           silleno =  Default();
       return silleno ;
       
   }
   //-------------------------------------------------------------------------
   //Metodo que retorna un registro vacio
   public String Disponible ( ){
       
       String Registro ="X";
       
       if ( ax ){
           ax = false;
           return "eax";
       }
       else if ( bx ){
           bx = false;
           return "ebx";
       }
       else if ( cx ){
           cx = false;
           return "ecx";
       }
       else if ( dx ){
           dx = false;
           return "edx";
       }
       return Registro;
   }
    public void HazDisponible (){
       
       
       
       
           ax = true;
           
       
       
           bx = true;
           
       
       
           cx = true;
           
       
       
           dx = true;
           
       }
       
   
   
   //-------------------------------------------------------------------------
   //Metodo que limpia el descriptor
   public void removerTodos () {
       datos.clear ();
       pos.clear();
   }
   
   //-------------------------------------------------------------------------
   //metodo que checa si una variable esta en datos
   public boolean contiene ( String nombre ) {
       return datos.contains ( nombre );
   }   
   
   //-------------------------------------------------------------------------
   //Metodo que cambia el registro donde se esta la variable 
   public void setPos ( String direccion, String registro ){
       for (int i = 0; i < datos.size(); i++) {
           if ( datos.get(i).equals( direccion ) ){
               pos.set(i, registro);
           }
       }
   }
   
   //-------------------------------------------------------------------------
   //Metodo que regresa la posicion de los datos  
   public String getPos ( String direccion ){
       for (int i = 0; i < datos.size(); i++) {
           if ( datos.get(i).equals( direccion ) ){
               return pos.get(i);
           }
       }
       return "X";
   }
   //-------------------------------------------------------------------------
   //Metodo que dice si no esta en registro una variable
   public boolean original ( String direccion ) {
      // emite("entra a original direccion= "+direccion);
       for (int i = 0; i < datos.size(); i++) {
           if ( datos.get(i).equals( direccion ) ){
               //emite("datos de i= "+datos.get(i));
               if ( datos.get(i).equals(pos.get(i)) ){
                   //emite("pos de i= "+pos.get(i));
                   return true;
               }
           }
       }
      /* if(vienedepop)
       {  
           vienedepop=false;
           return true;
       }*/
       return false;
   }    
   
   //-------------------------------------------------------------------------
   //Metodo que nos dice si no es temporal o numero
   public boolean noNumerosTemp ( String valor){
        String retornado = "";
      //emite("entra a noNumerosTemp");
      char [] cad = valor.toCharArray();
      
      if (cad[0] == 't'){
          try{
              for (int i = 1; i < cad.length; i++) {
              retornado += cad[i];
          }
           int pos = Integer.parseInt(retornado);
           return false;
              
          }
          catch (Exception ex){
              return true;
          }
           
       }
      else {
         try{
           int pos = Integer.parseInt(valor);
           //emite("esto trae pos: "+pos);
           //("retona falso");
           return false;
         }
         
         catch (Exception ex){
              return true;
          }
      }
      
   }
   
   //-------------------------------------------------------------------------
   //Metodo que nos dice si no es temporal 
   public boolean noNumerosTempSinnNum (String valor)
           {
       String retornado = "";
      char [] cad = valor.toCharArray();
             if (cad[0] == 't'){
          try{
              for (int i = 1; i < cad.length; i++) {
              retornado += cad[i];
          }
           int pos = Integer.parseInt(retornado);
           return false;
              
          }
          catch (Exception ex){
              return true;
          }
           
   
             }
         return true;
   }
}
