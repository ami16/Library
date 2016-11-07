package edu.cursor.library.book.service;

import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.infrastructure.exceptions.ISBNFormatException;
import edu.cursor.library.infrastructure.exceptions.NoSuchBookException;

import java.util.Comparator;
import java.util.List;


interface BookService {

    List<TblBook> createBookList();

    void addBookExist(String ISBN);
    void addBookNew(String ISBN, String author, String title, String publYear, String writYear, String genre);

//    void viewBookList();

    void replaceBook(int ISBN);
    void removeBook(int ISBN);

    void viewBookList();
    void viewBookList(Comparator<TblBook> comparator);
    boolean bookExists(String val) throws NoSuchBookException;
//    boolean validateISBN( String val ) throws ISBNFormatException;
    boolean validateISBN( String val ) ;
}
