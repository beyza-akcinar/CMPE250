
// Class representing events happening in the simulation
public class Event implements Comparable<Event> {
	
	private int playerID; // Field representing the respective player's ID
	private double arrivalTime; // Field representing the time the event will take place
	private double duration; // Field representing the duration of the event
	private String mark; // A string field representing the type of service, "m" for massage, "t" for training, "p" for physiotherapy
	
	// Class constructor
	public Event(int playerID, double arrivalTime, double duration, String mark) {
		this.playerID = playerID;
		this.arrivalTime = arrivalTime;
		this.duration = duration;
		this.mark = mark;
	}
	
	// CompareTo method for Event objects
	@Override
	public int compareTo(Event e) {
		if (this.arrivalTime > e.arrivalTime) {
			return 1;
		} else if (this.arrivalTime < e.arrivalTime) {
			return -1;
		} else {
			return this.playerID - e.playerID;
		}
	}
	
	// Getter methods for respective fields
	public String getMark() {
		return mark;
	}

	public int getPlayerID() {
		return playerID;
	}

	public double getArrivalTime() {
		return arrivalTime;
	}
	
	public double getDuration() {
		return duration;
	}

}
