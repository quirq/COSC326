package task7;

import java.util.ArrayList;
import java.util.Scanner;

public class Task7{

	public Task7(){
		validate(readText());
	}

	private void validate(ArrayList<String> emails){
		for(int i = 0; i < emails.size(); i++){

		}
	}

	private ArrayList readText(){
		Scanner scanner = new Scanner(System.in);
		ArrayList emails = new ArrayList<String>();

		while(scanner.hasNext()){
			emails.add(scanner.next());
		}

		return emails;
	}

	public static void main(String[] args){
		new Task7();
	}


}