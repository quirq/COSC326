package task7;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task7{

	private ArrayList<String> validDomainNames;

	public Task7(){
		validDomainNames = new ArrayList<>();
		validDomainNames.add("co.nz");
		validDomainNames.add("com.au");
		validDomainNames.add("co.ca");
		validDomainNames.add("com");
		validDomainNames.add("co.us");
		validDomainNames.add("co.uk");
		validate(readText());
	}

	private void validate(ArrayList<String> emails){
		for(String originalEmail : emails){
			String validatingEmail = originalEmail;

			try {
				validatingEmail = changeToLowercase(validatingEmail);
				validatingEmail = replaceAtTextWithSymbol(validatingEmail);
				validatingEmail = replaceDotTextWithSymbol(validatingEmail);
				validateDomain(validatingEmail);
				validateMailboxName(validatingEmail);

				System.out.println(validatingEmail);
			} catch (RuntimeException e){
				System.out.println(originalEmail + " " + e.getMessage());
			}

		}
	}

	private String changeToLowercase(String emailToValidate){
		return emailToValidate.toLowerCase();
	}

	private String replaceAtTextWithSymbol(String emailToReplaceAt){
		if(!emailToReplaceAt.contains("@") && !emailToReplaceAt.contains("_at_")){
			throw new RuntimeException("<- Missing @ symbol");
		}

		StringBuilder replacedEmailAt = new StringBuilder(emailToReplaceAt);
		if(emailToReplaceAt.contains("_at_") && !emailToReplaceAt.contains("@")){

			int lastIndexOfAt = emailToReplaceAt.lastIndexOf("_at_");
			replacedEmailAt.replace(lastIndexOfAt, lastIndexOfAt + 4, "@");

		}
		return replacedEmailAt.toString();
	}

	private String replaceDotTextWithSymbol(String emailToReplaceDot){


		StringBuilder replacedEmailDot = new StringBuilder(emailToReplaceDot);

		if(emailToReplaceDot.contains("_dot_") && emailToReplaceDot.indexOf("_dot_") > emailToReplaceDot.indexOf("@")){

			for(String validDomain : validDomainNames){
				if(emailToReplaceDot.lastIndexOf(validDomain) == emailToReplaceDot.length() - validDomain.length()){
					if(emailToReplaceDot.lastIndexOf("_dot_") + 5 == emailToReplaceDot.lastIndexOf(validDomain)){
						int dotIndex = emailToReplaceDot.indexOf("_dot_");
						replacedEmailDot.replace(dotIndex, dotIndex + 5, ".");
						return replacedEmailDot.toString();
					}
				}
			}
			throw new RuntimeException("<- Invalid extension");
		}
		return replacedEmailDot.toString();
	}

	private void validateDomain(String emailToValidateDomain){
		for(String validDomain : validDomainNames){
			if(emailToValidateDomain.lastIndexOf(validDomain) == emailToValidateDomain.length() - validDomain.length()){
				return;
			}
		}
		throw new RuntimeException("<- Invalid extension");
	}

	private void validateMailboxName(String emailToValidateMailbox){

		String mailbox = emailToValidateMailbox.substring(0, emailToValidateMailbox.indexOf("@"));

		String regex = "^[a-zA-Z0-9_.]-$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(mailbox);

		if(!matcher.matches()){
			throw new RuntimeException("<- Invalid mailbox");
		}

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