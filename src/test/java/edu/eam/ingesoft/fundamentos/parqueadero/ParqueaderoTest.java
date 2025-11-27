package edu.eam.ingesoft.fundamentos.parqueadero;

import edu.eam.ingesoft.fundamentos.parqueadero.logica.Parqueadero;
import edu.eam.ingesoft.fundamentos.parqueadero.logica.Propietario;
import edu.eam.ingesoft.fundamentos.parqueadero.logica.Vehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Parqueadero.
 */
public class ParqueaderoTest {

    private Parqueadero parqueadero;

    @BeforeEach
    public void setUp() {
        parqueadero = new Parqueadero();
    }

    // ==================== PRUEBAS DE REGISTRAR PROPIETARIO ====================

    @Test
    public void testRegistrarPropietarioExitoso() {
        boolean resultado = parqueadero.registrarPropietario("123456789", "Juan Pérez");
        assertTrue(resultado);
    }

    @Test
    public void testRegistrarPropietarioDuplicado() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        boolean resultado = parqueadero.registrarPropietario("123456789", "Otro Nombre");
        assertFalse(resultado);
    }

    @Test
    public void testRegistrarMultiplesPropietarios() {
        parqueadero.registrarPropietario("111111111", "Propietario 1");
        parqueadero.registrarPropietario("222222222", "Propietario 2");
        parqueadero.registrarPropietario("333333333", "Propietario 3");

        assertEquals(3, parqueadero.getPropietarios().size());
    }

    // ==================== PRUEBAS DE BUSCAR PROPIETARIO ====================

    @Test
    public void testBuscarPropietarioExistente() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        Propietario encontrado = parqueadero.buscarPropietario("123456789");

        assertNotNull(encontrado);
        assertEquals("Juan Pérez", encontrado.getNombre());
    }

    @Test
    public void testBuscarPropietarioNoExistente() {
        Propietario encontrado = parqueadero.buscarPropietario("999999999");
        assertNull(encontrado);
    }

    // ==================== PRUEBAS DE REGISTRAR VEHÍCULO ====================

    @Test
    public void testRegistrarVehiculoExitoso() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        boolean resultado = parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "123456789", "SEDAN");
        assertTrue(resultado);
    }

    @Test
    public void testRegistrarVehiculoDuplicado() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "123456789", "SEDAN");

        boolean resultado = parqueadero.registrarVehiculo("ABC123", 2021, "Azul", "123456789", "SUV");
        assertFalse(resultado);
    }

    @Test
    public void testRegistrarVehiculoPropietarioNoExiste() {
        boolean resultado = parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "999999999", "SEDAN");
        assertFalse(resultado);
    }

    // ==================== PRUEBAS DE BUSCAR VEHÍCULO ====================

    @Test
    public void testBuscarVehiculoExistente() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "123456789", "SEDAN");

        Vehiculo encontrado = parqueadero.buscarVehiculo("ABC123");

        assertNotNull(encontrado);
        assertEquals("SEDAN", encontrado.getTipo());
    }

    @Test
    public void testBuscarVehiculoNoExistente() {
        Vehiculo encontrado = parqueadero.buscarVehiculo("ZZZ999");
        assertNull(encontrado);
    }

    // ==================== PRUEBAS DE ACUMULAR HORAS CLIENTE ====================

    @Test
    public void testAcumularHorasClienteExitoso() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        boolean resultado = parqueadero.acumularHorasCliente("123456789", 50);

        assertTrue(resultado);
        assertEquals(50, parqueadero.buscarPropietario("123456789").getHorasAcumuladas());
    }

    @Test
    public void testAcumularHorasClienteNoExiste() {
        boolean resultado = parqueadero.acumularHorasCliente("999999999", 50);
        assertFalse(resultado);
    }

    // ==================== PRUEBAS DE REGISTRAR SERVICIO ====================

    @Test
    public void testRegistrarServicioExitoso() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "123456789", "SEDAN");

        double costo = parqueadero.registrarServicio("ABC123", 8, 13);

        // 5 horas × $1,500 = $7,500
        assertEquals(-7500, costo, 0.01);
    }

    @Test
    public void testRegistrarServicioAcumulaHoras() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "123456789", "SEDAN");

        parqueadero.registrarServicio("ABC123", 8, 13); // 5 horas

        assertEquals(5, parqueadero.buscarPropietario("123456789").getHorasAcumuladas());
    }

    @Test
    public void testRegistrarServicioHoraIngresoInvalidaBaja() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "123456789", "SEDAN");

        double costo = parqueadero.registrarServicio("ABC123", 0, 10);
        assertEquals(-1, costo, 0.01);
    }

    @Test
    public void testRegistrarServicioHoraIngresoInvalidaAlta() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "123456789", "SEDAN");

        double costo = parqueadero.registrarServicio("ABC123", 23, 24);
        assertEquals(-1, costo, 0.01);
    }

    @Test
    public void testRegistrarServicioHoraSalidaInvalidaBaja() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "123456789", "SEDAN");

        double costo = parqueadero.registrarServicio("ABC123", 1, 1);
        assertEquals(-1, costo, 0.01);
    }

    @Test
    public void testRegistrarServicioHoraSalidaInvalidaAlta() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "123456789", "SEDAN");

        double costo = parqueadero.registrarServicio("ABC123", 10, 25);
        assertEquals(-1, costo, 0.01);
    }

    @Test
    public void testRegistrarServicioHoraSalidaMenorQueIngreso() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "123456789", "SEDAN");

        double costo = parqueadero.registrarServicio("ABC123", 15, 10);
        assertEquals(-1, costo, 0.01);
    }

    @Test
    public void testRegistrarServicioVehiculoNoExiste() {
        double costo = parqueadero.registrarServicio("ZZZ999", 8, 13);
        assertEquals(-1, costo, 0.01);
    }

    // ==================== PRUEBAS DE CALCULAR TOTAL RECAUDADO ====================

    @Test
    public void testCalcularTotalRecaudadoSinServicios() {
        assertEquals(0, parqueadero.calcularTotalRecaudado(), 0.01);
    }

    @Test
    public void testCalcularTotalRecaudadoConServicios() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "123456789", "SEDAN");
        parqueadero.registrarVehiculo("XYZ789", 2022, "Negro", "123456789", "SUV");

        parqueadero.registrarServicio("ABC123", 8, 10); // 2 horas × 1500 = 3000
        parqueadero.registrarServicio("XYZ789", 10, 12); // 2 horas × 2300 = 4600

        assertEquals(-7600, parqueadero.calcularTotalRecaudado(), 0.01);
    }

    // ==================== PRUEBAS DE CONTAR CLIENTES VIP ====================

    @Test
    public void testContarClientesVIPSinVIP() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarPropietario("987654321", "María García");

        assertEquals(0, parqueadero.contarClientesVIP());
    }

    @Test
    public void testContarClientesVIPConVIP() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarPropietario("987654321", "María García");

        parqueadero.acumularHorasCliente("123456789", 600); // VIP

        assertEquals(1, parqueadero.contarClientesVIP());
    }

    @Test
    public void testContarClientesVIPMultiples() {
        parqueadero.registrarPropietario("111111111", "Cliente 1");
        parqueadero.registrarPropietario("222222222", "Cliente 2");
        parqueadero.registrarPropietario("333333333", "Cliente 3");

        parqueadero.acumularHorasCliente("111111111", 501); // VIP
        parqueadero.acumularHorasCliente("222222222", 200); // ESPECIAL
        parqueadero.acumularHorasCliente("333333333", 700); // VIP

        assertEquals(2, parqueadero.contarClientesVIP());
    }

    // ==================== PRUEBAS DE OBTENER CLIENTE MÁS HORAS ====================

    @Test
    public void testObtenerClienteMasHorasSinClientes() {
        assertNull(parqueadero.obtenerClienteMasHoras());
    }

    @Test
    public void testObtenerClienteMasHorasUnCliente() {
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.acumularHorasCliente("123456789", 100);

        Propietario mayor = parqueadero.obtenerClienteMasHoras();

        assertNotNull(mayor);
        assertEquals("123456789", mayor.getCedula());
    }

    @Test
    public void testObtenerClienteMasHorasMultiplesClientes() {
        parqueadero.registrarPropietario("111111111", "Cliente 1");
        parqueadero.registrarPropietario("222222222", "Cliente 2");
        parqueadero.registrarPropietario("333333333", "Cliente 3");

        parqueadero.acumularHorasCliente("111111111", 100);
        parqueadero.acumularHorasCliente("222222222", 500);
        parqueadero.acumularHorasCliente("333333333", 300);

        Propietario mayor = parqueadero.obtenerClienteMasHoras();

        assertEquals("222222222", mayor.getCedula());
        assertEquals(500, mayor.getHorasAcumuladas());
    }

    // ==================== PRUEBAS DE INTEGRACIÓN ====================

    @Test
    public void testFlujoCompletoConDescuentos() {
        // Registrar propietario
        parqueadero.registrarPropietario("123456789", "Juan Pérez");
        parqueadero.registrarVehiculo("ABC123", 2020, "Rojo", "123456789", "SEDAN");

        // Acumular horas para llegar a VIP
        parqueadero.acumularHorasCliente("123456789", 501);

        // Registrar servicio con descuento VIP
        double costo = parqueadero.registrarServicio("ABC123", 8, 10); // 2 horas

        // 2 horas × $1,500 × (1 - 0.15) = $2,550
        assertEquals(-2550, costo, 0.01);
    }
}
