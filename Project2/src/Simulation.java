import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

// Class used to execute Discrete Event Simulation.
public class Simulation {
	
	private PriorityQueue<Event> events; // PriorityQueue consisting of events in the simulation
	private PriorityQueue<Player> trainingQueue; // PriorityQueue consisting of players waiting for training process
	private PriorityQueue<Player> massageQueue; // PriorityQueue consisting of players waiting for massage process
	private PriorityQueue<Player> physiotherapyQueue; // PriorityQueue consisting of players waiting for physiotherapy process
	private ArrayList<Physiotherapist> physiotherapists; // ArrayList consisting of physiotherapists
	private ArrayList<Player> players; // ArrayList consisting of all players
	private ArrayList<Player> massageMax; // ArrayList consisting of players who got 3 massage services 
	private ArrayList<Player> massage; // ArrayList consisting of players currently in the massage process
	private ArrayList<Player> training; // ArrayList consisting of players currently in the training process
	private ArrayList<Player> physiotherapy; // ArrayList consisting of players currently in the physiotherapy process
	
	private int masseurs; // Field representing the number of masseurs
	private int trainers; // Field representing the number of trainers
	private int PTs; // Field representing the number of physiotherapists
	private int playerCount; // Field representing the number of players
	private int maxMassage; // Field representing the maximum size of massageQueue
	private int maxPT; // Field representing the maximum size of physiotherapyQueue
	private int maxTraining; // Field representing the maximum size of trainingQueue
	private int invalid; // Field representing the number of invalid attempts
	private int cancelled; // Field representing the number of cancelled attempts
	private int massageCount; // Field representing the total number of valid massage services
	private int trainingCount; // Field representing the total number of valid training services
	private int PTCount; // Field representing the total number of valid physiotherapy services
	private double totalMassage; // Field representing the total massage time
	private double totalPT; // Field representing the total physiotherapy time
	private double totalTraining; // Field representing the total training time
	private double massageQueueTime; // Field representing the total massageQueue waiting time
	private double PTQueueTime; // Field representing the total physiotherapyQueue waiting time
	private double trainingQueueTime; // Field representing the total trainingQueue waiting time
	private double turnaroundTime; // Field representing the total turnaround time (total time spent on training and physiotherapy queues and services)
	private double endingTime; // Field representing the ending time of the simulation
	
	// Class constructor
	public Simulation(int playerCount) {
		this.events = new PriorityQueue<Event>();
		this.trainingQueue = new PriorityQueue<Player>(playerCount, new TrainingComparator());
		this.massageQueue = new PriorityQueue<Player>(playerCount, new MassageComparator());
		this.physiotherapyQueue = new PriorityQueue<Player>(playerCount, new PhysiotherapyComparator());
		this.physiotherapists = new ArrayList<Physiotherapist>();
		this.players = new ArrayList<Player>();
		this.physiotherapy = new ArrayList<Player>();
		this.training  = new ArrayList<Player>();
		this.massage  = new ArrayList<Player>();
		this.massageMax = new ArrayList<Player>();
		this.playerCount = playerCount;
		this.invalid = 0;
		this.cancelled = 0;
		this.maxMassage = 0;
		this.maxPT = 0;
		this.maxTraining = 0;
		this.totalMassage = 0;
		this.totalPT = 0;
		this.totalTraining = 0;
		this.massageQueueTime = 0;
		this.PTQueueTime = 0;
		this.trainingQueueTime = 0;
		this.endingTime = 0;
		this.massageCount = 0;
		this.PTCount = 0;
		this.trainingCount = 0;
		this.turnaroundTime = 0;
	}

