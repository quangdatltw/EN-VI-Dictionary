package dictionary.db;

public interface Dictionary {
    void addWord(String word, String definition);

    void updateWord(String word, String definition);

    void removeWord(String word);
}
