package org.angelescobar.controller;


import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
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
import org.angelescobar.bean.Paciente;
import org.angelescobar.db.Conexion;
import org.angelescobar.system.Principal;


public class PacientesController implements Initializable {
    private Principal escenarioPrincipal; 
    private enum operaciones{NUEVO, AGREGA, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO, GUARDA};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private operaciones tipoDeOperaciones = operaciones.GUARDA;
    private ObservableList<Paciente>listaPaciente;
    private DatePicker fNacimiento, fPV;
    @FXML private TextField txtcodigoPaciente;
    @FXML private TextField txtnombresPaciente;
    @FXML private TextField txtapellidosPaciente;
    @FXML private TextField txtsexo;
    @FXML private TextField txtdireccionPaciente;
    @FXML private TextField txtTelefonoPersonal;
    @FXML private GridPane grpFechas;
    @FXML private TableView tblPacientes;
    @FXML private TableColumn colcodigoPaciente;
    @FXML private TableColumn colnombresPaciente;
    @FXML private TableColumn colapellidosPaciente;
    @FXML private TableColumn colsexo;
    @FXML private TableColumn colfechaNacimiento;
    @FXML private TableColumn coldireccionPaciente;
    @FXML private TableColumn colTelefonoPersonal;
    @FXML private TableColumn colFechaPrimeraVisita;
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
        fNacimiento = new DatePicker(Locale.ENGLISH);
        fNacimiento.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fNacimiento.getCalendarView().todayButtonTextProperty().set("Today");
        fNacimiento.getCalendarView().setShowWeeks(false);
        grpFechas.add(fNacimiento, 3, 1);
        fPV = new DatePicker(Locale.ENGLISH);
        fPV.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fPV.getCalendarView().todayButtonTextProperty().set("Today");
        fPV.getCalendarView().setShowWeeks(true);
        grpFechas.add(fPV, 4, 2);
        fNacimiento.getStylesheets().add("org/angelescobar/resource/DatePicker.css");
        fPV.getStylesheets().add("org/angelescobar/resource/DatePicker.css");
    }   

     public void cargarDatos(){
        tblPacientes.setItems(getPaciente());
        colcodigoPaciente.setCellValueFactory(new PropertyValueFactory<Paciente,Integer>("codigoPaciente"));
        colnombresPaciente.setCellValueFactory(new PropertyValueFactory<Paciente,String>("nombresPaciente"));
        colapellidosPaciente.setCellValueFactory(new PropertyValueFactory<Paciente,String>("apellidosPaciente"));
        colsexo.setCellValueFactory(new PropertyValueFactory<Paciente,String>("sexo"));
        colfechaNacimiento.setCellValueFactory(new PropertyValueFactory<Paciente,Date>("fechaNacimiento"));
        coldireccionPaciente.setCellValueFactory(new PropertyValueFactory<Paciente,String>("direccionPaciente"));
        colTelefonoPersonal.setCellValueFactory(new PropertyValueFactory<Paciente,String>("telefonoPersonal"));
        colFechaPrimeraVisita.setCellValueFactory(new PropertyValueFactory<Paciente,Date>("fechaPrimeraVisita"));
        
    }
    
    
    
    public void seleccionarElemento() {
//        txtcodigoPaciente.setText(String.valueOf(((Paciente)tblPacientes.getSelectionModel().getSelectedItems()).getCodigoPaciente()));
        txtcodigoPaciente.setText(String.valueOf(((Paciente)tblPacientes.getSelectionModel().getSelectedItems()).getCodigoPaciente()));
}
    
    public ObservableList<Paciente>getPaciente(){
        ArrayList<Paciente> lista = new ArrayList<Paciente>();
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPacientes()}"); 
           ResultSet resultado = procedimiento.executeQuery();
           while (resultado.next()){
               lista.add(new Paciente( resultado.getInt("codigoPaciente"),
                                    resultado.getString("nombresPaciente"),
                                    resultado.getString("apellidosPaciente"),
                                    resultado.getString("sexo"),
                                    resultado.getDate("fechaNacimiento"),
                                    resultado.getString("direccionPaciente"),
                                    resultado.getString("TelefonoPersonal"),
                                    resultado.getDate("FechaPrimeraVisita")));
           }
       }catch (Exception e){
           e.printStackTrace();
       }
             return listaPaciente = FXCollections.observableArrayList(lista);
    }
    
         public void nuevo(){
             
             switch(tipoDeOperacion){
                 case NINGUNO:
                     activarControles();
                     btnNuevo.setText("Gardar");
                     btnEliminar.setText("Cancelar");
                     btnEditar.setDisable(true);
                     btnReporte.setDisable(true);
                     imgNuevo.setImage(new Image("org/angelescobar/image/guardar.png"));
                     imgEliminar.setImage(new Image("org/angelescobar/image/cancelar.png"));
                     tipoDeOperacion = operaciones.GUARDA;
                     
                     break;
                     
                 case GUARDA:
                     desactivarControles();
                     limpiarControles();
                     btnNuevo.setText("Nuevo");
                     btnEliminar.setText("Eliminar");
                     btnEditar.setDisable(false);
                     btnReporte.setDisable(false);
                     imgNuevo.setImage(new Image("org/angelescobar/image/Nuevo.png"));
                     imgEliminar.setImage(new Image("org/angelescobar/image/Eliminar.png"));
                     tipoDeOperaciones = operaciones.NINGUNO;
                     break;
             }
         }   
            
         public void guardar(){
    Paciente registro=new Paciente();
    registro.setCodigoPaciente(Integer.parseInt(txtcodigoPaciente.getText()));
    registro.setNombresPaciente(txtnombresPaciente.getText());
    registro.setApelidosPaciente(txtapellidosPaciente.getText());
    registro.setSexo(txtsexo.getText());
    registro.setFechaNacimiento(fNacimiento.getSelectedDate());
    registro.setTelefonoPersonal(txtTelefonoPersonal.getText());
    registro.setFechaPrimeraVisita(fPV.getSelectedDate());
    try{
    PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp Agregar Paciente(?,?,?,?,?,?,?,?)}");
    procedimiento.setInt(1,registro.getCodigoPaciente());
    procedimiento.setString(2,registro.getNombresPaciente());
    procedimiento.setString(3,registro.getApelidosPaciente());
    procedimiento.setString(4,registro.getSexo());
    procedimiento.setDate(6,new java.sql.Date(registro.getFechaNacimiento().getTime()));
    procedimiento.setString(7,registro.getTelefonoPersonal());
    procedimiento.setDate(8,new java.sql.Date(registro.getFechaPrimeraVisita().getTime()));
    procedimiento.execute();
    listaPaciente.add(registro);

                      
        }catch(Exception e){
            e.printStackTrace();

    }
            
         }      
         
         public void eliminar(){

    switch(tipoDeOperacion){
        case GUARDA:
            desactivarControles();
            limpiarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            imgNuevo.setImage(new Image("/org/angelescobar/image/Nuevo1.jpg"));
            imgEliminar.setImage(new Image("/org/angelescobar/image/Eliminarl.png"));
            tipoDeOperacion=operaciones.NINGUNO;
        break;
        default:
            if(tblPacientes.getSelectionModel().getSelectedItem()!=null){
               int respuesta=JOptionPane.showConfirmDialog(null,"Esta seguro de eliminar el registro?", "Eliminar Paciente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
               if(respuesta == JOptionPane.YES_NO_OPTION){
                try{
                    
                    PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPaciente (?)}");
                    procedimiento.setInt(1,((Paciente)tblPacientes.getSelectionModel().getSelectedItem()).getCodigoPaciente());
                    procedimiento.execute();
                    listaPaciente.remove(tblPacientes.getSelectionModel().getSelectedIndex());
                    limpiarControles();
                    }catch(Exception e)    {
                        e.printStackTrace();
                        
                    }
                
                

            
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un elemento");
                
        }
    }
}            
                
  }
         public void editar(){

    switch(tipoDeOperacion){
        case NINGUNO:
            if(tblPacientes.getSelectionModel().getSelectedItem()!=null){
            btnEditar.setText("Actualizar");
            btnReporte.setText("Cancelar");
            btnNuevo.setDisable(true);
            btnEliminar.setDisable(true);
            imgEditar.setImage(new Image("/org/angelescobar/image/Actualizar.png"));
            imgReporte.setImage(new Image("/org/angelescobar/image/Cancelar.png"));
            activarControles();
            txtcodigoPaciente.setEditable(false);
            tipoDeOperacion=operaciones.ACTUALIZAR;
        }else
            JOptionPane.showMessageDialog(null,"Debe de seleccionar un elemento.");
        break;
        case ACTUALIZAR:
            actualizar();
            btnEditar.setText("Editar");
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEliminar.setDisable(false);
            imgEditar.setImage(new Image("/org/angelescobar/image/Actualizar.png"));
            imgReporte.setImage(new Image("/org/angelescobar/image/Reporte.png"));
            desactivarControles();
            limpiarControles();
            cargarDatos();
            break;                                       
        } 
    }

         public void actualizar(){
         try{
             PreparedStatement procedimiento=Conexion.getInstance().getConexion().prepareCall("{call sp_EditarPaciente(?, ?, ?, ?, ?, ?, ?, ?)}");
             Paciente registro = (Paciente)tblPacientes.getSelectionModel().getSelectedItem();
             registro.setNombresPaciente(txtnombresPaciente.getText());
             registro.setApelidosPaciente(txtapellidosPaciente.getText());
             registro.setSexo(txtsexo.getText());
             registro.setFechaNacimiento(fNacimiento.getSelectedDate());
             registro.setDireccionPaciente(txtdireccionPaciente.getText());
             registro.setTelefonoPersonal(txtTelefonoPersonal.getText());
             registro.getFechaPrimeraVisita(fPV.getSelectedDate());
             procedimiento.setInt(1, registro.getCodigoPaciente());
             procedimiento.setString(2, registro.getNombresPaciente());
             procedimiento.setString(3, registro.getApelidosPaciente());
             procedimiento.setString(4, registro.getSexo());
             procedimiento.setDate(5, new java.sql.Date(registro.getFechaNacimiento().getTime()));
             procedimiento.setString(6, registro.getDireccionPaciente());
             procedimiento.setString(7, registro.getTelefonoPersonal());
             procedimiento.setDate(8, new java.sql.Date(registro.getFechaPrimeraVisita().getTime()));
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
            btnReporte.setText("Reporte");
            btnNuevo.setDisable(false);
            btnEditar.setDisable(false);
            imgEditar.setImage(new Image("/org/angelescobar/image/actualizar.png"));
            imgReporte.setImage(new Image("/org/angelescobar/image/Reporte.png"));
            tblPacientes.getSelectionModel().clearSelection();
    }
         }
         
         
    public void desactivarControles(){
        txtcodigoPaciente.setEditable(false);
        txtnombresPaciente.setEditable(false);
        txtapellidosPaciente.setEditable(false);
        txtsexo.setEditable(false);
        txtdireccionPaciente.setEditable(false);
        txtTelefonoPersonal.setEditable(false);
      
        
    }
    
    public void activarControles(){
        txtcodigoPaciente.setEditable(true);
        txtnombresPaciente.setEditable(true);
        txtapellidosPaciente.setEditable(true);
        txtsexo.setEditable(true);
        txtdireccionPaciente.setEditable(true);
        txtTelefonoPersonal.setEditable(true);
    }
    
    public void limpiarControles (){
        txtcodigoPaciente.clear();
        txtnombresPaciente.clear();
        txtapellidosPaciente.clear();
        txtsexo.clear();
        txtdireccionPaciente.clear();
        txtTelefonoPersonal.clear();
        tblPacientes.getSelectionModel().clearSelection();
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




