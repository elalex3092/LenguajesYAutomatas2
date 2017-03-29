/*:-----------------------------------------------------------------------------
 *:                       INSTITUTO TECNOLOGICO DE LA LAGUNA
 *:                     INGENIERIA EN SISTEMAS COMPUTACIONALES
 *:                         LENGUAJES Y AUTOMATAS II           
 *: 
 *:        SEMESTRE:  Ene-Jun/2016            HORA: 18:00 HRS
 *:                                   
 *:               
 *:    # Clase con la funcionalidad del Generador de Codigo Objeto
 *                 
 *:                           
 *: Archivo       : GenCodigoObj.java
 *: Autor         : Equipo de Nuria Martinez  
 *: Fecha         : 28/May/2016
 *: Compilador    : Java JDK 7
 *: Descripción   :  
 *:                  
 *:           	     
 *: Ult.Modif.    :
 *:  Fecha      Modificó            Modificacion
 *:=============================================================================
 *: 28/05/2016  Equipo Completo   Errores que existian en las clases
 *:-----------------------------------------------------------------------------
 */

package compilador;

import java.util.ArrayList;
import java.util.Stack;

public class GenCodigoObj {
    private Compilador cmp;
    public Stack <String> Pilapops;
    Descriptor descriptor = new Descriptor();
    boolean decterminadas=false;
    //Constructor de la Clase
    public GenCodigoObj (Compilador cmp) {
         this.cmp = cmp;
         Pilapops=new Stack<String>();
    }
    
    //Fin del Constructor de la Clase
    
      private void emite ( String codObj ) {
      cmp.erroresListener.mostrarCodObj(codObj );
    }
    
    
    public void generar() {
        //Aqui va el algoritmo de Generacion de Codigo Objeto
        //emite("entra");
       descriptor.inicializar();
       agregar ();
       String plantilla="; Descripción del programa:\n" +
                         "; Autores:\n" +
                         "; Alan Emmanuel Araiza Martinez\n"+
                        "; Fecha de creación:\n" +
                        "; Revisiones:\n" +
                        "INCLUDE Irvine32.inc\n" +
                        "; \n" +
                        ".data \n";
       String data = ";--------------------------------------\n"+
                     ";Declaracion de las variables a usar\n"+
                     ";--------------------------------------\n";
       //emite(data);
       for (int i = 0; i < cmp.ts.getTamaño(); i++) {
           if ( cmp.ts.buscaComplex(i).equals("id"))
                if ( cmp.ts.buscaTipo(i).equals("integer") 
                        || cmp.ts.buscaTipo(i).equals("real"))
                {
                     data += cmp.ts.buscaLexema(i) + "       dword  ?" + "\n";
                      data += "etiq"+cmp.ts.buscaLexema(i)+"   " + "BYTE \""+
                           cmp.ts.buscaLexema(i)+ "= \" ,0\n"  ;
                }
       }
       
       String code = ".code \nmain PROC \n";
              code += ";--------------------------------------\n"+
                     ";Codigo de ensamblador\n"+
                     ";--------------------------------------\n";
       
       code += generando();
       String llamadas = ";--------------------------------------\n"+
                     ";Aqui se imprimen las variables\n"+
                     ";--------------------------------------\n";
       
       for (int i = 0; i < cmp.ts.getTamaño(); i++) {
           if ( cmp.ts.buscaComplex(i).equals("id"))
                if ( cmp.ts.buscaTipo(i).equals("integer") 
                        || cmp.ts.buscaTipo(i).equals("real"))
                {
                     llamadas +=   "mov edx,OFFSET etiq"+ cmp.ts.buscaLexema(i)+"\n";
                    
                       llamadas +=  "call WriteString\n";
                        llamadas += "mov eax, " + cmp.ts.buscaLexema(i) + "\n" 
                             + "call WriteDec \ncall Crlf" + "\n";
                }
       }
       
       llamadas +="call WaitMsg\n     exit\nmain ENDP\n" + "END main";
       
       emite(plantilla + data + code + llamadas); 
    }
   
