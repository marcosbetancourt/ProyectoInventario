package interfaz;

import java.util.Scanner;
import modelo.Producto;
import modelo.ProductoNoPerecedero;
import modelo.ProductoPerecedero;
import negocio.GestionInventario;
import negocio.Validacion;

/**
 * Clase principal de la capa de interfaz (consola).
 * Gestiona la interacción con el usuario mediante un menú de opciones,
 * delegando las validaciones y la lógica de negocio a la capa negocio.
 */
public class MainInventario {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Inicializa el inventario con restricciones: 500 unidades máximas y $5000.00 de presupuesto
        GestionInventario gestion = new GestionInventario(500, 5000.00);
        int opcion;

        do {
            System.out.println("\n=== SISTEMA DE GESTIÓN DE INVENTARIO ===");
            System.out.println("1. Registrar producto perecedero");
            System.out.println("2. Registrar producto no perecedero");
            System.out.println("3. Listar inventario completo");
            System.out.println("4. Buscar producto por código");
            System.out.println("5. Actualizar stock de un producto");
            System.out.println("6. Eliminar producto");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = Validacion.validarEnteroNoNegativo(sc.nextLine());

            switch (opcion) {
                case 1:
                    registrarPerecedero(sc, gestion);
                    break;
                case 2:
                    registrarNoPerecedero(sc, gestion);
                    break;
                case 3:
                    System.out.println("\n" + gestion.listarInventario());
                    break;
                case 4:
                    buscarProducto(sc, gestion);
                    break;
                case 5:
                    actualizarStock(sc, gestion);
                    break;
                case 6:
                    eliminarProducto(sc, gestion);
                    break;
                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Error: Ingrese una opción válida del 1 al 7.");
            }
        } while (opcion != 7);

