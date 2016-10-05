package edu.cursor;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class UserFunctions {


   public static void takeBook(int userId) {
      Scanner scan = new Scanner(System.in);
      String userChoice = "";
      boolean is = false;
      do {
//         System.out.println("Input ISBN please: ");

         Iterator<Book> itr = BookFunctions.bookList.iterator();

         boolean isISBN = false;
         do{
            System.out.println("Input correct ISBN please: ");
            userChoice = scan.nextLine().toLowerCase().trim();
            if( BookFunctions.validateISBN( userChoice ) ){
               isISBN = true;
            }
         } while(!isISBN);

         while (itr.hasNext()) {
            Book book = itr.next();
            if (book.getISBN() == Integer.parseInt(userChoice)) {
               is = true;
               System.out.print("Book is present. ");
               if (book.getQuantity() != 0) {
                  System.out.println("Take it.");
                  book.setQuantity(book.getQuantity() - 1);
                  book.setBookState( BookStates.TAKEN );
                  book.setTakenBy( userId );
                  book.setTakenAt( new LocalDate() );
               } else {
                  System.out.println("Book is unavailable. Turn back in few days. ");
               }
               break;
            }
         }
         if (!is) {
            System.out.println("No such book in list. Try ones more...");
         }
      } while (!is);
   }

   public static void returnBook(int userId) {
      Scanner scan = new Scanner(System.in);
      String userChoice = "";
      boolean is = false;
      do {

         System.out.println( "USER bookList Size: " + BookFunctions.getBookList(userId).size());

         if( BookFunctions.getBookList(userId).size() == 1 ){
            for( Book b : BookFunctions.getBookList() ){
               b.setTakenBy( 0 );
               b.setQuantity(b.getQuantity() + 1);
               b.setBookState( BookStates.AVAILBALE );
               b.setTakenAt( null );
            }
            is = true;

         } else {

            boolean isISBN = false;
            do{
               System.out.println("Input correct ISBN please: ");
               userChoice = scan.nextLine().toLowerCase().trim();
               if( BookFunctions.validateISBN( userChoice ) ){
                  isISBN = true;
               }
            } while(!isISBN);

            for( Book b : BookFunctions.getBookList() ){
               if(b.getTakenBy() == userId && b.getISBN() == Integer.parseInt(userChoice) ){
                  b.setTakenBy( 0 );
                  b.setQuantity(b.getQuantity() + 1);
                  b.setBookState( BookStates.AVAILBALE );
                  b.setTakenAt( null );
                  is = true;
               }
            }
         }

         if (!is) {
            System.out.println("No such book in list. Try ones more...");
         }
      } while (!is);
   }

   public static void showProfile(){
      User user = Auth.getInstance().getLoggedUser();
      Credential cred = Credential.getInstance();

      System.out.println("---------- User Profile ---------------");
      System.out.println("First Name: " + user.getFirstName() );
      System.out.println("Last Name: " + user.getLastName() );
      System.out.println("Email (login): " + user.getEmail() );
      System.out.println("Mobile #: " + user.getMobileNo() );
      System.out.println("Address: " + user.getAddress() );
      System.out.println("Reegistration Date: " + user.getRegistrationDate() );
      System.out.println("----------------------------------------");


      Scanner scan = new Scanner(System.in);
      String userChoice = "";
      boolean is = false;

      System.out.println( "Edit your profile: \n" +
          "a: First Name \n" +
          "b: Last Name \n" +
          "c: Mobile Number \n\n" +
          "z: Main Menu");

      do {
         System.out.println("Make your choice... (a, b, c, z)");
         userChoice = scan.nextLine().toLowerCase().trim();

         switch ( userChoice ){
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
               System.out.print(user.getMobileNo() + ". ");
               changeMobile(user);
               is = true;
               break;
            case "z":
               is = true;
               break;
         }

      } while (!is);
   }

   public static void changeNames(User user, int first_second){
      Scanner scan = new Scanner(System.in) ;

      boolean correctName = false;
      String desiredName;
      do {
         System.out.print("Pls give new '" + first_second + "' name: ");
         desiredName = scan.nextLine().trim();
         // X
         if (desiredName.equalsIgnoreCase("x"))
            break;
         if ( Auth.getInstance().validateName(desiredName) )
            correctName = true;
      } while (!correctName);

      if(correctName){
         if( first_second == 1 ){
            user.setFirstName(desiredName);
         } else if(first_second == 2 ){
            user.setLastName(desiredName);
         }
      }
      showProfile();
   }

   public static void changeMobile(User user){
      Scanner scan = new Scanner(System.in) ;

      boolean correctMobile = false;
      int desiredMobile;

      do {
         System.out.print("Pls give new mobile # (9 numbers): ");
         desiredMobile = scan.nextInt();
         // X
         if (Integer.toString(desiredMobile).equalsIgnoreCase("x"))
            break;
         if ( Auth.getInstance().validateMobile(desiredMobile) )
            correctMobile = true;
      } while (!correctMobile);

      if(correctMobile){
         user.setMobileNo(desiredMobile);
      }
      showProfile();
   }


   /**
    * @since 0.6
    * Admin can see all users list
    */
   public static void showUsersMenu(){

      Auth auth = Auth.getInstance();
      if( auth.getLoggedUser().getRole() != UserStates.ADMIN ){
         return;
      }

      boolean is = false;
      Scanner scan = new Scanner(System.in);
      String userReply = "";

      do{
         if( auth.getUserList().size() == 0 ) {
            System.out.println("There's no users in your DB. \nz - to Main menu");
         } else {
            System.out.println("z - to Main menu");
         }
         userReply = scan.nextLine().trim();
         if( userReply.equalsIgnoreCase("z") ){
            is = true;
         }

      } while(!is) ;
   }

   public static List<Integer> getUserIdsWithBooks() {
      List<Integer> userListWithBooks = new ArrayList<>();
      List<Book> bookList = BookFunctions.getBookList() ;
      for( Book b : bookList ){
         if( b.getTakenBy() != 0 ){
            userListWithBooks.add(b.getTakenBy()) ;
         }
      }
      return userListWithBooks;
   }

   public static void getUsersForAdmin(){
      List<User> userList = Auth.getInstance().getUserList() ;
      List<Book> bookList = BookFunctions.getBookList() ;

      for( User u : userList ){

         System.out.print( u );

         for( Book b : bookList ){
            if( u.getId() == b.getTakenBy() ){
               System.out.println( "  •— GOT BOOK " + b.getISBN() + ". \"" + b.getTitle() + "\" (" + b.getAuthor() + ")  <" + b.getTakenAt() + ">" );
            }
         }
      }
   }
}