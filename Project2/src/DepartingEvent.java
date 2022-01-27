
// Class representing events in which players depart from the services
public class DepartingEvent extends Event {
	
	// Class constructor
	public DepartingEvent(int playerID, double arrivalTime, double duration, String mark) {
		super(playerID, arrivalTime, duration, mark);
	}
	
}