   //-------------------------------------------------------------------------
   //Metodo que separa por bloques basicos
   private String generando (){
       String codigo = "";
       ArrayList<Cuadruplo> bloqueCuadruplos = new ArrayList <Cuadruplo>();
       
       
       int j = 1;
       int cont=0;
       for (int i = 0; i < cmp.cu.cuadruplos.size(); i++) {
           if ( cmp.cu.cuadruplos.get(i).bloqueBasico == j ){               
               bloqueCuadruplos.add(cmp.cu.cuadruplos.get(i));
              //emite("se agrega cuadriplo: "+i+" a bloqueCuadruplos bloquebasico: "+j);
           }
           else{
               codigo += Bloques(bloqueCuadruplos); 
               bloqueCuadruplos.clear();
               //codigo+=j;
               j++;
               bloqueCuadruplos.add(cmp.cu.cuadruplos.get(i)); 
                //emite("se agrega cuadriplo: "+i+" a bloqueCuadruplos bloquebasico: "+j);
               if( i== cmp.cu.cuadruplos.size()-1 ){
                   codigo += Bloques(bloqueCuadruplos);
                    
                   bloqueCuadruplos.clear();
                   // codigo+=j;
               }
           }
            if(i==cmp.cu.cuadruplos.size()-1)
            {
                   codigo += Bloques(bloqueCuadruplos); 
            }
               
           
       }
       
       return codigo;
   }
   
