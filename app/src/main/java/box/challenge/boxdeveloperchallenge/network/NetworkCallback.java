package box.challenge.boxdeveloperchallenge.network;

public interface NetworkCallback {
    void onSuccess();

    void onFail(String message);
}
