package dictionary.db.cmd;


import dictionary.db.DatabaseRequestHandle;
import dictionary.db.TextToSpeechAPI;
import dictionary.db.TranslateAPI;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CmdInterface {

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
                    CmdRequestDelegate.insertFromCommandline();
                    break;
                case 2:
                    CmdRequestDelegate.remove();
                    break;
                case 3:
                    CmdRequestDelegate.update();
                    break;
                case 4:
                    CmdRequestDelegate.showAllWords();
                    break;
                case 5:
                    CmdRequestDelegate.lookup();
                    break;
                case 6:
                    CmdRequestDelegate.search();
                    break;
                case 7:
                    break;
                case 8:
                    CmdRequestDelegate.insertDictionaryFromFile();
                    break;
                case 9:
                    CmdRequestDelegate.exportDictionaryToFile();
                    break;
                case 10:
                    TranslateAPI.cmdTranslate();
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
     * Start Commandline interface
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        DatabaseRequestHandle.loadLocalDictionary();
        dictionaryAdvanced();
    }
}