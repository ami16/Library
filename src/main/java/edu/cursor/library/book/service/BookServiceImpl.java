package edu.cursor.library.book.service;


import edu.cursor.library.book.entity.TblBook;
import edu.cursor.library.book.enums.Genre;
import edu.cursor.library.book.utils.CSVUtils;
import edu.cursor.library.book.utils.GenreUtils;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService{

    private static List<TblBook> bookList = new ArrayList<>();
    private Scanner scan = new Scanner(System.in);
    private String path = System.getProperty("user.dir") + "/src/main/java/edu/cursor/library/book/database/bookList.csv";


    public BookServiceImpl() {
        bookList = createBookList();
    }

    public void start() {
        while (true) {
            showMainMenu();
            getMainChoice();
        }
    }
    @Override
    public void showMainMenu() {
        System.out.println("MENU:\n");
        System.out.println("Hello !");
        System.out.println("1. View library books");
        System.out.println("2. Add Book");
        System.out.println("3. Remove Book");
        System.out.println("x. Exit");

    }
    @Override
    public List<TblBook> createBookList() {
        List <TblBook> bookList = new ArrayList<>();
        Collections.addAll(bookList, CSVUtils.readFile(path));
        return bookList;
    }

    @Override
    public void proceedMain(char answer) {
        switch (answer) {
            case '1': // List
                viewBookList();
                break;
            case '2': // Add book
                addBook();
                break;
            case '3': // Remove book
                removeBook();
                break;
        }
    }
    @Override
    public char getMainChoice() {
        char mainChoice;
        do {
            mainChoice = scan.next().charAt(0);
            switch (Character.toLowerCase(mainChoice)) {
                case '1':
                case '2':
                case '3':
                case '4':
                    proceedMain(mainChoice);
                    break;
                case 'x':
                    sayBye();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choose correct item (1-3 or x): ");
            }
            return mainChoice;
        } while (true);
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

    public void sayBye() {
        System.out.println("See ya.");
    }

}