package box.challenge.boxdeveloperchallenge.model;

import com.google.gson.annotations.Expose;

public class BoxSize {
    @Expose private int id;
    @Expose private String label;

    public BoxSize(int id, String label) {
        this.id = id;
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public int getId() {
        return id;
    }
}
