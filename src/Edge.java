import java.util.Objects;

public class Edge {
    private double weight ;
    private Vertex v1 ;
    private Vertex v2 ;
    private int traffic ;
    private double length ;


    public Edge(double weight, Vertex v1, Vertex v2) {
        this.weight = weight;
        this.v1 = v1;
        this.v2 = v2;
        traffic = 0 ;
        double x1=this.getV1().getX();
        double x2=this.getV2().getX();
        double y1=this.getV1().getY();
        double y2=this.getV2().getY();

        length = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }


    public double getLength() {
        return length;
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
    public String toString() {
        return "Edge{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                '}';
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

    public int getTraffic() {
        return traffic;
    }

    public void setTraffic(int traffic) {
        this.traffic = traffic;
    }
}
