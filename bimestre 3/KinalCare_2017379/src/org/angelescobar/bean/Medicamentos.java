package org.angelescobar.bean;

public class Medicamentos {
    
    private int codigoMedicamento;
    private String nombreMedicamento;

    public Medicamentos() {
    }

    public Medicamentos(int codigoMedicamento, String nombreMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
        this.nombreMedicamento = nombreMedicamento;
    }

    public int getCodigoMedicamento() {
        return codigoMedicamento;
    }

    public void setCodigoMedicamento(int codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    @Override
    public String toString() {
        return getCodigoMedicamento()+" | "+getNombreMedicamento();
    }
    
    
    
}
