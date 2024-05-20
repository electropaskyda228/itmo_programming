package main.java.models;

public class CurrentUser {
    private User currentUser = null;
    private User previousUser = null;

    private CurrentUser(){}

    private static class CurrentUserHolder {
        private static final CurrentUser HOLDER_INSTANCE = new CurrentUser();
    }

    public static CurrentUser getInstance() {
        return CurrentUserHolder.HOLDER_INSTANCE;
    }

    public void setCurrentUser(User user) {
        this.previousUser = this.currentUser;
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public void goBack() {
        currentUser = previousUser;
    }
}
