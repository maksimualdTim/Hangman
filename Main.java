import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String userInput = scanner.nextLine();

            doOperation(userInput);
        }
    }

    public static void printMenu() {
        System.out.println("1) Начать игру");
        System.out.println("q) Выход");
        System.out.print("Выберете опцию: ");
    }

    public static void doOperation(String userInput) {

        switch (userInput) {
            case "1":
                startGame();
                break;
            case "q":
                System.out.println("Спасибо!");
                System.exit(0);
                break;
            default:
                System.out.println("Неверная команда! Повторите попытку");
                break;
        }
    }

    public static void startGame() {
        try {
            String word = makeWord();
            ArrayList<Character> usedLetters = new ArrayList<Character>();
            int wrongGuesses = 0;

            while (!isGameOver(wrongGuesses)) {
                printHangman(wrongGuesses);

                printUsedLetters(usedLetters);

                printEncryptedWord(word, usedLetters);
                System.out.print("\nВведите букву: ");
                String input = scanner.nextLine();
                char letter = input.charAt(0);

                if (!Character.isAlphabetic(letter)) {
                    System.out.println("Это не буква");
                    continue;
                }

                if (usedLetters.contains(letter)) {
                    System.out.println("Вы уже вводили эту букву");
                    continue;
                }

                usedLetters.add(letter);

                if(!isWordContainsLetter(word, letter)) {
                    wrongGuesses++;
                }

                if(isUserWin(word, usedLetters)) {
                    System.out.println("Поздравляю! это действительно " + word);
                }
            }

            System.out.println("К сожалению, вы проиграли. Загаданное слово: " + word);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String makeWord() throws Exception {
        File words = new File("./dictionary.txt");

        if (words.exists()) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(words, "r");

            Random random = new Random();
            long wordNumber = random.nextLong(randomAccessFile.length());

            randomAccessFile.seek(wordNumber);
            randomAccessFile.readLine();
            String word = randomAccessFile.readLine();
            randomAccessFile.close();

            return new String(word.getBytes("ISO-8859-1"), "UTF8");
        }
        throw new Exception("Dictionary file not found");
    }

    public static void printHangman(Integer numberOfIncorrectGuesses) {
        switch (numberOfIncorrectGuesses) {
            case 1:
                System.out.println("\n" + "\n|" + "\n|" + "\n|" + "\n|" + "\n|" + "\n|_______________________\n");
                break;
            case 2:
                System.out.println(
                        "\n_________" + "\n|" + "\n|" + "\n|" + "\n|" + "\n|" + "\n|_______________________\n");
                break;
            case 3:
                System.out.println("\n_________" + "\n|                   |" + "\n|" + "\n|" + "\n|" + "\n|"
                        + "\n|_______________________\n");
                break;
            case 4:
                System.out.println("\n_________" + "\n|                   |" + "\n|                  O" + "\n|" + "\n|"
                        + "\n|" + "\n|_______________________\n");
                break;
            case 5:
                System.out.println("\n_________" + "\n|                   |" + "\n|                  O"
                        + "\n|                   |" + "\n|" + "\n|" + "\n|_______________________\n");
                break;
            case 6:
                System.out.println("\n_________" + "\n|                   |" + "\n|                  O"
                        + "\n|               ---|" + "\n|" + "\n|" + "\n|_______________________\n");
                break;
            case 7:
                System.out.println("\n_________" + "\n|                   |" + "\n|                  O"
                        + "\n|               ---|---" + "\n|" + "\n|" + "\n|_______________________\n");
                break;
            case 8:
                System.out.println("\n_________" + "\n|                   |" + "\n|                  O"
                        + "\n|               ---|---" + "\n|                  /" + "\n|                /"
                        + "\n|_______________________\n");
                break;
            case 9:
                System.out.println("\n_________" + "\n|                   |" + "\n|                  O"
                        + "\n|               ---|---" + "\n|                  /\\" + "\n|                /    \\"
                        + "\n|_______________________");
        }
    }

    public static boolean isGameOver(int guesses) {
        return guesses == 9;
    }

    public static void printEncryptedWord(String word, ArrayList<Character> usedLetters) {
        String result = "";
        for (Character letter : word.toCharArray()) {
            if(usedLetters.contains(letter)) {
                result += Character.toString(letter);
            } else {
                result += " _ ";
            }
        }
        System.out.println(result);
    }

    public static boolean isUserWin(String word, ArrayList<Character> usedLetters) {
        for (Character letter : word.toCharArray()) {
            if(!usedLetters.contains(letter)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWordContainsLetter(String word, Character letter) {
        for (Character wordLetter : word.toCharArray()) {
            if(letter.equals(wordLetter)) {
                return true;
            }
        }
        return false;
    }

    public static void printUsedLetters(ArrayList<Character> usedLetters) {
        if(usedLetters.size() != 0) {
            System.out.println("\nИспользованные буквы: ");
            String usedString = "";

            for (Character usedCharacter : usedLetters) {
                usedString += Character.toString(usedCharacter) + ", ";
            }
            usedString = usedString.trim();
            usedString = usedString.substring(0, usedString.length()-1);

            System.out.println(usedString);
        }
    }
}