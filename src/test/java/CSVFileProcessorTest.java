import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVFileProcessorTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setErr(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        System.setErr(standardOut);
    }

    @Test
    void initRightFile() {
        CSVFileProcessor processor = new CSVFileProcessor("src/main/resources/example.csv");

        processor.init();
        assertEquals("""
                Número de líneas procesadas correctamente: 10
                ---------------------------------------------
                User{email='alumno1@dominio.com', fullName='NombreAlumno1 ApellidoAlumno1', username='usuario1'}
                User{email='alumno2@dominio.com', fullName='NombreAlumno2 ApellidoAlumno2', username='usuario2'}
                User{email='alumno3@dominio.com', fullName='NombreAlumno3 ApellidoAlumno3', username='usuario3'}
                User{email='alumno4@dominio.com', fullName='NombreAlumno4 ApellidoAlumno4', username='usuario4'}
                User{email='alumno5@dominio.com', fullName='NombreAlumno5 ApellidoAlumno5', username='usuario5'}
                User{email='alumno17@dominio.com', fullName='NombreAlumno17 ApellidoAlumno17', username='usuario17'}
                User{email='alumno18@dominio.com', fullName='NombreAlumno18 ApellidoAlumno18', username='usuario18'}
                User{email='alumno20@dominio.com', fullName='NombreAlumno17 ApellidoAlumno17', username='usuario17'}
                User{email='alumno22@dominio.com', fullName='NombreAlumno22 ApellidoAlumno22', username='usuario22'}
                User{email='alumno23@dominio.com', fullName='NombreAlumno23 ApellidoAlumno23', username='usuario23'}

                Número de líneas erróneas: 15
                -----------------------------
                Línea 6 - Error: Línea mal formada - [alumno6@dominio, NombreAlumno6 ApellidoAlumno6, usuario6]
                Línea 7 - Error: Línea mal formada - [@dominio.com, NombreAlumno7 ApellidoAlumno7, usuario7]
                Línea 8 - Error: Línea mal formada - [NombreAlumno8 ApellidoAlumno8, usuario8]
                Línea 9 - Error: Línea mal formada - [alumno9@dominio.com, , usuario9]
                Línea 10 - Error: Línea mal formada - [alumno10@dominio.com, NombreAlumno10 ApellidoAlumno10]
                Línea 11 - Error: Línea mal formada - [alumno11@.com, NombreAlumno11 ApellidoAlumno11, usuario11]
                Línea 12 - Error: Línea mal formada - [alumno12@.com, NombreAlumno12 ApellidoAlumno12]
                Línea 13 - Error: Línea mal formada - []
                Línea 14 - Error: Línea mal formada - []
                Línea 15 - Error: Línea mal formada - [alumno15@dominio.com, NombreAlumno15 ApellidoAlumno15, usuario15, password15]
                Línea 16 - Error: Correo duplicado - 'alumno1@dominio.com'
                Línea 19 - Error: Línea mal formada - [alumno19@dominio.com, NombreAlumno1 ApellidoAlumno16]
                Línea 21 - Error: Línea mal formada - [alumno21@dominio.com, , usuario18]
                Línea 24 - Error: Correo duplicado - 'alumno23@dominio.com'
                Línea 25 - Error: Línea mal formada - [alumno25@dominio., NombreAlumno25 ApellidoAlumno25, usuario25]""",
                outputStreamCaptor.toString().trim());
    }

    @Test
    void initWrongFile() {
        CSVFileProcessor processor = new CSVFileProcessor("src/main/resources/example.txt");

        processor.init();
        assertEquals("Error: Extensión de fichero no compatible (.txt)", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void initNullFile() {
        CSVFileProcessor processor = new CSVFileProcessor(null);

        processor.init();
        assertEquals("Error: Nombre de fichero nulo", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void initEmptyFile() {
        CSVFileProcessor processor = new CSVFileProcessor("");

        processor.init();
        assertEquals("Error: Nombre de fichero vacío", outputStreamCaptor.toString()
                .trim());
    }

}