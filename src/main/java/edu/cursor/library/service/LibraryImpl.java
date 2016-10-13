package edu.cursor.library.service;

import edu.cursor.library.security.AuthImpl;
import edu.cursor.library.security.CredentialsImpl;
import edu.cursor.library.user.entity.TblUser;
import edu.cursor.library.user.enums.Role;

import java.util.Scanner;

public class LibraryImpl implements Library {

   public LibraryImpl() { }

   @Override
   public void start() {
      System.out.println("Lib started");
      AuthImpl auth = AuthImpl.getInstance() ;
      CredentialsImpl cred = CredentialsImpl.getInstance() ;

      while(true){
         showMainMenu(auth.isUserLogged(), auth);
      }

   }

   @Override
   public void showMainMenu(boolean userIsLogged, AuthImpl auth) {
      System.out.println("MENU:\n");

      if (userIsLogged) {
         TblUser user = auth.getLoggedUser();
         System.out.println("userISLogged: " + auth.isUserLogged() );
         System.out.println("loggedUser: " + user.geteMail() + " (id: " + user.getId() +  ")" );
         System.out.println("Hello, " + user.getFirstName());
         System.out.println("----------------------------------------------------------");

         if(auth.getLoggedUser().getRole() == Role.ADMIN){
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

   public void replyReader(int type){
      Scanner scan = new Scanner(System.in);
      boolean is = false;
      do {
         switch (type) {

            case 0: // not logged in
               switch (Character.toLowerCase(scan.next().charAt(0))) {
                  case '1':
                     // -- REGISTER
                     is = true;
                     break;
                  case '2':
                     // -- LOGIN
                     is = true;
                     break;
                  case '3':
                     // -- VIEW LIBRARY BOOKS
                     is = true;
                     break;
                  case 'x':
                     // -- Exit
                     sayBye();
                     break;
                  default:
                     System.out.println("Choose correct item (1-3 or x): ");
               }
               break;

            case 1: // simple logged in
               switch (Character.toLowerCase(scan.next().charAt(0))) {
                  case '1':
                     // -- VIEW LIBRARY BOOKS
                     is = true;
                     break;
                  case '2':
                     // -- MY BOOKS
                     is = true;
                     break;
                  case '3':
                     // -- My profile
                     is = true;
                     break;
                  case 'z':
                     // -- LOG OUT
                     is = true;
                     break;
                  case 'x':
                     // -- Exit
                     sayBye();
                     break;
                  default:
                     System.out.println("Choose correct item (1-3, x or z): ");
               }
               break;

            case 2: // logged in ADMIN
               switch (Character.toLowerCase(scan.next().charAt(0))) {
                  case '1':
                     // -- VIEW LIBRARY BOOKS
                     is = true;
                     break;
                  case '2':
                     // -- MY BOOKS
                     is = true;
                     break;
                  case '3':
                     // -- My profile
                     is = true;
                     break;
                  case '4':
                     // -- View users list
                     is = true;
                     break;
                  case 'z':
                     // -- LOG OUT
                     is = true;
                     break;
                  case 'x':
                     // -- Exit
                     sayBye();
                     break;
                  default:
                     System.out.println("Choose correct item (1-3, x or z): ");
               }
               break;

            default:
               replyReader(0) ;
         }
      } while(!is) ;
   }


   public void sayBye() {
      System.out.println("See ya.");
      System.exit(0);
   }
}