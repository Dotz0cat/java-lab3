import java.util.stream.Stream;

public class FileHandler {
    private String file;

    public FileHandler() {
        this.file = null;
    }

    public FileHandler(String file) {
        this.file = file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public Stream<DataCarrier> getStreamOf() {
       return null;
    }
}
