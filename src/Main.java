
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

    public static void main(String[] args) {
        int n , m ; //n=vertexes , m=edges
        int traffic , t=0 ;
        ArrayList<ArrayList<Vertex>> adjList = new ArrayList<>();
        HashMap<Integer,Integer> indexes = new HashMap<>();//id , index
        ArrayList<Vertex> vertexes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();


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

        traffic = 0 ;
        while(scanner.hasNextLine()){
            //set all explored and dist to default
            String line = scanner.nextLine();
            String[] str = line.split(" ");
            int time = Integer.parseInt(str[0]);
            int srcID = Integer.parseInt(str[1]);
            int dstID = Integer.parseInt(str[2]);

            //TO_DO : handle traffic and time here



            //update weights for each edge :
            for(Edge edge : edges){
                double weight = edge.getLength() * (1 + 0.3 * traffic);
                edge.setWeight(weight);
            }

            //for each path :
            Vertex src = findVertex(srcID,vertexes);
            Vertex dst = findVertex(dstID,vertexes);

            src.setDist(0);
            while(!dst.isExplored()){
                MinHeap 
            }
        }

    }
}
