package edu.cursor.library.service;

import java.util.*;
import java.util.stream.Collectors;

import edu.cursor.library.infrastructure.persistance.MySqlUserDao;
import edu.cursor.library.model.TblBook;
import edu.cursor.library.security.SingleUserAuthImpl;
import edu.cursor.library.security.RegisterImpl;
import edu.cursor.library.security.SecurityServiceImpl;
import edu.cursor.library.model.TblUser;
import edu.cursor.library.model.UserRole;
//import edu.cursor.library.infrastructure.persistance.CSVFileUserDao ;


public class UserServiceImpl implements UserService {

	private static String projPath = System.getProperty("user.dir"),
		dbPath = "/src/main/resources/",
		fileName = "userList.csv",
		file = projPath + dbPath + fileName ;
	private static final String DELIMITER = "," ;

	private UserBooksRegistryServiceImpl userBooksRegistry = new UserBooksRegistryServiceImpl();
	private BookServiceImpl bookService = BookServiceImpl.getInstance();
	private SingleUserAuthImpl auth = SingleUserAuthImpl.getInstance();

//	CSVFileUserDao userDao = new CSVFileUserDao();
	private MySqlUserDao userDao = new MySqlUserDao();


	private UserServiceImpl() { }
	private static UserServiceImpl instance ;
	public static UserServiceImpl getInstance(){
		if( instance == null ){
			synchronized (UserServiceImpl.class){
				// Double check
				if (instance == null) {
					instance = new UserServiceImpl() ;
				}
			}
		}
		return instance;
	}


	public void showUserList(){
		System.out.println("\tid\t\t First name \t\t Last Name \t\t email \t\t role \t\t registered  ");
		System.out.println(
			"---------------------------------------------------------------------------------" +
				"------------------------------------------------------------------------------");
		System.out.println( userDao.findAll() );
	}

	public void showUserListMenu(){
		boolean is = false;
		Scanner scan = new Scanner(System.in);
		String userReply = "";
//		TblUser currentUser = auth.getLoggedInUser() ;
		RegisterImpl register = new RegisterImpl();
//		LibraryServiceImpl lib = new LibraryServiceImpl();

		do{
			List<TblUser> userList = userDao.findAll();
			if( userList.size() == 0 ) {
				System.out.println("Ther'e no users. \nz - to Main menu");
			} else {
				System.out.println("a - ADD user, d - DELETE user, e - EDIT user, v - View taken books by user," +
					" " +
					"w - View ALL taken books" +
					"\n" +
					"z - to Main menu");
			}

			// Wait for user reply
			userReply = scan.nextLine().trim();
			switch (userReply.toLowerCase()){
				case "a":
					is = true;
					register.registerUser();
					break;
				case "d":
					is = true;
					promptDeleteUser();
					break;
				case "e":
					is = true;
					promptUpdateuser();
					break;
				case "v":
					is = true;
					promptViewTakenBooks();
					break;
				case "w":
					is = true;
					showAllUserBooks();
					break;
				case "z":
					is = true;
			}


		} while(!is);

	}


	private Integer presentUserCount( int userId ) {
		List<TblUser> userList = userDao.findAll();
		Map<TblUser, Long> presentUserMap = userList.stream()
			.filter(i -> i.getId().equals( userId ))
			.collect(
				Collectors.groupingBy(
					i -> i, Collectors.counting()
				)
			);
		return presentUserMap.size();
	}

	private void promptUpdateuser(){

		SecurityServiceImpl security = SecurityServiceImpl.getInstance();
		Scanner scan = new Scanner(System.in);
		boolean is = false;
//		List<TblUser> userList = userDao.findAll();

		do{

			System.out.println("Input user id to edit: ");
			String userReply = scan.nextLine();

			if( !security.validateUserId( userReply ) ){
				is = false;
			} else {
				is = true;

				TblUser user = userDao.findById(Integer.parseInt(userReply)) ;
				if( user != null ){
					showUserProfile(user);
				} else {
					System.out.println("Seems like's no such user in here...");
					is = false;
				}
			}
		}while (!is);
	}


