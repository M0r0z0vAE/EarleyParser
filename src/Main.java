
public class Main {
    public static void main(String[] args) throws Exception {
        String filenameStr = "src/res/file.txt";
        Earley rly = new Earley(filenameStr);
        if(rly.algorithm()) System.out.println("True\n");
        else System.out.println("False\n");
    }
}
