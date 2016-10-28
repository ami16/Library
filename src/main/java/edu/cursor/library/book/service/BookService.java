package edu.cursor.library.book.service;

import edu.cursor.library.book.entity.TblBook;

import java.util.Comparator;
import java.util.List;


interface BookService {

    List<TblBook> createBookList();

    void addBookExist(int ISBN);
    void addBookNew(int ISBN, String author, String title, String publYear, String writYear, String genre);
    TblBook getBookById(int isbn);
    void removeBook(int ISBN);
    void viewBookList();
    void viewBookList(Comparator<TblBook> comparator);
    void editBook(int isbn, char edit, String newValue);

}
