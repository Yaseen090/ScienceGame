package assets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {
    private Set<String> wordSet;

    public Dictionary(String filePath) {
        loadDictionary(filePath);
    }

    private void loadDictionary(String filePath) {
        wordSet = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wordSet.add(line.trim().toUpperCase()); // Assuming words are in uppercase
            }
        } catch (IOException e) {
            System.err.println("Error loading dictionary: " + e.getMessage());
        }
    }

    public boolean isValidWord(String word) {
        if(word.contains(" "))
        {
            int index=word.indexOf(' ');
            for(int i=0;i<26;i++)
            {
                word.replace(' ', (char) (i+65));
                if(wordSet.contains(word.toUpperCase()))
                    return true;
            }
            return false;
        }
        else
            return wordSet.contains(word.toUpperCase());
    }
    public Set<String> getWSet()
    {
        return wordSet;
    }
}
