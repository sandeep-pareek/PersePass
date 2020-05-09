package com.sandeep.persepass.data;

import com.sandeep.persepass.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            if (!username.isEmpty() && !password.isEmpty()) {
                LoggedInUser user =
                        new LoggedInUser(
                                password,
                                username);
                return new Result.Success<>(user);
            }
            return new Result.Error(new Exception("Invalid user or password!"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