	private void promptDeleteUser(){

		SecurityServiceImpl security = SecurityServiceImpl.getInstance();
		Scanner scan = new Scanner(System.in);
		TblUser loggedInUser = auth.getLoggedInUser();
		boolean is = false;

		do {
			System.out.println("Input user id to delete: ");
			String userReply = scan.nextLine();

			if( !security.validateUserId( userReply ) ){
				is = false;
			} else {
				is = true;

				if( presentUserCount( Integer.parseInt(userReply) ) > 0 ){
					boolean userConfirm = false;
					do{

						// Delete Yourself
						if( loggedInUser.getId().equals( Integer.parseInt(userReply) ) ){
							System.out.println("You can't delete yourself.");
							break;
						}

						System.out.println("Are u sure to delete this user? y/n:");
						String userConfirmReply = scan.nextLine().toLowerCase();

						switch (userConfirmReply){
							case "y":
								userConfirm = true;
								userDao.deleteUser( Integer.parseInt(userReply) );
								break;
							case "n":
								userConfirm = true;
								break;
						}
					}while(!userConfirm);
				} else {
					System.out.println("Seems like's no such user in here...");
					is = false;
				}
			}

		} while (!is);

		showUserList();
		showUserListMenu();
	}

	@Override
	public void takeBookRequest(TblUser user) {
		Scanner scan = new Scanner(System.in);
		String userChoice = "";
		boolean isInList = false;
		boolean isISBN = false;
		TblUser currentUser = auth.getLoggedInUser();

		do {

			do {
				System.out.println("Input desired book ISBN please: ");
				userChoice = scan.nextLine().toLowerCase().trim();
				if (bookService.validateISBN(userChoice)) {
					isISBN = true;
				} else {
					isISBN = false;
					System.out.println("Seems like not ISBN...");
				}
			} while (!isISBN);
			int userIsbn = Integer.parseInt(userChoice);


			// "How much books with this ISBN are present in Library?"
			Long _booksCount = bookService.readBookList().stream()
				.filter(i -> i.getISBN() == userIsbn)
				.count();
			System.out.println("_booksCount: " + _booksCount);

			if( _booksCount != 0 ){
				isInList = true;

				// 1
				List<TblBook> _takenBooks = userBooksRegistry.getTakenBooksByIsbn(userIsbn);
				System.out.println( "_takenBooks: " + _takenBooks );

				// If taken books == library books count ---> NO AVAILABLE BOOKS
				if( _takenBooks.size() == _booksCount ){
					System.out.println("NO AVAILABLE BOOKS. Try later.");
				} else if( _takenBooks.size() < _booksCount ){
					System.out.println("Availbale. Please take one.");

					// REAL TAKEBOOK METHOD
					takeBook( currentUser, bookService.getBookById(userIsbn) );

				} else {
					System.out.println("Smth wrong. Contact admin urgently!");
				}
			}
			else {
				isInList = false;
				System.out.println("No such book in list. Try ones more... \n");
			}
		} while(!isInList);
	}

	@Override
	public void takeBook(TblUser user, TblBook book) {
		userBooksRegistry.registerBook(user, book);
	}

	@Override
	public void returnBook(TblUser user, TblBook book) {
		userBooksRegistry.unregisterBook( user, book );
	}


	/**
	 ** ------------ User -----------------
	 **/

	// This is for admin eventually
	private void promptViewTakenBooks(){
		Scanner scan = new Scanner(System.in);
		String userReply;
		boolean isId = false;
		boolean userExists = false;

		do{
			System.out.println("Give proper user id: ");
			userReply = scan.nextLine();
			isId = SecurityServiceImpl.getInstance().validateUserId( userReply );

			if( isId ){
				userExists = SecurityServiceImpl.getInstance().userExists( Integer.parseInt(userReply) );

				if(userExists){
//					TblUser user = UserServiceImpl.getInstance().getUser(Integer.parseInt(userReply));
					TblUser user = userDao.findById( Integer.parseInt(userReply) ) ;
					showUserBooks( user );
					showUserBooksMenu( user );
				}
			}

		}while (!userExists);
	}


	private List<TblBook> getUserBooks( TblUser user ) {
		List<TblBook> userBooks = new ArrayList<>();
		int userId = user.getId();

		Map<Integer, List<Integer>> bookRegistry = userBooksRegistry.getRegistryMapByUsers();

		for( Map.Entry<Integer, List<Integer>> m : bookRegistry.entrySet() ){
			if( userId == m.getKey() ){

				for( int bookIsbn : m.getValue() ){
					userBooks.add( bookService.getBookById( bookIsbn ) );
				}
			}
		}
		return userBooks ;
	}

