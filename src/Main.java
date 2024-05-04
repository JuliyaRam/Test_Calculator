import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение через пробел: ");
        System.out.println(calc(scanner.nextLine()));
    }

    public static String calc(String entry) throws IOException {
        // Разбиваем строку на отдельные элементы, где пробел - разделитель
        String[] separationEntryArray = entry.split(" ");

        if (separationEntryArray.length < 2) {
            throw new IOException("т.к. строка не является математической операцией");
        } else if (separationEntryArray.length > 3) {
            throw new IOException("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        //Создаем строковое значение из элемента массива для удобства использования
        String oneNum = separationEntryArray[0];
        String operator = separationEntryArray[1];
        String twoNum = separationEntryArray[2];

        boolean isArab = isArab(oneNum) && isArab(twoNum); // Создаем логическую переменную isArab, у которой все операнды арабские числа
        int result;

        // Проверка на использование разных систем счисления
        if (!isArab(oneNum) && isArab(twoNum)) {
            throw new IOException("т.к. используются одновременно разные системы счисления");
        }
        if (isArab(oneNum) && !isArab(twoNum)) {
            throw new IOException("т.к. используются одновременно разные системы счисления");
        }
        // Выполнение операции для арабских чисел
        if (isArab(oneNum) && isArab(twoNum)) {     // Если операнды являются арабскими числами, конвертируем числа из строки в число
            int a, b;
            a = Integer.parseInt(oneNum);           // Преобразование строки в целое число
            b = Integer.parseInt(twoNum);           // Преобразование строки в целое число

            // Проверка, что операнды не превышают 10 и не меньше 1
            if (a < 1 || a > 10 || b < 1 || b > 10) {
                throw new IOException("т.к. операнды должны быть числами от 1 до 10");
            }
            result = calculate(a, operator, b);     // Создаем целочисленную переменную result, для размещения результата расчета

        /* Если все операнды - римские числа, конвертируем в арабские при помощи функции convToArab
        проверяем принадлежность их к промежутку от 1 до 10. Если true выполняем вычисление (calculate)
        Результат вычисления помещаем в целочисленную переменную result*/
        } else {
            // Конвертируем числа в арабские
            int a, b;
            a = convToArab(oneNum);
            b = convToArab(twoNum);
            result = calculate(a, operator, b);
        }

        // Преобразование результата в строку в зависимости от системы счисления
        String res;
        if (isArab) {                            // Если операция с арабскими числами
            res = String.valueOf(result);        // то в res записываем результат операции с арабскими числами
        } else {                                 // Если операция с римскими числами
            // Проверка на отрицательный или нулевой результат в римской системе
            if (result <= 0) {                   // Если результат вычисления римских чисел <= 0
                throw new IOException("т.к. в римской системе нет отрицательных чисел и нуля");
            } else {
                res = convToRim(result);         // в res записываем результат операции с римскими числами
            }
        }
        return (res);
    }

    // Функция проверяет, является ли строковый операнд арабским числом
    private static boolean isArab(String num) {
        try {
            Integer.parseInt(num);              // Блок try - пытаемся преобразовать строковый операнд в целое число
            return true;                        // Если преобразование успешно, метод возвращает true.
        } catch (
                NumberFormatException e) {      // Блок catch - обрабатываем исключение NumberFormatException, возникающее при ошибке преобразования.
            return false;                       // В случае ошибки, метод возвращает false
        }
    }

    // Функция проверяет, является ли строковый операнд римским числом
    private static boolean isRim(String num) {
        // Для этого создается массив rimNumArray, содержащий все возможные значения римских чисел.
        String[] rimNumArray = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XX", "XI", "XII", "L", "C"};
        for (String rimNum : rimNumArray) {         // перебор значений в массиве с помощью цикла for
            if (rimNum.equals(num)) {               // проверка каждого значения на совпадение с num с помощью условия if.
                return true;                        // true, если num является римским числом
            }
        }
        return false;
    }


    // Функция для выполнения операций с числами.
    private static int calculate(int oneNum, String operator, int twoNum) throws IOException {
        switch (operator) {
            case "+":
                return oneNum + twoNum;
            case "-":
                return oneNum - twoNum;
            case "*":
                return oneNum * twoNum;
            case "/":
                return oneNum / twoNum;
            default:
                throw new IOException("т.к. строка не является математической операцией");
        }
    }

    // Функция для конвертирования римских чисел в арабские
    private static int convToArab(String Num) throws IOException {
        if (Num.equals("I")) {
            return 1;
        } else
        if (Num.equals("II")) {
            return 2;
        } else
        if (Num.equals("III")) {
            return 3;
        } else
        if (Num.equals("IV")) {
            return 4;
        } else
        if (Num.equals("V")) {
            return 5;
        } else
        if (Num.equals("VI")) {
            return 6;
        } else
        if (Num.equals("VII")) {
            return 7;
        } else
        if (Num.equals("VIII")) {
            return 8;
        } else
        if (Num.equals("IX")) {
            return 9;
        } else
        if (Num.equals("X")) {
            return 10;
        } else {
            throw new IOException("т.к.римское число не должно превышать 10");
        }
    }

    // Функция для преобразования арабских чисел в римские
    private static String convToRim(int num) {
        int[] arabVal = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] rimVal = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder rim = new StringBuilder();
        for (int i = 0; i < arabVal.length; i++) {
            while (num >= arabVal[i]) {
                rim.append(rimVal[i]);
                num -= arabVal[i];
            }
        }
        return rim.toString();
    }
}
