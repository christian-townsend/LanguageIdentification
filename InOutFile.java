import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;

public class InOutFile {

    private String languageFiles[] = {"Learning\\English.txt", "Learning\\French.txt", "Learning\\German.txt", "Learning\\Italian.txt", "Learning\\Spanish.txt"}; // directory of Learning files
    String outputFiles[] = {"Models\\EnglishModel.txt", "Models\\FrenchModel.txt", "Models\\GermanModel.txt", "Models\\ItalianModel.txt", "Models\\SpanishModel.txt"}; // directory of model files
    private Locale[] locales = {new Locale("en"), new Locale("fr"), new Locale("de"), new Locale("it"), new Locale("es")};

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////// Write model files to directory /////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void createModelFiles() {

        String learningDirectory = "Learning\\";
        String modelsDirectory = "Models\\";

        File learningDir = new File(learningDirectory);
        File modelsDir = new File(modelsDirectory);

        String[] learningFiles = learningDir.list();
        String[] modelFiles = modelsDir.list();

        Menu goBack = new Menu();

        InOutFile createModels = new InOutFile(); // create instance of class to access necessary variables

        if (learningFiles.length == 0) {
            System.out.println("The Learning directory is empty. Please add files to the directory and try again.\n"); // request user to// enter file name of unknown language
            goBack.inputMenuOption(); // returns to main menu when directory is empty
        } else {
            for (int i = 0; i < createModels.languageFiles.length; i++) {
                String inputText = InOutFile.readUTF8File(createModels.languageFiles[i]); // Read element i from languageFiles
                ArrayList<String> wordList = Learning.extractWords(inputText, createModels.locales[i]); // Create wordList ArrayList based on inputText
                ArrayList<String> bigramList = Ngram.convertWordsToNgrams(wordList, 2); // Convert wordList to bigramList ArrayList
                ArrayList<String> bigramProbability = Ngram.calculateProbabilities(bigramList);
                InOutFile.outputWordsToUTF8File(createModels.outputFiles[i], bigramProbability);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////// Reads all content from Learning and puts it into a single string //////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static String readUTF8File(String filePath) {
        StringBuilder fileContent = new StringBuilder();
        try {
            Reader reader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                fileContent.append(s + "\n");
            }
            bufferedReader.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////// readModelFile reads one line at a time and saves it as an item of array list - separates nGrams ///////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static ArrayList<String> readModelFile(String filePath) {
        ArrayList<String> fileContent = new ArrayList<String>();
        try {
            Reader reader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                fileContent.add(s);
            }
            bufferedReader.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////// Write words from array list to UTF8File ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void outputWordsToUTF8File(String filePath, ArrayList<String> wordList) {

        try {
            Writer writer =
                    new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8);
            PrintWriter printWriter = new PrintWriter(writer);

            for (String str : wordList) {
                writer.write(str);
                writer.write(System.getProperty("line.separator"));
            }

            writer.close();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}