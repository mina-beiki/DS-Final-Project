//Mina Beiki - Code : 9831075
import java.util.* ;
//Input : 1. Enter all needed info .     2.Enter the commands.   3.when finished working with program , enter "done" .
public class Main {

    /**
     * Finds a vertex with using the given ID of it . Searches through the arrayList of vertices .
     * @param id long , id of vertex
     * @param vertexes ArrayList , vertices
     * @return vertex found
     */
    public static Vertex findVertex(long id , ArrayList<Vertex> vertexes){
        for(Vertex v : vertexes){
            if(v.getId()==id){
                return v ;
            }
        }
        return null ;
    }

    /**
     * Finds a vertex with using the given distance of it . Searches through the arrayList of vertices .
     * @param dist double , distance of vertex
     * @param vertexes ArrayList , vertices
     * @return vertex found
     */
    public static Vertex findVertexByDist(double dist , ArrayList<Vertex> vertexes){
        for(Vertex v : vertexes){
            if(v.getDist()==dist){
                return v ;
            }
        }
        return null ;
    }

    /**
     * Finds a edge through all the graph by the given vertices of the start and end of the edge (Note that the graph is undirected . )
     * @param v1 one vertex of the edge
     * @param v2 another vertex of the edge
     * @param edges ArrayList of edges
     * @return Edge found
     */
    public static Edge findEdge (Vertex v1 , Vertex v2 , ArrayList<Edge> edges){
        for(Edge e : edges){
            if((e.getV1().equals(v1) && e.getV2().equals(v2))||(e.getV2().equals(v1) && e.getV1().equals(v2))){
                return e ;
            }
        }
        return null ;
    }

    /**
     * Gets the index of the node in the min heap , via the given distance for that specific vertex in the min heap .
     * @param heap min heap of distances
     * @param dist double , distance
     * @return index of that vertex in min heap
     */
    public static int getNodeIndex(PriorityQueue<Double> heap , double dist){
        int i = 0 ;
        for(Double d : heap){
            if(d==dist){
                return i ;
            }
        }
        return 0 ;
    }


    public static void main(String[] args) {
        int n , m ; //n=vertexes , m=edges
        //implementation of the graph using adj list :
        ArrayList<ArrayList<Vertex>> adjList = new ArrayList<>();

        //HashMap for saving the indexes of vertices in the adjList :
        HashMap<Long,Integer> indexes = new HashMap<>();//id , index ; indexes for adjList

        ArrayList<Vertex> vertexes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();

        //saves the time taken to get to destination for each path
        ArrayList<Double> delayTimes = new ArrayList<>();

        //saves all of the command times given by the user
        ArrayList<Double> times = new ArrayList<>();

        HashMap<Vertex,Integer> heapIndexes ;//vertex , index in minHeap

        Scanner scanner = new Scanner(System.in);
        String nStr = scanner.next();
        String mStr = scanner.next();
        n = Integer.parseInt(nStr);
        m = Integer.parseInt(mStr);

        for(int i=0 ; i<n ; i++){
            long id  ;
            double x , y ;
            String idStr = scanner.next();
            id = Long.parseLong(idStr);

            String yStr = scanner.next();
            y = Double.parseDouble(yStr);

            String xStr = scanner.next();
            x = Double.parseDouble(xStr);

            Vertex vertex = new Vertex(x,y,id);

            indexes.put(id,i);
            vertexes.add(vertex);

        }

        for(int i=0 ; i<n ; i++){
            ArrayList<Vertex> list = new ArrayList<>();
            adjList.add(list);
        }

        for(int i=0 ; i<m ; i++){
            String id1Str = scanner.next();
            String id2Str = scanner.next();

            long id1 = Long.parseLong(id1Str);
            long id2 = Long.parseLong(id2Str);

            int index1 = indexes.get(id1);
            Vertex vertex1 = findVertex(id1,vertexes);

            int index2 = indexes.get(id2);
            Vertex vertex2 = findVertex(id2,vertexes);

            adjList.get(index1).add(vertex2);
            adjList.get(index2).add(vertex1);

            Edge edge = new Edge(0,vertex1,vertex2);

            edges.add(edge);
        }

        scanner.nextLine();

        System.out.println("Commands : ");
        //Getting Commands and processing paths :
        while(scanner.hasNextLine()){
            //set all explored and dist to default
            for(Vertex v : vertexes){
                v.reset();
            }

            String line = scanner.nextLine();
            //if user has entered done , exit .
            if(line.equals("done")){
                break;
            }
            String[] str = line.split(" ");
            double time = Double.parseDouble(str[0]);
            long srcID = Long.parseLong(str[1]);
            long dstID = Long.parseLong(str[2]);

            // System.out.println("time = "+time+" srcID = "+srcID+" dstID= "+dstID);

            if(time==0) {//if it is the first command
                //do nothing
            }else{
                int i=0 ;
                for(Double t : times){
                    if(time>t){
                        paths.get(i).decreaseTraffics();
                    }
                    i++ ;
                }
            }

            //update weights for each edge :
            for(Edge edge : edges){
                double weight = edge.getLength() * (1 + 0.3 * edge.getTraffic());
                edge.setWeight(weight);
            }

            //for each path :
            heapIndexes = new HashMap<>();
            Path path = new Path();
            Vertex src = findVertex(srcID,vertexes);
            Vertex dst = findVertex(dstID,vertexes);

            src.setDist(0);
            src.setPrev(null);
            heapIndexes.put(src,0);

            //min heap for distances :
            PriorityQueue<Double> distHeap = new PriorityQueue<>();

            distHeap.add(src.getDist());

            while(!dst.isExplored()){
                //remove v from min heap cause it is explored
                Vertex v = findVertexByDist(distHeap.peek(),vertexes);
                v.setExplored(true);
                //System.out.println(v+" is explored , prev = "+v.getPrev());
                //update index hash map and min heap  :
                distHeap.remove(v.getDist());
                heapIndexes.remove(v);

                int vIndex = indexes.get(v.getId());
                for(Vertex w : adjList.get(vIndex)){
                    Edge vwEdge = findEdge(v,w,edges);
                    if(v.getDist() + vwEdge.getWeight() < w.getDist()){

                        if(heapIndexes.containsKey(w)){
                            distHeap.remove(w.getDist());
                            heapIndexes.remove(w);
                        }
                        w.setDist(v.getDist() + vwEdge.getWeight());

                        distHeap.add(w.getDist());
                        heapIndexes.put(w,getNodeIndex(distHeap,w.getDist()));
                        w.setPrev(v);
                    }
                }
            }

            //add vertices to path :
            Vertex vertex = dst;
            path.insertVertex(dst);
            while(vertex!=src){
                path.insertVertex(vertex.getPrev());
                vertex = vertex.getPrev();
            }
            //path.insertVertex(src);
            path.reverseVertices();

            System.out.println("path : ");
            for(Vertex v : path.getVertices()){
                System.out.print(v+" ");
            }
            path.insertEdges(edges);
            path.increaseTraffics();
            paths.add(path);

            //calculate time :
            double totalTime = 0;
            for(Edge e : path.getEdges()){
                totalTime += e.getWeight();
            }
            totalTime = 120 * totalTime;
            delayTimes.add(totalTime);
            System.out.println("time = "+totalTime);
            times.add(totalTime + time);
            System.out.println("////////");

        }


    }
}
