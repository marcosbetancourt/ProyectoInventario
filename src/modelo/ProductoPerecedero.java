package modelo;

/**
 * Subclase que hereda de Producto para gestionar artículos con fecha de caducidad.
 * Aplica polimorfismo al calcular un descuento por proximidad de vencimiento.
 */

public class ProductoPerecedero extends Producto {

    private String fechaCaducidad;
    private int diasParaVencer;

    public ProductoPerecedero(String codigo, String nombre, double costoUnitario, double precioUnitario, int cantidad, String fechaCaducidad, int diasParaVencer) {
        super(codigo, nombre, costoUnitario, precioUnitario, cantidad);
        this.fechaCaducidad = fechaCaducidad;
        this.diasParaVencer = diasParaVencer;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        if (fechaCaducidad != null && !fechaCaducidad.trim().isEmpty()){
            this.fechaCaducidad = fechaCaducidad;
        }
    }

    public int getDiasParaVencer() {
        return diasParaVencer;
    }

    public void setDiasParaVencer(int diasParaVencer) {
        if (diasParaVencer >= 0){
            this.diasParaVencer = diasParaVencer;
        }
    }
    // Aplica 20% de descuento si faltan 5 días o menos para vencer).
    @Override
    public double calcularPrecioVenta(){
        if (diasParaVencer <= 5){
            return precioUnitario * 0.80;
        } else {
            return precioUnitario;
        }
    }
    @Override
    public String mostrarInformacion(){
        return "[PERECEDERO] " + super.mostrarInformacion()
                + "\n   Caducidad: " + fechaCaducidad + " | Días para vencer: " + diasParaVencer
                + " | Precio Venta Final: $" + String.format("%.2f", calcularPrecioVenta());
    }
}
