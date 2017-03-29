/*:-----------------------------------------------------------------------------
 *:                       INSTITUTO TECNOLOGICO DE LA LAGUNA
 *:                     INGENIERIA EN SISTEMAS COMPUTACIONALES
 *:                         LENGUAJES Y AUTOMATAS II           
 *: 
 *:        SEMESTRE: ______________            HORA: ______________ HRS
 *:                                   
 *:               
 *:    # Clase con la funcionalidad del Generador de COdigo Intermedio
 *                 
 *:                           
 *: Archivo       : GenCodigoInt.java
 *: Autor         : Fernando Gil  
 *: Fecha         : 03/SEP/2014
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

import java.util.Stack;

public class GenCodigoInt {
 
    public static final int  NIL = 0;
    private String     preAnalisis;
    private String     prefijoString="";
    private String     tempultimo="";
    private String     sigultimo="";
    private String     sentenciaL="";
    private boolean    masdeuno=false;
    private final Compilador cmp;
    private int        consecutivoTemp; 
    private int        consecutivoEtiq;
    private int        consecutivo=0;
    public  ArrayList<String> temporales = new ArrayList<String>();
    
    ArrayList<ElemBuffer> prefijo = new ArrayList <> ();
    public Stack <ElemBuffer> Pila=new Stack<>();
     public Stack <String> Pilaprueba;
    //--------------------------------------------------------------------------
    // Constructor de la clase, recibe la referencia de la clase principal del 
    // compilador.
    //
	public GenCodigoInt ( Compilador c ) {
        cmp = c;
    }
    // Fin del Constructor
    //--------------------------------------------------------------------------
	
    public void generar () {
        consecutivoTemp = 1;
        consecutivoEtiq = 1;
        preAnalisis = cmp.be.preAnalisis.complex;
        CLASS ();
    } 
    
    //--------------------------------------------------------------------------
    private String tempnuevo () {
      return "t" + consecutivoTemp++;    
    }
    
    //--------------------------------------------------------------------------
    
    private String etiqnueva () {
      return "etiq" + consecutivoEtiq++;    
    }
    
    //--------------------------------------------------------------------------
    
    
    private void emite ( String c3d ) {
     cmp.erroresListener.mostrarCodInt( c3d + "\n" );
    }
    
    //--------------------------------------------------------------------------
    
    
    private void cod3Direcciones ( ArrayList<ElemBuffer> prefij ) {
      
        int i = 0,ap=0,bp=0;
        
      while ( prefij.get(i).operador )
      {
          ElemBuffer a = prefij.get(i + 1);
          ElemBuffer b = prefij.get(i + 2);
          if (a.operador || b.operador)
          {
              if (a.operador)
                  i ++;
              else
                  i +=2;
          }
          else
          {
              
              String t = tempnuevo ();
              Linea_TS li = new Linea_TS();
              li.setLExema(t);
              li.setComplex(t);
                cmp.ts.insertar(li);
              tempultimo=t;
              temporales.add(t);
              ap=cmp.ts.buscar(a.elemento);
              bp=cmp.ts.buscar(b.elemento);
              if(ap==0&&bp==0)
              {  emite ( t + ":=" + a.elemento + prefijo.get(i).elemento +b.elemento);
                  cmp.cu.insertar(new Cuadruplo(prefijo.get(i).elemento,a.elemento ,b.elemento, t));
              }
              else  if(ap==0&&bp!=0)
              { emite ( t + ":=" + a.elemento + prefijo.get(i).elemento +"["+bp+"]");
                cmp.cu.insertar(new Cuadruplo(prefijo.get(i).elemento,a.elemento ,"["+bp+"]", t));
              }
              else  if(ap!=0&&bp==0)
              { emite ( t + ":=" + "["+ap+"]" + prefijo.get(i).elemento +b.elemento);
                cmp.cu.insertar(new Cuadruplo(prefijo.get(i).elemento,"["+ap+"]" ,b.elemento, t));
              }
              else
              {emite ( t + ":=" + "["+ap+"]" + prefijo.get(i).elemento +"["+bp+"]");
               cmp.cu.insertar(new Cuadruplo(prefijo.get(i).elemento,"["+ap+"]" ,"["+bp+"]", t));
              }
              
              prefijo.remove ( a );
              prefijo.remove ( b );
              prefijo.get( i ).elemento = t;
              prefijo.get( i ).operador = false;
              
              i = 0;
              
          }
          
      }
    }
    public void ConvertirAprefijo (  ){
        boolean parentesis=false;
        Stack <ElemBuffer> auxP = new Stack<>();
        Stack <ElemBuffer> prefijoInv = new Stack<>();
        Stack <ElemBuffer> pilaParentesis = new Stack<>();
        Stack <ElemBuffer> pilaAUX = new Stack<>();
        ElemBuffer actual;
        ElemBuffer actualP;
       
        while ( !Pila.empty()){
            
            actual = (ElemBuffer) Pila.pop();
            
            if (parentesis==false){
            if ( !actual.operador )
            {   prefijoInv.push(actual);
           
            }
            else
            {
                if(")".equals(actual.elemento))
                        parentesis=true;
                else
                if(auxP.empty()){ 
                
                auxP.push(actual); }
                else
                {
                    
                    actualP=auxP.pop();
                    
                    if(actual.jerarquia<actualP.jerarquia)
                    {   boolean jmayor=true;
                    
                        while(jmayor)
                        {
                             
                            prefijoInv.push(actualP);
                            actualP=new ElemBuffer("",true,0);
                            if(!auxP.empty())
                            {
                                actualP=auxP.pop();
                                
                                if(actual.jerarquia>=actualP.jerarquia)
                                    jmayor=false;
                            }
                            else
                                jmayor=false;
                        }
                        if (actualP.elemento!="")
                        {auxP.push(actualP);
                   
                         auxP.push(actual);
                   
                    }else {auxP.push(actual);
                    }
                    }
                    else
                    { auxP.push(actualP);
                    
                         auxP.push(actual);
                    ;
                    }
                }
            }
            }
                else
                {  
                      parentesis=false;
                      int contparentesis=0;
                       
                       if(")".equals(actual.elemento))
                                contparentesis++;
                       pilaParentesis.push(actual);
                       
                        boolean noparequecierra=true;
                        while (noparequecierra)
                        {
                            actual=Pila.pop();
                            
                            if(")".equals(actual.elemento))
                                contparentesis++;
                            
                            if("(".equals(actual.elemento))
                            { if(contparentesis==0)
                                {noparequecierra=false; break;}
                                else contparentesis--;  pilaParentesis.push(actual);
                            }
                            else{
                               pilaParentesis.push(actual);}
                            
                            
                            
                        }
                        pilaParentesis= ConvierteAPrefijobis(pilaParentesis);
                         while(!pilaParentesis.empty())
                {
                    pilaAUX.push(pilaParentesis.pop());
                }
                    while(!pilaAUX.empty())
                {
                    prefijoInv.push(pilaAUX.pop());
                }
                    while(!pilaParentesis.empty())
                {
                    prefijoInv.push(pilaParentesis.pop());
                }
                    
                }
            
            }
            if(!auxP.empty())
            {
                while(!auxP.empty())
                {
                    prefijoInv.push(auxP.pop());
                }
            }    
            
              
              while(!prefijoInv.empty())
                {
                    actualP=prefijoInv.pop();
                    
                    prefijo.add(actualP);
                    prefijoString+=actualP.elemento;
                    
                }
              cod3Direcciones(prefijo);
              
               prefijo = new ArrayList <> ();
               Pila=new Stack<>();
               prefijoString="";
        }
            
    
    public Stack <ElemBuffer> ConvierteAPrefijobis(Stack <ElemBuffer> pila)
    {
        
        boolean parentesis=false;
         Stack <ElemBuffer> auxP2 = new Stack<>();
         Stack <ElemBuffer> prefijoInv2 = new Stack<>();
        Stack <ElemBuffer> pilaParentesis2 = new Stack<>();
        Stack <ElemBuffer> pilaAUX1 = new Stack<>();
        Stack <ElemBuffer> pilaAUX2 = new Stack<>();
        ElemBuffer actual;
        ElemBuffer actualP;
        while ( !pila.empty()){
            pilaAUX1.push(pila.pop());
        }
        pila=pilaAUX1;
        while ( !pila.empty()){
            
            actual = (ElemBuffer) pila.pop();
            
            if (parentesis==false){
            if ( !actual.operador )
            {   prefijoInv2.push(actual);
            
            }
            else
            {
                if(")".equals(actual.elemento))
                        parentesis=true;
                else
                if(auxP2.empty()){ 
                
                auxP2.push(actual); }
                else
                {
              
                    actualP=auxP2.pop();
                    
                    if(actual.jerarquia<actualP.jerarquia)
                    {   boolean jmayor=true;
                   
                        while(jmayor)
                        {
                            
                            prefijoInv2.push(actualP);
                            actualP=new ElemBuffer("",true,0);
                            if(!auxP2.empty())
                            {
                                actualP=auxP2.pop();
                               
                                if(actual.jerarquia>=actualP.jerarquia)
                                    jmayor=false;
                            }
                            else
                                jmayor=false;
                        }
                        if (actualP.elemento!="")
                        {auxP2.push(actualP);
                   
                         auxP2.push(actual);
                   
                    }else {auxP2.push(actual);
                    }
                    }
                    else
                    { auxP2.push(actualP);
                    
                         auxP2.push(actual);
                   
                    }
                }
            }
            }
                else
                {   //error("Entra a parentesis=true ->"+actual.elemento);
                      parentesis=false;
                      int contparentesis=0;
                       
                       if(")".equals(actual.elemento))
                                contparentesis++;
                       pilaParentesis2.push(actual);
                       
                        boolean noparequecierra=true;
                        while (noparequecierra)
                        {
                            actual=pila.pop();
                           
                            if(")".equals(actual.elemento))
                                contparentesis++;
                            
                            if("(".equals(actual.elemento))
                            { if(contparentesis==0)
                                {noparequecierra=false; break;}
                                else contparentesis--;  pilaParentesis2.push(actual);
                            }
                            else{
                                pilaParentesis2.push(actual);}
                            
                            
                            
                        }
                        pilaParentesis2= ConvierteAPrefijobis(pilaParentesis2);
                       while(!pilaParentesis2.empty())
                {
                    pilaAUX2.push(pilaParentesis2.pop());
                }
                    while(!pilaAUX2.empty())
                {
                    actual=pilaAUX2.pop();
                   
                    prefijoInv2.push(actual);
                }
                    
                }
            
            }
            if(!auxP2.empty())
            {
                while(!auxP2.empty())
                {
                    prefijoInv2.push(auxP2.pop());
                }
            }    
            return prefijoInv2;
    }
    //--------------------------------------------------------------------------
   
    //--------------------------------------------------------------------------
    // Autor: Alan
    // primeros ( CLASS ) = { type }
    private void CLASS(  ) {
       
        if ( preAnalisis.equals( "type" ) ){
            
            /*
        CLASS → type
                id = class  
		private        
                    DECLARACIONES
                    DECS_METODOS
                public
                    DECLARACIONES
		    DECS_METODOS
-		    end ;

		implementation
                    IMPL_METODOS
		
		Execution  
		    begin
		        LISTA_SENTENCIAS
		    end .

        */
            emparejar("type");
            emparejar("id");
            emparejar("oprel");
            emparejar("class");
            emparejar("private");
            DECLARACIONES( );
            DECS_METODOS( );
            emparejar ( "public" );
            DECLARACIONES(  );
            DECS_METODOS(  );
            emparejar( "end" );
            emparejar( ";" );
            emparejar( "implementation" );
            IMPL_METODOS(  );
            emparejar( "execution" );
            emparejar( "begin" );
            LISTA_SENTENCIAS(  );
            emparejar( "end" );
            emparejar( "." );
                       
        }
        else {
            error( "ERROR EN EL PROCEDURE [CLASS]. " +
                   "Se esperaba type. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
    }
    //--------------------------------------------------------------------------
    // Autor: Alan
    // primeros ( DECLARACIONES ) = { id ,Empty }
    private void DECLARACIONES ( ) {
       
         Atributos LISTA_ID=new Atributos();
        if ( preAnalisis.equals( "id" ) ) {
            // DECLARACIONES →  LISTA_ID   :  TIPO  ; DECLARACIONES
            LISTA_ID( LISTA_ID );
            emparejar( ":" );
            TIPO(  );
            emparejar( ";" );
            DECLARACIONES(  );
            
        }        
    }
    //--------------------------------------------------------------------------
    // Autor: Alan
    // primeros ( LISTA_ID  ) = { id }
    private void LISTA_ID( Atributos LISTA_ID ) {
       
         Linea_BE id = new Linea_BE();
         Atributos LISTA_ID2=new Atributos();
        if ( preAnalisis.equals( "id" ) ) {

            // LISTA_ID → id  DIMENSION  LISTA_ID2
            id = cmp.be.preAnalisis;
            emparejar( "id" );
            if (LISTA_ID.encabezado) {
            emite( "pop " + "["+id.entrada+"]");
             cmp.cu.insertar(new Cuadruplo("pop", "["+id.entrada+"]", "", ""));
            LISTA_ID2.encabezado=true;
           
            }
             
            DIMENSION(  );
            LISTA_ID2( LISTA_ID2 );
        }
        else {
            error( "ERROR EN EL PROCEDURE [LISTA_ID]. " +
                   "Se esperaba un identificador. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
    }
    //--------------------------------------------------------------------------
    // Autor: Alan
    // primeros ( LISTA_ID2 ) = { Coma, Empty }
    private void LISTA_ID2(Atributos LISTA_ID2 ) {
             Atributos LISTA_ID21=new Atributos();
        if ( preAnalisis.equals( "," ) ) {
            Linea_BE id = new Linea_BE();
            // LISTA_ID2 → , id  DIMENSION  LISTA_ID2
            emparejar( "," );
             id = cmp.be.preAnalisis;
            emparejar( "id" );
           if (LISTA_ID2.encabezado) {
            emite( "pop " + "["+id.entrada+"]");
            cmp.cu.insertar(new Cuadruplo("pop", "["+id.entrada+"]", "", ""));
            LISTA_ID21.encabezado=true;
           
            }
            DIMENSION(  );
            LISTA_ID2( LISTA_ID21 );
            
        }
        else {
            // LISTA_ID2 →  ϵ 
        }
    }
    //--------------------------------------------------------------------------
    // Autor: Alan
    // primeros ( DIMENSION ) = { [, Empty }
    private void DIMENSION(   ) {
                
        if (preAnalisis.equals( "[" ) ) {
            
            // DIMENSION → [ num ] 
            emparejar( "[" );
            emparejar( "num" );
            emparejar( "]" );
            
        }
        else {
            // DIMENSION → ϵ
                    
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Aimee
    // primeros ( TIPO ) = { integer, real, string  }
    private void TIPO(   ) {
       
        if ( preAnalisis.equals( "integer" ) ) {
        
            // TIPO → integer
            emparejar( "integer" );
            
        }
        else if ( preAnalisis.equals( "real" ) ) {
        
            // TIPO → real
            emparejar( "real" );
           
        }
        else if ( preAnalisis.equals( "string" ) ) {
        
            // TIPO → string
            emparejar( "string" );
            
        }
        else {
            error( "ERROR EN EL PROCEDURE [TIPO]. " +
                   "Se esperaba integer, real o string. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Aimee
    // primeros ( DECS_METODOS ) = {  function, Empty }
    private void DECS_METODOS(   ) {
       
        if (preAnalisis.equals( "function" ) ) {
            
            // DECS_METODOS → ENCAB_METODO  DECS_METODOS
            ENCAB_METODO(  );
            DECS_METODOS(  );
            
            
        }
        else {
            // DECS_METODOS → ϵ 
           
        }
        
        
    }
    //--------------------------------------------------------------------------
    // Autor: Aimee
    // primeros ( ENCAB_METODO ) = { function }
    private void ENCAB_METODO(   ) {
       
         Linea_BE id = new Linea_BE();
          Atributos LISTA_PARAMETROS=new Atributos();
       if ( preAnalisis.equals( "function" ) ) {
            
            // ENCAB_METODO → function  id ( LISTA_PARAMETROS )  :  TIPO_METODO ; 
            emparejar( "function" );
            id = cmp.be.preAnalisis;
            emparejar( "id" );
            //ASM
            emite("proc " + id.lexema);
            cmp.cu.insertar(new Cuadruplo("proc", id.lexema, "", ""));
            LISTA_PARAMETROS.encabezado=true;
            //
            emparejar( "(" );
            LISTA_PARAMETROS( LISTA_PARAMETROS );
            emparejar( ")" );
            emparejar( ":" );
            TIPO_METODO(  );
            emparejar( ";" );
            
            }
        else {
             error( "ERROR EN EL PROCEDURE [ENCAB_METODO]. " +
                   "Se esperaba la palabra reservada function. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Aimee
    // primeros ( LISTA_PARAMETROS ) = { id, Empty }
    private void LISTA_PARAMETROS(Atributos LISTA_PARAMETROS   ) {
         Atributos LISTA_ID=new Atributos();
         Atributos LISTA_PARAMETROS2=new Atributos();
        if ( preAnalisis.equals( "id" ) ) {
            
            // LISTA_PARAMETROS →  LISTA_ID   :  TIPO   LISTA_PARAMETROS2
            //ASM
            if (LISTA_PARAMETROS.encabezado)
            {LISTA_ID.encabezado=true;
             LISTA_PARAMETROS2.encabezado=true;
            }
            //
            LISTA_ID( LISTA_ID );
            emparejar( ":" );
            TIPO(  );
            LISTA_PARAMETROS2( LISTA_PARAMETROS2 );    
            }
        else {
            // LISTA_PARAMETROS →   ϵ 
            }
          
        
        
    }
    //--------------------------------------------------------------------------
    // Autor: Aimee
    // primeros ( LISTA_PARAMETROS2 ) = { ; , Empty }
    private void LISTA_PARAMETROS2(  Atributos LISTA_PARAMETROS2 ) {
        Atributos LISTA_ID=new Atributos();
         Atributos LISTA_PARAMETROS21=new Atributos();
        
        if ( preAnalisis.equals( ";" ) ) {
             
            // LISTA_PARAMETROS2  →   ;  LISTA_ID   :  TIPO   LISTA_PARAMETROS2
            emparejar( ";" );
            //ASM
            if (LISTA_PARAMETROS2.encabezado)
            {LISTA_ID.encabezado=true;
             LISTA_PARAMETROS21.encabezado=true;
            }
            //
            LISTA_ID(LISTA_ID  );
            emparejar( ":" );
            TIPO(  );
            LISTA_PARAMETROS2( LISTA_PARAMETROS21 );
            }
        else {
            // LISTA_PARAMETROS2  →    ϵ 
            }
        
        
    }
    //--------------------------------------------------------------------------
    // Autor: Aimee
    // primeros ( TIPO_METODO ) = { void, integer, real, string }
    private void TIPO_METODO(   ) {
                
        if ( preAnalisis.equals( "void" ) ) {
            
            // TIPO_METODO → void
            emparejar( "void" );
        }
        else if ( preAnalisis.equals( "integer" ) || 
                  preAnalisis.equals( "real" )    || 
                  preAnalisis.equals( "string" ) ) {
            // TIPO_METODO → TIPO
            TIPO(  );
            }
        else {
            error( "ERROR EN EL PROCEDURE [TIPO_METODO]. " +
                   "Se esperaba void, integer, real o string. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Aimee
    // primeros ( IMPL_METODOS ) = { function ,Empty }
    private void IMPL_METODOS(   ) {
        
        if ( preAnalisis.equals( "function" ) ) {
            
            // IMPL_METODOS → IMPL_METODO    IMPL_METODOS
            IMPL_METODO(  );
            IMPL_METODOS(  );
            
            }
        else {
            
            // IMPL_METODOS → ϵ
             }
         
        
    }
    //--------------------------------------------------------------------------
    // Autor: Susy
    // primeros ( IMPL_METODO ) = { function }
    private void IMPL_METODO(   ) {
       
        if ( preAnalisis.equals( "function" ) ) {
            
            // IMPL_METODO → function  id  : :  SENTENCIA_COMPUESTA  ;
            emparejar( "function" );
            emparejar( "id" );
            emparejar( ":" );
            emparejar( ":" );
            SENTENCIA_COMPUESTA(  );          
            emparejar( ";" );
            
        }
        else {
            error( "ERROR EN EL PROCEDURE [IMPL_METODO]. " +
                   "Se esperaba la palabra reservada function (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Susy
    // primeros ( SENTENCIA_COMPUESTA ) = { begin }
    private void SENTENCIA_COMPUESTA(   ) {
        
        if ( preAnalisis.equals( "begin" ) ) {
            
            // SENTENCIA_COMPUESTA 	→ begin   SENTENCIAS_OPTATIVAS  end 
            emparejar( "begin" );
            SENTENCIAS_OPTATIVAS(  );
            emparejar( "end" );
           
        }
        else {
            error( "ERROR EN EL PROCEDURE [SENTENCIA_COMPUESTA]. " +
                   "Se esperaba la palabra reservada begin (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Susy
    // primeros ( SENTENCIAS_OPTATIVAS ) = { id, begin, if, while, return, Empty }
    private void SENTENCIAS_OPTATIVAS(   ) {
        
        Atributos LISTA_SENTENCIAS = new Atributos();
         if ( preAnalisis.equals( "id" ) || 
             preAnalisis.equals( "begin" ) || 
             preAnalisis.equals( "if" ) || 
             preAnalisis.equals( "while" ) || 
             preAnalisis.equals( "return" ) ) {
            // SENTENCIAS_OPTATIVAS → LISTA_SENTENCIAS
            LISTA_SENTENCIAS(  );
            }
        else {
            // SENTENCIAS_OPTATIVAS → ϵ 
            }
        
        
    }
    //--------------------------------------------------------------------------
    // Autor: Susy
    // primeros ( LISTA_SENTENCIAS ) = { id, begin, if, while, return, Empty }
    private void LISTA_SENTENCIAS(   ) {
         //error( " entra a expresion"+"PRINCIPAL: "+LISTA_SENTENCIAS.principal+"FACTOR2: "+EXPRESION.factor2+"PREANALISIS: "+preAnalisis);
           Atributos SENTENCIA = new Atributos();
        if ( preAnalisis.equals( "id" ) || 
             preAnalisis.equals( "begin" ) || 
             preAnalisis.equals( "if" ) || 
             preAnalisis.equals( "while" ) || 
             preAnalisis.equals( "return" ) ) {
            
            // LISTA_SENTENCIAS → SENTENCIA    LISTA_SENTENCIAS
            SENTENCIA( SENTENCIA );
            LISTA_SENTENCIAS();
            }
    }
    //--------------------------------------------------------------------------
    // Autor: Susy
    // primeros ( SENTENCIA  ) = { id, begin if, while, return }
    private void SENTENCIA(  Atributos SENTENCIA) {
       Linea_BE ida = cmp.be.preAnalisis;
      //("entra a sentencia preanalisis = "+ ida.lexema);
        Atributos SENTENCIA2 = new Atributos();
        Atributos SENTENCIA_COMPUESTA = new Atributos();
        Atributos EXPRESION = new Atributos();
        Atributos SENTENCIA1 = new Atributos();
        Atributos SINO = new Atributos();
        Atributos RETORNO = new Atributos();
        Linea_BE id = new Linea_BE();
        
        if ( preAnalisis.equals( "begin" ) ) {
            
            // SENTENCIA  → SENTENCIA_COMPUESTA ;
            SENTENCIA_COMPUESTA(  );
            emparejar( ";" );
            
        } 
        else if ( preAnalisis.equals( "id" ) ) {
            
            // SENTENCIA  → id  SENTENCIA2  ;
            id = cmp.be.preAnalisis;
            emparejar ( "id" );
             // ("entra por id : "+ id.lexema+ " ."+id.entrada);
            sentenciaL="["+id.entrada+"]";
            SENTENCIA2.h=id.lexema;
            SENTENCIA2( SENTENCIA2 );
            if(SENTENCIA2.Lugar!="")
            {
                // ("sentencia2.Lugar no trae nada esto se va a emitir en id : "+ id.lexema+ " ."+id.entrada);
                emite("["+id.entrada+"]:=" +SENTENCIA2.Lugar);
            cmp.cu.insertar(new Cuadruplo(":=", SENTENCIA2.Lugar, "", "[" + id.entrada+ "]"));
            }
           
            emparejar( ";" );
            
                
            }        
        else if ( preAnalisis.equals( "if" ) ) {
            
            // SENTENCIA  → if  EXPRESION  then SENTENCIA  SINO  
            emparejar( "if" );
            EXPRESION( EXPRESION );
            SENTENCIA.verdadera=etiqnueva();
            SENTENCIA.falsa=etiqnueva();
            emite("if"+EXPRESION.codigo+"goto"+SENTENCIA.verdadera);
            cmp.cu.insertar(new Cuadruplo(EXPRESION.oprel,   EXPRESION.arg1 ,  EXPRESION.arg2 , SENTENCIA.verdadera));
            emite("goto "+SENTENCIA.falsa);
            cmp.cu.insertar(new Cuadruplo("goto", "", "", SENTENCIA.falsa));
            emparejar( "then" );
            // A.S.
               emite(SENTENCIA.verdadera+":");
               cmp.cu.insertar(new Cuadruplo("", "", "", SENTENCIA.verdadera));
            SENTENCIA( SENTENCIA );
            //Accion Semantica 2
            SINO.verdadera = SENTENCIA.falsa;
            //Fin Accion Semantica 2
            
            SINO(SINO);
            
            
            
            }
        else if ( preAnalisis.equals( "while" ) ) {
            
            // SENTENCIA  → while   EXPRESION  do  SENTENCIA   
             //A. S.
            if(SENTENCIA.verdadera==""&&sigultimo=="")
            {   SENTENCIA.inicio = etiqnueva();
                    emite(SENTENCIA.inicio + ":");
                    cmp.cu.insertar(new Cuadruplo("", "", "", SENTENCIA.inicio));
            }
            else if(SENTENCIA.verdadera!="")
                SENTENCIA.inicio=SENTENCIA.verdadera;
            else if(sigultimo!="")
                SENTENCIA.inicio=sigultimo;
                 
            
                    //
            emparejar( "while" );
            EXPRESION( EXPRESION );
            SENTENCIA.verdadera=etiqnueva();
            SENTENCIA.falsa=etiqnueva();
            emite("if "+EXPRESION.codigo+"goto "+SENTENCIA.verdadera);
            cmp.cu.insertar(new Cuadruplo(EXPRESION.oprel,   EXPRESION.arg1  ,  EXPRESION.arg2 , SENTENCIA.verdadera));
            emite("goto "+SENTENCIA.falsa);
            cmp.cu.insertar(new Cuadruplo("goto", "", "", SENTENCIA.falsa));
            emparejar( "do" );
            //A.S.
                      emite(SENTENCIA.verdadera+":");
                      cmp.cu.insertar(new Cuadruplo("", "", "", SENTENCIA.verdadera));
                    //
            SENTENCIA( SENTENCIA );
            //A.S.
                    emite("goto "+SENTENCIA.inicio);
                    cmp.cu.insertar(new Cuadruplo("goto", "", "", SENTENCIA.inicio));    
                    emite(SENTENCIA.falsa + ":");
                    cmp.cu.insertar(new Cuadruplo("", "", "", SENTENCIA.falsa));
              //
            
            }
        else if ( preAnalisis.equals( "return" ) ) {
        
            // SENTENCIA  → return  RETORNO   ;
            emparejar( "return" );
            RETORNO( RETORNO );
            emparejar( ";" );
            }
        
        else {
            error( "ERROR EN EL PROCEDURE [SENTENCIA]. " +
                   "Se esperaba un identificador, begin, if, while o return (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Nuria
    // primeros ( SENTENCIA2 ) = { [, opasig, (  }
    private void SENTENCIA2( Atributos SENTENCIA2 ) {
       //("entra a sentencia2 preanalisis = "+ preAnalisis);
        Atributos VARIABLE = new Atributos();
        Atributos EXPRESION = new Atributos();
        Atributos SENTENCIA_METODO = new Atributos();
        
        if (preAnalisis.equals("[") ) {
            
            // SENTENCIA2 → VARIABLE   opasig   EXPRESION 
            VARIABLE( VARIABLE );
            emparejar( "opasig" );
            EXPRESION( EXPRESION );
            
            }
        else if ( preAnalisis.equals( "opasig" ) ) {
            
            // SENTENCIA2 → opasig  EXPRESION
            emparejar( "opasig" );
            EXPRESION.sentencia2=true;
            EXPRESION( EXPRESION );
           SENTENCIA2.Lugar=EXPRESION.Lugar;
           }
        else if ( preAnalisis.equals( "(" ) ) {
            
            // SENTENCIA2 → SENTENCIA_METODO 
            SENTENCIA_METODO.h=SENTENCIA2.h;
            SENTENCIA_METODO( SENTENCIA_METODO );
            }
        else {
            error( "ERROR EN EL PROCEDURE [SENTENCIA2]. " +
                   "Se esperaba un '[', '=' o '('. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Nuria
    // primeros ( SINO	) = { else, Empty }
    private void SINO( Atributos SINO ) {
        
        
       Atributos SENTENCIA=new Atributos();
        if(preAnalisis.equals("else"))
	{
		emparejar("else");
                //A.S.
                SINO.siguiente=etiqnueva();
                emite("go to "+SINO.siguiente);
                cmp.cu.insertar(new Cuadruplo("goto", "", "", SINO.siguiente));
                emite(SINO.verdadera+":");
                cmp.cu.insertar(new Cuadruplo("", "", "", SINO.verdadera));
               SENTENCIA.verdadera=SINO.verdadera;
               SENTENCIA(SENTENCIA);
		emite(SINO.siguiente+":");
                cmp.cu.insertar(new Cuadruplo("", "", "", SINO.siguiente));
                sigultimo=SINO.siguiente;
	}
        else{
           emite(SINO.verdadera+":");
           cmp.cu.insertar(new Cuadruplo("", "", "", SINO.verdadera));
        }
        
        
    }
    //--------------------------------------------------------------------------
    // Autor: Nuria
    // primeros ( SENTENCIA_METODO ) = { ( }
    private void SENTENCIA_METODO( Atributos SENTENCIA_METODO ) {
        
        Atributos LISTA_EXPRESIONES = new Atributos();
         
        if ( preAnalisis.equals( "(" ) ) {
            
            // SENTENCIA_METODO  → ( LISTA_EXPRESIONES )
            emparejar( "(" );
            LISTA_EXPRESIONES.sentencia_metodo=true;
            LISTA_EXPRESIONES( LISTA_EXPRESIONES );
            emparejar( ")" );
            emite("call "+ SENTENCIA_METODO.h+","+consecutivo);
                 cmp.cu.insertar(new Cuadruplo("call", SENTENCIA_METODO.h, "" + consecutivo, ""));
                 consecutivo=0;
        }
        else {
            error( "ERROR EN EL PROCEDURE [SENTENCIA_METODO]. " +
                   "Se esperaba un ('. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Nuria
    // primeros ( LISTA_EXPRESIONES ) = { id, num, num.num, (, literal, Empty }
    private void LISTA_EXPRESIONES( Atributos LISTA_EXPRESIONES ) {
        
       //error( " entra a lista_expresiones "+"PRINCIPAL: "+LISTA_EXPRESIONES.principal+"FACTOR2: "+LISTA_EXPRESIONES.factor2+"PREANALISIS: "+preAnalisis);
        Atributos EXPRESION = new Atributos();
        Atributos LISTA_EXPRESIONES2 = new Atributos();
        //ASM
            if (LISTA_EXPRESIONES.factor2)
            { EXPRESION.factor2=true;
              LISTA_EXPRESIONES2.factor2=true;
              
            }
             
            if(!LISTA_EXPRESIONES.principal)
            { 
              EXPRESION.principal=false;
              LISTA_EXPRESIONES2.principal=false;
            }
        //
            
            
       if(preAnalisis.equals("num"))
	{
		EXPRESION(EXPRESION);
                
                //ASM
                if (LISTA_EXPRESIONES.factor2||LISTA_EXPRESIONES.sentencia_metodo)
                {consecutivo++;
                 
                }
                if(LISTA_EXPRESIONES.sentencia_metodo)
                {
                    LISTA_EXPRESIONES2.sentencia_metodo=true;
                    emite("param "+EXPRESION.Lugar);
                    cmp.cu.insertar(new Cuadruplo("param",EXPRESION.Lugar, "", ""));
                }
                //
		LISTA_EXPRESIONES2(LISTA_EXPRESIONES2);
                //ASM
                LISTA_EXPRESIONES.Lugar=LISTA_EXPRESIONES2.h;
                //
                
	}
	else
	if(preAnalisis.equals("id"))
	{
                
		EXPRESION(EXPRESION);
                 /*ASM */ if (LISTA_EXPRESIONES.factor2||LISTA_EXPRESIONES.sentencia_metodo)
                 { consecutivo++;
                     
                 }
                 if(LISTA_EXPRESIONES.sentencia_metodo)
                {
                    LISTA_EXPRESIONES2.sentencia_metodo=true;
                    emite("param "+EXPRESION.Lugar);
                    cmp.cu.insertar(new Cuadruplo("param",EXPRESION.Lugar, "", ""));
                }
		LISTA_EXPRESIONES2(LISTA_EXPRESIONES2);
                /*ASM */ LISTA_EXPRESIONES.Lugar=LISTA_EXPRESIONES2.h;
                
		
	}
	else
	if(preAnalisis.equals("num.num"))
	{
		EXPRESION(EXPRESION);
                
                /*ASM */if (LISTA_EXPRESIONES.factor2||LISTA_EXPRESIONES.sentencia_metodo)
                { consecutivo++;
                 
                }
                if(LISTA_EXPRESIONES.sentencia_metodo)
                {
                    LISTA_EXPRESIONES2.sentencia_metodo=true;
                    emite("param "+EXPRESION.Lugar);
                    cmp.cu.insertar(new Cuadruplo("param",EXPRESION.Lugar, "", ""));
                }
		LISTA_EXPRESIONES2(LISTA_EXPRESIONES2);
                /*ASM */LISTA_EXPRESIONES.Lugar=LISTA_EXPRESIONES2.h;
               
	}
	else
	if(preAnalisis.equals("("))
	{
		EXPRESION(EXPRESION); 
                /*ASM */if (LISTA_EXPRESIONES.factor2||LISTA_EXPRESIONES.sentencia_metodo)
                { consecutivo++;
                    
                }
                if(LISTA_EXPRESIONES.sentencia_metodo)
                {
                    LISTA_EXPRESIONES2.sentencia_metodo=true;
                    emite("param "+EXPRESION.Lugar);
                    cmp.cu.insertar(new Cuadruplo("param",EXPRESION.Lugar, "", ""));
                }
		LISTA_EXPRESIONES2(LISTA_EXPRESIONES2);
               /*ASM */ LISTA_EXPRESIONES.Lugar=LISTA_EXPRESIONES2.h;
              
	}
	else if(preAnalisis.equals("literal"))
	{
		EXPRESION(EXPRESION);
                /*ASM */if (LISTA_EXPRESIONES.factor2||LISTA_EXPRESIONES.sentencia_metodo)
                { consecutivo++;
                 
                }
                if(LISTA_EXPRESIONES.sentencia_metodo)
                {
                    LISTA_EXPRESIONES2.sentencia_metodo=true;
                  emite("param "+EXPRESION.Lugar);
                    cmp.cu.insertar(new Cuadruplo("param",EXPRESION.Lugar, "", ""));
                }
		LISTA_EXPRESIONES2(LISTA_EXPRESIONES2);
                /*ASM */LISTA_EXPRESIONES.Lugar=LISTA_EXPRESIONES2.h;
               
	}
        else {
            
            // LISTA_EXPRESIONES → ϵ
            }
      
    }
    //--------------------------------------------------------------------------
    // Autor: Nuria
    // primeros ( LISTA_EXPRESIONES2 ) = { Coma, Empty }
    private void LISTA_EXPRESIONES2( Atributos LISTA_EXPRESIONES2 ) {
        Atributos EXPRESION = new Atributos();
        Atributos LISTA_EXPRESIONES21 = new Atributos();
        
        if ( preAnalisis.equals( "," ) ) {
            
            // LISTA_EXPRESIONES2 →  ,  EXPRESION   LISTA_EXPRESIONES2  
            emparejar( "," );
                //ASM
            if (LISTA_EXPRESIONES2.factor2)
            { EXPRESION.factor2=true;
              LISTA_EXPRESIONES21.factor2=true;
              
            }
             
            if(!LISTA_EXPRESIONES2.principal)
            { 
              EXPRESION.principal=false;
              LISTA_EXPRESIONES21.principal=false;
            }
            if(LISTA_EXPRESIONES2.sentencia_metodo)
                {
                    EXPRESION.sentencia_metodo=true;
                    LISTA_EXPRESIONES21.sentencia_metodo=true;
                    
                }
        //
            EXPRESION( EXPRESION );
          
                consecutivo++;
             if(LISTA_EXPRESIONES2.sentencia_metodo)
                {
                     emite("param "+EXPRESION.Lugar);
                    cmp.cu.insertar(new Cuadruplo("param",EXPRESION.Lugar, "", ""));
                }
            //fin de la accion semantica
            LISTA_EXPRESIONES2( LISTA_EXPRESIONES21 );
          
            }
        else {
            // LISTA_EXPRESIONES2 →  ϵ
            //ASM
           
            
            //
            }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Nuria
    // primeros ( RETORNO ) = { void, id, num, num.num, (, literal }
    private void RETORNO( Atributos RETORNO ) {
        // error( " entra a RETORNO"+"PRINCIPAL: "+RETORNO.principal+"FACTOR2: "+RETORNO.factor2+"PREANALISIS: "+preAnalisis);
        Atributos EXPRESION = new Atributos();
        
        if ( preAnalisis.equals( "void" ) ) {
            
            // RETORNO  → void
            emparejar( "void" );
            emite("return");
            cmp.cu.insertar(new Cuadruplo("return","", "", ""));
            }
        else if ( preAnalisis.equals( "id" ) || 
                  preAnalisis.equals( "num" ) || 
                  preAnalisis.equals( "num.num" ) || 
                  preAnalisis.equals( "(" ) ||
                  preAnalisis.equals( "literal" ) ) {
            
            // RETORNO  → EXPRESION
            EXPRESION( EXPRESION );
            emite("return "+EXPRESION.Lugar);
            cmp.cu.insertar(new Cuadruplo("return",EXPRESION.Lugar, "", ""));
            }
        else {
            error( "ERROR EN EL PROCEDURE [RETORNO]. " +
                   "Se esperaba un identificador, un numero, '(' o una cadena. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
    }
    //--------------------------------------------------------------------------
    // Autor: Nuria
    // primeros ( EXPRESION ) = { id, num, num.num, (, literal }
    private void EXPRESION( Atributos EXPRESION ) {
        //( " entra a expresion"+"PRINCIPAL: "+EXPRESION.principal+"FACTOR2: "+EXPRESION.factor2+"PREANALISIS: "+preAnalisis);
        Atributos EXPRESION_SIMPLE = new Atributos();
        Atributos EXPRESION2 = new Atributos();
        
        if ( preAnalisis.equals( "id" ) || 
             preAnalisis.equals( "num" ) || 
             preAnalisis.equals( "num.num" ) || 
             preAnalisis.equals( "(" ) || 
             preAnalisis.equals( "literal" ) )  {
             //Accion Semantica {1b}
             EXPRESION2.principal=false;
             EXPRESION_SIMPLE.principal=false;
            
            
            if(EXPRESION.factor2)
            { EXPRESION2.factor2=true;
                EXPRESION_SIMPLE.factor2=true;
            }
            //
            // EXPRESION → EXPRESION_SIMPLE  EXPRESION2 
            EXPRESION_SIMPLE( EXPRESION_SIMPLE );
             EXPRESION.Lugar=EXPRESION_SIMPLE.Lugar;
             EXPRESION2.Lugar=EXPRESION_SIMPLE.Lugar;
            
           
            EXPRESION2( EXPRESION2 );
            EXPRESION.codigo=EXPRESION2.codigo;
            EXPRESION.arg1=EXPRESION2.arg1;
            EXPRESION.arg2=EXPRESION2.arg2;
            EXPRESION.oprel=EXPRESION2.oprel;
            if(EXPRESION.sentencia2&&masdeuno)
            { EXPRESION.Lugar=sentenciaL;
              sentenciaL="";
            }
            //Accion Semantica {}
            //error("checa si entra a convertirprefijo expresimple.fac2= "+EXPRESION_SIMPLE.factor2+" expre2.fac2= "+EXPRESION2.factor2+"/n expsim.operacion="+EXPRESION_SIMPLE.operacion+" exp2.op="+ EXPRESION2.operacion);
            if(!EXPRESION_SIMPLE.factor2&&!EXPRESION2.factor2){
             if(EXPRESION_SIMPLE.operacion||EXPRESION2.operacion)
            if ( EXPRESION.principal )
            {
                //error(" esto trae Expresion.Lugar antes de entrar a convertirAprefijo "+EXPRESION.Lugar);
               ConvertirAprefijo (  );
               
               emite(EXPRESION.Lugar+":="+tempultimo);
               cmp.cu.insertar(new Cuadruplo(":=", tempultimo, "", EXPRESION.Lugar));
               EXPRESION.Lugar="";
               masdeuno=false;
            }
            else {//error("no convierte a prefijo no es la expresion princial");
           
            }
            else {
            while ( !Pila.empty())
                Pila.pop();
            
            }
            
            }
             else {
            while ( !Pila.empty())
                Pila.pop();
            //EXPRESION.Lugar="";
            }
           
            
            //Fin Accion Semantica {1}
            
            
        }
        else {
            error( "ERROR EN EL PROCEDURE [RETORNO]. " +
                   "Se esperaba un identificador, un numero, '(' o una cadena. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Nuria
    // primeros ( EXPRESION2 ) = { oprel, Empty }
    private void EXPRESION2( Atributos EXPRESION2 ) {
          //error( " entra a expresion2"+"PRINCIPAL: "+EXPRESION2.principal+"FACTOR2: "+EXPRESION2.factor2+"PREANALISIS: "+preAnalisis);
        Atributos EXPRESION_SIMPLE = new Atributos();
        Linea_BE oprel = new Linea_BE();
        if ( preAnalisis.equals( "oprel" ) ) {
           
            // EXPRESION2 	→ oprel   EXPRESION_SIMPLE
            oprel=cmp.be.preAnalisis;
            emparejar( "oprel" );
             //ASM
            masdeuno=true;
            if(!EXPRESION2.principal)
            { 
              EXPRESION_SIMPLE.principal=false;
            }
            //
            EXPRESION_SIMPLE( EXPRESION_SIMPLE );
            EXPRESION2.factor2=EXPRESION_SIMPLE.factor2;
            EXPRESION2.codigo=EXPRESION2.Lugar+oprel.lexema+EXPRESION_SIMPLE.Lugar;
            EXPRESION2.arg1=EXPRESION2.Lugar;
            EXPRESION2.arg2=EXPRESION_SIMPLE.Lugar;
            EXPRESION2.oprel=oprel.lexema;
           }
        else {
            //error("sale de expr2 "+EXPRESION2.principal);
            // EXPRESION2 	→ ϵ 
           }
        //error( " sale de EXPRESION2 "+"PRINCIPAL: "+EXPRESION2.principal+"FACTOR2: "+EXPRESION2.factor2+"PREANALISIS: "+preAnalisis);
    }
    //--------------------------------------------------------------------------
    // Autor: Alejandro Saucedo Ramirez
    // primeros ( EXPRESION_SIMPLE ) = { id, num, num.num, (, literal }
    private void EXPRESION_SIMPLE( Atributos EXPRESION_SIMPLE ) {
         //error( " entra a expresion_simple "+"PRINCIPAL: "+EXPRESION_SIMPLE.principal+"FACTOR2: "+EXPRESION_SIMPLE.factor2+"PREANALISIS: "+preAnalisis);
        Atributos TERMINO = new Atributos();
        Atributos EXPRESION_SIMPLE2 = new Atributos();
        
        if ( preAnalisis.equals( "id" ) ||
             preAnalisis.equals( "num" ) ||
             preAnalisis.equals( "num.num" ) ||
             preAnalisis.equals( "(" ) ||
             preAnalisis.equals( "literal" ) ) {
            
            // EXPRESION_SIMPLE → TERMINO   EXPRESION_SIMPLE2 
             //ASM
            if(!EXPRESION_SIMPLE.principal)
            { 
              TERMINO.principal=false;
              EXPRESION_SIMPLE2.principal=false;
            }
             if(EXPRESION_SIMPLE.factor2)
            { 
              TERMINO.factor2=true;
              EXPRESION_SIMPLE2.factor2=true;
            }
            //
            TERMINO( TERMINO );
            EXPRESION_SIMPLE.Lugar=TERMINO.Lugar;
           
            EXPRESION_SIMPLE2( EXPRESION_SIMPLE2 );
            
            //ASM
            if (TERMINO.factor2||EXPRESION_SIMPLE2.factor2)
            EXPRESION_SIMPLE.factor2=true;
            if(EXPRESION_SIMPLE2.operacion||TERMINO.operacion)
                EXPRESION_SIMPLE.operacion=true;
            //
           
        }
        else {
            error( "ERROR EN EL PROCEDURE [EXPRESION_SIMPLE]. " +
                   "Se esperaba un identificador, un número o caracter o un '('. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" ); 
        }
        //error( " sale de EXPRESIONSIMLE "+"PRINCIPAL: "+EXPRESION_SIMPLE.principal+"FACTOR2: "+EXPRESION_SIMPLE.factor2+"PREANALISIS: "+preAnalisis);
        
    }
    //--------------------------------------------------------------------------
    // Autor: Alejandro Saucedo Ramirez
    // primeros ( EXPRESION_SIMPLE2 ) = { opsuma, Empty }
    private void EXPRESION_SIMPLE2( Atributos EXPRESION_SIMPLE2 ) {
         //error( " entra a expresion_Simple2 "+"PRINCIPAL: "+EXPRESION_SIMPLE2.principal+"FACTOR2: "+EXPRESION_SIMPLE2.factor2+"PREANALISIS: "+preAnalisis);
        Atributos TERMINO = new Atributos();
        Atributos EXPRESION_SIMPLE21 = new Atributos();
        
        if ( preAnalisis.equals( "opsuma" ) ) {
            
            // EXPRESION_SIMPLE2 → opsuma  TERMINO   EXPRESION_SIMPLE2 
            emparejar( "opsuma" );
            
            //Accion Semantica {7}
            masdeuno=true;
            Pila.push( new ElemBuffer ( "+", true, 1 ));
            EXPRESION_SIMPLE2.operacion=true;
             //ASM
            if(!EXPRESION_SIMPLE2.principal)
            { 
              TERMINO.principal=false;
              EXPRESION_SIMPLE21.principal=false;
            }
            //
            //Fin Accion Semantica {8}
            TERMINO( TERMINO);
            EXPRESION_SIMPLE2(EXPRESION_SIMPLE21);
            //ASM
            if (TERMINO.factor2||EXPRESION_SIMPLE21.factor2)
            EXPRESION_SIMPLE2.factor2=true;
            //
        }
        else {
           // EXPRESION_SIMPLE2 → ϵ  
           
           
        }
        //error( " sale de EXPRSIMPLE2 "+"PRINCIPAL: "+EXPRESION_SIMPLE2.principal+"FACTOR2: "+EXPRESION_SIMPLE2.factor2+"PREANALISIS: "+preAnalisis);
        
    }
    //--------------------------------------------------------------------------
    // Autor: Alejandro Saucedo Ramirez
    // primeros ( FACTOR ) = { id, num, num.num, (, literal }
    private void FACTOR( Atributos FACTOR ) {
        //error( " entra a FACTOR "+"PRINCIPAL: "+FACTOR.principal+"FACTOR2: "+FACTOR.factor2+"PREANALISIS: "+preAnalisis);
        //Atributos FACTOR2 = new Atributos();
        Atributos EXPRESION = new Atributos();
        Atributos FACTOR2=new Atributos();
        Linea_BE id;
        Linea_BE num;
        Linea_BE numnum;
        Linea_BE literal;
        
        if ( preAnalisis.equals( "id" ) ){
            
            // FACTOR → id  FACTOR2 
            id = cmp.be.preAnalisis;
            emparejar( "id" );
            //error( "id.lexema es:" + id.lexema);
            //Accion Semantica {2}
            ElemBuffer aux =  new ElemBuffer( id.lexema, false, 0 );
            Pila.push(aux);
            int p = id.entrada;
            FACTOR.Lugar = "[" + p + "]";
            if(FACTOR.factor2)
            {
                if ( p != NIL) {
                    
                    emite("param "+FACTOR.Lugar);
                    cmp.cu.insertar(new Cuadruplo("param",FACTOR.Lugar, "", ""));
                } 
                else {
		//error
                }
            }
                 
            if(!FACTOR.principal)
            { 
              FACTOR2.principal=false;
            }
            FACTOR2.h=id.lexema;
            //Fin Accion Semantica {2}
            
            FACTOR2( FACTOR2 );
            //Accion semantica -------------------------------------------------
            FACTOR.factor2=FACTOR2.factor2;
            FACTOR.principal=false;
            
            
             int p2 = cmp.ts.buscar(id.lexema);
                if ( p2 != NIL) {
                    FACTOR.Lugar = "[" + p2 + "]";
                } else {
		//error
                }
            //------------------------------------------------------------------
            
            
            
            
        }
        else if ( preAnalisis.equals( "num" ) ){
            
            // FACTOR → num 
            num = cmp.be.preAnalisis;
            emparejar( "num" );
            
            //Accion Semantica {3}
            int p = num.entrada;
            FACTOR.Lugar = "[" + p + "]";
            if(FACTOR.factor2)
            {
                if ( p != NIL) {
                    
                    emite("param "+FACTOR.Lugar);
                    cmp.cu.insertar(new Cuadruplo("param",FACTOR.Lugar, "", ""));
                } 
                else {
		//error
                }
            }
                else {
		Pila.push( new ElemBuffer( num.lexema, false, 0 ));
                }
            
            //Fin Accion Semantica {3}
            //Accion semantica -------------------------------------------------
           
            //------------------------------------------------------------------
            
            
        }
        else if ( preAnalisis.equals( "num.num" ) ){
            
            // FACTOR → num.num
           numnum = cmp.be.preAnalisis;
            emparejar( "num.num" );
            
            //Accion Semantica {4}
            int p = numnum.entrada;
            FACTOR.Lugar = "[" + p + "]";
             if(FACTOR.factor2)
            {
                if ( p != NIL) {
                    
                    emite("param "+FACTOR.Lugar);
                    cmp.cu.insertar(new Cuadruplo("param",FACTOR.Lugar, "", ""));
                } 
                else {
		//error
                }
            }
                else {
		Pila.push( new ElemBuffer( numnum.lexema, false, 0 ));
                }
            
            //Fin Accion Semantica {4}
            
            //Accion semantica -------------------------------------------------
            
            //------------------------------------------------------------------
            
            
            
        }
        else if ( preAnalisis.equals( "(" ) ){
            
            // FACTOR → ( EXPRESION )
            emparejar( "(" );
            
            //Accion Semantica {5}
            Pila.push(new ElemBuffer ( "(", true, 3 ));
            //Fin Accion Semantica {5}
            EXPRESION.principal=false;
            EXPRESION( EXPRESION );
            emparejar( ")" );
            
            //Accion Semantica {6}
            Pila.push(new ElemBuffer ( ")", true, 3 ));
             if(EXPRESION.operacion)
                FACTOR.operacion=true;
             FACTOR.Lugar=EXPRESION.Lugar;
            //Fin Accion Semantica {6}
            
        }
        else if ( preAnalisis.equals( "literal" ) ){
            
            // FACTOR → literal
            literal = cmp.be.preAnalisis;
            emparejar( "literal" );
            
            //Accion semantica -------------------------------------------------
           FACTOR.factor2=true;
           int p = literal.entrada;
            FACTOR.Lugar = "[" + p + "]";
            //------------------------------------------------------------------
            
            
            
        }
        else {
           error( "ERROR EN EL PROCEDURE [FACTOR]. " +
                   "Se esperaba un identificador, un número o caracter o un '('. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" ); 
        }
        // error( " sale de FACTOR "+"PRINCIPAL: "+FACTOR.principal+"FACTOR2: "+FACTOR.factor2+"PREANALISIS: "+preAnalisis);
    }
     private void FACTOR2(Atributos FACTOR2)
{
      //error( " entra a FACTOR2 "+"PRINCIPAL: "+FACTOR2.principal+"FACTOR2: "+FACTOR2.factor2+"PREANALISIS: "+preAnalisis);
    Atributos LISTA_EXPRESIONES = new Atributos(); 
	if(preAnalisis.equals("("))
	{
		emparejar("(");
                //ASM
                LISTA_EXPRESIONES.factor2=true;
                 //ASM
                if(!FACTOR2.principal)
                { 
                    LISTA_EXPRESIONES.principal=false;
                }
                FACTOR2.factor2=true;
                //
		LISTA_EXPRESIONES(LISTA_EXPRESIONES);
		emparejar(")");
                
                //inicio accion semantica
                 
                      
                emite("call "+ FACTOR2.h+","+consecutivo);
                 cmp.cu.insertar(new Cuadruplo("call", FACTOR2.h, "" + consecutivo, ""));
                 consecutivo=0;
                 emite(tempnuevo()+ FACTOR2.h+","+consecutivo);
                     //error( " sale de FACTOR2 ");
		// fin
	}
	else
	{
            FACTOR2.factor2=false;
	}
}
    //--------------------------------------------------------------------------
    // Autor: Alejandro Saucedo Ramirez
    // primeros ( TERMINO ) = { id, num, num.num, (, literal }
    private void TERMINO( Atributos TERMINO ) {
        // error( " entra a TERMINO "+"PRINCIPAL: "+TERMINO.principal+"FACTOR2: "+TERMINO.factor2+"PREANALISIS: "+preAnalisis);
        Atributos FACTOR = new Atributos();
        Atributos TERMINO2 = new Atributos();
        
        if ( preAnalisis.equals( "id" ) ||
             preAnalisis.equals( "num" ) ||
             preAnalisis.equals( "num.num" ) ||
             preAnalisis.equals( "(" ) ||
             preAnalisis.equals( "literal" )  ) {
            // TERMINO → FACTOR   TERMINO2 
             //ASM
            if(!TERMINO.principal)
            { 
              FACTOR.principal=false;
              TERMINO2.principal=false;
            }
             if(TERMINO.factor2)
            { 
              FACTOR.factor2=true;
              TERMINO2.factor2=true;
            }
            //
            FACTOR( FACTOR ); 
            TERMINO.Lugar=FACTOR.Lugar;
            TERMINO2(TERMINO2);
             if(TERMINO2.operacion||FACTOR.operacion)
                TERMINO.operacion=true;
            if (!FACTOR.principal||!TERMINO2.principal)
                TERMINO.principal=false;
            if (FACTOR.factor2||TERMINO2.factor2)
                TERMINO.factor2=true;
            //error( " sale de TERMINO "+"PRINCIPAL: "+TERMINO.principal+"FACTOR2: "+TERMINO.factor2+"PREANALISIS: "+preAnalisis);
        }        
        
        else {
            error( "ERROR EN EL PROCEDURE [TERMINO]. " +
                   "Se esperaba un identificador, un número o caracter o un '('. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Alejandro Saucedo Ramirez
    // primeros ( TERMINO2 ) = { opmult, Empty  }
    private void TERMINO2( Atributos TERMINO2 ) {
          //error( " entra a TERMINO2 "+"PRINCIPAL: "+TERMINO2.principal+"FACTOR2: "+TERMINO2.factor2+"PREANALISIS: "+preAnalisis);
        Atributos FACTOR = new Atributos();
        Atributos TERMINO21 = new Atributos();
        
        if ( preAnalisis.equals( "opmult" ) ) {
            
            // TERMINO2 → opmult  FACTOR  TERMINO2
            emparejar( "opmult" );
            
            //Accion Semantica {8}
            masdeuno=true;
            Pila.push( new ElemBuffer ( "*", true, 2 ));
            TERMINO2.operacion=true;
            //Fin Accion Semantica {8}
            
            FACTOR( FACTOR );
            TERMINO2(TERMINO21);
             if (FACTOR.factor2||TERMINO21.factor2)
                TERMINO2.factor2=true;
            // error( " sale de TERMINO2 "+"PRINCIPAL: "+TERMINO2.principal+"FACTOR2: "+TERMINO2.factor2+"PREANALISIS: "+preAnalisis);
        }
        else {
            // TERMINO2 → ϵ 
            
            
        }
         
        
    }
    //--------------------------------------------------------------------------
    // Autor: Alejandro Saucedo Ramirez
    // primeros ( VARIABLE ) = { [ }
    private void VARIABLE ( Atributos VARIABLE ) {
        //error( " entra a VARIABLE "+"PRINCIPAL: "+VARIABLE.principal+"FACTOR2: "+VARIABLE.factor2+"PREANALISIS: "+preAnalisis); 
        Atributos EXPRESION = new Atributos();
        
        if ( preAnalisis.equals( "[" ) ) {
            
            // VARIABLE → [ EXPRESION ] 
            emparejar( "[" );
            EXPRESION( EXPRESION );
            emparejar( "]" );
            }
        else {
            error( "ERROR EN EL PROCEDURE [VARIABLE]. " +
                   "Se esperaba '['. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
          
        
    }
    //************EMPAREJAR**************//
   private void emparejar(String t)
    {
        if (cmp.be.preAnalisis.complex.equals(t)) {
            cmp.be.siguiente();
            preAnalisis = cmp.be.preAnalisis.complex;            
        } else {
            errorEmparejar( cmp.be.preAnalisis.lexema);
        }
    }
    //--------------------------------------------------------------------------
    // Metodo para devolver un error al emparejar
    //--------------------------------------------------------------------------

    private void errorEmparejar ( String _token ) {
        String msjError = "ERROR SINTACTICO: ";
              
        if ( _token.equals ( "id" ) )
            msjError += "Se esperaba un identificador" ;
        else if ( _token.equals ( "num" ) )
            msjError += "Se esperaba una constante entera" ;
        else if ( _token.equals ( "num.num" ) )
            msjError += "Se esperaba una constante real";
        else if ( _token.equals ( "literal" ) )
            msjError += "Se esperaba una literal";
        else if ( _token.equals ( "oparit" ) )
            msjError += "Se esperaba un Operador Aritmetico";
        else if ( _token.equals ( "oprel" ) )
            msjError += "Se esperaba un Operador Relacional";
        else 
            msjError += "Se esperaba " + _token;
                
        cmp.me.error ( Compilador.ERR_SINTACTICO, msjError );    
    }            

    // Fin de ErrorEmparejar
    //--------------------------------------------------------------------------
	
    //--------------------------------------------------------------------------
    // Metodo para mostrar un error sintactico
 
    private void error ( String _token ) {
        cmp.me.error ( cmp.ERR_SINTACTICO,
         "ERROR SINTACTICO: en la produccion del simbolo  " + _token );
    }
 
    // Fin de error
    //--------------------------------------------------------------------------
    

}
