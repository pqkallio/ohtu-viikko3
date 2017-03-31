package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;

public class AuthenticationService {
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MIN_ALFA_I = 10;
    private static final int MAX_ALFA_I = 35;
    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password
        boolean usernameInvalid = usernameInvalid(username);
        boolean passwordInvalid = passwordInvalid(password);
        
        return usernameInvalid || passwordInvalid;
    }

    private boolean usernameInvalid(String username) {
        if (username.length() < MIN_USERNAME_LENGTH) {
            return true;
        }
        
        for (int i = 0; i < username.length(); i++) {
            int c = Character.getNumericValue(username.charAt(i));
            
            if (c < MIN_ALFA_I || c > MAX_ALFA_I) {
                return true;
            }
        }
        
        return false;
    }

    private boolean passwordInvalid(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            return true;
        }
        
        for (int i = 0; i < password.length(); i++) {
            int c = Character.getNumericValue(password.charAt(i));
            
            if (c < MIN_ALFA_I || c > MAX_ALFA_I) {
                return false;
            }
        }
        
        return true;
    }
}
