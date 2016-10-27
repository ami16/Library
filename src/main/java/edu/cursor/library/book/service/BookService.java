package edu.cursor.library.book.service;

import edu.cursor.library.book.entity.TblBook;

import java.util.List;


interface BookService {

    List<TblBook> createBookList();

    void addBookOld(int ISBN);

    void addBookNew(int ISBN, String author, String title, String publYear, String writYear, String genre);

    void viewBookList();

    void replaceBook(int ISBN);

    void removeBook(int ISBN);
}
