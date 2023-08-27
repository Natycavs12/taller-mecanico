package Service_automotor;

import Service_automotor.Vista.Vista;
import Service_automotor.modelo.clases.Modelo;

/**
 *
 * @author natyb
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Modelo m = new Modelo();
        Vista v = new Vista(m);
        Controlador c = new Controlador(m,v);
        c.iniciar();
    }
    
}
