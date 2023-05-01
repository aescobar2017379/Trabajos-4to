/*
//crear el constructor de la clase public
// declarar una variable privada y estatica del formulario que se requiere utilizar ... se recomienda JFrame
// agregar libreria 
// crearemos un método para la instancia del objeto 
// agregar el paquete de login
 */
package org.angelescobar.clases;

import javax.swing.JFrame;
import org.angelescobar.system.Login;


public class LoginSingleton {
    
    private static JFrame log;
    private LoginSingleton(){
        
    }
     
    public static JFrame getinstance(){
        if(log == null)
            log = new Login();
        return log;
    }
}
