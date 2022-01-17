package eu.palantir.portal.dto.options;

import java.util.ArrayList;
import java.util.List;

public class ToList {
    public List<String> list = new ArrayList<>();

    public ToList(String query) {
        String[] items = query.split(",");
        for (String item : items) {
            this.list.add(item);
        }
    }

    @Override
    public String toString() {
        return "ToList{" + "list=" + list + '}';
    }

}
