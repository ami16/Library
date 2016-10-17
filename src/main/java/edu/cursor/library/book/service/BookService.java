package edu.cursor.library.book.service;

import edu.cursor.library.book.entity.BookTbl;

import java.util.List;


public interface BookService {
   void showMainMenu();
    List<BookTbl> createBookList();
    void proceedMain(char answer);
    char getMainChoice();
    void addBook();
    void viewBookList();
    void removeBook();
}
