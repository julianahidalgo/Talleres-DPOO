package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest
{
    private ProductoMenu hambSencilla;  // 12.000
    private ProductoMenu papas;         // 5.500
    private ProductoMenu gaseosa;       // 4.000
    private ArrayList<ProductoMenu> baseItems;

    @BeforeEach
    void setUp( ) throws Exception
    {
        hambSencilla = new ProductoMenu("Hamburguesa Sencilla", 12000);
        papas        = new ProductoMenu("Papas Medianas", 5500);
        gaseosa      = new ProductoMenu("Gaseosa", 4000);

        baseItems = new ArrayList<>();
        baseItems.add(hambSencilla);
        baseItems.add(papas);
        baseItems.add(gaseosa);
    }

    @AfterEach
    void tearDown( ) throws Exception
    { }

    @Test
    void testGetNombre( )
    {
        Combo c = new Combo("Clásico", 0.10, baseItems);
        assertEquals("Clásico", c.getNombre(), "El nombre del combo no es el esperado.");
    }

    @Test
    void testGetPrecio_sinDescuento( )
    {
        Combo c = new Combo("Sin Dto", 0.0, baseItems);
        assertEquals(21500, c.getPrecio(), "Un combo con 0% de descuento debe costar la suma de los ítems.");
    }

    @Test
    void testGetPrecio_conDescuentoIntermedio( )
    {
        Combo c = new Combo("Clásico", 0.10, baseItems);
        assertEquals(19350, c.getPrecio(), "El precio con descuento no es el esperado.");
    }

    @Test
    void testGetPrecio_descuentoTotal( )
    {
        Combo c = new Combo("Gratis", 1.0, baseItems);
        assertEquals(0, c.getPrecio(), "Un combo con 100% de descuento debe costar 0.");
    }

    @Test
    void testGetPrecio_listaVacia( )
    {
        Combo c = new Combo("Vacío", 0.25, new ArrayList<>());
        assertEquals(0, c.getPrecio(), "Un combo sin ítems debe costar 0, independientemente del descuento.");
    }

    @Test
    void testRedondeoTruncado( )
    {
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(papas); // 5500
        Combo c = new Combo("Mini", 0.15, items);
        assertEquals(4675, c.getPrecio(), "El precio debe truncarse a entero (no redondear hacia arriba).");
    }

    @Test
    void testGenerarTextoFactura_contenido( )
    {
        Combo c = new Combo("Clásico", 0.10, baseItems);
        String factura = c.generarTextoFactura();

        assertNotNull(factura, "La factura no debe ser nula.");
        assertFalse(factura.isEmpty(), "La factura no debe estar vacía.");
        assertTrue(factura.contains("Combo Clásico"), "La factura no contiene el nombre del combo.");
        assertTrue(factura.contains("Descuento: 0.1"), "La factura no contiene el descuento del combo.");

        assertTrue(factura.contains("19350"), "La factura no contiene el total esperado.");
    }

    @Test
    void testCopiaDefensiva_deLista( )
    {
        ArrayList<ProductoMenu> itemsOrig = new ArrayList<>();
        itemsOrig.add(hambSencilla);

        Combo c = new Combo("Seguro", 0.20, itemsOrig);

        itemsOrig.add(papas);  

        assertEquals(9600, c.getPrecio(), "El combo debe usar copia defensiva de la lista de ítems.");
    }
}