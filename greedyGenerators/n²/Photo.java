import java.io.IOException;
import java.util.HashSet;

@SuppressWarnings("Duplicates")
public class Photo {
    HashSet<String> tags;
    String name;

    public Photo(HashSet<String> tags, String name) {
        this.tags = tags;
        this.name = name;
    }
}