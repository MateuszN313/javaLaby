import java.io.File;
import java.io.IOException;
import java.util.*;

public class DeathCauseStatisticList {
    List<DeathCauseStatistic> list;

    public DeathCauseStatisticList(){
        this.list = new ArrayList<>();
    }

    public void repopulate(String path){
        this.list.clear();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            scanner.nextLine();
            scanner.nextLine();

            while (scanner.hasNextLine()){
                DeathCauseStatistic deathCauseStatistic = DeathCauseStatistic.fromCsvLine(scanner.nextLine());
                this.list.add(deathCauseStatistic);
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    public List<DeathCauseStatistic> mostDeadlyDiseases(int age, int n){
        int[][] ageBrackets = DeathCauseStatistic.AgeBracketDeaths.AGE_BRACKETS;
        int count=0;
        while(count<=this.list.toArray().length){
            if(age >= ageBrackets[count][0] && age <= ageBrackets[count][1]){
                break;
            }
            count++;
        }
        final int ageGroupIndex = count;

        List<DeathCauseStatistic> sortedList = new ArrayList<>(this.list);
        sortedList.sort(Comparator.comparing(d -> d.getDeathsInAgeGroup()[ageGroupIndex]));

        List<DeathCauseStatistic> resultList = new ArrayList<>();
        for(int i=0; i<n; i++){
            resultList.add(sortedList.get(sortedList.toArray().length-i-1));
        }

        return resultList;
    }
}
