package dictionary.db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class InputHandle {
    @SuppressWarnings("FieldMayBeFinal")
    private static Scanner scn = new Scanner(System.in);
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

    public static void inputUpdateDefinition(String word) {
        int para = 10;
        String wordDef = LocalDictionary.getDefinition(word);
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
                    wordDef = inputIdiom(wordDef);
                } else {
                    if (wordDef.contains(wordType)) {
                        String wordDef1 = wordDef.substring(0, wordDef.indexOf(wordType));
                        String wordDef2 = wordDef.substring(wordDef.indexOf(wordType));
                        String wordDef3 = wordDef2.substring(0, wordDef2.indexOf("\n"));
                        wordDef2 = wordDef2.substring(wordDef2.indexOf("\n") + 1);

                        System.out.print("Write meaning and example (Press ENTER if you have nothing to write in)\n" + "- ");
                        wordDef = wordDef1 + wordDef3 + inputMeaning() + "\n" + wordDef2;

                    } else {
                        System.out.println(wordType);
                        System.out.print("- ");
                        wordDef = wordDef + "\n" + wordType + inputMeaning();
                    }
                }
            }
        }
        LocalDictionary.updateWord(word, wordDef);
    }

    public static String inputDefinition() {
        String def = "";
        System.out.println(rules);
        for (String w : wordTypes) {
            System.out.println(w);
            if (scn.nextLine().equals("YES")) {
                if (w.equals(wordTypes[6])) {
                    def = inputIdiom(def);
                } else {
                    System.out.print("- ");
                    def = def + "\n" + w + inputMeaning();
                }
            }
        }
        return def;
    }

    public static String inputMeaning() {
        StringBuilder m = new StringBuilder();
        String str = scn.nextLine();
        while (!str.isEmpty()) {
            System.out.print("VD: ");
            String ex = inputExample();
            m.append("\n").append("    - ").append(str).append(ex);
            System.out.print("- ");
            str = scn.nextLine();
        }
        return m.toString();
    }

    private static String inputExample() {
        StringBuilder ex = new StringBuilder();
        String str = scn.nextLine();
        while (!str.isEmpty()) {
            ex.append("\n").append("        VD: ").append(str);
            System.out.print("VD: ");
            str = scn.nextLine();
        }
        return ex.toString();
    }

    private static String inputIdiom(String def) {
        System.out.print("Thành ngữ: ");
        String str = scn.nextLine();
        def = def + "\n" + wordTypes[6] + " " + str + "\n";
        System.out.print("Ý nghĩa thành ngữ: ");
        str = scn.nextLine();
        def = def + "    - " + str;
        return def;
    }

    public static List<String> inputSearch(String prefix) {
        List<String> wordlist = LocalDictionary.getWordlist();
        List<String> result = new ArrayList<>();

        int begin = 0;
        int end = wordlist.size() - 1;

        try {
            begin = LocalDictionary.getIndex().get((int) prefix.charAt(0) - 97);
            if ((int) prefix.charAt(0) - 97 < 25) {
                end = LocalDictionary.getIndex().get((int) prefix.charAt(0) - 96) - 1;
            }
        } catch (IndexOutOfBoundsException ignored) {
            end = LocalDictionary.getIndex().get(0) - 1;
        }
        wordlist = wordlist.subList(begin, end);

        for (String word : wordlist) {
            if (word.matches(prefix + "(.*)")) {
                result.add(word);
            }
        }
        return result;
    }

    public static boolean inputFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");

                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1].replace("\\n", "\n");
                    LocalDictionary.addWord(key, value);
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
