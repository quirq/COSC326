package task6;

import java.util.Scanner;

public class Task6 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("What base are you adding in?");
        int base = Integer.parseInt(scan.nextLine());
        System.out.println("What is the first number to be added?");
        String first = scan.nextLine();
        System.out.println("What is the second number to be added?");
        String second = scan.nextLine();

        String[] firstString = first.split("(?!^)");
        String[] secondString = second.split("(?!^)");

        int size = firstString.length;
        if(secondString.length > firstString.length) {
            size = secondString.length;
        }

        int[] firstNumber = new int[size];
        for(int i = 0; i < firstString.length - size; i++) {
            firstNumber[i] = 0;
        }
        for(int i = firstString.length - size; i < firstString.length; i++) {
            firstNumber[i] = Integer.parseInt(firstString[i]);
        }

        int[] secondNumber = new int[size];
        for(int i = 0; i < secondString.length - size; i++) {
            secondNumber[i] = 0;
        }
        for(int i = secondString.length - size; i < secondString.length; i++) {
            secondNumber[i] = Integer.parseInt(secondString[i]);
        }

    }
    

}
