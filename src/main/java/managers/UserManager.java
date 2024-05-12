package main.java.managers;

public class UserManager {

    private UserManager() {}

    private static class UserManagerHolder {
        private static final UserManager HOLDER_INSTANCE = new UserManager();
    }

    public static UserManager getInstance() {
        return UserManagerHolder.HOLDER_INSTANCE;
    }

}
