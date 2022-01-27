
// Class representing students that can be placed to houses if needed conditions are satisfied. They stay at houses until they graduate.
public class Student {
	
	private int id;
	private String name;
	// Field representing how many semesters are left until the student graduates
	private int duration;
	private double rating;
	private House house;
	
	// a boolean value used for checking whether the student has stayed in any house during his/her academic period
	private boolean housed;
	
	// Constructor of the class
	public Student(int id, String name, int duration, double rating) {
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.rating = rating;
		this.housed = false;
		this.house = null;
	}
	
	// Getter methods for respective fields
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}

	public double getRating() {
		return rating;
	}
	
	public boolean getHoused() {
		return housed;
	}
	
	public House getHouse() {
		return house;
	}
	
	// Setter methods for respective fields
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public void setHouse(House house) {
		this.house = house;
		housed = true;
	}
	
}
