

import java.util.PriorityQueue;

public class Dijkstra_Bidirectional extends Graph {

	public int numEdgesRelaxedSource;
	public int numEdgesRelaxedTarget;

	//we also need to maintain a global best distance which is actually the minimum one
	int best ;


	public Dijkstra_Bidirectional(Graph g) {
		super(g);
	}

	public int execute(int source, int target) { // complete this function
	// okay, so what we need to do is an alternating dijkstra from start node and end node.
	//which means, we will have individual entities like priority queues, boolean arrays etc.
	//we maintain a current node value, which will indicate where the other guy is standing right now,
	//which will be basically the top of priority queue.
	//once we land at the same node, we move to the next phase, where we will keep in mind, the minimum distance found yet.

	//okay, so we start with assigning each of them, their resources.
	int[] distance_source = new int[numVertices];
	int[] distance_target = new int[numVertices];
	boolean[] closed_source = new boolean[numVertices];
	boolean[] closed_target = new boolean[numVertices];
	numEdgesRelaxedSource=0;
	numEdgesRelaxedTarget=0;

	//setting the global maxima, that we aim to minimize
	best = Integer.MAX_VALUE;

	//same as dijkstra
		for (int i = 0; i < numVertices; i++) {
				distance_source[i] = Integer.MAX_VALUE;
				closed_source[i] = false;
				distance_target[i] = Integer.MAX_VALUE;
				closed_target[i] = false;
		}

		distance_source[source]=0;
		distance_target[target]=0;

		PriorityQueue<Element> pq_source = new PriorityQueue<Element>(new ElementComparator());
		pq_source.add(new Element(source, 0));

		PriorityQueue<Element> pq_target = new PriorityQueue<Element>(new ElementComparator());
		pq_target.add(new Element(target, 0));

		//now, we have to make a loop, where, alternate dijkstra takes place
		while(pq_source.size()>0 && pq_target.size()>0){

			//FIRST WE DO FROM SOURCE SIDE
			Element minELementSource = pq_source.remove();
			int minVertexSource = minELementSource.item;



			//WE CAN NOT TERMINATE HERE AS MAYBE THERE IS ANOTHER PATH, THATS BETTER THAN THIS

			// if( minVertexSource == target){
			// 	break;
			// }

			if(closed_source[minVertexSource]){
				continue;
			}
			closed_source[minVertexSource] = true;
			numEdgesRelaxedSource+=adjList.get(minVertexSource).size();
			for(Edge adjEdgeSource : adjList.get(minVertexSource)){
				int adjVertexSource = adjEdgeSource.dest;
				if(!closed_source[adjVertexSource]){
					int dist_S = distance_source[minVertexSource]+ adjEdgeSource.weight;
					if (dist_S < distance_source[adjVertexSource]) {
						distance_source[adjVertexSource] = dist_S;
						pq_source.add(new Element(adjVertexSource, dist_S));
					}
				}

			}

			if(closed_target[minVertexSource]){
				//means we have found an intersection in paths
				best = Math.min(best,distance_source[minVertexSource] + distance_target[minVertexSource]);
			}

			/////////////////////////////////////////////////////////////
			//NOW WE DO FROM THE OTHER SIDE.
			Element minELementTarget = pq_target.remove();
			int minVertexTarget = minELementTarget.item;
			//as now, we move in the opposite direction 


		//WE CAN NOT TERMINATE HERE AS MAYBE THERE IS ANOTHER PATH, THATS BETTER THAN THIS
		//	if( minVertexTarget == source){
		//	break;
		//	}



			if(closed_target[minVertexTarget]){
				continue;
			}
			closed_target[minVertexTarget] = true;
			numEdgesRelaxedTarget+=adjList.get(minVertexTarget).size();
			for(Edge adjEdgeTarget : adjList.get(minVertexTarget)){
				int adjVertexTarget = adjEdgeTarget.dest;
				if(!closed_target[adjVertexTarget]){
					int dist_T = distance_target[minVertexTarget]+ adjEdgeTarget.weight;
					if (dist_T < distance_target[adjVertexTarget]) {
						distance_target[adjVertexTarget] = dist_T;
						pq_target.add(new Element(adjVertexTarget, dist_T));
					}
				}

			}
			
			if(closed_source[minVertexTarget]){
				//means we have found an intersection in paths
				best = Math.min(best,distance_source[minVertexTarget] + distance_target[minVertexTarget]);
			}
		//OKAY, ONE THING I DID WRONG EARLIER WAS, WE DONT COMPARE TOP ELEMENTS DISCOVERED, AS PATHS CAN
		//CROSS. SO WE NEED TO KEEP TRACK OF INTERSECTION OF WHATEVER HAS BEEN DISCOVERED ON BOTH SIDES.
			 
			if(minVertexSource==minVertexTarget){
				//means, we have found a path.

			}

			if (pq_source.peek().priority + pq_target.peek().priority >= best) {
   				break;
			}



		}




	return best;

	}
}
