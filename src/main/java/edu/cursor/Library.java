package edu.cursor;

import java.util.List;

public class Library {

	public void run() {

		Auth auth = Auth.getInstance();
		LibFunctions func = new LibFunctions();
		System.out.println("Library 0.6.\n");
//		List<User> uList = auth.getUserList() ;

		while (true) {

//			System.out.println( uList.toString() );

			func.showMainMenu( auth.getUserIsLogged());
			char mainChoice = func.getMain( auth.getUserIsLogged());
			func.proceedMain(mainChoice, auth.getUserIsLogged());

		}

	}

}