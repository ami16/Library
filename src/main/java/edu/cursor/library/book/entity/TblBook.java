package edu.cursor.library.book.entity;


import edu.cursor.library.book.enums.Genre;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDate;

import java.util.EnumSet;


public class TblBook {

    private int ISBN;
    private String author;
    private String title;
    private LocalDate publYear;
    private LocalDate writYear;
    private EnumSet<Genre> genre;

    public TblBook(Integer ISBN, String author, String title, LocalDate publYear, LocalDate writYear, EnumSet<Genre> genre) {
        this.ISBN = ISBN;
        this.author = author;
        this.title = title;
        this.publYear = publYear;
        this.writYear = writYear;
        this.genre = genre;
    }

    public TblBook() {
    }

    public Integer getISBN() {
        return ISBN;
    }

    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublYear() {
        return publYear;
    }

    public void setPublYear(LocalDate publYear) {
        this.publYear = publYear;
    }

    public LocalDate getWritYear() {
        return writYear;
    }

    public void setWritYear(LocalDate writYear) {
        this.writYear = writYear;
    }

    public EnumSet<Genre> getGenre() {
        return genre;
    }

    public void setGenre(EnumSet<Genre> genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TblBook tblBook = (TblBook) o;

        return new EqualsBuilder()
                .append(getISBN(), tblBook.getISBN())
                .append(getAuthor(), tblBook.getAuthor())
                .append(getTitle(), tblBook.getTitle())
                .append(getPublYear(), tblBook.getPublYear())
                .append(getWritYear(), tblBook.getWritYear())
                .append(getGenre(), tblBook.getGenre())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getISBN())
                .append(getAuthor())
                .append(getTitle())
                .append(getPublYear())
                .append(getWritYear())
                .append(getGenre())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,
                ToStringStyle.SHORT_PREFIX_STYLE)
                .append("ISBN", ISBN)
                .append("Author", author)
                .append("Title", title)
                .append("Published", publYear)
                .append("Written", writYear)
                .append("Genre", genre)
                .toString();
    }
}
