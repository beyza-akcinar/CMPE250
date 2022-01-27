
// Class representing physiotherapists that can give physiotherapy services
public class Physiotherapist {
	
	private double serviceTime; // Field representing the service time of the physiotherapist
	
	// Class constructor
	public Physiotherapist(double serviceTime) {
		this.serviceTime = serviceTime;
	}

	// Getter methods for the respective fields
	public double getServiceTime() {
		return serviceTime;
	}

}
