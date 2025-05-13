import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular{
    private Map<String, String> codeToDescription;

    public ICDCodeTabularOptimizedForTime(String path){
        this.codeToDescription = new HashMap<>();
        try(Scanner scanner = new Scanner(new File(path))){
            for(int i=0; i<87; i++){
                scanner.nextLine();
            }
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();
                if(line.matches("[A-Z][0-9]{2}.+")){
                    String[] tokens = line.split("\\s+",2);
                    this.codeToDescription.put(tokens[0],tokens[1]);
                }
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String getDescription(String icd10) throws IndexOutOfBoundsException {
        if(!this.codeToDescription.containsKey(icd10)) throw new IndexOutOfBoundsException("Code not found");

        return this.codeToDescription.get(icd10);
    }
}
