package task6;

import java.util.Scanner;

public class Task6 {
    public static void main(String[] args) {
        int base;
        int size;
        int iterator;

        Scanner scan = new Scanner(System.in);

        System.out.println("What base are you adding in?");
        base = Integer.parseInt(scan.nextLine());
        System.out.println("What is the first number to be added?");
        String first = scan.nextLine();
        System.out.println("What is the second number to be added?");
        String second = scan.nextLine();

        String[] firstString = first.split("(?!^)");
        String[] secondString = second.split("(?!^)");

        size = firstString.length;
        if(secondString.length > firstString.length) {
            size = secondString.length;
        } else if(secondString.length == firstString.length) {
            size++;
        }

        int[] firstNumber = new int[size];
        for(int i = 0; i < firstString.length - size; i++) {
            firstNumber[i] = 0;
        }
        iterator = 0;
        for(int i = firstString.length - size; i < firstString.length; i++) {
            firstNumber[i] = Integer.parseInt(firstString[iterator]);
            iterator++;
        }

        int[] secondNumber = new int[size];
        for(int i = 0; i < secondString.length - size; i++) {
            secondNumber[i] = 0;
        }
        iterator = 0;
        for(int i = secondString.length - size; i < secondString.length; i++) {
            secondNumber[i] = Integer.parseInt(secondString[iterator]);
            iterator++;
        }



    }
    

}
