package com.example.transportcompanybackend.etc;


public class Constants {
    public static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";
    public static final String EMAIL_PATTERN = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$";
    public static final String NOT_VALID_PASSWORD_MESSAGE = "Password must be at least 8 characters long " +
            "and contain 1 uppercase, 1 lowercase and 1 digit";
    public static final String NOT_VALID_EMAIL_MESSAGE = "Invalid email address";
}
