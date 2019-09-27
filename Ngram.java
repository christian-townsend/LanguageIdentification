import java.util.ArrayList;
import java.util.Collections;

public class Ngram {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////// Convert Words from array list to bigrams //////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static ArrayList<String> convertWordsToNgrams(ArrayList<String> wordList, int nGramSize) {
        ArrayList<String> bigramList = new ArrayList<String>();
        for (String word : wordList) {
            int beginIndex = 0;
            while (beginIndex < word.length() - 1) {
                String bigram = word.substring(beginIndex, beginIndex + nGramSize);
                bigramList.add(bigram);
                beginIndex++;
            }
        }
        Collections.sort(bigramList);
        return bigramList;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////// Calculate probabilities ///////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<String> calculateProbabilities(ArrayList<String> nGram) {
        double probability;
        int counter = 0;
        double totalNgrams = 0;
        double score;

        String bigramProbability;
        ArrayList<String> myList = new ArrayList<String>();
        String curr = nGram.get(0);

        for (String next : nGram) {
            totalNgrams++;
        }

        for (String next : nGram) {
            if (next.equals(curr)) {
                counter++;
            } else {
                probability = counter / totalNgrams;
                myList.add(bigramProbability = curr + ' ' + probability);

                counter = 1;
                curr = next;
            }
        }
        return myList;
    }

}
