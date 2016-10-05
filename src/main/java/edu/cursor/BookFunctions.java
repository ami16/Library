package edu.cursor;

import org.joda.time.LocalDate;

import java.util.*;
import java.util.stream.Collectors;

public class BookFunctions {

    private static List<Book> bookList = new ArrayList<>();
    Scanner scan = new Scanner(System.in);


    public BookFunctions() {
        bookList = createBookList();
    }

    public void showMainMenu() {
        System.out.println("MENU:\n");
        System.out.println("Hello !");
        System.out.println("1. View library books");
        System.out.println("2. Sort by Author");
        System.out.println("3. Add Book");
        System.out.println("4. Remove Book");
        System.out.println("x. Exit");

    }

    public List<Book> createBookList() {
        // For test - example
        bookList.add(new Book(159, "Pavelkevych", "My book", new LocalDate(2015, 7, 12), new LocalDate(2016, 10, 10), Genre.History));
        bookList.add(new Book(357, "Pavelk", "My book2", new LocalDate(2013, 5, 1), new LocalDate(2016, 8, 12), Genre.Horror));
        bookList.add(new Book(159, "Pavelkevych", "My book", new LocalDate(2015, 7, 12), new LocalDate(2016, 10, 10), Genre.History));
        bookList.add(new Book(357, "Pavelk", "My book2", new LocalDate(2013, 5, 1), new LocalDate(2016, 8, 12), Genre.Horror));
        bookList.add(new Book(159, "Pavelkevych", "My book", new LocalDate(2015, 7, 12), new LocalDate(2016, 10, 10), Genre.History));
        bookList.add(new Book(159, "Pavelkevych", "My book", new LocalDate(2015, 7, 12), new LocalDate(2016, 10, 10), Genre.History));
        bookList.add(new Book(159, "Pavelkevych", "My book", new LocalDate(2015, 7, 12), new LocalDate(2016, 10, 10), Genre.History));
        bookList.add(new Book(357, "Pavelk", "My book2", new LocalDate(2013, 5, 1), new LocalDate(2016, 8, 12), Genre.Horror));
        bookList.add(new Book(159, "Pavelkevych", "My book", new LocalDate(2015, 7, 12), new LocalDate(2016, 10, 10), Genre.History));
        bookList.add(new Book(159, "Pavelkevych", "My book", new LocalDate(2015, 7, 12), new LocalDate(2016, 10, 10), Genre.History));
        bookList.add(new Book(159, "Pavelkevych", "My book", new LocalDate(2015, 7, 12), new LocalDate(2016, 10, 10), Genre.History));
        bookList.add(new Book(357, "Pavelk", "My book2", new LocalDate(2013, 5, 1), new LocalDate(2016, 8, 12), Genre.Horror));
        return bookList;
    }


    public void proceedMain(char answer) {
        switch (answer) {
            case '1': // List
                viewBookList();
                break;
            case '2': // Sort
                sort();
                break;
            case '3': // Add book
                addBook();
                break;
            case '4': // Remove book
                removeBook();
                break;
        }
    }

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
                    System.out.println("Choose correct item (1-4 or x): ");
            }
            return mainChoice;
        } while (true);
    }

    public void addBook() {
        System.out.println("Pls enter ISBN for new book.");
        Integer ISBNnew = scan.nextInt();
        String left = scan.nextLine();
        Boolean newBook = bookList.stream().map(s -> s.getISBN()).collect(
                Collectors.toList()).stream().anyMatch(ISBNnew::equals);
        if (newBook) {
            Book oldBook = bookList.stream().filter(s -> s.getISBN().equals(ISBNnew)).findFirst().get();
            bookList.add(oldBook);
        } else {
            System.out.println("Pls enter Author for new book.");
            String Authornew = scan.nextLine();
            System.out.println("Pls enter Title for new book.");
            String Titlenew = scan.nextLine();
            System.out.println("Pls enter PublYear(YYYY-MM-DD) for new book.");
            LocalDate PublYearnew = LocalDate.parse(scan.nextLine());
            System.out.println("Pls enter WritYear(YYYY-MM-DD) for new book.");
            LocalDate WritYearnew = LocalDate.parse(scan.nextLine());
            Genre genrenew = chooseGenre();
            bookList.add(new Book(ISBNnew, Authornew, Titlenew, PublYearnew, WritYearnew, genrenew));
        }

    }

    public void viewBookList() {
        bookList.stream().collect(
                Collectors.groupingBy(
                        i -> i, Collectors.counting()
                ))
                .forEach((k, v) -> System.out.println("-" + k + " amount: " + v + ";"));
    }

    public Genre chooseGenre() {
        System.out.println("Pls chose genre for new book.");
        String tryGenre = scan.nextLine();
        Boolean checkGenre = Arrays.stream(Genre.values())
                .map(Genre::name)
                .collect(Collectors.toList())
                .stream()
                .anyMatch(tryGenre::equals);

        System.out.println(checkGenre);
        if (checkGenre) {
            Genre genre = Genre.valueOf(tryGenre);
            return genre;
        } else {
            System.out.println("Wrong genre!!!");
            return chooseGenre();
        }
    }

    public void removeBook() {
        System.out.println("Pls enter ISBN for remove book.");
        Integer ISBNnew = scan.nextInt();
        String left = scan.nextLine();
        for (Iterator it = bookList.iterator(); it.hasNext(); ) {
            Book book = (Book) it.next();
            if (book.getISBN().equals(ISBNnew)) {
                bookList.remove(book);
                break;
            } else {
                System.out.println("Sory book with this ISBN not exist");
                removeBook();
            }
        }

    }

    public void sort() {
        bookList.stream().sorted((o1, o2) -> o1.getAuthor() != o2.getAuthor() ? o1.getAuthor().
                compareTo(o2.getAuthor()) : o1.getTitle().compareTo(o2.getTitle())).collect(Collectors.toList());
        bookList.stream().forEach(book -> System.out.println(book));
    }


    public void sayBye() {
        System.out.println("See ya.");
    }

}