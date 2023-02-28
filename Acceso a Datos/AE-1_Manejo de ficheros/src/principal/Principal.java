package principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import secundario.Metodos;

public class Principal {
	public static void main(String[] args) {

        Scanner lector = new Scanner(System.in);
        boolean cerrar = false;
        
        // M�todo est�tico que carga un arrayList con los coches que contiene el fichero "coches.dat". 
        // De no existir, crea un arraylist vacio.
     	Metodos.cargarLista();   
     		
        // Mientras no se pulse la opci�n 5 (la de cerrar) mostrar� siempre el men� y pedir� una acci�n al usuario.
        while(!cerrar) {  
        	// Muestra el men� en pantalla.
            Metodos.showMenu();       
            
            // Solicita una acci�n al usuario.
            System.out.print("\n>> ");
            String opc = lector.next();     

            // En funci�n de la opci�n deseada, se ejecutar� un m�todo u otro.
            switch (opc) {
                case "1":   
                	// Pide los datos necesarios al usuario, crea el objeto coche y lo a�ade al arraylist en memoria.
                    Metodos.addCoche();
                    break;

                case "2":
                	// Pide el id del coche al usuario, lo busca en el ArrayList y lo elimina del mismo.
                    Metodos.delCoche();
                    break;

                case "3":
                    // Pide el id del coche al usuario, lo busca en el ArrayList y ejecuta y muestra su m�todo toString().
                    Metodos.consultCoche();
                    break;

                case "4":
                	// Muestra todos los toString() de los coches del ArrayList.
                    Metodos.listCoches();
                    break;

                case "5":
                	// Exporta a un CSV (separado por ";") todos los coches.
                    Metodos.exportCsv();
                    break;

                case "6":
                	// Guarda la informaci�n del ArrayList en memoria en el fichero binario "coches.dat". De no existir,
                	// lo crea.
                    Metodos.safe();
                    
                    // Cerramos el objeto Scanner y cambiamos la variable "cerrar" a true para salir del bucle, y por tanto,
                    // del programa.
                    lector.close();
                    cerrar = true;
                    System.out.println("\nSaliendo del programa...");
                    break;

                default:
                    System.out.println("Opci�n incorrecta.");
            }
        }
    }
}
