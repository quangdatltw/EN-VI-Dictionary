package main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DictionaryCommandline {
    public static void showAllWords() {
        for (String word : LocalDictionary.wordlist) {
            System.out.println(LocalDictionary.getDefinition(word)
                    + "\n --------------------------------------------------");
        }
    }

    public static void dictionaryBasic() {
        int para = 10;
        while (para != 0) {
            Scanner scn = new Scanner(System.in);
            System.out.println("""
                        [0] Exit
                        [1] Add
                        [2] Display
                        [3] Update""");
            try {
                para = scn.nextInt();
            } catch (InputMismatchException i) {
                i.printStackTrace();
            }
            scn.nextLine();
            switch (para) {
                case 0:
                    System.out.println("Goodbye world!");
                    break;
                case 1:
                    DictionaryManagement.insertFromCommandline();
                    break;
                case 2:
                    showAllWords();
                    break;
                case 3:
                    System.out.println("File path:");
                    String filepath = scn.nextLine();
                    DictionaryManagement.insertFromFile(filepath);
                    break;
                default:
                    System.out.println("Command doesn't exist");
            }
        }
    }
    public static void main(String[] args) {
            DictionaryDatabase.loadLocalDictionary();
            dictionaryBasic();
    }
}