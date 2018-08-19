package box.challenge.boxdeveloperchallenge.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import box.challenge.boxdeveloperchallenge.model.BoxColor;
import box.challenge.boxdeveloperchallenge.model.BoxSize;
import box.challenge.boxdeveloperchallenge.model.UserProfile;
import box.challenge.boxdeveloperchallenge.network.SignUpError;

public class JsonTranslator {
    private static Gson gson;

    public static String userProfileToJson(UserProfile userProfile) {
        return getGson().toJson(userProfile);
    }

    public static UserProfile jsonToUserProfile(String userProfileJson) {
        return getGson().fromJson(userProfileJson, UserProfile.class);
    }

    public static String boxSizeToJson(BoxSize boxSize) {
        return getGson().toJson(boxSize);
    }

    public static BoxSize jsonToBoxSize(String boxSizeJson) {
        return getGson().fromJson(boxSizeJson, BoxSize.class);
    }

    public static String boxColorToJson(BoxColor boxColor) {
        return getGson().toJson(boxColor);
    }

    public static BoxColor jsonToBoxColor(String boxColorJson) {
        return getGson().fromJson(boxColorJson, BoxColor.class);
    }

    public static String signUpErrorToJson(SignUpError signUpError) {
        return getGson().toJson(signUpError);
    }

    public static SignUpError jsonToSignUpError(String signUpErrorJson) {
        return getGson().fromJson(signUpErrorJson, SignUpError.class);
    }

    private static Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }
}
