import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static String processText(String text, char startChar, char endChar) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Текст не може бути порожнім або складатися лише з пробілів.");
        }
        if (!Character.isLetter(startChar) || !Character.isLetter(endChar)) {
            throw new IllegalArgumentException("Початкова та кінцева літери мають бути буквами.");
        }

        StringBuilder result = new StringBuilder();
        String[] sentences = text.split("(?<=\\.)|(?<=!)|(?<=\\?)");

        for (String sentence : sentences) {
            String regex = String.format("%s.*?%s",
                    Pattern.quote(String.valueOf(startChar)),
                    Pattern.quote(String.valueOf(endChar)));
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(sentence);

            String longestSubstring = null;
            while (matcher.find()) {
                String currentSubstring = matcher.group();
                if (longestSubstring == null || currentSubstring.length() > longestSubstring.length()) {
                    longestSubstring = currentSubstring;
                }
            }

            if (longestSubstring != null) {
                sentence = sentence.replace(longestSubstring, "").trim();
            }
            result.append(sentence).append(" ");
        }

        return result.toString().trim();
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String inputText;
            do {
                System.out.println("Введіть текст (не може бути порожнім):");
                inputText = scanner.nextLine();
                if (inputText.trim().isEmpty()) {
                    System.out.println("Помилка: текст не може бути порожнім або складатися лише з пробілів.");
                }
            } while (inputText.trim().isEmpty());

            char startChar = '\0';
            while (true) {
                System.out.println("Введіть початкову літеру для підрядків (один символ):");
                String startCharInput = scanner.nextLine();
                if (startCharInput.length() == 1 && Character.isLetter(startCharInput.charAt(0))) {
                    startChar = startCharInput.charAt(0);
                    break;
                } else {
                    System.out.println("Помилка: потрібно ввести одну літеру.");
                }
            }

            char endChar = '\0';
            while (true) {
                System.out.println("Введіть кінцеву літеру для підрядків (один символ):");
                String endCharInput = scanner.nextLine();
                if (endCharInput.length() == 1 && Character.isLetter(endCharInput.charAt(0))) {
                    endChar = endCharInput.charAt(0);
                    break;
                } else {
                    System.out.println("Помилка: потрібно ввести одну літеру.");
                }
            }

            String result = processText(inputText, startChar, endChar);

            System.out.println("Оброблений текст:");
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.err.println("Помилка: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Непередбачена помилка: " + e.getMessage());
        }
    }
}
