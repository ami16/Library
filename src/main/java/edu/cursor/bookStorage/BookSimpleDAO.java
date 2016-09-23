package edu.cursor.bookStorage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Root-UA on 23.09.2016.
 */
public class BookSimpleDAO implements BookDAO {

    private final List<Book> books = new ArrayList<Book>();


    @Override
    public Integer addBook(Book book) {
        Integer ISBN = generateBookISBN();
        book.setBookISBN(ISBN);
        books.add(book);
        return ISBN;
    }

    @Override
    public void deleteBook(Integer ISBN) {
        for(Iterator<Book> it = books.iterator(); it.hasNext();) {
            Book bk = it.next();
            if(bk.getBookISBN().equals(ISBN)) {
                it.remove();
            }
        }
    }

    @Override
    public Book getBook(Integer ISBN) {
        for(Book book : books) {
            if(book.getBookISBN().equals(ISBN)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public List<Book> findBooks() {
        return books;
    }

    private Integer generateBookISBN() {
        Integer ISBN = (int)Math.round(Math.random() * 1000);
        while(getBook(ISBN) != null) {
            ISBN = (int)Math.round(Math.random() * 1000);
        }
        return ISBN;
    }
}
