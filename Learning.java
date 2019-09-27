import java.util.*;
import java.text.*;

public class Learning {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////Extract words from element of languageFiles Array///////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<String> extractWords(String inputText, Locale currentLocale) {

        ArrayList<String> wordList = new ArrayList<>();
        BreakIterator wordIterator = BreakIterator.getWordInstance(currentLocale);
        wordIterator.setText(inputText);
        int start = wordIterator.first();
        int end = wordIterator.next();
        while (end != BreakIterator.DONE) {
            String word = inputText.substring(start,end);
            word = word.toLowerCase();
            if (Character.isLetter(word.charAt(0)) && word.length() > 1) {
                wordList.add(word);
            }
            start = end;
            end = wordIterator.next();
        }
        return wordList;
    }

}