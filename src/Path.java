import java.util.* ;

public class Path {
    private ArrayList<Edge> edges ;
    private ArrayList<Vertex> vertices ;

    public Path() {
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void resetEdges() {
        for(Edge e : edges){
            e.setTraffic(0);
        }
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public void insertEdges(){
        for(int i=0 ; i<vertices.size()-1 ; i++){
            Vertex v1 = vertices.get(i);
            Vertex v2 = vertices.get(i+1);

            for(Edge e : edges){
                if((e.getV1().equals(v1) && e.getV2().equals(v2))||e.getV2().equals(v1) && e.getV1().equals(v2)){
                    edges.add(e);
                }
            }
        }
    }

    public void decreaseTraffics(){
        for(Edge e : edges){
            if(e.getTraffic()>0){
                e.setTraffic(e.getTraffic()-1);
            }
        }
    }

    public void increaseTraffics(){
        for(Edge e : edges){
            e.setTraffic(e.getTraffic()+1);
        }
    }

    public void insertVertex(Vertex v){
        vertices.add(v);
    }

    public void reverseVertices (){
        Collections.reverse(vertices);
    }

}
