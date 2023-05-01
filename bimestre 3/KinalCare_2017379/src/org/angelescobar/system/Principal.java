/*
nombre: Angel Gabriel Escobar Arevalo
seccion tecnica: IN5CV
carne: 2017379
fecha de creación: 22/04/2022
fecha de modificación: 26/04/2022
 */
package org.angelescobar.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import org.angelescobar.controller.DetalleRecetaController;
import org.angelescobar.controller.DoctoresController;
import org.angelescobar.controller.EspecialidadesController;
import org.angelescobar.controller.MedicamentosController;
import org.angelescobar.controller.MenuPrincipalController;
import org.angelescobar.controller.PacientesController;
import org.angelescobar.controller.ProgramadorController;
import org.angelescobar.controller.RecetaController;

/**
 *
 * @author angel
 */
public class Principal extends Application {
        private Stage escenarioPrincipal;
        private Scene escena;
        private final String PAQUETE_VISTA = "/org/angelescobar/view/";
        
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinalcare");
        escenarioPrincipal.getIcons().add(new Image("/org/angelescobar/image/Icono.png"));
//        Parent root = FXMLLoader.load(getClass().getResource("/org/angelescobar/view/MenuPrincipalView.fxml"));
//        Scene escena = new Scene (root);
//        escenarioPrincipal.setScene(escena);
        menuPrincipal();
        escenarioPrincipal.show();
        
        
    }
        
    public void menuPrincipal(){
           try {
               MenuPrincipalController ventanaMenu = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 600, 400);
               ventanaMenu.setEscenarioPrincipal(this);
           }catch (Exception e){
               e.printStackTrace();
           }
    }
    public void ventanaProgramador(){
     try{
        ProgramadorController VistaProgramador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml",600,400);
        VistaProgramador.setEscenarioPrincipal(this);
        
     }catch (Exception e){
          e.printStackTrace();
    }
} 
    public void ventanaPaciente(){
        try{
            PacientesController vistaPaciente = (PacientesController)cambiarEscena("PacientesView.fxml",1200,400);
            vistaPaciente.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
  
        public void ventanaEspecialidad(){
        try{
            EspecialidadesController vistaEspecialidades = (EspecialidadesController)cambiarEscena("EspecialidadesView.fxml",800,407);
            vistaEspecialidades.setEscenarioPrincipal(this);
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaMedicamentos(){
        try{
            MedicamentosController vistaMedicamentos = (MedicamentosController)cambiarEscena("MedicamentosView.fxml",1000,400);
            vistaMedicamentos.setEscenarioPrincipal(this);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDoctores(){
        try{
            DoctoresController vistaDoctores = (DoctoresController)cambiarEscena("DoctoresView.fxml",600,400);
            vistaDoctores.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaRecetas(){
        try{
            RecetaController vistaRecetas = (RecetaController)cambiarEscena("RecetasView.fxml",600,400);
            vistaRecetas.setEscenarioPrinicpal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDetalleReceta(){
        try{
            DetalleRecetaController vistaDetalleReceta = (DetalleRecetaController)cambiarEscena("DetalleRecetaView.fxml",600,400);
            vistaDetalleReceta.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader ();
            InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
            cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
            cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
            escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.sizeToScene();
            resultado = (Initializable)cargadorFXML.getController();
            
                
        return resultado;
    }
    
}
