package edu.cursor.library.book.service;


import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.enums.Genre;
import edu.cursor.library.book.utils.CSVUtils;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    private static List<TblBook> bookList = new ArrayList<>();
    private Scanner scan = new Scanner(System.in);
    private String path = System.getProperty("user.dir") + "/src/main/java/edu/cursor/library/book/database/bookList.csv";


    public BookServiceImpl() {
        bookList = createBookList();
    }


    @Override
    public List<TblBook> createBookList() {
        List<TblBook> bookList = new ArrayList<>();
        Collections.addAll(bookList, CSVUtils.readFile(path));
        return bookList;
    }


    @Override
    public void addBookOld(Integer ISBN) {
        if (bookList.stream()
                .anyMatch(s -> s.getISBN() == ISBN)) {
            bookList.add(bookList.stream()
                    .filter(s -> s.getISBN() == ISBN)
                    .findFirst().get());
            CSVUtils.writeLine(bookList, path);
        }

    }

    @Override
    public void addBookNew(Integer ISBN, String author, String title, String publYear, String writYear, Genre genre) {
        try {
            LocalDate PublYearNew = LocalDate.parse(publYear);
            LocalDate WritYearNew = LocalDate.parse(writYear);
            bookList.add(new TblBook(ISBN, author, title, PublYearNew, WritYearNew, genre));
            CSVUtils.writeLine(bookList, path);
        } catch (IllegalArgumentException i) {
            System.out.println(i.getMessage());
        }
    }

    @Override
    public void viewBookList() {
        bookList.stream().collect(
                Collectors.groupingBy(
                        i -> i, Collectors.counting()
                ))
                .forEach((k, v) -> System.out.println("-" + k + " amount: " + v + ";"));
    }

    @Override
    public void removeBook(Integer ISBN) {
        for (Iterator it = bookList.iterator(); it.hasNext(); ) {
            if (((TblBook) it.next()).getISBN() == ISBN) {
                it.remove();
                CSVUtils.writeLine(bookList, path);
                return;
            }
        }
        // for future creating class Exception
        // throw new  throw new BookStoreException();
    }


}