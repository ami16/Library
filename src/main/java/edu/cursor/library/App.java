package edu.cursor.library;

import edu.cursor.library.model.BookGenre;
import edu.cursor.library.service.LibraryServiceImpl ;
//import org.apache.log4j.Logger;


public class App {

//   private static final Logger log = Logger.getLogger(App.class);

   public static void main(String[] args) {
      LibraryServiceImpl lib = new LibraryServiceImpl();
      lib.start();
   }

}