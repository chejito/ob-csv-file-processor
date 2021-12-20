import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static ArrayList<ArrayList<String>> rightLines = new ArrayList<>();
    static ArrayList<Integer> errorLines = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filePath;

        do {
            System.out.println("Introduce el fichero a procesar \n(Escribe 'q' y pulsa ENTER para salir del programa):");
             filePath = scanner.nextLine();
            if (filePath.equals("q")) {
                break;
            }
        } while (filePath.equals(""));

        if (!filePath.equals("q")) {
            readCSVFile(filePath);
        }

        System.out.printf("Número de líneas procesadas correctamente: %d\n", rightLines.size());
        rightLines.forEach(System.out::println);
        System.out.printf("Número de líneas erróneas: %d\n", errorLines.size());
        System.out.println("Programa finalizado.");
    }
//        filePath: src/main/resources/example.csv

    private static void readCSVFile(String filePath) {
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

    private static boolean areFieldsRight(String[] values) {
        return values != null && values.length == 3 && !areFieldsEmptyStrings(values) && isEmailRight(values[0]);
    }

    private static boolean areFieldsEmptyStrings(String[] values) {
        return (values[0].equalsIgnoreCase("") ||
                values[1].equalsIgnoreCase("") ||
                values[2].equalsIgnoreCase(""));
    }

    private static boolean isEmailRight(String email) {
        final Pattern PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = PATTERN.matcher(email);

        return matcher.find();
    }

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
