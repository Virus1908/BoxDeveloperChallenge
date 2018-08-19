package box.challenge.boxdeveloperchallenge.model;

import com.google.gson.annotations.Expose;

public class UserProfile {
    @Expose private String userName;
    @Expose private String email;
    @Expose private int boxSizeId;
    @Expose private int boxColorId;
    @Expose private boolean printName;

    public UserProfile(String userName, String email, int boxSizeId, int boxColorId, boolean printName) {
        this.userName = userName;
        this.email = email;
        this.boxSizeId = boxSizeId;
        this.boxColorId = boxColorId;
        this.printName = printName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public int getBoxSizeId() {
        return boxSizeId;
    }

    public int getBoxColorId() {
        return boxColorId;
    }

    public boolean isPrintName() {
        return printName;
    }
}
