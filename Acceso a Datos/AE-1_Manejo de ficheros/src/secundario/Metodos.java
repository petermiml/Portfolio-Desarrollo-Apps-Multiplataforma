package secundario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Metodos {

	// Creamos el ArrayList de coches vac�o.
    private static List<Coche> listaCoches;
    
    // M�todo que muestra el men�.
    public static void showMenu(){
        System.out.println("====== Bienvenido a la aplicaci�n del concesionario. �Qu� deseas hacer? ======\n");
        System.out.println("    1. A�adir nuevo coche.\n" +
                "    2. Borrar coche por id.\n" +
                "    3. Consulta coche por id.\n" +
                "    4. Listado de coches.\n" +
                "    5. Exportar coches a archivo CSV.\n" +
                "    6. Terminar el programa.");
    }
    
    // M�todo que instancia la lista de coches. En caso de que haya un archivo "coches.dat", cargar� en dich
    public static void cargarLista(){
        // Primero creamos el ArrayList, que en principio estar� vac�o.
        listaCoches = new ArrayList<Coche>();

        // Ahora creamos el objeto file.
        File file = new File("coches.dat");

        // Si el fichero existe, ir� leyendo atributos, guardar� cada atributo en una variable y despu�s,
        // crear� un objeto con dichos atributos. Finalmente, a�adir� el objeto al ArrayList.
        // Repetir� el proceso hasta que haya le�do hasta el final del fichero.
        if(file.exists()) {

            boolean eof;
            try (FileInputStream fichero = new FileInputStream("coches.dat");
                 DataInputStream lector = new DataInputStream(fichero)) {
                eof = false;
                while (!eof) {
                    try {
                        int id = lector.readInt();
                        String matricula = lector.readUTF();
                        String marca = lector.readUTF();
                        String modelo = lector.readUTF();
                        String color = lector.readUTF();

                        Coche coche = new Coche(id, matricula, marca, modelo, color);
                        listaCoches.add(coche);
                    } catch (EOFException e1) {
                        eof = true;
                    } catch (IOException e2) {
                        System.out.println("Ha ocurrido un error al leer los registros.");
                        System.out.println(e2.getMessage());
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Ha ocurrido un error al leer el fichero");
                System.out.println(e.getMessage());
            }
        }
        
        // Si no existe, no pasa nada, porque igualmente tendremos nuestro ArrayList vac�o.
    }

    // M�todo que solicita datos al usuario sobre un coche para a�adirlos al arraylist.
    public static void addCoche(){
    	// Creamos el objeto Scanner para solicitar datos al usuario
        Scanner lector = new Scanner(System.in);
        System.out.print("\nIntroduce el id: ");
        int id = lector.nextInt();
        lector.nextLine();
        
        //Creamos una variable para controlar si el ID o matr�cula introducidas se encuentran en el ArrayList.
        boolean existe = false;
        

        // Recorre el ArrayList buscando dicho id. 
        for(int i = 0; i < listaCoches.size(); i++){
            Coche c = listaCoches.get(i);
            if(id == c.getId()){	// Si lo encuentra, sale del bucle y cambia la variable "existe" a true.
                existe = true;	
                break;
            }
        }

        //Si la variable "existe" es true, indica que no puede a�adirse el coche. 
        // Si no, repite el proceso pero con la matricula
        if(existe){
            System.out.println("\nEl id introducido ya existe. No puede a�adirse el coche.\n");
        }else {
            System.out.print("\nIntroduce la matricula: ");
            String matricula = lector.nextLine();

            // Recorre el ArrayList buscando la matr�cula. Si la encuentra, indica que no puede a�adirse el coche.

            existe = false;

            for (int i = 0; i < listaCoches.size(); i++) {
                Coche c = listaCoches.get(i);
                if (matricula.equals(c.getMatricula())) {
                    existe = true;
                    break;
                }
            }

            // Si ni la matricula ni el id estan en el ArrayList, sigue pidiendo datos.
            if (existe) {
                System.out.println("\nLa matr�cula introducida ya existe. No puede a�adirse el coche.\n");
            } else {

                System.out.print("\nIntroduce la marca: ");
                String marca = lector.nextLine();
            	
                System.out.print("\nIntroduce el modelo: ");
                String modelo = lector.nextLine();
                
                System.out.print("\nIntroduce el color: ");
                String color = lector.nextLine();

                // Una vez tiene todos los datos, crea un objeto de tipo coche con los mismos y lo a�ade al ArrayList.
                Coche coche = new Coche(id, matricula, marca, modelo, color);
                listaCoches.add(coche);
                System.out.println("\n\nCOCHE A�ADIDO CON �XITO.\n");
            }
        }
    }

    // M�todo que solicita el id al usuario, y en caso de que se encuentre en el ArrayList, utiliza el 
    // m�todo remove() para eliminar ese objeto del mismo.
    public static void delCoche(){
        // Si el tama�o del ArrayList es 0, eso significa que no hay coches, por lo que es absurdo pedirle
    	// el dato al usuario.
        if(listaCoches.size()==0) {
        	System.out.println("\nNo hay coches almacenados en la base de datos\n");
        	
        }else {
        	Scanner lector = new Scanner(System.in);
            System.out.print("\nIntroduce el id del coche que deseas borrar: ");
            int idBorrar = lector.nextInt();
            lector.nextLine();
            
	        // Recorre el ArrayList y si encuentra un coche con el id introducido por el usuario, lo elimina del ArrayList.
	        for(int i = 0; i < listaCoches.size(); i++){
	            Coche c = listaCoches.get(i);
	            if(idBorrar == c.getId()){
	                listaCoches.remove(c);
	                System.out.println("\nCOCHE BORRADO\n");
	                break;
	            // Si no encuentra ninguno, indica que no se encuentra dicho coche en la base de datos.
	            }else if(i == listaCoches.size()-1){
	                System.out.println("\nEl coche no se encuentra en la base de datos.\n");
	            }
	        }
        }
        
    }

    // M�todo que solicita un id y busca el coche con dicho ID en el ArrayList. De existir, muestra su toString()
    public static void consultCoche(){
    	// Si el tama�o del ArrayList es 0, eso significa que no hay coches, por lo que es absurdo pedirle
    	// el dato al usuario para buscarlo.
        if(listaCoches.size()==0) {
        	System.out.println("\nNo hay coches almacenados en la base de datos\n");
	        }else {
	    	Scanner lector = new Scanner(System.in);
	        System.out.print("\nIntroduce el id del coche que deseas buscar: ");
	        int idBuscado = lector.nextInt();
	
	        // Funcionamiento similar al de eliminar el coche.
	        // Recorreremos cada uno de los objetos en el ArrayList. Si el id del objeto iterado es la buscada,
	        // imprime la informaci�n de dicho coche. Si no, indicar� que el coche ya existe.
	        for(int i = 0; i < listaCoches.size(); i++){
	            Coche c = listaCoches.get(i);
	            if(idBuscado == c.getId()){
	                System.out.println("\nINFORMACI�N DEL COCHE ENCONTRADA:\n\n " + c.toString() + "\n");
	                break;
	            }else if(i == listaCoches.size()-1){
	                System.out.println("\nEl coche no se encuentra en la base de datos.\n");
	            }
	        }
        }
    }

    // M�todo que recorre el ArrayList y muestra los toString de todos los objetos en el mismo.
    public static void listCoches(){
        // Saca el listado de coches en el ArrayList. 
    	// Recorre el ArrayList y va mostrando el toString de cada objeto iterado.
        
    	if(listaCoches.size()>0) {
    		System.out.println("\nLISTADO DE COCHES: \n");

            for(int i = 0; i < listaCoches.size(); i++){
                Coche c = listaCoches.get(i);
                System.out.println("COCHE " + (i+1) + "-> " + c.toString());
            }
            System.out.println();
    	}else {
    		System.out.println("\nNo hay coches almacenados en la base de datos.\n");
    	}
    	
    }

    // M�todo que exporta la informaci�n en el ArrayList a un fichero .csv separado por ";"
    public static void exportCsv(){
        // Exporta la informaci�n a un fichero .csv (de no existir, se crea).
        try(FileWriter fichero = new FileWriter("coches.csv");
            BufferedWriter escritor = new BufferedWriter(fichero)){
            
        	// Primero escribo una linea como cabecera para indicar qu� es cada cosa en el csv.
        	escritor.write("ID");
        	escritor.write(";");
        	
        	escritor.write("Matr�cula");
        	escritor.write(";");
        	
        	escritor.write("Marca");
        	escritor.write(";");
        	
        	escritor.write("Modelo");
        	escritor.write(";");
        	
        	escritor.write("Color");
        	escritor.write(";");
        	escritor.newLine();
        	
        	// Por cada coche en el ArrayList escribir� los atributos del coche en el archivo.
        	// Separo cada valor por ";" porque normalmente los .csv tienen ese formato.
            for(Coche c: listaCoches){
            	
                escritor.write(String.valueOf(c.getId()));
                escritor.write(";");

                escritor.write(c.getMatricula());
                escritor.write(";");

                escritor.write(c.getMarca());
                escritor.write(";");
                
                escritor.write(c.getModelo());
                escritor.write(";");

                escritor.write(c.getColor());
                escritor.newLine();	// Hago un salto de linea al final.
                
                
            }
            // Finalmente informo al usuario de que se ha exportado la informaci�n.
            System.out.println("\nSE HA EXPORTADO LA INFORMACI�N AL ARCHIVO \"coches.csv\" CORRECTAMENTE.\n");
        }catch (IOException e) {
            System.out.println("Ha ocurrido un error al escribir datos en el fichero");
            System.out.println(e.getMessage());
        }
    }

    // M�todo que crea el objeto "coches.dat" en caso de no existir o lo sobreescribe en caso de existir con los
    // datos que est�n en el ArrayList cargado
    public static void safe(){
        // En el caso de no existir el fichero coches.dat, se crea.
    	// Tanto si existe como si no, carga en dicho fichero (sobreescribi�ndolo) los datos del ArrayList en memoria.
        try(FileOutputStream fichero = new FileOutputStream("coches.dat");
            DataOutputStream escritor = new DataOutputStream(fichero)){
            // Por cada coche en el ArrayList escribir� las siguientes l�neas en el archivo
            for(Coche c: listaCoches){
                escritor.writeInt(c.getId());
                escritor.writeUTF(c.getMatricula());
                escritor.writeUTF(c.getMarca());
                escritor.writeUTF(c.getModelo());
                escritor.writeUTF(c.getColor());
            }
        }catch (IOException e) {
            System.out.println("Ha ocurrido un error al escribir datos en el fichero");
            System.out.println(e.getMessage());
        }
    }
    
    


}
