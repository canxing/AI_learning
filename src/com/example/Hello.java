package com.example;

public class Hello {
    public static void main(String[] args) {
        String name = "Gemini CLI"; // 默认名字
        
        if (args.length > 0) {
            name = args[0];
        }
        
        System.out.println("Hello, " + name + "!");
        System.out.println("Current Java version: " + System.getProperty("java.version"));
    }
}
