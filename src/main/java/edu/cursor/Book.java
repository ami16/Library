package edu.cursor;

import java.util.*;

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

   public static List<Book> bookList = new ArrayList<>();

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

   public static List<Book> getBookList() {
      return bookList;
   }

   public BookStates getBookState() {
      return bookState;
   }

   public int getTakenBy() {
      return takenBy;
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

   public static void setBookList(List<Book> bookList) {
      Book.bookList = bookList;
   }

   public void setBookState(BookStates bookState) {
      this.bookState = bookState;
   }

   public void setTakenBy(int takenBy) {
      this.takenBy = takenBy;
   }

   @Override
   public String toString() {
      String taken = "";
      if(takenBy != 0) {
         taken = "<" + takenBy + ">" ;
      }
      return ISBN + "\t'" + title + "' -- " + author + " (" + getCategory() + ") " + publishedYear + "/" + writtenYear + " (" + getQuantity() + ") " + taken  + "\n" ;
   }

   public Book getBook(Integer ISBN) {
      Book book = null;
      Iterator<Book> itr = bookList.iterator();
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

      if(bookList.size() == 0) {
         return userBooks;
      }

      for( Book book : bookList ){
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


   public static List<Book> createBookList() {

      bookList.add(new Book(1000000005, "Mothers, Tell Your Daughters", "Bonnie Jo Campbell", BookCategories.BIOGRAPHY, 2015, 1980, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000009, "Elon Musk: Tesla, SpaceX & Fantastic Future", "Ashlee Vance", BookCategories.BIOGRAPHY, 2015, 2014, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000015, "The Jemima Code", "Toni Tipton-Martin", BookCategories.BIOGRAPHY, 2000, 1992, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000016, "The Wright Brothers", "David McCullough", BookCategories.BIOGRAPHY, 2016, 1897, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000021, "Fortune Smiles", "Adam Johnson", BookCategories.BIOGRAPHY, 2007, 1943, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000031, "The Fifth Season", "N.K. Jemisin", BookCategories.BIOGRAPHY, 2015, 2000, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000032, "Nimona", "Noelle Stevenson", BookCategories.BIOGRAPHY, 2011, 1993, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000037, "Real Men Don't Sing", "Allison McCracken", BookCategories.BIOGRAPHY, 2016, 2011, 1, BookStates.AVAILBALE));

      bookList.add(new Book(1000000002, "Accidental Saints", "Nadia Bolz-Weber", BookCategories.LOVE_STORY, 2015, 1914, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000007, "Undermajordomo Minor", "Patrick deWitt", BookCategories.LOVE_STORY, 2000, 1943, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000010, "Adventures Of Lovelace And Babbage", "Sydney Padua", BookCategories.LOVE_STORY, 2001, 1951, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000019, "The Bear Ate Your Sandwich", "Julia Sarcone-Roach", BookCategories.LOVE_STORY, 2001, 1898, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000020, "Among The Wild Mulattos", "Tom Williams", BookCategories.LOVE_STORY, 2013, 1991, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000033, "The Sellout", "Paul Beatty", BookCategories.LOVE_STORY, 2004, 1897, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000036, "Fates And Furies", "Lauren Groff", BookCategories.LOVE_STORY, 1997, 1880, 1, BookStates.AVAILBALE));

      bookList.add(new Book(1000000003, "Slade House", "David Mitchell", BookCategories.FICTION, 2010, 1950, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000004, "Spy Games", "Adam Brookes", BookCategories.FICTION, 2000, 1949, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000012, "Strangers Drowning", "Larissa MacFarquhar", BookCategories.FICTION, 2003, 1939, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000013, "Honeydew", "Edith Pearlman", BookCategories.FICTION, 2010, 1977, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000008, "All The Old Knives", "Olen Steinhauer", BookCategories.FICTION, 2016, 1913, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000014, "The Unauthorised Life", "Jonathan Bate", BookCategories.FICTION, 2000, 1965, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000023, "Habitat", "Lauren Liess", BookCategories.FICTION, 2010, 1997, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000026, "Death And Mr. Pickwick", "Stephen Jarvis", BookCategories.FICTION, 1999, 1954, 1, BookStates.AVAILBALE));

      bookList.add(new Book(1000000011, "Small Plates To Share", "Ghillie Başan", BookCategories.TALES, 2014, 1898, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000001, "Peter Pan", "Author The First", BookCategories.TALES, 2003, 1918, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000018, "The Turnip Princess", "Franz-Xaver von Schönwerth", BookCategories.TALES, 2014, 1886, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000022, "A History of Japan", "Shigeru Mizuki", BookCategories.TALES, 2007, 2005, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000024, "A Spool Of Blue Thread", "Anne Tyler", BookCategories.TALES, 1999, 1931, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000034, "Hotels of North America", "Rick Moody", BookCategories.TALES, 2010, 1995, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000035, "Kitchen Hacks", "America's Test Kitchen", BookCategories.TALES, 1997, 1905, 1, BookStates.AVAILBALE));

      bookList.add(new Book(1000000006, "Ancillary Mercy", "Ann Leckie", BookCategories.FANTASY, 2001, 1919, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000017, "Secrets Of State", "Matthew Palmer", BookCategories.FANTASY, 2004, 1880, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000025, "March", "Andrew Aydin", BookCategories.FANTASY, 2014, 1995, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000027, "Home", "Carson Ellis", BookCategories.FANTASY, 2012, 1971, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000028, "Listen Slowly", "Thanhhà Lại", BookCategories.FANTASY, 2015, 1881, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000029, "Murder On Steep Street", "Heda Margolius Kovály", BookCategories.FANTASY, 1999, 1962, 1, BookStates.AVAILBALE));
      bookList.add(new Book(1000000030, "The Shepherd's Crown", "Terry Pratchett", BookCategories.FANTASY, 2011, 1934, 1, BookStates.AVAILBALE));

      return bookList;
   }
}