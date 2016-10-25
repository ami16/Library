package edu.cursor.library.book.service;

import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.enums.Genre;

import java.util.List;


interface BookService {

    List<TblBook> createBookList();

    void addBookOld(int ISBN);

    void addBookNew(int ISBN, String author, String title, String publYear, String writYear, Genre genre);

    void viewBookList();

    void removeBook(int ISBN);
}