        sc.close();
    }

    // Solicita y valida los datos para registrar un producto perecedero
    private static void registrarPerecedero(Scanner sc, GestionInventario gestion) {
        System.out.print("Código: ");
        String codigo = sc.nextLine();
        if (!Validacion.esTextoValido(codigo)) {
            System.out.println("Error: El código es obligatorio.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        if (!Validacion.esTextoValido(nombre)) {
            System.out.println("Error: El nombre es obligatorio.");
            return;
        }

        System.out.print("Costo unitario ($): ");
        double costo = Validacion.validarDecimalPositivo(sc.nextLine());
        if (costo == -1) {
            System.out.println("Error: El costo unitario es obligatorio y debe ser mayor a 0.");
            return;
        }

        System.out.print("Precio unitario de venta ($): ");
        double precio = Validacion.validarDecimalPositivo(sc.nextLine());
        if (precio == -1) {
            System.out.println("Error: El precio unitario es obligatorio y debe ser mayor a 0.");
            return;
        }

        System.out.print("Cantidad en stock: ");
        int cantidad = Validacion.validarEnteroNoNegativo(sc.nextLine());
        if (cantidad == -1) {
            System.out.println("Error: La cantidad es obligatoria y debe ser un entero >= 0.");
            return;
        }

        System.out.print("Fecha de caducidad (AAAA-MM-DD): ");
        String fecha = sc.nextLine();
        if (!Validacion.esTextoValido(fecha)) {
            System.out.println("Error: La fecha de caducidad es obligatoria.");
            return;
        }

        System.out.print("Días para vencer: ");
        int dias = Validacion.validarEnteroNoNegativo(sc.nextLine());
        if (dias == -1) {
            System.out.println("Error: Los días para vencer deben ser un entero >= 0.");
            return;
        }

        Producto nuevo = new ProductoPerecedero(codigo.trim(), nombre.trim(), costo, precio, cantidad, fecha.trim(), dias);
        System.out.println(gestion.registrarProducto(nuevo));
    }

    // Solicita y valida los datos para registrar un producto no perecedero
    private static void registrarNoPerecedero(Scanner sc, GestionInventario gestion) {
        System.out.print("Código: ");
        String codigo = sc.nextLine();
        if (!Validacion.esTextoValido(codigo)) {
            System.out.println("Error: El código es obligatorio.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        if (!Validacion.esTextoValido(nombre)) {
            System.out.println("Error: El nombre es obligatorio.");
            return;
        }

        System.out.print("Costo unitario ($): ");
        double costo = Validacion.validarDecimalPositivo(sc.nextLine());
        if (costo == -1) {
            System.out.println("Error: El costo unitario es obligatorio y debe ser mayor a 0.");
            return;
        }

        System.out.print("Precio unitario de venta ($): ");
        double precio = Validacion.validarDecimalPositivo(sc.nextLine());
        if (precio == -1) {
            System.out.println("Error: El precio unitario es obligatorio y debe ser mayor a 0.");
            return;
        }

        System.out.print("Cantidad en stock: ");
        int cantidad = Validacion.validarEnteroNoNegativo(sc.nextLine());
        if (cantidad == -1) {
            System.out.println("Error: La cantidad es obligatoria y debe ser un entero >= 0.");
            return;
        }

        System.out.print("Ubicación en bodega: ");
        String ubicacion = sc.nextLine();
        if (!Validacion.esTextoValido(ubicacion)) {
            System.out.println("Error: La ubicación en bodega es obligatoria.");
            return;
        }

        System.out.print("Meses de garantía: ");
        int garantia = Validacion.validarEnteroNoNegativo(sc.nextLine());
        if (garantia == -1) {
            System.out.println("Error: Los meses de garantía deben ser un entero >= 0.");
            return;
        }

        Producto nuevo = new ProductoNoPerecedero(codigo.trim(), nombre.trim(), costo, precio, cantidad, ubicacion.trim(), garantia);
        System.out.println(gestion.registrarProducto(nuevo));
    }

    // Busca y muestra la información detallada de un producto por su código
    private static void buscarProducto(Scanner sc, GestionInventario gestion) {
        System.out.print("Ingrese el código del producto a buscar: ");
        String codigo = sc.nextLine();
        if (!Validacion.esTextoValido(codigo)) {
            System.out.println("Error: Debe ingresar un código válido.");
            return;
        }

        Producto encontrado = gestion.buscarPorCodigo(codigo);
        if (encontrado != null) {
            System.out.println("Producto encontrado:\n" + encontrado.mostrarInformacion());
        } else {
            System.out.println("No se encontró ningún producto con el código " + codigo + ".");
        }
    }

    // Actualiza la cantidad disponible en bodega de un producto existente
    private static void actualizarStock(Scanner sc, GestionInventario gestion) {
        System.out.print("Ingrese el código del producto: ");
        String codigo = sc.nextLine();
        if (!Validacion.esTextoValido(codigo)) {
            System.out.println("Error: El código es obligatorio.");
            return;
        }

        System.out.print("Ingrese la nueva cantidad en stock: ");
        int nuevaCantidad = Validacion.validarEnteroNoNegativo(sc.nextLine());
        if (nuevaCantidad == -1) {
            System.out.println("Error: La cantidad debe ser un número entero >= 0.");
            return;
        }

        if (gestion.actualizarStock(codigo, nuevaCantidad)) {
            System.out.println("Stock actualizado correctamente.");
        } else {
            System.out.println("Error: Producto no encontrado o se exceden los límites de bodega/presupuesto.");
        }
    }

    // Elimina un producto del inventario por su código
    private static void eliminarProducto(Scanner sc, GestionInventario gestion) {
        System.out.print("Ingrese el código del producto a eliminar: ");
        String codigo = sc.nextLine();
        if (!Validacion.esTextoValido(codigo)) {
            System.out.println("Error: El código es obligatorio.");
            return;
        }

        if (gestion.eliminarProducto(codigo)) {
            System.out.println("Producto eliminado del inventario.");
        } else {
            System.out.println("Error: No existe un producto con el código " + codigo + ".");
        }
    }
}