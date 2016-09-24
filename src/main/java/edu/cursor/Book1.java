package edu.cursor;

public class Book1 {

   private String isbn; //10
   private String title;
   private String author;
   private int category;
   private short available;

   /*
   1 Boigraphy
   2 Love stories
   3 Fiction
   4 Tales
   5 Fantasy
    */

   public Book1() {}

   public Book1(String isbn, String title, String author, int category, short available) {
      this.isbn = isbn;
      this.title = title;
      this.author = author;
      this.category = category;
      this.available = available;
   }
   public String getIsbn() { return isbn; }
   public String getTitle() { return title; }
   public String getAuthor() { return author; }
   public int getCategory() { return category; }
   public short isAvailable() { return available; }

   public void setIsbn(String isbn) { this.isbn = isbn; }
   public void setTitle(String title) { this.title = title; }
   public void setAuthor(String author) { this.author = author; }
   public void setCategory(int category) { this.category = category; }
   public void setAvailable(short available) { this.available = available; }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Book1)) return false;

      Book1 book1 = (Book1) o;

      if (getCategory() != book1.getCategory()) return false;
      if (available != book1.available) return false;
      if (!getIsbn().equals(book1.getIsbn())) return false;
      if (!getTitle().equals(book1.getTitle())) return false;
      return getAuthor().equals(book1.getAuthor());
   }

   @Override
   public int hashCode() {
      int result = getIsbn().hashCode();
      result = 31 * result + getTitle().hashCode();
      result = 31 * result + getAuthor().hashCode();
      result = 31 * result + getCategory();
      result = 31 * result + (int) available;
      return result;
   }

//   @Override
//   public String toString() {
//      return "\tBook " + "isbn: " + isbn +
//          ", title: \t" + title +
//          ", (" + author + '\'' +
//          "), \t<" + category + ">, left: \t" + available + "\n" ;
//   }

   @Override
   public String toString() {
      return isbn + "\t'" + title + "' -- " + author + ". \t<" + category + ">  (" + available + ")\n" ;
   }
}