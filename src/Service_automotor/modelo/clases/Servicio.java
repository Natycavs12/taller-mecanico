package Service_automotor.modelo.clases;

import java.time.LocalDate;

/**
 *
 * @author natyb
 */
public class Servicio {
    private int IDServicio, costo, cantKms;
    private java.time.LocalDate fechaRealizacion;
    private int IDAuto;

    public Servicio(int IDServicio, int costo, int cantKms, LocalDate fechaRealizacion) {
        this.IDServicio = IDServicio;
        this.costo = costo;
        this.cantKms = cantKms;
        this.fechaRealizacion = fechaRealizacion;
    }

    public Servicio(int IDServicio, int costo, int cantKms, LocalDate fechaRealizacion, int IDAuto) {
        this.IDServicio = IDServicio;
        this.costo = costo;
        this.cantKms = cantKms;
        this.fechaRealizacion = fechaRealizacion;
        this.IDAuto = IDAuto;
    }

    public int getIDServicio() {
        return IDServicio;
    }

    public int getCosto() {
        return costo;
    }

    public int getCantKms() {
        return cantKms;
    }

    public LocalDate getFechaRealizacion() {
        return fechaRealizacion;
    }

    public int getIDAuto() {
        return IDAuto;
    }

    public void setIDServicio(int IDServicio) {
        this.IDServicio = IDServicio;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void setCantKms(int cantKms) {
        this.cantKms = cantKms;
    }

    public void setFechaRealizacion(LocalDate fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public void setIDAuto(int IDAuto) {
        this.IDAuto = IDAuto;
    }
    
    @Override
    public String toString() {
//        return "Servicio{" + "IDServicio=" + IDServicio + ", costo=" + costo + ", cantKms=" + cantKms + ", fechaRealizacion=" + fechaRealizacion + "}\n";
//        return "{ID Servicio: " + IDServicio + ", costo: " + costo + ", cantidad de Kms: " + cantKms + ", fecha de Realización: " + fechaRealizacion + "}\n";
        return "{ID Servicio: " + IDServicio + ", costo: " + costo + ", cantidad de Kms: " 
                + cantKms + ", fecha de Realización: " + fechaRealizacion + ", ID Auto: " + IDAuto + "}\n";
    }
    
    
}
