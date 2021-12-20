import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String filePath = getFilePath();

        // Si la ruta del fichero está vacía, se procesa el fichero de prueba "example.csv" de la carpeta resources.
        if (filePath.equals("")) {
            CSVFileProcessor.readCSVFile("src/main/resources/example.csv");
        } else {
            CSVFileProcessor.readCSVFile(filePath);
        }

        showResults();
    }

    /**
     * Método que muestra los resultados del proceso por consola.
     */
    private static void showResults() {
        System.out.printf("\nNúmero de líneas procesadas correctamente: %d\n", CSVFileProcessor.rightLines.size());
        CSVFileProcessor.rightLines.forEach(System.out::println);
        System.out.printf("\nNúmero de líneas erróneas: %d\n", CSVFileProcessor.errorLines.size());
    }

    /**
     * Método que obtiene la ruta del fichero.
     *
     * @return Ruta del fichero
     */
    private static String getFilePath() {
        Scanner scanner = new Scanner(System.in);
        String filePath;

        System.out.println("Introduce el fichero a procesar o deja en blanco y pulsa ENTER\n" +
                "para procesar el archivo de prueba example.csv:");

        filePath = scanner.nextLine();
        scanner.close();
        return filePath;
    }
}
