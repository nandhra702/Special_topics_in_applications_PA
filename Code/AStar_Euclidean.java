

public class AStar_Euclidean extends AStar {

	public AStar_Euclidean(Graph g) {
		super(g);
	}

	protected int heuristic(int v, int target) { // complete this function

	//its written use graph superclass, and graph superclass has a coordinates entity.
	//so now, we implement eucledian distances. 


	Vertex sCoord = coordinates.get(v);
	Vertex tCoord = coordinates.get(target);


	double xhalf = Math.pow(sCoord.x-tCoord.y, 2);
    double yhalf = Math.pow(sCoord.y-tCoord.y,2);

    return (int) Math.sqrt(xhalf * xhalf + yhalf * yhalf);
	//we return integer because this function is defined to return an integer
	}
}
