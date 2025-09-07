
public class Vuelo {
	
	// Atributos
	
	private String fecha;
	private Ruta ruta;
	private Avion avion;
	private Map<String, Tiquete> tiquetes;
	
	//Constructor
	
	public Vuelo(Ruta ruta, String fecha, Avion avion) {
		
		this ruta = ruta;
		this fecha = fecha;
		this avion = avion;
		
	}
	
	// Metodos
	
	public Ruta getRuta() {
		
		return ruta;
	}
	
	public String getFecha() {
		
		return fecha;
		
	}
	
	public Avion getAvion() {
		
		return avion;
		
	}
	
	public Collection<Tiquete> getTiquetes(){
		
		return tiquetes.values();
		
	}
	
	public int venderTiquetes(Cliente cliente, CalculadoraTarifas calculadora, int cantidad) {
		
		return 0;
	}
	
	public boolean equals(Object obj) {
		
		return true;
	}

}
