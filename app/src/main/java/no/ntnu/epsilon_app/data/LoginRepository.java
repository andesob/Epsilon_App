package no.ntnu.epsilon_app.data;

import java.util.List;

import no.ntnu.epsilon_app.SharedUserPrefs;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public boolean isAdmin(){
        return user.getGroups().contains(Group.ADMIN);
    }

    public boolean isBoardmember(){
        return user.getGroups().contains(Group.BOARD);
    }

    public void logout() {
        user = null;
        //dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public LoggedInUser login(long userid, String displayname, List<String> groups) {
        // handle login
        LoggedInUser loggedInUser = dataSource.login(userid, displayname, groups);
        if (loggedInUser != null) {
            setLoggedInUser(loggedInUser);
        }
        return loggedInUser;
    }
}