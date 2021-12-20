import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVFileProcessor {

    static ArrayList<ArrayList<String>> rightLines = new ArrayList<>();
    static ArrayList<Integer> errorLines = new ArrayList<>();

    /**
     * Método que lee las líneas del fichero una a una y las envía como un array de String para ser procesadas.
     * En caso de no existir el fichero o no poderlo leer, lanza una excepción que se procesa imprimiendo por consola
     * un mensaje de error con la descripción.
     *
     * @param filePath Ruta del fichero
     */
    static void readCSVFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                lineCount++;
                processLine(lineCount, values);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
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
    private static void processLine(int lineCount, String[] values) {
        if  (!areFieldsRight(values)) {
            errorLines.add(lineCount);
            System.err.printf("Línea %d - Línea mal formada\n", lineCount);
        } else if (isEmailDuplicated(values[0])) {
            errorLines.add(lineCount);
            System.err.printf("Línea %d - Correo duplicado: %s\n", lineCount, values[0]);
        } else {
            rightLines.add(new ArrayList<>(List.of(values)));
            System.out.printf("Línea %d: Procesada correctamente\n", lineCount);
        }
    }

    /**
     * Método que comprueba que los campos de cada línea están correctamente formados.
     * Se comprueba que están todos, que no están vacíos y que el e-mail tiene el formato correcto.
     *
     * @param values Array con los valores de la línea.
     * @return Devuelve true si los campos son correctos, false en caso negativo.
     */
    private static boolean areFieldsRight(String[] values) {
        return values != null && values.length == 3 && !areFieldsEmptyStrings(values) && isEmailRight(values[0]);
    }

    /**
     * Método que comprueba si los campos de la línea están vacíos.
     *
     * @param values Array con los valores de la línea.
     * @return Devuelve true si alguno de los campos está vacío, false en caso negativo.
     */
    private static boolean areFieldsEmptyStrings(String[] values) {
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
    private static boolean isEmailRight(String email) {
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
    private static boolean isEmailDuplicated(String email) {
        boolean emailDuplicated = false;

        for (List<String> record : rightLines) {
            if (record.get(0).equals(email)) {
                emailDuplicated = true;
                break;
            }
        }

        return emailDuplicated;
    }
}
