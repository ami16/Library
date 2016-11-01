package edu.cursor.library.user.service;

import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.service.BookServiceImpl;
import edu.cursor.library.infrastructure.exceptions.ISBNFormatException;
import edu.cursor.library.security.auth.AuthImpl;
import edu.cursor.library.security.register.RegisterImpl;
import edu.cursor.library.security.service.SecurityServiceImpl;
import edu.cursor.library.service.LibraryImpl;
import edu.cursor.library.user.entity.TblUser;
import edu.cursor.library.user.enums.Role;
import edu.cursor.library.user.registry.UserBooksRegistryImpl;
import edu.cursor.library.user.utils.IOCsv;
import org.joda.time.LocalDate;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;


public class UserServiceImpl implements UserService {

    private static List<TblUser> userList = new ArrayList<>();
    private static String projPath = System.getProperty("user.dir"),
            dbPath = "/src/main/resources/",
            fileName = "userList.csv",
            file = projPath + dbPath + fileName;
    private static final String DELIMITER = ",";

    UserBooksRegistryImpl userBooksRegistry = new UserBooksRegistryImpl();
    BookServiceImpl bookService = BookServiceImpl.getInstance();
    AuthImpl auth = AuthImpl.getInstance();


    private UserServiceImpl() {
    }

    private static UserServiceImpl instance;

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            synchronized (UserServiceImpl.class) {
                // Double check
                if (instance == null) {
                    instance = new UserServiceImpl();
                }
            }
        }
        return instance;
    }

    public List<TblUser> createUserList() {
        List<TblUser> userList = new ArrayList<>();
        Collections.addAll(userList, IOCsv.readFile(file));
        return userList;
    }

//	@Override
//	public void showUserList() {
//		userList.stream().forEach(System.out::println);
//	}

//	@Override
//	public List<TblUser> getUserList() {
//		return userList;
//	}

//	@Override
//	public void deleteUser(int choice) {
//		try {
//			for (Iterator it = userList.iterator(); it.hasNext();) {
//				TblUser user = (TblUser) it.next();
//				if (user.getId() == choice) {
//					it.remove();
//					IOCsv.writeFile(userList, file);
//					System.out.println("User removed successfully.");
//				}
//			}
//		} catch (Exception e) {
//			System.out.println("User is not found. Try again");
//		} finally {
//			lib.showUserSubMenuAdmin();
//		}
//	}

//	@Override
//	public void addUser(TblUser newUser) {
//		try {
//			userList.add(new TblUser(newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.geteMail(),
//					newUser.getMobileNum(), newUser.getAddress(), newUser.getDateOfRegistration(), newUser.getRole()));
//			IOCsv.writeFile(userList, file);
//			System.out.println("Profile created successfully.");
//		} catch (IllegalArgumentException ie) {
//			System.out.println("Something wrong. Try again");
//		}
//	}

