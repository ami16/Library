package edu.cursor;

import java.util.*;

public class LibFunctions {

	AuthFactory auth = AuthFactory.getInstance();
	private static List<Book1> bList = new ArrayList<>();

	public LibFunctions() {
		List<User> uList = auth.createUserList();
		bList = createBookList() ;
	}

	public void showMainMenu(boolean userIsLogged) {
		System.out.println("MENU:\n");

		 System.out.println("userISLogged: " + auth.getUserIsLogged() );
		 System.out.println("loggedUser: " + auth.getLoggedUser() );
//		 System.out.println( uList.toString() );
//		 System.out.println( auth.getuList() );

		if (userIsLogged) {

			User user = getUser(auth.getLoggedUser());
			System.out.println("Hello, " + user.getFirstName());
			System.out.println("1. View my profile");
			System.out.println("1. View library books");
			System.out.println("2. My books");
			System.out.println("z. Log out");
			System.out.println("x. Exit");
		} else {
			System.out.println("1. REGISTER");
			System.out.println("2. LOGIN");
			System.out.println("3. VIEW LIBRARY BOOKS");
			System.out.println("x. EXIT");
		}
	}

	public char getMain(boolean userIsLogged) {
		// Main or logged choice
		char choice;
		if (userIsLogged) {
			choice = getLoggedChoice();
		} else {
			choice = getMainChoice();
		}
		return choice;
	}

	public char getMainChoice() {
		Scanner scan = new Scanner(System.in);
		char mainChoice = 0;
		boolean is = false;
		do {
			mainChoice = scan.next().charAt(0);
			switch (Character.toLowerCase(mainChoice)) {
			case '1':
			case '2':
			case '3':
				is = true;
				break;
			case 'x':
				is = true;
				sayBye();
				System.exit(0);
				break;
			case 'z':
				is = true;
				logOut();
				break;
			default:
				System.out.println("Choose correct item (1-3 or x): ");
			}
		} while (!is);
		return mainChoice;
	}

	public char getLoggedChoice() {
		Scanner scan = new Scanner(System.in);
		char loggedChoice = 0;
		boolean is = false;
		do {
			loggedChoice = scan.next().charAt(0);
			switch (Character.toLowerCase(loggedChoice)) {
			case '1':
			case '2':
				is = true;
				break;
			case 'x':
				is = true;
				sayBye();
				System.exit(0);
				break;
			case 'z':
				is = true;
				logOut();
				break;
			default:
				System.out.println("Choose correct item (1-2, z or x): ");
			}
		} while (!is);
		return loggedChoice;
	}

	public void proceedMain(char answer) {
		switch (answer) {

		case '1': // Register
			registerUser();
			break;

		case '2': // Login
			validateUser();
			break;

		case '3': // View books
			getLibraryBooks();
			break;
		}
	}

	public void proceedLogged(char answer) {
		switch (answer) {

		case '1': // View LIBRARY
			System.out.println("View LIBRARY");
			break;

		case '2': // My books
			System.out.println("My books");
			break;

		case 'z': // Log out
			// logOut();
			break;

		case 'x': // EXIT
			System.out.println("EXIT");
			break;

		default:
			break;
		}
	}

	// 1 - REGISTER USER

	public void registerUser() {
		Scanner scan = new Scanner(System.in);
		String desiredMail;
		boolean loginAllowed = false;

		// LOGIN
		// for Andrew. It`s for you :) sorry..
		// Okay..
		outer: do {
			System.out.println("Please enter your MAIL as a login: ");
			desiredMail = scan.nextLine().trim();
			// X
			if (desiredMail.equalsIgnoreCase("x"))
				break;
			if (validateMail(desiredMail)) {

				if (!loginAvailable(desiredMail)) {
					loginAllowed = false;
				} else {

					// MAIL ok.
					// Now PASS
					boolean passCorrect = false;
					boolean passEqual = false;
					String pass1;
					System.out.print("OKAY NOW! Enter your password. ");
					do {
						System.out.println("Password must be at least 4 chars:");
						pass1 = scan.nextLine().trim();
						// X
						if (pass1.equalsIgnoreCase("x"))
							break outer;
						if (validatePass(pass1))
							passCorrect = true;
					} while (!passCorrect);
					do {
						System.out.println("Repeat pass ones more:");
						String pass2 = scan.nextLine().trim();

						// X
						if (pass2.equalsIgnoreCase("x"))
							break outer;
						if (pass1.equals(pass2)) {
							passEqual = true;
						} else {
							System.out.println("Your passwords doesn't match.");
						}
					} while (!passEqual);

					// PASS ok.
					// Now Name
					boolean correctName = false;
					String desiredName;
					do {
						System.out.print("Pls give your name: ");
						desiredName = scan.nextLine().trim();
						// X
						if (desiredName.equalsIgnoreCase("x"))
							break outer;
						if (validateName(desiredName))
							correctName = true;
					} while (!correctName);

					// Name ok.
					// Now name2
					boolean correctName2 = false;
					String desiredName2;
					do {
						System.out.print("Pls provide your second name: ");
						desiredName2 = scan.nextLine().trim();
						// X
						if (desiredName2.equalsIgnoreCase("x"))
							break outer;
						if (validateName(desiredName2))
							correctName2 = true;
					} while (!correctName2);

					// name2 ok.
					// Now MOBILE
					boolean correctMobile = false;
					int desiredMobile;
					do {
						System.out.print("Your mobile # (9 numbers): ");
						desiredMobile = scan.nextInt();
						// X
						if ( Integer.toString(desiredMobile).equalsIgnoreCase("x"))
							break outer;
						if (validateMobile(desiredMobile))
							correctMobile = true;
					} while (!correctMobile);

					// MOBILE ok.
					// Now ADDRESS
					boolean correctAddr = false;
					String desiredAddr;
					do {
						System.out.print("Provide your address (10 chars min): ");
						desiredAddr = scan.nextLine().trim();
						// X
						if ( desiredAddr.equalsIgnoreCase("x"))
							break outer;
						if ( desiredAddr.length() > 10 )
							correctAddr = true;
					} while (!correctAddr);

					loginAllowed = true;

//					uList.add(new User( getNewUserId(), desiredName, desiredName2, desiredMail, desiredMobile, desiredAddr, getNewUserRegDate() ));
					int newId = getNewUserId() ;
					auth.addUser( newId, desiredName, desiredName2, desiredMail, desiredMobile, desiredAddr, getNewUserRegDate(), pass1 ) ;
					System.out.println("Now login using your credentials");
				}

			} else {
				System.out.println("Not allowed");
			}
		} while (!loginAllowed);
	}

