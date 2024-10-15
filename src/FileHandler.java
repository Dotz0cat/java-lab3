import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {
    private final String file;

    public FileHandler(String file) {
        this.file = file;
    }

    public Set<IrisData> getSetOf() {
        InputStream input = getClass().getResourceAsStream(this.file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        Set<IrisData> data = reader.lines()
                .skip(1)
                .map(IrisData::IrisDataMap)
                .collect(Collectors.toSet());
        return data;
    }
}
