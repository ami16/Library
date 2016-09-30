package edu.cursor;

import java.util.List;

public class Library {

	public void run() {

		AuthFactory auth = AuthFactory.getInstance();
		LibFunctions func = new LibFunctions();
		System.out.println("Library 0.3.\n");
		List<User> uList = auth.getuList() ;

		while (true) {

//			System.out.println( uList.toString() );

			func.showMainMenu( auth.getUserIsLogged());
			char mainAnswer = func.getMain( auth.getUserIsLogged());
			func.proceedMain(mainAnswer);

		}

	}

}