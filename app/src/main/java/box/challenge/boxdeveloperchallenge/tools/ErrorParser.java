package box.challenge.boxdeveloperchallenge.tools;

import java.util.LinkedList;
import java.util.List;

import box.challenge.boxdeveloperchallenge.model.MainActivityError;
import box.challenge.boxdeveloperchallenge.network.SignUpError;

public class ErrorParser {
    private static final String NAME = "UserName";
    private static final String EMAIL = "Email";
    private static final String BOX_SIZE_ID = "BoxSizeId";
    private static final String BOX_COLOR_ID = "BoxColorId";

    public List<MainActivityError> parseSignUpError(String message) {
        SignUpError signUpError = JsonTranslator.jsonToSignUpError(message);
        List<MainActivityError> errors = new LinkedList<>();
        if (signUpError.getMissingFields() != null) {
            for (String missingFieldName : signUpError.getMissingFields()) {
                if (NAME.equals(missingFieldName)) {
                    errors.add(MainActivityError.MISSING_NAME);
                } else if (EMAIL.equals(missingFieldName)) {
                    errors.add(MainActivityError.MISSING_EMAIL);
                }
            }
        }
        if (signUpError.getInvalidFields() != null) {
            for (String invalidFieldName : signUpError.getInvalidFields()) {
                if (NAME.equals(invalidFieldName)) {
                    errors.add(MainActivityError.WRONG_NAME);
                } else if (EMAIL.equals(invalidFieldName)) {
                    errors.add(MainActivityError.WRONG_EMAIL);
                } else if (BOX_SIZE_ID.equals(invalidFieldName)) {
                    errors.add(MainActivityError.WRONG_BOX_SIZE);
                } else if (BOX_COLOR_ID.equals(invalidFieldName)) {
                    errors.add(MainActivityError.WRONG_BOX_COLOR);
                }
            }
        }
        if (signUpError.isWrongColor()) {
            errors.add(MainActivityError.WRONG_SIZE_COLOR_PAIR);
        }
        return errors;
    }
}
