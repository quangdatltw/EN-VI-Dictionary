package dictionary.cmd;

import dictionary.GeneralRequestResolve;
import dictionary.db.LocalDictionary;
import dictionary.db.LocalDictionaryRequestHandle;

import java.util.List;

public class CmdRequestDelegate {
    private static LocalDictionaryRequestHandle librarian = new LocalDictionaryRequestHandle();

    /**
     * Show all words.
     */
    public static void showAllWords() {
        for (String i : LocalDictionary.getWordlist()) {
            System.out.println(librarian.getDefinition(i)
                    + "\n----------------------------------------------------");
        }
    }

    /**
     * Get and check word wanted to ADD -> get input word's definition from InputHandle
     */
    public static void insertFromCommandline() {
        String word = CmdRequestResolve.inputString();
        int check = checkWordAvailability(word, 2);
        if (check == 1) {
            System.out.println("Word is empty");
        } else if (check == -1) {
            System.out.print("Definition: ");
            String def = word + CmdRequestResolve.getInputDefinition();
            librarian.addWord(word, def);
        }
        System.out.println("-------------------------------------------------");

    }

    /**
     * Get and check word wanted to UPDATE -> word is UPDATED in InputHandle
     */
    public static void update() {
        String word;
        while (true) {
            word = CmdRequestResolve.inputString();
            int check = checkWordAvailability(word, 1);
            if (check == 0){
                break;
            } else if (check == 1) {
                continue;
            } else {
                String wordDef = librarian.getDefinition(word);
                System.out.println("This is word definition: ");
                System.out.println(wordDef);
                wordDef = CmdRequestResolve.getInputUpdate(wordDef);
                librarian.updateWord(word, wordDef);
            }
            System.out.println("-------------------------------------------------");
        }
    }

    /**
     * Remove word from Database.
     */
    public static void remove() {
        String word;
        while (true) {
            word = CmdRequestResolve.inputString();
            if (word.equals("0")) {
                break;
            }
            if (!librarian.checkWordExistence(word)) {
                System.out.println("word not found! \n");
            } else {
                librarian.removeWord(word);
                System.out.println("Word removed! \n");
            }
        }
    }

    /**
     * Get and check word -> Print word's definition
     */
    public static void lookup() {
        String word;
        while (true) {
            word = CmdRequestResolve.inputString();
            int check = checkWordAvailability(word, 1);
            if (check == 0){
                break;
            } else if (check == 1) {
                continue;
            } else {
                System.out.print("Definition: ");
                System.out.println(librarian.getDefinition(word));
            }
            System.out.println("-------------------------------------------------");
        }
    }

    /**
     * Get and check Prefix -> Get word list from InputHandle
     */
    public static void search() {
        String prefix;
        while (true) {
            prefix = CmdRequestResolve.inputString();
            if (prefix.equals("0")) {
                break;
            }
            List<String> wordlist = GeneralRequestResolve.getSearchedList(prefix);
            if (wordlist.isEmpty()) {
                System.out.println("There are no words start with:" + prefix);
            } else {
                for (String i : wordlist) {
                    System.out.println(i);
                }
            }
            System.out.println("----------------------------------------------------");
        }
    }

    /**
     * Check if word is available for using further
     */
    private static int checkWordAvailability(String word, int mode) {
        if (word.equals("0")) {
            return 0;
        }
        if (word.isEmpty()) {
            System.out.println("Input is empty");
            return 1;
        }

        if (!librarian.checkWordExistence(word) && mode == 1) {
            System.out.println("word not found! \n" + "Do you want to add this word? [Y/N]: ");
            if (CmdRequestResolve.scn.nextLine().equalsIgnoreCase("y")) {
                System.out.println("Word: " + word);
                System.out.println("Definition: ");
                String definition = word + CmdRequestResolve.getInputDefinition();
                librarian.addWord(word, definition);
            }
            return 1;
        }

        if (librarian.checkWordExistence(word) && mode == 2) {
            System.out.print("Word already exist! \n Do you want to update this word? [Y/N]: ");
            if (CmdRequestResolve.scn.nextLine().equalsIgnoreCase("y")) {
                CmdRequestResolve.getInputUpdate(word);
                String newDef = CmdRequestResolve.getInputUpdate(word);
                librarian.updateWord(word, newDef);
            }
            return 1;
        }

        return -1;
    }

    /**
     * Get file path from cmd -> insert file handled in InputHandle
     */
    public static void insertDictionaryFromFile() {
        String filePath;
        while (true) {
            System.out.println("File path:");
            filePath = CmdRequestResolve.scn.nextLine();
            if (filePath.equals("0")) {
                break;
            }
            if (GeneralRequestResolve.importDFile(filePath)) {
                System.out.println("Please make sure file path is correct");
                System.out.println("[0] Exit");
            } else {
                System.out.println("File imported");
            }
        }
    }

    /**
     * Export database to file.
     */
    public static void exportDictionaryToFile() {
        if (GeneralRequestResolve.exportDFile()) {
            System.out.println("Dictionary exported: src/main/resources/external_dictionary/Dictionary_EX.txt");
        } else {
            System.out.println("Error!");
        }
    }

}
