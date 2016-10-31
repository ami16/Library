package edu.cursor.library.book.service;

import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.utils.GenreUtils;
import edu.cursor.library.book.utils.IOUtils;
import edu.cursor.library.infrastructure.exceptions.ISBNFormatException;
import edu.cursor.library.infrastructure.exceptions.NoSuchBookException;
import edu.cursor.library.user.registry.entity.TblUserBooksRegistry;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.stream.Collectors;

import static edu.cursor.library.infrastructure.Constans.*;

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
    public void addBookExist(String isbn) {
        try {
            if (validateISBN(isbn) && existingBook(isbn)) {
                bookList.add(getBookById(isbn));
                IOUtils.writeLine(bookList, file);
                return;
            }
        } catch (NoSuchBookException | ISBNFormatException e) {
            System.out.println(e.getMessage());
            // Logger code here
        }

    }

    @Override
    public void addBookNew(String isbn, String author, String title, String publYear, String writYear, String genre) {
        try {
            if (validateISBN(isbn)) {
                bookList.add(new TblBook(Integer.parseInt(isbn), author, title,
                        LocalDate.parse(publYear), LocalDate.parse(writYear),
                        GenreUtils.insertGenre(genre)));
                IOUtils.writeLine(bookList, file);

            }
        } catch (IllegalArgumentException | ISBNFormatException i) {
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
    public void removeBook(String isbn) {
        try {
        if (validateISBN(isbn) && existingBook(isbn)) {
            for (Iterator it = bookList.iterator(); it.hasNext(); ) {
                if (((TblBook) it.next()).getISBN() == Integer.parseInt(isbn)) {
                    it.remove();
                    IOUtils.writeLine(bookList, file);
                }
            }
        }
        }        catch (ISBNFormatException | NoSuchBookException e) {
            System.out.println(e.getMessage());
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
    public void editBook(String isbn, char edit, String newValue) {
        try {
            if (validateISBN(isbn) && existingBook(isbn)) {
                switch (edit) {
                    case AUTHOR_EDIT:
                        getBookById(isbn).setAuthor(newValue);
                        IOUtils.writeLine(bookList, file);
                        return;
                    case TITLE_EDIT:
                        getBookById(isbn).setTitle(newValue);
                        IOUtils.writeLine(bookList, file);
                        return;
                    case PUBL_YEAR_EDIT:
                        getBookById(isbn).setPublYear(LocalDate.parse(newValue));
                        IOUtils.writeLine(bookList, file);
                        return;
                    case WRIT_YEAR_EDIT:
                        getBookById(isbn).setWritYear(LocalDate.parse(newValue));
                        IOUtils.writeLine(bookList, file);
                        return;
                    case GENRE_EDIT:
                        getBookById(isbn).setGenre(GenreUtils.insertGenre(newValue));
                        IOUtils.writeLine(bookList, file);
                        return;
                    default:
                        System.out.println("Wrong choice!!!");
                        return;

                }
            }
        } catch (IllegalArgumentException | NoSuchBookException | ISBNFormatException e) {
            System.out.println(e.getMessage());
            //logger
        }

    }

    @Override
    public TblBook getBookById(String isbn) {
        try {
            if (validateISBN(isbn) && existingBook(isbn)) {
                return bookList.stream()
                        .filter(s -> s.getISBN() == Integer.parseInt(isbn))
                        .findFirst().get();
            }
        } catch (NoSuchBookException | ISBNFormatException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public boolean validateISBN(String val) throws ISBNFormatException {
        if (val.matches("^(\\d{1,10})$")) {
            return true;
        }
        throw new ISBNFormatException("Wrong ISBN  :  ", val);
    }

    @Override
    public boolean existingBook(String val) throws NoSuchBookException {
        if (bookList.stream()
                .anyMatch(s -> s.getISBN() == Integer.parseInt(val))) {
            return true;
        }
        throw new NoSuchBookException("Doesn't exict book with ISBN = ", val);
    }

    @Override
    /*
    ** For future release!!
     */
    public void viewAvailableBooks(List<TblUserBooksRegistry> userBooks) {
        List<TblBook> filtred = bookList;
        System.out.println(filtred);
        System.out.println(userBooks);

        for (Iterator it = filtred.iterator(); it.hasNext(); ) {
            for (Iterator iter = userBooks.iterator(); iter.hasNext(); ) {
                System.out.println(iter.next());
                System.out.println(iter.next());
//
//                  Some magic code here!!!
//
            }
        }

        System.out.println(filtred);

    }
}

