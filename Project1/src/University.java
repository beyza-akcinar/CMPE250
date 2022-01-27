
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

// Class for representing the university and its dormitory office which allocate students to available houses at the beginning of every semester
public class University {
	
	// List of students in need of a house
	private LinkedList<Student> waitingStudents; 
	// List of available houses
	private LinkedList<House> remainingHouses; 
	// List of all students
	private ArrayList<Student> students;
	// List of all houses
	private ArrayList<House> houses;
	// List of occupied houses
	private ArrayList<House> fullHouses;
	
	// Class Constructor
	public University() {
		this.waitingStudents = new LinkedList<Student>();
		this.remainingHouses = new LinkedList<House>();
		this.students = new ArrayList<Student>();
		this.houses = new ArrayList<House>();
		this.fullHouses = new ArrayList<House>();
	}
	
	// Method for removing graduated students from waitingStudents list
	public void graduate() {
		for (Student s : students) {
			if (s.getDuration() == 0) {
				if (waitingStudents.contains(s)) {
					waitingStudents.remove(s);
				}		
			}
		}
	}
	
	// Method for checking whether new allocations can be made after a new semester, and the ones possible are arranged 
	public void checkPlacement() {
		if (waitingStudents.size() > 0 && remainingHouses.size() > 0) {	
			
			Iterator<Student> iter1 = waitingStudents.iterator();
			while (true) {
				if (!iter1.hasNext()) {
					break;
				}
				Student student = iter1.next();
				Iterator<House> iter2 = remainingHouses.iterator();
				while (true) {
					if (!iter2.hasNext()) {
						break;
					}
					House house = iter2.next();
					if (house.getRating() >= student.getRating()) {
						student.setHouse(house);
						house.setDuration(student.getDuration());
						fullHouses.add(house);
						iter1.remove();
						iter2.remove();
						break;
					}
				}
			}
		}
	}
	
	// Method for applying changes for both houses and students when a new semester arrives
	public void nextSemester() {
		for (Student s : students) {
			if (s.getDuration() > 1) {
				s.setDuration(s.getDuration()-1);
			} else if (s.getDuration() == 1) {
				s.setDuration(s.getDuration()-1);
				if (s.getHouse() != null) {
					fullHouses.remove(s.getHouse());
					Iterator<House> it = remainingHouses.iterator();
					int index = -1;
					while (it.hasNext()) {
						House h = it.next();
						if (s.getHouse().getId() < h.getId()) {
							index = remainingHouses.indexOf(h);
							break;
						}
					}
					if (index == -1) {
						remainingHouses.add(s.getHouse());
					} else {
						remainingHouses.add(index, s.getHouse());
					}
					s.getHouse().setDuration(0);
					s.setHouse(null);
				}
			} 
		}
		for (House h : houses) {
			if (h.getDuration() > 0) {
				h.setDuration(h.getDuration()-1);
				if (h.getDuration() == 0) {
					if (!remainingHouses.contains(h)) {
						Iterator<House> it = remainingHouses.iterator();
						int index = -1;
						while (it.hasNext()) {
							House d = it.next();
							if (h.getId() < d.getId()) {
								index = remainingHouses.indexOf(d);
								break;
							}
						}
						if (index == -1) {
							remainingHouses.add(h);
						} else {
							remainingHouses.add(index, h);
						}
					}
				}
			}
		}
	}

	// Getter methods for respective fields
	public LinkedList<Student> getWaitingStudents() {
		return waitingStudents;
	}

	public LinkedList<House> getRemainingHouses() {
		return remainingHouses;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}
	
	public ArrayList<House> getHouses() {
		return houses;
	}

	public ArrayList<House> getFullHouses() {
		return fullHouses;
	}

}
