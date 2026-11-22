package negocio;

import java.util.ArrayList;
import modelo.Producto;

/**
 * Clase encargada de gestionar la lógica de negocio del inventario.
 * Aplica agregación sobre objetos Producto y controla las restricciones
 * de capacidad física de bodega y presupuesto máximo.
 */
public class GestionInventario {

    // Atributos encapsulados (Agregación: almacena múltiples instancias de Producto)
    private ArrayList<Producto> inventario;
    private int capacidadMaximaUnidades;
    private double presupuestoMaximo;

    // Constructor: inicializa la lista dinámica y define los límites del sistema
    public GestionInventario(int capacidadMaximaUnidades, double presupuestoMaximo) {
        this.inventario = new ArrayList<>();
        this.capacidadMaximaUnidades = capacidadMaximaUnidades;
        this.presupuestoMaximo = presupuestoMaximo;
    }

    // Getters de las restricciones del sistema
    public int getCapacidadMaximaUnidades() {
        return capacidadMaximaUnidades;
    }

    public double getPresupuestoMaximo() {
        return presupuestoMaximo;
    }

    // Calcula el total de unidades físicas almacenadas actualmente en bodega
    public int calcularTotalUnidades() {
        int totalUnidades = 0;
        for (Producto p : inventario) {
            totalUnidades += p.getCantidad();
        }
        return totalUnidades;
    }

    // Calcula el capital total invertido en el inventario actual
    public double calcularInversionTotal() {
        double totalInversion = 0;
        for (Producto p : inventario) {
            totalInversion += p.calcularValorCostoTotal();
        }
        return totalInversion;
    }

    // Busca un producto por su código ignorando mayúsculas/minúsculas
    public Producto buscarPorCodigo(String codigo) {
        if (!Validacion.esTextoValido(codigo)) {
            return null;
        }
        for (Producto p : inventario) {
            if (p.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return p;
            }
        }
        return null;
    }

    // Registra un producto verificando duplicados, capacidad de bodega y presupuesto
    public String registrarProducto(Producto nuevo) {
        if (nuevo == null) {
            return "Error: El producto no puede ser nulo.";
        }
        if (buscarPorCodigo(nuevo.getCodigo()) != null) {
            return "Error: Ya existe un producto registrado con el código " + nuevo.getCodigo() + ".";
        }
        if (calcularTotalUnidades() + nuevo.getCantidad() > capacidadMaximaUnidades) {
            return "Error: Se supera la capacidad máxima de bodega (" + capacidadMaximaUnidades + " unidades).";
        }
        if (calcularInversionTotal() + nuevo.calcularValorCostoTotal() > presupuestoMaximo) {
            return "Error: Se supera el presupuesto máximo asignado ($" + String.format("%.2f", presupuestoMaximo) + ").";
        }

        inventario.add(nuevo);
        return "Producto registrado exitosamente.";
    }

    // Actualiza el stock de un producto existente respetando los límites del almacén
    public boolean actualizarStock(String codigo, int nuevaCantidad) {
        Producto p = buscarPorCodigo(codigo);
        if (p == null || nuevaCantidad < 0) {
            return false;
        }

        // Se resta el aporte actual del producto antes de evaluar la nueva cantidad
        int unidadesSinProducto = calcularTotalUnidades() - p.getCantidad();
        double inversionSinProducto = calcularInversionTotal() - p.calcularValorCostoTotal();
        double nuevoCostoLote = p.getCostoUnitario() * nuevaCantidad;

        if (unidadesSinProducto + nuevaCantidad > capacidadMaximaUnidades) {
            return false;
        }
        if (inversionSinProducto + nuevoCostoLote > presupuestoMaximo) {
            return false;
        }

        p.setCantidad(nuevaCantidad);
        return true;
    }

    // Elimina un producto del inventario según su código
    public boolean eliminarProducto(String codigo) {
        Producto p = buscarPorCodigo(codigo);
        if (p != null) {
            inventario.remove(p);
            return true;
        }
        return false;
    }

    // Genera el reporte completo del inventario aplicando polimorfismo
    public String listarInventario() {
        if (inventario.isEmpty()) {
            return "El inventario se encuentra vacío.";
        }

        String reporte = "--- LISTADO DE INVENTARIO ---\n";
        for (Producto p : inventario) {
            // Polimorfismo: ejecuta el método sobrescrito de ProductoPerecedero o ProductoNoPerecedero
            reporte += p.mostrarInformacion() + "\n";
        }
        reporte += "--------------------------------------------------\n";
        reporte += "Capacidad ocupada: " + calcularTotalUnidades() + " / " + capacidadMaximaUnidades + " unidades\n";
        reporte += "Presupuesto invertido: $" + String.format("%.2f", calcularInversionTotal()) + " / $" + String.format("%.2f", presupuestoMaximo);
        return reporte;
    }
}