package task2;

import java.util.ArrayList;
import java.util.Scanner;

public class Task2 {

    ArrayList<String> dateLines;
    ArrayList<String> months = new ArrayList<>();

    public static void main(String[] args) {
        new Task2();
    }

    public Task2(){
        readInput();
        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        months.add("Aug");
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");

        for(String originalDate : dateLines){
            String modifiedDate = originalDate;
            try{
                System.out.println(validateInputText(modifiedDate));
            } catch(RuntimeException e){
                System.out.println(String.format("%s - INVALID: %s", originalDate, e.getMessage()));
            }

        }
    }

    private String validateInputText(String date){
        String[] dateArray = null;
        int year = 0;
        boolean leap = false;

        //Check how to split the string
        if(date.contains(" ")){
            dateArray = date.split(" ");
        } else if(date.contains("-")){
            dateArray = date.split("-");
        } else if(date.contains("/")){
            dateArray = date.split("/");
        } else {
            throw new RuntimeException("Incorrect number of inputs");
        }

        if(dateArray.length != 3){
            throw new RuntimeException("Incorrect number of inputs");
        }

        //Validate year is valid number
        try{
            year = Integer.parseInt(dateArray[2]);
        } catch (NumberFormatException e){
            throw new RuntimeException("Year is not a valid number");
        }

        //Check for leap year
        if(year < 100 && year > 49){
            year += 1900;
        } else if(year > 0 && year < 50){
            year += 2000;
        }
        if(year < 1753 || year > 3000){
            throw new RuntimeException("Year out of range");
        }

        if(year % 4 == 0) {
            if (year % 100 == 0 && year % 400 != 0){
                leap = false;
            } else {
                leap = true;
            }
        }

        //Check month
        int month = -1;
        String monthString = null;
        try{
            month = Integer.parseInt(dateArray[1]);
        } catch(NumberFormatException e){
            monthString = dateArray[1];
            for(String s : months){
                if(monthString.equals(s)){
                    month = months.indexOf(s) + 1;
                } else if(monthString.equals(s.toLowerCase())){
                    month = months.indexOf(s) + 1;
                } else if(monthString.equals(s.toUpperCase())){
                    month = months.indexOf(s) + 1;
                }
            }
            if(month == -1){
                throw new RuntimeException("Month not valid");
            }
        }
        if(month < 1 || month > 12){
            throw new RuntimeException("Month not valid");
        }

        //Checking day
        int day = -1;

        try{
            day = Integer.parseInt(dateArray[0]);
        } catch (NumberFormatException e){
            throw new RuntimeException("Day is not a valid number");
        }

        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            if(day < 1 || day > 31){
                throw new RuntimeException("Day is out of range");
            }
        } else if(month == 4 || month == 6 || month == 9 || month == 11){
            if(day < 1 || day > 30){
                throw new RuntimeException("Day is out of range");
            }
        } else if(month == 2){
            if(leap){
                if(day < 1 || day > 29){
                    throw new RuntimeException("Day is out of range");
                }
            } else {
                if(day < 1 || day > 28){
                    throw new RuntimeException("Day is out of range");
                }
            }
        }

        if(day < 10){
            return String.format("0%d %s %d", day, months.get(month - 1), year);
        }
        return String.format("%d %s %d", day, months.get(month - 1), year);

    }

    public void readInput(){
        Scanner scanner = new Scanner(System.in);
        dateLines = new ArrayList<>();

        while(scanner.hasNextLine()){
            dateLines.add(scanner.nextLine());
        }
    }

}
