package main;

import java.util.HashMap;
public class LocalDictionary {

        private final HashMap<String, String> dictionary;

        public LocalDictionary() {
            dictionary = new HashMap<>();
        }
        /**
         * Thêm từ vào trong thư viện
         * @param word là từ cần thêm
         * @param definition là nghĩa của từ
         */
        public void addWord(String word, String definition) {
            dictionary.put(word.toLowerCase(), definition);
        }

        public String getDefinition(String word) {
            return dictionary.get(word.toLowerCase());
        }
    }
