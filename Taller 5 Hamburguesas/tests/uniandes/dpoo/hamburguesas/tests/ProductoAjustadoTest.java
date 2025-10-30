package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest
{
    private ProductoMenu base;
    private ProductoAjustado ajustado;

    private Ingrediente lechuga;     // 1.000
    private Ingrediente tocineta;    // 3.000
    private Ingrediente cebolla;     // 800

    @BeforeEach
    void setUp( ) throws Exception
    {
        base = new ProductoMenu( "Hamburguesa Sencilla", 12000 );
        ajustado = new ProductoAjustado( base );

        lechuga  = new Ingrediente( "lechuga", 1000 );
        tocineta = new Ingrediente( "tocineta", 3000 );
        cebolla  = new Ingrediente( "cebolla", 800 );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "Hamburguesa Sencilla", ajustado.getNombre( ), 
            "El nombre del producto ajustado no es el esperado." );
    }

    @Test
    void testGetPrecio_sinAdicionesNiEliminaciones( )
    {
        assertEquals( 12000, ajustado.getPrecio( ), 
            "El precio sin adiciones ni eliminaciones debe ser el precio base." );
    }

    @Test
    void testGetPrecio_conAdiciones( )
    {
        ajustado.agregarIngrediente( lechuga );   // +1000
        ajustado.agregarIngrediente( tocineta );  // +3000

        assertEquals( 12000 + 1000 + 3000, ajustado.getPrecio( ),
            "El precio con adiciones no está sumando correctamente los costos adicionales." );
    }

    @Test
    void testGetPrecio_conEliminaciones_noDescuenta( )
    {
        ajustado.agregarIngrediente( lechuga );  // +1000
        ajustado.eliminarIngrediente( cebolla ); // NO debe restar

        assertEquals( 12000 + 1000, ajustado.getPrecio( ),
            "Eliminar ingredientes no debería disminuir el precio del producto." );
    }

    @Test
    void testGenerarTextoFactura_conAdicionesYEliminaciones( )
    {
        ajustado.agregarIngrediente( lechuga );
        ajustado.agregarIngrediente( tocineta );
        ajustado.eliminarIngrediente( cebolla );

        String factura = ajustado.generarTextoFactura( );

        assertNotNull( factura, "El texto de la factura no debe ser nulo." );
        assertFalse( factura.isEmpty( ), "El texto de la factura no debe estar vacío." );

        assertTrue( factura.contains( "Hamburguesa Sencilla" ), 
            "La factura no contiene el nombre del producto base." );
        assertTrue( factura.contains( String.valueOf(12000 + 1000 + 3000) ), 
            "La factura no contiene el precio total correcto." );

        assertTrue( factura.contains( "lechuga" ), 
            "La factura no lista la adición 'lechuga'." );
        assertTrue( factura.contains( "1000" ), 
            "La factura no muestra el costo de 'lechuga'." );
        assertTrue( factura.contains( "tocineta" ), 
            "La factura no lista la adición 'tocineta'." );
        assertTrue( factura.contains( "3000" ), 
            "La factura no muestra el costo de 'tocineta'." );
        assertTrue( factura.contains( "cebolla" ), 
            "La factura no muestra la eliminación 'cebolla'." );
    }

    @Test
    void testGenerarTextoFactura_formatoExacto( )
    {
        
        ajustado.agregarIngrediente( lechuga );   // +1000
        ajustado.eliminarIngrediente( cebolla );  // no descuenta

        String esperado =
        	      "Hamburguesa Sencilla\n"
        	    + "    +lechuga\n"
        	    + "                1000\n"
        	    + "    -cebolla\n"
        	    + "            13000\n";


        assertEquals( esperado, ajustado.generarTextoFactura( ), 
            "El formato del texto de la factura no es el esperado." );
    }

    @Test
    void testGetPrecio_conIngredientesRepetidos( )
    {
        ajustado.agregarIngrediente( lechuga );
        ajustado.agregarIngrediente( lechuga ); 

        assertEquals( 12000 + 1000 + 1000, ajustado.getPrecio( ),
            "Agregar el mismo ingrediente dos veces debe sumar su costo dos veces." );
    }

    @Test
    void testGetPrecio_soloEliminaciones_noCambianPrecio( )
    {
        ajustado.eliminarIngrediente( cebolla );
        ajustado.eliminarIngrediente( lechuga );

        assertEquals( 12000, ajustado.getPrecio( ),
            "Eliminar ingredientes no debe cambiar el precio base cuando no hay adiciones." );
    }
}