//	@Override
//	public void showUserProfile(TblUser user) {
//		try {
//			for (Iterator it = userList.iterator(); it.hasNext();) {
//				TblUser userId = (TblUser) it.next();
//				if (userId.getId().equals(user.getId()))
//					;
//			}
//			System.out.println(user.toString());
//		} catch (Exception ue) {
//			System.out.println("User not found");
//		} finally {
//			if (user.getRole().equals(Role.ADMIN)) {
//				lib.showMainMenuAdmin();
//			} else
//				lib.showMainMenuLoggedIn();
//		}
//	}


    /*--- INTRUSION ---*/
    public TblUser getUser(int id) {
        List<TblUser> userList = getUserList();
        for (TblUser u : userList) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<TblUser> getUserList() {
        userList.clear();

        try (Scanner scan = new Scanner(new File(file))) {
            String[] fields;
            while (scan.hasNext()) {
                fields = scan.nextLine().split(",");
                if (fields[0].trim().contains("Id")) continue;
                userList.add(new TblUser(
                        Integer.parseInt(fields[0]),
                        fields[1],
                        fields[2],
                        fields[3],
                        Integer.parseInt(fields[4]),
                        fields[5],
                        LocalDate.parse(fields[6]),
                        Role.valueOf(fields[7])
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void showUserList() {
        System.out.println("\tid\t\t First name \t\t Last Name \t\t email \t\t role \t\t registered  ");
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------------");
        System.out.println(getUserList());
    }

    public void showUserListMenu() {
        boolean is = false;
        Scanner scan = new Scanner(System.in);
        String userReply = "";
        TblUser currentUser = auth.getLoggedInUser();
        RegisterImpl register = new RegisterImpl();
        LibraryImpl lib = new LibraryImpl();

        do {

            List<TblUser> userList = getUserList();
            if (userList.size() == 0) {
                System.out.println("Ther'e no users. \nz - to Main menu");
            } else {
                System.out.println("a - ADD user, d - DELETE user, e - EDIT user, v - View taken books by user " +
                        "\nz - to Main" +
                        " menu");
            }

            // Wait for user reply
            userReply = scan.nextLine().trim();
            switch (userReply.toLowerCase()) {
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
                case "z":
                    is = true;
//					lib.showMainMenuAdmin();
            }


        } while (!is);

    }

    public void putUserList(List<TblUser> userList) {

        try (FileWriter fw = new FileWriter(new File(file))) {

            fw.write("Id,firstName,lastName,eMail,mobileNum,address,dateOfRegistration,role\n");
            for (TblUser u : userList) {
                fw.append(u.getId().toString());
                fw.append(DELIMITER);
                fw.append(u.getFirstName());
                fw.append(DELIMITER);
                fw.append(u.getLastName());
                fw.append(DELIMITER);
                fw.append(u.geteMail());
                fw.append(DELIMITER);
                fw.append(u.getMobileNum().toString());
                fw.append(DELIMITER);
                fw.append(u.getAddress());
                fw.append(DELIMITER);
                fw.append(u.getDateOfRegistration().toString());
                fw.append(DELIMITER);
                fw.append(u.getRole().toString());
                fw.append("\n");
            }
            fw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean addUser(TblUser user) {
        List<TblUser> tempUserList = getUserList();
        tempUserList.add(user);

        putUserList(tempUserList);
        return true;
    }


    public Integer presentUserCount(int userId) {
        Map<TblUser, Long> presentUserMap = userList.stream()
                .filter(i -> i.getId().equals(userId))
                .collect(
                        Collectors.groupingBy(
                                i -> i, Collectors.counting()
                        )
                );
        return presentUserMap.size();
    }

    public void promptUpdateuser() {

        SecurityServiceImpl security = SecurityServiceImpl.getInstance();
        Scanner scan = new Scanner(System.in);
        boolean is = false;
        List<TblUser> userList = getUserList();

        do {

            System.out.println("Input user id to edit: ");
            String userReply = scan.nextLine();

            if (!security.validateUserId(userReply)) {
                is = false;
            } else {
                is = true;

                TblUser user = getUser(Integer.parseInt(userReply));
                if (user != null) {
                    showUserProfile(user);
                } else {
                    System.out.println("Seems like's no such user in here...");
                    is = false;
                }
            }
        } while (!is);
    }

    public void updateUser(TblUser user) {
        boolean loggedUserIsAdmin = auth.getLoggedInUser().getRole() == Role.valueOf("ADMIN");
        List<TblUser> tempUserList = getUserList();

        for (TblUser u : tempUserList) {
            if (u.getId().equals(user.getId())) {
                u.setFirstName(user.getFirstName());
                u.setLastName(user.getLastName());
                u.setMobileNum(user.getMobileNum());
                if (loggedUserIsAdmin) {
                    u.setRole(user.getRole());
                }
                break;
            }
        }
        putUserList(tempUserList);
    }


    public void promptDeleteUser() {

        SecurityServiceImpl security = SecurityServiceImpl.getInstance();
        Scanner scan = new Scanner(System.in);
        TblUser loggedInUser = auth.getLoggedInUser();
        boolean is = false;

        do {
            System.out.println("Input user id to delete: ");
            String userReply = scan.nextLine();

            if (!security.validateUserId(userReply)) {
                is = false;
            } else {
                is = true;

                if (presentUserCount(Integer.parseInt(userReply)) > 0) {
                    boolean userConfirm = false;
                    do {

                        // Delete Yourself
                        if (loggedInUser.getId().equals(Integer.parseInt(userReply))) {
                            System.out.println("You can't delete yourself.");
                            break;
                        }

                        System.out.println("Are u sure to delete this user? y/n:");
                        String userConfirmReply = scan.nextLine().toLowerCase();

                        switch (userConfirmReply) {
                            case "y":
                                userConfirm = true;
                                deleteUser(Integer.parseInt(userReply));
                                break;
                            case "n":
                                userConfirm = true;
                                break;
                        }
                    } while (!userConfirm);
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
    public void deleteUser(int userId) {
        List<TblUser> tempUserList = getUserList();

        int counter = 0;
        for (TblUser u : tempUserList) {
            if (u.getId().equals(userId)) {
                tempUserList.remove(counter);
                break;
            }
            counter++;
        }
        putUserList(tempUserList);
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
                try {
                    if (bookService.validateISBN(userChoice)) {
                        isISBN = true;
                    } else {
                        isISBN = false;
                        System.out.println("Seems like not ISBN...");
                    }
                } catch (ISBNFormatException e) {
                    System.out.println(e.getMessage());
                }
            } while (!isISBN);
            String userIsbn = userChoice;


            // "How much books with this ISBN are present in Library?"
            Long _booksCount = bookService.getBookList().stream()
                    .filter(i -> i.getISBN() == Integer.parseInt(userIsbn))
                    .count();
            System.out.println("_booksCount: " + _booksCount);

            if (_booksCount != 0) {
                isInList = true;

                // 1
                List<TblBook> _takenBooks = userBooksRegistry.getTakenBooksByIsbn(Integer.parseInt(userIsbn));
                System.out.println("_takenBooks: " + _takenBooks);

                // If taken books == library books count ---> NO AVAILABLE BOOKS
                if (_takenBooks.size() == _booksCount) {
                    System.out.println("NO AVAILABLE BOOKS. Try later.");
                } else if (_takenBooks.size() < _booksCount) {
                    System.out.println("Availbale. Please take one.");

                    // REAL TAKEBOOK METHOD
                    takeBook(currentUser, bookService.getBookById(userIsbn));

                } else {
                    System.out.println("Smth wrong. Contact admin urgently!");
                }
            } else {
                isInList = false;
                System.out.println("No such book in list. Try ones more... \n");
            }
        } while (!isInList);
    }

    @Override
    public void takeBook(TblUser user, TblBook book) {
        userBooksRegistry.registerBook(user, book);
    }

    @Override
    public void returnBook(TblUser user, TblBook book) {
        userBooksRegistry.unregisterBook(user, book);
    }


    /**
     * * ------------ User -----------------
     **/

    // This is for admin eventually
    public void promptViewTakenBooks() {
        Scanner scan = new Scanner(System.in);
        String userReply;
        boolean isId = false;
        boolean userExists = false;

        do {
            System.out.println("Give proper user id: ");
            userReply = scan.nextLine();
            isId = SecurityServiceImpl.getInstance().validateUserId(userReply);

            if (isId) {
                userExists = SecurityServiceImpl.getInstance().userExists(Integer.parseInt(userReply));

                if (userExists) {
                    TblUser user = UserServiceImpl.getInstance().getUser(Integer.parseInt(userReply));
                    showUserBooks(user);
                    showUserBooksMenu(user);
                }
            }

        } while (!userExists);
    }


    public List<TblBook> getUserBooks(TblUser user) {
        List<TblBook> userBooks = new ArrayList<>();
        int userId = user.getId();

        Map<Integer, List<Integer>> bookRegistry = userBooksRegistry.getRegistryMapByUsers();

        for (Map.Entry<Integer, List<Integer>> m : bookRegistry.entrySet()) {
            if (userId == m.getKey()) {

                for (int bookIsbn : m.getValue()) {
                    userBooks.add(bookService.getBookById(String.valueOf(bookIsbn)));
                }
            }
        }
        return userBooks;
    }

    public void showUserBooks(TblUser user) {

        if (user.getId().equals(auth.getLoggedInUser().getId())) {
            System.out.println("--- My books here ---");
        } else {
            System.out.println("--- User books here (" + user.getId() + ", " + user.getFirstName() + ", "
                    + user.getLastName() + ", " + ", " + user.geteMail() + ", "
                    + user.getRole() + ") ---");
        }

        for (TblBook b : getUserBooks(user)) {
            System.out.println(b);
        }

        System.out.println("---------------------");
    }

    public void showUserBooksMenu(TblUser user) {

        boolean is = false;
        Scanner scan = new Scanner(System.in);
        String userReply = "";
        TblUser currentUser = auth.getLoggedInUser();

        do {
            List<TblBook> userBooks = getUserBooks(user);
            if (userBooks.size() == 0) {
                System.out.println("You have no books taken. \n");
            } else {
                if (currentUser == user) {
                    System.out.println("r - Return book \n");
                }
            }
//			if( currentUser.getRole() == Role.ADMIN ){
//				System.out.println("u - to Users menu");
//			}
            System.out.println("z - to Main menu");


            // Wait for user reply
            userReply = scan.nextLine().trim();

            if (userReply.equalsIgnoreCase("r") && currentUser == user) {
                is = true;

                if (userBooks.size() == 1) {
                    returnBook(currentUser, userBooks.get(0));
                } else if (userBooks.size() > 1) {

                    // READ ISBN of returning book
                    boolean isIsbn = false;
                    do {

                        System.out.println("Give ISBN of book to return");
                        userReply = scan.nextLine();
                        try {
                            if (!bookService.validateISBN(userReply)) {
                                isIsbn = false;
                                System.out.println("Seems like invalid ISBN...");
                            } else if (bookService.getBookById(userReply) != null) {
                                isIsbn = true;
                                for (TblBook book : userBooks) {
                                    if (book.getISBN() == Integer.parseInt(userReply)) {
                                        userBooksRegistry.unregisterBook(currentUser, book);
                                        break;
                                    }
                                }
                            } else {
                                isIsbn = false;
                                System.out.println("No such book here.");
                            }
                        } catch (ISBNFormatException e) {
                            System.out.println(e.getMessage());
                        }

                    } while (!isIsbn);

                }

                showUserBooks(currentUser);
                showUserBooksMenu(currentUser);
            }

            if (userReply.equalsIgnoreCase("z")) {
                is = true;
            }

        } while (!is);
    }


    public void showUserProfile(TblUser user) {
        TblUser currentUser = auth.getLoggedInUser();

        System.out.println("---------- User Profile ---------------");
        System.out.println("First Name: " + user.getFirstName());
        System.out.println("Last Name: " + user.getLastName());
        System.out.println("Email (login): " + user.geteMail());
        System.out.println("Mobile #: " + user.getMobileNum());
        System.out.println("Address: " + user.getAddress());
        System.out.println("Registration Date: " + user.getDateOfRegistration());
        System.out.println("Role: " + user.getRole().toString());
        System.out.println("----------------------------------------");


        Scanner scan = new Scanner(System.in);
        String userReply = "";
        boolean is = false;

        System.out.print("Edit your profile: \n" +
                "a: First Name \n" +
                "b: Last Name \n" +
                "c: Mobile Number \n");
        if (currentUser.getRole().equals(Role.ADMIN)) {
            System.out.print("r: Role \n");
            System.out.print("\nu: User List \n");
        }
        System.out.print("\nz: Main Menu \n");

        do {
            if (currentUser.getRole().equals(Role.ADMIN)) {
                System.out.println("Make your choice... (a, b, c, u, z)");
            } else {
                System.out.println("Make your choice... (a, b, c, z)");
            }

            userReply = scan.nextLine().toLowerCase().trim();

            switch (userReply) {
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
                    if (currentUser.getRole().equals(Role.ADMIN)) {
                        System.out.print(user.getRole() + ". ");
                        changeRole(user);
                        is = true;
                    } else {
                        is = false;
                    }
                    break;
                case "u":
                    if (currentUser.getRole().equals(Role.ADMIN)) {
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

    public void changeNames(TblUser user, int first_second) {
        SecurityServiceImpl securityService = SecurityServiceImpl.getInstance();
        Scanner scan = new Scanner(System.in);

        boolean correctName = false;
        String desiredName;
        do {
            System.out.print("Pls give new '" + first_second + "' name: ");
            desiredName = scan.nextLine().trim();
            // X
            if (desiredName.equalsIgnoreCase("x"))
                break;
            if (securityService.validateName(desiredName))
                correctName = true;
        } while (!correctName);

        if (correctName) {
            if (first_second == 1) {
                user.setFirstName(desiredName);
            } else if (first_second == 2) {
                user.setLastName(desiredName);
            }
            updateUser(user);
        }
        showUserProfile(user);
    }

    public void changeMobile(TblUser user) {
        SecurityServiceImpl securityService = SecurityServiceImpl.getInstance();
        Scanner scan = new Scanner(System.in);

        boolean correctMobile = false;
        String desiredMobile;

        do {
            System.out.print("Pls give new mobile # (9 numbers starting with NON-ZERO digit): ");
            desiredMobile = scan.nextLine();
            // X
            if (desiredMobile.equalsIgnoreCase("x"))
                break;
            if (securityService.validateMobile(desiredMobile))
                correctMobile = true;
        } while (!correctMobile);

        if (correctMobile) {
            user.setMobileNum(Integer.parseInt(desiredMobile));
            updateUser(user);
        }
        showUserProfile(user);
    }

    public void changeRole(TblUser user) {
        SecurityServiceImpl securityService = SecurityServiceImpl.getInstance();
        Scanner scan = new Scanner(System.in);

        boolean correctRole = false;
        String desiredRole;

        do {
            System.out.print("Pls give desired role - 'a' for admin or 'u' for user: ");
            desiredRole = scan.nextLine().toLowerCase();
            // X
            if (desiredRole.equals("x"))
                break;
            if (desiredRole.equals("a")) {
                correctRole = true;
                user.setRole(Role.ADMIN);
            }
            if (desiredRole.equals("u")) {
                correctRole = true;
                user.setRole(Role.USER);
            }
        } while (!correctRole);
        if (correctRole) {
            updateUser(user);
        }
        showUserProfile(user);
    }

}