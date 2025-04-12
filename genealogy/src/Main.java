import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Person p1 = new Person("Jan", "Kowalski", LocalDate.of(1980, 1, 12), null);
        Person p2 = new Person("Joanna", "Nowak", LocalDate.of(1970, 11, 22), null);
        Person p3 = new Person("Bartłomiej", "Daniluk", LocalDate.of(1999, 1, 17), null);

        List<Person> people = new ArrayList<>();
        people.add(p1);
        people.add(p2);
        people.add(p3);

        p1.adopt(p3);
        p2.adopt(p3);

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

        PlantUMLRunner.setPath("diagrams");

        PlantUMLRunner.generateUML(Person.toUml(peopleFromCsv),PlantUMLRunner.pathToJar, "test");
        System.out.println(Person.sortByBirthdate(people));
        System.out.println(Person.getDead(peopleFromCsv));
        System.out.println(Person.getOldestLiving(peopleFromCsv));

        Person.toBinaryFile(people, "resources/family.bin");
        System.out.println(Person.fromBinaryFile("resources/family.bin"));
    }
}