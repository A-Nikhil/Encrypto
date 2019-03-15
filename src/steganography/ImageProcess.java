package steganography;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class ImageProcess {
    public BufferedImage image;
    public ArrayList<String> finalMessage;

    private boolean checkOne(int x) {
        return false;
    }

    private boolean checkOdd(int x) {
        return false;
    }

    void Image() {
        int p1, a1, r1, g1, b1, p2, a2, r2, g2, b2, i = image.getWidth() + 1, j = image.getHeight() + 1, c = 0;
        while (i < image.getWidth()) {
            while (j < image.getHeight()) {
                System.out.println("Image Processing");
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
                    break;

                j += 2;
                if (j>image.getWidth()) {
                    i++;
                    j = 0;
                }
            }
        }
    }
}
