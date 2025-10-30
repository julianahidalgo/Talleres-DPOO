package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Producto;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest
{
    private Pedido pedido;
    private ProductoMenu hambSencilla; // 12.000
    private ProductoMenu papas;        // 5.500
    private ProductoMenu gaseosa;      // 4.000

    @TempDir
    File tempDir;

    @BeforeEach
    void setUp( ) throws Exception
    {
        pedido = new Pedido("Juliana", "Cll 123 #45-67");
        hambSencilla = new ProductoMenu("Hamburguesa Sencilla", 12000);
        papas        = new ProductoMenu("Papas Medianas", 5500);
        gaseosa      = new ProductoMenu("Gaseosa", 4000);
    }

    @AfterEach
    void tearDown( ) throws Exception
    { }

    @Test
    void testIds_incrementanSecuencialmente( )
    {

        Pedido p2 = new Pedido("Ana", "Calle 1");
        assertEquals(pedido.getIdPedido() + 1, p2.getIdPedido(),
            "El id del segundo pedido debe ser el id del primero + 1.");
    }

    @Test
    void testGetNombreCliente( )
    {
        assertEquals("Juliana", pedido.getNombreCliente(),
            "El nombre del cliente no es el esperado.");
    }

    @Test
    void testAgregarProducto_y_TotalConIVA( )
    {

        pedido.agregarProducto(hambSencilla);
        pedido.agregarProducto(papas);
        pedido.agregarProducto(gaseosa);

        int netoEsperado = 12000 + 5500 + 4000;

        int ivaEsperado = (int)(netoEsperado * 0.19);

        int totalEsperado = netoEsperado + ivaEsperado;

        assertEquals(totalEsperado, pedido.getPrecioTotalPedido(),
            "El precio total del pedido no coincide con neto + IVA.");
    }

    @Test
    void testFactura_contieneClienteDireccionItemsYTotales( )
    {
        pedido.agregarProducto(hambSencilla);
        pedido.agregarProducto(papas);

        String factura = pedido.generarTextoFactura();

        assertNotNull(factura, "La factura no debe ser nula.");
        assertFalse(factura.isEmpty(), "La factura no debe estar vacía.");

        assertTrue(factura.contains("Cliente: Juliana"), "La factura no contiene el nombre del cliente.");
        assertTrue(factura.contains("Dirección: Cll 123 #45-67"), "La factura no contiene la dirección del cliente.");
        assertTrue(factura.contains("----------------"), "La factura debe incluir separadores.");

        assertTrue(factura.contains("Hamburguesa Sencilla"), "La factura no incluye 'Hamburguesa Sencilla'.");
        assertTrue(factura.contains("Papas Medianas"), "La factura no incluye 'Papas Medianas'.");

        int neto = 12000 + 5500;              // 17500
        int iva  = (int)(neto * 0.19);        // 3325
        int total = neto + iva;               // 20825

        assertTrue(factura.contains("Precio Neto:  " + neto),
            "La factura no contiene el precio neto esperado.");
        assertTrue(factura.contains("IVA:          " + iva),
            "La factura no contiene el IVA esperado.");
        assertTrue(factura.contains("Precio Total: " + total),
            "La factura no contiene el total esperado.");
    }

    @Test
    void testPedidoVacio_netoIvaYTotalCero( )
    {
        String factura = pedido.generarTextoFactura();

        assertTrue(factura.contains("Precio Neto:  0"), "Pedido vacío debe tener neto 0.");
        assertTrue(factura.contains("IVA:          0"), "Pedido vacío debe tener IVA 0.");
        assertTrue(factura.contains("Precio Total: 0"), "Pedido vacío debe tener total 0.");
        assertEquals(0, pedido.getPrecioTotalPedido(), "Total debe ser 0 si no hay productos.");
    }

    @Test
    void testGuardarFactura_creaArchivoConContenidoCorrecto( ) throws IOException
    {
        pedido.agregarProducto(hambSencilla);
        pedido.agregarProducto(gaseosa);

        String esperado = pedido.generarTextoFactura();

        File out = new File(tempDir, "factura.txt");
        pedido.guardarFactura(out);

        assertTrue(out.exists(), "El archivo de factura no fue creado.");
        List<String> lines = Files.readAllLines(out.toPath());
        String contenido = String.join("\n", lines) + "\n"; // normalizamos salto final
        assertEquals(esperado, contenido, "El contenido del archivo no coincide con la factura generada.");
    }

    @SuppressWarnings("unused")
    private static class Dummy implements Producto {
        @Override public String getNombre() { return "dummy"; }
        @Override public int getPrecio() { return 1; }
        @Override public String generarTextoFactura() { return "dummy\n            1\n"; }
    }
}

