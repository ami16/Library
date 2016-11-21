package edu.cursor.library.ui;

import edu.cursor.library.model.BookComparator;
import edu.cursor.library.model.TblBook;
import edu.cursor.library.security.RegisterImpl;
import edu.cursor.library.security.SingleUserAuthImpl;
import edu.cursor.library.model.TblUser;
import edu.cursor.library.model.UserRole;
import edu.cursor.library.security.ValidateImpl;
import edu.cursor.library.service.BookServiceImpl;
import edu.cursor.library.service.UserBooksRegistryServiceImpl;
import edu.cursor.library.service.UserServiceImpl;

import static edu.cursor.library.infrastructure.Constants.*;

import java.util.*;

public class LibraryMenu {

   private SingleUserAuthImpl auth = SingleUserAuthImpl.getInstance();
   private BookServiceImpl bookService = BookServiceImpl.getInstance();
   private UserServiceImpl userService = UserServiceImpl.getInstance();
   private UserBooksRegistryServiceImpl booksRegistryService = new UserBooksRegistryServiceImpl();
//   private MySqlUserBooksRegistryDao booksRegistryDao = new MySqlUserBooksRegistryDao();
   private BookMenu bookMenu = new BookMenu();

   
   public LibraryMenu() {
   }

   public void showMainMenu() {
      boolean userIsLogged = auth.isUserLoggedIn() ;
      System.out.println("MENU:\n");

      if (userIsLogged) {
         TblUser user = auth.getLoggedInUser();
         System.out.println("userISLogged: " + auth.isUserLoggedIn());
         System.out.println("loggedInUser: " + user.getEMail() + " (id: " + user.getId() + ")");
         System.out.println("Hello, " + user.getFirstName());
         System.out.println("----------------------------------------------------------");

         if (auth.userCanCrud()) {
            showMainMenuAdmin();
         } else {
            showMainMenuLoggedIn();
         }
      } else {
         showMainMenuSimple();
      }
   }

   public void showMainMenuSimple() {
      System.out.println("--- simple ---");
      System.out.println("1. REGISTER");
      System.out.println("2. LOGIN");
      System.out.println("3. VIEW LIBRARY BOOKS");
      System.out.println("x. EXIT");
      replyReader(0);
   }

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
                     showLibraryBooks();
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
                     showLibraryBooks();
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
                     showLibraryBooks();
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

   private void getBooksPrintHeader(){
      System.out.println("\tisbn\t|\t\tauthor\t\t\t\t  |  \t\t\ttitle\t\t\t  |  publ. / writt. |   " +
          "genre   |  amount | taken  \n" +
          "-----------------------------------------------------------------------------------------------------" +
          "-----------------------");
   }

   private void print(Map<TblBook, Long> books, BookPrinter bookPrinter) {
      getBooksPrintHeader();

      for(Map.Entry<TblBook, Long> m : books.entrySet() ){
         TblBook b = m.getKey();
         Integer takenCount = booksRegistryService.getTakenBooksCountByIsbn( b.getISBN() );
         System.out.println( bookPrinter.accept(m.getKey()) + " (" + m.getValue() + "|" + takenCount + ")" );
      }
   }

   private void print(List<TblBook> books, BookPrinter bookPrinter) {
      getBooksPrintHeader();

      for( TblBook b : books ){
         System.out.println( bookPrinter.accept(b) );
      }
   }


   public void showLibraryBooks() {
      print( bookService.getBookListGroupedMap(), new BookPrinterTabbed() );
   }

   public void showLibraryBooks(Comparator<TblBook> comparator) {
      print( bookService.getBookListGroupedMap(comparator), new BookPrinterTabbed() );
   }

   public void proposeLibrarySorted() {
      System.out.println("-------------------------------------------------------------------------------------------" +
          "---------------------------------\n" +
          "You can sort this list by: i) ISBN, a) Author , b) Title, p) Publish Date w) Written Date.");
      if (auth.isUserLoggedIn()) {
         System.out.println("t - take a book");

         if (auth.getLoggedInUser().getRole() == UserRole.ADMIN) {
            System.out.println("n - add a book");
            System.out.println("e - edit a book");
            System.out.println("d - delete book");
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
            if( userChoice.equalsIgnoreCase("x") ){ break; }
         switch (userChoice) {
            case "i":
               showLibraryBooks(BookComparator.ISBN_);
               proposeLibrarySorted();
               is = true;
               break;
            case "a":
               showLibraryBooks(BookComparator.AUTHOR_);
               proposeLibrarySorted();
               is = true;
               break;
            case "b":
               showLibraryBooks(BookComparator.TITLE_);
               proposeLibrarySorted();
               is = true;
               break;
            case "p":
               showLibraryBooks(BookComparator.PUBLISHED_);
               proposeLibrarySorted();
               is = true;
               break;
            case "w":
               showLibraryBooks(BookComparator.WRITTEN_);
               proposeLibrarySorted();
               is = true;
               break;
            case "t":
               if (auth.isUserLoggedIn()) {
                  is = true;

                  userService.takeBookRequest(auth.getLoggedInUser());
                  showLibraryBooks();
                  proposeLibrarySorted();
                  break;
               } else {
                  is = false;
                  System.out.println("Choose correct item (a-d, t or z): ");
                  break;
               }

            case "n":
               if (auth.userCanCrud()) {

                  // ADMIN! Add a NEW book!
                  bookMenu.promptAddNewBook();
                  showLibraryBooks();
                  proposeLibrarySorted();
                  is = true;
                  break;
               } else {
                  is = false;
                  System.out.println("Choose correct item (a-d, k, n, t or z): ");
                  break;
               }
            case "e":
               if (auth.userCanCrud()) {

                  // ADMIN! Edit a book!
                  bookMenu.promptEditBook();
                  showLibraryBooks();
                  proposeLibrarySorted();
                  is = true;
                  break;
               } else {
                  is = false;
                  System.out.println("Choose correct item: ");
                  break;
               }
            case "d":
               if (auth.userCanCrud()) {

                  // ADMIN! DELETE a book!
                  bookMenu.promptDeleteBook();
                  showLibraryBooks();
                  proposeLibrarySorted();
                  is = true;
                  break;
               } else {
                  is = false;
                  System.out.println("Choose correct item: ");
                  break;
               }

            case "z":
               is = true;
               break;
            default:
               System.out.println("Choose correct MENU item");
         }
      } while (!is);
      return userChoice;
   }
   

   public void sayBye() {
      System.out.println("See ya.");
      System.exit(0);
   }
}