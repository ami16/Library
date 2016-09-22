package edu.cursor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class LibFunctions {

   public void showMainMenu(){
      System.out.println("MENU:\n");
      System.out.println("1. Register");
      System.out.println("2. Login");
      System.out.println("3. View books");
   }

   public char getMain(){
      Scanner scan = new Scanner(System.in) ;
      // Main choice
      char mainChoice = getMainChoice(scan) ;
      return mainChoice;
   }

   public char getMainChoice( Scanner scan ){
      char mainChoice = 0 ;
      boolean is = false ;
      do {
         mainChoice = scan.next().charAt(0) ;
         switch ( mainChoice ){
            case '1' :case '2' :case '3':
               is = true ; break;
            default: System.out.println("Choose correct item (1-3): ");
         }
      } while ( !is ) ;
      return mainChoice ;
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

         default:
            break;
      }
   }

   private void validateUser(){
      Scanner scan = new Scanner(System.in) ;
      List<User> uList = getInitUsers();

      validateUser(scan, 'l');  // login
      validateUser(scan, 'p');  // pass
      Library.setUserIsLogged(true);
   }


   public void validateUser( Scanner scan, char login_pass ){
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
                  if( iterator.next().getLogin().equals( uVar ) ){ is = true ; }
               } else {
                  if( iterator.next().getPass().equals( uVar ) ){ is = true ; }
               }

            }
         }
         if( !is ){ System.out.println( lineOutput ); }
      } while ( !is ) ;
   }


   /* 1. USERS COLLECTION
         Olesya
   */
   private static List<User> uList = new ArrayList<>() ;
   private static List<User> getInitUsers(){
      uList.add( new User("user1", "1234", "mail1@m.c", "name1", 0) ) ;
      uList.add( new User("user2", "2345", "mail2@m.c", "name2", 0) ) ;
      uList.add( new User("user3", "3456", "mail3@m.c", "name3", 0) ) ;
      return uList ;
   }

}