package edu.cursor.library.service;

import edu.cursor.library.security.AuthImpl;
import edu.cursor.library.security.CredentialImpl;
import edu.cursor.library.user.entity.TblUser;

public class LibraryImpl implements Library {

   public LibraryImpl() { }

   @Override
   public void start() {
      System.out.println("Lib started");
      AuthImpl auth = AuthImpl.getInstance() ;
      CredentialImpl cred = CredentialImpl.getInstance() ;

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

         if(auth.getLoggedUser().getRole() == UserStates.ADMIN){
            showMainMenuAdmin();
         } else {
            showMainMenuLogged();
         }
      } else {
         showMainMenuSimple();
      }
   }

   @Override
   public void showMainMenuLogged() {
      // Regular logged user see this
      System.out.println("1. View library books");
      System.out.println("2. My books");
      System.out.println("3. My profile");
      System.out.println("z. Log out");
      System.out.println("x. Exit");
   }

   @Override
   public void showMainMenuAdmin() {
      // Logged == ADMIN == see this
      System.out.println("1. View library books");
      System.out.println("2. My books");
      System.out.println("3. My profile");
      System.out.println("4. View users list");
      System.out.println("z. Log out");
      System.out.println("x. Exit");
   }

   @Override
   public void showMainMenuSimple() {
      System.out.println("1. REGISTER");
      System.out.println("2. LOGIN");
      System.out.println("3. VIEW LIBRARY BOOKS");
      System.out.println("x. EXIT");
   }
}