package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DictionaryManagement {
    public static Scanner scn = new Scanner(System.in);
    public static void insertFromCommandline() {
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
                System.out.print("Word to insert: ");
                String word = scn.nextLine().toLowerCase();
                if ((int) word.charAt(0) < 97 || (int) word.charAt(0) > 122) {
                    System.out.println("\nWord should start with a letter from alphabet");
                    continue;
                }
                if (!LocalDictionary.getDefinition(word).equals("word not found! \n")) {
                    System.out.print("Word already exist! \n Do you want to update this word? [Y/N]: ");
                    String answer = scn.nextLine();
                    if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                        InputHandle.inputUpdateDefinition(word);
                    }
                    continue;
                }
                System.out.print("Definition: ");
                String definition = word + InputHandle.inputDefinition();
                LocalDictionary.putWord(word, definition);
                System.out.println("-------------------------------------------------");
            }
    }

    public static void dictionaryUpdate() {
        String word = "";
        while (true) {
            System.out.println("[0] Exit");
            System.out.print("Word to update: ");
            try {
                word = scn.nextLine().toLowerCase();
            } catch (NullPointerException n) {
                n.printStackTrace();
            }
            if (word.equals("0")) {
                break;
            }
            if ((int) word.charAt(0) < 97 || (int) word.charAt(0) > 122) {
                System.out.println("\nWord should start with a letter from alphabet");
                continue;
            }
            if ("word not found! \n".equals(LocalDictionary.getDefinition(word))) {
                System.out.println("word not found! \n" + "Do you want to add this word? [Y/N]:");
                String answer = scn.nextLine();
                if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                    System.out.println("Word: " + word);
                    System.out.println("Definition: ");
                    String definition = word + InputHandle.inputDefinition();
                    LocalDictionary.putWord(word, definition);
                } else {
                    continue;
                }
            } else {
                System.out.println("This is word definition: ");
                System.out.println(LocalDictionary.getDefinition(word));
                InputHandle.inputUpdateDefinition(word);
            }
            System.out.println("-------------------------------------------------");
        }
    }

    public static void dictionaryRemove() {
        String word = "";
        while (true) {
            System.out.println("[0] Exit");
            System.out.print("Word to remove: ");
            try {
                word = scn.nextLine().toLowerCase();
            } catch (NullPointerException n) {
                n.printStackTrace();
            }
            if (word.equals("0")) {
                break;
            }
            if ("word not found! \n".equals(LocalDictionary.getDefinition(word))) {
                System.out.println("word not found! \n");
            } else {
                LocalDictionary.removeWord(word);
                System.out.println("Word removed! \n");
            }
        }
    }

    public static void dictionaryLookup() {
        String word = "";
        while (true) {
            System.out.println("[0] Exit");
            System.out.print("Word to lookup: ");
            try {
                word = scn.nextLine().toLowerCase();
            } catch (NullPointerException n) {
                n.printStackTrace();
            }
            if (word.equals("0")) {
                break;
            }
            if ("word not found! \n".equals(LocalDictionary.getDefinition(word)) ) {
                System.out.println("word not found! \n" + "Do you want to add this word? [Y/N]:");
                String answer = scn.nextLine();
                if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                    System.out.println("Word: " + word);
                    System.out.println("Definition:  ");
                    String definition = word + InputHandle.inputDefinition();
                    LocalDictionary.putWord(word, definition);
                } else {
                    continue;
                }
            } else {
                System.out.print("Definition: ");
                System.out.println(LocalDictionary.getDefinition(word));
            }
            System.out.println("-------------------------------------------------");
        }
    }

    public static void insertFromFile() {
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
