/*:-----------------------------------------------------------------------------
 *:                       INSTITUTO TECNOLOGICO DE LA LAGUNA
 *:                     INGENIERIA EN SISTEMAS COMPUTACIONALES
 *:                         LENGUAJES Y AUTOMATAS II           
 *: 
 *:        SEMESTRE: ______________            HORA: ______________ HRS
 *:                                   
 *:               
 *:    # Interfaz Java que permitira escuchar los errores procedentes desde el compilador
 *                 
 *:                           
 *: Archivo       : ErroresListener.java
 *: Autor         : Fernando Gil  
 *: Fecha         : 03/SEP/2013
 *: Compilador    : Java JDK 7
 *: Descripción   : Esta interfaz debera ser implementada por la Interfaz Grafica
 *                  de Usuario para poder recibir los mensajes de error procedentes
 *                  del Manejador de Errores del compilador. 
 *:                  
 *:           	     
 *: Ult.Modif.    :
 *:  Fecha      Modificó            Modificacion
 *:=============================================================================
 *:-----------------------------------------------------------------------------
 */


package compilador;


public interface ErroresListener {
    
    public void mostrarErrores  ( String error   );
    public void mostrarCodInt   ( String codint  );
    public void mostrarWarning  ( String warning );
    public void mostrarCodObj   ( String codobj  );
}
