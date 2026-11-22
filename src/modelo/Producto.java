package modelo;

/**
 * Superclase base que representa un artículo genérico del inventario.
 * Aplica encapsulamiento en sus atributos y define los métodos base
 * que serán sobrescritos polimórficamente por las subclases.
 */

public class Producto {

    private String codigo;
    private String nombre;
    private double costoUnitario;
    protected double precioUnitario;
    private int cantidad;

    public Producto(String codigo, String nombre, double costoUnitario, double precioUnitario, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.costoUnitario = costoUnitario;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo != null && !codigo.trim().isEmpty()){
            this.codigo = codigo;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()){
            this.nombre = nombre;
        }
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        if (costoUnitario > 0){
            this.costoUnitario = costoUnitario;
        }
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        if (precioUnitario > 0) {
            this.precioUnitario = precioUnitario;
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if(cantidad >= 0){
            this.cantidad = cantidad;
        }
    }
    // Retorna el precio unitario base (sobrescrito en las subclases)).
    public double calcularPrecioVenta(){
        return precioUnitario;
    }

    public double calcularValorCostoTotal(){
        return costoUnitario * cantidad;
    }

    public String mostrarInformacion(){
        return "Codigo: " +codigo+
                " | Nombre: " +nombre+
                " | Costo Unitario: $" +costoUnitario+
                " | Precio Base: $" +precioUnitario+
                " | Stock: " +cantidad+
                " | Inversión Total: $" +String.format("%.2f", calcularValorCostoTotal());
    }
}
