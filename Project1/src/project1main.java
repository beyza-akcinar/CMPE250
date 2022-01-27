
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.File;

public class project1main {
	public static void main(String[] args) throws FileNotFoundException {
		 
		//Taking file names as arguments
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		in.useLocale(Locale.US);
		
		//Initializing university object
		University university = new University();
		int maxDuration = 0;
		
		// Input Operations
		// Initializing student and house objects
		while (in.hasNext()) {
			String ind = in.next();
			if (ind.equals("s")) {
				int id = in.nextInt();
				String name = in.next();
				int duration = in.nextInt();
				if (duration > maxDuration) {
					maxDuration = duration;
				}
				double rating = in.nextDouble();
				Student st = new Student(id, name, duration, rating);
				university.getStudents().add(st);
				if (st.getDuration() !=0) {
					university.getWaitingStudents().add(st);
				}
				
			} else if (ind.equals("h")) {
				int id = in.nextInt();
				int duration = in.nextInt();
				double rating = in.nextDouble();
				House h = new House(id, duration, rating);
				university.getHouses().add(h);
				if (duration != 0) {
					university.getFullHouses().add(h);
				} else {
					university.getRemainingHouses().add(h);
				}
			}
			
		}
		
		// Sorting Collections
		
		Collections.sort(university.getRemainingHouses(), new Comparator<House>() {
	        @Override
	        public int compare(House o1, House o2) {
	            return o1.getId() - o2.getId();
	        }
	    });
		
		Collections.sort(university.getWaitingStudents(), new Comparator<Student>() {
	        @Override
	        public int compare(Student o1, Student o2) {
	            return o1.getId() - o2.getId();
	        }
	    });
		
		Collections.sort(university.getHouses(), new Comparator<House>() {
	        @Override
	        public int compare(House o1, House o2) {
	            return o1.getId() - o2.getId();
	        }
	    });
		
		Collections.sort(university.getStudents(), new Comparator<Student>() {
	        @Override
	        public int compare(Student o1, Student o2) {
	            return o1.getId() - o2.getId();
	        }
	    });
		
		// University Simulation Execution
		// Iterates until all students graduate (when their duration field becomes 0)
		// Every semester possible placements are made, graduated students move from their houses so that another student can be placed
		for (int i=0; i< maxDuration; i++) {
			university.checkPlacement();
			university.nextSemester();
			university.graduate();
		}
		
		// Output Operations
		// Printing names of the students (sorted with respect to increasing id order) who could not stay at any house
		for (Student s  : university.getStudents()) {
			if (!s.getHoused()) {
				out.println(s.getName());
			}
		}
		
		in.close();
		out.close();
	}

}
