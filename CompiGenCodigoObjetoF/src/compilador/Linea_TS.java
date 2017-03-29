/*:-----------------------------------------------------------------------------
 *:                       INSTITUTO TECNOLOGICO DE LA LAGUNA
 *:                     INGENIERIA EN SISTEMAS COMPUTACIONALES
 *:                         LENGUAJES Y AUTOMATAS II           
 *: 
 *:        SEMESTRE: ______________            HORA: ______________ HRS
 *:                                   
 *:               
 *:    # Clase que representa una localidad de la Tabla de Simbolos
 *                 
 *:                           
 *: Archivo       : Linea_TS.java
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

public class Linea_TS 
{
    
        private String complex;
        private String lexema;
        private String tipo;
        private String ambito;
        private int sigUso;
        //---------------------------------------------------------------------
       //Constructores 
        public Linea_TS()
        {
            complex = "";
            lexema  = "";
            tipo    = "";
            ambito  = "";
        }

        public Linea_TS( String _complex, String _lexema, String _tipo, String _ambito)
        {
            complex = _complex;
            lexema  = _lexema;
            tipo    = _tipo;
            ambito  = _ambito;
        }

        public Linea_TS(Linea_TS T)
        {
            this.complex = T.complex;
            this.lexema  = T.lexema;
            this.tipo    = T.tipo;
            this.ambito  = T.ambito;
        }
        
        //----------------------------------------------------------------------
       //Metodos get/set
       
        public void setComplex ( String Complex )
        {
            complex = Complex;
        }
        
        public String getComplex ( )
        {
            return complex;
        }
        
        //----------------------------------------------------------------------
        
         public void setLExema ( String Lexema )
        {
            lexema = Lexema;
        }
        
        public String getLexema ( )
        {
            return lexema;
        }
        //----------------------------------------------------------------------
        
         public void setTipo ( String Tipo )
        {
            tipo = Tipo;
        }
        
        public String getTipo ( )
        {
            return tipo;
        }
        
       //----------------------------------------------------------------------
       
        public void setAmbito ( String Ambito )
        {
            ambito = Ambito;
        }
        
        public String getAmbito ( )
        {
            return ambito;
        }
         public void SetSigUso(int sigU){
            sigUso = sigU;            
        }
        public int getSigUso ()
        {
            return sigUso;
        }

        String getParametros() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        String getTipoMetodo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

         String getTipoArreglo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
}
