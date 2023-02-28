package secundario;


public class Coche{
	
	private int id;
    private String matricula, modelo, marca, color;

    public Coche(int id, String matricula, String marca, String modelo, String color){
        this.id = id;
        this.matricula = matricula;
        this.modelo = modelo;
        this.marca = marca;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    public String getMarca() {
		return marca;
	}

	@Override
    public String toString() {
        return "ID: " + id +
                ", Matrícula: " + matricula + 
                ", Marca: " + marca +
                ", Modelo: " + modelo + 
                ", Color: " + color;
    }
}
