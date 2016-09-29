package edu.cursor;

public class Library {

	public void run() {

		AuthFactory auth = AuthFactory.getInstance();
		LibFunctions func = new LibFunctions();
		System.out.println("Library 0.1.\n");

		while (true) {

			func.showMainMenu( auth.getUserIsLogged());
			char mainAnswer = func.getMain( auth.getUserIsLogged());
			func.proceedMain(mainAnswer);

		}

	}

}