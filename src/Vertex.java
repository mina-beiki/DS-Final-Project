import java.util.Objects;

public class Vertex {
    private double x ;
    private double y ;
    private long id ;
    private double dist ;
    private Vertex prev ;
    private boolean explored ;

    public Vertex(double x, double y, long id) {
        this.x = x;
        this.y = y;
        this.id = id;
        dist = Double.POSITIVE_INFINITY ;
        prev = null ;
        explored = false;

    }

    public Vertex getPrev() {
        return prev;
    }

    public void setPrev(Vertex prev) {
        this.prev = prev;
    }

    public void reset(){
        this.dist = Double.POSITIVE_INFINITY ;
        this.explored = false ;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public double getDist() {
        return dist;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public long getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
        return Double.compare(vertex.getX(), getX()) == 0 &&
                Double.compare(vertex.getY(), getY()) == 0 &&
                getId() == vertex.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getId());
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                '}';
    }
}
