package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DictionaryManagement {
    public static void insertFromCommandline() {
        Scanner scn = new Scanner(System.in);
        int numOfWord = -1;
        while (numOfWord < 1) {
            System.out.print("Number of Words: ");
            try {
                numOfWord = scn.nextInt();
            } catch (InputMismatchException i) {
                System.out.println("Error input (n > 0 && n = integer)");
            }
            scn.nextLine();
        }
            for (int i = 1; i <= numOfWord; i++) {
                System.out.print("Word: ");
                String word = scn.nextLine();
                System.out.print("Definition: ");
                String definition = scn.nextLine();
                LocalDictionary.toWordList(word);
                LocalDictionary.addWord(word, definition);
                System.out.println("-------------------------------------------------");
            }
    }

    public static void insertFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");

                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1];

                    LocalDictionary.toWordList(key);
                    LocalDictionary.addWord(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Please make sure file path is correct");
        }
    }

    public static void dictionaryLookup() {
        Scanner scn = new Scanner(System.in);
        String word = "";
        while (!word.equals("0")) {
            System.out.println("[0] Return");
            System.out.println("Word: ");
            try {
                word = scn.nextLine();
            } catch (NullPointerException n) {
                n.printStackTrace();
            }
            System.out.print("Definition: ");
            System.out.println(LocalDictionary.getDefinition(word));
            System.out.println("-------------------------------------------------");
        }
    }
}
