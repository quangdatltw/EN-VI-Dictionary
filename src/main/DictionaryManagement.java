package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DictionaryManagement {
    public static Scanner scn = new Scanner(System.in);
    public static void insertFromCommandline() {
        int numOfWord = -1;
        String word;
        while (numOfWord < 1) {
            System.out.print("Number of words to add: ");
            try {
                numOfWord = scn.nextInt();
            } catch (InputMismatchException i) {
                System.out.println("Error input (n > 0 && n = integer)");
            }
        }
        scn.nextLine();
            for (int i = 1; i <= numOfWord; i++) {
                word = InputHandle.inputString();
                int check = wordAvailable(word, 2);
                if (check == 0){
                    break;
                } else if (check == 1) {
                    continue;
                } else {
                    System.out.print("Definition: ");
                    LocalDictionary.putWord(word,word + InputHandle.inputDefinition());
                }
                System.out.println("-------------------------------------------------");
            }
    }

    public static void dictionaryUpdate() {
        String word;
        while (true) {
            word = InputHandle.inputString();
            int check = wordAvailable(word, 1);
            if (check == 0){
                break;
            } else if (check == 1) {
                continue;
            } else {
                System.out.println("This is word definition: ");
                System.out.println(LocalDictionary.getDefinition(word));
                InputHandle.inputUpdateDefinition(word);
            }
            System.out.println("-------------------------------------------------");
        }
    }

    public static void dictionaryRemove() {
        String word;
        while (true) {
            word = InputHandle.inputString();
            if (word.equals("0")) {
                break;
            }
            if (!LocalDictionary.checkWord(word)) {
                System.out.println("word not found! \n");
            } else {
                LocalDictionary.removeWord(word);
                System.out.println("Word removed! \n");
            }
        }
    }

    public static void dictionaryLookup() {
        String word;
        while (true) {
            word = InputHandle.inputString();
            int check = wordAvailable(word, 1);
            if (check == 0){
                break;
            } else if (check == 1) {
                continue;
            } else {
                System.out.print("Definition: ");
                System.out.println(LocalDictionary.getDefinition(word));
            }
            System.out.println("-------------------------------------------------");
        }
    }

    private static int wordAvailable(String word, int mode) {
        if (word.equals("0")) {
            return 0;
        }
        if (word.isEmpty()) {
            System.out.println("Input is empty");
            return 1;
        }
        if ((int) word.charAt(0) < 97 || (int) word.charAt(0) > 122) {
            System.out.println("\nWord should start with a letter from alphabet");
            return 1;
        }

        if (!LocalDictionary.checkWord(word) && mode == 1) {
            System.out.println("word not found! \n" + "Do you want to add this word? [Y/N]: ");
            if (scn.nextLine().equalsIgnoreCase("y")) {
                System.out.println("Word: " + word);
                System.out.println("Definition: ");
                String definition = word + InputHandle.inputDefinition();
                LocalDictionary.putWord(word, definition);
            }
            return 1;
        }

        if (LocalDictionary.checkWord(word) && mode == 2) {
            System.out.print("Word already exist! \n Do you want to update this word? [Y/N]: ");
            if (scn.nextLine().equalsIgnoreCase("y")) {
                InputHandle.inputUpdateDefinition(word);
            }
            return 1;
        }

        return -1;
    }

    public static void dictionaryinsertFromFile() {
        String result = null;
        String filePath;
        while (result == null) {
            System.out.println("File path:");
            filePath = scn.nextLine().trim();
            if (filePath.equals("0")) {
                break;
            }
            result = InputHandle.inputFile(filePath);
        }
        System.out.println(result);
    }

    public static void dictionaryExportToFile() {
        try (FileWriter writer = new FileWriter("Dictionary_EX.txt")) {
            String def;
            for (String word : LocalDictionary.getWordlist()) {
                def = LocalDictionary.getDefinition(word).replace("\n", "\\n");
                writer.write(word + "\t" + def + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Dictionary exported: EN-VI-Dictionary\\Dictionary_Ex.txt");
    }
}
