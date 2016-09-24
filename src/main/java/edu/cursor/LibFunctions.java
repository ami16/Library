package edu.cursor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class LibFunctions {

   private static List<User> uList = new ArrayList<>();
   private static List<Book1> bList = new ArrayList<>();

   public LibFunctions() {
      uList = createUserList() ;
      bList = createBookList() ;
   }

   public void showMainMenu(boolean userIsLogged ){
      System.out.println("MENU:\n");

//      System.out.println("userISLogged: " + Library.getUserIsLogged() );
//      System.out.println("loggedUser: " + Library.getLoggedUser() );
//      System.out.println( uList.toString() );

      if( userIsLogged ){

         User user = getUser(Library.getLoggedUser()) ;
         System.out.println("Hello, " + user.getName() + " (" + user.getLogin() + ") <" +  user.getEmail() + "> ! ");
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

   public char getMain( boolean userIsLogged ){
      // Main or logged choice
      char choice ;
      if( userIsLogged ){
         choice = getLoggedChoice() ;
      } else {
         choice = getMainChoice() ;
      }
      return choice;
   }

   public char getMainChoice(){
      Scanner scan = new Scanner(System.in) ;
      char mainChoice = 0 ;
      boolean is = false ;
      do {
         mainChoice = scan.next().charAt(0) ;
         switch ( Character.toLowerCase(mainChoice) ){
            case '1' :case '2' :case '3':
               is = true ; break;
            case 'x': is = true ; sayBye(); System.exit(0); break;
            case 'z': is = true ; logOut(); break;
            default: System.out.println("Choose correct item (1-3 or x): ");
         }
      } while ( !is ) ;
      return mainChoice ;
   }

   public char getLoggedChoice(){
      Scanner scan = new Scanner(System.in) ;
      char loggedChoice = 0 ;
      boolean is = false ;
      do {
         loggedChoice = scan.next().charAt(0) ;
         switch ( Character.toLowerCase(loggedChoice) ){
            case '1' :case '2' : is = true ; break;
            case 'x': is = true ; sayBye(); System.exit(0); break;
            case 'z': is = true; logOut(); break;
            default: System.out.println("Choose correct item (1-2, z or x): ");
         }
      } while ( !is ) ;
      return loggedChoice ;
   }



   public void proceedMain( char answer ){
      switch(answer){

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


   public void proceedLogged( char answer ){
      switch(answer){

         case '1': // View LIBRARY
            System.out.println("View LIBRARY");
            break;

         case '2': // My books
            System.out.println("My books");
            break;


         case 'z': // Log out
//            logOut();
            break;

         case 'x': // EXIT
            System.out.println("EXIT");
            break;

         default: break;
      }
   }




   // 1 - REGISTER USER

   public void registerUser(){
      Scanner scan = new Scanner(System.in) ;
      String desiredLogin ;
      boolean loginAllowed = false ;

      // LOGIN
      outer:
      do{
         System.out.println("Please enter your nickname for LOGIN (4-20 long, no spec chars at begin & the end): ");
         desiredLogin = scan.nextLine().trim() ;
         // X
         if(desiredLogin.equalsIgnoreCase("x")) break;
         if( validateLogin( desiredLogin )  ){
            loginAllowed = true ;

            if( !loginAvailable( desiredLogin ) ){
               loginAllowed = false ;
            } else {


               // LOGIN ok.
               // Now PASS
               boolean correctPass = false ;
               boolean equalPass = false ;
               String pass1 ;
               System.out.print("OKAY NOW! Enter your password. ");
               do {
                  System.out.println("Password must be at least 4 chars:");
                  pass1 = scan.nextLine().trim();
                  // X
                  if(pass1.equalsIgnoreCase("x")) break outer;
                  if( validatePass( pass1 ) )
                     correctPass = true ;
               } while( !correctPass ) ;
               do{
                  System.out.println("Repeat pass ones more:");
                  String pass2 = scan.nextLine().trim() ;

                  // X
                  if(pass2.equalsIgnoreCase("x")) break outer;
                  if( pass1.equals( pass2 ) ) {
                     equalPass = true;
                  } else { System.out.println("Your passwords doesn't match."); }
               } while( !equalPass ) ;



               // PASS ok.
               // Now MAIL
               boolean correctMail = false ;
               String desiredMail ;
               do {
                  System.out.print("Pls give your valid email: ");
                  desiredMail = scan.nextLine().trim();
                  // X
                  if(desiredMail.equalsIgnoreCase("x")) break outer;
                  if( validateMail( desiredMail ) )
                     correctMail = true ;
               } while( !correctMail ) ;


               // MAIL ok.
               // Now NAME
               boolean correctName = false ;
               String desiredName ;
               do {
                  System.out.print("And now pls provide your name: ");
                  desiredName = scan.nextLine().trim();
                  // X
                  if(desiredName.equalsIgnoreCase("x")) break outer;
                  if( validateName( desiredName ) )
                     correctName = true ;
               } while( !correctName ) ;



               uList.add( new User(desiredLogin, pass1, desiredMail, desiredName, 0) ) ;
               System.out.println("Now login using your credentials");

            }

         } else {
            System.out.println("Not allowed");
         }
      } while( !loginAllowed ) ;


   }



   // 2 - LOGIN  <VALIDATE USER>
   private void validateUser(){

      Scanner scan = new Scanner(System.in) ;
      String uLog, uPass, verifiedLog = "" ;
      boolean is = false ;

      System.out.println( "Enter your login:" );
      // LOGIN
      do {
         uLog = scan.nextLine().trim() ;
         if(uLog.equals("")){
            System.out.print("Enter smth: ");
         }
         else {
            is = userExists( uLog ) ;
            if( is )
               verifiedLog = uLog ;
         }
         if( !is ){ System.out.println( "No such login (3)" ); }
      }
      while ( !is ) ;


      // PASS
      System.out.println( "Enter your pass:" );
      do {
         uPass = scan.nextLine().trim() ;
         if(uPass.equals("")){
            System.out.print("Enter smth: ");
         }
         else {
//            System.out.println("uLog: " + uLog + ", uPass: " + uPass);
            is = passCorrect( verifiedLog, uPass ) ;
            if( is ) logIn( uLog );
         }
         if( !is ){ System.out.println( "Wrong password (3)" ); }
      }
      while ( !is ) ;

   }





   public boolean validateLogin( String val ){
      // http://stackoverflow.com/questions/12018245/regular-expression-to-validate-username
      return val.matches("^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$") ;
   }

   public boolean validatePass( String val ){
      return ( val.length() >= 4) ;
   }

   public boolean validateMail( String val ){
      return val.matches("^(.+)@(.+)$") ;
   }

   public boolean validateName( String val ){
      return val.matches("^[a-zA-Z0-9_-]{2,16}$") ;
   }

   public boolean loginAvailable( String desiredLogin ){
      boolean loginAvailable = false ;
      Iterator<User> iterator = uList.iterator();
      while(iterator.hasNext()){
         User user = iterator.next() ;
         if( user.getLogin().equals( desiredLogin ) ){
            loginAvailable = false ;
            System.out.println("Login NOT available.");
            break;
         } else {
            loginAvailable = true ;
         }
      }
      return loginAvailable ;
   }

   private User getUser( String login ){
      Iterator<User> iterator = uList.iterator();
      while(iterator.hasNext()){
         User user = iterator.next() ;
         if( user.getLogin().equals( login ) ){
            return user ;
         }
      }
      return iterator.next() ;
   }
   private boolean userExists( String login ){
      Iterator<User> iterator = uList.iterator();
      while(iterator.hasNext()){
         User user = iterator.next() ;
         if( user.getLogin().equals( login ) ){
//            System.out.println("user.getLogin().equals( login ): " + user.getLogin().equals( login ) + ", LOGIN: " + login);
            return true ;
         }
      } return false ;
   }
   private boolean passCorrect( String login, String pass ){
      Iterator<User> iterator = uList.iterator();
      while(iterator.hasNext()){
         User user = iterator.next() ;
         if( user.getLogin().equals(login) && user.getPass().equals(pass)  ){
            return true ;
         }
      } return false ;
   }

   private void logIn( String login ){
      Library.setLoggedUser(login);
      Library.setUserIsLogged(true);
   }
   private void logOut(){
      Library.setLoggedUser("");
      Library.setUserIsLogged(false);
   }

   // 1. USERS LIST. Olesya
   private static List<User> createUserList(){
      uList.add( new User("peter_griffin", "peter1234", "peter@gmail.com", "Peter", 0));
      uList.add( new User("lois", "lois1234", "lois@gmail.com", "Lois", 0));
      uList.add( new User("stewie_best", "coolStew", "stewie@gmail.com", "Stewie", 0 ));
      uList.add( new User("surfin_bird", "pticasinica25", "sufrin_bird@gmail.com", "Bird", 0 ));
      uList.add( new User("user1", "1234", "mail1@m.c", "name1", 0) ) ;
      uList.add( new User("user2", "2345", "mail2@m.c", "name2", 0) ) ;
      uList.add( new User("user3", "3456", "mail3@m.c", "name3", 0) ) ;
      return uList ;
   }


   private static List<Book1> createBookList(){
      /*
         1 Boigraphy
         2 Love stories
         3 Fiction +
         4 Tales
         5 Fantasy
    */

      bList.add( new Book1("0000000005", "Mothers, Tell Your Daughters", "Bonnie Jo Campbell", 1, (short) 1));
      bList.add( new Book1("0000000009", "Elon Musk: Tesla, SpaceX And Fantastic Future", "Ashlee Vance", 1, (short) 1));
      bList.add( new Book1("0000000015", "The Jemima Code", "Toni Tipton-Martin", 1, (short) 1));
      bList.add( new Book1("0000000016", "The Wright Brothers", "David McCullough", 1, (short) 1));
      bList.add( new Book1("0000000021", "Fortune Smiles", "Adam Johnson", 1, (short) 1));
      bList.add( new Book1("0000000031", "The Fifth Season", "N.K. Jemisin", 1, (short) 1));
      bList.add( new Book1("0000000032", "Nimona", "Noelle Stevenson", 1, (short) 1));
      bList.add( new Book1("0000000037", "Real Men Don't Sing", "Allison McCracken", 1, (short) 1));

      bList.add( new Book1("0000000002", "Accidental Saints", "Nadia Bolz-Weber", 2, (short) 1));
      bList.add( new Book1("0000000007", "Undermajordomo Minor", "Patrick deWitt", 2, (short) 1));
      bList.add( new Book1("0000000010", "Adventures Of Lovelace And Babbage", "Sydney Padua", 2, (short) 1));
      bList.add( new Book1("0000000019", "The Bear Ate Your Sandwich", "Julia Sarcone-Roach", 2, (short) 1));
      bList.add( new Book1("0000000020", "Among The Wild Mulattos", "Tom Williams", 2, (short) 1));
      bList.add( new Book1("0000000033", "The Sellout", "Paul Beatty", 2, (short) 1));
      bList.add( new Book1("0000000036", "Fates And Furies", "Lauren Groff", 2, (short) 1));

      bList.add( new Book1("0000000003", "Slade House", "David Mitchell", 3, (short) 1));
      bList.add( new Book1("0000000004", "Spy Games", "Adam Brookes", 3, (short) 1));
      bList.add( new Book1("0000000012", "Strangers Drowning", "Larissa MacFarquhar", 3, (short) 1));
      bList.add( new Book1("0000000013", "Honeydew", "Edith Pearlman", 3, (short) 1));
      bList.add( new Book1("0000000008", "All The Old Knives", "Olen Steinhauer", 3, (short) 1));
      bList.add( new Book1("0000000014", "The Unauthorised Life", "Jonathan Bate", 3, (short) 1));
      bList.add( new Book1("0000000023", "Habitat", "Lauren Liess", 3, (short) 1));
      bList.add( new Book1("0000000026", "Death And Mr. Pickwick", "Stephen Jarvis", 3, (short) 1));

      bList.add( new Book1("0000000011", "Small Plates To Share", "Ghillie Başan", 4, (short) 1));
      bList.add( new Book1("0000000001", "Peter Pan", "Author The First", 4, (short) 1));
      bList.add( new Book1("0000000018", "The Turnip Princess", "Franz-Xaver von Schönwerth", 4, (short) 1));
      bList.add( new Book1("0000000022", "A History of Japan", "Shigeru Mizuki", 4, (short) 1));
      bList.add( new Book1("0000000024", "A Spool Of Blue Thread", "Anne Tyler", 4, (short) 1));
      bList.add( new Book1("0000000034", "Hotels of North America", "Rick Moody", 4, (short) 1));
      bList.add( new Book1("0000000035", "Kitchen Hacks", "America's Test Kitchen", 4, (short) 1));

      bList.add( new Book1("0000000006", "Ancillary Mercy", "Ann Leckie", 5, (short) 1));
      bList.add( new Book1("0000000017", "Secrets Of State", "Matthew Palmer", 5, (short) 1));
      bList.add( new Book1("0000000025", "March", "Andrew Aydin", 5, (short) 1));
      bList.add( new Book1("0000000027", "Home", "Carson Ellis", 5, (short) 1));
      bList.add( new Book1("0000000028", "Listen, Slowly", "Thanhhà Lại", 5, (short) 1));
      bList.add( new Book1("0000000029", "Murder On Steep Street", "Heda Margolius Kovály", 5, (short) 1));
      bList.add( new Book1("0000000030", "The Shepherd's Crown", "Terry Pratchett", 5, (short) 1));

      return bList ;
   }

   public void getLibraryBooks(){
      System.out.println("\tisbn\t|\t\ttitle\t\t\t\t\t|\tauthor\t\t| <category> | (available)");
      System.out.println("--------------------------------------------------------------------------------------------");
      System.out.println( bList.toString() );
   }

   public void sayBye(){ System.out.println("See ya."); }

}