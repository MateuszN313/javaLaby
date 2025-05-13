import java.util.Arrays;

public class DeathCauseStatistic {
    private String icd10;
    private int[] deathsInAgeGroup;

    public class AgeBracketDeaths {
        public final int young;
        public final int old;
        public final int deathCount;

        public AgeBracketDeaths(int young, int old, int deathCount) {
            this.young = young;
            this.old = old;
            this.deathCount = deathCount;
        }

        public static final int[][] AGE_BRACKETS = {
                {0, 4}, {5, 9}, {10, 14}, {15, 19}, {20, 24},
                {25, 29}, {30, 34}, {35, 39}, {40, 44}, {45, 49},
                {50, 54}, {55, 59}, {60, 64}, {65, 69}, {70, 74},
                {75, 79}, {80, 84}, {85, 89}, {90, 94}, {95, 200}
        };

        @Override
        public String toString() {
            return "AgeBracketDeaths{" +
                    "young=" + young +
                    ", old=" + old +
                    ", deathCount=" + deathCount +
                    '}';
        }
    }

    public DeathCauseStatistic(String icd10, int[] deathsInAgeGroup) {
        this.icd10 = icd10;
        this.deathsInAgeGroup = deathsInAgeGroup;
    }

    public String getIcd10() {
        return icd10;
    }

    public int[] getDeathsInAgeGroup() {
        return deathsInAgeGroup;
    }

    @Override
    public String toString() {
        return "DeathCauseStatistic{" +
                "icd10='" + icd10 + '\'' +
                ", deathsInAgeGroup=" + Arrays.toString(deathsInAgeGroup) +
                '}';
    }

    public static DeathCauseStatistic fromCsvLine(String line){
        String[] tokens = line.trim().split(",");

        String icd10 = tokens[0].trim();
        int[] deathsInAgeGroup = new int[tokens.length-2];

        for(int i=2; i<tokens.length; i++){
            String token = tokens[i];
            if(token.equals("-")){
                deathsInAgeGroup[i-2] = 0;
            }else{
                deathsInAgeGroup[i-2] = Integer.parseInt(token);
            }
        }

        return new DeathCauseStatistic(icd10,deathsInAgeGroup);
    }

    public AgeBracketDeaths getDeathsByAge(int age){
        int[][] ageBrackets = AgeBracketDeaths.AGE_BRACKETS;
        for(int i=0; i<ageBrackets.length; i++){
            int young = ageBrackets[i][0];
            int old = ageBrackets[i][1];
            if(age>=young && age<=old){
                return new AgeBracketDeaths(young, old, deathsInAgeGroup[i]);
            }
        }
        return null;
    }
}
