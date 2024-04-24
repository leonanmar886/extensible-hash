package models;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private String name;
    private List<Integer> entryValues;

    public Bucket(String name, List<Integer> entryValues) {
        this.name = name;
        this.entryValues = entryValues;
    }

    public Bucket() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getEntryValues() {
        return entryValues;
    }

    public void setEntryValues(List<Integer> entryValues) {
        this.entryValues = entryValues;
    }
}
