/*:-----------------------------------------------------------------------------
 *:                       INSTITUTO TECNOLOGICO DE LA LAGUNA
 *:                     INGENIERIA EN SISTEMAS COMPUTACIONALES
 *:                         LENGUAJES Y AUTOMATAS II           
 *: 
 *:                  SEMESTRE: ___________    HORA: ___________ HRS
 *:                                   
 *:               
 *:         Clase con la funcionalidad del Analizador Sintactico
 *                 
 *:                           
 *: Archivo       : SintacticoSemantico.java
 *: Autor         : Fernando Gil  ( Estructura general de la clase  )
 *:                 Grupo de Lenguajes y Automatas II ( Procedures  )
 *: Fecha         : 03/SEP/2014
 *: Compilador    : Java JDK 7
 *: Descripción   : Esta clase implementa un parser descendente del tipo 
 *:                 Predictivo Recursivo. Se forma por un metodo por cada simbolo
 *:                 No-Terminal de la gramatica mas el metodo emparejar ().
 *:                 El analisis empieza invocando al metodo del simbolo inicial.
 *: Ult.Modif.    :
 *:  Fecha      Modificó            Modificacion
 *:=============================================================================
 *: 22/Feb/2015 FGil                -Se mejoro errorEmparejar () para mostrar el
 *:                                 numero de linea en el codigo fuente donde 
 *:                                 ocurrio el error.
 *: 08/Sep/2015 FGil                -Se dejo lista para iniciar un nuevo analizador
 *:                                 sintactico.
 *:-----------------------------------------------------------------------------
 */
package compilador;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

public class SintacticoSemantico {
    
    public static final String NIL = "";
    public static final String VACIO = "vacio";
    public static final String ERROR_TIPO = "error_tipo";
    ArrayList <Integer> listaEntradas = new ArrayList <Integer>();
    ArrayList <Integer> listaDimensiones = new ArrayList <Integer>(); 
    ArrayList <String> listaRetornos = new ArrayList <String>(); 
    
           

    private Compilador cmp;
    private boolean    analizarSemantica = false;
    private String     preAnalisis;
    
    //--------------------------------------------------------------------------
    // Constructor de la clase, recibe la referencia de la clase principal del 
    // compilador.
    //

