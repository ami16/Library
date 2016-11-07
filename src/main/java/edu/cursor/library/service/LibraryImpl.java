package edu.cursor.library.service;

import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.service.BookServiceImpl;
import edu.cursor.library.security.auth.SingleUserAuthImpl;
import edu.cursor.library.security.credentials.service.CredentialsImpl;
import edu.cursor.library.security.register.RegisterImpl;
import edu.cursor.library.security.validate.ValidateImpl;
import edu.cursor.library.user.entity.TblUser;
import edu.cursor.library.user.enums.Role;
import edu.cursor.library.user.registry.UserBooksRegistryImpl;
import edu.cursor.library.user.service.UserServiceImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class LibraryImpl implements Library {

	public static final String WRONG_CHOICE_MENU = "Choose correct item (1-3 or x): ";
	private static LibraryImpl instance;

	private Scanner scan = new Scanner(System.in);

	private SingleUserAuthImpl auth = SingleUserAuthImpl.getInstance();
	private CredentialsImpl cred = CredentialsImpl.getInstance();
	private BookServiceImpl bookService = BookServiceImpl.getInstance();
	private UserServiceImpl userService = UserServiceImpl.getInstance();
	private UserBooksRegistryImpl userBooksRegistry = new UserBooksRegistryImpl();

	public LibraryImpl() {}


	@Override
	public void start() {
		System.out.println("Lib started");
		while (true) {
			showMainMenu();
		}
	}

	@Override
	public void showMainMenu() {
		boolean userIsLogged = auth.isUserLoggedIn() ;
		System.out.println("MENU:\n");

		if (userIsLogged) {
			TblUser user = auth.getLoggedInUser();
			System.out.println("userISLogged: " + auth.isUserLoggedIn());
			System.out.println("loggedInUser: " + user.geteMail() + " (id: " + user.getId() + ")");
			System.out.println("Hello, " + user.getFirstName());
			System.out.println("----------------------------------------------------------");

			if (auth.getLoggedInUser().getRole() == Role.ADMIN) {
				showMainMenuAdmin();
			} else {
				showMainMenuLoggedIn();
			}
		} else {
			showMainMenuSimple();
		}
	}

	@Override
	public void showMainMenuSimple() {
		System.out.println("--- simple ---");
		System.out.println("1. REGISTER");
		System.out.println("2. LOGIN");
		System.out.println("3. VIEW LIBRARY BOOKS");
		System.out.println("x. EXIT");
		replyReader(0);
	}

	@Override
	public void showMainMenuLoggedIn() {
		// Regular logged user see this
		System.out.println("--- loGGed ---");
		System.out.println("1. View library books");
		System.out.println("2. My books");
		System.out.println("3. My profile");
		System.out.println("z. Log out");
		System.out.println("x. Exit");
		replyReader(1);
	}

	@Override
	public void showMainMenuAdmin() {
		// Logged In == +ADMIN == see this
		System.out.println("--- ADMIN ---");
		System.out.println("1. View library books");
		System.out.println("2. My books");
		System.out.println("3. My profile");
		System.out.println("4. View users list");
		System.out.println("z. Log out");
		System.out.println("x. Exit");
		replyReader(2);
	}

	public void replyReader(int type) {
		Scanner scan = new Scanner(System.in);
		boolean is = false;
		RegisterImpl register = new RegisterImpl();
		TblUser currentUser = auth.getLoggedInUser() ;
		ValidateImpl validate = new ValidateImpl();

		do {
			switch (type) {

			case 0: // not logged in
				switch (Character.toLowerCase(scan.next().charAt(0))) {
				case '1':
					// ++ REGISTER
					register.registerUser();
					is = true;
					break;
				case '2':
					// ++ LOGIN
					validate.validateUser();
					is = true;
					break;
				case '3':
					// ++ VIEW LIBRARY BOOKS
					viewLibraryBooks();
					proposeLibrarySorted();
					is = true;
					break;
				case 'x':
					sayBye();
					break;
				default:
					System.out.println(WRONG_CHOICE_MENU);
				}
				break;

			case 1: // simple logged in
				switch (Character.toLowerCase(scan.next().charAt(0))) {
				case '1':
					// ++ VIEW LIBRARY BOOKS
					viewLibraryBooks();
					proposeLibrarySorted();
					is = true;
					break;
				case '2':
					// ++ MY BOOKS
					userService.showUserBooks( currentUser );
					userService.showUserBooksMenu( currentUser );
					is = true;
					break;
				case '3':
					// ++ My profile
					userService.showUserProfile( currentUser );
					is = true;
					break;
				case 'z':
					// ++ LOG OUT
					auth.logOut();
					is = true;
					break;
				case 'x':
					// ++ Exit
					sayBye();
					break;
				default:
					System.out.println(WRONG_CHOICE_MENU);
				}
				break;

			case 2: // logged in ADMIN
				switch (Character.toLowerCase(scan.next().charAt(0))) {
				case '1':
					// ++ VIEW LIBRARY BOOKS
					viewLibraryBooks();
					proposeLibrarySorted();
					is = true;
					break;
				case '2':
					// ++ MY BOOKS
					userService.showUserBooks( currentUser );
					userService.showUserBooksMenu( currentUser );
					is = true;
					break;
				case '3':
					// ++ My profile
					is = true;
					userService.showUserProfile( currentUser );
					is = true;
					break;
				case '4':
					// ++ View users list
					userService.showUserList();
					userService.showUserListMenu();
					is = true;
					break;
				case 'z':
					// ++ LOG OUT
					auth.logOut();
					is = true;
					break;
				case 'x':
					// ++ Exit
					sayBye();
					break;
				default:
					System.out.println(WRONG_CHOICE_MENU);
				}
				break;

			default:
				replyReader(0);
			}
		} while (!is);
	}



	public void viewLibraryBooks() {
		System.out.println("\tisbn\t|\t\tauthor\t\t\t\t  |  \t\t\ttitle\t\t\t  |  publ. / writt. |   genre   |  amount  ");
		System.out.println(
			"---------------------------------------------------------------------------------------------------------------------");
		bookService.viewBookList();
	}

	public void viewLibraryBooks(Comparator<TblBook> comparator) {
		System.out.println("\tisbn\t|\t\tauthor\t\t\t\t  |  \t\t\ttitle\t\t\t  |  publ. / writt. |   genre   |  amount  ");
		System.out.println(
			"---------------------------------------------------------------------------------------------------------------------");
		bookService.viewBookList(comparator);
	}

	public void proposeLibrarySorted() {
		System.out.println("-----------------------------------------------------------\n" +
			"You can sort this list by: a) Author , b) Title, c) Publish Date d) Written Date.");
		if (auth.isUserLoggedIn()) {
			System.out.println("t - take a book");

			if (auth.getLoggedInUser().getRole() == Role.ADMIN) {
				System.out.println("n - add a NEW book");
				System.out.println("k - add the same book");
				System.out.println("e - edit a book");
			}
		}
		System.out.println("z - to Main menu ");

		getLibSortedChoice();
	}

	public String getLibSortedChoice() {
		Scanner scan = new Scanner(System.in);
		String userChoice = "";
		boolean is = false;
		do {
			userChoice = scan.nextLine().toLowerCase().trim();
			switch (userChoice) {
				case "a":
					Collections.sort(bookService.getBookList(), TblBook.BookComparator.AUTHOR_);
					viewLibraryBooks(TblBook.BookComparator.AUTHOR_);
					proposeLibrarySorted();
					is = true;
					break;
				case "b":
					Collections.sort(bookService.getBookList(), TblBook.BookComparator.TITLE_);
					viewLibraryBooks(TblBook.BookComparator.TITLE_);
					proposeLibrarySorted();
					is = true;
					break;
				case "c":
					Collections.sort(bookService.getBookList(), TblBook.BookComparator.PUBLISHED_);
					viewLibraryBooks(TblBook.BookComparator.PUBLISHED_);
					proposeLibrarySorted();
					is = true;
					break;
				case "d":
					Collections.sort(bookService.getBookList(), TblBook.BookComparator.WRITTEN_);
					viewLibraryBooks(TblBook.BookComparator.WRITTEN_);
					proposeLibrarySorted();
					is = true;
					break;
				case "t":
					if (auth.isUserLoggedIn()) {
						is = true;

						userService.takeBookRequest(auth.getLoggedInUser());

						viewLibraryBooks();
						proposeLibrarySorted();
						break;
					} else {
						is = false;
						System.out.println("Choose correct item (a-d, t or z): ");
						break;
					}

				case "n":
					if (auth.getLoggedInUser().getRole() == Role.ADMIN) {

						// ADMIN! Add a NEW book!
						System.out.println("Pls enter ISBN for new book.");
						String ISBNnew = scan.nextLine();
						System.out.println("Pls enter Author for new book.");
						String authorNew = scan.nextLine();
						System.out.println("Pls enter Title for new book.");
						String titleNew = scan.nextLine();
						System.out.println("Pls enter Published Year(YYYY-MM-DD) for new book.");
						String publYearNew = scan.nextLine();
						System.out.println("Pls enter Written Year (YYYY-MM-DD) for new book.");
						String writYearNew = scan.nextLine();
						System.out.println("Pls enter genre for new book.");
						String genreNew = scan.nextLine();
						bookService.addBookNew(ISBNnew, authorNew, titleNew, publYearNew, writYearNew, genreNew);

						viewLibraryBooks();
						proposeLibrarySorted();
						is = true;
						break;
					} else {
						is = false;
						System.out.println("Choose correct item (a-d, k, n, t or z): ");
						break;
					}
				case "k":
					if (auth.getLoggedInUser().getRole() == Role.ADMIN) {

						// ADMIN! Add a book "exist" !
						bookService.addBookExist(scan.nextLine());
						viewLibraryBooks();
						proposeLibrarySorted();
						is = true;
						break;
					} else {
						is = false;
						System.out.println("Choose correct item (a-d, k, n, t or z): ");
						break;
					}
				case "e":
					if (auth.getLoggedInUser().getRole() == Role.ADMIN) {

						// ADMIN! Edit a book!
						System.out.println("---- EDIT BOOK - zaglushka -----");
						viewLibraryBooks();
						proposeLibrarySorted();
						is = true;
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
		System.exit(0);
	}
}