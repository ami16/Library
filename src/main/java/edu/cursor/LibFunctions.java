package edu.cursor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class LibFunctions {

   private static List<User> uList = new ArrayList<>();

   public LibFunctions() {
      uList = createUserList() ;
   }

   public void showMainMenu(boolean userIsLogged ){
      System.out.println("MENU:\n");
      if( userIsLogged ){
         System.out.println("Hello, " + Library.getLoggedUser() + "! ");
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
            case 'z': is = true ; logOut();
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
            case 'z': is = true; logOut();
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

            break;
      }
   }





   // 1 - REGISTER USER

   public void registerUser(){
      Scanner scan = new Scanner(System.in) ;
      String desiredLogin ;
      boolean loginAllowed = false ;

      // LOGIN
      do{
         System.out.println("Please enter your nickname for LOGIN (4-20 long, no spec chars at begin & the end): ");
         desiredLogin = scan.nextLine().trim() ;
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
                  if( validatePass( pass1 ) )
                     correctPass = true ;
               } while( !correctPass ) ;
               do{
                  System.out.println("Repeat pass ones more:");
                  String pass2 = scan.nextLine().trim() ;

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
                  if( validateName( desiredName ) )
                     correctName = true ;
               } while( !correctName ) ;



               uList.add( new User(desiredLogin, pass1, desiredMail, desiredName, 0) ) ;

               Library.setUserIsLogged(true);
               Library.setLoggedUser(desiredLogin);
               System.out.println("Congrats! You're in!");

            }

         } else {
            System.out.println("Not allowed");
         }
      } while( !loginAllowed ) ;


   }




   private void validateUser(){
      // generate initial users list
      List<User> uList = createUserList() ;

      validateUser('l');  // login
      validateUser('p');  // pass
      Library.setUserIsLogged(true);
   }


   public void validateUser( char login_pass ){
      Scanner scan = new Scanner(System.in) ;
      String uVar ;
      boolean is = false ;
      String lineInput = login_pass == 'l' ? "Enter your login:" : "Enter your password:";
      String lineOutput = login_pass == 'l' ? "No such login (2)" : "Wrong password (2)";

      System.out.println( lineInput );

      do {
         uVar = scan.nextLine().trim() ;
         if(uVar.equals("")){
            is = false ; System.out.print("Enter smth: ");
         } else {
            Iterator<User> iterator = uList.iterator();

            while(iterator.hasNext()){
               if(login_pass == 'l'){
                  if( iterator.next().getLogin().equals( uVar ) ){ is = true ; Library.setLoggedUser( uVar ); }
               } else {
                  if( iterator.next().getPass().equals( uVar ) ){ is = true ; }
               }
            }
         }
         if( !is ){ System.out.println( lineOutput ); }
      } while ( !is ) ;
   }



   public void proceedLogged( char answer ){
      switch(answer){

         case '1': // View LIBRARY

            break;

         case '2': // My books

            break;


         case 'z': // Log out

            break;

         case 'x': // EXIT

            break;

         default: break;
      }
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
//      return ( val.length() > 1) ;
      return val.matches("^[a-zA-Z0-9_-]{2,16}$") ;
   }

   public boolean loginAvailable( String desiredLogin ){
      boolean loginAvailable = false ;

//      System.out.println( "uList.size: " + uList.size() );
      Iterator<User> iterator = uList.iterator();
//      System.out.println( "List Login: " + iterator.next().getLogin() );
      while(iterator.hasNext()){
         if( iterator.next().getLogin().equals( desiredLogin ) ){
            loginAvailable = false ;
            System.out.println("Login NOT available.");
            break;
         } else {
            loginAvailable = true ;
         }
      }


      return loginAvailable ;
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

   public void sayBye(){ System.out.println("See ya."); }

}