    public SintacticoSemantico(Compilador c) {
        cmp = c;
        
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    // Metodo que inicia la ejecucion del analisis sintactico predictivo.
    // analizarSemantica : true = realiza el analisis semantico a la par del sintactico
    //                     false= realiza solo el analisis sintactico sin comprobacion semantica

    public void analizar(boolean analizarSemantica) {
        this.analizarSemantica = analizarSemantica;
        preAnalisis = cmp.be.preAnalisis.complex;

        // * * *   INVOCAR AQUI EL PROCEDURE DEL SIMBOLO INICIAL   * * *
        CLASS( new Atributos() );
    }

    //--------------------------------------------------------------------------

    private void emparejar(String t) {
        if (cmp.be.preAnalisis.complex.equals(t)) {
            cmp.be.siguiente();
            preAnalisis = cmp.be.preAnalisis.complex;            
        } else {
            errorEmparejar( t, cmp.be.preAnalisis.lexema, cmp.be.preAnalisis.numLinea );
        }
    }
    
    //--------------------------------------------------------------------------
    // Metodo para devolver un error al emparejar
    //--------------------------------------------------------------------------
 
    private void errorEmparejar(String _token, String _lexema, int numLinea ) {
        String msjError = "";

        if (_token.equals("id")) {
            msjError += "Se esperaba un identificador";
        } else if (_token.equals("num")) {
            msjError += "Se esperaba una constante entera";
        } else if (_token.equals("num.num")) {
            msjError += "Se esperaba una constante real";
        } else if (_token.equals("literal")) {
            msjError += "Se esperaba una literal";
        } else if (_token.equals("oparit")) {
            msjError += "Se esperaba un operador aritmetico";
        } else if (_token.equals("oprel")) {
            msjError += "Se esperaba un operador relacional";
        } else if (_token.equals("opasig")) {
            msjError += "Se esperaba operador de asignacion";
        } else {
            msjError += "Se esperaba " + _token;
        }
        msjError += " se encontró " + ( _lexema.equals ( "$" )? "fin de archivo" : _lexema ) + 
                    ". Linea " + numLinea;        // FGil: Se agregó el numero de linea

        cmp.me.error(Compilador.ERR_SINTACTICO, msjError);
    }

    // Fin de ErrorEmparejar
    //--------------------------------------------------------------------------
    // Metodo para mostrar un error sintactico

    private void error(String _descripError) {
        cmp.me.error(cmp.ERR_SINTACTICO, _descripError);
    }
     private void retroceder(int n) {
        for (int i = 0; i < n; i++) {
            cmp.be.anterior();
            preAnalisis = cmp.be.preAnalisis.complex;
        }
    }
    
    private static boolean isCompatibleParams( String[] p1, String[] p2 ) {
        
        boolean esCompatible = false;
        if ( p1.length == p2.length ) {
            for ( int i = 0; i < p1.length; i++ ) {
  
                if ( p1[ i ].equals( "real" ) ) {
                
                    if ( p2[ i ].equals( "real" ) || p2[ i ].equals( "integer" ) ) {
                    
                        esCompatible = true;
                    
                    }
                    else {
                    
                        esCompatible = false;
                        break;
                    
                    }
                
                }
                else if ( p1[i].equals(p2[i])  ) {
                
                    esCompatible = true;
                
                }
                else {
                
                    esCompatible = false;
                    break;
                
                
                }
            
            }
        }
        else {
            esCompatible = false;
        }
            
        
        return esCompatible;
        
    }
    private static String[] separarParams( String cadena ){
        
        String delim = "X";
        String[] tmp = cadena.split( delim );
        return tmp;
        
    }
    private static int countParams ( String params ) {
                             
        return separarParams( params ).length;
        
    }
    
    // Fin de error
    //--------------------------------------------------------------------------
    //  *  *   *   *    PEGAR AQUI EL CODIGO DE LOS PROCEDURES  *  *  *  *
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // Autor: Alan
    // primeros ( CLASS ) = { type }
    private void CLASS( Atributos CLASS ) {
        
        Linea_BE id = new Linea_BE();
        Atributos DECLARACIONES1 = new Atributos();
        Atributos DECS_METODOS1 = new Atributos();
        Atributos DECLARACIONES2 = new Atributos();
        Atributos DECS_METODOS2 = new Atributos();
        Atributos IMPL_METODOS = new Atributos();
        Atributos LISTA_SENTENCIAS = new Atributos();
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
            emparejar ( "type" );
            id = cmp.be.preAnalisis; // salvamos el valor de id
            emparejar ( "id" );
            emparejar ( "oprel" );
            emparejar ( "class" );
            
            // Acción semantica 1
            if ( analizarSemantica ) {
                
                cmp.ts.anadeTipo( id.entrada, "class" );
                
            }
            // Fin acción semantica
            
            emparejar ( "private" );
            DECLARACIONES( DECLARACIONES1 );
            DECS_METODOS( DECS_METODOS1 );
            emparejar ( "public" );
            DECLARACIONES( DECLARACIONES2 );
            DECS_METODOS( DECS_METODOS2 );
            emparejar( "end" );
            emparejar( ";" );
            emparejar( "implementation" );
            IMPL_METODOS( IMPL_METODOS );
            emparejar( "execution" );
            emparejar( "begin" );
            LISTA_SENTENCIAS( LISTA_SENTENCIAS );
            emparejar( "end" );
            emparejar( "." );
            
            // Acción semantica 2
            // PENDIENTE
            /*if ( analizarSemantica ) {
                
                if ( DECLARACIONES1.tipo.equals( VACIO ) &&
                     DECS_METODOS1.tipo.equals( VACIO )  &&
                     DECLARACIONES2.equals( VACIO )      &&
                     DECS_METODOS2.tipo.equals( VACIO )  &&
                     LISTA_SENTENCIAS.tipo.equals( VACIO )) {
                    
                    CLASS.tipo = VACIO;
                    
                }
                else {
                    
                    CLASS.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO,
                                 "En produccion [CLASS]. " +
                                 "Hay un errores en el cuerpo de la clase. " ); 
                    
                }
                
            }*/
            // Fin acción semantica
            
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
    private void DECLARACIONES( Atributos DECLARACIONES ) {
        
        Atributos LISTA_ID = new Atributos();
        Atributos TIPO = new Atributos();
        Atributos DECLARACIONES1 = new Atributos();
        
        if ( preAnalisis.equals( "id" ) ) {
            // DECLARACIONES →  LISTA_ID   :  TIPO  ; DECLARACIONES
            LISTA_ID( LISTA_ID );
            emparejar( ":" );
            TIPO( TIPO );
            
            
            
            // Accion semantica 3
            if ( analizarSemantica ) {
                Iterator<Integer> iteList = listaEntradas.iterator();
            
                Iterator<Integer> iteDim = listaDimensiones.iterator();
                DECLARACIONES.tipo = VACIO;
                while( iteList.hasNext() ) {
                    
                    int entrada = iteList.next();
                    int dimension = iteDim.next();
                    
                    if ( dimension  != -1 ) {
                        if ( dimension  > 0 ) {
                        
                            if ( cmp.ts.buscaTipo( entrada ).equals( NIL ) ) {
                                
                                cmp.ts.anadeTipo( entrada, "array (1.." + dimension + "," + TIPO.tipo + ")" );
                                
                            }
                            else {
                                
                                DECLARACIONES.tipo = ERROR_TIPO;
                                cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [DECLARACIONES]. "
                                    + "Variable redeclarada. " + "Linea: "
                                    + cmp.be.preAnalisis.getNumLinea());
                                
                            }
                        
                        }
                        else {
                            
                            DECLARACIONES.tipo = ERROR_TIPO;
                            cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [DECLARACIONES]. "
                                    + "Arreglo con dimension negativa. " + "Linea: "
                                    + cmp.be.preAnalisis.getNumLinea());
                            
                        }
                    }
                    else {
                        
                        
                        if ( cmp.ts.buscaTipo( entrada ).equals( NIL ) ) {
                            
                            cmp.ts.anadeTipo( entrada , TIPO.tipo );
                            
                        }
                        else {
                            
                            DECLARACIONES.tipo = ERROR_TIPO;
                            cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [DECLARACIONES]. "
                                    + "Variable redeclarada. " + "Linea: "
                                    + cmp.be.preAnalisis.getNumLinea());
                            
                        }
                        
                    }
                    
                }
                
                 listaEntradas.removeAll( listaEntradas );
                listaDimensiones.removeAll( listaDimensiones );  

            }
            // Fin accion semantica
            
            emparejar( ";" );
            DECLARACIONES( DECLARACIONES1 );
            
            // Accion semantica 11
            if ( analizarSemantica ) {
                
                if ( DECLARACIONES.tipo.equals( VACIO ) && 
                     DECLARACIONES1.tipo.equals( VACIO ) ) {
                    
                    DECLARACIONES.tipo = VACIO;
                    
                }
                else {
                    
                    DECLARACIONES.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [DECLARACIONES]. "
                                    + "Hay error en la declaracion de variables");
                }
                
            }
            // Fin accion semantica
        }
        else {
            // DECLARACIONES →  ϵ
            
            // Accion semantica 12            
            if ( analizarSemantica ) {
                
                DECLARACIONES.tipo = VACIO;
                
            }
            // Fin accion semantica
            
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Alan
    // primeros ( LISTA_ID  ) = { id }
    private void LISTA_ID( Atributos LISTA_ID ) {
        
        Atributos DIMENSION = new Atributos();
        Atributos LISTA_ID2 = new Atributos();
        Linea_BE id = new Linea_BE();
        if ( preAnalisis.equals( "id" ) ) {

            // LISTA_ID → id  DIMENSION  LISTA_ID2
            id = cmp.be.preAnalisis; 
            emparejar( "id" );
            DIMENSION( DIMENSION );
            
            // Accion semantica 4
            if ( analizarSemantica ) {
                
                listaEntradas.add( id.entrada );
                
            }
            // Fin accion semantica
            
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
    private void LISTA_ID2( Atributos LISTA_ID2 ) {

        Atributos DIMENSION = new Atributos();
        Atributos LISTA_ID21 = new Atributos();
        Linea_BE id = new Linea_BE();
        if ( preAnalisis.equals( "," ) ) {
            
            // LISTA_ID2 → , id  DIMENSION  LISTA_ID2
            emparejar( "," );
            id = cmp.be.preAnalisis;
            emparejar( "id" );
            DIMENSION( DIMENSION );
            
            // Accion semantica 5
            if ( analizarSemantica ) {
                
                listaEntradas.add( id.entrada );
                
            }
            // Fin accion semantica
            
            LISTA_ID2( LISTA_ID21 );
            
        }
        else {
            // LISTA_ID2 →  ϵ 
        }
    }
    //--------------------------------------------------------------------------
    // Autor: Alan
    // primeros ( DIMENSION ) = { [, Empty }
    private void DIMENSION( Atributos DIMENSION ) {
        
        Linea_BE num = new Linea_BE();
        
        if ( preAnalisis.equals( "[" ) ) {
            
            // DIMENSION → [ num ] 
            emparejar( "[" );
            num = cmp.be.preAnalisis;
            emparejar( "num" );
            emparejar( "]" );
            
            // Accion semantica 6
            if ( analizarSemantica ) {
                
                listaDimensiones.add( Integer.parseInt( num.lexema ) );
                
            }
            // Fin de accion semantica
            
        }
        else {
            // DIMENSION → ϵ
            
            // Accion semantica 7
            if ( analizarSemantica ) {
                
                listaDimensiones.add( -1 );
                
            }
            // Fin de accion semantica
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Aimee
    // primeros ( TIPO ) = { integer, real, string  }
    private void TIPO( Atributos TIPO ) {
       
        if ( preAnalisis.equals( "integer" ) ) {
        
            // TIPO → integer
            emparejar( "integer" );
            
            // Accion semantica 8
            if ( analizarSemantica ) {
                
                TIPO.tipo = "integer";
                
            }
            // Fin accion semantica
        
        }
        else if ( preAnalisis.equals( "real" ) ) {
        
            // TIPO → real
            emparejar( "real" );
            // Accion semantica 9
            if ( analizarSemantica ) {
                
                TIPO.tipo = "real";
                
            }            
            // Fin accion semantica
        
        }
        else if ( preAnalisis.equals( "string" ) ) {
        
            // TIPO → string
            emparejar( "string" );
            // Accion semantica 10
            if ( analizarSemantica ) {
                
                TIPO.tipo = "string";
                
            }            
            // Fin accion semantica
            
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
    private void DECS_METODOS( Atributos DECS_METODOS ) {
        
        Atributos ENCAB_METODO = new Atributos();
        Atributos DECS_METODOS1 = new Atributos();
        if ( preAnalisis.equals( "function" ) ) {
            
            // DECS_METODOS → ENCAB_METODO  DECS_METODOS
            ENCAB_METODO( ENCAB_METODO );
            DECS_METODOS( DECS_METODOS1 );
            
            // Accion semantica 13
            if ( analizarSemantica ) {
                
                if ( ENCAB_METODO.tipo.equals( VACIO ) &&
                     DECS_METODOS1.tipo.equals( VACIO )) {
                    
                    DECS_METODOS.tipo = VACIO;
                    
                }
                else {
                    
                    DECS_METODOS.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [DECS_METODOS]. "
                                    + "Hay error en la declaracion de metodos. "); 
                    
                }
                
            }
            // Fin accion semantica
            
        }
        else {
            // DECS_METODOS → ϵ 
            
            // Accion semantica 14
            if ( analizarSemantica ) {
                
                DECS_METODOS.tipo = VACIO;
                
            }
            // Fin accion semantica
        }
        
        
    }
    //--------------------------------------------------------------------------
    // Autor: Aimee
    // primeros ( ENCAB_METODO ) = { function }
    private void ENCAB_METODO( Atributos ENCAB_METODO ) {
        
        Linea_BE id = new Linea_BE();
        Atributos LISTA_PARAMETROS = new Atributos();
        Atributos TIPO_METODO = new Atributos();
        if ( preAnalisis.equals( "function" ) ) {
            
            // ENCAB_METODO → function  id ( LISTA_PARAMETROS )  :  TIPO_METODO ; 
            emparejar( "function" );
            id = cmp.be.preAnalisis;
            emparejar( "id" );
            emparejar( "(" );
            LISTA_PARAMETROS( LISTA_PARAMETROS );
            emparejar( ")" );
            emparejar( ":" );
            TIPO_METODO( TIPO_METODO );
            emparejar( ";" );
            
            // Accion semantica 15
            if ( analizarSemantica ) {
                
                if ( cmp.ts.buscaTipo( id.entrada ).equals( NIL ) ) {
                    
                    if ( LISTA_PARAMETROS.tipo != ERROR_TIPO ) {
                        
                        cmp.ts.anadeTipo( id.entrada, LISTA_PARAMETROS.tipo + "->" + TIPO_METODO.tipo );
                        ENCAB_METODO.tipo = VACIO;
                        
                    }
                    else {
                        
                        ENCAB_METODO.tipo = ERROR_TIPO;
                        cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [ENCAB_METODO]. "
                                    + "Hay error en los parametros. "); 
                    }                    
                    
                }
                else {
                    
                    ENCAB_METODO.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [ENCAB_METODO]. "
                                    + "Funcion ya declarada. "); 
                }
                
            }
            // Fin accion semantica
            
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
    private void LISTA_PARAMETROS( Atributos LISTA_PARAMETROS ) {
        
        Atributos LISTA_ID = new Atributos();
        Atributos TIPO = new Atributos();
        Atributos LISTA_PARAMETROS2 = new Atributos();
        
        if ( preAnalisis.equals( "id" ) ) {
            
            // LISTA_PARAMETROS →  LISTA_ID   :  TIPO   LISTA_PARAMETROS2
            LISTA_ID( LISTA_ID );
            emparejar( ":" );
            TIPO( TIPO );
            
            Iterator<Integer> iteList = listaEntradas.iterator();
            Iterator<Integer> iteDim = listaDimensiones.iterator();  
            // Accion semantica 16
            if ( analizarSemantica ) {
                
                while( iteList.hasNext() ) {
                    
                    int entrada = iteList.next();
                    int dimension = iteDim.next();
                    
                    if ( cmp.ts.buscaTipo( entrada ).equals( NIL ) ) { // Si es la primera vez que se declara
                        
                        if ( dimension != -1 ) { // si se trata de un array
                            
                            if ( dimension > 0 ) { //si la dimension del array es mayor de cero
                                
                                String expresionTipo = "array (1.." + dimension + "," + TIPO.tipo + ")";
                                cmp.ts.anadeTipo( entrada, expresionTipo );
                                if ( LISTA_PARAMETROS.aux.equals( "" ) ) {
                                    
                                    LISTA_PARAMETROS.aux = expresionTipo;
                                
                                }
                                else {
                                    
                                    LISTA_PARAMETROS.aux = LISTA_PARAMETROS.aux + "X" + expresionTipo;
                                    
                                }
                            }
                            else {
                                
                                LISTA_PARAMETROS.aux = ERROR_TIPO; // Arreglo con dimensión negativa
                                cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [LISTA_PARAMETROS]. "
                                    + "Arreglo con dimension negativa. " + "Linea: "
                                    + cmp.be.preAnalisis.getNumLinea());
                                break; // romper el ciclo
                                
                                
                            }
                            
                        }
                        else {
                            
                            cmp.ts.anadeTipo( entrada, TIPO.tipo );
                            
                            if ( LISTA_PARAMETROS.aux.equals( "" ) ) {
                                
                                LISTA_PARAMETROS.aux = TIPO.tipo;
                                
                            }
                            else {
                                
                                LISTA_PARAMETROS.aux = LISTA_PARAMETROS.aux + "X" + TIPO.tipo;
                                
                            }
                            
                        }
                        
                    }
                    else {
                        
                        LISTA_PARAMETROS.aux = ERROR_TIPO; // Variable redeclarada
                        cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [LISTA_PARAMETROS]. "
                                    + "Variable redeclarada. " + "Linea: "
                                    + cmp.be.preAnalisis.getNumLinea());
                        break;
                        
                    }
                    
                }
                
                listaEntradas.removeAll( listaEntradas );
                listaDimensiones.removeAll( listaDimensiones );
               
                
                
            }
            // Fin accion semantica
            LISTA_PARAMETROS2( LISTA_PARAMETROS2 );    
            
                   
            // Accon semantica n
            if ( analizarSemantica ) {
                
                if ( LISTA_PARAMETROS.aux.equals( ERROR_TIPO ) ||
                     LISTA_PARAMETROS2.tipo.equals( ERROR_TIPO )) {
                    
                    LISTA_PARAMETROS.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [LISTA_PARAMETROS2]. "
                                    + "Hubo error en los argumentos. " );
                    
                }
                else {
                    
                    if ( LISTA_PARAMETROS2.tipo.equals( "" ) ) {
                        
                        LISTA_PARAMETROS.tipo = LISTA_PARAMETROS.aux;
                        
                    }
                    else {
                        
                        LISTA_PARAMETROS.tipo = LISTA_PARAMETROS.aux + "X" + LISTA_PARAMETROS2.tipo;
                        
                    }
                    
                }
                
            }
            // Fin accion semantica
            
            
            
            
        }
        else {
            // LISTA_PARAMETROS →   ϵ 
            
            // Accion semantica 17
            if ( analizarSemantica ) {
                
                LISTA_PARAMETROS.tipo = NIL;
                
            }
            // Fin accion semantica
        }
          
        
        
    }
    //--------------------------------------------------------------------------
    // Autor: Aimee
    // primeros ( LISTA_PARAMETROS2 ) = { ; , Empty }
    private void LISTA_PARAMETROS2( Atributos LISTA_PARAMETROS2 ) {
        
        Atributos LISTA_ID = new Atributos();
        Atributos TIPO = new Atributos();
        Atributos LISTA_PARAMETROS21 = new Atributos();
        
        if ( preAnalisis.equals( ";" ) ) {
             
            // LISTA_PARAMETROS2  →   ;  LISTA_ID   :  TIPO   LISTA_PARAMETROS2
            emparejar( ";" );
            LISTA_ID( LISTA_ID );
            emparejar( ":" );
            TIPO( TIPO );
            
            Iterator<Integer> iteList = listaEntradas.iterator();
            Iterator<Integer> iteDim = listaDimensiones.iterator();
            // Accion semantica 18
            if ( analizarSemantica ) {
                
                while( iteList.hasNext() ) {
                    
                    int entrada = iteList.next();
                    int dimension = iteDim.next();
                    
                    if ( cmp.ts.buscaTipo( entrada ).equals( NIL ) ) { // Si es la primera vez que se declara
                        
                        if ( dimension != -1 ) { // si se trata de un array
                            
                            if ( dimension > 0 ) { //si la dimension del array es mayor de cero
                                
                                String expresionTipo = "array (1.." + dimension + "," + TIPO.tipo + ")";
                                cmp.ts.anadeTipo( entrada, expresionTipo );
                                if ( LISTA_PARAMETROS2.aux.equals( NIL ) ) {
                                    
                                    LISTA_PARAMETROS2.aux = expresionTipo;
                                
                                }
                                else {
                                    
                                    LISTA_PARAMETROS2.aux = LISTA_PARAMETROS2.aux + "X" + expresionTipo;
                                    
                                }
                            }
                            else {
                                
                                LISTA_PARAMETROS2.aux = ERROR_TIPO; // Arreglo con dimensión negativa
                                cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [LISTA_PARAMETROS2]. "
                                    + "Arreglo con dimension negativa. " + "Linea: "
                                    + cmp.be.preAnalisis.getNumLinea());
                                break; // romper el ciclo
                                
                                
                            }
                            
                        }
                        else {
                            
                            cmp.ts.anadeTipo( entrada, TIPO.tipo );
                            
                            if ( LISTA_PARAMETROS2.aux.equals( "" ) ) {
                                
                                LISTA_PARAMETROS2.aux = TIPO.tipo;
                                
                            }
                            else {
                                
                                LISTA_PARAMETROS2.aux = LISTA_PARAMETROS2.aux + "X" + TIPO.tipo;
                                
                            }
                            
                        }
                        
                    }
                    else {
                        
                        LISTA_PARAMETROS2.aux = ERROR_TIPO; // Variable redeclarada
                        cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [LISTA_PARAMETROS2]. "
                                    + "Variable redeclarada. " + "Linea: "
                                    + cmp.be.preAnalisis.getNumLinea());
                        break;
                        
                    }
                    
                }
                
                listaEntradas.removeAll( listaEntradas );
                listaDimensiones.removeAll( listaDimensiones );
                
                
                
            }
            // Fin accion semantica
            LISTA_PARAMETROS2( LISTA_PARAMETROS21 );
            
            
            // Accion semantica n
            if ( analizarSemantica ) {
                
                if ( LISTA_PARAMETROS2.aux.equals( ERROR_TIPO ) ||
                     LISTA_PARAMETROS21.tipo.equals( ERROR_TIPO )) {
                    
                    LISTA_PARAMETROS2.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [LISTA_PARAMETROS2]. "
                                    + "Hubo error en los argumentos. " );
                    
                }
                else {
                    
                    if ( LISTA_PARAMETROS21.tipo.equals( "" ) ) {
                        
                        LISTA_PARAMETROS2.tipo = LISTA_PARAMETROS2.aux;
                        
                    }
                    else {
                        
                        LISTA_PARAMETROS2.tipo = LISTA_PARAMETROS2.aux + "X" + LISTA_PARAMETROS21.tipo;
                        
                    }
                    
                }
                
            }
            // Fin accion semantica
            
            
        }
        else {
            // LISTA_PARAMETROS2  →    ϵ 
            
            
            // Accion semantica 19
            if ( analizarSemantica ) {
                
                LISTA_PARAMETROS2.tipo = "";
                
            }
            // Fin accion semantica
        }
        
        
    }
    //--------------------------------------------------------------------------
    // Autor: Aimee
    // primeros ( TIPO_METODO ) = { void, integer, real, string }
    private void TIPO_METODO( Atributos TIPO_METODO ) {
        
        Atributos TIPO = new Atributos();
        
        if ( preAnalisis.equals( "void" ) ) {
            
            // TIPO_METODO → void
            emparejar( "void" );
            
            // Accion semantica 20
            if ( analizarSemantica ) {
                
                TIPO_METODO.tipo = "void";
                
            }
            // Fin accion semantica
            
        }
        else if ( preAnalisis.equals( "integer" ) || 
                  preAnalisis.equals( "real" )    || 
                  preAnalisis.equals( "string" ) ) {
            // TIPO_METODO → TIPO
            TIPO( TIPO );
            
            // Accion semantica 21
            if ( analizarSemantica ) {
                
                TIPO_METODO.tipo = TIPO.tipo;
                
            }
            // Fin acción semantica
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
    private void IMPL_METODOS( Atributos IMPL_METODOS ) {
        
        Atributos IMPL_METODO = new Atributos();
        Atributos IMPL_METODOS1 = new Atributos();
        if ( preAnalisis.equals( "function" ) ) {
            
            // IMPL_METODOS → IMPL_METODO    IMPL_METODOS
            IMPL_METODO( IMPL_METODO );
            IMPL_METODOS( IMPL_METODOS1 );
            
            // Accion semantica 22
            if ( analizarSemantica ) {
                
                if ( IMPL_METODO.tipo.equals( VACIO ) &&
                     IMPL_METODOS1.tipo.equals( VACIO ) ) {
                    
                    IMPL_METODOS.tipo  = VACIO;
                    
                }
                else {
		
                    IMPL_METODOS.tipo  = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [IMPL_METODOS]. "
                                    + "Error en la implementacion de metodos. " );
                    
                    
                }

                
            }
            // Fin accion semantica 22
            
        }
        else {
            
            // IMPL_METODOS → ϵ
            // Accion semantica
            if( analizarSemantica ) {
                
                IMPL_METODOS.tipo = VACIO;
                
            }
            // Fin accion semantica
        }
         
        
    }
    //--------------------------------------------------------------------------
    // Autor: Susy
    // primeros ( IMPL_METODO ) = { function }
    private void IMPL_METODO( Atributos IMPL_METODO ) {
        
        Linea_BE id = new Linea_BE();
        Atributos SENTENCIA_COMPUESTA = new Atributos();
        if ( preAnalisis.equals( "function" ) ) {
            
            // IMPL_METODO → function  id  : :  SENTENCIA_COMPUESTA  ;
            emparejar( "function" );
            id = cmp.be.preAnalisis;
            emparejar( "id" );
            // Accion semantica 24
            if ( analizarSemantica ) {
                
                
                if ( cmp.ts.buscaTipo( id.entrada ).contains( "->" ) ) {
                    
                    IMPL_METODO.aux = VACIO;
                    
                }
                else {
                    
                    IMPL_METODO.aux = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [IMPL_METODO]. "
                                    + "Nombre de funcion no fue definida en la clase como function. " + "Linea: "
                                    + cmp.be.preAnalisis.getNumLinea());
                    
                }
                
                
            }
            // Fin accion semantica
            emparejar( ":" );
            emparejar( ":" );
            SENTENCIA_COMPUESTA( SENTENCIA_COMPUESTA );          
            Iterator<String> iteReturns = listaRetornos.iterator();
            // Accion semantica 25
            if ( analizarSemantica ) {
                
                if ( IMPL_METODO.aux.equals( VACIO ) && 
                     SENTENCIA_COMPUESTA.tipo.equals( VACIO ) ) {
                    
                    if ( listaRetornos.isEmpty() ) {
                        
                        IMPL_METODO.tipo = ERROR_TIPO;
                        cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [IMPL_METODO]. "
                                    + "Falta sentencia return. " );
                        
                    }
                    else {
                        
                        if ( cmp.ts.buscaTipo( id.entrada ).contains( "->" ) ) {
                            
                            while ( iteReturns.hasNext() ) {
                                int cc = cmp.ts.buscaTipo( id.entrada ).indexOf( ">" );
                                String r = cmp.ts.buscaTipo( id.entrada ).substring( cc + 1, cmp.ts.buscaTipo( id.entrada ).length() );
                                if ( iteReturns.next().equals( r ) ) {
                                    
                                    IMPL_METODO.tipo = VACIO;
                                    
                                }
                                else {
                                    
                                    IMPL_METODO.tipo = ERROR_TIPO;
                                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [IMPL_METODO]. "
                                    + "No coindice el tipo retornado con el definido para esta funcion. " );
                                }
                                
                            }
                            
                        }
                        
                    }
                    
                }
                else {
                    
                    IMPL_METODO.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [IMPL_METODO]. "
                                    + "Hay error en la implementacion de metodos. " );
                }
            
                listaRetornos.removeAll( listaRetornos );
            }
            // Fin accion semantica
            
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
    private void SENTENCIA_COMPUESTA( Atributos SENTENCIA_COMPUESTA ) {
        
        Atributos SENTENCIAS_OPTATIVAS = new Atributos();
        if ( preAnalisis.equals( "begin" ) ) {
            
            // SENTENCIA_COMPUESTA 	→ begin   SENTENCIAS_OPTATIVAS  end 
            emparejar( "begin" );
            SENTENCIAS_OPTATIVAS( SENTENCIAS_OPTATIVAS );
            emparejar( "end" );
            
            // Accion semantica 26
            if ( analizarSemantica ) {
                
                SENTENCIA_COMPUESTA.tipo =  SENTENCIAS_OPTATIVAS.tipo;
                
            }
            // Fin accion semantica
            
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
    private void SENTENCIAS_OPTATIVAS( Atributos SENTENCIAS_OPTATIVAS ) {
        
        Atributos LISTA_SENTENCIAS = new Atributos();
         if ( preAnalisis.equals( "id" ) || 
             preAnalisis.equals( "begin" ) || 
             preAnalisis.equals( "if" ) || 
             preAnalisis.equals( "while" ) || 
             preAnalisis.equals( "return" ) ) {
            // SENTENCIAS_OPTATIVAS → LISTA_SENTENCIAS
            LISTA_SENTENCIAS( LISTA_SENTENCIAS );
            
            // Accion semantica 27
            if ( analizarSemantica ) {
                
                SENTENCIAS_OPTATIVAS.tipo = LISTA_SENTENCIAS.tipo;
                
            }
            // Fin accion semantica
        }
        else {
            // SENTENCIAS_OPTATIVAS → ϵ 
            
            // Accion semantica 28
            if ( analizarSemantica ) {
                
                SENTENCIAS_OPTATIVAS.tipo = VACIO;
                
            }
            // Fin accion semantica
        }
        
        
    }
    //--------------------------------------------------------------------------
    // Autor: Susy
    // primeros ( LISTA_SENTENCIAS ) = { id, begin, if, while, return, Empty }
    private void LISTA_SENTENCIAS( Atributos LISTA_SENTENCIAS ) {
        
        Atributos SENTENCIA = new Atributos();
        Atributos LISTA_SENTENCIAS1 = new Atributos();
        if ( preAnalisis.equals( "id" ) || 
             preAnalisis.equals( "begin" ) || 
             preAnalisis.equals( "if" ) || 
             preAnalisis.equals( "while" ) || 
             preAnalisis.equals( "return" ) ) {
            
            // LISTA_SENTENCIAS → SENTENCIA    LISTA_SENTENCIAS
            SENTENCIA( SENTENCIA );
            if ( analizarSemantica ) {
                
                if ( SENTENCIA.tipo.equals( VACIO ) ) {
                    
                    LISTA_SENTENCIAS.tipo = VACIO;
                    
                }
                else {
                    
                    LISTA_SENTENCIAS.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [LISTA_SENTENCIAS]. "
                                    + "Hay error en la lista de sentencias" );
                    
                }
                
            }
            LISTA_SENTENCIAS( LISTA_SENTENCIAS1 );
            
            // Accion semantica 29
            if ( analizarSemantica ) {
                
                if ( SENTENCIA.tipo.equals( VACIO ) &&
                     LISTA_SENTENCIAS1.tipo.equals( VACIO ) ) {
                    
                    LISTA_SENTENCIAS.tipo = VACIO;
                    
                }
                else {
                    
                    LISTA_SENTENCIAS.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [LISTA_SENTENCIAS]. "
                                    + "Hay error en la lista de sentencias" );
                    
                }
                
            }
            // Fin accion semantica
            
        }
        else {
        // LISTA_SENTENCIAS → ϵ 
        
        // Accion semantica 30
        if ( analizarSemantica ) {
            
            LISTA_SENTENCIAS.tipo = VACIO;
            
        }
        // Fin accion semantica
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Susy
    // primeros ( SENTENCIA  ) = { id, begin if, while, return }
    private void SENTENCIA( Atributos SENTENCIA ) {
        
        Atributos SENTENCIA2 = new Atributos();
        Atributos SENTENCIA_COMPUESTA = new Atributos();
        Atributos EXPRESION = new Atributos();
        Atributos SENTENCIA1 = new Atributos();
        Atributos SINO = new Atributos();
        Atributos RETORNO = new Atributos();
        Linea_BE id = new Linea_BE();
        
        if ( preAnalisis.equals( "begin" ) ) {
            
            // SENTENCIA  → SENTENCIA_COMPUESTA ;
            SENTENCIA_COMPUESTA( SENTENCIA_COMPUESTA );
            emparejar( ";" );
            
            // Accion semantica 31
            if ( analizarSemantica ) {
                
                SENTENCIA.tipo = SENTENCIA_COMPUESTA.tipo;
                
            }
            // Fin accion semantica
           
            
        } 
        else if ( preAnalisis.equals( "id" ) ) {
            
            // SENTENCIA  → id  SENTENCIA2  ;
            id = cmp.be.preAnalisis;
            emparejar ( "id" );
            
            // Accion semantica 35
            if ( analizarSemantica ) {
                
                if ( !cmp.ts.buscaTipo( id.entrada ).isEmpty() ) {
                    
                    SENTENCIA2.h = cmp.ts.buscaTipo( id.entrada );  
                    SENTENCIA.tipo = VACIO;
                }
                else {
                    
                    SENTENCIA.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [SENTENCIA]. "
                                    + "El identificador '" + id.lexema + "' no a sido declarado. " +
                                    "Linea: " + cmp.be.preAnalisis.getNumLinea() );
                    
                }
                
                
            }
            // Fin accion semantica
            
            SENTENCIA2( SENTENCIA2 );
            emparejar( ";" );
            
            // Accion semantica 36
            if ( analizarSemantica ) {
                
                if ( SENTENCIA2.tipo.equals( VACIO ) ) {
                                         
                    SENTENCIA.tipo = VACIO;
                }
                else {
                    
                    SENTENCIA.tipo = ERROR_TIPO;
                    
                }
                
            }
            // Fin accion semantica
            
        }        
        else if ( preAnalisis.equals( "if" ) ) {
            
            // SENTENCIA  → if  EXPRESION  then SENTENCIA  SINO  
            emparejar( "if" );
            EXPRESION( EXPRESION );
            emparejar( "then" );
            SENTENCIA( SENTENCIA1 );
            SINO( SINO );
            
            // Accion semantica 32
            if ( analizarSemantica ) {
                
                if ( EXPRESION.tipo.equals( "boolean" ) &&
                     SENTENCIA1.tipo.equals( VACIO ) &&
                     SINO.tipo.equals( VACIO ) ) {
                    
                    SENTENCIA.tipo = VACIO;
                    
                }
                else {
                    
                    SENTENCIA.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [SENTENCIA]. "
                                    + "La expresion debe de ser de tipo boolean" );
                    
                }
               
                
            }
            // Fin accion semantica
            
        }
        else if ( preAnalisis.equals( "while" ) ) {
            
            // SENTENCIA  → while   EXPRESION  do  SENTENCIA   
            emparejar( "while" );
            EXPRESION( EXPRESION );
            emparejar( "do" );
            SENTENCIA( SENTENCIA1 );
            
            // Accion semantica 33
            if ( analizarSemantica ) {
                
                
                if ( EXPRESION.tipo.equals( "boolean" ) &&
                     SENTENCIA1.tipo.equals( VACIO ) ) {
                    
                    SENTENCIA.tipo = VACIO;
                    
                }
                else {
                    
                    SENTENCIA.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [SENTENCIA]. "
                                    + "La expresion debe de ser de tipo boolean" );
                    
                }
                
                
            }
            // Fin accion semantica
            
        }
        else if ( preAnalisis.equals( "return" ) ) {
        
            // SENTENCIA  → return  RETORNO   ;
            emparejar( "return" );
            RETORNO( RETORNO );
            emparejar( ";" );
            
            // Accion semantica 34
            if ( analizarSemantica ) {
                
                listaRetornos.add ( RETORNO.tipo );
                SENTENCIA.tipo = VACIO;
                
            }
            // Fin accion semantica
            
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
        
        Atributos VARIABLE = new Atributos();
        Atributos EXPRESION = new Atributos();
        Atributos SENTENCIA_METODO = new Atributos();
        
        if ( preAnalisis.equals("[") ) {
            
            // SENTENCIA2 → VARIABLE   opasig   EXPRESION 
            VARIABLE( VARIABLE );
            emparejar( "opasig" );
            EXPRESION( EXPRESION );
            int i = 0;
            String t = "";
            // Accion semantica 38 PENDIENTE
            if ( analizarSemantica ) {
                
                if ( SENTENCIA2.h.contains( "array" )) {
                    i = SENTENCIA2.h.indexOf( "," );
                    t = SENTENCIA2.h.substring( i + 1, SENTENCIA2.h.length()-1 );
                }
                 if ( 
                      VARIABLE.tipo.equals( "integer" ) && 
                      EXPRESION.tipo.equals( t ) ||
                      t.equals( "real" ) &&
                      EXPRESION.tipo.equals( "integer" )){
                    
                         SENTENCIA2.tipo = VACIO;
                    
                    }
                    else {
                          
                        SENTENCIA2.tipo = ERROR_TIPO;
                        cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [SENTENCIA2]. "
                                    + "Tipo de dato no compatible con el array. " + "Linea: "
                                    + cmp.be.preAnalisis.getNumLinea());
                        
                    }
                
                
            }
            // Fin accion semantica
        }
        else if ( preAnalisis.equals( "opasig" ) ) {
            
            // SENTENCIA2 → opasig  EXPRESION
            emparejar( "opasig" );
            EXPRESION( EXPRESION );
            
            // Accion semantica 37 PENDIENTE
            if ( analizarSemantica ) {
                
                if ( SENTENCIA2.h.equals( EXPRESION.tipo ) ||
                     SENTENCIA2.h.equals( "real" ) && 
                     EXPRESION.tipo.equals( "integer" ) ) {
                    
                    SENTENCIA2.tipo = VACIO;
                    
                }
                else {
                    
                    SENTENCIA2.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [SENTENCIA2]. "
                                    + "El tipo de la expresion no es compatible con el identificador. " + SENTENCIA2.h + "" + EXPRESION.tipo);
                    
                }
                                
            }
            // Fin accion semantica
        }
        else if ( preAnalisis.equals( "(" ) ) {
            
            // SENTENCIA2 → SENTENCIA_METODO 
            
            int i ;
            String t;
            // Accion semantica 63
            if ( analizarSemantica ) {
                i = SENTENCIA2.h.indexOf( ">" );
                t = SENTENCIA2.h.substring( i + 1,SENTENCIA2.h.length() );
                SENTENCIA_METODO.tipoMetodo = t;
                
                if ( !SENTENCIA_METODO.tipoMetodo.equals( "void" ) ) {
                    
                    SENTENCIA2.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [SENTENCIA2]. "
                                    + "Solo se puede invocar directamente metodos de tipo 'void'. " );
                }
                else {
                    
                    SENTENCIA2.tipo = VACIO;
                    
                }
            }
            // Fin accion semantica
            SENTENCIA_METODO( SENTENCIA_METODO );
            
            // Accion semantica 64
            if ( analizarSemantica ) {
             
                int c = SENTENCIA2.h.indexOf( "-" );
                
                String tmp = SENTENCIA2.h.substring( 0,c );
                if ( SENTENCIA2.tipo.equals( VACIO ) &&
                     isCompatibleParams( separarParams( tmp ), separarParams( SENTENCIA_METODO.tipo ) )){
                    SENTENCIA2.tipo = VACIO;
                }
                else {
                    SENTENCIA2.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [SENTENCIA2]. "
                                    + "No coinciden los argumentos del metodo " + tmp + " : " + SENTENCIA_METODO.tipo );
                    
                }
                
                /*String senMetodo = SENTENCIA_METODO.tipo + "->void";
                if ( SENTENCIA2.tipo.equals( VACIO ) && 
                     SENTENCIA2.h.equals( senMetodo ) ) {
                    
                    SENTENCIA2.tipo = VACIO;
                    
                } 
                else {
                    
                    SENTENCIA2.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [SENTENCIA2]. "
                                    + "No coinciden los argumentos del metodo. " + senMetodo + " : " + SENTENCIA2.h );
                }*/
                
            }
            // Fin accion semantica
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
        
        Atributos SENTENCIA = new Atributos();
        
        if ( preAnalisis.equals( "else" ) ) {
            
            // SINO  → else  SENTENCIA
            emparejar( "else" );
            SENTENCIA( SENTENCIA );
            
            // Accion semantica 40
            if ( analizarSemantica ) {
                
                SINO.tipo = SENTENCIA.tipo;
                
            }
            // Fin accion semantica
        }
        else {
            // SINO → ϵ
            
            // Accion semantica 41
            if ( analizarSemantica ) {
                
                SINO.tipo = VACIO;
                
            }
            // Fin accion semantica
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
            LISTA_EXPRESIONES( LISTA_EXPRESIONES );
            // Accion semantica
            if ( analizarSemantica ) {
                
                SENTENCIA_METODO.tipo = LISTA_EXPRESIONES.parametros;
                
            }
            // Fin accion semantica
            emparejar( ")" );
            
            
            
            
            
            
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
        
        Atributos EXPRESION = new Atributos();
        Atributos LISTA_EXPRESIONES2 = new Atributos();
        
        if ( preAnalisis.equals( "id" ) ||
             preAnalisis.equals( "num" ) || 
             preAnalisis.equals( "num.num" ) ||
             preAnalisis.equals( "(" ) ||
             preAnalisis.equals( "literal" ) ) {
            
            // LISTA_EXPRESIONES → EXPRESION    LISTA_EXPRESIONES2
            EXPRESION( EXPRESION );
            LISTA_EXPRESIONES2( LISTA_EXPRESIONES2 );
            
            // Accion semantica 60
            if ( analizarSemantica ) {
                
                if ( EXPRESION.tipo.equals( ERROR_TIPO ) ||
                     LISTA_EXPRESIONES2.tipo.equals( ERROR_TIPO )) {
                    
                    LISTA_EXPRESIONES.tipo = ERROR_TIPO;
                    
                }
                else {
                    if ( LISTA_EXPRESIONES2.parametros.equals( NIL ) ) {
                        
                        LISTA_EXPRESIONES.parametros = EXPRESION.tipo;
                        
                    }
                    else {
                        
                        LISTA_EXPRESIONES.parametros = EXPRESION.tipo + "X" + LISTA_EXPRESIONES2.parametros;
                        
                    }
                    
                    LISTA_EXPRESIONES.tipo = VACIO;
                    
                }
                
                
            }
            // Fin accion semantica
            
        }
        else {
            
            // LISTA_EXPRESIONES → ϵ
            
            // Accion semantica 62
            if ( analizarSemantica ) {
                
                LISTA_EXPRESIONES.tipo = VACIO;
                
            }
            // Fin accion semantica
            
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
            EXPRESION( EXPRESION );
            // Accion semantica 61
            if ( analizarSemantica ) {
                
                if ( EXPRESION.tipo.equals( ERROR_TIPO ) ||
                     LISTA_EXPRESIONES21.tipo.equals( ERROR_TIPO ) ) {
                    
                    LISTA_EXPRESIONES2.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [LISTA_EXPRESIONES2]. "
                                    + "Error en la lista de expresiones. " );
                }
                else {
                    
                    
                    LISTA_EXPRESIONES2.tipo = VACIO;
                    
                }
                                
            }
            // Fin accion semantica
            LISTA_EXPRESIONES2( LISTA_EXPRESIONES21 );
            
            // Accion semantica MIL
            if (analizarSemantica){
                
                if ( LISTA_EXPRESIONES21.parametros.equals( NIL ) ) {
                        
                        LISTA_EXPRESIONES2.parametros = EXPRESION.tipo;
                        
                    }
                    else {
                        
                        LISTA_EXPRESIONES2.parametros = EXPRESION.tipo + "X" + LISTA_EXPRESIONES21.parametros;
                        
                    }
                 
                
            }
            // Fin accion semantica
            
            
            
            
        }
        else {
            // LISTA_EXPRESIONES2 →  ϵ
            
            // Accion semantica 63
            if ( analizarSemantica ) {
                
                LISTA_EXPRESIONES2.tipo = VACIO; 
                
            }
            // Fin accion semantica
            
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Nuria
    // primeros ( RETORNO ) = { void, id, num, num.num, (, literal }
    private void RETORNO( Atributos RETORNO ) {
        
        Atributos EXPRESION = new Atributos();
        
        if ( preAnalisis.equals( "void" ) ) {
            
            // RETORNO  → void
            emparejar( "void" );
            // Accion semantica 42
            if ( analizarSemantica ) {
                
                RETORNO.tipo = "void";
                
            }
            // Fin accion semantica
            
        }
        else if ( preAnalisis.equals( "id" ) || 
                  preAnalisis.equals( "num" ) || 
                  preAnalisis.equals( "num.num" ) || 
                  preAnalisis.equals( "(" ) ||
                  preAnalisis.equals( "literal" ) ) {
            
            // RETORNO  → EXPRESION
            EXPRESION( EXPRESION );
            
            // Accion semantica 43
            if ( analizarSemantica ) {
                
                RETORNO.tipo = EXPRESION.tipo;
                                
            }
            // Fin accion semantica
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
        
        Atributos EXPRESION_SIMPLE = new Atributos();
        Atributos EXPRESION2 = new Atributos();
        
        if ( preAnalisis.equals( "id" ) || 
             preAnalisis.equals( "num" ) || 
             preAnalisis.equals( "num.num" ) || 
             preAnalisis.equals( "(" ) || 
             preAnalisis.equals( "literal" ) )  {
            
            // EXPRESION → EXPRESION_SIMPLE  EXPRESION2 
            EXPRESION_SIMPLE( EXPRESION_SIMPLE );
            EXPRESION2( EXPRESION2 );
            
            // Accion semantica 44
            if ( analizarSemantica ) {
                
                if ( EXPRESION_SIMPLE.tipo != ERROR_TIPO &&
                     EXPRESION2.tipo != ERROR_TIPO ) {
                    
                    if ( EXPRESION2.tipo.equals( VACIO ) ) {
                        
                        EXPRESION.tipo = EXPRESION_SIMPLE.tipo;
                        
                    }
                    else if ( EXPRESION_SIMPLE.tipo.equals( EXPRESION2.tipo ) ||
                              EXPRESION_SIMPLE.tipo != "string" &&
                              EXPRESION2.tipo != "string" ) {
                        
                        EXPRESION.tipo = "boolean";
                        
                    }
                    else {
                        
                        EXPRESION.tipo = ERROR_TIPO;
                        cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [EXPRESION]. "
                                    + "Tipo no compatible en expresion condicional " );
                        
                    }
                    
                    
                }
                else {
                    
                    EXPRESION.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [EXPRESION]. "
                                    + "Error en las expresiones condicionales. " );
                }
                               
                
            }
            // Fin accion semantica

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
        
        Atributos EXPRESION_SIMPLE = new Atributos();
        
        if ( preAnalisis.equals( "oprel" ) ) {
            
            // EXPRESION2 	→ oprel   EXPRESION_SIMPLE
            emparejar( "oprel" );
            EXPRESION_SIMPLE( EXPRESION_SIMPLE );
            
            // Accion semantica 56
            if ( analizarSemantica ) {
                
                EXPRESION2.tipo = EXPRESION_SIMPLE.tipo;
                
            }
            // Fin accion semantica
        }
        else {
            // EXPRESION2 	→ ϵ 
            
            // Accion semantica 57
            if ( analizarSemantica ) {
                
                EXPRESION2.tipo = VACIO;
                
            }
            // Fin accion semantica
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Alejandro Saucedo Ramirez
    // primeros ( EXPRESION_SIMPLE ) = { id, num, num.num, (, literal }
    private void EXPRESION_SIMPLE( Atributos EXPRESION_SIMPLE ) {
        
        Atributos TERMINO = new Atributos();
        Atributos EXPRESION_SIMPLE2 = new Atributos();
        
        if ( preAnalisis.equals( "id" ) ||
             preAnalisis.equals( "num" ) ||
             preAnalisis.equals( "num.num" ) ||
             preAnalisis.equals( "(" ) ||
             preAnalisis.equals( "literal" ) ) {
            
            // EXPRESION_SIMPLE → TERMINO   EXPRESION_SIMPLE2 
            TERMINO( TERMINO );
            EXPRESION_SIMPLE2( EXPRESION_SIMPLE2 );
            
      
            
            // Accion semantica 45
            if ( analizarSemantica ) {
                 
                if ( EXPRESION_SIMPLE2.tipo != ERROR_TIPO && 
                     TERMINO.tipo != ERROR_TIPO ) {
                    
                    if ( EXPRESION_SIMPLE2.tipo.equals( VACIO ) && TERMINO.tipo != VACIO ) {
                        
                        EXPRESION_SIMPLE.tipo = TERMINO.tipo;
                        
                    }
                    /*else if ( TERMINO.tipo.equals( "string" ) ) {
                            
                        
                        if ( EXPRESION_SIMPLE2.tipo.equals( "string" ) )
                            EXPRESION_SIMPLE.tipo = "string";           
                        else {
                            EXPRESION_SIMPLE.tipo = ERROR_TIPO;
                            cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [EXPRESION_SIMPLE]. "
                                    + "El tipo de la expresion no es compatible. " );
                        }
                                                
                    }*/
                    else if ( EXPRESION_SIMPLE2.tipo.equals( "real" ) ||
                              TERMINO.tipo.equals( "real" ) ) {
                            
                            EXPRESION_SIMPLE.tipo = "real";                         
                                                
                    }
                    else if ( EXPRESION_SIMPLE2.tipo.equals("integer") && TERMINO.tipo.equals("integer")){
                        
                        EXPRESION_SIMPLE.tipo = "integer";
                    }
                    else{
                        
                        EXPRESION_SIMPLE.tipo = "ERROR_TIPO";
                        
                    }
                    
                    
                }
                else {
                    
                    EXPRESION_SIMPLE.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [EXPRESION_SIMPLE]. "
                                    + "El tipo de la expresion no es compatible. " );
                    
                }
                 
            }
            // Fin accion semantica
            
        }
        else {
            error( "ERROR EN EL PROCEDURE [EXPRESION_SIMPLE]. " +
                   "Se esperaba un identificador, un número o caracter o un '('. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" ); 
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Alejandro Saucedo Ramirez
    // primeros ( EXPRESION_SIMPLE2 ) = { opsuma, Empty }
    private void EXPRESION_SIMPLE2( Atributos EXPRESION_SIMPLE2 ) {
        
        Atributos TERMINO = new Atributos();
        Atributos EXPRESION_SIMPLE21 = new Atributos();
        
        if ( preAnalisis.equals( "opsuma" ) ) {
            
            // EXPRESION_SIMPLE2 → opsuma  TERMINO   EXPRESION_SIMPLE2 
            emparejar( "opsuma" );
            TERMINO( TERMINO );
            EXPRESION_SIMPLE2( EXPRESION_SIMPLE21 );
            
            // Accion semantica 54
            if ( analizarSemantica ) {
                
                if ( TERMINO.tipo != ERROR_TIPO &&
                     EXPRESION_SIMPLE21.tipo != ERROR_TIPO ) {
                    
                    if ( TERMINO.tipo != "string" ) {
                        
                        if ( EXPRESION_SIMPLE21.tipo.equals( VACIO ) && TERMINO.tipo != VACIO ) {
                            
                            EXPRESION_SIMPLE2.tipo = TERMINO.tipo;
                            
                        }
                       
                        else if ( TERMINO.tipo.equals( "integer" ) &&
                                 EXPRESION_SIMPLE21.tipo.equals( "integer" ) ) {
                                
                                EXPRESION_SIMPLE2.tipo = "integer";
                                
                            }
                        else {
                                
                                EXPRESION_SIMPLE2.tipo = "real";
                                
                            }
                            
                        
                        
                    }
                    else {
                        
                        EXPRESION_SIMPLE2.tipo = ERROR_TIPO;
                        
                    }
                    
                }
                else {
                    
                    EXPRESION_SIMPLE2.tipo = ERROR_TIPO;
                    
                }
                        
            }
            // Fin accion semantica
            
        }
        else {
           // EXPRESION_SIMPLE2 → ϵ  
           
           // Accion semantica 55
           if ( analizarSemantica ) {
               
               EXPRESION_SIMPLE2.tipo = VACIO;
               
           }
           // Fin accion semantica
        }
        
        
    }
    //--------------------------------------------------------------------------
    // Autor: Alejandro Saucedo Ramirez
    // primeros ( FACTOR ) = { id, num, num.num, (, literal }
    private void FACTOR( Atributos FACTOR ) {
        
        Atributos FACTOR2 = new Atributos();
        Atributos EXPRESION = new Atributos();
        Linea_BE id = new Linea_BE();
         Linea_BE num = new Linea_BE();
        Linea_BE numnum = new Linea_BE();
        Linea_BE literal = new Linea_BE();
        if ( preAnalisis.equals( "id" ) ){
            
            // FACTOR → id  FACTOR2 
            id = cmp.be.preAnalisis;
            emparejar( "id" );
            // Accion semantica 66
            if ( analizarSemantica ) {
                FACTOR2.id = id.lexema;
                    
                if ( !cmp.ts.buscaTipo( id.entrada ).isEmpty() ) {
                    
                    FACTOR2.tipoMetodo = cmp.ts.buscaTipo( id.entrada );                 
                    FACTOR.tipo = VACIO;
                }
                else {
                    
                    FACTOR.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [FACTOR]. "
                                    + "El identificador '" + FACTOR2.id + "' no a sido declarado. " +
                                    "Linea: " + cmp.be.preAnalisis.getNumLinea() );
                    
                }
                
                
            }
            // Fin accion semantica
            FACTOR2( FACTOR2 );
            
            // Accion semantica 47
            if ( analizarSemantica ) {
                
                if ( FACTOR.tipo != ERROR_TIPO && FACTOR2.tipo != ERROR_TIPO ) {
                    
                    int coco;
                    String soso;
                    if ( cmp.ts.buscaTipo( id.entrada ).contains("->" ) ) {
                        coco = cmp.ts.buscaTipo( id.entrada ).indexOf( ">" );
                        soso = cmp.ts.buscaTipo( id.entrada ).substring( coco + 1 , FACTOR2.tipoMetodo.length() );
                        
                        
                        if ( soso.equals("void") ){
                            
                            FACTOR.tipo = ERROR_TIPO;
                            cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [FACTOR]. "
                                    + "No se puede usar tipo void en una expresion" );
                            
                        }
                        else {
                            
                            FACTOR.aux = soso;
                            
                        }
                    
                    }
                    else {
                        FACTOR.aux = cmp.ts.buscaTipo( id.entrada );
                    }
                    
                    if ( FACTOR2.tipo.equals( VACIO ) ) {
                        int i;
                        String t;
                        if ( FACTOR.aux.contains("array") ) {
                            
                            i = FACTOR.aux.indexOf( "," );
                            t = FACTOR.aux.substring( i + 1, FACTOR.aux.length() );
                            FACTOR.tipo = t; 
                            
                        }
                        else {
                            
                            FACTOR.tipo = FACTOR.aux;
                            
                        }
                        
                    }
                    else {
                        
                        if ( !FACTOR2.tipo.equals("string") ) {
                            
                            if ( FACTOR2.tipo.equals( "integer" ) &&
                                 EXPRESION.tipo.equals( "integer" )) {
                                
                                FACTOR.tipo = "integer";
                                
                            }
                            else {
                                
                                FACTOR.tipo = "real";
                                
                            }
                        }
                        /*else if ( FACTOR2.tipo.equals("string") ) {
                            
                                FACTOR.tipo = "string";
                            
                        }*/
                        else {
                            
                            FACTOR.tipo = ERROR_TIPO;
                            
                        }
                        
                        
                    }
                    
                }
                else {
                    
                    FACTOR.tipo = ERROR_TIPO;
                    
                }
                
            }
            // Fin accion semantica
            
            
            
            
        }
       
        else if ( preAnalisis.equals( "num" ) ){
            
            // FACTOR → num 
            num = cmp.be.preAnalisis;
            emparejar( "num" );
            
            // Accion semantica 48
            if ( analizarSemantica ) {
                
               
                
                FACTOR.tipo = "integer";
                cmp.ts.anadeTipo(num.entrada, "integer");
                
            }
            // Fin accion semantica
            
        }
        else if ( preAnalisis.equals( "num.num" ) ){
            
            // FACTOR → num.num
             numnum = cmp.be.preAnalisis;
            emparejar( "num.num" );
            
            // Accion semantica 49
            if ( analizarSemantica ) {
                
                FACTOR.tipo = "real";
                cmp.ts.anadeTipo(numnum.entrada, "real");
            }
            // Fin accion semantica
            
        }
        else if ( preAnalisis.equals( "(" ) ){
            
            // FACTOR → ( EXPRESION )
            emparejar( "(" );
            EXPRESION( EXPRESION );
            
            
            // Accion semantica
            if (analizarSemantica){
                
                FACTOR.tipo = EXPRESION.tipo;
                
                
            }
            // Fin accion semantica
            
            
            emparejar( ")" );
            
            
        }
        else if ( preAnalisis.equals( "literal" ) ){
            
            // FACTOR → literal
            literal = cmp.be.preAnalisis;
            emparejar( "literal" );
            
            // Accion semantica 51
            if ( analizarSemantica ) {
                
                FACTOR.tipo = "string";
                cmp.ts.anadeTipo(literal.entrada, "string");
            }
            // Fin accion semantica
            
        }
        else {
           error( "ERROR EN EL PROCEDURE [FACTOR]. " +
                   "Se esperaba un identificador, un número o caracter o un '('. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" ); 
        }
        
    }
    //--------------------------------------------------------------------------
    // Autor: Alejandro Saucedo Ramirez
    // primeros ( FACTOR2 ) = { (, Empty }
    private void FACTOR2( Atributos FACTOR2 ) {
        
        Atributos LISTA_EXPRESIONES = new Atributos();
        
        if ( preAnalisis.equals( "(" ) ){
            // FACTOR2 → ( LISTA_EXPRESIONES ) 
            
            // Accion semantica 66
            if ( analizarSemantica ) {
                int i;
                String s;
                
                if ( FACTOR2.tipoMetodo.contains( "->" ) ) {
                    i = FACTOR2.tipoMetodo.indexOf( ">" );
                    s = FACTOR2.tipoMetodo.substring( i + 1 , FACTOR2.tipoMetodo.length() );
                    FACTOR2.tipo = s;
                    
                }
                else {
                    
                    FACTOR2.tipo = ERROR_TIPO;
                    cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [FACTOR2]. "
                                    + "El identificador'" + FACTOR2.id + "' no a sido declarado como function. ");
                }
                
            }
            // Fin accion semantica
            emparejar( "(" );
            LISTA_EXPRESIONES( LISTA_EXPRESIONES );
            // Accion semantica 70
            if ( analizarSemantica ) {
            int i = FACTOR2.tipoMetodo.indexOf( "-" );
            String t = FACTOR2.tipoMetodo.substring( 0, i );
            String []tmp = separarParams(t);
            String []tmp2 = separarParams(LISTA_EXPRESIONES.parametros);
            if ( isCompatibleParams( tmp , tmp2 ) != true ) {
                
                FACTOR2.tipo = ERROR_TIPO;
                cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [FACTOR2]. "
                                    + "Tipo de parametros de la funcion " +FACTOR2.id +"(...) no coinciden " + t + " : " + LISTA_EXPRESIONES.parametros );
                
            }
            else {
                //
                //cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [FACTOR2]. "
                  //                  + "Tipo de parametros de la funcion " +FACTOR2.id +"(...) no coinciden " + t + " : " + LISTA_EXPRESIONES.parametros );
                FACTOR2.tipo = VACIO;
            }
            /*if ( t.equals( LISTA_EXPRESIONES.parametros ) ) {
                
                // COCOC
                
                
            }
            else {
                
                FACTOR2.tipo = ERROR_TIPO;
                cmp.me.error(Compilador.ERR_SEMANTICO, "En produccion [FACTOR]. "
                                    + "Tipo de parametros no coinciden " + t + " : " + LISTA_EXPRESIONES.parametros );
            }*/
            }
            // Fin accion semantica
            emparejar( ")" );
            
            
        }
        else {
            
            // FACTOR2 → ϵ
            
            // Accion semantica 59
            if ( analizarSemantica ) {
                
                FACTOR2.tipo = VACIO;
                
            }
            // Fin accion semantica
        }
        
        
    }
    //--------------------------------------------------------------------------
    // Autor: Alejandro Saucedo Ramirez
    // primeros ( TERMINO ) = { id, num, num.num, (, literal }
    private void TERMINO( Atributos TERMINO ) {
        
        Atributos FACTOR = new Atributos();
        Atributos TERMINO2 = new Atributos();
        
        if ( preAnalisis.equals( "id" ) ||
             preAnalisis.equals( "num" ) ||
             preAnalisis.equals( "num.num" ) ||
             preAnalisis.equals( "(" ) ||
             preAnalisis.equals( "literal" )  ) {
            // TERMINO → FACTOR   TERMINO2 
            FACTOR( FACTOR ); 
            TERMINO2( TERMINO2 );
            
            // Accion semantica 46
            if ( analizarSemantica ) {
                
                if ( FACTOR.tipo != ERROR_TIPO &&
                     TERMINO2.tipo != ERROR_TIPO ) {
                    
                    if ( TERMINO2.tipo.equals( VACIO ) && FACTOR.tipo != VACIO  ) {
                        
                        TERMINO.tipo = FACTOR.tipo;
                        
                    }
                   
                        
                    else if ( FACTOR.tipo.equals( "integer" ) && 
                             TERMINO2.tipo.equals( "integer" ) ) {
                            
                            TERMINO.tipo = "integer";
                            
                        }
                    else if ( FACTOR.tipo.equals("real") || TERMINO2.tipo.equals("real") ) {
                            
                            TERMINO.tipo = "real";
                            
                        }
                    
                     
                    
                }
                else {
                    
                    TERMINO.tipo = ERROR_TIPO;
                    
                }
                
            }
            // Fin accion semantica
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
        
        Atributos FACTOR = new Atributos();
        Atributos TERMINO21 = new Atributos();
        
        if ( preAnalisis.equals( "opmult" ) ) {
            
            // TERMINO2 → opmult  FACTOR  TERMINO2
            emparejar( "opmult" );
            FACTOR( FACTOR );
            TERMINO2( TERMINO21 );
            
            // Accion semantica 52
            if ( analizarSemantica ) {
                
                /*if ( FACTOR.tipo != ERROR_TIPO && 
                     TERMINO21.tipo != ERROR_TIPO ) {
                    
                    if ( FACTOR.tipo != "string" ) {
                        
                        if ( TERMINO21.tipo != VACIO ) {
                            
                            TERMINO2.tipo = FACTOR.tipo;
                            
                        }
                        else { 
                            if ( FACTOR.tipo.equals( "integer" ) &&
                                      TERMINO2.tipo.equals( "integer" ) ) {
                            
                                TERMINO2.tipo = "integer";
                            }
                        
                            else {
                            
                                TERMINO2.tipo = "real";
                            
                            }
                        
                        }
                        
                    }
                    else {
                        
                        TERMINO2.tipo = ERROR_TIPO;
                        
                    }
                    
                }
                else {
                    
                    TERMINO2.tipo = ERROR_TIPO;
                    
                }
                
                */
                 if ( TERMINO21.tipo != ERROR_TIPO &&
                     FACTOR.tipo != ERROR_TIPO ) {
                    
                    if ( TERMINO21.tipo != "string" ) {
                        
                        if ( TERMINO21.tipo.equals( VACIO ) && FACTOR.tipo != VACIO ) {
                            
                            TERMINO2.tipo = FACTOR.tipo;
                            
                        }
                       
                        else if ( TERMINO21.tipo.equals( "integer" ) &&
                                 FACTOR.tipo.equals( "integer" ) ) {
                                
                                TERMINO2.tipo = "integer";
                                
                            }
                        else {
                                
                                TERMINO2.tipo = "real";
                                
                            }
                            
                        
                        
                    }
                    else {
                        
                        TERMINO2.tipo = ERROR_TIPO;
                        
                    }
                    
                }
                else {
                    
                    TERMINO2.tipo = ERROR_TIPO;
                    
                }
            }
            // Fin accion semantica
            
        }
        else {
            // TERMINO2 → ϵ 
            
            // Accion semantica 53
            if ( analizarSemantica ) {
                
                TERMINO2.tipo = VACIO;
                
            }
            // Fin accion semantica
        }
         
        
    }
    //--------------------------------------------------------------------------
    // Autor: Alejandro Saucedo Ramirez
    // primeros ( VARIABLE ) = { [ }
    private void VARIABLE ( Atributos VARIABLE ) {
        
        Atributos EXPRESION = new Atributos();
        
        if ( preAnalisis.equals( "[" ) ) {
            
            // VARIABLE → [ EXPRESION ] 
            emparejar( "[" );
            EXPRESION( EXPRESION );
            emparejar( "]" );
            
            // Accion semantica 39
            if ( analizarSemantica ) {
                
               
                VARIABLE.tipo = EXPRESION.tipo;
                
            }
            // Fin accion semantica
        }
        else {
            error( "ERROR EN EL PROCEDURE [VARIABLE]. " +
                   "Se esperaba '['. (" +
                   "Linea: " +
                   cmp.be.preAnalisis.getNumLinea() +
                   ")" );
        }
          
        
    }
    //--------------------------------------------------------------------------
    


    
}

//------------------------------------------------------------------------------
//::