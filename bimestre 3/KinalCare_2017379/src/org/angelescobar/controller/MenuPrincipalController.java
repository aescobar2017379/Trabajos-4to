
package org.angelescobar.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.angelescobar.system.Principal;


public class MenuPrincipalController implements  Initializable{
    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void ventanaProgramador(){
        
        escenarioPrincipal.ventanaProgramador();
    }
    public void ventanaPaciente(){
        escenarioPrincipal.ventanaPaciente();
    }
}
