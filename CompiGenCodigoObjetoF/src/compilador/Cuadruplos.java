/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.ArrayList;


public class Cuadruplos {
public ArrayList <Cuadruplo> cuadruplos;   
private Compilador cmp;
private int bloqeB = 1;
public  boolean              bandera = true;
public  int                  cont    = 0;
    private int contparam=1;

public Cuadruplos( Compilador c ){
   cuadruplos = new ArrayList <Cuadruplo>();
   cmp=c;
   
}

//Metodo por construir
 public void inicializar () {
        cuadruplos.clear();
        bloqeB = 1;
        cont=0;
        bandera = true;
    }

public void  insertar( Cuadruplo c ){
    
        if ( c.op.equals("") ){
            c.lider = true;           
            c.bloqueBasico = cont++;
            bandera = false;
        }
          
        if ( bandera ) {
            c.lider = true;
            c.bloqueBasico = cont++;
            bandera = false;
        }
       
        
        else if (  c.op.equals ( "goto")|| c.op.equals ( " return " ) || ( c.op.equals ( "<" )
                    || c.op.equals ( ">" ) || c.op.equals ( "!=" )
                    || c.op.equals ( "<=" ) || c.op.equals ( ">=" )
                   || c.op.equals ( "==" ) && c.resultado.substring(0, 4).equals( "etiq" ) ) ) {
            bandera=true;
           }
         
        
        c.bloqueBasico = cont;
        cuadruplos.add ( c );

}

public void RemoverTodo( ){
    cuadruplos.clear( );
}

public int GetTamaño( ){
    return cuadruplos.size( );
}

public ArrayList<Cuadruplo>  GetCuadruplos( ){
    return cuadruplos;
}

//Metodo que calcula los usos siguientes
    public void SigUsos () {
                

        for (int i = 0; i < cuadruplos.size(); i++) {
            Cuadruplo cuadruplo = cuadruplos.get(i);
            
            if ( !cuadruplo.arg1.equals ( "" ) )
                for (int j = i+1; j < cuadruplos.size(); j++) {
                        if((cuadruplos.get(i).bloqueBasico)==(cuadruplos.get(j).bloqueBasico))
                    {
                        int y=0;
                        String ytemp="";
                    if ( cuadruplos.get(i).arg1.equals(cuadruplos.get(j).arg1)  
                      || cuadruplos.get(i).arg1.equals(cuadruplos.get(j).arg2))
                  
                    {
                        cuadruplo.arg1SigUso = j+1;
                        cuadruplos.set(i, cuadruplo );
                       if(cuadruplos.get(i).arg1.substring(0, 1).equals("[") && cuadruplos.get(i).arg1.length()==4) 
                       {y = Integer.parseInt(cuadruplos.get(i).arg1.substring(1,3));
                           cmp.ts.anadeSigUso(y, j+1);}
                        else  if(cuadruplos.get(i).arg1.substring(0, 1).equals("["))
                        {
                         y = Integer.parseInt(cuadruplos.get(i).arg1.substring(1,2)); 
                         cmp.ts.anadeSigUso(y, j+1);
                        }
                        else  if (cuadruplos.get(i).arg1.substring(0,1).equals("t"))
                        { ytemp = cuadruplos.get(i).arg1;
                            cmp.ts.anadeSigUsoC(ytemp, j+1);}
                        else if (y==0)
                        { 
                         cmp.ts.anadeSigUso(y,0);}
                            
                   

                        break;
                    }
                    }
                }
            if ( !cuadruplo.arg2.equals("") )
                for (int j = i+1; j < cuadruplos.size(); j++) {
                        if((cuadruplos.get(i).bloqueBasico)==(cuadruplos.get(j).bloqueBasico))
                    { 
                        int z=0;
                        String ztemp="";
                    if ( cuadruplos.get(i).arg2.equals(cuadruplos.get(j).arg1)  
                      || cuadruplos.get(i).arg2.equals(cuadruplos.get(j).arg2))
                    //  || cuadruplos.get(i).arg2.equals(cuadruplos.get(j).resultado))
                    {
                        cuadruplo.arg2SigUso = j+1;
                        cuadruplos.set(i, cuadruplo );
                         if(cuadruplos.get(i).arg2.substring(0, 1).equals("[") && cuadruplos.get(i).arg2.length()==4) 
                       {z = Integer.parseInt(cuadruplos.get(i).arg2.substring(1,3));
                           cmp.ts.anadeSigUso(z, j+1);}
                        else  if(cuadruplos.get(i).arg2.substring(0, 1).equals("["))
                        {
                         z = Integer.parseInt(cuadruplos.get(i).arg2.substring(1,2)); 
                         cmp.ts.anadeSigUso(z, j+1);
                        }
                        else  if (cuadruplos.get(i).arg2.substring(0,1).equals("t"))
                        { ztemp = cuadruplos.get(i).arg2;
                            cmp.ts.anadeSigUsoC(ztemp, j+1);}
                        else if (z==0)
                        { 
                         cmp.ts.anadeSigUso(z,0);}
                        break;
                    }
                    }
            }
            if ( !cuadruplo.resultado.equals("")    )
            for (int j = i+1; j < cuadruplos.size(); j++) {
                    if((cuadruplos.get(i).bloqueBasico)==(cuadruplos.get(j).bloqueBasico))
                    { 
                        int x=0;
                        String xtemp="";
                if ( cuadruplos.get(i).resultado.equals(cuadruplos.get(j).arg1)  
                  || cuadruplos.get(i).resultado.equals(cuadruplos.get(j).arg2))
                 
                {
                    cuadruplo.resSigUso = j+1;
                    cuadruplos.set(i, cuadruplo );
               
                   
                       if(cuadruplos.get(i).resultado.substring(0, 1).equals("[") && cuadruplos.get(i).resultado.length()==4) 
                       {x = Integer.parseInt(cuadruplos.get(i).resultado.substring(1,3));
                           cmp.ts.anadeSigUso(x, j+1);}
                        else  if(cuadruplos.get(i).resultado.substring(0, 1).equals("["))
                        {
                         x = Integer.parseInt(cuadruplos.get(i).resultado.substring(1,2)); 
                         cmp.ts.anadeSigUso(x, j+1);
                        }
                        else  if (cuadruplos.get(i).resultado.substring(0,1).equals("t"))
                        { xtemp = cuadruplos.get(i).resultado;
                            cmp.ts.anadeSigUsoC(xtemp, j+1);}
                       else if (x==0)
                        { 
                         cmp.ts.anadeSigUso(x,0);}

                    break;
                }
                    }
            }
            
            if (  cuadruplo.op.equals ( "<" )
                    || cuadruplo.op.equals ( ">" ) || cuadruplo.op.equals ( "!=" )
                    || cuadruplo.op.equals ( "<=" ) || cuadruplo.op.equals ( ">=" )
                   || cuadruplo.op.equals ( "==" )|| cuadruplo.op.equals("goto") 
                    || cuadruplo.op.equals("") )
            {
                cuadruplo.resSigUso = 0;
                cuadruplos.set(i, cuadruplo);
            }
        }
       
        
        
    }


}
