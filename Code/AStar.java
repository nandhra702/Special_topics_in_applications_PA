import java.util.PriorityQueue;

public abstract class AStar extends Graph {

    public int numEdgesRelaxed;

    public AStar(Graph g) {
        super(g);
    }
    
    public int execute(int source, int target) { // complete this function
    //okay, so its same as dijkstra. The change is that here, we will order/put the distances in minheap as
    /* d = f + h, where h is the undersestimating heuristic (BUT WE PUT THIS ONLY IN THE HEAP).
    // This way, I seach with a sense of direction
    as now I wont go looking for paths that give a higher/farther estimate to the target than others. */

    //hence, here we need to simply call the heuristic function while adding in pq. In the final distance
    //array, we will only store the actual values and not those with the heuristics in them.

    int[] distance = new int[numVertices];
    boolean[] closed = new boolean[numVertices];
	numEdgesRelaxed = 0;

	for (int i = 0; i < numVertices; i++) {
		distance[i] = Integer.MAX_VALUE;
		closed[i] = false;
	}

    distance[source] = 0;
		PriorityQueue<Element> open = new PriorityQueue<Element>(new ElementComparator());
        //here we make first change, instead of adding zero, we add the heuristic data
		open.add(new Element(source, heuristic(source, target))); 

    while (open.size() > 0) {
			Element minElement = open.remove();
			int minVertex = minElement.item;
			if (minVertex == target)
				break;
			if (closed[minVertex])
				continue;
			closed[minVertex] = true;
			numEdgesRelaxed += adjList.get(minVertex).size();
			for (Edge adjEdge : adjList.get(minVertex)) {
				int adjVertex = adjEdge.dest;
				if (!closed[adjVertex]) {
					int dist = distance[minVertex] + adjEdge.weight ;
                    //since this is relaxation, we dont include heuristic here.Its only used in priority q
					if (dist < distance[adjVertex]) {
						distance[adjVertex] = dist;
						open.add(new Element(adjVertex, dist + heuristic(adjVertex, target)));
					}
				}
			}
		}
		return distance[target];
    
    }

    protected abstract int heuristic(int v, int target);
}
