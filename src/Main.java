
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
        int traffic ;
        ArrayList<ArrayList<Vertex>> adjList = new ArrayList<>();
        HashMap<Integer,Integer> indexes = new HashMap<>();//id , index ; indexes for adjList
        ArrayList<Vertex> vertexes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Path> paths = new ArrayList<>();
        ArrayList<Double> delayTimes = new ArrayList<>();
        ArrayList<Double> times = new ArrayList<>();
        HashMap<Vertex,Integer> heapIndexes ;//vertex , index in minHeap

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
            double time = Double.parseDouble(str[0]);
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
            heapIndexes = new HashMap<>();
            Path path = new Path();
            Vertex src = findVertex(srcID,vertexes);
            Vertex dst = findVertex(dstID,vertexes);

            src.setDist(0);
            src.setPrev(null);
            heapIndexes.put(src,0);
            PriorityQueue<Double> distHeap = new PriorityQueue<>();

            distHeap.add(src.getDist());

            while(!dst.isExplored()){
                //remove v from min heap cause it is explored
                Vertex v = findVertexByDist(distHeap.peek(),vertexes);
                v.setExplored(true);
                //System.out.println(v+" is explored , prev = "+v.getPrev());
                //update index hashmap :
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
