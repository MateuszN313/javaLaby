import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Person p1 = new Person("Jan", "Kowalski", LocalDate.of(1980, 1, 12), null);
        Person p2 = new Person("Kamil", "Nowak", LocalDate.of(1990, 11, 22), null);
        Person p3 = new Person("Bartłomiej", "Daniluk", LocalDate.of(1999, 1, 17), null);

        List<Person> people = new ArrayList<>();
        people.add(p1);
        people.add(p2);
        people.add(p3);

        p1.adopt(p2);
        p1.adopt(p3);

        System.out.println(p1.getYoungestChild());
        System.out.println(p1.getChildren().get(0));

        Family fam1 = new Family();
        fam1.add(p1, p2, p3);

        Person test=Person.fromCsvLine("Marek Kowalski,15.05.1899,25.06.1857,,");
        if(test != null){
            System.out.println(test.toString());
        }

        List<Person> peopleFromCsv=Person.fromCsv("resources/family.csv");
        if(peopleFromCsv != null){
            System.out.println(peopleFromCsv);
            System.out.println(peopleFromCsv.get(1).getChildren());
        }



//        List<Person> people = List.of(
//                new Person("Jan", "Kowalski", LocalDate.of(1980, 1, 12)),
//                new Person("Kamil", "Nowak", LocalDate.of(1990, 11, 22)),
//                new Person("Bartłomiej", "Daniluk", LocalDate.of(1999, 1, 17))
//        );







    }
}