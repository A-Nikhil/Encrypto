package steganography;

import Procedure.WorksNote;
import Procedure.WorksMessage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class Encryption {

    public String imageLoc;
    public String text;
    public String title;

    public Encryption() {
        imageLoc = "";
        text = "";
        title = "";
    }

    public String performOperation(int senderID, int revID) throws IOException {
        System.out.println(senderID);
        System.out.println(revID);
        File inputImage = new File(imageLoc);
        BufferedImage image = ImageIO.read(inputImage);
        ImageProcess obj = new ImageProcess();
        obj.image = image;
        System.out.println(image.getHeight());
        System.out.println(image.getWidth());

        // Creating a directory
        WinInteract.FileCreation fileCreate = new WinInteract.FileCreation();
        boolean isCreationSuccess;
        if (senderID == revID)
            isCreationSuccess = fileCreate.createFile(senderID);
        else
            isCreationSuccess  = fileCreate.createFile(senderID, revID);
        if (isCreationSuccess)
            System.out.println("Directory is Created, Control is back to Encryption");
        else
            System.out.println("Directory already exists, Control is back to Encryption");

        // Formatting the text -- "\b\b" means an Enter
        List<String> finalMessage = new ArrayList<>();
        List<String> alpha = new ArrayList<>();
        List<String> beta = new ArrayList<>();
        String[] enters = text.split("  ");
        for (String x : enters) {
            String[] textInALine = x.split(" ");
            for (String y : textInALine) {
                alpha.add(y);
                beta.add(EncryptThis(y, senderID, revID));
                for (int k = 0; k < y.length(); k++) {
                    finalMessage.add(EncryptThis(y.charAt(k), senderID, revID));
                }
            }
            finalMessage.add(EncryptThis(' ', senderID, revID));
        }

        if (senderID == revID) {
            WorksNote worksNote = new WorksNote();
            worksNote.title = title;
            worksNote.Preprocess(alpha, senderID);
            worksNote.Postprocess(beta, senderID);
        } else {
            WorksMessage worksMessage = new WorksMessage();
            worksMessage.title = title;
            worksMessage.Preprocess(alpha, senderID, revID);
            worksMessage.Postprocess(beta, senderID, revID);
        }

        System.out.println("Going to process Image");

        System.out.println("saving image");
        String outputLoc = "D:\\myprojects\\Encrypto\\files\\";
        if (senderID == revID)
            outputLoc = outputLoc.concat("Notes\\" + senderID + "\\" +  title + ".jpg");
        else
            outputLoc = outputLoc.concat("Messages\\" + senderID + "_" + revID + "\\" +  title + ".jpg");
        System.out.println("OUTPUT LOCATION : " + outputLoc);
        File outputImage = new File(outputLoc);
        ImageIO.write(image, "jpg", outputImage);
        return outputLoc;
    }

    private int FindKey(int user1, int user2) {
        int key = (user1 + user2) / 2, sum = 0;
        while (key > 0) {
            sum += (key % 10);
            key /= 10;
        }
        return sum;
    }

    private String EncryptThis(char x, int user1, int user2) {
        int key = FindKey(user1, user2);

        int asc = (int) x;
        if (asc > 64 && asc < 91) {
            asc += key;
            if (asc > 90)
                asc = 64 + (90 - asc);
        } else if (asc > 47 && asc < 58) {
            asc += key;
            if (asc > 57)
                asc = 47 + (57 - asc);
        } else if (asc > 96 && asc < 123) {
            asc += key;
            if (asc > 122)
                asc = 96 + (122 - asc);
        } else
            asc += key;


        return BinaryThis(asc);
    }

    private String EncryptThis(String z, int senderID, int recID) {
        int key = FindKey(senderID, recID);
        String fr = "";
        for (int i=0; i< z.length(); i++) {
            char ch = z.charAt(i);
            if (!Character.isLetterOrDigit(ch)) {
                fr = fr.concat(Character.toString(ch));
                continue;
            }
            int asc = (int)ch;
            asc = asc + key;
            if (Character.isUpperCase(ch) && asc > 90) {
                asc = (asc - 90) + 64;
            } else if (Character.isDigit(ch) && asc > 57) {
                asc = (asc - 57) + 47;
            } else if (Character.isLowerCase(ch) && asc > 122) {
                asc = (asc - 122) + 97;
            }
            char ch1 = (char)asc;
            fr = fr.concat(Character.toString(ch1));
        }
        return fr;
    }

    private String BinaryThis(int x) {
        String bin = "";
        while (x > 0) {
            bin = Integer.toString(x % 2).concat(bin);
            x /= 2;
        }
        if (bin.length() == 7)
            bin = "0" + bin;
        if (bin.length() == 6)
            bin = "00" + bin;
        return bin;
    }

    private boolean checkOne(char x) {
        return (x == '1');
    }

    private boolean checkOdd(int x) {
        return (x % 2 == 0);
    }

    public static void main(String[] args) throws IOException {
        //
        Encryption obj = new Encryption();
        obj.performOperation(152, 152);
    }
}