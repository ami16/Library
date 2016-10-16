package edu.cursor.library.book.service;


import edu.cursor.library.book.entity.BookTbl;
import edu.cursor.library.book.enums.Genre;
import edu.cursor.library.book.utils.CSVUtils;
import edu.cursor.library.book.utils.GenreUtils;
import org.joda.time.LocalDate;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService{

    private static List<BookTbl> bookList = new ArrayList<>();
    Scanner scan = new Scanner(System.in);
    private String path = System.getProperty("user.dir") + "/src/main/java/edu/cursor/library/book/database/573.csv";
    BufferedReader bfr = null;
    FileWriter writer = null;

    public BookServiceImpl() throws IOException {
        bookList = createBookList();
    }

    public void start() throws IOException {
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
    public List<BookTbl> createBookList() throws IOException {
        List <BookTbl> bookList = new ArrayList<>();
        Collections.addAll(bookList, CSVUtils.readFile(bfr, path));
        return bookList;
    }

    @Override
    public void proceedMain(char answer) throws IOException {
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
    public char getMainChoice() throws IOException {
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
    public void addBook() throws IOException {
        System.out.println("Pls enter ISBN for new book.");
        Integer ISBNnew = scan.nextInt();
        String left = scan.nextLine();
        Boolean newBook = bookList.stream().map(s -> s.getISBN()).collect(
                Collectors.toList()).stream().anyMatch(ISBNnew::equals);
        if (newBook) {
            BookTbl oldBook = bookList.stream().filter(s -> s.getISBN().equals(ISBNnew)).findFirst().get();
            bookList.add(oldBook);
            CSVUtils.writeLine(writer, bookList, path);

        } else {
            System.out.println("Pls enter Author for new book.");
            String authorNew = scan.nextLine();
            System.out.println("Pls enter Title for new book.");
            String titleNew = scan.nextLine();
            System.out.println("Pls enter PublYear(YYYY-MM-DD) for new book.");
            LocalDate PublYearNew = LocalDate.parse(scan.nextLine());
            System.out.println("Pls enter WritYear(YYYY-MM-DD) for new book.");
            LocalDate WritYearNew = LocalDate.parse(scan.nextLine());
            Genre genreNew = GenreUtils.chooseGenre();
            bookList.add(new BookTbl(ISBNnew, authorNew, titleNew, PublYearNew, WritYearNew, genreNew));
            CSVUtils.writeLine(writer, bookList, path);
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

    public void removeBook() throws IOException {
        System.out.println("Pls enter ISBN for remove book.");
        Integer ISBNnew = scan.nextInt();
        String left = scan.nextLine();
        for (Iterator it = bookList.iterator(); it.hasNext(); ) {
            BookTbl book = (BookTbl) it.next();
            if (book.getISBN().equals(ISBNnew)) {
                bookList.remove(book);
                CSVUtils.writeLine(writer, bookList, path);
                break;
            } else {
                System.out.println("Sory book with this ISBN not exist");
                removeBook();
            }
        }

    }

    public void sayBye() {
        System.out.println("See ya.");
    }

}