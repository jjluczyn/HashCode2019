package verolog.score;

import java.util.HashSet;

public class Photo {
    public boolean vertical;
    public HashSet<String> tags;
    public String name;

    public Photo(boolean vertical, HashSet<String> tags, String name) {
        this.vertical = vertical;
        this.tags = tags;
        this.name = name;
    }



}
