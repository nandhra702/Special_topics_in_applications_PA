import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class LandmarkGenerator {

    private static int euclidean(List<Vertex> coordinates, int vertex, double xCenter, double yCenter) {
        double diffX = coordinates.get(vertex).x - xCenter;
        double diffY = coordinates.get(vertex).y - yCenter;
        return (int) Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static void precomputeLandmarkDistances(Graph g, String fromLandmarkPath, String toLandmarkPath, HashSet<Integer> landmarks) throws IOException { // complete this function
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
    }
}
