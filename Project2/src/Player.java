import java.util.Comparator;

// Class representing Players who can take massage, training, and physiotherapy services.
public class Player {
	
	private int ID; // Field representing player ID
	private int skill; // Field representing the skill level for the player
	private int massageCount; // Field representing the total number of massage services gotten by the player
	private double arrivalTime; // Field representing the latest checkpoint time for the player (when player enters a queue, exits a service etc.)
	private double trainingTime; // Field representing the latest training duration of the player
	private double duration; // Field representing the recent event duration of the player
	private double MassageQueueTime; // Field representing the total time spent while player is in massageQueue
	private double PTQueueTime; // Field representing the total time spent while player is in physiotherapyQueue
	private double TrainingQueueTime; // Field representing the total time spent while player is in trainingQueue
	
	// Class constructor
	public Player(int ID, int skill) {
		this.ID = ID;
		this.skill = skill;
		this.massageCount = 0;
		this.arrivalTime = 0;
		this.trainingTime = 0;
		this.duration = 0;
		this.MassageQueueTime = 0;
		this.PTQueueTime = 0;
		this.TrainingQueueTime = 0;
	}
	
	// Getter methods for respective fields
	public int getID() {
		return ID;
	}

	public int getSkill() {
		return this.skill;
	}

	public double getArrivalTime() {
		return arrivalTime;
	}

	public int getMassageCount() {
		return massageCount;
	}

	public double getTrainingTime() {
		return trainingTime;
	}

	public double getDuration() {
		return duration;
	}

	public double getMassageQueueTime() {
		return MassageQueueTime;
	}
	
	public double getPTQueueTime() {
		return PTQueueTime;
	}
	
	public double getTrainingQueueTime() {
		return TrainingQueueTime;
	}
	
	// Setter methods for respective fields
	public void setMassageQueueTime(double massageQueueTime) {
		MassageQueueTime = massageQueueTime;
	}

	public void setPTQueueTime(double pTQueueTime) {
		PTQueueTime = pTQueueTime;
	}

	public void setTrainingQueueTime(double trainingQueueTime) {
		TrainingQueueTime = trainingQueueTime;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public void setArrivalTime(double arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public void setMassageCount(int massageCount) {
		this.massageCount = massageCount;
	}

	public void setTrainingTime(double trainingTime) {
		this.trainingTime = trainingTime;
	}

}

// Comparator class for players.
// Compares according to skill (higher is prioritized), arrival time (lower is prioritized), and id (lower is prioritized), respectively.
class MassageComparator implements Comparator<Player> {
	
	public int compare(Player one, Player two) {
		if (one.getSkill() != two.getSkill()) {
			return two.getSkill() - one.getSkill();
		} else {
			if (one.getArrivalTime() > two.getArrivalTime()) {
				return 1;
			} else if (one.getArrivalTime() < two.getArrivalTime()) {
				return -1;
			} else {
				return one.getID() - two.getID();
			}
		}
	}
		

}
// Comparator class for players.
// Compares according to training time (higher is prioritized), arrival time (lower is prioritized), and id (lower is prioritized), respectively.
class PhysiotherapyComparator implements Comparator<Player> {
	
	public int compare(Player one, Player two) {
		if (one.getTrainingTime() > two.getTrainingTime()) {
			return -1;
		} else if (one.getTrainingTime() < two.getTrainingTime()) {
			return 1;
		} else {
			if (one.getArrivalTime() > two.getArrivalTime()) {
				return 1;
			} else if (one.getArrivalTime() < two.getArrivalTime()) {
				return -1;
			} else {
				return one.getID() - two.getID();
			}
		}
	}
		

}

// Comparator class for players.
// Compares according to arrival time (lower is prioritized) and id (lower is prioritized), respectively.
class TrainingComparator implements Comparator<Player> {
	
	public int compare(Player one, Player two) {
		if (one.getArrivalTime() > two.getArrivalTime()) {
			return 1;
		} else if (one.getArrivalTime() < two.getArrivalTime()) {
			return -1;
		} else {
			return one.getID() - two.getID();
		}
	}
		

}

