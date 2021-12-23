import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineProcessor {

    ArrayList<User> rightLines = new ArrayList<>();
    ArrayList<ErrorUser> errorLines = new ArrayList<>();

    /**
     * Método que lee las líneas del fichero una a una y las envía como un array de String para ser procesadas.
     * En caso de no existir el fichero o no poderlo leer, lanza una excepción que se procesa imprimiendo por consola
     * un mensaje de error con la descripción.
     *
     * @param filePath Ruta del fichero
     */
    public void readCSVFile(String filePath) throws Exception {

            if (filePath == null) {
                throw new Exception("Nombre de fichero nulo");
            } else if (filePath.equals("")) {
                throw new Exception("Nombre de fichero vacío");
            } else if (!filePath.endsWith(".csv")) {
                String extension = filePath.replaceAll("^.*\\.(.*)$", "$1");
                String message = String.format("Extensión de fichero no compatible (.%s)\n", extension);
                throw new Exception(message);
            }

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                lineCount++;
                processLine(lineCount, values);
            }

    }

    /**
     * Método que procesa una línea del fichero.
     * Si la línea está correctamente formada, la añade al ArrayList de líneas procesadas correctamente.
     * Si está mal formada, o el email está duplicado, imprime por consola un mensaje de error
     * y añade su número de línea al ArrayList de líneas incorrectas.
     *
     * @param lineCount Número de línea dentro del fichero.
     * @param values Array con los valores de la línea.
     */
    void processLine(int lineCount, String[] values) {
        if  (!areFieldsRight(values)) {
            errorLines.add(new ErrorUser(lineCount, "Línea mal formada", values));
        } else if (isEmailDuplicated(values[0])) {
            errorLines.add(new ErrorUser(lineCount, "Correo duplicado", values));
        } else {
            rightLines.add(new User(values[0], values[1], values[2]));
        }
    }

    /**
     * Método que comprueba que los campos de cada línea están correctamente formados.
     * Se comprueba que están todos, que no están vacíos y que el e-mail tiene el formato correcto.
     *
     * @param values Array con los valores de la línea.
     * @return Devuelve true si los campos son correctos, false en caso negativo.
     */
    private boolean areFieldsRight(String[] values) {
        return values != null && values.length == 3 && !areFieldsEmptyStrings(values) && isEmailRight(values[0]);
    }

    /**
     * Método que comprueba si los campos de la línea están vacíos.
     *
     * @param values Array con los valores de la línea.
     * @return Devuelve true si alguno de los campos está vacío, false en caso negativo.
     */
    private boolean areFieldsEmptyStrings(String[] values) {
        return (values[0].equalsIgnoreCase("") ||
                values[1].equalsIgnoreCase("") ||
                values[2].equalsIgnoreCase(""));
    }

    /**
     * Método que comprueba si el e-mail tiene un formato correcto.
     *
     * @param email E-mail de la línea procesada.
     * @return Devuelve true si el e-mail tiene un formato correcto, false en caso negativo.
     */
    private boolean isEmailRight(String email) {
        final Pattern PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = PATTERN.matcher(email);
        return matcher.find();
    }

    /**
     * Método que comprueba si el e-mail está ya almacenado en el ArrayList de líneas procesadas correctamente.
     *
     * @param email E-mail de la línea procesada.
     * @return True si el e-mail ya se ha guardado antes, false en caso negativo.
     */
    private boolean isEmailDuplicated(String email) {
        boolean emailDuplicated = false;

        for (User record : rightLines) {
            if (record.getEmail().equals(email)) {
                emailDuplicated = true;
                break;
            }
        }
        return emailDuplicated;
    }
}
