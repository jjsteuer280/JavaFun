//Authors:Julianna, Parker
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
//Import Chain

public class PredictiveTextGame {
    private final ArrayList<String> messageText;

    public PredictiveTextGame() {
        messageText = new ArrayList<>();
    }
//Declare ArrayList

    public void loadMessageTemplate(String filename) {
        //This method reads a file and then adds it into the array
        File theFile = new File(filename + ".dat");
        Scanner readFile = null;
        try {
            readFile = new Scanner(theFile);
            while (readFile.hasNext()) {
                messageText.add(readFile.next() + " ");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        } finally {
            if (readFile != null) {
                readFile.close();
            }
        }
    }

    public void fillBlanksWithInput(String fileName) {
        //Takes user input and finds brackets takes the words inside and removes brackets. Asks user for a word
        Scanner userInput = new Scanner(System.in);

        int i;
        for (i = 0; i < messageText.size(); i++) {
            String brakets = messageText.get(i);
            if (brakets.contains("[") && brakets.contains("]")) {
                String last = "";
                String words = messageText.get(i).replaceAll("[\\[\\]]", "");
                if(words.endsWith(",") || words.endsWith("!")|| words.endsWith(".")|| words.endsWith("?")) {
                    last = words.substring(words.length() - 1);
                    words = words.replace(last, "");
                }
                System.out.println("Enter a/an " + words);
                String message = userInput.nextLine();
                messageText.set(i, message + " " + last);
            }
        }
    }

    public void shareFinalResult(String fileName) {
        //Creates a counter that takes the word length until it reaches 40.
        //Then it stops printing goes to the next line and continues printing.
        int fullCount = 0;
        for (int i = 0; i < messageText.size(); i++) {
            String word = messageText.get(i);
            int count = word.length();
            int maxCount = 40;
            fullCount = fullCount + count;
            if (fullCount < maxCount ) {
                System.out.print(messageText.get(i) + "");
            }
            while (fullCount > maxCount) {
                System.out.println(messageText.get(i) + "");
                fullCount = 0;
            }
        }
        PrintWriter newFile = null;
        try {
            newFile = new PrintWriter(fileName + ".out");
            fullCount = 0;
            for (int i = 0; i < messageText.size(); i++) {
                String word = messageText.get(i);
                int count = word.length();
                int maxCount = 40;

                fullCount = fullCount + count;
                if (fullCount < maxCount ) {
                   newFile.print(messageText.get(i) + " ");
                }
                while (fullCount > maxCount) {
                    newFile.println(messageText.get(i) + " ");
                    fullCount = 0;
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (newFile != null) {
                newFile.close();
            }
        }

    }

    public void playPredictiveTextGame (String fileName) {
        //calls all other methods with fileName
        loadMessageTemplate(fileName);
        fillBlanksWithInput(fileName);
        shareFinalResult(fileName);
    }

    public static void main(String[] args) {
        //Main method which asks the user for a filename and gives that filename to playPredictiveTextGame
        //So the method can call all the other methods with this information
        Scanner userInput = new Scanner(System.in);
        System.out.println("Give a file name without the suffix: ");
        String fileName = userInput.next();
        PredictiveTextGame game = new PredictiveTextGame();
        game.playPredictiveTextGame(fileName);
    }
}

