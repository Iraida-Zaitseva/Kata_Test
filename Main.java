import java.util.Scanner;

class Main {

    // Метод для преобразования римского числа в арабское
    private static int romanToArabic(String roman) {
        int result = 0;
        String romanNumeral = roman.toUpperCase();
        String[] romanSymbols = {"I", "IV", "V", "IX", "X"};
        int[] arabicValues = {1, 4, 5, 9, 10};

        int i = 0;
        while (i < romanNumeral.length()) {
            boolean found = false;
            for (int j = 0; j < romanSymbols.length; j++) {
                if (romanNumeral.startsWith(romanSymbols[j], i)) {
                    result += arabicValues[j];
                    i += romanSymbols[j].length();
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("Ошибка: Неверное римское число.");
            }
        }
        return result;
    }

    // Метод для преобразования арабского числа в римское
    private static String arabicToRoman(int number) {
        if (number < 1 || number > 10) {
            throw new IllegalArgumentException("Ошибка: Римские числа должны быть от 1 до 10.");
        }

        String[] romanSymbols = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        return romanSymbols[number - 1];
    }

    // Метод для вычисления результата
    public static String calc(String input) {
        String[] tokens = input.split(" ");

        if (tokens.length != 3) {
            throw new IllegalArgumentException("Ошибка: Неверный формат выражения. Ожидается: <число> <операция> <число>");
        }

        // Проверяем, что оба числа арабские или оба римские
        boolean isArabic = isArabicNumber(tokens[0]) && isArabicNumber(tokens[2]);
        boolean isRoman = isRomanNumber(tokens[0]) && isRomanNumber(tokens[2]);

        if (!(isArabic || isRoman)) {
            throw new IllegalArgumentException("Ошибка: Все числа должны быть в одной системе счисления (либо арабские, либо римские).");
        }

        int num1, num2;
        String operator = tokens[1];

        if (isArabic) {
            num1 = Integer.parseInt(tokens[0]);
            num2 = Integer.parseInt(tokens[2]);
        } else {
            num1 = romanToArabic(tokens[0]);
            num2 = romanToArabic(tokens[2]);
        }

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new IllegalArgumentException("Ошибка: Числа должны быть от 1 до 10.");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Ошибка: Деление на ноль.");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Ошибка: Неверная операция. Доступные операции: +, -, *, /.");
        }

        if (isRoman && result < 1) {
            throw new IllegalArgumentException("Ошибка: Римские числа не могут быть меньше 1.");
        }

        if (isArabic) {
            return String.valueOf(result);
        } else {
            return arabicToRoman(result);
        }
    }

    // Проверка, является ли строка арабским числом
    private static boolean isArabicNumber(String str) {
        try {
            int num = Integer.parseInt(str);
            return num >= 1 && num <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Проверка, является ли строка римским числом
    private static boolean isRomanNumber(String str) {
        String[] validRomans = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (String roman : validRomans) {
            if (roman.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите арифметическое выражение (например, 5 + 3 или V - III):");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
