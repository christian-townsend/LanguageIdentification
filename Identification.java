import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.io.File;

public class Identification {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////// Get input from user for unknown files /////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    String languages[] = {"English", "French", "German", "Italian", "Spanish"};

    public static String getFileName() {

        String languages[] = {"English", "French", "German", "Italian", "Spanish"};
        String testDirectory = "Testing\\";
        String modelsDirectory = "Models\\";

        Menu goBack = new Menu();
        File testDir = new File(testDirectory); // create new instance of File object
        File modelsDir = new File(modelsDirectory);


        String[] modelsFiles = modelsDir.list();
        String[] unknownFiles = testDir.list();
        if (modelsFiles.length == 0) {
            System.out.println("The Models directory is empty. Please create models and try again.\n"); // request user to// enter file name of unknown language
            goBack.inputMenuOption(); // returns to main menu when directory is empty
        }

        ArrayList<String> directoryFiles = new ArrayList<String>();
        String input;

        do {

            if (unknownFiles.length == 0) {
                System.out.println("The Unknown directory is empty. Please add files to the directory and try again.\n"); // request user to// enter file name of unknown language
                goBack.inputMenuOption(); // returns to main menu when directory is empty
            } else {
                System.out.println();
                System.out.println("Please enter the file-name of one of the files listed below.");
                for (String aFile : unknownFiles) {
                    directoryFiles.add(aFile); // adds found file paths to directoryFiles ArrayList
                    System.out.println(aFile);
                }
            }

            Scanner scanner = new Scanner(System.in); // declare scanner object for user input
            input = scanner.next(); // get file name


            if (!directoryFiles.contains(input)) {
                System.out.println("The file does not exist, please try again.");
                scanner.nextLine();
                directoryFiles.clear();
            }

        } while (!directoryFiles.contains(input));

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Testing\\"); // allows to shorten the input process
            stringBuilder.append(input); // appends unknown file name to stringBuilder
            input = stringBuilder.toString(); // creates full string specifying file directory

            return input;

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////// Get input from user for unknown files /////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void processUnknownFile(String input) {

        InOutFile processFile = new InOutFile();
        Identification identify = new Identification();
        // Unknown file processes through read, extract and convert to nGrams
        String readUnknownText = InOutFile.readUTF8File(input);
        ArrayList<String> unknownList = Learning.extractWords(readUnknownText, Locale.ROOT);
        ArrayList<String> unknownBigramList = Ngram.convertWordsToNgrams(unknownList, 2);

        double[] score; // score of each language
        double maxScore = 0; // the maxScore is returned after all iterations of the for loop below
        score = new double[5]; // allows the program to store all scores

        String identifiedLanguage = ""; // the identified language - makes use of if statement below

        for (int j = 0; j < processFile.outputFiles.length; j++) {

            ArrayList<String> modelLanguage = InOutFile.readModelFile(processFile.outputFiles[j]);
            score[j] = Identification.calculateScore(modelLanguage, unknownBigramList);

                    // updates the maxScore if the next element in the loop is greater in size
            if (score[j] > maxScore) {
                maxScore = score[j];
                identifiedLanguage = identify.languages[j]; // with each iteration, this will update depending on the maxScore
            } else {
                maxScore = maxScore;
            }
        }

        System.out.println();
        // Prints name of the identifiedLanguage & the score associated with it
        System.out.println("Identified Language: " + identifiedLanguage);
        // Print score if necessary System.out.println("Score: " + maxScore);
        System.out.println();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////// Calculates and returns totalScore for each Language passed through the method /////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static double calculateScore(ArrayList<String> modelBigram, ArrayList<String> unknownBigram) {

        double totalScore = 1; // the product of all probabilities
        double currentScore; // the probability of the current target

        for (String target : unknownBigram) {
            // target is a bigram in unknownBigram. It's of the form "xy"
            // element below is a line in model file that contains
            // bigram and probability. It's of the form "xy 0.abcdef"

            String element = binarySearch(modelBigram, 0, modelBigram.size(), target);
            element = element.split(" ")[1];
            currentScore = Double.parseDouble(element);
            totalScore = totalScore *= currentScore;
        }
        return totalScore; // returns total of all multiplied currentScores
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////// Search for entries ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String binarySearch(ArrayList<String> arr, int first, int last, String target) {
        int mid; // index of the midpoint
        String midvalue; // object that is assigned arr[mid]
        int origLast = last; // save original value of last
        while (first < last) { // test for nonempty sublist
            mid = (first + last) / 2;
            midvalue = arr.get(mid);
            if (midvalue.contains(target)) {
                return midvalue; // have a match
            } else if (target.compareTo(midvalue) < 0) {
                last = mid; // search lower sublist. Reset last
            } else {
                first = mid + 1; // search upper sublist. Reset first
            }
        }
        return "0 0"; // target not found
    }

}
