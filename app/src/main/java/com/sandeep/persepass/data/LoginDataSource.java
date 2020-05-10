package com.sandeep.persepass.data;

import com.sandeep.persepass.data.model.LoggedInUser;
import com.sandeep.persepass.security.Secure;

import java.io.IOException;

public class LoginDataSource {
    public Result<LoggedInUser> login(String username, String password) {

        try {
            if (!username.isEmpty() && !password.isEmpty()) {
                LoggedInUser user =
                        new LoggedInUser(
                                Secure.getMD5Hash(password),
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
