import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class LandmarkGenerator {

    private static int euclidean(List<Vertex> coordinates, int vertex, double xCenter, double yCenter) {
        double diffX = coordinates.get(vertex).x - xCenter;
        double diffY = coordinates.get(vertex).y - yCenter;
        return (int) Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static void precomputeLandmarkDistances(Graph g, String fromLandmarkPath, String toLandmarkPath, HashSet<Integer> landmarks) throws IOException { // complete this function
     
     Dijkstra_SSSP dijkstra_sssp_object = new Dijkstra_SSSP(g); //this is my new object
     int[][] matrix = new int[landmarks.size()][g.numVertices];
     int rowIndex=0;

    
    for(int landmark :landmarks){
        //iterate through the landmarks. For each one, we apply dijsktras algo:
        int[] distances_from_landmark = dijkstra_sssp_object.execute(landmark);
       

        
        for (int v = 0; v < g.numVertices; v++) {
            matrix[rowIndex][v] = distances_from_landmark[v];
        }

        rowIndex++;
    }

    // 4. Write distances to file
    LandmarkReaderWriter.writeDistances(matrix,landmarks.size(),g.numVertices,
            fromLandmarkPath
    );

    }

    /**
     * Creates n random landmarks.
     */
    public static void makeRandomLandmarks(Graph g, int n, String fromLandmarkPath, String toLandmarkPath) throws IOException {

        HashSet<Integer> landmarks = new HashSet<>();
        Random rand = new Random();
        while (landmarks.size() != n)
            landmarks.add(rand.nextInt(g.numVertices));
        precomputeLandmarkDistances(g, fromLandmarkPath, toLandmarkPath, landmarks);
    }

    /**
     * Creates gridDim^2 grid landmarks.
     */
    public static void makeGridLandmarks(Graph g, int gridDim, String fromLandmarkPath, String toLandmarkPath) throws IOException { // complete this function

    double maxX = 0; 
    double minX = Integer.MAX_VALUE;
    double maxY = 0; 
    double minY = Integer.MAX_VALUE;

    for(int i=0;i<g.numVertices;i++){
        Vertex v = g.coordinates.get(i);
        double xcoord = v.x;
        if(xcoord > maxX){
            maxX = xcoord;
        }
        if(xcoord < minX){
            minX = xcoord;
        }
        double ycoord = v.y;
        if(ycoord > maxY){
            maxY = ycoord;
        }
        if(ycoord < minY){
            minY = ycoord;
        }

    }
    

    double gridXsize = (maxX-minX)/gridDim;
    double gridYsize = (maxY-minY)/gridDim;

    //////NOW WE HAVE TO COMPUTE THE CENTER POINTS FOR EACH GRID BLOCK
    
    HashSet<Integer> landmarks = new HashSet<>();

    for(int i=0;i<gridDim;i++){
        for(int j=0;j<gridDim;j++){

            //now for each block, indices [i][j]
            double x_start = minX + i*gridXsize;
            double y_start = minY + j*gridYsize;

            //now the center of this block is
            double centerx = x_start + gridXsize/2;
            double centery = y_start + gridYsize/2;

            //now we need to find distance of this center from all vertices.
            //we then assign the closest vertex as landmark for this center
            double mindist = Integer.MAX_VALUE;
            int closestLandmark = -1;

            for(int k=0;k<g.numVertices;k++){

                Vertex ver = g.coordinates.get(k);
                double dx = ver.x - centerx;
                double dy = ver.y - centery;
                double dist = dx*dx + dy*dy;

                if(dist<mindist){
                   mindist= dist;
                   closestLandmark = k;
                }

            }

            if (closestLandmark != -1) {
            landmarks.add(closestLandmark);
        }
            //so this simply denotes the number of vertex, thats a closest landmark in a grid 
            //this way, in the end, I'll have as many landmarks as I have boxes in a grid.

        
        }
    }

    precomputeLandmarkDistances(g, fromLandmarkPath, toLandmarkPath, landmarks);



    }
}
