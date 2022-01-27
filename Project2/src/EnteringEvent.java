
// Class representing events in which players try entering to the services
public class EnteringEvent extends Event {
	
	// Class constructor
	public EnteringEvent(int playerID, double arrivalTime, double duration, String mark) {
		super(playerID, arrivalTime, duration, mark);
	}

}
