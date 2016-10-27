package edu.cursor.library.user.registry.entity;

import java.util.Comparator;

public class TblUserBooksRegistry implements Comparable<TblUserBooksRegistry> {

   private int userId;
   private int bookIsbn;

   public TblUserBooksRegistry() { }

   public TblUserBooksRegistry(Integer userId, Integer bookIsbn) {
      this.userId = userId;
      this.bookIsbn = bookIsbn;
   }

   public int getUserId() {
      return userId;
   }

   public void setUserId(int userId) {
      this.userId = userId;
   }

   public int getBookIsbn() {
      return bookIsbn;
   }

   public void setBookIsbn(int bookIsbn) {
      this.bookIsbn = bookIsbn;
   }

   @Override
   public String toString() {
      return "" +
          "userId=" + userId +
          ", bookIsbn=" + bookIsbn +
          "\n";
   }


   // ----- COMPARES -----
   @Override
   public int compareTo(TblUserBooksRegistry r) {
        return RegistryComparator.USER_ID.compare(this, r) ;
   }

   public static class RegistryComparator {
      public static final Comparator<TblUserBooksRegistry> USER_ID = (TblUserBooksRegistry r1, TblUserBooksRegistry r2) -> r2.userId - r1.getUserId();
   }
}