   //-------------------------------------------------------------------------
   //Trabaja cada bloque basico individualmente para crear el codigo objeto
   private String Bloques ( ArrayList <Cuadruplo> bloque ){
       String codigo = "";
       
       for (int i = 0; i < bloque.size(); i++) {        
           
           if ( bloque.get(i).op.equals("+") ){   
               if(!decterminadas)
               {
                   descriptor.HazDisponible();
                   decterminadas=true;
               }
                
               if ( descriptor.original( retorna( bloque.get(i).arg2) ) ){
                  
                    if(descriptor.noNumerosTempSinnNum(retorna(bloque.get(i).arg2)))
                    {
                   codigo += descriptor.lleno();
                   String reg = descriptor.Disponible();
                    
                   codigo += "mov "+ reg + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg2)) 
                           + "\n";
                   codigo += "add " + reg + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg1) )
                           + "\n";
                   descriptor.setPos ( retorna(bloque.get(i).resultado), reg);
                    }
                    else
                    {
                           codigo += "add " + descriptor.getPos (retorna(bloque.get(i).arg2)) 
                           + ", "   + descriptor.getPos (retorna(bloque.get(i).arg1) )
                           + "\n";
                           descriptor.setPos ( retorna(bloque.get(i).resultado), 
                           descriptor.getPos (retorna(bloque.get(i).arg2)));
                    
                    }
               }
               else{
                   if(!descriptor.noNumerosTempSinnNum(retorna(bloque.get(i).arg2)))
                   {
                      
                       codigo += "add " + descriptor.getPos (retorna(bloque.get(i).arg2) ) + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg1) )
                           + "\n";
                       descriptor.setPos ( retorna(bloque.get(i).resultado), 
                       descriptor.getPos (retorna(bloque.get(i).arg2) ));
                   }
                   else
                   {
                       
                      codigo += descriptor.lleno();
                   String reg = descriptor.Disponible();
                   codigo += "mov "+ reg + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg2)) 
                           + "\n";
                   
                   
                   codigo += "add " + reg + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg1) )
                           + "\n";
                    descriptor.setPos ( retorna(bloque.get(i).resultado), reg);
                   }
                   
               
               }
               if (!descriptor.noNumerosTemp(retorna(bloque.get(i).arg1))){
                  descriptor.DefaultSinMov(retorna(bloque.get(i).arg1));                     
               }
              
           }
            if ( bloque.get(i).op.equals("*") ){   
               if(!decterminadas)
               {
                   descriptor.HazDisponible();
                   decterminadas=true;
               }
                
               if ( descriptor.original( retorna( bloque.get(i).arg2) ) ){
                   
                    if(descriptor.noNumerosTempSinnNum(retorna(bloque.get(i).arg2)))
                    {
                   codigo += descriptor.lleno();
                   String reg = descriptor.Disponible();
                   codigo += "mov "+ reg + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg2)) 
                           + "\n";
                   codigo += "mul " + reg + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg1) )
                           + "\n";
                   descriptor.setPos ( retorna(bloque.get(i).resultado), reg);
                    }
                    else
                    {
                           codigo += "mul " + descriptor.getPos (retorna(bloque.get(i).arg2)) 
                           + ", "   + descriptor.getPos (retorna(bloque.get(i).arg1) )
                           + "\n";
                           descriptor.setPos ( retorna(bloque.get(i).resultado), 
                           descriptor.getPos (retorna(bloque.get(i).arg2)));
                    
                    }
               }
               else{
                   if(!descriptor.noNumerosTempSinnNum(retorna(bloque.get(i).arg2)))
                   {
                       codigo += "mul " + descriptor.getPos (retorna(bloque.get(i).arg2) ) + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg1) )
                           + "\n";
                       descriptor.setPos ( retorna(bloque.get(i).resultado), 
                       descriptor.getPos (retorna(bloque.get(i).arg2) ));
                   }
                   else
                   {
                      codigo += descriptor.lleno();
                   String reg = descriptor.Disponible();
                   codigo += "mov "+ reg + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg2)) 
                           + "\n";
                   
                   
                   codigo += "mul " + reg + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg1) )
                           + "\n";
                    descriptor.setPos ( retorna(bloque.get(i).resultado), reg);
                   }
                   
               
               }
               if (!descriptor.noNumerosTemp(retorna(bloque.get(i).arg1))){
                  descriptor.DefaultSinMov(retorna(bloque.get(i).arg1));                     
               }
              
           }
           
              if ( bloque.get(i).op.equals(":=") ){
                  if(!decterminadas)
               {
                   descriptor.HazDisponible();
                   decterminadas=true;
               }
                   if(descriptor.noNumerosTemp(retorna(bloque.get(i).arg1))&&
                      descriptor.noNumerosTemp(retorna(bloque.get(i).resultado))&&
                           (!retorna(bloque.get(i).arg1).equals(descriptor.getPos(retorna(bloque.get(i).arg1)))
                           ))
               {
                  
               codigo += "mov  "+ retorna(bloque.get(i).arg1) + ", " 
                           + descriptor.getPos(retorna(bloque.get(i).arg1))
                           + "\n";
              
               }
                  
                    if ( descriptor.original( retorna( bloque.get(i).resultado) ) ){
                       
                        String x ="";
                        if(!descriptor.noNumerosTempSinnNum(retorna(bloque.get(i).arg1)))
                        {
                         x=descriptor.getPos (retorna(bloque.get(i).arg1)) ;
                      
                        }
                        else
                        {
                          codigo += descriptor.lleno();      
                          x=descriptor.getPos (retorna(bloque.get(i).arg1)) ;
                      
                        
                        }
                        
                        
                        
                        if(descriptor.noNumerosTempSinnNum(retorna(bloque.get(i).arg1)))
                        {
                          String reg = descriptor.Disponible();
                         if(!reg.equals(x))
                         codigo += "mov "+ reg + ", " 
                           + x 
                           + "\n";
                         descriptor.setPos ( retorna(bloque.get(i).resultado), reg);
                        
                        }
                        else
                        {
                            descriptor.setPos ( retorna(bloque.get(i).resultado), x);  
                        }
                       
                    }
                    else
                    { 
                         
                         codigo += "mov "+ descriptor.getPos ( retorna(bloque.get(i).resultado)) + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg1)) 
                           + "\n";
                        
                    }
              }
              if ( bloque.get(i).op.equals("pop") ){
                  
                   String reg = descriptor.Disponible();
                   emite("entro a pop "+reg);
                   codigo += "mov "+ reg + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg1)) 
                           + "\n";
                   codigo += "push " + reg 
                           + "\n";
                   Pilapops.push(reg);
                   
              }
             
              if ( bloque.get(i).op.equals("param") ){
                  codigo+="pop "+Pilapops.pop()+ "\n";
              }
              
               if ( bloque.get(i).op.equals("==")|| bloque.get(i).op.equals("<")
                 || bloque.get(i).op.equals("<=")|| bloque.get(i).op.equals(">=")
                 || bloque.get(i).op.equals(">")){
                   
                   if ( descriptor.original( retorna( bloque.get(i).arg1) ) ){
                   codigo += descriptor.lleno();
                   String reg = descriptor.Disponible();
                   codigo += "mov "+ reg + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg1)) 
                           + "\n";
                   codigo += "cmp " + reg + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg2) )
                           + "\n";
                    codigo += descriptor.Default( );
                    codigo += saltos(bloque.get(i).op)+ " " +bloque.get(i).resultado+"\n";
           
               }
               else{
               codigo += "cmp " + descriptor.getPos (retorna(bloque.get(i).arg1) ) + ", " 
                           + descriptor.getPos (retorna(bloque.get(i).arg2) )
                           + "\n";
                codigo += descriptor.Default( );
                codigo += saltos(bloque.get(i).op)+ " " +bloque.get(i).resultado+"\n";
             
               
               }
               if (!descriptor.noNumerosTemp(retorna(bloque.get(i).arg1))){
                  descriptor.DefaultSinMov(retorna(bloque.get(i).arg1));                     
               }
               if (!descriptor.noNumerosTemp(retorna(bloque.get(i).resultado))){
                   descriptor.DefaultSinMov(retorna(bloque.get(i).resultado));
               }      
                   
               }
           if ( bloque.get(i).op.equals("") ){
               codigo += bloque.get(i).resultado + ": \n";
           }
           
           else if ( bloque.get(i).op.equals("goto") ){  
               codigo += descriptor.Default( );
               codigo += "jmp " + bloque.get(i).resultado + "\n";
           }
           
           if ( i == bloque.size()-1 && !bloque.get(i).op.equals("goto")){
                codigo += descriptor.Default( );
           }
       }
       
       
       return codigo;
       
   }
   
   
   //-------------------------------------------------------------------------
   //Metodo que nos retorna el tipo de salto en ensamblador
   public String saltos (String salto){
       if(salto.equals(">"))
           return "jg";
       else if(salto.equals(">="))
           return "jge";
        else if(salto.equals("<"))
           return "jl";
        else if(salto.equals("<="))
           return "jle";
        else
           return "je";
   }
   //-------------------------------------------------------------------------
   //Metodo que vacia la tabla de simbolos
   public void agregar (){
       //emite("entra agregar");
       for (int i = 0; i < cmp.ts.getTamaño() ; i++) {
           //emite("entra for i= "+i);
           //emite("i= "+cmp.ts.buscaTipo(i));
           if ( cmp.ts.buscaTipo(i).equals("id") 
                   || cmp.ts.buscaTipo(i).equals("integer") 
                   || cmp.ts.buscaTipo(i).equals("real") ){
               //emite("se va a agregar al descriptor "+cmp.ts.buscaLexema(i));
               descriptor.insertar(cmp.ts.buscaLexema(i));
           }
       }
       
       for (int i = 0; i < cmp.temporales.size(); i++) {
          // emite("se va a agregar al descriptor "+cmp.temporales.get(i));
           descriptor.insertar(cmp.temporales.get(i));
       }
   }
   
   //-------------------------------------------------------------------------
   //Metodo que regresa el lexema
   public String retorna ( String valor ){
      String retornado = "";
      emite("entra a retorna");
      char [] cad = valor.toCharArray();
      
      if (cad[0] == '['){
          emite("cad[0]="+cad[0]);
           for (int i = 1; i < cad.length-1; i++) {
              retornado += cad[i];
          }
           int pos = Integer.parseInt(retornado);
           emite("en retorna esto trae pos:"+pos);
           emite(" esto retornara "+cmp.ts.buscaLexema(pos));
           return cmp.ts.buscaLexema(pos);
       }
      else {
          return valor;
      }       
       
   }
               
    }

