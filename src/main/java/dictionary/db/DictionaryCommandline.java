package dictionary.db;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DictionaryCommandline {

    /**
     * Dictionary advanced - show list of available action
     */
    public static void dictionaryAdvanced() {
        int para = 12;
        while (para != 0) {
            Scanner scn = new Scanner(System.in);
            System.out.print("""
                        [0] Exit
                        [1] Add
                        [2] Remove
                        [3] Update
                        [4] Display
                        [5] Lookup
                        [6] Search
                        [7] Game
                        [8] Insert from file
                        [9] Export to file
                        [10] GG Translate
                        [11] Text To Speech
                        
                        Your action:\s""");
            try {
                para = scn.nextInt();
            } catch (InputMismatchException i) {
                i.printStackTrace();
                para = 12;
            }
            scn.nextLine();
            switch (para) {
                case 0:
                    System.out.println("Goodbye world!");
                    break;
                case 1:
                    DictionaryInteract.insertFromCommandline();
                    break;
                case 2:
                    DictionaryInteract.remove();
                    break;
                case 3:
                    DictionaryInteract.update();
                    break;
                case 4:
                    showAllWords();
                    break;
                case 5:
                    DictionaryInteract.lookup();
                    break;
                case 6:
                    DictionaryInteract.search();
                    break;
                case 7:
                    break;
                case 8:
                    DictionaryInteract.insertDictionaryFromFile();
                    break;
                case 9:
                    DictionaryInteract.exportDictionaryToFile();
                    break;
                case 10:
                    TranslateAPI.translate();
                    break;
                case 11:
                    TextToSpeechAPI.convert();
                    break;
                default:
                    System.out.println("Action not supported");
            }
        }
    }

    /**
     * Show all words.
     */
    public static void showAllWords() {
        for (String i : LocalDictionary.getWordlist()) {
            System.out.println(LocalDictionary.getDefinition(i)
                    + "\n----------------------------------------------------");
        }
    }

    /**
     * Start Commandline interface
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        DictionaryDatabase.loadLocalDictionary();
        dictionaryAdvanced();
    }
}