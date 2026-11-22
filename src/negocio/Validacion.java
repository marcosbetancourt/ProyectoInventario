package negocio;

/**
 * Clase utilitaria con métodos estáticos para validar entradas de usuario
 * (campos vacíos, formatos numéricos con try-catch y rangos permitidos).
 */
public class Validacion {
    public static boolean esTextoValido(String texto){
        if (texto != null && !texto.trim().isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public static double validarDecimalPositivo(String entrada){
        if (!esTextoValido(entrada)){
            return -1;
        }
        try {
            double valor = Double.parseDouble(entrada.trim());
            if (valor > 0){
                return valor;
            } else {
                return -1;
            }
        } catch (NumberFormatException e){
            return -1;
        }
    }

    public static int validarEnteroNoNegativo(String entrada){
        if (!esTextoValido(entrada)){
            return -1;
        }
        try {
            int valor = Integer.parseInt(entrada.trim());
            if (valor >= 0){
                return valor;
            } else {
                return -1;
            }
        } catch (NumberFormatException e){
            return -1;
        }
    }
}
