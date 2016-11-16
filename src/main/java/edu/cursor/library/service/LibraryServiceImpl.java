package edu.cursor.library.service;

import edu.cursor.library.model.TblBook;
import edu.cursor.library.ui.LibraryMenu;

import java.util.Comparator;
import java.util.Scanner;


public class LibraryServiceImpl implements LibraryService {

	private static LibraryServiceImpl instance;
	private Scanner scan = new Scanner(System.in);
	private LibraryMenu libraryMenu = new LibraryMenu();

	public LibraryServiceImpl() {}


	@Override
	public void start() {
		System.out.println("Lib started");
		while (true) {
			libraryMenu.showMainMenu();
		}
	}

	public void viewLibraryBooks() {
		libraryMenu.showLibraryBooks();
	}

	public void viewLibraryBooks(Comparator<TblBook> comparator) {
		libraryMenu.showLibraryBooks();
	}

}