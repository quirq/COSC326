//package task7;

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
				validatingEmail = replaceDomainDotTextWithSymbol(validatingEmail);
				validatingEmail = replaceMailboxDotTextWithSymbol(validatingEmail);
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

	private String replaceDomainDotTextWithSymbol(String emailToReplaceDot){


		StringBuilder replacedEmailDot = new StringBuilder(emailToReplaceDot);
		String mailbox = emailToReplaceDot.substring(0, emailToReplaceDot.indexOf("@"));
		String domainOnly = emailToReplaceDot.substring(emailToReplaceDot.indexOf("@") + 1);

		char charAfterAt = emailToReplaceDot.charAt(emailToReplaceDot.indexOf("@") + 1);
		if(charAfterAt == '['){

			StringBuilder bracketIP = new StringBuilder(domainOnly);
			while(bracketIP.toString().contains("_dot_")){
				int dotIndex = bracketIP.toString().indexOf("_dot_");
				bracketIP.replace(dotIndex, dotIndex + 5, ".");
			}
			String sansBracketIP = bracketIP.substring(1, bracketIP.length() - 1);

			String regex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
					"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(sansBracketIP);

			if(!matcher.matches() || bracketIP.indexOf("[") != 0 || bracketIP.lastIndexOf("]") != bracketIP.length() - 1){
				throw new RuntimeException("<- Invalid extension");
			}
			replacedEmailDot.replace(replacedEmailDot.indexOf("@") + 1, replacedEmailDot.length(), bracketIP.toString());
			return replacedEmailDot.toString();
		}
		else if(emailToReplaceDot.contains("_dot_")) {

			domainOnly = domainOnly.replaceAll("_dot_", ".");

		}

		Pattern domainPattern = Pattern.compile("^[A-Za-z0-9]+(\\.[A-Za-z0-9-]+)*$");
		Matcher matcher = domainPattern.matcher(domainOnly);
		if(!matcher.matches()){
			throw new RuntimeException("<- Invalid domain");
		}

//		String regex = "^[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
//		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//		Matcher match = pattern.matcher(domainOnly);
//
//		if(!match.matches()){
//			throw new RuntimeException("<- Invalid domain");
//		}

		return mailbox + "@" + domainOnly;
	}

	private String replaceMailboxDotTextWithSymbol(String emailToReplaceMailboxDot){

		String mailboxOnly = emailToReplaceMailboxDot.substring(0, emailToReplaceMailboxDot.indexOf("@"));

		mailboxOnly = emailToReplaceMailboxDot.replaceAll("_dot_", ".");
		return mailboxOnly;
	}

	private void validateDomain(String emailToValidateDomain){
		if(emailToValidateDomain.charAt(emailToValidateDomain.indexOf("@") + 1) == '['){
			return;
		}
		for(String validDomain : validDomainNames){
			if(emailToValidateDomain.lastIndexOf(validDomain) == emailToValidateDomain.length() - validDomain.length()){
				return;
			}
		}
		throw new RuntimeException("<- Invalid extension");
	}

	private void validateMailboxName(String emailToValidateMailbox){

		String mailbox = emailToValidateMailbox.substring(0, emailToValidateMailbox.indexOf("@"));

		//String regex = "^(?!-)(?!.*--)(?!_)(?!.*__)+(?<!-)+(?<!_)$";
		String regex = "^(?!-)(?!.*--)(?!_)(?!.*__)(?!.*-\\.)(?!.*-_)(?!.*_\\.)(?!.*_-)(?!.*\\.-)(?!.*\\._)[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*+(?<!-)+(?<!_)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(mailbox);

		if(!matcher.matches()){
			throw new RuntimeException("<- Invalid mailbox");
		}

	}

	private ArrayList readText(){
		Scanner scanner = new Scanner(System.in);

		ArrayList emails = new ArrayList<String>();

		while(scanner.hasNextLine()){
			emails.add(scanner.nextLine());
		}

//		ArrayList emails = new ArrayList<String>();
//		try {
//			Scanner scanner = new Scanner(new File("task7/input.txt"));
//			while(scanner.hasNextLine()){
//				emails.add(scanner.nextLine());
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}

		return emails;
	}

	public  void main(String[] args){
		new Task7();
	}


}