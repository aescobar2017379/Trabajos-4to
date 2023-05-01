
package org.angelescobar.bean;

import java.util.Date;


public class Paciente {
    private int codigoPaciente;
    private String nombresPaciente;
    private String apelidosPaciente;
    private String sexo;
    private Date fechaNacimiento;
    private String direccionPaciente;
    private String TelefonoPersonal;
    private Date FechaPrimeraVisita;

    public Paciente() {
    }

    public Paciente(int codigoPaciente, String nombresPaciente, String apelidosPaciente, String sexo, Date fechaNacimiento, String direccionPaciente, String TelefonoPersonal, Date FechaPrimeraVisita) {
        this.codigoPaciente = codigoPaciente;
        this.nombresPaciente = nombresPaciente;
        this.apelidosPaciente = apelidosPaciente;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.direccionPaciente = direccionPaciente;
        this.TelefonoPersonal = TelefonoPersonal;
        this.FechaPrimeraVisita = FechaPrimeraVisita;
    }

    

    public int getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(int codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }

    public String getNombresPaciente() {
        return nombresPaciente;
    }

    public void setNombresPaciente(String nombresPaciente) {
        this.nombresPaciente = nombresPaciente;
    }

    public String getApelidosPaciente() {
        return apelidosPaciente;
    }

    public void setApelidosPaciente(String apelidosPaciente) {
        this.apelidosPaciente = apelidosPaciente;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccionPaciente() {
        return direccionPaciente;
    }

    public void setDireccionPaciente(String direccionPaciente) {
        this.direccionPaciente = direccionPaciente;
    }

    public String getTelefonoPersonal() {
        return TelefonoPersonal;
    }

    public void setTelefonoPersonal(String TelefonoPersonal) {
        this.TelefonoPersonal = TelefonoPersonal;
    }

    public Date getFechaPrimeraVisita() {
        return FechaPrimeraVisita;
    }

    public void setFechaPrimeraVisita(Date FechaPrimeraVisita) {
        this.FechaPrimeraVisita = FechaPrimeraVisita;
    }

    public void getFechaPrimeraVisita(Date selectedDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    


    
    
}

