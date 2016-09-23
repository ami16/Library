package edu.cursor.bookStorage;

/**
 * Created by Root-UA on 23.09.2016.
 */
public class Book {
    private Integer ISBN;
    private String Author;
    private String Title;
    private Integer Quantity;


    public Book() {
    }

    public Book(String author, String title, Integer quantity) {
        Author = author;
        Title = title;
        Quantity = quantity;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        this.Quantity = quantity;
    }

    public Integer getBookISBN() {
        return ISBN;
    }

    public void setBookISBN(Integer bookISBN) {
        ISBN = bookISBN;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (ISBN != null ? !ISBN.equals(book.ISBN) : book.ISBN != null) return false;
        if (getAuthor() != null ? !getAuthor().equals(book.getAuthor()) : book.getAuthor() != null) return false;
        if (getTitle() != null ? !getTitle().equals(book.getTitle()) : book.getTitle() != null) return false;
        return getQuantity() != null ? getQuantity().equals(book.getQuantity()) : book.getQuantity() == null;

    }

    @Override
    public int hashCode() {
        int result = ISBN != null ? ISBN.hashCode() : 0;
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getQuantity() != null ? getQuantity().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN=" + ISBN +
                ", Author='" + Author + '\'' +
                ", Title='" + Title + '\'' +
                ", quantity=" + Quantity +
                '}';
    }
}