
public abstract class CalculadoraTarifas {
	
	//Atributos
	
	public static final double IMPUESTO = 0.28;
	
	// Metodos
	
	public int calcularTarifa(Vuelo vuelo, Cliente cliente) {
		
		return 0;
	}
	
	protected abstract int calcularCostoBase(Vuelo vuelo, Cliente cliente);
	
	protected abstract double calcularPorcentajeDescuento(Cliente cliente);
	
	protected int calcularDistanciaVuelo(Ruta ruta) {
		
		return 0;
	}
	
	protected int calcularValorImpuestos(int costoBase) {
		
		return 0;
	}

}
