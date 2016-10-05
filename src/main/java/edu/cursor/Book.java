package edu.cursor;

import java.util.*;
//import java.time.LocalDate;
import org.joda.time.LocalDate;

public class Book implements Comparable<Book> {

   private Integer ISBN;
   private String title;
   private String author;
   private BookCategories category;
   private int publishedYear;
   private int writtenYear;
   private Integer quantity;
   private BookStates bookState;
   private int takenBy; // User id
   private LocalDate takenAt ;


   public Book(Integer ISBN, String title, String author, BookCategories category, int publYear, int writYear, Integer quantity, BookStates bookState) {
      this.ISBN = ISBN;
      this.title = title;
      this.author = author;
      this.category = category;
      this.publishedYear = publYear;
      this.writtenYear = writYear;
      this.quantity = quantity;
      this.bookState = bookState;
   }

   public Integer getISBN() {
      return ISBN;
   }

   public String getTitle() {
      return title;
   }

   public String getAuthor() {
      return author;
   }

   public int getPublishedYear() {
      return publishedYear;
   }

   public int getWrittenYear() {
      return writtenYear;
   }

   public Integer getQuantity() {
      return quantity;
   }

   public BookCategories getCategory() {
      return category;
   }

   public BookStates getBookState() {
      return bookState;
   }

   public int getTakenBy() {
      return takenBy;
   }

   public LocalDate getTakenAt() {
      return takenAt;
   }


   public void setISBN(Integer ISBN) {
      this.ISBN = ISBN;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public void setPublishedYear(int publishedYear) {
      this.publishedYear = publishedYear;
   }

   public void setWrittenYear(int writtenYear) {
      this.writtenYear = writtenYear;
   }

   public void setQuantity(Integer quantity) {
      this.quantity = quantity;
   }

   public void setCategory(BookCategories category) {
      this.category = category;
   }

   public void setBookState(BookStates bookState) {
      this.bookState = bookState;
   }

   public void setTakenBy(int takenBy) {
      this.takenBy = takenBy;
   }

   public void setTakenAt(LocalDate takenAt) {
      this.takenAt = takenAt;
   }

   @Override
   public String toString() {
      String taken_by = "", taken_at = "";
      if(takenBy != 0) {
         taken_by = "<" + takenBy + ">" ;
      }
      if(takenAt != null) {
         taken_at = " {" + takenAt + "}" ;
      }
      return ISBN + "\t'" + title + "' -- " + author + " (" + getCategory() + ") " + publishedYear + "/" + writtenYear + " (" + getQuantity() + ") " + taken_by  + taken_at + "\n" ;
   }

   public Book getBook(Integer ISBN) {
      Book book = null;
      Iterator<Book> itr = BookFunctions.bookList.iterator();
      while (itr.hasNext()) {
         book = itr.next();
         if (book.getISBN().equals(ISBN)) {
            return book;
         }
      }
      return book;
   }

   public static List<Book> getUserBooks( int userId ) {
      List<Book> userBooks = new ArrayList<>();

      if(BookFunctions.bookList.size() == 0) {
         return userBooks;
      }

      for( Book book : BookFunctions.bookList ){
         if( book.getTakenBy() == userId ){
            userBooks.add( book ) ;
         }
      }
      return userBooks ;
   }

   public static void showUserBooks( int userId ) {
      System.out.println("--- My books here ---");
      System.out.println( getUserBooks( userId ) );
      System.out.println("---------------------");
   }

   // ----- COMPARES -----
   @Override
   public int compareTo(Book b) {
      return Comparators.TITLE.compare(this, b);
   }

   public static class Comparators {

      public static final Comparator<Book> TITLE = (Book b1, Book b2) -> b1.getTitle().compareTo(b2.getTitle());
      public static final Comparator<Book> AUTHOR = (Book b1, Book b2) -> b1.getAuthor().compareTo(b2.getAuthor());
      public static final Comparator<Book> WRITTEN = (Book b1, Book b2) -> (b1.getWrittenYear() - b2.getWrittenYear());
      public static final Comparator<Book> PUBLISHED = (Book b1, Book b2) -> (b1.getPublishedYear() - b2.getPublishedYear());

      public static final Comparator<Book> TITLE_ = (Book b1, Book b2) -> TITLE.thenComparing(AUTHOR.thenComparing(WRITTEN.thenComparing(PUBLISHED))).compare(b1, b2);
      public static final Comparator<Book> AUTHOR_ = (Book b1, Book b2) -> AUTHOR.thenComparing(TITLE.thenComparing(WRITTEN.thenComparing(PUBLISHED))).compare(b1, b2);
      public static final Comparator<Book> WRITTEN_ = (Book b1, Book b2) -> WRITTEN.thenComparing(TITLE.thenComparing(AUTHOR.thenComparing(PUBLISHED))).compare(b1, b2);
      public static final Comparator<Book> PUBLISHED_ = (Book b1, Book b2) -> PUBLISHED.thenComparing(TITLE.thenComparing(AUTHOR.thenComparing(WRITTEN))).compare(b1, b2);
   }

}