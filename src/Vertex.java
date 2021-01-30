import java.util.Objects;

public class Vertex {
    private double x ;
    private double y ;
    private int id ;

    public Vertex(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "x=" + x +
                ", y=" + y +
                ", id=" + id +
                '}';
    }
}
