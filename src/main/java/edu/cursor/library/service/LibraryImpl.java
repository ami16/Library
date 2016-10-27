package edu.cursor.library.service;

import edu.cursor.library.book.service.BookServiceImpl;
import edu.cursor.library.security.auth.AuthImpl;
import edu.cursor.library.security.register.RegisterImpl;
import edu.cursor.library.security.validate.ValidateImpl;
import edu.cursor.library.user.entity.TblUser;
import edu.cursor.library.user.enums.Role;
import edu.cursor.library.user.service.UserServiceImpl;
import java.util.Scanner;

public class LibraryImpl implements Library {
	private Scanner scan = new Scanner(System.in);
	public static final String WRONG_CHOICE_MENU = "Choose correct item (1-3 or x): ";
	private UserServiceImpl userService = new UserServiceImpl();
	private BookServiceImpl bookService = new BookServiceImpl();
	private ValidateImpl validService =new ValidateImpl();
	private RegisterImpl register = new RegisterImpl();
	private AuthImpl auth = AuthImpl.getInstance();
    private static LibraryImpl instance;
	private LibraryImpl() {
	}
	public static LibraryImpl getInstance(){
	      if( instance == null ){
	         synchronized (LibraryImpl.class){
	            // Double check
	            if (instance == null) {
	               instance = new LibraryImpl() ;
	            }
	         }
	      }
	      return instance ;
	   }

	@Override
	public void start() {
		System.out.println("Lib started");
		while (true) {
			showMainMenu(auth.isUserLogged(), auth);
		}
	}

	@Override
	public void showMainMenu(boolean userIsLogged, AuthImpl auth) {
		System.out.println("MENU:\n");

		if (userIsLogged) {
			TblUser user = auth.getLoggedUser();
			System.out.println("userISLogged: " + auth.isUserLogged());
			System.out.println("loggedUser: " + user.geteMail() + " (id: " + user.getId() + ")");
			System.out.println("Hello, " + user.getFirstName());
			System.out.println("----------------------------------------------------------");

			if (auth.getLoggedUser().getRole() == Role.ADMIN) {
				showMainMenuAdmin();
			} else {
				showMainMenuLogged();
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
	public void showMainMenuLogged() {
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
		// Logged == ADMIN == see this
		System.out.println("--- ADMIN ---");
		System.out.println("1. View library books");
		System.out.println("2. My books");
		System.out.println("3. My profile");
		System.out.println("4. View users list");
		System.out.println("z. Log out");
		System.out.println("x. Exit");
		replyReader(2);
	}
	public void showBookSubMenuAdmin() {
		System.out.println("--- BOOK LIST ---");
		System.out.println("1. Add new book");
		System.out.println("2. Add exsist book");
		System.out.println("3. Remove book");
		System.out.println("4. Back to main menu");
		switch (Character.toLowerCase(scan.next().charAt(0))) {
		case '1': {
			//bookService.addBookNew(ISBN, author, title, publYear, writYear, genre);
			break;
		}
		case '2' : {
			System.out.println("Select book`s ISBN for add: ");
			bookService.addBookOld(scan.nextInt());
			break;
		}
		case '3': {
			System.out.println("Select book`s ISBN for remove: ");
		bookService.replaceBook(scan.nextInt());
		break;
		}
		case '4' : {
			showMainMenuAdmin();
			break;
		}
		default : 
			System.out.println("Select correct item (1-4)");
			showBookSubMenuAdmin();
			}
		
	}

	public void showUserSubMenuAdmin() {
		System.out.println("1. Add user ");
		System.out.println("2. Remove user ");
		System.out.println("3. Back to main menu ");
		switch (Character.toLowerCase(scan.next().charAt(0))) {
		case '1': {
			register.registerUser();
			break;
		}
		case '2': {
			System.out.println("Select user`s id for remove: ");
			userService.deleteUser(scan.nextInt());
			break;
		}
		case '3': {
			showMainMenuAdmin();
			break;
		}
		default:
			System.out.println("Select correct item (1-3)");
			showUserSubMenuAdmin();
		}
	}

	public void replyReader(int type) {
		boolean is = false;
		do {
			switch (type) {

			case 0: // not logged in
				switch (Character.toLowerCase(scan.next().charAt(0))) {
				case '1':
					register.registerUser();
					is = true;
					break;
				case '2':
					validService.validateUser();
					is = true;
					break;
				case '3':
					bookService.viewBookList();
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
					bookService.viewBookList();
					is = true;
					break;
				case '2':
					// -- MY BOOKS
					is = true;
					break;
				case '3':
					userService.showUserProfile(auth.getLoggedUser());
					is = true;
					break;
				case 'z':
					auth.logOut();
					is = true;
					break;
				case 'x':
					// -- Exit
					sayBye();
					break;
				default:
					System.out.println(WRONG_CHOICE_MENU);
				}
				break;

			case 2: // logged in ADMIN
				switch (Character.toLowerCase(scan.next().charAt(0))) {
				case '1':
					bookService.viewBookList();
					showBookSubMenuAdmin();
					is = true;
					break;
				case '2':
					// -- MY BOOKS
					is = true;
					break;
				case '3':
					userService.showUserProfile(auth.getLoggedUser());
					is = true;
					break;
				case '4':
					System.out.println("-— USER LIST —-"); 
					userService.showUserList(); 
					showUserSubMenuAdmin(); 
					is = true;
					break;
				case 'z':
					auth.logOut();
					is = true;
					break;
				case 'x':
					// -- Exit
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

	public void sayBye() {
		System.out.println("See ya.");
		System.exit(0);
	}
}