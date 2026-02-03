package com.example;

public class Hello {
    public static void main(String[] args) {
        String name = args.length > 0 ? args[0] : "Gemini CLI";
        System.out.println(getGreeting(name));
        System.out.println("Current Java version: " + System.getProperty("java.version"));
    }

    public static String getGreeting(String name) {
        return "Hello, " + name + "!";
    }
}