	// Method for executing events in particular order.
	// Depending on the type of the event, players are either taken out from massage, training, and physiotherapy processes or loaded into respective queues.
	public void execution() {
		while(!events.isEmpty()) {
			double time = events.peek().getArrivalTime();
			if (events.size() == 1) {
				this.endingTime = time;
			}
			LinkedList<Event> EL = new LinkedList<Event>();
			while(events.size() != 0 && Math.abs(events.peek().getArrivalTime() - time) < 0.0000000001) {
				Event E = events.poll();
				EL.add(E);
				Player P = players.get(E.getPlayerID());
				if (E.getMark().equals("m") && P.getMassageCount() == 3) {
					continue;
				} else if (E instanceof EnteringEvent && massageQueue.contains(P) || physiotherapyQueue.contains(P) || trainingQueue.contains(P) || massage.contains(P) || training.contains(P) || physiotherapy.contains(P)) {
					continue;
				}
				players.get(E.getPlayerID()).setDuration(E.getDuration());
			}
			Iterator<Event> it = EL.iterator();
			while (it.hasNext()) {
				Event E = it.next();
				Player P = players.get(E.getPlayerID());
				if (E instanceof DepartingEvent) {
					if (E.getMark().equals("m")) {
						int ind = 0;
						for (int i=0; i<this.masseurs; i++) {
							if (massage.get(i) != null) {
								if(massage.get(i).equals(P)) {
									ind = i;
									this.totalMassage += (time - P.getArrivalTime());
									P.setArrivalTime(time);
									massage.set(ind, null);
								}
							}
						}
										
					} else if (E.getMark().equals("t")) {
						int ind = 0;
						for (int i=0; i<this.trainers; i++) {
							if (training.get(i) != null) {
								if (training.get(i).equals(P)) {
									ind = i;
									training.set(ind, null);
									this.totalTraining += (time - P.getArrivalTime());
									this.turnaroundTime += (time - P.getArrivalTime());
									P.setTrainingTime(time - P.getArrivalTime());
									P.setArrivalTime(time);
									physiotherapyQueue.add(P);
									this.PTCount += 1;
								}
							}
						}
						
					} else if (E.getMark().equals("p")) {
						int ind = 0;
						for (int i=0; i<this.PTs; i++) {
							if (physiotherapy.get(i) != null) {
								if (physiotherapy.get(i).equals(P)) {
									ind = i;
									physiotherapy.set(ind, null);
									this.totalPT += (time - P.getArrivalTime());
									this.turnaroundTime += (time - P.getArrivalTime());
									P.setArrivalTime(time);
								}
							}	
						}
					}	
					
				} else if (E instanceof EnteringEvent) {
					if (E.getMark().equals("m")) {
						if (P.getMassageCount() == 3) {
							invalid += 1;
							continue;
						} else {
							if (massageQueue.contains(P) || physiotherapyQueue.contains(P) || trainingQueue.contains(P) || massage.contains(P) || training.contains(P) || physiotherapy.contains(P)) {								
								cancelled += 1;
								continue;
							}
						}
						
						P.setArrivalTime(time);
						P.setMassageCount(P.getMassageCount() + 1);
						if (P.getMassageCount() == 3) {
							this.massageMax.add(P);
						}
						massageQueue.add(P);
						this.massageCount += 1;	
					
					} else if (E.getMark().equals("t")) {						
						if (massageQueue.contains(P) || physiotherapyQueue.contains(P) || trainingQueue.contains(P) || massage.contains(P) || training.contains(P) || physiotherapy.contains(P)) {
							cancelled += 1;
							continue;
						}
						
						P.setArrivalTime(time);
						trainingQueue.add(P);
						this.trainingCount += 1;
						
					}
				}
			}
		
			massageDeque(time);
			trainingDeque(time);
			PTDeque(time);
		}
	}
	
	// Method for checking whether players waiting in messageQueue can be transferred to massage process in every time iteration.
	public void massageDeque(double time) {
		while(true) {
			if (massageQueue.size() == 0) {
				return;
			}
			Player P = massageQueue.poll();
			int ind = 0;
			for (int i=0; i<this.masseurs; i++) {
				if (massage.get(i) == null) {
					ind = i;
					massage.set(ind, P);
					P.setMassageQueueTime(P.getMassageQueueTime() + time - P.getArrivalTime());
					this.massageQueueTime += (time - P.getArrivalTime());
					P.setArrivalTime(time);
					DepartingEvent DE = new DepartingEvent(P.getID(), time + P.getDuration(), P.getDuration(), "m");
					this.events.add(DE);
					break;
				}
				if (i == this.masseurs -1) {
					this.massageQueue.add(P);
					if (massageQueue.size() > this.maxMassage) {
						this.maxMassage = massageQueue.size();
					}
					return;
				}
			}
		}	
	}
	
