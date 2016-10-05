package edu.cursor;


import org.joda.time.LocalDate;


public class Book {

    private Integer ISBN;
    private String Author;
    private String Title;
    private LocalDate PublYear;
    private LocalDate WritYear;
    private Genre genre;

    public Book(String author, String title, LocalDate publYear, LocalDate writYear, Genre genre) {
        Author = author;
        Title = title;
        PublYear = publYear;
        WritYear = writYear;
        this.genre = genre;
    }

    public Book(Integer ISBN, String author, String title, LocalDate publYear, LocalDate writYear, Genre genre) {
        this.ISBN = ISBN;
        Author = author;
        Title = title;
        PublYear = publYear;
        WritYear = writYear;
        this.genre = genre;
    }

    public Integer getISBN() {
        return ISBN;
    }

    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public LocalDate getPublYear() {
        return PublYear;
    }

    public void setPublYear(LocalDate publYear) {
        PublYear = publYear;
    }

    public LocalDate getWritYear() {
        return WritYear;
    }

    public void setWritYear(LocalDate writYear) {
        WritYear = writYear;
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

        Book book = (Book) o;

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
                ", Author='" + Author + '\'' +
                ", Title='" + Title + '\'' +
                ", PublYear=" + PublYear +
                ", WritYear=" + WritYear +
                ", genre=" + genre +
                '}';
    }
}
