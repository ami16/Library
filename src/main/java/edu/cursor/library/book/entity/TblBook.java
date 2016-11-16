package edu.cursor.library.book.entity;

import lombok.*;
import edu.cursor.library.book.enums.Genre;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDate;

import java.util.Comparator;
import java.util.EnumSet;

@Data
public class TblBook implements Comparable<TblBook> {

    private int ISBN;
    private String author;
    private String title;
    private LocalDate publYear;
    private LocalDate writYear;
    private EnumSet<Genre> genre;

    // ----- COMPARES -----
    @Override
    public int compareTo(TblBook b) {
//        return BookComparator.TITLE.compare(this, b);
        return 0;
    }

    public static class BookComparator {

        public static final Comparator<TblBook> TITLE = (TblBook b1, TblBook b2) -> b1.getTitle().compareTo(b2.getTitle());
        public static final Comparator<TblBook> AUTHOR = (TblBook b1, TblBook b2) -> b1.getAuthor().compareTo(b2.getAuthor());
        public static final Comparator<TblBook> WRITTEN = (TblBook b1, TblBook b2) -> ( b1.getWritYear().compareTo( b2.getWritYear() ) );
        public static final Comparator<TblBook> PUBLISHED = (TblBook b1, TblBook b2) -> (b1.getPublYear().compareTo( b2.getPublYear() ) );

        public static final Comparator<TblBook> TITLE_ = (TblBook b1, TblBook b2) -> TITLE.thenComparing(AUTHOR.thenComparing(WRITTEN.thenComparing(PUBLISHED))).compare(b1, b2);
        public static final Comparator<TblBook> AUTHOR_ = (TblBook b1, TblBook b2) -> AUTHOR.thenComparing(TITLE.thenComparing(WRITTEN.thenComparing(PUBLISHED))).compare(b1, b2);
        public static final Comparator<TblBook> WRITTEN_ = (TblBook b1, TblBook b2) -> WRITTEN.thenComparing(TITLE.thenComparing(AUTHOR.thenComparing(PUBLISHED))).compare(b1, b2);
        public static final Comparator<TblBook> PUBLISHED_ = (TblBook b1, TblBook b2) -> PUBLISHED.thenComparing(TITLE.thenComparing(AUTHOR.thenComparing(WRITTEN))).compare(b1, b2);
    }
}