	public void showUserBooks( TblUser user ) {

		if( user.getId().equals( auth.getLoggedInUser().getId() ) ){
			System.out.println("--- My books here ---");
		} else {
			System.out.println("--- User books here (" + user.getId() + ", " + user.getFirstName() + ", "
				                 + user.getLastName() + ", " + ", " + user.geteMail() + ", "
								     + user.getRole() + ") ---");
		}

		for( TblBook b : getUserBooks( user ) ){
			System.out.println(b);
		}

		System.out.println("---------------------");
	}


	public void showAllUserBooks( ) {
		System.out.println("--- All Users books here --- \n" );

		List<TblBook> userBooks = new ArrayList<>();
		Map<Integer, List<Integer>> bookRegistry = userBooksRegistry.getRegistryMapByUsers();

		for( Map.Entry<Integer, List<Integer>> m : bookRegistry.entrySet() ){

//			TblUser user = getUser(m.getKey());
			TblUser user = userDao.findById(m.getKey());
			System.out.println("• User " + user.getId() + ", " + user.getFirstName() + " (" + user.geteMail() +
				") has books: " );

			for( Integer x : m.getValue() ){
				System.out.println( "- " + bookService.getBookById( x ) );
			}
			System.out.println();

		}
		System.out.println("---------------------");
	}


	public void showUserBooksMenu(TblUser user ){

		boolean is = false;
		Scanner scan = new Scanner(System.in);
		String userReply = "";
		TblUser currentUser = auth.getLoggedInUser() ;

		do{
			List<TblBook> userBooks = getUserBooks( user );
			if( userBooks.size() == 0 ) {
				System.out.println("You have no books taken. \n");
			} else {
				if( currentUser == user ){
					System.out.println("r - Return book \n");
				}
			}
			System.out.println("z - to Main menu");


			// Wait for user reply
			userReply = scan.nextLine().trim();

			if( userReply.equalsIgnoreCase("r") && currentUser == user ){
				is = true;

				if( userBooks.size() == 1 ){
					returnBook( currentUser, userBooks.get(0) );
				} else if( userBooks.size() > 1 ){

					// READ ISBN of returning book
					boolean isIsbn = false ;
					do{

						System.out.println("Give ISBN of book to return");
						userReply = scan.nextLine();
						if( !bookService.validateISBN(userReply) ){
							isIsbn = false;
							System.out.println("Seems like invalid ISBN...");
						} else if(bookService.getBookById( Integer.parseInt(userReply) ) != null ) {
							isIsbn = true;
							for( TblBook book : userBooks ){
								if( book.getISBN() == Integer.parseInt( userReply ) ){
									userBooksRegistry.unregisterBook( currentUser, book );
									break;
								}
							}
						} else {
							isIsbn = false;
							System.out.println("No such book here.");
						}

					} while (!isIsbn);

				}

				showUserBooks( currentUser );
				showUserBooksMenu( currentUser );
			}

			if( userReply.equalsIgnoreCase("z") ){
				is = true;
			}

		} while(!is) ;
	}





