package edu.cursor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class LibFunctions {

   public void showMainMenu( boolean userIsLogged ){
      System.out.println("MENU:\n");
      if( userIsLogged ){
         System.out.println("Hello, " + Library.getLoggedUser() + "! ");
         System.out.println("1. View library books");
         System.out.println("2. My books");
         System.out.println("z. LOG OUT");
         System.out.println("x. EXIT");
      } else {
         // TEST for loggedUser
//         System.out.println("Hello, " + Library.getLoggedUser() + "! ");
         System.out.println("1. Register");
         System.out.println("2. Login");
         System.out.println("3. View library books");
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
            case 'z': is = true; logOut(); showMainMenu( Library.getUserIsLogged() );
            default: System.out.println("Choose correct item (1-2, z or x): ");
         }
      } while ( !is ) ;
      return loggedChoice ;
   }



   public void proceedMain( char answer ){
      switch(answer){

         case '1': // Register

            break;

         case '2': // Login
            validateUser();
            break;


         case '3': // View books

            break;
      }
   }


   public void registerUser(){
      
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



   private void logOut(){
      Library.setLoggedUser("");
      Library.setUserIsLogged(false);
   }


   // 1. USERS LIST. Olesya
   private static List<User> uList = new ArrayList<>();
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