package WinInteract;

import java.io.File;

@SuppressWarnings("Duplicates")
public class FileCreation {
    public String filename = "";
    public boolean createFile(int noter) {
        boolean verify = false;
        filename = "D:\\MyProjects\\Encrypto\\files\\Notes\\" + noter;
        File dir = new File(filename);
        if(!dir.exists()) {
            System.out.println("Creating directory");
            verify = false;

            try {
                dir.mkdirs();
                verify = true;
            } catch (SecurityException e) {
                e.printStackTrace();
                System.out.println("Directory creation error");
            }
        }
        if (verify)
            System.out.println("Directory Created");
        else {
            System.out.println("Directory already exists");
        }
        return verify;
    }

    public boolean createFile(int sender, int receiver) {
        boolean verify = false;
        filename = "D:\\MyProjects\\Encrypto\\files\\Messages\\" + sender + "_" + receiver;
        File dir = new File(filename);
        if(!dir.exists()) {
            System.out.println("Creating directory");
            verify = false;

            try {
                dir.mkdirs();
                verify = true;
            } catch (SecurityException e) {
                e.printStackTrace();
                System.out.println("Directory creation error");
            }
        }
        if (verify)
            System.out.println("Directory Created");
        else {
            System.out.println("Directory already exists");
        }
        return verify;
    }

//    public static void main(String []args) {
////        Testing here
//        FileCreation obj = new FileCreation();
//        System.out.println(obj.createFile(101));
//        System.out.println(obj.createFile(101, 123));
//    }
}
