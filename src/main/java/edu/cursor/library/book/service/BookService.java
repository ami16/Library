package edu.cursor.library.book.service;

import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.infrastructure.exceptions.ISBNFormatException;
import edu.cursor.library.infrastructure.exceptions.NoSuchBookException;
import edu.cursor.library.user.registry.entity.TblUserBooksRegistry;

import java.util.Comparator;
import java.util.List;


interface BookService {

    void addBookExist(String isbn);

    void addBookNew(String isbn, String author, String title, String publYear, String writYear, String genre);

    List<TblBook> createBookList();

    TblBook getBookById(String isbn);

    void removeBook(String isbn);

    void viewBookList();

    void viewBookList(Comparator<TblBook> comparator);

    void editBook(String isbn, char edit, String newValue);

    boolean validateISBN(String val) throws ISBNFormatException;

    boolean existingBook(String val) throws NoSuchBookException;

    void viewAvailableBooks (List<TblUserBooksRegistry> userBooks);
}
