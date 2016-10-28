package edu.cursor.library.book.service;

import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.utils.GenreUtils;
import edu.cursor.library.book.utils.IOUtils;
import edu.cursor.library.infrastructure.exceptions.NoSuchBookException;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    private static List<TblBook> bookList = new ArrayList<>();

    public List<TblBook> getBookList() {
        if (bookList.size() == 0) {
            createBookList();
        }
        return bookList;
    }

    private static final String projPath = System.getProperty("user.dir"),
            dbPath = "/src/main/resources/",
            fileName = "bookList.csv",
            file = projPath + dbPath + fileName;


    private static BookServiceImpl instance;

    private BookServiceImpl() {
    }

    public static BookServiceImpl getInstance() {
        if (instance == null) {
            synchronized (BookServiceImpl.class) {
                if (instance == null) {
                    instance = new BookServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void addBookExist(int ISBN) {
        try {
            if (bookList.stream()
                    .anyMatch(s -> s.getISBN() == ISBN)) {
                bookList.add(bookList.stream()
                        .filter(s -> s.getISBN() == ISBN)
                        .findFirst().get());
                IOUtils.writeLine(bookList, file);
                return;
            }
            throw new NoSuchBookException("Doesn't exict book with ISBN = ", ISBN);
        } catch (NoSuchBookException nb) {
            // Logger code here
        }

    }

    @Override
    public void addBookNew(int ISBN, String author, String title, String publYear, String writYear, String genre) {
        try {
            bookList.add(new TblBook(ISBN, author, title,
                    LocalDate.parse(publYear), LocalDate.parse(writYear),
                    GenreUtils.insertGenre(genre)));

        } catch (IllegalArgumentException i) {
            System.out.println(i.getMessage());

            // Logger code here
        }
    }

    @Override
    public List<TblBook> createBookList() {
        Collections.addAll(bookList, IOUtils.readFile(file));
        return bookList;
    }


    @Override
    public void removeBook(int ISBN) {

        for (Iterator it = bookList.iterator(); it.hasNext(); ) {
            if (((TblBook) it.next()).getISBN() == ISBN) {
                it.remove();
                IOUtils.writeLine(bookList, file);
            }
        }

    }

    @Override
    public void viewBookList() {
        if (bookList.size() == 0) {
            bookList = getBookList();
        }
        bookList.stream().collect(
                Collectors.groupingBy(
                        i -> i, Collectors.counting()
                ))
                .forEach((k, v) -> System.out.print(k + ", (" + v + "); \n"));
    }


    @Override
    public void viewBookList(Comparator<TblBook> comparator) {
        Map<TblBook, Long> map = bookList.stream().collect(
                Collectors.groupingBy(
                        i -> i, Collectors.counting()
                ));

        SortedSet<TblBook> keys = new TreeSet<>(comparator);
        keys.addAll(map.keySet());

        for (TblBook book : keys) {
            System.out.print(book + ", (" + map.get(book) + "); \n");
        }
    }

    @Override
    public void editBook(int isbn, char edit, String newValue) {
        try {
            switch (edit) {
                case 'a':
                    getBookById(isbn).setAuthor(newValue);
                    break;
                case 't':
                    getBookById(isbn).setTitle(newValue);
                    break;
                case 'p':
                    getBookById(isbn).setPublYear(LocalDate.parse(newValue));
                    break;
                case 'w':
                    getBookById(isbn).setWritYear(LocalDate.parse(newValue));
                    break;
                case 'g':
                    getBookById(isbn).setGenre(GenreUtils.insertGenre(newValue));
                    break;
                default:
                    System.out.println("Wrong choice!!!");
                    return;

            }
        } catch (IllegalArgumentException i) {
            System.out.println(i.getMessage());
            //logger
        }

    }

    @Override
    public TblBook getBookById(int isbn) {
        try {
            for (TblBook b : bookList
                    .stream()
                    .filter(i -> i.getISBN() == isbn)
                    .collect(Collectors.toList())) {
                if (b.getISBN() == isbn) {
                    return b;
                }
                throw new NoSuchBookException("Doesn't exict book with ISBN = ", isbn);
            }
        } catch (NoSuchBookException nb) {
            //logger
        }
        return null;

    }

}