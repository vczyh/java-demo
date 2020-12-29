package com.vczyh;

public class App {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Hello World!");
            try {
                throw new NullPointerException("error");
            } catch (Exception e) {
                System.out.println(e);
                break;
            }
        }
    }
}
