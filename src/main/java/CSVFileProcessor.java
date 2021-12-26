import java.util.Arrays;

public class CSVFileProcessor {

    private final String filePath;
    private final LineProcessor processor;

    public CSVFileProcessor(String filePath) {
        this.filePath = filePath;
        this.processor = new LineProcessor();
    }

    /**
     * Método que inicia el proceso del archivo.
     * Si el archivo no existe, o es nulo, pide otro archivo por consola.
     */
    void init() {
        try {
            processor.readCSVFile(filePath);
            showResults();
        } catch (Exception e) {
            System.err.printf("\nError: %s\n", e.getMessage());
        }
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
