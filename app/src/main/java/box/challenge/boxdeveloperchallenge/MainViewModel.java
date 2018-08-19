package box.challenge.boxdeveloperchallenge;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.LinkedList;
import java.util.List;

import box.challenge.boxdeveloperchallenge.model.BoxColor;
import box.challenge.boxdeveloperchallenge.model.BoxSize;
import box.challenge.boxdeveloperchallenge.model.MainActivityError;
import box.challenge.boxdeveloperchallenge.model.MainActivityEvent;
import box.challenge.boxdeveloperchallenge.model.UserProfile;
import box.challenge.boxdeveloperchallenge.network.NetworkCallback;
import box.challenge.boxdeveloperchallenge.network.UserProfileApi;
import box.challenge.boxdeveloperchallenge.tools.BoxProvider;
import box.challenge.boxdeveloperchallenge.tools.ErrorParser;
import box.challenge.boxdeveloperchallenge.tools.InputUtils;
import box.challenge.boxdeveloperchallenge.tools.JsonTranslator;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<List<BoxColor>> boxColors;
    private MutableLiveData<MainActivityEvent> mainActivityEvent;
    private MutableLiveData<List<MainActivityError>> mainActivityErrors;
    private MutableLiveData<Boolean> isSignUpEnabled;

    private UserProfileApi userProfileApi;
    private ErrorParser errorParser;
    private BoxProvider boxProvider;

    public MainViewModel(@NonNull Application application) {
        super(application);
        boxColors = new MutableLiveData<>();
        mainActivityEvent = new MutableLiveData<>();
        mainActivityErrors = new MutableLiveData<>();
        userProfileApi = new UserProfileApi();
        errorParser = new ErrorParser();
        isSignUpEnabled = new MutableLiveData<>();
        isSignUpEnabled.setValue(true);
        boxProvider = new BoxProvider();
    }

    public LiveData<MainActivityEvent> getMainActivityEvent() {
        return mainActivityEvent;
    }

    public LiveData<List<MainActivityError>> getMainActivityErrors() {
        return mainActivityErrors;
    }

    public LiveData<List<BoxColor>> getBoxColors() {
        return boxColors;
    }

    public LiveData<Boolean> getIsSignUpEnabled() {
        return isSignUpEnabled;
    }

    public BoxSize[] getBoxSizes() {
        return boxProvider.getAvailableBoxSizes();
    }

    public void onBoxSizeSelected(int position) {
        boxColors.setValue(boxProvider.getBoxColorsForSelectedSize(getBoxSizes()[position].getId()));
    }

    public void onBoxSizeUnselected() {
        throw new IllegalStateException("box size can't be unselected");
    }

    public void signUp(String name, String email, BoxSize boxSize, BoxColor boxColor, boolean printName) {
        UserProfile userProfile = new UserProfile(name, email, boxSize.getId(), boxColor.getId(), printName);
        if (!isProfileValid(userProfile)) {
            mainActivityErrors.setValue(getProfileErrors(userProfile));
            return;
        }
        isSignUpEnabled.setValue(false);
        mainActivityEvent.setValue(MainActivityEvent.SHOW_PROGRESS_BAR);
        userProfileApi.signUp(JsonTranslator.userProfileToJson(userProfile), new NetworkCallback() {
            @Override
            public void onSuccess() {
                mainActivityEvent.setValue(MainActivityEvent.SUCCESSFUL_SIGN_UP);
                isSignUpEnabled.setValue(true);
                mainActivityEvent.setValue(MainActivityEvent.HIDE_PROGRESS_BAR);
            }

            @Override
            public void onFail(String message) {
                mainActivityEvent.setValue(MainActivityEvent.SIGN_UP_ERROR);
                mainActivityErrors.setValue(errorParser.parseSignUpError(message));
                isSignUpEnabled.setValue(true);
                mainActivityEvent.setValue(MainActivityEvent.HIDE_PROGRESS_BAR);
            }
        });
    }

    private boolean isProfileValid(UserProfile userProfile) {
        return !TextUtils.isEmpty(userProfile.getUserName()) && !TextUtils.isEmpty(userProfile.getEmail())
                && InputUtils.isNameValid(userProfile.getUserName()) && InputUtils.isEmailValid(userProfile.getEmail());
    }

    private List<MainActivityError> getProfileErrors(UserProfile userProfile) {
        List<MainActivityError> errors = new LinkedList<>();
        if (TextUtils.isEmpty(userProfile.getUserName())) {
            errors.add(MainActivityError.MISSING_NAME);
        } else {
            if (!InputUtils.isNameValid(userProfile.getUserName())) {
                errors.add(MainActivityError.WRONG_NAME);
            }
        }
        if (TextUtils.isEmpty(userProfile.getEmail())) {
            errors.add(MainActivityError.MISSING_EMAIL);
        } else {
            if (!InputUtils.isEmailValid(userProfile.getEmail())) {
                errors.add(MainActivityError.WRONG_EMAIL);
            }
        }
        return errors;
    }
}
