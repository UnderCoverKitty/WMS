public class SessionManager {
    private static SessionManager instance;
    private String loggedInUserName;
    private String currentUserRole;

    // Private constructor to enforce singleton pattern
    private SessionManager() {}

    // Get the single instance of SessionManager
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Set the logged-in user's name
    public void setLoggedInUserName(String name) {
        this.loggedInUserName = name;
    }

    // Get the logged-in user's name
    public String getLoggedInUserName() {
        return loggedInUserName;
    }

    // Set the current user's role
    public void setCurrentUserRole(String role) {
        this.currentUserRole = role;
    }

    // Get the current user's role
    public String getCurrentUserRole() {
        return currentUserRole;
    }
}