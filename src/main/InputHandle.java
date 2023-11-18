package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandle {
    @SuppressWarnings("FieldMayBeFinal")
    private static Scanner scn = new Scanner(System.in);
    private static final String[] wordTypesE = {"* Danh từ:", "* Động từ:", "* Tính từ:", "* Trạng từ:", "* Giới từ:", "* Phó từ:", "! Thành ngữ:","* Nội động từ:", "* Ngoại động từ:"};

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
        String str;
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
                String wordType = wordTypesE[para - 1];
                String wordDef = LocalDictionary.getDefinition(word);
                if (para == 7) {
                    System.out.print("Thành ngữ: ");
                    str = scn.nextLine();
                    wordDef = wordDef + "\n" + wordType + " " + str + "\n";
                    System.out.print("Ý nghĩa thành ngữ: ");
                    str = scn.nextLine();
                    wordDef = wordDef + "    - " + str;
                    LocalDictionary.putWord(word, wordDef);
                } else {
                    if (wordDef.contains(wordType)) {
                        String wordDef1 = wordDef.substring(0, wordDef.indexOf(wordType));
                        String wordDef2 = wordDef.substring(wordDef.indexOf(wordType));
                        String wordDef3 = wordDef2.substring(0, wordDef2.indexOf("\n"));
                        wordDef2 = wordDef2.substring(wordDef2.indexOf("\n") + 1);

                        System.out.print("Write meaning and example (Press ENTER if you have nothing to write in)\n" + "- ");
                        try {
                            wordDef = wordDef1 + wordDef3 + inputWordTypeMeaning() + "\n" + wordDef2;
                            LocalDictionary.putWord(word, wordDef);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Something is wrong");
                        }
                    } else {
                        System.out.println(wordType);
                        System.out.print("- ");
                        wordDef = wordDef + "\n" + wordType + inputWordTypeMeaning();
                        LocalDictionary.updateWord(word, wordDef);
                    }
                }
            }
        }
    }

    public static String inputDefinition() {

        StringBuilder def = new StringBuilder();
        String str;
        System.out.println("""
                        
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
                        """);
        for (String w : wordTypesE) {
            System.out.println(w);
            if (w.equals(wordTypesE[6])) {
                str = scn.nextLine();
                if (str.equals("YES")) {
                    System.out.print("- ");
                    str = scn.nextLine();
                    def.append("\n").append(w).append(" ").append(str).append("\n");
                    System.out.print("Ý nghĩa thành ngữ: ");
                    str = scn.nextLine();
                    def.append("    - ").append(str);
                }
                continue;
            }
            if (scn.nextLine().equals("YES")) {
                System.out.print("- ");
                def.append("\n").append(w).append(inputWordTypeMeaning());
            }
        }
        return def.toString();
    }

    public static String inputWordTypeMeaning() {
        StringBuilder wtm = new StringBuilder();
        String str = scn.nextLine();
        while (!str.isEmpty()) {
            System.out.print("VD: ");
            String ex = inputExample();
            wtm.append("\n").append("    - ").append(str).append(ex);
            System.out.print("- ");
            str = scn.nextLine();
        }
        return wtm.toString();
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

    public static String inputFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");

                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1].replace("\\n", "\n");
                    LocalDictionary.putWord(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Please make sure file path is correct");
            System.out.println("[0] Exit");
            return null;
        }
        return "Load from file succeed";
    }

}
