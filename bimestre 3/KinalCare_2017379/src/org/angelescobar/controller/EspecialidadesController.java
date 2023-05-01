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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.angelescobar.bean.Especialidades;
import org.angelescobar.db.Conexion;
import org.angelescobar.system.Principal;

public class EspecialidadesController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, ACTUALIZAR, CANCELAR, GUARDAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Especialidades> listaEspecialidades;
    @FXML private TextField txtCodigoEspecialidad;
    @FXML private TextField txtDescripcion;
    @FXML private GridPane grpDatos;
    @FXML private TableView tblEspecialidades;
    @FXML private TableColumn colCodigoEspecialidad;
    @FXML private TableColumn colDescripcion;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnBuscar;
    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgBuscar;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                cargarDatos();
    }
    
    public void cargarDatos(){
        tblEspecialidades.setItems(getEspecialidad());
        colCodigoEspecialidad.setCellValueFactory(new PropertyValueFactory<Especialidades,Integer>("codigoEspecialidad"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Especialidades,String>("descripcion"));
    }
    
    public void seleccionarElemento(){
        if(tblEspecialidades.getSelectionModel().getSelectedItem() !=null){
        txtCodigoEspecialidad.setText(String.valueOf(((Especialidades)tblEspecialidades.getSelectionModel().getSelectedItem()).getCodigoEspecialidad()));
        txtDescripcion.setText(((Especialidades)tblEspecialidades.getSelectionModel().getSelectedItem()).getDescripcion());
        }else{
            JOptionPane.showConfirmDialog(null, "No existen registros");
        }
    }
    
    public ObservableList<Especialidades>getEspecialidad(){
        ArrayList<Especialidades> lista = new ArrayList<Especialidades>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEspecialidades}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Especialidades(resultado.getInt("codigoEspecialidad"),
                                             resultado.getString("descripcion")
                ));      
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaEspecialidades = FXCollections.observableArrayList(lista);
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnBuscar.setDisable(true);
                imgAgregar.setImage(new Image("/org/oliverperez/image/Agregar5.png"));
                imgEliminar.setImage(new Image("/org/oliverperez/image/Cancelar5.png"));
                txtCodigoEspecialidad.setEditable(false);
                tipoDeOperacion = operaciones.GUARDAR;
            break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnBuscar.setDisable(false);
                imgAgregar.setImage(new Image("/org/oliverperez/image/Agregar2.png"));
                imgEliminar.setImage(new Image("/org/oliverperez/image/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;
        }
                
    }
    
    public void guardar(){
        Especialidades registro = new Especialidades();
        registro.setDescripcion(txtDescripcion.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEspecialidad(?)}");       procedimiento.setInt(1, registro.getCodigoEspecialidad());
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.execute();
            listaEspecialidades.add(registro);
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnBuscar.setDisable(false);
                imgAgregar.setImage(new Image("/org/oliverperez/image/Agregar2.png"));
                imgEliminar.setImage(new Image("/org/oliverperez/image/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
            break;
            default:
                if(tblEspecialidades.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"¿Está seguro de eliminar este elemento?","Eliminar Especialidad", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEspecialidad(?)}");
                            procedimiento.setInt(1,((Especialidades)tblEspecialidades.getSelectionModel().getSelectedItem()).getCodigoEspecialidad());
                            procedimiento.execute();
                            listaEspecialidades.remove(tblEspecialidades.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    
                }else{
                    JOptionPane.showConfirmDialog(null,"Debe seleccionar un elemneto");
                }
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblEspecialidades.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnBuscar.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/angelescobar/image/Actualizar4.png"));
                    imgBuscar.setImage(new Image("/org/angelescobar/image/Cancela3.png"));
                    activarControles();
                    txtCodigoEspecialidad.setEditable(false);
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showConfirmDialog(null,"Debe seleccionar un elemento");
            }
            break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnBuscar.setText("Buscar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/angelescobar/image/Editar.png"));
                imgBuscar.setImage(new Image("/org/angelescobar/image/BuscarD.png"));
                desactivarControles();
                limpiarControles();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEspecialidad(?, ?)}");
            Especialidades registro = (Especialidades)tblEspecialidades.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            procedimiento.setInt(1, registro.getCodigoEspecialidad());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void buscar(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnBuscar.setText("Buscar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/angelescobar/image/Editar.png"));
                imgBuscar.setImage(new Image("/org/angelescobar/image/BuscarD.png"));
                tblEspecialidades.getSelectionModel().clearSelection();
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }

    public void desactivarControles(){
        txtCodigoEspecialidad.setEditable(false);
        txtDescripcion.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoEspecialidad.setEditable(true);
        txtDescripcion.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoEspecialidad.clear();
        txtDescripcion.clear(); 
        tblEspecialidades.getSelectionModel().clearSelection();

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
