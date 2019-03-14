package task6;

import java.util.Scanner;

public class Task6 {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        int base = Integer.parseInt(scan.nextLine());
        String first = scan.nextLine();
        String second = scan.nextLine();

        addition(base, first, second);
    }

    private static void addition(int base, String first, String second) {
        int size;
        int iterator;

        String[] firstString = first.split("(?!^)");
        String[] secondString = second.split("(?!^)");

        //sets size based on the longest number input
        size = firstString.length + 1;
        if(secondString.length > firstString.length) {
            size = secondString.length + 1;
        } else if(secondString.length == firstString.length) {
            size++;
        }

        //reverses the inputs
        iterator = 0;
        int[] firstNumber = new int[size];
        for(int i = firstString.length-1; i >= 0; i--) {
            firstNumber[iterator] = Integer.parseInt(firstString[i]);
            iterator++;
        }

        iterator = 0;
        int[] secondNumber = new int[size];
        for(int i = secondString.length-1; i >= 0; i--) {
            secondNumber[iterator] = Integer.parseInt(secondString[i]);
            iterator++;
        }

        /*
        for(int i : firstNumber){
            System.out.print(i + " ");
        }
        System.out.println();
        for(int i : secondNumber){
            System.out.print(i + " ");
        }
        */

        int[] sum = new int[size];
        int[] divisor = new int[size];
        int[] quotient = new int[size];
        int remainder = 0;

        //add the two numbers together by array index, back to front
        iterator = size - 1;
        for(int i = 0; i < size; i++) {
            sum[iterator] = firstNumber[i] + secondNumber[i];
            iterator--;
        }

        //if the number in sum is greater than the base, subtract the base and add one to the next number along
        for(int i = size - 1; i >= 0; i--) {
            if(sum[i] >= base) {
                sum[i] -= base;
                sum[i-1]++;
            }
        }

        //makes a copy of sum to use in division
        System.arraycopy(sum, 0, divisor, 0, size);

        for(int i = 0; i < size; i++) {
            quotient[i] = divisor[i]/2;
            if(divisor[i]%2 == 1) {
                if(i != size -1) {
                    divisor[i + 1] += base;
                } else {
                    remainder = 1;
                }
            }
        }

        System.out.println("\nSum in base " + base + ":");

        //place removes placeholder 0s
        boolean place = false;
        for(int i : sum){
            if(i != 0) {
                place = true;
            }
            if(place) {
                System.out.print(i);
            }
        }

        System.out.println("\nQuotient:");
        place = false;
        for(int i: quotient){
            if(i != 0) {
                place = true;
            }
            if(place) {
                System.out.print(i);
            }
        }

        System.out.println("\nRemainder: " + remainder);
    }
}
