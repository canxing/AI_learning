package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloTest {

    @Test
    void testGetGreetingWithDefaultName() {
        // 测试传入特定名字
        String result = Hello.getGreeting("Gemini");
        assertEquals("Hello, Gemini!", result, "Greeting should contain the provided name");
    }

    @Test
    void testGetGreetingWithDifferentName() {
        // 测试传入另一个名字
        String result = Hello.getGreeting("World");
        assertEquals("Hello, World!", result, "Greeting should adapt to different names");
    }
}
