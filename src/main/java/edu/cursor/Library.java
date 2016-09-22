package edu.cursor;

import java.util.List;

public class Library {

   private static boolean userIsLogged = false ;

   public static boolean getUserIsLogged() { return userIsLogged; }
   public static void setUserIsLogged(boolean userIsLogged) { Library.userIsLogged = userIsLogged; }


   public void run(){

      LibFunctions func = new LibFunctions() ;
      System.out.println("Library 0.1.\n");

      while (true) {

         func.showMainMenu(userIsLogged);
         char mainAnswer = func.getMain(userIsLogged);

         func.proceedMain(mainAnswer);


//      System.out.println("User is " + getUserIsLogged());
         func.showMainMenu(userIsLogged);
         char loggedAnswer = func.getMain(userIsLogged);
         func.proceedLogged(loggedAnswer);
      }

   }

}