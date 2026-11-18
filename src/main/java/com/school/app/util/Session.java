package com.school.app.util;

public class Session {
    private static String currentUser;
    private static String currentRole; // ADMIN, TEACHER, STUDENT
    private static int currentUserId;

    public static void login(String user, String role, int id) {
        currentUser = user;
        currentRole = role;
        currentUserId = id;
    }

    public static String getCurrentUser() { return currentUser; }
    public static String getCurrentRole() { return currentRole; }
    public static int getCurrentUserId() { return currentUserId; }
}