	private int getNewUserId(){
		Iterator<User> itr = auth.getuList().iterator();
		int currentId = 1 ;
		while (itr.hasNext()) {
			currentId = itr.next().getId();
		}
		return currentId + 1;
	}
	private String getNewUserRegDate(){
		Date date = new Date() ;
		return date.toString() ;
	}

	// 2 - LOGIN <VALIDATE USER>
	private void validateUser() {

		Scanner scan = new Scanner(System.in);
		String uLog, uPass, verifiedLog = "";
		boolean is = false;

		System.out.println("Enter your login:");
		// LOGIN
		do {
			uLog = scan.nextLine().trim();
			if (uLog.equals("")) {
				System.out.print("Enter smth: ");
			} else {
				is = userExists(uLog);
				if (is)
					verifiedLog = uLog;
			}
			if (!is) {
				System.out.println("No such login (3)");
			}
		} while (!is);

		// PASS
		System.out.println("Enter your pass:");
		do {
			uPass = scan.nextLine().trim();
			if (uPass.equals("")) {
				System.out.print("Enter smth: ");
			} else {
				// System.out.println("uLog: " + uLog + ", uPass: " + uPass);
				is = passCorrect(verifiedLog, uPass);
				if (is)
					logIn(uLog);
			}
			if (!is) {
				System.out.println("Wrong password (3)");
			}
		} while (!is);

	}

	public boolean validateLogin(String val) {
		// http://stackoverflow.com/questions/12018245/regular-expression-to-validate-username
		return val.matches("^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
	}

	public boolean validatePass(String val) {
		return (val.length() >= 4);
	}

	public boolean validateMail(String val) {
		return val.matches("^(.+)@(.+)\\.(.+){1,3}$");
	}

	public boolean validateName(String val) {
		return val.matches("^[a-zA-Z0-9_-]{2,16}$");
	}

	private boolean validateMobile( int val ){
		return Integer.toString(val).matches("^(\\d{9})$");
	}

	public boolean loginAvailable(String desiredLogin) {
		boolean loginAvailable = false;
		Iterator<User> iterator = auth.getuList().iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (user.getEmail().equals(desiredLogin)) {
				loginAvailable = false;
				System.out.println("Login NOT available.");
				break;
			} else {
				loginAvailable = true;
			}
		}
		return loginAvailable;
	}

