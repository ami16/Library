package edu.cursor.library.book.service;

import edu.cursor.library.book.entity.TblBook;

import java.util.Comparator;
import java.util.List;


interface BookService {

    List<TblBook> createBookList();

    void addBookExist(int ISBN);
    void editBook(int isbn, char edit, String newValue);
    TblBook getBookById(int isbn);
    void addBookNew(int ISBN, String author, String title, String publYear, String writYear, String genre);

//    void viewBookList();

    void replaceBook(int ISBN);
    void removeBook(int ISBN);

    void viewBookList();
    void viewBookList(Comparator<TblBook> comparator);
    boolean validateISBN( String val );
}
