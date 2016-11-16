package edu.cursor.library.ui;

import edu.cursor.library.model.TblBook;


public class BookPrinterTabbed implements BookPrinter {

   @Override
   public String accept(TblBook b){
      return b.getISBN() + " \t "
          + b.getAuthor() + " \t "
          + b.getTitle() + " \t "
          + b.getPublYear() + " \t "
          + b.getWritYear() + " \t "
          + b.getGenre() + " \t "
          ;
   }
}