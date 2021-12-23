import java.util.Arrays;
import java.util.Scanner;

public class CSVFileProcessor {

    private String filePath;
    private final LineProcessor processor;

    public CSVFileProcessor(String filePath) {
        this.filePath = filePath;
        this.processor = new LineProcessor();
    }

    void init() {
        if (filePath == null || filePath.equals("")) {
            filePath = getFilePath();
        }
        processor.readCSVFile(filePath);
        showResults();
    }

    /**
     * Método que obtiene la ruta del fichero.
     *
     * @return Ruta del fichero
     */
    private String getFilePath() {
        Scanner scanner = new Scanner(System.in);
        String filePath;

        System.out.println("Introduce el fichero a procesar o deja en blanco y pulsa ENTER\n" +
                "para procesar el archivo de prueba example.csv:");

        filePath = scanner.nextLine();
        scanner.close();
        return filePath;
    }


    /**
     * Método que muestra los resultados del proceso por consola.
     */
    void showResults() {
        String message = String.format("\nNúmero de líneas procesadas correctamente: %d\n", processor.rightLines.size());
        System.out.print(message);
        System.out.println("-".repeat(message.length() - 2));
        processor.rightLines.forEach(System.out::println);

        message = String.format("\nNúmero de líneas erróneas: %d\n", processor.errorLines.size());
        System.out.print(message);
        System.out.println("-".repeat(message.length() - 2));
        processor.errorLines.forEach(x -> {
            if (x.getError().equals("Correo duplicado")){
                System.out.printf("Línea %d - Error: %s - '%s'\n", x.getLine(), x.getError(),x.getValues()[0]);
            } else {
                System.out.printf("Línea %d - Error: %s - %s\n", x.getLine(), x.getError(), Arrays.toString(x.getValues()));
            }
        });
    }
}
