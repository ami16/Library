package edu.cursor.library.book.service;

import edu.cursor.library.book.entity.TblBook;

import java.util.List;


public interface BookService {
   void showMainMenu();
    List<TblBook> createBookList();
    void proceedMain(char answer);
    char getMainChoice();
    void addBook();
    void viewBookList();
    void removeBook(Integer ISBN);
}
