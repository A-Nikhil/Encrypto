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
        System.out.println(image.getHeight());
        System.out.println(image.getWidth());

        // Creating a directory
        WinInteract.FileCreation fileCreate = new WinInteract.FileCreation();
        boolean isCreationSuccess;
        if (senderID == revID)
            isCreationSuccess = fileCreate.createFile(senderID, senderID);
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
        int p1, a1, r1, g1, b1, p2, a2, r2, g2, b2, i = 0, j = 0, c = 0;
        outer:
        while (i < image.getWidth()) {
            while (j < image.getHeight()) {
                p1 = image.getRGB(i, j);
                a1 = (p1 % 0xff000000) >> 24;
                r1 = (p1 & 0x00ff0000) >> 16;
                g1 = (p1 & 0x0000ff00) >> 8;
                b1 = p1 & 0x000000ff;

                p2 = image.getRGB(i, j + 1);
                a2 = (p2 % 0xff000000) >> 24;
                r2 = (p2 & 0x00ff0000) >> 16;
                g2 = (p2 & 0x0000ff00) >> 8;
                b2 = p2 & 0x000000ff;

                System.out.println(a1 + " " + r1 + " " + g1 + " " + b1 + " " + a2 + " " + r2 + " " + g2 + " " + b2 + " ");
                for (int traverse = 0; traverse < 8; traverse++) {
                    char ch = finalMessage.get(c).charAt(traverse);
                    System.out.print(ch + " ");
                    switch (traverse) {
                        case 0:
                            if (checkOne(ch)) {
                                if (!checkOdd(a1))
                                    a1 += 52;
                            } else {
                                if (checkOdd(a1))
                                    a1 += 52;
                            }
                            break;

                        case 1:
                            if (checkOne(ch)) {
                                if (!checkOdd(r1))
                                    r1 += 52;
                            } else {
                                if (checkOdd(r1))
                                    r1 += 52;
                            }
                            break;

                        case 2:
                            if (checkOne(ch)) {
                                if (!checkOdd(g1))
                                    g1 += 52;
                            } else {
                                if (checkOdd(g1))
                                    g1 += 52;
                            }
                            break;

                        case 3:
                            if (checkOne(ch)) {
                                if (!checkOdd(b1))
                                    b1 += 52;
                            } else {
                                if (checkOdd(b1))
                                    b1 += 52;
                            }
                            break;

                        case 4:
                            if (checkOne(ch)) {
                                if (!checkOdd(a2))
                                    a2 += 52;
                            } else {
                                if (checkOdd(a2))
                                    a2 += 52;
                            }
                            break;

                        case 5:
                            if (checkOne(ch)) {
                                if (!checkOdd(r2))
                                    r2 += 52;
                            } else {
                                if (checkOdd(r2))
                                    r2 += 52;
                            }
                            break;

                        case 6:
                            if (checkOne(ch)) {
                                if (!checkOdd(g2))
                                    g2 += 52;
                            } else {
                                if (checkOdd(g2))
                                    g2 += 52;
                            }
                            break;

                        case 7:
                            if (checkOne(ch)) {
                                if (!checkOdd(b2))
                                    b2 += 52;
                            } else {
                                if (checkOdd(b2))
                                    b2 += 52;
                            }
                            break;
                    }
                }
                System.out.println();
                System.out.println(a1 + " " + r1 + " " + g1 + " " + b1 + " " + a2 + " " + r2 + " " + g2 + " " + b2 + " ");
                c++;
                if (c == finalMessage.size())
                    break outer;

                j += 2;
                if (j>image.getWidth()) {
                    i++;
                    j = 0;
                }
            }
        }
        String outputLoc = fileCreate.filename + "\\" + title +".jpg";
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