
import java.util.* ;

public class Main {

    public static void main(String[] args) {
        int n , m ; //n=vertexes , m=edges
        HashMap<Integer,ArrayList<Double>> places = new HashMap<>();// id , position
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();
        HashMap<Integer,Integer> indexes = new HashMap<>();//id , index

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

            ArrayList<Double> xy = new ArrayList<>();
            xy.add(x);
            xy.add(y);

            places.put(id , xy);
            indexes.put(id,i);

        }

        for(int i=0 ; i<n ; i++){
            ArrayList<Integer> list = new ArrayList<>();
            adjList.add(list);
        }

        for(int i=0 ; i<m ; i++){
            String id1Str = scanner.next();
            String id2Str = scanner.next();

            int id1 = Integer.parseInt(id1Str);
            int id2 = Integer.parseInt(id2Str);

            int index1 = indexes.get(id1);
            adjList.get(index1).add(id2);

            int index2 = indexes.get(id2);
            adjList.get(index2).add(id1);

        }

        System.out.println(indexes);
        System.out.println(adjList);

    }
}
