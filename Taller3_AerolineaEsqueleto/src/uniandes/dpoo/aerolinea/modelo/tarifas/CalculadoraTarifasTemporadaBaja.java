
public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas{
	
	protected final int COSTO_POR_KM_NATURAL = 600;
	protected final int COSTO_POR_KM_CORPORATIVO = 900;
	protected final double DESCUENTO_PEQ = 0.02;
	protected final double DESCUENTO_MEDIANAS = 0.1;
	protected final double DESCEUNTO_GRANDES = 0.2;
	
	// Metodos
	
	public int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
		
		return 0;
	}
	
	public double calcularPorcentajeDescuento(Cliente cliente) {
		
		return 0.0;
	}
	

}
