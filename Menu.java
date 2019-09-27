import java.util.Scanner;

public class Menu {

    private int menuOption = 0; // corresponds with menu options
    private boolean isNumber;

    public static void inputMenuOption() {

        Menu option = new Menu();
         // sanity check on input

        while (option.menuOption!= 3) {

            // Display Menu Options
            do {
                System.out.println("Please select one of the options below:");
                System.out.println("1: Create Model Files");
                System.out.println("2: Identify language from input file");
                System.out.println("3: Quit program");
                System.out.println();
                Scanner scanner = new Scanner(System.in);

                if (scanner.hasNextInt()) {
                    option.menuOption = scanner.nextInt();
                    option.isNumber = true;
                } else {
                    System.out.println("--Please enter a valid menu option--");
                    option.isNumber = false;
                    scanner.next();
                }
            } while (!(option.isNumber));


            if (option.menuOption == 1) {
                InOutFile.createModelFiles();
                System.out.println("Model files created!\n");
            } else if (option.menuOption == 2) {
                String input = Identification.getFileName();
                Identification.processUnknownFile(input); // process input file through method
            } else if (option.menuOption == 3){
                System.exit(0);
            } else {
                System.out.println();
                System.out.println("--Please enter a valid menu option--");
                System.out.println();
            }
        }
    }
}
