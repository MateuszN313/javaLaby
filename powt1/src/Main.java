import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        try{
//            File file = new File("resources/zgony.csv");
//            Scanner scanner = new Scanner(file);
//
//            scanner.nextLine();
//
//            for(int i=0; i<4 && scanner.hasNextLine(); i++) scanner.nextLine();
//
//            DeathCauseStatistic deathCauseStatistic = DeathCauseStatistic.fromCsvLine(scanner.nextLine());
//
//            System.out.println(deathCauseStatistic.getIcd10());
//            System.out.println(deathCauseStatistic.getDeathsInAgeGroup()[13]);
//
//            DeathCauseStatistic.AgeBracketDeaths ageBracketDeaths = deathCauseStatistic.getDeathsByAge(67);
//            System.out.println(ageBracketDeaths);
//
//        }catch(IOException e){
//            System.out.println(e.getMessage());
//        }

        DeathCauseStatisticList deathCauseStatisticList = new DeathCauseStatisticList();
        deathCauseStatisticList.repopulate("resources/zgony.csv");
        //System.out.println(deathCauseStatisticList.list);

        List<DeathCauseStatistic> top5in43yo = deathCauseStatisticList.mostDeadlyDiseases(43,5);
        //System.out.println(top5in43yo);

        ICDCodeTabularOptimizedForTime icdCodeTabularOptimizedForTime = new ICDCodeTabularOptimizedForTime("resources/icd10.txt");
        System.out.println(icdCodeTabularOptimizedForTime.getDescription("A01.00"));

        ICDCodeTabularOptimizedForMemory icdCodeTabularOptimizedForMemory = new ICDCodeTabularOptimizedForMemory("resources/icd10.txt");
        System.out.println(icdCodeTabularOptimizedForTime.getDescription("A01.01"));
    }
}