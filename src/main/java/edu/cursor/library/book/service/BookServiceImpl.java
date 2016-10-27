package edu.cursor.library.book.service;


import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.utils.IOUtils;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    private static List<TblBook> bookList = new ArrayList<>();
    private String path = System.getProperty("user.dir") + "/src/main/resources/bookList.csv";


    public BookServiceImpl() {
        bookList = createBookList();
    }


    @Override
    public List<TblBook> createBookList() {
        List<TblBook> bookList = new ArrayList<>();
        Collections.addAll(bookList, IOUtils.readFile(path));
        return bookList;
    }


    @Override
    public void addBookOld(int ISBN) {
        if (bookList.stream()
                .anyMatch(s -> s.getISBN() == ISBN)) {
            bookList.add(bookList.stream()
                    .filter(s -> s.getISBN() == ISBN)
                    .findFirst().get());
            IOUtils.writeLine(bookList, path);
        }

        // for future creating class Exception
        // Logger code here
    }

    @Override
    public void addBookNew(int ISBN, String author, String title, String publYear, String writYear, String genre) {
        try {
            bookList.add(new TblBook(ISBN, author, title,
                    LocalDate.parse(publYear), LocalDate.parse(writYear),
                    IOUtils.converToEnumSet(genre)));

        } catch (IllegalArgumentException i) {
            System.out.println(i.getMessage());
            // for future creating class Exception
            // Logger code here
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
    public void removeBook(int ISBN) {
        for (Iterator it = bookList.iterator(); it.hasNext(); ) {
            if (((TblBook) it.next()).getISBN() == ISBN) {
                it.remove();
                IOUtils.writeLine(bookList, path);
                return;
            }
        }
        // for future creating class Exception
        // Logger code here
        // throw new  throw new BookStoreException();
    }


}