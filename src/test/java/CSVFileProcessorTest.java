import org.junit.jupiter.api.Test;

class CSVFileProcessorTest {

    @Test
    void init() {
        CSVFileProcessor processor = new CSVFileProcessor("src/main/resources/example.csv");
        processor.init();
    }

}