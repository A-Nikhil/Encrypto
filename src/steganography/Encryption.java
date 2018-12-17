package steganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Encryption {

    public String imageLoc;
    public String text;

    public Encryption() {
        imageLoc = "";
        text = "";
    }

    public String performOperation(int senderID, int revID) throws IOException {
        System.out.println(senderID);
        System.out.println(revID);
        File inputImage = new File("C:\\Users\\besta\\Pictures\\S Saver\\IMG_20170610_212533.jpg");
        BufferedImage image = ImageIO.read(inputImage);
        System.out.println(image.getHeight());
        System.out.println(image.getWidth());

        // Creating a directory
        WinInteract.FileCreation filecreate = new WinInteract.FileCreation();
        boolean isCreationSuccess;
        if (senderID == revID)
            isCreationSuccess = filecreate.createFile(senderID);
        else
            isCreationSuccess  = filecreate.createFile(senderID, revID);
        if (isCreationSuccess)
            System.out.println("Directory is Created, Control is back to Encryption");
        else
            System.out.println("Directory already exists, Control is back to Encryption");

        // Formatting the text -- "\b\b" means an Enter
        List<String> finalMessage = new ArrayList<>();
        String []enters = text.split("  ");
        for (String x : enters) {
            String []textInALine = x.split(" ");
            for (String y : textInALine) {
                for (int k = 0; k < y.length(); k++) {
                    finalMessage.add(EncryptThis(y.charAt(k), senderID, revID));
                }
            }
            finalMessage.add(EncryptThis(' ', senderID, revID));
        }

        int p1, a1, r1, g1, b1, p2, a2, r2, g2, b2, i=0, j=0, c=0;
        while (i<image.getWidth()) {
            while (j<image.getHeight()) {
                p1 = image.getRGB(i, j);
                a1 = (p1 % 0xff000000) >> 24;
                r1 = (p1 & 0x00ff0000) >> 16;
                g1 = (p1 & 0x0000ff00) >> 8;
                b1 =  p1 & 0x000000ff;

                p2 = image.getRGB(i, j + 1);
                a2 = (p2 % 0xff000000) >> 24;
                r2 = (p2 & 0x00ff0000) >> 16;
                g2 = (p2 & 0x0000ff00) >> 8;
                b2 =  p2 & 0x000000ff;

                for (int counter = 0; counter < finalMessage.get(c).length(); counter++) {

                    switch (counter) {
                        case 0:
                            if (checkOne(finalMessage.get(c).charAt(counter))) {
                                if (!checkOdd(a1))
                                    a1++;
                            } else {
                                if (checkOdd(a1))
                                    a1++;
                            }
                            break;

                        case 1:
                            if (checkOne(finalMessage.get(c).charAt(counter))) {
                                if (!checkOdd(r1))
                                    r1++;
                            } else {
                                if (checkOdd(r1))
                                    r1++;
                            }
                            break;

                        case 2:
                            if (checkOne(finalMessage.get(c).charAt(counter))) {
                                if (!checkOdd(g1))
                                    g1++;
                            } else {
                                if (checkOdd(g1))
                                    g1++;
                            }
                            break;

                        case 3:
                            if (checkOne(finalMessage.get(c).charAt(counter))) {
                                if (!checkOdd(b1))
                                    b1++;
                            } else {
                                if (checkOdd(b1))
                                    b1++;
                            }
                            break;

                        case 4:
                            if (checkOne(finalMessage.get(c).charAt(counter))) {
                                if (!checkOdd(a2))
                                    a2++;
                            } else {
                                if (checkOdd(a2))
                                    a2++;
                            }
                            break;

                        case 5:
                            if (checkOne(finalMessage.get(c).charAt(counter))) {
                                if (!checkOdd(r2))
                                    r2++;
                            } else {
                                if (checkOdd(r2))
                                    r2++;
                            }
                            break;

                        case 6:
                            if (checkOne(finalMessage.get(c).charAt(counter))) {
                                if (!checkOdd(g2))
                                    g2++;
                            } else {
                                if (checkOdd(g2))
                                    g2++;
                            }
                            break;

                        case 7:
                            if (checkOne(finalMessage.get(c).charAt(counter))) {
                                if (!checkOdd(b2))
                                    b2++;
                            } else {
                                if (checkOdd(b2))
                                    b2++;
                            }
                            break;

                    }
                }

                c++;
            }
        }

        File outputImage = new File("C:\\Users\\besta\\Pictures\\S Saver\\IMG_20170610_2125354.jpg");
        ImageIO.write(image, "jpg", outputImage);

        return "";
    }

    private String EncryptThis(char x, int user1, int user2) {
        int key = (user1 + user2)/2, sum = 0;
        while (key>0) {
            sum += (key%10);
            key /= 10;
        }
        key = sum;

        int asc = (int)x;
        if (asc > 64 && asc < 91) {
            asc += key;
            if (asc>90)
                asc = 64 + (90-asc);
        } else if (asc > 47 && asc < 58) {
            asc += key;
            if (asc > 57)
                asc = 47 + (57-asc);
        } else if (asc > 96 && asc < 123) {
            asc += key;
            if (asc > 122)
                asc = 96 + (122-asc);
        } else
            asc += key;


        return BinaryThis(asc);
    }

    private String BinaryThis(int x) {
        String bin = "";
        while (x>0) {
            bin = Integer.toString(x%2) + bin;
            x /= 2;
        }
        System.out.println(bin);
        return bin;
    }

    private boolean checkOne (char x) {
        return (x=='1');
    }

    private boolean checkOdd (int x) {
        return (x%2==0);
    }

    public static void main(String []args) throws IOException{
        //
    }
}
