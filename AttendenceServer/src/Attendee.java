
public class Attendee {
	private String firstName, lastName;
	private int rowNumber;
	public Attendee(String firstName, String lastName, int rowNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.rowNumber = rowNumber;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	
	public int getRowNumber() {
		return rowNumber;
	}

}
