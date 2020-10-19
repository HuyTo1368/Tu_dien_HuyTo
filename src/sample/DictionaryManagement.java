package sample;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary{
    public ArrayList<Word> insertFromCommandline() throws IOException {
        Scanner scanner = new Scanner(Paths.get("D:\\Tudien JAVAFX\\tudien.txt"), "UTF-8");
        while (scanner.hasNextLine()) {
            String string = scanner.nextLine();
            String[] s = string.split("\\t");
            Word word = new Word(s[0], s[1], s[2], s[3]);
            super.words.add(word);
        }
        return words;
    }
}
