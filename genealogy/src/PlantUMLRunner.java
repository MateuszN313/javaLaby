import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class PlantUMLRunner {
    public static String pathToJar;

    public static void setPath(String path) {
        pathToJar = path;
    }
    public static void generateUML(String data, String outputDir, String outputFilename){
        File dir = new File(outputDir);
        dir.mkdirs();

        File file = new File(dir, outputFilename+".puml");
        try(FileWriter writer = new FileWriter(file)){
            writer.write(data);
        }catch(Exception e){}
    }
}
