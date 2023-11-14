package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class InputHandle {
    private static Scanner scn = new Scanner(System.in);
    private static final String[] wordTypesE = {"* Danh từ:", "* Động từ:", "* Tính từ:", "* Trạng từ:", "* Giới từ:", "* Phó từ:", "! Thành ngữ:","* Nội động từ:", "* Ngoại động từ:"};

    public static String inputDefinition() {

        String def = "";
        String str;
        System.out.println("""
                        Rules: Remember ENTER if you don't have anything to write in.
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
                System.out.print("- ");
                str = scn.nextLine();
                if (str.equals("YES")) {
                    str = scn.nextLine();
                    def = def + w + " " + str;
                    System.out.print("Ý nghĩa thành ngữ: ");
                    str = scn.nextLine();
                    def = def + "    - " + str + "\n";
                } else {
                    continue;
                }
            }
            if (scn.nextLine().equals("YES")) {
                System.out.print("- ");
                def = def + w + "\n" + inputWordTypeMeaning();
            }
        }
        return def;
    }

    public static String inputWordTypeMeaning() {
        String wtm = "";
        String str = scn.nextLine();
        while (!str.isEmpty()) {
            System.out.print("VD: ");
            wtm = wtm + "    - " + str + "\n" + inputExample();
            System.out.print("- ");
            str = scn.nextLine();
        }
        return wtm;
    }

    public static String inputExample() {
        String ex = "";
        String str = scn.nextLine();
        while (!str.isEmpty()) {

            ex = ex + "        VD: " + str + "\n";
            System.out.print("VD: ");
            str = scn.nextLine();
        }
        return ex;
    }

    public static String inputFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");

                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1];

                    LocalDictionary.toWordList(key);
                    LocalDictionary.addWord(key, value);
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
