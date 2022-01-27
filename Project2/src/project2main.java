import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Scanner;

// Main class of the project
public class project2main {
	public static void main(String[] args) throws FileNotFoundException {

		// Taking file names as arguments
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		in.useLocale(Locale.US);
		
		// Input Operations
		int N = in.nextInt();
		
		Simulation simulation = new Simulation(N);
		
		for (int i=0; i<N; i++) {
			int ID = in.nextInt();
			int skill = in.nextInt();
			Player P = new Player(ID, skill);
			simulation.getPlayers().add(P);
		}
		
		int A = in.nextInt();
		
		for (int i=0; i<A; i++) {
			String s = in.next();
			if (s.equals("m")) {
				int playerID = in.nextInt();
				double arrivalTime = in.nextDouble();
				double duration = in.nextDouble();
				EnteringEvent EE = new EnteringEvent(playerID, arrivalTime, duration, "m");
				simulation.getEvents().add(EE);
				
			} else if (s.equals("t")) {
				int playerID = in.nextInt();
				double arrivalTime = in.nextDouble();
				double duration = in.nextDouble();
				EnteringEvent EE = new EnteringEvent(playerID, arrivalTime, duration, "t");
				simulation.getEvents().add(EE);
				
			}
		}
		
		int P = in.nextInt();
		
		for (int i=0; i<P; i++) {
			double serviceTime = in.nextDouble();
			Physiotherapist T = new Physiotherapist(serviceTime);
			simulation.getPhysiotherapists().add(T);
		}
		
		int C = in.nextInt();
		int M = in.nextInt();
		simulation.setTrainers(C);
		simulation.setMasseurs(M);
		simulation.setPTs(P);
		
		// Simulation execution
		simulation.execution();
			
		
		// Output Operations
		out.println(simulation.getMaxTraining());
		out.println(simulation.getMaxPT());
		out.println(simulation.getMaxMassage());
		
		if (simulation.getTrainingCount() != 0) {
			out.printf(Locale.US, "%.3f\n", simulation.getTrainingQueueTime()/(double)simulation.getTrainingCount());
		} else {
			out.println("0.000");
		}
		
		if (simulation.getPTCount() != 0) {
			out.printf(Locale.US, "%.3f\n", simulation.getPTQueueTime()/(double)simulation.getPTCount());
		} else {
			out.println("0.000");
		}
		
		if (simulation.getMassageCount() != 0) {
			out.printf(Locale.US, "%.3f\n", simulation.getMassageQueueTime()/(double)simulation.getMassageCount());
		} else {
			out.println("0.000");
		}
		
		if (simulation.getTrainingCount() != 0) {
			out.printf(Locale.US, "%.3f\n", simulation.getTotalTraining()/(double)simulation.getTrainingCount());
		} else {
			out.println("0.000");
		}
		
		if (simulation.getPTCount() != 0) {
			out.printf(Locale.US, "%.3f\n", simulation.getTotalPT()/(double)simulation.getPTCount());
		} else {
			out.println("0.000");
		}
		
		if (simulation.getMassageCount() != 0) {
			out.printf(Locale.US, "%.3f\n", simulation.getTotalMassage()/(double)simulation.getMassageCount());
		} else {
			out.println("0.000");
		}
		
		if (simulation.getTrainingCount() != 0) {
			out.printf(Locale.US, "%.3f\n", simulation.getTurnaroundTime()/(double)simulation.getTrainingCount());
		} else {
			out.println("0.000");
		}
		
		double max1 = -1;
		int ind1 = -1;
		
		for(int i=0; i<simulation.getPlayers().size(); i++) {
			Player F = simulation.getPlayers().get(i);
			if (F.getPTQueueTime() > max1) {
				ind1 = i;
				max1 = F.getPTQueueTime();
			}
		}
		
		out.print(ind1);
		out.printf(Locale.US, " %.3f\n", max1);
		
		Collections.sort(simulation.getMassageMax(), new Comparator<Player>() {
			@Override
			public int compare(Player p1, Player p2) {
				if (p1.getMassageQueueTime() < p2.getMassageQueueTime()) {
					return -1;
				} else if (p1.getMassageQueueTime() > p2.getMassageQueueTime()){
					return 1;
				} else {
					return p1.getID() - p2.getID();
				}
			}
		});
		
		if (simulation.getMassageMax().size() != 0) {
			out.print(simulation.getMassageMax().get(0).getID());
			out.printf(Locale.US, " %.3f\n", simulation.getMassageMax().get(0).getMassageQueueTime());
		} else {
			out.println("-1 -1");
		}
		
		out.println(simulation.getInvalid());
		out.println(simulation.getCancelled());
		out.printf(Locale.US, "%.3f\n", simulation.getEndingTime());
		
		in.close();
		out.close();
	}
}
