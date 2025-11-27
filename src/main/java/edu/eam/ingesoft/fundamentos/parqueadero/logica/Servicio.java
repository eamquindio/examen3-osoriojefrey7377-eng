package edu.eam.ingesoft.fundamentos.parqueadero.logica;

/**
 * Clase que representa un servicio de parqueo en el parqueadero.
 * El servicio es responsable de calcular su duración y costo.
 */
public class Servicio {

    // ==================== ATRIBUTOS ====================

    private int horaIngreso;
    private int horaSalida;
    private Vehiculo vehiculo;
    private double costo;

    // ==================== CONSTRUCTOR ====================

    /**
     * Crea una nueva instancia de Servicio y calcula su costo.
     * El costo debe calcularse automáticamente al crear el servicio usando calcularCosto().
     * @param horaIngreso Hora en que ingresó el vehículo (1-22)
     * @param horaSalida Hora en que salió el vehículo (2-23)
     * @param vehiculo Objeto Vehiculo que utilizó el servicio
     */
    public Servicio(int horaIngreso, int horaSalida, Vehiculo vehiculo) {
        // TODO: Implementar constructor
        this.horaIngreso=horaIngreso;
        this.horaSalida=horaSalida;
        this.vehiculo=vehiculo;
        this.costo=calcularCosto();
        
        // Pista: Asignar atributos y usar calcularCosto() para el costo
    }

    // ==================== GETTERS ====================

    /**
     * @return La hora de ingreso del vehículo
     */
    public int getHoraIngreso() {
        return horaIngreso;
    }

    /**
     * @return La hora de salida del vehículo
     */
    public int getHoraSalida() {
        return horaSalida;
    }

    /**
     * @return El vehículo asociado al servicio
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * @return El costo calculado del servicio
     */
    public double getCosto() {
        return costo;
    }

    // ==================== MÉTODOS DE NEGOCIO ====================

    /**
     * Calcula la duración del servicio en horas.
     * @return Número de horas de uso (horaSalida - horaIngreso)
     */
    public int calcularHoras() {
        // TODO: Implementar método
        return horaSalida - horaIngreso;
    }

    /**
     * Calcula el costo total del servicio aplicando tarifas y descuentos.
     * Fórmula: costo = horas × tarifaVehículo × (1 - descuentoPropietario)
     *
     * Pista:
     * - El vehículo tiene el método obtenerTarifaHora()
     * - El vehículo tiene el propietario asociado con getPropietario()
     * - El propietario tiene el método obtenerDescuento()
     *
     * @return El costo total del servicio
     */
    public double calcularCosto() {
        // TODO: Implementar método
        int horas=calcularHoras();
        double tarifa=vehiculo.obtenerTarifaHora();
        double descuento=vehiculo.getPropietario().obtenerDescuento();
        double costo=horas*tarifa*(1 - descuento);

        return costo;
    }

    public int getHorasUsadas() {
        return calcularHoras();
    }
}
