package edu.cursor;

import java.util.List;

public class Library {

   private static boolean userIsLogged = false ;
   private static String loggedUser ;

   public static boolean getUserIsLogged() { return userIsLogged; }
   public static void setUserIsLogged(boolean userIsLogged) { Library.userIsLogged = userIsLogged; }

   public static String getLoggedUser() { return loggedUser; }
   public static void setLoggedUser(String loggedUser) { Library.loggedUser = loggedUser; }

   public void run(){

      LibFunctions func = new LibFunctions() ;
      System.out.println("Library 0.1.\n");

      while (true) {

         func.showMainMenu(userIsLogged);
         char mainAnswer = func.getMain(userIsLogged);

//         boolean processedMain = func.proceedMain(mainAnswer);
         func.proceedMain(mainAnswer);

         System.out.println("here");

      }

   }

}