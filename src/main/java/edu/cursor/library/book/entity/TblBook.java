package edu.cursor.library.book.entity;


import edu.cursor.library.book.enums.Genre;
import org.joda.time.LocalDate;


public class TblBook {

    private int ISBN;
    private String author;
    private String title;
    private LocalDate publYear;
    private LocalDate writYear;
    private Genre genre;

    public TblBook(Integer ISBN, String author, String title, LocalDate publYear, LocalDate writYear, Genre genre) {
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
        if (o == null || getClass() != o.getClass()) return false;

        TblBook book = (TblBook) o;

        if (getISBN() != null ? !getISBN().equals(book.getISBN()) : book.getISBN() != null) return false;
        if (getAuthor() != null ? !getAuthor().equals(book.getAuthor()) : book.getAuthor() != null) return false;
        if (getTitle() != null ? !getTitle().equals(book.getTitle()) : book.getTitle() != null) return false;
        if (getPublYear() != null ? !getPublYear().equals(book.getPublYear()) : book.getPublYear() != null)
            return false;
        if (getWritYear() != null ? !getWritYear().equals(book.getWritYear()) : book.getWritYear() != null)
            return false;
        return getGenre() == book.getGenre();

    }

    @Override
    public int hashCode() {
        int result = getISBN() != null ? getISBN().hashCode() : 0;
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getPublYear() != null ? getPublYear().hashCode() : 0);
        result = 31 * result + (getWritYear() != null ? getWritYear().hashCode() : 0);
        result = 31 * result + (getGenre() != null ? getGenre().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN=" + ISBN +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", publYear=" + publYear +
                ", writYear=" + writYear +
                ", genre=" + genre +
                '}';
    }
}
