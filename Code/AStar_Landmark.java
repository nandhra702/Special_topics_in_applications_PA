

import java.io.FileNotFoundException;

public class AStar_Landmark extends AStar {

	// stores the shortest path lengths from each landmark (row index) to each vertex (column index)
	private int[][] from;

	// stores the shortest path lengths to each landmark (row index) from each vertex (column index)
	// this is relevant for directed graphs only
	private int[][] to;

	public AStar_Landmark(Graph g, String fromLandmarkFilePath, String toLandmarkFilePath) throws FileNotFoundException {
		super(g);
		from = LandmarkReaderWriter.readDistances(fromLandmarkFilePath);
		if (!g.is_undirected)
			to = LandmarkReaderWriter.readDistances(toLandmarkFilePath);
	}

	//okay, so we need to complete this function. Let me explain how this heuristic works:
	/*So, select some landmarks. Then do dijkstra for every landmark on the whole graph
	so this way, you have distances from every vertex to the landmark and distance from the 
	landmark to the target vertex.
	
	Now what we do is, using the triangle inequality, for EVERY landmark, we compute the expected
	/heuristic distace between source and target ( straight line types). Now that we have all min. values
	of the 3rd side of triangles, we pick the maximum one as a heuristic. REASON being:

	See, always, in two points, the shortest distance is the straight line distance. So, if 
	the distances b.w both points using a landmark shows a high value, means the landmark is 
	close to the actual straight line path that may or maynot exist between source and target.
	Hence its the best way to move forward. The equality in the documentation equations states that
	the heuristic value can be equal to the distace ( cause it is the heuristic).

	//this might not be a very good way to look at it, I have realised it now, 
	as its essentially, how, this side has to be more than difference of other 2
	*/

	/*
	pseudocode
	int maxval = 0;
	for( all the landmarks){
	int val = 	abs(distancefromsource to landmark - fistance from landmark to target)
	maxval = max(maxval,val);
	}*/



	protected int heuristic(int v, int target) { // complete this function

	int heuvalue = 0;

	for(int l=0;l<from.length;l++){
		int checkval = Math.abs(from[l][v] - from[l][target]);
		if(checkval > heuvalue){
			heuvalue = checkval;
		}
	}

	return heuvalue;

	}
}
