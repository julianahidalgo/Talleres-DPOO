package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest
{
    private ProductoMenu producto1;
    private ProductoMenu producto2;

    @BeforeEach
    void setUp( ) throws Exception
    {
        producto1 = new ProductoMenu( "Hamburguesa Sencilla", 12000 );
        producto2 = new ProductoMenu( "Papas Medianas", 5500 );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "Hamburguesa Sencilla", producto1.getNombre( ), "El nombre del producto no es el esperado." );
        assertEquals( "Papas Medianas", producto2.getNombre( ), "El nombre del producto no es el esperado." );
    }

    @Test
    void testGetPrecio( )
    {
        assertEquals( 12000, producto1.getPrecio( ), "El precio del producto no es el esperado." );
        assertEquals( 5500, producto2.getPrecio( ), "El precio del producto no es el esperado." );
    }

    @Test
    void testGenerarTextoFactura( )
    {
        String factura = producto1.generarTextoFactura( );

        assertNotNull( factura, "El texto de la factura no debe ser nulo." );
        assertFalse( factura.isEmpty( ), "El texto de la factura no debe estar vacío." );


        assertTrue( factura.contains( "Hamburguesa Sencilla" ), "El texto de la factura no contiene el nombre esperado." );
        assertTrue( factura.contains( "12000" ), "El texto de la factura no contiene el precio esperado." );

 
        String esperado = "Hamburguesa Sencilla\n" + "            12000\n";
        assertEquals( esperado, factura, "El formato del texto de la factura no es el esperado." );
    }

    @Test
    void testPrecioCero( )
    {
        ProductoMenu productoGratis = new ProductoMenu( "Agua", 0 );
        assertEquals( 0, productoGratis.getPrecio( ), "El precio de un producto gratuito debe ser 0." );
    }
}