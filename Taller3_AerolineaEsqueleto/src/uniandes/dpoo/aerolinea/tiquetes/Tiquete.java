
public class Tiquete {
	
	//Atributos
	
	private String codigo;
	private int tarifa;
	private boolean usado;
	private Cliente clienteComprador;
	private Vuelo vuelo;
	
	
	// Constructor
	
	public Tiquete(String codigo, Vuelo vuelo, Cliente clienteComprador, int tarifa) {
		
        this.codigo = codigo;
        this.vuelo = vuelo;
        this.clienteComprador = clienteComprador;
        this.tarifa = tarifa;
		
	}
	
	//Metodos
	
    public Cliente getCliente()
    {
        return clienteComprador;
    }
    
    public Vuelo getVuelo()
    {
        return vuelo;
    }
    
    public String getCodigo()
    {
        return codigo;
    }
    
    public int getTarifa()
    {
        return tarifa;
    }
    
    public void marcarComoUsado()
    {
        this.usado = true;
    }
    
    public boolean esUsado()
    {
        return usado;
    }
    
    

}
