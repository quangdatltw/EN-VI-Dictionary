package dictionary.db.cmd;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CmdRequestResolve {
    private static final String[] wordTypes  = {"* Danh từ:", "* Động từ:", "* Tính từ:", "* Trạng từ:", "* Giới từ:", "* Phó từ:", "! Thành ngữ:","* Nội động từ:", "* Ngoại động từ:"};
    private static final String rules =   """
                                    Rules: Remember ENTER if you have nothing to write in.
                                    Type 'YES' (Only for word type) if there are meaning for that word type.
                                    The structure will like this:
                                    [1] Word type appear
                                    [2] Type meaning
                                    [3] Type examples of the meaning in the form: English sentence = Vietnamese meaning
                                    Ex: fast
                                    * Tính từ:
                                    YES
                                    - nhanh, mau
                                    VD: a fast train = một đoàn tàu nhanh
                                    VD: No
                                    - bền, không phai
                                    VD: a fast color = màu bền
                                    VD: No
                                    - No
                                    
                                    -> Done
                                    """;
    public static Scanner scn = new Scanner(System.in);

    /**
     * Get string input from commandline.
     *
     * @return the string
     */
    public static String inputString() {
        System.out.println("[0] Exit");
        System.out.print("Input: ");
        try {
            return scn.nextLine().toLowerCase().trim();
        } catch (NullPointerException n) {
            n.printStackTrace();
            return "404";
        }
    }

    /**
     * Update word's definition.
     *
     * @param wordDef the word
     * @return the string
     */
    public static String getInputUpdate(String wordDef) {
        int para = 10;
        while (para != 0) {
            System.out.println("""
                    [0] Exit
                    What word type you want to add/update:
                    [1] * Danh từ
                    [2] * Động từ
                    [3] * Tính từ
                    [4] * Trạng từ
                    [5] * Giới từ
                    [6] * Phó từ
                    [7] ! Thành ngữ
                    [8] * Nội động từ
                    [9] * Ngoại động từ""");
            try {
                para = scn.nextInt();
                scn.nextLine();
            } catch (InputMismatchException i) {
                i.printStackTrace();
                para = 10;
            }
            if (para < 10 && para > 0) {
                String wordType = wordTypes[para - 1];
                if (para == 7) {
                    wordDef = getInputIdiom(wordDef);
                } else {
                    if (wordDef.contains(wordType)) {
                        String wordDef1 = wordDef.substring(0, wordDef.indexOf(wordType));
                        String wordDef2 = wordDef.substring(wordDef.indexOf(wordType));
                        String wordDef3 = wordDef2.substring(0, wordDef2.indexOf("\n"));
                        wordDef2 = wordDef2.substring(wordDef2.indexOf("\n") + 1);

                        System.out.print("Write meaning and example (Press ENTER if you have nothing to write in)\n" + "- ");
                        wordDef = wordDef1 + wordDef3 + getInputMeaning() + "\n" + wordDef2;

                    } else {
                        System.out.println(wordType);
                        System.out.print("- ");
                        wordDef = wordDef + "\n" + wordType + getInputMeaning();
                    }
                }
            }
        }
        return wordDef;
    }

    /**
     * Get word's input definition
     *
     * @return the string
     */
    public static String getInputDefinition() {
        String def = "";
        System.out.println(rules);
        for (String w : wordTypes) {
            System.out.println(w);
            if (scn.nextLine().equals("YES")) {
                if (w.equals(wordTypes[6])) {
                    def = getInputIdiom(def);
                } else {
                    System.out.print("- ");
                    def = def + "\n" + w + getInputMeaning();
                }
            }
        }
        return def;
    }

    /**
     * Get meaning of word's type
     *
     * @return the string
     */
    public static String getInputMeaning() {
        StringBuilder m = new StringBuilder();
        String str = scn.nextLine();
        while (!str.isEmpty()) {
            System.out.print("VD: ");
            String ex = getInputExample();
            m.append("\n").append("    - ").append(str).append(ex);
            System.out.print("- ");
            str = scn.nextLine();
        }
        return m.toString();
    }

    /**
     * Get example of word's meaning
     *
     * @return the string
     */
    private static String getInputExample() {
        StringBuilder ex = new StringBuilder();
        String str = scn.nextLine();
        while (!str.isEmpty()) {
            ex.append("\n").append("        VD: ").append(str);
            System.out.print("VD: ");
            str = scn.nextLine();
        }
        return ex.toString();
    }

    private static String getInputIdiom(String def) {
        System.out.print("Thành ngữ: ");
        String str = scn.nextLine();
        def = def + "\n" + wordTypes[6] + " " + str + "\n";
        System.out.print("Ý nghĩa thành ngữ: ");
        str = scn.nextLine();
        def = def + "    - " + str;
        return def;
    }
}
