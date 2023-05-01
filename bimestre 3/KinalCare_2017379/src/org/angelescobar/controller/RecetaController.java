package org.angelescobar.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.angelescobar.system.Principal;
import java.util.Date;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javax.swing.JOptionPane;
import org.angelescobar.bean.Doctores;
import org.angelescobar.bean.Receta;
import org.angelescobar.db.Conexion;

public class RecetaController implements Initializable{
    private Principal escenarioPrincipal;
    private DatePicker fReceta;

    public void setEscenarioPrinicpal(Principal aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}; 
    private operaciones tipoDeOperacion = operaciones.NINGUNO;     
    private ObservableList<Receta> listaReceta;
    private ObservableList<Doctores> listaDoctores;
    @FXML private GridPane grpFecha;
    @FXML private TextField txtCodRec;
    @FXML private ComboBox cmbNumCol;
    @FXML private TableView tblRecetas;
    @FXML private TableColumn colCodRec;
    @FXML private TableColumn colFecRec;
    @FXML private TableColumn colColegiado;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnReport;
    @FXML private Button btnEditar;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgReport;
    @FXML private ImageView imgEditar;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fReceta = new DatePicker(Locale.ENGLISH);
        fReceta.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fReceta.getCalendarView().todayButtonTextProperty().set("Today");
        fReceta.getCalendarView().setShowWeeks(false);
        grpFecha.add(fReceta, 2, 2);
        cmbNumCol.setItems(getDoctor());
    }
    public void cargarDatos(){
        tblRecetas.setItems(getReceta());
        colCodRec.setCellValueFactory(new PropertyValueFactory<Receta, Integer>("codigoReceta"));
        colFecRec.setCellValueFactory(new PropertyValueFactory<Receta, Date>("fechaReceta"));
        colColegiado.setCellValueFactory(new PropertyValueFactory<Receta, Integer>("numeroColegiado"));
       
        

        
        
    }
    public void seleccionarElemento (){
        txtCodRec.setText(String.valueOf(((Receta)tblRecetas.getSelectionModel().getSelectedItem()).getCodigoReceta()));
        cmbNumCol.selectionModelProperty().set(((Receta)tblRecetas.getSelectionModel().getSelectedItem()).getNumeroColegiado());
        fReceta.selectedDateProperty().set(((Receta)tblRecetas.getSelectionModel().getSelectedItem()).getFechaReceta()); 
    }
    
    public ObservableList<Receta>getReceta(){
        ArrayList<Receta> lista = new ArrayList<Receta>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarRecetas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Receta( 
                    resultado.getInt("codigoReceta"),
                    resultado.getDate("fechaReceta"),
                    resultado.getInt("numeroColegiado")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaReceta = FXCollections.observableArrayList(lista);
    }
    public ObservableList<Doctores>getDoctor(){
        ArrayList<Doctores> lista = new ArrayList<Doctores>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDoctores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Doctores(resultado.getInt("numeroColegiado"),
                                     resultado.getString("nombreDoctor"),
                                     resultado.getString("apellidoDoctor"),
                                     resultado.getString("telefonoContacto"),
                                     resultado.getInt("codigoEspecialidad")));
                
            }
        }catch(Exception e){
           
        }
        return listaDoctores = FXCollections.observableArrayList(lista);
    }
    @FXML
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReport.setDisable(true);
                imgNuevo.setImage(new Image("/org/angelescobar/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/angelescobar/images/Cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReport.setDisable(false);
                imgNuevo.setImage(new Image("/org/angelescobar/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/angelescobar/images/eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
      }  
    }
    public void guardar(){
        Receta registro = new Receta();
        registro.setCodigoReceta(Integer.parseInt(txtCodRec.getText()));
        registro.setFechaReceta(fReceta.getSelectedDate());
        registro.setNumeroColegiado(((Doctores)cmbNumCol.getSelectionModel().getSelectedItem()).getNumeroColegiado());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarReceta(?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoReceta());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaReceta().getTime()));
            procedimiento.setInt(3, registro.getNumeroColegiado());
            procedimiento.execute();
            listaReceta.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReport.setDisable(false);
                imgNuevo.setImage(new Image("/org/angelescobar/images/agregar.png"));
                imgEliminar.setImage(new Image("/org/angelescobar/images/eliminar.png"));
                tipoDeOperacion =operaciones.NINGUNO;
            break;
            default:
                if(tblRecetas.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?", "Eliminar Receta",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarReceta(?)}");
                            procedimiento.setInt(1, ((Receta)tblRecetas.getSelectionModel().getSelectedItem()).getCodigoReceta());
                            procedimiento.execute();
                            listaReceta.remove(tblRecetas.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                            
                        }catch(Exception e){
                            e.printStackTrace();  
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe Seleccionar un Elemento");
                }
            
        }
        
    }
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblRecetas.getSelectionModel().getSelectedItem() !=null){
                btnEditar.setText("Actualizar");
                btnReport.setText("Cancelar");
                btnNuevo.setDisable(true);
                btnEliminar.setDisable(true);
                imgEditar.setImage(new Image("/org/angelescobar/images/Guardar.png"));
                imgReport.setImage(new Image("/org/angelescobar/images/Cancelar.png"));
                activarControles();
                txtCodRec.setEditable(false);
                tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
                
                }
                   
            break;   
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReport.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/angelescobar/images/editar.png"));
                imgReport.setImage(new Image("/org/angelescobar/images/report.png"));
                desactivarControles();
                limpiarControles();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
    }
    public void actualizar(){
        try{
           
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarReceta(?,?,?)}");
            Receta registro = (Receta)tblRecetas.getSelectionModel().getSelectedItem();
            registro.setFechaReceta(fReceta.getSelectedDate());
            registro.setNumeroColegiado(((Receta)cmbNumCol.getSelectionModel().getSelectedItem()).getNumeroColegiado());
            procedimiento.setInt(1, registro.getCodigoReceta());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaReceta().getTime()));
            procedimiento.setInt(3, registro.getNumeroColegiado());
            procedimiento.execute();
                    }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReport.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/angelescobar/images/editar.png"));
                imgReport.setImage(new Image("/org/angelescobar/images/report.png"));
                tblRecetas.getSelectionModel().clearSelection();
                tipoDeOperacion = operaciones.NINGUNO;
            break;
        }
}
     public void desactivarControles(){
        txtCodRec.setEditable(false);
        cmbNumCol.setSelectionModel(null);
        fReceta.setSelectedDate(null);
        
        
        
    }
    public void activarControles(){
        txtCodRec.setEditable(true);
        cmbNumCol.setEditable(true);     
        
    }
    public void limpiarControles(){
        txtCodRec.clear();
        cmbNumCol.setSelectionModel(null);
        fReceta.setSelectedDate(null);
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
