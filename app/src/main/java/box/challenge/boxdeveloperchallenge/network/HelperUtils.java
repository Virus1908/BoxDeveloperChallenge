package box.challenge.boxdeveloperchallenge.network;

import android.text.TextUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import box.challenge.boxdeveloperchallenge.model.UserProfile;
import box.challenge.boxdeveloperchallenge.tools.JsonTranslator;

public class HelperUtils {
    private static final String NAME = "UserName";
    private static final String EMAIL = "Email";
    private static final String BOX_SIZE_ID = "BoxSizeId";
    private static final String BOX_COLOR_ID = "BoxColorId";
    private static final int NAME_MIN_LENGTH = 6;

    public static SignUpError checkProfile(String profileJson) {
        UserProfile userProfile = JsonTranslator.jsonToUserProfile(profileJson);
        List<String> missingFields = new LinkedList<>();
        List<String> invalidFields = new LinkedList<>();
        boolean isColorWrong = false;
        if (TextUtils.isEmpty(userProfile.getUserName())) {
            missingFields.add(NAME);
        } else {
            if (!isNameValid(userProfile.getUserName())) {
                invalidFields.add(NAME);
            }
        }
        if (TextUtils.isEmpty(userProfile.getEmail())) {
            missingFields.add(EMAIL);
        } else {
            if (!isEmailValid(userProfile.getEmail())) {
                invalidFields.add(EMAIL);
            }
        }
        if (userProfile.getBoxSizeId() < 0 || userProfile.getBoxSizeId() > 2) {
            invalidFields.add(BOX_SIZE_ID);
        }
        if (userProfile.getBoxColorId() < 0 || userProfile.getBoxColorId() > 5) {
            invalidFields.add(BOX_COLOR_ID);
        }
        if (invalidFields.isEmpty()) {
            isColorWrong = new Random().nextBoolean();
        }
        if (missingFields.isEmpty() && invalidFields.isEmpty() && !isColorWrong) {
            return null;
        } else {
            return new SignUpError(missingFields, invalidFields, isColorWrong);
        }
    }

    private static boolean isNameValid(String name) {
        return name.length() >= NAME_MIN_LENGTH;
    }

    private static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
