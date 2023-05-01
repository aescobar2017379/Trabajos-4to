package org.angelescobar.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.angelescobar.bean.Doctores;
import org.angelescobar.bean.Especialidades;
import org.angelescobar.db.Conexion;
import org.angelescobar.system.Principal;

public class DoctoresController  implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ACTUALIZAR, ELIMINAR, EDITAR, NINGUNO, CANCELAR};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Doctores> listaDoctor;
    private ObservableList<Especialidades> listaEspecialidad;
    

    @FXML private TextField txtNumeroColegiado;
    @FXML private TextField txtNombresDoctor;
    @FXML private TextField txtApellidosDoctor;
    @FXML private TextField txtTelefonoContacto;
    @FXML private ComboBox cmbCodigoEspecialidad;
    @FXML private TableView tblDoctores;
    @FXML private TableColumn colNumeroColegiado;
    @FXML private TableColumn colNombresDoctor;
    @FXML private TableColumn colApellidosDoctor;
    @FXML private TableColumn colTelefonoContacto;
    @FXML private TableColumn colCodigoEspecialidad;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    

   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoEspecialidad.setItems(getEspecialidad());
    }

    public void cargarDatos(){
        tblDoctores.setItems(getDoctor());
        colNumeroColegiado.setCellValueFactory(new PropertyValueFactory<Doctores,Integer>("numeroColegiado"));
        colNombresDoctor.setCellValueFactory(new PropertyValueFactory<Doctores,String>("nombresDoctor"));
        colApellidosDoctor.setCellValueFactory(new PropertyValueFactory<Doctores,String>("apellidosDoctor"));
        colTelefonoContacto.setCellValueFactory(new PropertyValueFactory<Doctores,String>("telefonoContacto"));
        colCodigoEspecialidad.setCellValueFactory(new PropertyValueFactory<Doctores,Integer>("codigoEspecialidad"));
}
    
     public ObservableList<Doctores>getDoctor(){
         ArrayList<Doctores> lista = new ArrayList<Doctores>();
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarDoctores}");
             ResultSet resultado = procedimiento.executeQuery();
             while (resultado.next()){
                 lista.add(new Doctores(resultado.getInt("numeroColegiado"),
                                      resultado.getString("nombresDoctor"),
                                      resultado.getString("apellidosDoctor"),
                                      resultado.getString("telefonoContacto"),
                                      resultado.getInt("codigoEspecialidad")));
             }
         }catch (Exception e){
             e.printStackTrace();
         }
         return listaDoctor = FXCollections.observableArrayList(lista);
    }
    
    
     public ObservableList<Especialidades>getEspecialidad(){
         ArrayList<Especialidades> lista =  new ArrayList<Especialidades>();
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEspecialidades}");
             ResultSet resultado = procedimiento.executeQuery();
             while (resultado.next()){
                 lista.add(new Especialidades(resultado.getInt("codigoEspecialidad"), resultado.getString("descripcion")));            
             }
         }catch (Exception e){
             e.printStackTrace();
         }
         return listaEspecialidad = FXCollections.observableArrayList(lista);
     }
     
     
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }  
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
}