package main;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DictionaryCommandline {

    public static void dictionaryAdvanced() {
        int para = 10;
        while (para != 0) {
            Scanner scn = new Scanner(System.in);
            System.out.println("""
                        [0] Exit
                        [1] Add
                        [2] Remove
                        [3] Update
                        [4] Display
                        [5] Lookup
                        [6] Search
                        [7] Game
                        [8] Insert from file
                        [9] Export to file""");
            try {
                para = scn.nextInt();
            } catch (InputMismatchException i) {
                i.printStackTrace();
                para = 10;
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
                    DictionaryManagement.dictionaryRemove();
                    break;
                case 3:
                    DictionaryManagement.dictionaryUpdate();
                    break;
                case 4:
                    showAllWords();
                    break;
                case 5:
                    DictionaryManagement.dictionaryLookup();
                    break;
                case 6:
                    dictionarySearcher();
                    break;
                case 7:
                    TranslateAPI.sentenceTranslator();
                    break;
                case 8:
                    DictionaryManagement.dictionaryinsertFromFile();
                    break;
                case 9:
                    DictionaryManagement.dictionaryExportToFile();
                    break;
                default:
                    System.out.println("Action not supported");
            }
        }
    }

    public static void showAllWords() {
        for (String i : LocalDictionary.getWordlist()) {
            System.out.println(LocalDictionary.getDefinition(i)
                    + "\n----------------------------------------------------");
        }
    }

    public static void dictionarySearcher() {
        Scanner scn = new Scanner(System.in);
        String find = "";
        while (find.isEmpty()) {
            System.out.print("Find: ");
            find = scn.nextLine().toLowerCase().trim();
            if (find.isEmpty()) {
                System.out.println("There is no input.");
            }
        }
        List<String> wordlist = LocalDictionary.getWordlist();
        int begin = 0;
        int end = wordlist.size() - 1;

        try {
            begin = LocalDictionary.getIndex().get((int) find.charAt(0) - 97);
            if (begin == 122) {
                end = LocalDictionary.getIndex().get((int) find.charAt(0) - 96) - 1;
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("There are no words start with:" + find);
        }
        wordlist = wordlist.subList(begin, end);

        boolean nonexistentword = true;
        for (String word : wordlist) {
            if (word.matches(find + "(.*)")) {
                System.out.println(word);
                nonexistentword = false;
            }
        }
        if (nonexistentword) {
            System.out.println("There are no words start with:" + find);
        }
    }

    static {
        DictionaryDatabase.loadLocalDictionary();
        dictionaryAdvanced();
    }
    public static void main(String[] args) {
    }
}