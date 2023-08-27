package Service_automotor.modelo.clases;

/**
 *
 * @author natyb
 */
public class Automovil {
    
    private int IDAuto,anioFabricacion;
    private String patente, marca, modelo;
    private int idCliente;

    public Automovil(int IDAuto, String patente, String marca, String modelo, int anioFabricacion) {
        this.IDAuto = IDAuto;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
    }

    public Automovil(int IDAuto, String patente, String marca, String modelo, int anioFabricacion, int idCliente) {
        this.IDAuto = IDAuto;
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.idCliente = idCliente;
    }
    
    @Override
    public String toString() {
//        return "Automovil{" + "IDAuto=" + IDAuto + ", patente=" + patente + ", marca=" + marca + ", modelo=" + modelo + ", anioFabricacion=" + anioFabricacion + "}\n";
        return "{IDAuto: " + IDAuto + ", patente: " + patente + ", marca: " + marca + ", modelo: " + modelo + ", año de fabricación: " + anioFabricacion + "}\n";
    }

    public int getIDAuto() {
        return IDAuto;
    }

    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public int getIdCliente() {
        return idCliente;
    }
    
    
}
