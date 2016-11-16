package edu.cursor.library.model;

import java.util.Comparator;

public class BookComparator implements Comparable<TblBook> {

   public BookComparator() {
   }

   @Override
   public int compareTo(TblBook b) {
      return 0;
//      return BookComparator.TITLE.compare(this, b);
   }

   private static final Comparator<TblBook> ISBN = (TblBook b1, TblBook b2)
       -> b1.getISBN().compareTo(b2.getISBN());
   private static final Comparator<TblBook> TITLE = (TblBook b1, TblBook b2)
       -> b1.getTitle().compareTo(b2.getTitle());
   private static final Comparator<TblBook> AUTHOR = (TblBook b1, TblBook b2)
       -> b1.getAuthor().compareTo(b2.getAuthor());
   private static final Comparator<TblBook> WRITTEN = (TblBook b1, TblBook b2)
       -> ( b1.getWritYear().compareTo(b2.getWritYear() ) );
   private static final Comparator<TblBook> PUBLISHED = (TblBook b1, TblBook b2)
       -> (b1.getPublYear().compareTo(b2.getPublYear() ) );

   public static final Comparator<TblBook> ISBN_ = (TblBook b1, TblBook b2)
       -> ISBN.thenComparing(TITLE.thenComparing(AUTHOR.thenComparing(WRITTEN.thenComparing(PUBLISHED))))
       .compare(b1, b2);
   public static final Comparator<TblBook> TITLE_ = (TblBook b1, TblBook b2)
       -> TITLE.thenComparing(AUTHOR.thenComparing(WRITTEN.thenComparing(PUBLISHED))).compare(b1, b2);
   public static final Comparator<TblBook> AUTHOR_ = (TblBook b1, TblBook b2)
       -> AUTHOR.thenComparing(TITLE.thenComparing(WRITTEN.thenComparing(PUBLISHED))).compare(b1, b2);
   public static final Comparator<TblBook> WRITTEN_ = (TblBook b1, TblBook b2)
       -> WRITTEN.thenComparing(TITLE.thenComparing(AUTHOR.thenComparing(PUBLISHED))).compare(b1, b2);
   public static final Comparator<TblBook> PUBLISHED_ = (TblBook b1, TblBook b2)
       -> PUBLISHED.thenComparing(TITLE.thenComparing(AUTHOR.thenComparing(WRITTEN))).compare(b1, b2);

}