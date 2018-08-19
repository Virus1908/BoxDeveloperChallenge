package box.challenge.boxdeveloperchallenge.network;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import box.challenge.boxdeveloperchallenge.tools.JsonTranslator;

public class UserProfileApi {
    @SuppressLint("StaticFieldLeak")
    public void signUp(String userProfileJson, NetworkCallback callback) {
        new AsyncTask<String, Void, SignUpError>() {
            @Override
            protected SignUpError doInBackground(String... profile) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return HelperUtils.checkProfile(profile[0]);
            }

            @Override
            protected void onPostExecute(SignUpError error) {
                if (error == null) {
                    callback.onSuccess();
                } else {
                    callback.onFail(JsonTranslator.signUpErrorToJson(error));
                }
            }
        }.execute(userProfileJson);
    }
}
