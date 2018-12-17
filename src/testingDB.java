import java.io.File;

public class testingDB {
    public static void main(String[] args) {
        File dir = new File("D:\\MyProjects\\Encrypto\\files\\TestFolder");
        if(!dir.exists()) {
            System.out.println("Creating Directory");
            boolean trial = false;

            try {
                dir.mkdir();
                trial = true;
            } catch (SecurityException e) {
                e.printStackTrace();
            }

            if (trial)
                System.out.println("Directory Created");
        }
    }
}