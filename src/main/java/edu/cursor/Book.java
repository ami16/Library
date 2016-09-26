package edu.cursor;


import java.time.*;



public class Book {

    private Integer ISBN;
    private String Author;
    private String Title;
    private LocalDate PublYear;
    private LocalDate WritYear;
    private Integer quantity;

    public Book(Integer ISBN, String author, String title, LocalDate publYear, LocalDate writYear, Integer quantity) {
        this.ISBN = ISBN;
        Author = author;
        Title = title;
        PublYear = publYear;
        WritYear = writYear;
        this.quantity = quantity;
    }

    public Book(String author, String title, LocalDate publYear, LocalDate writYear, Integer quantity) {
        Author = author;
        Title = title;
        PublYear = publYear;
        WritYear = writYear;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!getISBN().equals(book.getISBN())) return false;
        if (!getAuthor().equals(book.getAuthor())) return false;
        if (!getTitle().equals(book.getTitle())) return false;
        if (!getPublYear().equals(book.getPublYear())) return false;
        if (!getWritYear().equals(book.getWritYear())) return false;
        return getQuantity().equals(book.getQuantity());

    }

    @Override
    public int hashCode() {
        int result = getISBN().hashCode();
        result = 31 * result + getAuthor().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getPublYear().hashCode();
        result = 31 * result + getWritYear().hashCode();
        result = 31 * result + getQuantity().hashCode();
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
                ", quantity=" + quantity +
                '}';
    }
}
