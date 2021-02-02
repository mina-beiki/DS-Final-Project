
import java.util.* ;

public class Main {

    public static Vertex findVertex(int id , ArrayList<Vertex> vertexes){
        for(Vertex v : vertexes){
            if(v.getId()==id){
                return v ;
            }
        }
        return null ;
    }

    public static Vertex findVertexByDist(double dist , ArrayList<Vertex> vertexes){
        for(Vertex v : vertexes){
            if(v.getDist()==dist){
                return v ;
            }
        }
        return null ;
    }

    public static Edge findEdge (Vertex v1 , Vertex v2 , ArrayList<Edge> edges){
        for(Edge e : edges){
            if((e.getV1().equals(v1) && e.getV2().equals(v2))||(e.getV2().equals(v1) && e.getV1().equals(v2))){
                return e ;
            }
        }
        return null ;
    }

    public static int getNodeIndex(PriorityQueue<Double> minHeap , double dist){
        int i = 0 ;
        for(Double d : minHeap){
            if(d==dist){
                return i ;
            }
            i++;
        }
        return 0 ;
    }

    public static void main(String[] args) {
        int n , m ; //n=vertexes , m=edges
        int traffic ;
        ArrayList<ArrayList<Vertex>> adjList = new ArrayList<>();
        HashMap<Integer,Integer> indexes = new HashMap<>();//id , index ; indexes for adjList
        ArrayList<Vertex> vertexes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        HashMap<Vertex,Integer> heapIndexes = new HashMap<>();//unexplored vertex, index in minHeap
        ArrayList<Path> paths = new ArrayList<>();
        ArrayList<Double> times = new ArrayList<>();



        Scanner scanner = new Scanner(System.in);
        String nStr = scanner.next();
        String mStr = scanner.next();
        n = Integer.parseInt(nStr);
        m = Integer.parseInt(mStr);

        for(int i=0 ; i<n ; i++){
            int id  ;
            double x , y ;
            String idStr = scanner.next();
            id = Integer.parseInt(idStr);

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

            int id1 = Integer.parseInt(id1Str);
            int id2 = Integer.parseInt(id2Str);

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
        traffic = 0 ;

        while(scanner.hasNextLine()){
            //set all explored and dist to default
            for(Vertex v : vertexes){
                v.reset();
            }


            String line = scanner.nextLine();
            String[] str = line.split(" ");
            int time = Integer.parseInt(str[0]);
            int srcID = Integer.parseInt(str[1]);
            int dstID = Integer.parseInt(str[2]);

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
            Path path = new Path();
            Vertex src = findVertex(srcID,vertexes);
            Vertex dst = findVertex(dstID,vertexes);

            src.setDist(0);
            PriorityQueue<Double> distHeap = new PriorityQueue<>();
            for(Vertex v : vertexes){
                distHeap.add(v.getDist());
            }

            //save info to heapIndexes :
            int i=0;

            for (Double dist : distHeap) {
                Vertex vertex = findVertexByDist(dist, vertexes);
                heapIndexes.put(vertex, i);
                i++;
            }

            //print dist heap :
            /*Object[] arr = distHeap.toArray();
            System.out.println("Value in array: ");
            for (int j = 0; j < arr.length; j++)
                System.out.println("Value: " + arr[j].toString());*/

        ////////////////////////

            while(!dst.isExplored()){
                //remove v from min heap cause it is explored
                Vertex v = findVertexByDist(distHeap.remove(),vertexes);
                v.setExplored(true);
                System.out.println(v+" is explored.");
                path.insertVertex(v);
                //update index hashmap :
                heapIndexes.remove(v);

                int vIndex = indexes.get(v.getId());
                for(Vertex w : adjList.get(vIndex)){
                    Edge vwEdge = findEdge(v,w,edges);
                    if(v.getDist() + vwEdge.getWeight() < w.getDist()){
                        w.setDist(v.getDist() + vwEdge.getWeight());
                        //find w index in minheap using hashmap :
                        int wIndex = heapIndexes.get(w);
                        //update min heap:
                        distHeap.remove(wIndex);
                        heapIndexes.remove(w);
                        distHeap.add(w.getDist());
                        heapIndexes.put(w,getNodeIndex(distHeap,w.getDist()));
                    }
                }
            }
            paths.add(path);
            path.insertEdges(edges);
            path.increaseTraffics();

            //calculate time :
            double totalTime = 0;
            for(Edge e : path.getEdges()){
                totalTime += e.getWeight();
            }
            totalTime = 120 * totalTime;
            times.add(totalTime);

            //System.out.println("path= "+path.getVertices());

            ///////////////////
        }

    }
}
