package box.challenge.boxdeveloperchallenge.network;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SignUpError {
    @Expose private List<String> missingFields;
    @Expose private List<String> invalidFields;
    @Expose private boolean wrongColor;

    public SignUpError(List<String> missingFields, List<String> invalidFields, boolean wrongColor) {
        this.missingFields = missingFields;
        this.invalidFields = invalidFields;
        this.wrongColor = wrongColor;
    }

    public List<String> getMissingFields() {
        return missingFields;
    }

    public List<String> getInvalidFields() {
        return invalidFields;
    }

    public boolean isWrongColor() {
        return wrongColor;
    }
}
