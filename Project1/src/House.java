
// Class representing houses that can allocate students of the university
public class House {
	
	private int id;
	// field representing how many semesters left for this house to be available
	private int duration;
	private double rating;
	
	//Constructor of the class
	public House(int id, int duration, double rating) {
		this.id = id;
		this.duration = duration;
		this.rating = rating;
	}
	
	// Getter methods for the respective fields
	public int getId() {
		return id;
	}

	public int getDuration() {
		return duration;
	}

	public double getRating() {
		return rating;
	}
	
	// Setter method for the duration
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
