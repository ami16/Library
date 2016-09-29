package edu.cursor;

public class User {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private int mobileNo;
	private String address;
	private String dateOfRegistration;

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public int getMobileNo() { return mobileNo; }
	public void setMobileNo(int mobileNo) { this.mobileNo = mobileNo; }
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }
	public String getDateOfRegistration() { return dateOfRegistration; }
	public void setDateOfRegistration(String dateOfRegistration) { this.dateOfRegistration = dateOfRegistration; }

	public User(int id, String firstName, String lastName, String email, int mobileNo, String address, String dateOfRegistration) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNo = mobileNo;
		this.address = address;
		this.dateOfRegistration = dateOfRegistration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((dateOfRegistration == null) ? 0 : dateOfRegistration.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + mobileNo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (dateOfRegistration == null) {
			if (other.dateOfRegistration != null)
				return false;
		} else if (!dateOfRegistration.equals(other.dateOfRegistration))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (mobileNo != other.mobileNo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id: " + id + ", firstName: " + firstName + ", lastName: " + lastName + ", email: " + email
				+ ", mobileNo: " + mobileNo + ", address: " + address + ", dateOfRegistration: " + dateOfRegistration
				+ "]\n";
	}
}