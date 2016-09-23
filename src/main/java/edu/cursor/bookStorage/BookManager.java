package edu.cursor.bookStorage;

import java.util.List;


/**
 * Created by Root-UA on 23.09.2016.
 */
public class BookManager {
    private BookDAO dao;

    public BookManager() {
        dao = BookDAOFactory.getBookDAO();
    }

    public Integer addBook(Book book) {
        return dao.addBook(book);
    }

    public void deleteBook(Integer ISBN) {
        dao.deleteBook(ISBN);
    }

    public Book getBook(Integer ISBN) {
        return dao.getBook(ISBN);
    }

    public List<Book> findBooks() {
        return dao.findBooks();
    }
}
