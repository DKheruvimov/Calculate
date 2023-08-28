import java.util.Scanner;
import java.util.TreeMap;

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
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректное выражение. Введите выражение в формате: A +,-,*,/ B.");
        }

        String leftStr = parts[0];
        String operator = parts[1];
        String rightStr = parts[2];

        boolean isLeftRoman = isRomanNumber(leftStr);
        boolean isRightRoman = isRomanNumber(rightStr);

        int left, right;
        if (isLeftRoman) {
            left = fromRoman(leftStr);
        } else {
            left = Integer.parseInt(leftStr);
        }

        if (isRightRoman) {
            right = fromRoman(rightStr);
        } else {
            right = Integer.parseInt(rightStr);
        }

        if ((isLeftRoman && !isRightRoman) || (!isLeftRoman && isRightRoman)) {
            throw new IllegalArgumentException("Нельзя совершать операции между римскими и арабскими числами.");
        }

        if (left < 1 || left > 10 || right < 1 || right > 10) {
            throw new IllegalArgumentException("Вводимые числа должны быть от 1 до 10.");
        }

        int result = switch (operator) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> left / right;

            default -> throw new IllegalArgumentException("Некорректная операция. Допустимые операции: +, -, *, /.");
        };

        if (isLeftRoman || isRightRoman) {
            if (result <= 0) {
                throw new IllegalArgumentException("Римские числа могут давать только положительный результат.");
            }
            return toRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    public static boolean isRomanNumber(String input) {
        return input.matches("^[IVXLC]+$");
    }

    public static int fromRoman(String roman) {
        TreeMap<Character, Integer> map = new TreeMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);


        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = map.get(roman.charAt(i));
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }

        return result;
    }

    public static String toRoman(int number) {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

        String roman = "";
        int key;

        do {
            key = map.floorKey(number);
            roman += map.get(key);
            number -= key;
        } while (number > 0);

        return roman;
    }
}
