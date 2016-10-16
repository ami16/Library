package edu.cursor.library.book.service;

import edu.cursor.library.book.entity.BookTbl;

import java.util.List;

/**
 * Created by Root-UA on 16.10.2016.
 */
public interface BookService {
   void showMainMenu();
    List<BookTbl> createBookList();
    void proceedMain(char answer);
    char getMainChoice();
    void addBook();
    void viewBookList();
    void removeBook();
}
