import java.util.Objects;

public class Edge {
    private double weight ;
    private Vertex v1 ;
    private Vertex v2 ;


    public Edge(double weight, Vertex v1, Vertex v2) {
        this.weight = weight;
        this.v1 = v1;
        this.v2 = v2;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Vertex getV1() {
        return v1;
    }

    public void setV1(Vertex v1) {
        this.v1 = v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public void setV2(Vertex v2) {
        this.v2 = v2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.getWeight(), getWeight()) == 0 &&
                getV1().equals(edge.getV1()) &&
                getV2().equals(edge.getV2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getV1(), getV2());
    }
}