	private User getUser(String login) {
		Iterator<User> iterator = auth.getuList().iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (user.getEmail().equals(login)) {
				return user;
			}
		}
		return iterator.next();
	}

	private boolean userExists(String login) {
		Iterator<User> iterator = auth.getuList().iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (user.getEmail().equals(login)) {
				// System.out.println("user.getLogin().equals( login ): " +
				// user.getLogin().equals( login ) + ", LOGIN: " + login);
				return true;
			}
		}
		return false;
	}
    //need class Credential for password
	private boolean passCorrect(String login, String pass) {
		Iterator<User> iterator = auth.getuList().iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
//			if (user.getEmail().equals(login) && user.getPass().equals(pass)) {
			if (user.getEmail().equals(login) ) {
				return true;
			}
		}
		return false;
	}

	private void logIn(String login) {
		auth.setLoggedUser(login);
		auth.setUserIsLogged(true);
	}

	private void logOut() {
		auth.setLoggedUser("");
		auth.setUserIsLogged(false);
	}



	private static List<Book1> createBookList() {
		/*
		 * 1 Boigraphy 2 Love stories 3 Fiction + 4 Tales 5 Fantasy
		 */

		bList.add(new Book1("0000000005", "Mothers, Tell Your Daughters", "Bonnie Jo Campbell", 1, (short) 1));
		bList.add(
				new Book1("0000000009", "Elon Musk: Tesla, SpaceX And Fantastic Future", "Ashlee Vance", 1, (short) 1));
		bList.add(new Book1("0000000015", "The Jemima Code", "Toni Tipton-Martin", 1, (short) 1));
		bList.add(new Book1("0000000016", "The Wright Brothers", "David McCullough", 1, (short) 1));
		bList.add(new Book1("0000000021", "Fortune Smiles", "Adam Johnson", 1, (short) 1));
		bList.add(new Book1("0000000031", "The Fifth Season", "N.K. Jemisin", 1, (short) 1));
		bList.add(new Book1("0000000032", "Nimona", "Noelle Stevenson", 1, (short) 1));
		bList.add(new Book1("0000000037", "Real Men Don't Sing", "Allison McCracken", 1, (short) 1));

		bList.add(new Book1("0000000002", "Accidental Saints", "Nadia Bolz-Weber", 2, (short) 1));
		bList.add(new Book1("0000000007", "Undermajordomo Minor", "Patrick deWitt", 2, (short) 1));
		bList.add(new Book1("0000000010", "Adventures Of Lovelace And Babbage", "Sydney Padua", 2, (short) 1));
		bList.add(new Book1("0000000019", "The Bear Ate Your Sandwich", "Julia Sarcone-Roach", 2, (short) 1));
		bList.add(new Book1("0000000020", "Among The Wild Mulattos", "Tom Williams", 2, (short) 1));
		bList.add(new Book1("0000000033", "The Sellout", "Paul Beatty", 2, (short) 1));
		bList.add(new Book1("0000000036", "Fates And Furies", "Lauren Groff", 2, (short) 1));

		bList.add(new Book1("0000000003", "Slade House", "David Mitchell", 3, (short) 1));
		bList.add(new Book1("0000000004", "Spy Games", "Adam Brookes", 3, (short) 1));
		bList.add(new Book1("0000000012", "Strangers Drowning", "Larissa MacFarquhar", 3, (short) 1));
		bList.add(new Book1("0000000013", "Honeydew", "Edith Pearlman", 3, (short) 1));
		bList.add(new Book1("0000000008", "All The Old Knives", "Olen Steinhauer", 3, (short) 1));
		bList.add(new Book1("0000000014", "The Unauthorised Life", "Jonathan Bate", 3, (short) 1));
		bList.add(new Book1("0000000023", "Habitat", "Lauren Liess", 3, (short) 1));
		bList.add(new Book1("0000000026", "Death And Mr. Pickwick", "Stephen Jarvis", 3, (short) 1));

		bList.add(new Book1("0000000011", "Small Plates To Share", "Ghillie Başan", 4, (short) 1));
		bList.add(new Book1("0000000001", "Peter Pan", "Author The First", 4, (short) 1));
		bList.add(new Book1("0000000018", "The Turnip Princess", "Franz-Xaver von Schönwerth", 4, (short) 1));
		bList.add(new Book1("0000000022", "A History of Japan", "Shigeru Mizuki", 4, (short) 1));
		bList.add(new Book1("0000000024", "A Spool Of Blue Thread", "Anne Tyler", 4, (short) 1));
		bList.add(new Book1("0000000034", "Hotels of North America", "Rick Moody", 4, (short) 1));
		bList.add(new Book1("0000000035", "Kitchen Hacks", "America's Test Kitchen", 4, (short) 1));

		bList.add(new Book1("0000000006", "Ancillary Mercy", "Ann Leckie", 5, (short) 1));
		bList.add(new Book1("0000000017", "Secrets Of State", "Matthew Palmer", 5, (short) 1));
		bList.add(new Book1("0000000025", "March", "Andrew Aydin", 5, (short) 1));
		bList.add(new Book1("0000000027", "Home", "Carson Ellis", 5, (short) 1));
		bList.add(new Book1("0000000028", "Listen, Slowly", "Thanhhà Lại", 5, (short) 1));
		bList.add(new Book1("0000000029", "Murder On Steep Street", "Heda Margolius Kovály", 5, (short) 1));
		bList.add(new Book1("0000000030", "The Shepherd's Crown", "Terry Pratchett", 5, (short) 1));

		return bList;
	}

	public void getLibraryBooks() {
		System.out.println("\tisbn\t|\t\ttitle\t\t\t\t\t|\tauthor\t\t| <category> | (available)");
		System.out.println(
				"--------------------------------------------------------------------------------------------");
		System.out.println(bList.toString());
	}

	public void sayBye() {
		System.out.println("See ya.");
	}

}