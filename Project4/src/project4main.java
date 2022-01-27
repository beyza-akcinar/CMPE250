import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

// Main class of the project. Input/Output operations are handled here.
public class project4main {
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		in.useLocale(Locale.US);
		
	// Input Operations
		int gTrain = in.nextInt();
		ArrayList<Integer> GTcapacity = new ArrayList<Integer>();
		
		for (int i=0; i<gTrain; i++) {
			GTcapacity.add(in.nextInt());
		}
		
		int rTrain = in.nextInt();
		ArrayList<Integer> RTcapacity = new ArrayList<Integer>();
		
		for (int i=0; i<rTrain; i++) {
			RTcapacity.add(in.nextInt());
		}
		
		int gDeer = in.nextInt();
		ArrayList<Integer> GDcapacity = new ArrayList<Integer>();
		
		for (int i=0; i<gDeer; i++) {
			GDcapacity.add(in.nextInt());
		}
		
		int rDeer = in.nextInt();
		ArrayList<Integer> RDcapacity = new ArrayList<Integer>();
		
		for (int i=0; i<rDeer; i++) {
			RDcapacity.add(in.nextInt());
		}
		
		int bags = in.nextInt();
		ArrayList<Integer> gift = new ArrayList<Integer>();
		gift.add(0);
		int giftCount = 0;
		
		Graph graph = new Graph(2 + bags + gTrain + rTrain + rDeer + gDeer);
		
		int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, h = 0;
		int id = 0, id2 = 0, id3 = 0, id4 = 0, id5 = 0, id6 = 0, id7 = 0, id8 = 0;
		
	// Constructing the graph
		
		// Adding edges between the vehicles and the sink
		graph.addEdge(bags + 1, GTcapacity);
		graph.addEdge(bags + gTrain + 1, RTcapacity);
		graph.addEdge(bags + gTrain + rTrain + 1, GDcapacity);
		graph.addEdge(bags + gTrain + rTrain + gDeer + 1, RDcapacity);
		
