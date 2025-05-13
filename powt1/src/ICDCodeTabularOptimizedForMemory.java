import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular{
    String path;

    public ICDCodeTabularOptimizedForMemory(String path) {
        this.path = path;
    }

    @Override
    public String getDescription(String icd10) throws IndexOutOfBoundsException {
        String description = "";
        try(Scanner scanner = new Scanner(new File(this.path))){
            for(int i=0; i<87; i++){
                scanner.nextLine();
            }
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();
                if(line.matches("[A-Z][0-9]{2}.+")){
                    String[] tokens = line.split("\\s+",2);
                    if(tokens[0].equals(icd10)){
                        description = tokens[1];
                        break;
                    }
                }
            }
            if(description.isEmpty()){
                throw new IndexOutOfBoundsException("Code not found");
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return description;
    }
}
