import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение:");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) {
        Map<String, Integer> romanNumerals = new HashMap<>();
        romanNumerals.put("I", 1);
        romanNumerals.put("II", 2);
        romanNumerals.put("III", 3);
        romanNumerals.put("IV", 4);
        romanNumerals.put("V", 5);
        romanNumerals.put("VI", 6);
        romanNumerals.put("VII", 7);
        romanNumerals.put("VIII", 8);
        romanNumerals.put("IX", 9);
        romanNumerals.put("X", 10);

        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        String leftStr = parts[0];
        String operator = parts[1];
        String rightStr = parts[2];

        boolean leftIsRoman = romanNumerals.containsKey(leftStr);
        boolean rightIsRoman = romanNumerals.containsKey(rightStr);

        if (leftIsRoman != rightIsRoman) {
            throw new IllegalArgumentException("Нельзя выполнять операции между римскими и арабскими числами");
        }

        int left, right;
        if (leftIsRoman) {
            left = romanNumerals.get(leftStr);
            right = romanNumerals.get(rightStr);
             if (left + right > 10) {
                throw new IllegalArgumentException("Сумма римских чисел не должна быть больше 10");
             }
        } else {
            left = Integer.parseInt(leftStr);
            right = Integer.parseInt(rightStr);
        }

        if ((left < 1 || left > 10) || (right < 1 || right > 10)) {
            throw new IllegalArgumentException("Числа должны быть от 1 до 10");
        }

        int result = switch (operator) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> left / right;
            default -> throw new IllegalArgumentException("Некорректная операция");
        };

        if (leftIsRoman) {
            if (result <= 0) {
                throw new IllegalArgumentException("Римские числа могут давать только положительный результат");
            }
            return toRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    public static String toRoman(int number) {
        String[] romanNumerals = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        return romanNumerals[number];
    }
}
