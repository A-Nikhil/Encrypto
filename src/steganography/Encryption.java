package steganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

        int p, a, r, g, b;
        for(int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                p = image.getRGB(i,j);
                a = (p % 0xff000000) >> 24;
                r = (p & 0x00ff0000) >> 16;
                g = (p & 0x0000ff00) >> 8;
                b =  p & 0x000000ff;

                a = (a + 54) % 255;
                r = (r + 54) % 255;
                b = (b + 54) % 255;
                g = (g + 54) % 255;

                p = (a<<24) | (r<<16) | (g<<8) | b;
                image.setRGB(i,j,p);
            }
        }

        File outputImage = new File("C:\\Users\\besta\\Pictures\\S Saver\\IMG_20170610_2125354.jpg");
        ImageIO.write(image, "jpg", outputImage);

        return "";
    }

    public static void main(String []args) throws IOException{
        Encryption obj = new Encryption();
        String alpha = obj.performOperation(1,1);
    }
}
