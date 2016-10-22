package edu.cursor.library.book.service;

import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.enums.Genre;

import java.util.List;


public interface BookService {
   void showMainMenu();
    List<TblBook> createBookList();
    void proceedMain(char answer);
    char getMainChoice();
    void addBookOld(Integer ISBN);
    void addBookNew(Integer ISBN, String author, String title, String publYear, String writYear, Genre genre)
    void viewBookList();
    void removeBook(Integer ISBN);
}
