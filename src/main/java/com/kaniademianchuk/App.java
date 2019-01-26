package com.kaniademianchuk;

import com.kaniademianchuk.ui.MainMenu;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        new MainMenu(reader).run();
    }

}
