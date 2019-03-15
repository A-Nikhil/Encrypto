package Procedure;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("Duplicates")
public class Inbox {
    public static void main(String[] args) throws IOException {
        String location = "D:\\myprojects\\Encrypto\\files\\Notes\\124\\Wassup.jpg";
        int x = location.lastIndexOf('\\');
        String newLocation = location.substring(0, x);
        System.out.println(newLocation);
        Desktop.getDesktop().open(new File(newLocation));
    }
}
