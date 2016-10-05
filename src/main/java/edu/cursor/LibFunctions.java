package edu.cursor;

import java.util.*;

public class LibFunctions {

	Auth auth = Auth.getInstance();
	Credential cred = Credential.getInstance();

	public LibFunctions() {
		auth.createUserList();
		cred.createCredList();
		BookFunctions.bookList = BookFunctions.createBookList() ;
	}

	public void showMainMenu(boolean userIsLogged) {
		System.out.println("MENU:\n");

		if (userIsLogged) {
			User user = auth.getLoggedUser() ;

			System.out.println("userISLogged: " + auth.getUserIsLogged() );
			System.out.println("loggedUser: " + user.getEmail() + " (id: " + user.getId() +  ")" );
			System.out.println("Hello, " + user.getFirstName());
			System.out.println("----------------------------------------------------------");

			if(auth.getLoggedUser().getRole() == UserStates.ADMIN){
				// Logged == ADMIN == see this
				System.out.println("1. View library books");
				System.out.println("2. My books");
				System.out.println("3. My profile");
				System.out.println("4. View users list");
				System.out.println("z. Log out");
				System.out.println("x. Exit");
			} else {
				// Regular logged user see this
				System.out.println("1. View library books");
				System.out.println("2. My books");
				System.out.println("3. My profile");
				System.out.println("z. Log out");
				System.out.println("x. Exit");
			}
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
		// FOR ALL. The very beginning.
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

		// IF IS ADMIN
		if( auth.getLoggedUser().getRole() == UserStates.ADMIN ){
			do {
				loggedChoice = scan.next().charAt(0);
				switch (Character.toLowerCase(loggedChoice)) {
					case '1':
					case '2':
					case '3':
					case '4':
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
						System.out.println("Choose correct item (1-4, z or x): ");
				}
			} while (!is);
			return loggedChoice;
		}

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
					proposeLibrarySorted();
					break;
			}
		} else {

			switch (answer) {
				case '1': // View books
					getLibraryBooks();
					proposeLibrarySorted();
					break;

				case '2': // My books
					Book.showUserBooks(auth.getLoggedUser().getId());
					BookFunctions.showMyBooksMenu(auth.getLoggedUser().getId());
					break;

				case '3': // My profile
					UserFunctions.showProfile();
					break;

				case '4': // (*ADMIN*) View users list
					if( auth.getLoggedUser().getRole() == UserStates.ADMIN ){
						UserFunctions.getUsersForAdmin();
						UserFunctions.showUsersMenu();
					}
					break;
			}
		}
	}

	public void getLibraryBooks() {
		System.out.println("\tisbn\t|\t\t\ttitle\t\t\t\t\t  |  \tauthor\t | publ. / writt. | (category) | (available) | <taken by> ");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------");
		System.out.println( BookFunctions.bookList );
	}


	public void proposeLibrarySorted() {
		System.out.println("You can sort this list by: a) Title, b) Author, c) Written Date d) Publish Date.") ;
		if( auth.getUserIsLogged() ){
			System.out.println("t - take a book" );

			if( auth.getLoggedUser().getRole() == UserStates.ADMIN ){
				System.out.println("k - add a book" );
				System.out.println("i - edit a book" );
			}
		}
		System.out.println("z - to Main menu ");

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
					is = true;
					Collections.sort(BookFunctions.bookList, Book.Comparators.TITLE_);
					getLibraryBooks();
					proposeLibrarySorted();
					break;
				case "b":
					is = true;
					Collections.sort(BookFunctions.bookList, Book.Comparators.AUTHOR_);
					getLibraryBooks();
					proposeLibrarySorted();
					break;
				case "c":
					is = true;
					Collections.sort(BookFunctions.bookList, Book.Comparators.WRITTEN_);
					getLibraryBooks();
					proposeLibrarySorted();
					break;
				case "d":
					is = true;
					Collections.sort(BookFunctions.bookList, Book.Comparators.PUBLISHED_);
					getLibraryBooks();
					proposeLibrarySorted();
					break;
				case "t":
					if( auth.getUserIsLogged() ){
						is = true;
						UserFunctions.takeBook( auth.getLoggedUser().getId() ) ;
						getLibraryBooks();
						proposeLibrarySorted();
						break;
					} else {
						is = false;
						System.out.println("Choose correct item (a-d, t or z): ");
						break;
					}
				case "k":
					if( auth.getLoggedUser().getRole() == UserStates.ADMIN ){
						is = true;
						System.out.println("---- K - zaglushka -----");
						break;
					} else {
						is = false;
						System.out.println("Choose correct item (a-d, t or z): ");
						break;
					}
				case "i":
					if( auth.getLoggedUser().getRole() == UserStates.ADMIN ){
						is = true;
						System.out.println("---- I - zaglushka -----");
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