package edu.cursor.bookStorage;

import java.util.List;

/**
 * Created by Root-UA on 23.09.2016.
 */
public interface BookDAO {

    Integer addBook(Book book);

    void deleteBook(Integer ISBN);

    Book getBook(Integer ISBN);

    List<Book> findBooks();
}
