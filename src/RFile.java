import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RFile {
    private String path_name;

    public RFile(String name){
        path_name = name;
    }

    public ArrayList<String> OpenAndRead() throws FileNotFoundException {
        ArrayList<String> res = new ArrayList<>();
        try{
            File myFile = new File(path_name);
            try (Scanner scanf = new Scanner(myFile)) {
                while (scanf.hasNextLine()){
                    String m = scanf.nextLine();
                    if(m != "") res.add(m);
                }
            }
            return res;
        }
        catch(FileNotFoundException e){
            System.out.println("Ошибка: " + e);
        }
        return null;
    }
}
