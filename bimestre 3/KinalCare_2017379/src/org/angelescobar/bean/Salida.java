package org.angelescobar.bean;


public class Salida {
    
    public Salida() {
    }
    
    public void salir () {
        Salida app = new Salida();
        app.attachShutdownHuck();
        System.exit(0);
        
}

    public void attachShutdownHuck() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run(){
                
            }
        });
    }

}
