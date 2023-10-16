package com.example.appnotas.Adapter;

public class User {
    private static User instance;
    private String userUid;

    private User() {
        // Constructor privado para prevenir la creación directa de instancias
    }

    public static synchronized User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
