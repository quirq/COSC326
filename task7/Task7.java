package task7;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Task7{

	public Task7(){
		validate(readText());
	}

	private void validate(ArrayList<String> emails){
		for(String s : emails){
			String curr = s;

			curr = changeToLowercase(curr);



			System.out.println(curr);
		}
	}

	private String changeToLowercase(String emailToValidate){
		return emailToValidate.toLowerCase();
	}

	private String replaceAtTextWithSymbol(String emailToValidate){

	}

	private String replaceDotTextWithSymbol(String emailToValidate){

	}

	private void validateMailbox(String emailToValidate){
		
		String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(regex);
	}

	private ArrayList readText(){
		Scanner scanner = new Scanner(System.in);
		ArrayList emails = new ArrayList<String>();

		emails.add(scanner.nextLine());

		return emails;
	}

	public static void main(String[] args){
		new Task7();
	}


}