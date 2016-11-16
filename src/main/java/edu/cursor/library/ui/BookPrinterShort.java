package edu.cursor.library.ui;


import edu.cursor.library.model.TblBook;

public class BookPrinterShort implements BookPrinter {

   @Override
   public String accept(TblBook b) {
      return b.getISBN() + " \t "
          + b.getAuthor() + " \t "
          + b.getTitle() + " \t "
          + b.getWritYear() + " \t "
          + b.getPublYear() + " \t "
          + b.getGenre() + " \t "
          ;
   }
}