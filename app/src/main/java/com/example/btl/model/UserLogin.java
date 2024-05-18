package com.example.btl.model;

public class UserLogin {
    private static UserLogin instance;
    private User currentUser;

    private UserLogin() {
        // Khởi tạo
    }

    public static synchronized UserLogin getInstance() {
        if (instance == null) {
            instance = new UserLogin();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}

