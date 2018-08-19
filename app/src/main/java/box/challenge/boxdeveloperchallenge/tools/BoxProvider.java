package box.challenge.boxdeveloperchallenge.tools;

import java.util.LinkedList;
import java.util.List;

import box.challenge.boxdeveloperchallenge.model.BoxColor;
import box.challenge.boxdeveloperchallenge.model.BoxSize;

public class BoxProvider {
    public BoxSize[] getAvailableBoxSizes() {
        BoxSize[] boxSizes = new BoxSize[3];
        boxSizes[0] = new BoxSize(0, "small cube (15 cm x 15 cm x 15 cm)");
        boxSizes[1] = new BoxSize(1, "medium rectangular box (100 cm x 25 cm x 25 cm)");
        boxSizes[2] = new BoxSize(2, "large cube (75 cm x 75 cm x 75 cm)");
        return boxSizes;
    }

    public List<BoxColor> getBoxColorsForSelectedSize(int selectedSizeId) {
        List<BoxColor> boxColors = new LinkedList<>();
        if (selectedSizeId == 0) {
            boxColors.add(new BoxColor(0, "red"));
            boxColors.add(new BoxColor(1, "blue"));
            boxColors.add(new BoxColor(2, "yellow"));
        } else if (selectedSizeId == 1) {
            boxColors.add(new BoxColor(0, "red"));
            boxColors.add(new BoxColor(2, "yellow"));
            boxColors.add(new BoxColor(3, "purple"));
            boxColors.add(new BoxColor(4, "green"));
        } else if (selectedSizeId == 2) {
            boxColors.add(new BoxColor(4, "green"));
            boxColors.add(new BoxColor(5, "orange"));
            boxColors.add(new BoxColor(1, "blue"));
        }
        return boxColors;
    }
}
