package Service_automotor.modelo.clases;

/**
 *
 * @author natyb
 */
public class Cliente {

    private String DNI, nombre, apellido, mail, celular;
    private int IDCliente;
    private boolean activo;

    public Cliente(int IDCliente, String DNI, String nombre, String apellido, String mail, String celular) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.IDCliente = IDCliente;
    }

    public Cliente(int IDCliente, String DNI, String nombre, String apellido, String mail, String celular, boolean activo) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.IDCliente = IDCliente;
        this.activo = activo;
    }

    @Override
    public String toString() {
//        return "Cliente{" + "DNI=" + DNI + ", nombre=" + nombre + ", apellido=" + apellido + ", mail=" + mail + ", celular=" + celular + ", IDCliente=" + IDCliente + "}\n";
        return "{ID: " + IDCliente + ", nombre: " + nombre + ", apellido: " + apellido + ", DNI: " + DNI + ", mail: " + mail + ", celular: " + celular + "}\n";
    }

    public String getDNI() {
        return DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getMail() {
        return mail;
    }

    public String getCelular() {
        return celular;
    }

    public int getIDCliente() {
        return IDCliente;
    }

    public boolean isActivo() {
        return activo;
    }

}
