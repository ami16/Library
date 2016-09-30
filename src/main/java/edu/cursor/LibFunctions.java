package edu.cursor;

import java.util.*;

public class LibFunctions {

	AuthFactory auth = AuthFactory.getInstance();
	Credential cred = Credential.getInstance();
	public static List<Book> bList = Book.bList ;
//	private static Map<Integer, Integer> ubList = User.ubList ;

	public LibFunctions() {
		auth.createUserList();
		cred.createCredList();
		bList = Book.createBookList() ;
	}

	public void showMainMenu(boolean userIsLogged) {
		System.out.println("MENU:\n");

		 System.out.println("userISLogged: " + auth.getUserIsLogged() );
		 System.out.println("loggedUser: " + auth.getLoggedUser() );
//		 System.out.println( uList.toString() );
//		 System.out.println( auth.getuList() );

		if (userIsLogged) {

			User user = auth.getUser( auth.getLoggedUser() );
			System.out.println("Hello, " + user.getFirstName());
			System.out.println("1. View library books");
			System.out.println("2. My books");
			System.out.println("3. My profile");
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
				auth.logOut();
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
				auth.logOut();
				break;
			default:
				System.out.println("Choose correct item (1-3, z or x): ");
			}
		} while (!is);
		return loggedChoice;
	}

	public void proceedMain(char answer, boolean isLogged) {
		if( !isLogged ) {
			switch (answer) {
				case '1': // Register
					auth.registerUser();
					break;

				case '2': // Login
					auth.validateUser();
					break;

				case '3': // View books
					getLibraryBooks();
					proposeLibSorted();
					break;
			}
		} else {
			switch (answer) {
				case '1': // View books
					getLibraryBooks();
					proposeLibSorted();
					break;

				case '2': // My books
					System.out.println("---My books here---");
					User.showMyBooks();
					System.out.println("-------------------");
					break;

				case '3': // My profile
					System.out.println("---My PROFILE---");
					break;
			}
		}
	}

	public void proceedLogged(char answer) {
		switch (answer) {

		case '1': // 1. View library books
			System.out.println("View LIBRARY");
			break;

		case '2': // 2. My books
			System.out.println("My books");
			break;

		case '3': // 3. My profile
			System.out.println("My profile");
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

	public void getLibraryBooks() {
		System.out.println("\tisbn\t|\t\t\ttitle\t\t\t\t\t  |  \tauthor\t |   publ. / writt.  |   (category)   |    (available)");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------");
		System.out.println( bList.toString() );
	}


	public void proposeLibSorted() {
		System.out.println("You can sort this list by: a) Title, b) Author, c) Written Date d) Publish Date.") ;
		if( auth.getUserIsLogged() ){
			System.out.println("t) - take a book" );
		}
		System.out.println("z) - to Main menu ");

		getLibSortedChoice() ;
	}

	public String getLibSortedChoice() {
		Scanner scan = new Scanner(System.in);
		String userChoice = "";
		boolean is = false;
		do {
			userChoice = scan.nextLine().toLowerCase().trim() ;
			switch (userChoice) {
				case "a":
					is = true; System.out.println("A!");
					Collections.sort(bList, Book.Comparators.TITLE_);
					getLibraryBooks();
					proposeLibSorted();
					break;
				case "b":
					is = true; System.out.println("B");
					Collections.sort(bList, Book.Comparators.AUTHOR_);
					getLibraryBooks();
					proposeLibSorted();
					break;
				case "c":
					is = true; System.out.println("C!");
					Collections.sort(bList, Book.Comparators.WRITTEN_);
					getLibraryBooks();
					proposeLibSorted();
					break;
				case "d":
					is = true; System.out.println("D");
					Collections.sort(bList, Book.Comparators.PUBLISHED_);
					getLibraryBooks();
					proposeLibSorted();
					break;
				case "t":
					if( auth.getUserIsLogged() ){
						is = true;
						User.takeBook() ;
						getLibraryBooks();
						proposeLibSorted();
						break;
					} else {
						is = false;
						System.out.println("Choose correct item (a-d, t or z): ");
						break;
					}


				case "z":
					is = true;
					break;
				default:
					System.out.println("Choose correct item (a-d, t or z): ");
			}
		} while (!is);
		return userChoice;
	}




	public void sayBye() {
		System.out.println("See ya.");
	}

}