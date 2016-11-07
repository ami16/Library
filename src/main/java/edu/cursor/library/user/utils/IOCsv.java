package edu.cursor.library.user.utils;

import edu.cursor.library.user.entity.TblUser;
import edu.cursor.library.user.enums.Role;
import org.joda.time.LocalDate;
import java.io.*;
import java.util.List;

public class IOCsv {

	private static final char DEFAULT_SEPARATOR = ',';
	private static File file;

	public static void writeFile(List<TblUser> userList, String path) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
			bw.write("Id,firstName,lastName,eMail,mobileNum,address,dateOfRegistration,role,\n");
			for (TblUser b : userList) {
				bw.append(b.getId().toString());
				bw.append(DEFAULT_SEPARATOR);
				bw.append(b.getFirstName());
				bw.append(DEFAULT_SEPARATOR);
				bw.append(b.getLastName());
				bw.append(DEFAULT_SEPARATOR);
				bw.append(b.geteMail());
				bw.append(DEFAULT_SEPARATOR);
				bw.append(b.getMobileNum().toString());
				bw.append(DEFAULT_SEPARATOR);
				bw.append(b.getAddress());
				bw.append(DEFAULT_SEPARATOR);
				bw.append(b.getDateOfRegistration().toString());
				bw.append(DEFAULT_SEPARATOR);
				bw.append(b.getRole().toString());
				bw.append("\n");
			}
			bw.flush();
		} catch (IOException ioe) {
			// Logger code here
		}
	}

	public static TblUser[] readFile(String path) {
		String line;
		int counter = 0;
		TblUser[] userArray;
		file = new File(path);
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while ((line = br.readLine()) != null) {
				counter++;
			}
		} catch (IOException ioe) {
			// Logger code here
		} finally {
			userArray = new TblUser[counter - 1];
		}
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			line = br.readLine();
			int index = 0;
			while ((line = br.readLine()) != null) {
				String[] fields = line.split(",");
				Integer Id = Integer.parseInt(fields[0]);
				String firstName = fields[1];
				String lastName = fields[2];
				String eMail = fields[3];
				Integer mobileNum = Integer.parseInt(fields[4]);
				String address = fields[5];
				LocalDate dateOfRegistration = LocalDate.parse(fields[6]);
				String role = fields[7].toUpperCase();
				TblUser user = new TblUser(Id, firstName, lastName, eMail, mobileNum, address, dateOfRegistration,
						Role.valueOf(role));
				userArray[index] = user;
				index++;
			}
		} catch (IOException ioe) {
			// Logger code here
		}
		return userArray;
	}
}