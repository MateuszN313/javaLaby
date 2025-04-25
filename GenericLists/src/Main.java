import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        CustomList<Integer> list = new CustomList<>();

        list.add(5);
        list.add(2);
        list.addFirst(4);

//        System.out.println(list.getLast());
//        System.out.println(list.getFirst());
        System.out.println(list);

//        list.removeFirst();
//        list.removeLast();
//        System.out.println(list);

        System.out.println(list.get(1));
        System.out.println(list.size());

        System.out.println("\n");

        for(Integer x : list){
            System.out.println(x);
        }
    }
}