	// Method for checking whether players waiting in trainingQueue can be transferred to training process in every time iteration.
	public void trainingDeque(double time) {
		while(true) {
			if (trainingQueue.size() == 0) {
				return;
			}
			Player P = trainingQueue.poll();
			int ind = 0;
			for (int i=0; i<this.trainers; i++) {
				if (training.get(i) == null) {
					ind = i;
					training.set(ind, P);
					P.setTrainingQueueTime(P.getTrainingQueueTime() + time - P.getArrivalTime());
					this.turnaroundTime += (time - P.getArrivalTime());
					this.trainingQueueTime += (time - P.getArrivalTime());
					P.setArrivalTime(time);
					P.setTrainingTime(P.getDuration());
					DepartingEvent DE = new DepartingEvent(P.getID(), time + P.getDuration(), P.getDuration(), "t");
					this.events.add(DE);
					break;
				}
				if (i == this.trainers -1) {
					this.trainingQueue.add(P);
					if (trainingQueue.size() > this.maxTraining) {
						this.maxTraining = trainingQueue.size();
					}
					return;
				}
			}
		}
	}
	
	// Method for checking whether players waiting in physiotherapyQueue can be transferred to physiotherapy process in every time iteration.
	public void PTDeque(double time) {
		while(true) {
			if (physiotherapyQueue.size() == 0) {
				return;
			}
			Player P = physiotherapyQueue.poll();
			int ind = 0;
			for (int i=0; i<this.PTs; i++) {
				if (physiotherapy.get(i) == null) {
					ind = i;
					physiotherapy.set(ind, P);
					P.setPTQueueTime(P.getPTQueueTime() + time - P.getArrivalTime());
					this.turnaroundTime += (time - P.getArrivalTime());
					this.PTQueueTime += (time - P.getArrivalTime());
					P.setArrivalTime(time);
					P.setDuration(physiotherapists.get(ind).getServiceTime());
					DepartingEvent DE = new DepartingEvent(P.getID(), time + P.getDuration(), P.getDuration(), "p");
					this.events.add(DE);
					break;
				}
				if (i == this.PTs -1) {
					this.physiotherapyQueue.add(P);
					if (physiotherapyQueue.size() > this.maxPT) {
						this.maxPT = physiotherapyQueue.size();
					}
					return;
				}
			}
		}
	}
	
	// Getter methods for respective fields
	public ArrayList<Physiotherapist> getPhysiotherapists() {
		return physiotherapists;
	}

	public PriorityQueue<Event> getEvents() {
		return events;
	}

	public int getMasseurs() {
		return masseurs;
	}

	public int getTrainers() {
		return trainers;
	}
	
	public int getMaxMassage() {
		return maxMassage;
	}

	public int getMaxPT() {
		return maxPT;
	}

	public int getMaxTraining() {
		return maxTraining;
	}

	public int getInvalid() {
		return invalid;
	}

	public int getCancelled() {
		return cancelled;
	}

	public int getMassageCount() {
		return massageCount;
	}

	public int getTrainingCount() {
		return trainingCount;
	}

	public int getPTCount() {
		return PTCount;
	}

	public double getTotalMassage() {
		return totalMassage;
	}

	public double getTotalPT() {
		return totalPT;
	}

	public double getTotalTraining() {
		return totalTraining;
	}
	
	public double getEndingTime() {
		return endingTime;
	}

	public double getMassageQueueTime() {
		return massageQueueTime;
	}

	public double getPTQueueTime() {
		return PTQueueTime;
	}

	public double getTrainingQueueTime() {
		return trainingQueueTime;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public double getTurnaroundTime() {
		return turnaroundTime;
	}
	
	public ArrayList<Player> getMassageMax() {
		return massageMax;
	}
	
	// Setter methods for respective fields
	public void setPTs(int PTs) {
		this.PTs = PTs;
		for (int i=0; i<this.PTs; i++) {
			this.physiotherapy.add(null);
		}
	}

	public void setMasseurs(int masseurs) {
		this.masseurs = masseurs;
		for (int i=0; i<this.masseurs; i++) {
			this.massage.add(null);
		}
	}

	public void setTrainers(int trainers) {
		this.trainers = trainers;
		for (int i=0; i<this.trainers; i++) {
			this.training.add(null);
		}
	}
	
}

