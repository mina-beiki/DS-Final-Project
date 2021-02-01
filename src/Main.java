
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
            if(e.getV1().equals(v1) && e.getV2().equals(v2)){
                return e ;
            }
        }
        return null ;
    }

    public static void main(String[] args) {
        int n , m ; //n=vertexes , m=edges
        int traffic , t=0 ;
        ArrayList<ArrayList<Vertex>> adjList = new ArrayList<>();
        HashMap<Integer,Integer> indexes = new HashMap<>();//id , index ; indexes for adjList
        ArrayList<Vertex> vertexes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        HashMap<Vertex,Integer> heapIndexes = new HashMap<>();//unexplored vertex, index in minHeap


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

            Edge edge1 = new Edge(0,vertex1,vertex2);
            Edge edge2 = new Edge(0,vertex2,vertex1);

            edges.add(edge1);
            edges.add(edge2);

        }

        scanner.nextLine();
        traffic = 0 ;
        while(scanner.hasNextLine()){
            //set all explored and dist to default



            String line = scanner.nextLine();
            String[] str = line.split(" ");
            int time = Integer.parseInt(str[0]);
            int srcID = Integer.parseInt(str[1]);
            int dstID = Integer.parseInt(str[2]);

            System.out.println("time = "+time+" srcID = "+srcID+" dstID= "+dstID);
            //TO_DO : handle traffic and time here
            //t is the previous time
            if(t==0){//if it is the first command
                traffic = 0 ;
            }else if (time<t){

            }
            t = time ;

            //update weights for each edge :
            for(Edge edge : edges){
                double weight = edge.getLength() * (1 + 0.3 * traffic);
                edge.setWeight(weight);
            }

            //for each path :
            Vertex src = findVertex(srcID,vertexes);
            Vertex dst = findVertex(dstID,vertexes);

            MinHeap distHeap = new MinHeap(n);
            for(Vertex v : vertexes){
                distHeap.insert(v.getDist());
            }
            distHeap.minHeap();
            //save info to heapIndexes :
            int i=0;
            for(Double dist : distHeap.getHeap()){
                Vertex vertex = findVertexByDist(dist,vertexes);
                heapIndexes.put(vertex,i);
                i++ ;
            }

            src.setDist(0);
            while(!dst.isExplored()){
                //remove v from min heap cause it is explored
                Vertex v = findVertexByDist(distHeap.remove(),vertexes);
                v.setExplored(true);
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
                        distHeap.deleteNode(wIndex);
                        heapIndexes.remove(w);
                        distHeap.insert(w.getDist());
                        distHeap.minHeap();
                        heapIndexes.put(w,distHeap.getNodeIndex(w.getDist()));
                        //update hash map:

                        //update prev
                    }
                }
            }
        }

    }
}
