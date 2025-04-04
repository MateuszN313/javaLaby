import javax.naming.PartialResultException;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Person implements Comparable<Person>{
    private String name;
    private String surname;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private Set<Person> children;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Person(String name, String surname, LocalDate birthDate,  LocalDate deathDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.children = new HashSet<>();
    }

    public boolean adopt(Person child) {
        if(child == null || child == this) return false;
        return this.children.add(child);
    }

    @Override
    public String toString(){
        return "Osoba o imieniu " + this.name + " i nazwisku " + this.surname;
    }

    public Person getYoungestChild() {
        if (this.children == null || this.children.isEmpty()) {
            return null;
        }
        Person youngest = this.children.iterator().next();
        for (Person child: this.children) {
            if(youngest.compareTo(child) < 0) {
                youngest = child;
            }
        }
        return youngest;
    }

    @Override
    public int compareTo(Person other) {
//        return this.birthDate.compareTo(other.birthDate);
        if (this.birthDate.isAfter(other.birthDate)) {
            return 1; // other starszy
        } else if (this.birthDate.isBefore(other.birthDate)) {
            return -1; // other młodszy
        } else {
            return 0; // other równi
        }
    }

    public List<Person> getChildren(){
        List<Person> sortedChildren = new ArrayList<>(this.children);
        Collections.sort(sortedChildren, Collections.reverseOrder());
        return sortedChildren;
    }
     public static Person fromCsvLine(String line){
        String[] split=line.split(",");
        String[] fullName=split[0].split(" ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try {
            String name = fullName[0];
            String surname = fullName[1];
            LocalDate birthDate = LocalDate.parse(split[1], formatter);
            LocalDate deathDate = null;
            if (!split[2].isEmpty()) {
                deathDate = LocalDate.parse(split[2], formatter);
                if(birthDate.isAfter(deathDate)){
                    throw new NegativeLifespanException("This person has negative lifespan");
                }
            }

            Person result = new Person(name, surname, birthDate, deathDate);
            return result;
        }catch(NegativeLifespanException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<Person> fromCsv(String path){
        try{
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            List<Person> result = new ArrayList<>();
            Set<String> peopleFullNames = new HashSet<>();
            Map<String, Person> peopleMap = new HashMap<>();

            scanner.nextLine();
            while(scanner.hasNextLine()){
                String line=scanner.nextLine();

                Person person = Person.fromCsvLine(line);
                String fullName = person.name + " " + person.surname;

                if(peopleFullNames.contains(fullName)){
                    throw new AmbigousPersonException("There are two peaople with same fullname in file");
                }else{
                    peopleFullNames.add(fullName);
                }

                String[] splitLine = line.split(",");
                peopleMap.put(fullName, person);

                String parent1FullName="";
                String parent2FullName="";
                if(splitLine.length>3) {
                    parent1FullName = splitLine[3];
                }
                if(splitLine.length>4) {
                    parent2FullName = splitLine[4];
                }

                if(!parent1FullName.isEmpty()){
                    Person parent1=peopleMap.get(parent1FullName);
                    try{
                        checkParentAge(parent1,person);
                        parent1.adopt(person);
                    }catch (ParentingAgeException e){
                        System.out.println(e.getMessage());
                        System.out.println("czy dodać?");
                        Scanner sysScanner = new Scanner(System.in);
                        String input = sysScanner.nextLine();
                        if(input.equalsIgnoreCase("y")){
                            parent1.adopt(person);
                        }
                    }
                }
                if(!parent2FullName.isEmpty()){
                    Person parent2=peopleMap.get(parent1FullName);
                    try{
                        checkParentAge(parent2,person);
                        parent2.adopt(person);
                    }catch (ParentingAgeException e){
                        System.out.println(e.getMessage());
                        System.out.println("czy dodać?");
                        Scanner sysScanner = new Scanner(System.in);
                        String input = sysScanner.nextLine();
                        if(input.equalsIgnoreCase("y")){
                            parent2.adopt(person);
                        }
                    }
                }


                result.add(person);
            }
            return result;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void checkParentAge(Person parent, Person child) throws ParentingAgeException{
        if(parent.birthDate.plusYears(15).isAfter(child.birthDate)){
            throw new ParentingAgeException("Rodzic jest mlodszy niz 15 lat w chwili urodzin dziecka");
        }
        if(parent.deathDate != null && parent.deathDate.isBefore(child.birthDate)){
            throw new ParentingAgeException("Rodzic nie żuje w chwili urodzin dziecka");
        }
    }

//    Collections.sort(al, Collections.reverseOrder());

}
