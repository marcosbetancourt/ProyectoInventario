package modelo;

/**
 * Subclase que hereda de Producto para gestionar artículos duraderos en bodega.
 * Aplica polimorfismo al calcular un recargo cuando tienen garantía extendida.
 */
public class ProductoNoPerecedero extends Producto{

    private String ubicacionBodega;
    private int mesesGarantia;

    public ProductoNoPerecedero(String codigo, String nombre, double costoUnitario, double precioUnitario, int cantidad, String ubicacionBodega, int mesesGarantia) {
        super(codigo, nombre, costoUnitario, precioUnitario, cantidad);
        this.ubicacionBodega = ubicacionBodega;
        this.mesesGarantia = mesesGarantia;
    }

    public String getUbicacionBodega() {
        return ubicacionBodega;
    }

    public void setUbicacionBodega(String ubicacionBodega) {
        if (ubicacionBodega != null && !ubicacionBodega.trim().isEmpty()){
            this.ubicacionBodega = ubicacionBodega;
        }
    }

    public int getMesesGarantia() {
        return mesesGarantia;
    }

    public void setMesesGarantia(int mesesGarantia) {
        if (mesesGarantia >= 0){
            this.mesesGarantia = mesesGarantia;
        }
    }
    // Aplica 5% de recargo si la garantía supera los 12 meses).
    @Override
    public double calcularPrecioVenta(){
        if (mesesGarantia > 12){
            return precioUnitario * 1.05;
        } else {
            return precioUnitario;
        }
    }
    @Override
    public String mostrarInformacion(){
        return "[NO PERECEDERO] " +super.mostrarInformacion() +
                " | Ubicación de la bodega: " + ubicacionBodega +
                " | Meses de garantía: " + mesesGarantia +
                " | Precio Final Venta: $" + String.format("%.2f", calcularPrecioVenta());
    }
}
