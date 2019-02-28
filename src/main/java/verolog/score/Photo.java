package verolog.score;

import java.util.Set;

public class Photo {
    public boolean vertical;
    public Set<String> tags;
    public String name;

    public Photo(boolean vertical, Set<String> tags, String name) {
        this.vertical = vertical;
        this.tags = tags;
        this.name = name;
    }



}
