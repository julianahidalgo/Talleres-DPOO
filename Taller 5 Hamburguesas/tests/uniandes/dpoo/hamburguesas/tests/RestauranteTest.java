package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest
{
    private Restaurante restaurante;

    private static final File FACTURAS_DIR = new File("./facturas");

    @BeforeEach
    void setUp( ) throws Exception
    {
        if (!FACTURAS_DIR.exists()) FACTURAS_DIR.mkdirs();
        restaurante = new Restaurante();
        restaurante.cargarInformacionRestaurante(
            new File("data/ingredientes.txt"),
            new File("data/menu.txt"),
            new File("data/combos.txt")
        );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {

    }

    @Test
    void testCargaDeInformacion_basica( )
    {
        List<ProductoMenu> menu = restaurante.getMenuBase();
        List<Combo> combos = restaurante.getMenuCombos();
        assertNotNull(menu, "El menú base no debe ser nulo.");
        assertNotNull(combos, "La lista de combos no debe ser nula.");
        assertTrue(menu.size() > 0, "El menú base debe tener al menos un producto.");
        assertTrue(combos.size() >= 0, "La lista de combos debe existir (puede estar vacía).");
    }

    @Test
    void testIniciarPedido_y_getPedidoEnCurso( ) throws YaHayUnPedidoEnCursoException
    {
        restaurante.iniciarPedido("Juliana", "Cll 123 #45-67");
        Pedido enCurso = restaurante.getPedidoEnCurso();
        assertNotNull(enCurso, "Debe existir un pedido en curso luego de iniciar.");
        assertEquals("Juliana", enCurso.getNombreCliente(), "El nombre del cliente no coincide.");
    }

    @Test
    void testIniciarPedido_dosVeces_debeLanzar( ) throws YaHayUnPedidoEnCursoException
    {
        restaurante.iniciarPedido("Ana", "Calle 1");
        assertThrows(YaHayUnPedidoEnCursoException.class,
            () -> restaurante.iniciarPedido("Beto", "Calle 2"),
            "No se puede iniciar un nuevo pedido si ya hay uno en curso.");
    }

    @Test
    void testCerrarSinIniciar_debeLanzar( )
    {
        Restaurante r2 = new Restaurante(); 
        assertThrows(NoHayPedidoEnCursoException.class,
            r2::cerrarYGuardarPedido,
            "Cerrar sin haber iniciado un pedido debe lanzar NoHayPedidoEnCursoException.");
    }

    @Test
    void testFlujoCompleto_creaFactura_enCarpetaFija( ) throws Exception
    {
        restaurante.iniciarPedido("Cliente X", "Dir X");
        Pedido enCurso = restaurante.getPedidoEnCurso();
        assertNotNull(enCurso, "Debe existir pedido en curso.");

        List<ProductoMenu> menu = restaurante.getMenuBase();
        assertFalse(menu.isEmpty(), "Se requiere al menos un producto en el menú para la prueba.");
        enCurso.agregarProducto(menu.get(0));

        if (!restaurante.getMenuCombos().isEmpty())
        {
            enCurso.agregarProducto(restaurante.getMenuCombos().get(0));
        }

        int id = enCurso.getIdPedido();
        File facturaEsperada = new File(FACTURAS_DIR, "factura_" + id + ".txt");

        restaurante.cerrarYGuardarPedido();

        assertTrue(facturaEsperada.exists(), "No se encontró la factura en ./facturas/factura_" + id + ".txt");
        assertNull(restaurante.getPedidoEnCurso(), "Después de cerrar, no debe haber pedido en curso.");
    }

    @Test
    void testTotalesMinimos_enPedido( ) throws Exception
    {
        restaurante.iniciarPedido("Cliente Y", "Dir Y");
        Pedido p = restaurante.getPedidoEnCurso();

        ProductoMenu prod = restaurante.getMenuBase().get(0);
        p.agregarProducto(prod);

        int netoMin = prod.getPrecio();
        int ivaMin  = (int)(netoMin * 0.19);
        int totalMin = netoMin + ivaMin;

        assertTrue(p.getPrecioTotalPedido() >= totalMin,
            "El total del pedido debería ser al menos el primer producto con IVA.");
    }
}