		// Adding edges between the source & bags and bags & vehicles
		// Bags which has no "a" constraint are each grouped together as one node 
		for (int i=1; i<bags+1; i++) {
			String s = in.next();
			int gifts = in.nextInt();
			gift.add(gifts);
			if (gifts == 0) {
				continue;
			}
			giftCount += gifts;
			if (s.charAt(0) == 'a') {
				if (s.equals("abd") || s.equals("a") || s.equals("ab") || s.equals("ad")) {
					graph.addEdge(i, GTcapacity, bags, 1, gift);
				}
				if (s.equals("abe") || s.equals("a") || s.equals("ab") || s.equals("ae")) {
					graph.addEdge(i, GDcapacity, bags + gTrain + rTrain, 1, gift);
				}
				if (s.equals("acd") || s.equals("a") || s.equals("ac") || s.equals("ad")) {
					graph.addEdge(i, RTcapacity, bags + gTrain, 1, gift);
				}
				if (s.equals("ace") || s.equals("a") || s.equals("ac") || s.equals("ae")) {
					graph.addEdge(i, RDcapacity, bags + gTrain + rTrain + gDeer, 1, gift);
				}
				
			} else if (s.equals("b")) {
				if (a == 1) {
					for (Edge E : graph.getEdges().get(id)) {
						 int newCap = E.getCapacity() + gifts;
						 if (E.getCapacity() != 0) {
							 E.setCapacity(newCap);
						 }
					 }
					for (Edge E : graph.getEdges().get(0)) {
						 if (E.getTarget() == id) {
							 E.setCapacity(E.getCapacity() + gifts);
						 }
					 }
				} else {
					graph.addEdge(i, GTcapacity, bags, gifts, gift);
					graph.addEdge(i, GDcapacity, bags + gTrain + rTrain, gifts, gift);
					a += 1;
					id = i;
				}
			} else if (s.equals("c")) {
				if (b == 1) {
					for (Edge E : graph.getEdges().get(id2)) {
						 int newCap = E.getCapacity() + gifts;
						 if (E.getCapacity() != 0) {
							 E.setCapacity(newCap);
						 }
					 }
					for (Edge E : graph.getEdges().get(0)) {
						 if (E.getTarget() == id2) {
							 E.setCapacity(E.getCapacity() + gifts);
						 }
					 }
				} else {
					graph.addEdge(i, RTcapacity, bags + gTrain, gifts, gift);
					graph.addEdge(i, RDcapacity,  bags + gTrain + rTrain + gDeer, gifts, gift);
					b += 1;
					id2 = i;
				}
			} else if (s.equals("d")) {
				if (c == 1) {
					for (Edge E : graph.getEdges().get(id3)) {
						 int newCap = E.getCapacity() + gifts;
						 if (E.getCapacity() != 0) {
							 E.setCapacity(newCap);
						 }
					 }
					for (Edge E : graph.getEdges().get(0)) {
						 if (E.getTarget() == id3) {
							 E.setCapacity(E.getCapacity() + gifts);
						 }
					 }
				} else {
					graph.addEdge(i, GTcapacity, bags, gifts, gift);
					graph.addEdge(i, RTcapacity, bags + gTrain, gifts, gift);
					c += 1;
					id3 = i;
				}
			} else if (s.equals("e")) {
				if (d == 1) {
					for (Edge E : graph.getEdges().get(id4)) {
						 int newCap = E.getCapacity() + gifts;
						 if (E.getCapacity() != 0) {
							 E.setCapacity(newCap);
						 }
					 }
					for (Edge E : graph.getEdges().get(0)) {
						 if (E.getTarget() == id4) {
							 E.setCapacity(E.getCapacity() + gifts);
						 }
					 }
				} else {
					graph.addEdge(i, GDcapacity, bags + gTrain + rTrain, gifts, gift);
					graph.addEdge(i, RDcapacity,  bags + gTrain + rTrain + gDeer, gifts, gift);
					d += 1;
					id4 = i;
				}
			} else if (s.equals("bd")) {
				if (e == 1) {
					for (Edge E : graph.getEdges().get(id5)) {
						 int newCap = E.getCapacity() + gifts;
						 if (E.getCapacity() != 0) {
							 E.setCapacity(newCap);
						 }
					 }
					for (Edge E : graph.getEdges().get(0)) {
						 if (E.getTarget() == id5) {
							 E.setCapacity(E.getCapacity() + gifts);
						 }
					 }
				} else {
					graph.addEdge(i, GTcapacity, bags, gifts, gift);
					e += 1;
					id5 = i;
				}
			} else if (s.equals("be")) {
				if (f == 1) {
					for (Edge E : graph.getEdges().get(id6)) {
						 int newCap = E.getCapacity() + gifts;
						 if (E.getCapacity() != 0) {
							 E.setCapacity(newCap);
						 }
					 }
					for (Edge E : graph.getEdges().get(0)) {
						 if (E.getTarget() == id6) {
							 E.setCapacity(E.getCapacity() + gifts);
						 }
					 }
				} else {
					graph.addEdge(i, GDcapacity, bags + gTrain + rTrain, gifts, gift);
					f += 1;
					id6 = i;
				}
			} else if (s.equals("ce")) {
				if (g == 1) {
					for (Edge E : graph.getEdges().get(id7)) {
						 int newCap = E.getCapacity() + gifts;
						 if (E.getCapacity() != 0) {
							 E.setCapacity(newCap);
						 }
					 }
					for (Edge E : graph.getEdges().get(0)) {
						 if (E.getTarget() == id7) {
							 E.setCapacity(E.getCapacity() + gifts);
						 }
					 }
				} else {
					graph.addEdge(i, RDcapacity,  bags + gTrain + rTrain + gDeer, gifts, gift);
					g += 1;
					id7 = i;
				}
			} else if (s.equals("cd")) {
				if (h == 1) {
					for (Edge E : graph.getEdges().get(id8)) {
						 int newCap = E.getCapacity() + gifts;
						 if (E.getCapacity() != 0) {
							 E.setCapacity(newCap);
						 }
					 }
					for (Edge E : graph.getEdges().get(0)) {
						 if (E.getTarget() == id8) {
							 E.setCapacity(E.getCapacity() + gifts);
						 }
					 }
				} else {
					graph.addEdge(i, RTcapacity, bags + gTrain, gifts, gift);	
					h += 1;
					id8 = i;
				}
			}
		}
		
	// Dinic Max Flow Algorithm execution
		int maxFlow  = graph.Dinic();
		
	// Output Operations
		out.print(giftCount - maxFlow);
		
		in.close();
		out.close();
	}

}
