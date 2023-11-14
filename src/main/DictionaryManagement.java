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
            System.out.print("Number of words to add: ");
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
                if (!LocalDictionary.getDefinition(word).equals("word not found! \n")) {
                    System.out.print("Word already exist! \n Do you want to update this word? [Y/N]: ");
                    String answer = scn.nextLine();
                    if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {

                    } else {
                        continue;
                    }
                }
                System.out.print("Definition: ");
                String definition = word + "\n" + InputHandle.inputDefinition();
                LocalDictionary.toWordList(word);
                LocalDictionary.addWord(word, definition);
                System.out.println("-------------------------------------------------");
            }
    }


    public static void insertFromFile() {
        Scanner scn = new Scanner(System.in);
        String result = null;
        String filePath;
        while (result == null) {
            System.out.println("File path:");
            filePath = scn.nextLine();
            if (filePath.equals("0")) {
                break;
            }
            result = InputHandle.inputFile(filePath);
        }
        System.out.println(result);
    }

    public static void dictionaryLookup() {
        Scanner scn = new Scanner(System.in);
        String word = "";
        while (!word.equals("0")) {
            System.out.println("[0] Return");
            System.out.print("Word: ");
            try {
                word = scn.nextLine();
            } catch (NullPointerException n) {
                n.printStackTrace();
            }
            if ("word not found! \n".equals(LocalDictionary.getDefinition(word))) {
                System.out.println("word not found! \n");
            } else {
                System.out.println("Definition: ");
                System.out.println(LocalDictionary.getDefinition(word));
            }
            System.out.println("-------------------------------------------------");
        }
    }
}