	public void showUserProfile(TblUser user){
		TblUser currentUser = auth.getLoggedInUser();

		System.out.println("---------- User Profile ---------------");
		System.out.println("First Name: " + user.getFirstName() );
		System.out.println("Last Name: " + user.getLastName() );
		System.out.println("Email (login): " + user.geteMail() );
		System.out.println("Mobile #: " + user.getMobileNum() );
		System.out.println("Address: " + user.getAddress() );
		System.out.println("Registration Date: " + user.getDateOfRegistration() );
		System.out.println("Role: " + user.getRole().toString() );
		System.out.println("----------------------------------------");


		Scanner scan = new Scanner(System.in);
		String userReply = "";
		boolean is = false;

		System.out.print( "Edit your profile: \n" +
			"a: First Name \n" +
			"b: Last Name \n" +
			"c: Mobile Number \n") ;
		if( currentUser.getRole().equals( UserRole.ADMIN ) ){
			System.out.print( "r: Role \n");
			System.out.print("\nu: User List \n");
		}
		System.out.print("\nz: Main Menu \n");

		do {
			if( currentUser.getRole().equals( UserRole.ADMIN ) ){
				System.out.println("Make your choice... (a, b, c, u, z)");
			} else {
				System.out.println("Make your choice... (a, b, c, z)");
			}

			userReply = scan.nextLine().toLowerCase().trim();

			switch ( userReply ){
				case "a":
					System.out.print(user.getFirstName() + ". ");
					changeNames(user, 1);
					is = true;
					break;
				case "b":
					System.out.print(user.getLastName() + ". ");
					changeNames(user, 2);
					is = true;
					break;
				case "c":
					System.out.print(user.getMobileNum() + ". ");
					changeMobile(user);
					is = true;
					break;
				case "r":
					if( currentUser.getRole().equals( UserRole.ADMIN ) ){
						System.out.print(user.getRole() + ". ");
						changeRole(user);
						is = true;
					} else {
						is = false;
					}
					break;
				case "u":
					if( currentUser.getRole().equals( UserRole.ADMIN ) ) {
						is = true;
						showUserList();
						showUserListMenu();
						break;
					} else {
						System.out.println("Incorrect choice...");
						is = false;
					}
				case "z":
					is = true;
					break;
			}

		} while (!is);
	}

	public void changeNames(TblUser user, int first_second){
		SecurityServiceImpl securityService = SecurityServiceImpl.getInstance();
		Scanner scan = new Scanner(System.in) ;

		boolean correctName = false;
		String desiredName;
		do {
			System.out.print("Pls give new '" + first_second + "' name: ");
			desiredName = scan.nextLine().trim();
			// X
			if (desiredName.equalsIgnoreCase("x"))
				break;
			if ( securityService.validateName(desiredName) )
				correctName = true;
		} while (!correctName);

		if(correctName){
			if( first_second == 1 ){
				user.setFirstName(desiredName);
			} else if(first_second == 2 ){
				user.setLastName(desiredName);
			}
			userDao.updateUser(user);
		}
		showUserProfile(user);
	}

	public void changeMobile(TblUser user){
		SecurityServiceImpl securityService = SecurityServiceImpl.getInstance();
		Scanner scan = new Scanner(System.in) ;

		boolean correctMobile = false;
		String desiredMobile;

		do {
			System.out.print("Pls give new mobile # (9 numbers starting with NON-ZERO digit): ");
			desiredMobile = scan.nextLine();
			// X
			if (desiredMobile.equalsIgnoreCase("x"))
				break;
			if ( securityService.validateMobile(desiredMobile) )
				correctMobile = true;
		} while (!correctMobile);

		if(correctMobile){
			user.setMobileNum( Integer.parseInt(desiredMobile) );
//			updateUser(user);
			userDao.updateUser(user);
		}
		showUserProfile(user);
	}

	public void changeRole(TblUser user){
		SecurityServiceImpl securityService = SecurityServiceImpl.getInstance();
		Scanner scan = new Scanner(System.in) ;

		boolean correctRole = false;
		String desiredRole;

		do {
			System.out.print("Pls give desired role - 'a' for admin or 'u' for user: ");
			desiredRole = scan.nextLine().toLowerCase();
			// X
			if (desiredRole.equals("x"))
				break;
			if ( desiredRole.equals("a") ){
				correctRole = true;
				user.setRole( UserRole.ADMIN );
			}
			if ( desiredRole.equals("u") ){
				correctRole = true;
				user.setRole( UserRole.USER );
			}
		} while (!correctRole);
		if(correctRole){
//			updateUser(user);
			userDao.updateUser(user);
		}
		showUserProfile(user);
	}


	public static void main(String[] args) {
//		UserServiceImpl userService = new UserServiceImpl();
//		UserBooksRegistryServiceImpl userBooksRegistry = new UserBooksRegistryServiceImpl();
//		BookServiceImpl bookService = BookServiceImpl.getInstance();
//		SingleUserAuthImpl auth = SingleUserAuthImpl.getInstance();
//		CSVFileUserDao userDao = new CSVFileUserDao();


//		System.out.println( userService.userDao.findAll() );
//		System.out.println( userService.userDao.findById(14) );

//		TblUser usr = userService.userDao.findById(14) ;
//		System.out.println( userService.getUserBooks( usr ) );

//		System.out.println( userBooksRegistry.getTakenBooksByUserId(14) );

	}

}