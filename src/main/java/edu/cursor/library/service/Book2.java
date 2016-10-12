package edu.cursor.library.service;

import edu.cursor.library.book.enums.Genre;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;

public class Book2 {

    private int ISBN;
    private String author;
    private String title;
    private LocalDate publYear;
    private LocalDate writYear;
    private Genre genre;

    public Book2() {
        this.ISBN = ISBN;
    }

    public Book2(Integer ISBN, String author, String title, LocalDate publYear, LocalDate writYear, Genre genre) {
        this.ISBN = ISBN;
        this.author = author;
        this.title = title;
        this.publYear = publYear;
        this.writYear = writYear;
        this.genre = genre;
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book2)) return false;

        Book2 book2 = (Book2) o;

        if (getISBN() != book2.getISBN()) return false;
        if (!getAuthor().equals(book2.getAuthor())) return false;
        if (!getTitle().equals(book2.getTitle())) return false;
        if (!getPublYear().equals(book2.getPublYear())) return false;
        if (!getWritYear().equals(book2.getWritYear())) return false;

        return new EqualsBuilder()
            .append(this.getISBN(), book2.getISBN())
            .append(this.getAuthor(), book2.getAuthor())
            .append(this.getTitle(), book2.getTitle())
            .append(this.getPublYear(), book2.getPublYear())
            .append(this.getWritYear(), book2.getWritYear())
            .append(this.getGenre(), book2.getGenre())
            .isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(this.getISBN())
            .append(this.getAuthor())
            .append(this.getTitle())
            .append(this.getPublYear())
            .append(this.getWritYear())
            .append(this.getGenre())
            .toHashCode() ;
    }

    @Override
    public String toString() {
//        return "Book2{" +
//            "ISBN=" + ISBN +
//            ", author='" + author + '\'' +
//            ", title='" + title + '\'' +
//            ", publYear=" + publYear +
//            ", writYear=" + writYear +
//            ", genre=" + genre +
//            '}';
        return new ToStringBuilder(this)
            .append("isbn: ", this.getISBN())
            .append("author: ", this.getAuthor())
            .append("title: ", this.getTitle())
            .append("pYr: ", this.getPublYear())
            .append("wYr: ", this.getWritYear())
            .append("gnr: ", this.getGenre())
            .toString();
    }

    public static void main(String[] args) {
        Book2 book2 = new Book2(777, "Auth77", "Tit77", LocalDate.parse("2017"), LocalDate.parse("1777"), Genre.HISTORY) ;
        System.out.println(book2);

//        System.out.println( new );
    }
}
