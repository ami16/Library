package edu.cursor.bookStorage;

import java.util.List;

/**
 * Created by Root-UA on 23.09.2016.
 */
public class Test {
    public static void main(String[] args) {
        BookManager bm = new BookManager();

        Book c1 = new Book("Socrat", "Book1", 10);
        Book c2 = new Book("Shakespeare", "Book2", 20);
        Book c3 = new Book("King", "Book3", 30);
        System.out.println("ADD Book ==============");
        Integer ISBN1 = bm.addBook(c1);
        Integer ISBN2 = bm.addBook(c2);
        Integer ISBN3 = bm.addBook(c3);
        List<Book> result1 = bm.findBooks();
        for(Book b : result1) {
            System.out.println(b);
        }

        System.out.println("DELETE Book ==============");
        bm.deleteBook(ISBN1);
        List<Book> result3 = bm.findBooks();
        for(Book b : result3) {
            System.out.println(b);
        }

        System.out.println("GET Book ==============");
        Book Book = bm.getBook(ISBN2);
        System.out.println(Book);
    }
}
