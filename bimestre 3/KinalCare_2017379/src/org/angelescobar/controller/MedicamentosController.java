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
//import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.angelescobar.bean.Medicamentos;
import org.angelescobar.db.Conexion;
import org.angelescobar.system.Principal;

public class MedicamentosController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Medicamentos> listaMedicamento;
    @FXML private TextField txtCodigoMedicamento;
    @FXML private TextField txtNombreMedicamento;
//    @FXML private GridPane grpDatos;
    @FXML private TableView tblMedicamentos;
    @FXML private TableColumn colCodigoMedicamento;
    @FXML private TableColumn colNombreMedicamento;
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
        tblMedicamentos.setItems(getMedicamento());
        colCodigoMedicamento.setCellValueFactory(new PropertyValueFactory<Medicamentos,Integer>("codigoMedicamento"));
        colNombreMedicamento.setCellValueFactory(new PropertyValueFactory<Medicamentos,String>("nombreMedicamento"));     
        
    }
    
    public void seleccionarElemento(){
        if(tblMedicamentos.getSelectionModel().getSelectedItem() !=null){
            txtCodigoMedicamento.setText(String.valueOf(((Medicamentos)tblMedicamentos.getSelectionModel().getSelectedItem()).getCodigoMedicamento()));
            txtNombreMedicamento.setText(((Medicamentos)tblMedicamentos.getSelectionModel().getSelectedItem()).getNombreMedicamento());   
        }else{
            JOptionPane.showConfirmDialog(null, "No existen registros");
        }
    }
    
    public ObservableList<Medicamentos>getMedicamento(){
        ArrayList<Medicamentos> lista = new ArrayList<Medicamentos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarMedicamentos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Medicamentos(resultado.getInt("codigoMedicamento"),
                                           resultado.getString("nombreMedicamento")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
            return listaMedicamento = FXCollections.observableArrayList(lista);
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnBuscar.setDisable(true);
                imgAgregar.setImage(new Image("/org/angelescobar/image/Agregar4.png"));
                imgEliminar.setImage(new Image("/org/angelescobar/image/remove.png"));
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
                imgAgregar.setImage(new Image("/org/angelescobar/image/Agregar5.png"));
                imgEliminar.setImage(new Image("/org/angelescobar/image/cancelar2.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        Medicamentos registro = new Medicamentos();
        registro.setCodigoMedicamento(Integer.parseInt(txtCodigoMedicamento.getText()));
        registro.setNombreMedicamento(txtNombreMedicamento.getText());
        try{
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarMedicamento(?, ?)}");
            procedimiento.setInt(1, registro.getCodigoMedicamento());
            procedimiento.setString(2, registro.getNombreMedicamento());
            procedimiento.execute();
            listaMedicamento.add(registro);
            
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
                imgAgregar.setImage(new Image("/org/angelescobar/image/Agregar5.png"));
                imgEliminar.setImage(new Image("/org/angelescobar/image/cancelar2.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                
            break; 
            default:
                if(tblMedicamentos.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"¿Está seguro de eliminar el elemento?","Eliminar Medicamento", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarMedicamento(?)}");
                            procedimiento.setInt(1, ((Medicamentos)tblMedicamentos.getSelectionModel().getSelectedItem()).getCodigoMedicamento());
                            procedimiento.execute();
                            listaMedicamento.remove(tblMedicamentos.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                            
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showConfirmDialog(null, "Debe seleccionar un elemento");
                }
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblMedicamentos.getSelectionModel().getSelectedItem() !=null){
                    btnEditar.setText("Actualizar");
                    btnBuscar.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/angelescobar/image/Actualizar5.png"));
                    imgBuscar.setImage(new Image("/org/angelescobar/image/Cancelar6.png"));
                    activarControles();
                    txtCodigoMedicamento.setEditable(false);
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showConfirmDialog(null, "Debe seleccionar un elemento");
            }
            break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnBuscar.setText("Buscar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/angelescobar/image/editar2.png"));
                imgBuscar.setImage(new Image("/org/angelescobar/image/busqueda.png"));
                desactivarControles();
                limpiarControles();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarMedicamento(?, ?)}");
            Medicamentos registro = (Medicamentos)tblMedicamentos.getSelectionModel().getSelectedItem();
            registro.setNombreMedicamento(txtNombreMedicamento.getText());
            procedimiento.setInt(1, registro.getCodigoMedicamento());
            procedimiento.setString(2, registro.getNombreMedicamento());
            procedimiento.execute();
        }catch(Exception e) {
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
                imgEditar.setImage(new Image("/org/angelescobar/image/editar2.png"));
                imgBuscar.setImage(new Image("/org/angelescobar/image/busqueda.png"));
                tblMedicamentos.getSelectionModel().clearSelection(); 
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    
    public void desactivarControles(){
        txtCodigoMedicamento.setEditable(false);
        txtNombreMedicamento.setEditable(false);        
    }
    
    public void activarControles(){
        txtCodigoMedicamento.setEditable(true);
        txtNombreMedicamento.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoMedicamento.clear();
        txtNombreMedicamento.clear();
        tblMedicamentos.getSelectionModel().clearSelection();
    }
 
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal() {
        escenarioPrincipal.menuPrincipal();
    }
}
