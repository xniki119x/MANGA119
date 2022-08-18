package f1cont.niki119.manga119.type;

import f1cont.niki119.manga119.UIntVar;
import f1cont.niki119.manga119.helpers.LogHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageWBMP implements LogHelper {

    public static byte type = 0x00;
    public static byte header = 0x00;
    public UIntVar width;
    public UIntVar height;
    public byte[] data;
    public int gg = 500;

    public ImageWBMP(BufferedImage image, int f) {
        gg = f;
        width = new UIntVar(image.getWidth());
        height = new UIntVar(image.getHeight());
        data = new byte[determineSize(width.number, height.number)];
        fillData(data, image);
    }

    public int determineSize(int x, int y) {
        int columns = x / 8 + ((x % 8) > 0 ? 1 : 0);
        return columns * y;
    }

    public void fillData(byte[] data, BufferedImage image) {
        for (int y = 0; y < height.number; y++) {
            for (int _byte = 0; _byte < (width.number / 8); _byte++) {
                for (int i = 0; i < 8; i++) {
                    Color rgb = new Color(image.getRGB(i + _byte * 8, y));
                    boolean isWhite = isWhite(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
                    addPixel(isWhite);
                }
            }
            for (int i = 0; i < width.number % 8; i++) {
                Color rgb = new Color(image.getRGB(i + (width.number / 8) * 8, y));
                boolean isWhite = isWhite(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
                addPixel(isWhite);
            }
            if (width.number % 8 > 0) nextByte();
        }
    }

    public boolean isWhite(int r, int g, int b) {
        return ((r + g + b) > gg);
    }

    private int f = 0;
    private int s = 1;
    private byte nextByte = 0x00;

    public void addPixel() {
        addPixel(false);
    }

    public void addPixel(boolean isWhite) {
        nextByte = (byte) (nextByte << 1);
        if (isWhite) {
            nextByte = (byte) (nextByte | 0x01);
        }
        s++;
        if (s > 8) {
            nextByte();
        }
    }

    public void nextByte() {
        if (s < 8) nextByte = (byte) (nextByte << (9 - s));
        data[f++] = nextByte;
        s = 1;
        nextByte = 0x00;
    }

    public byte[] getFullData() {
        byte[] full_data = new byte[data.length + 2 + width.size + height.size];
        full_data[0] = type;
        full_data[1] = header;
        addBytesToBytes(2, width.data, full_data);
        addBytesToBytes(2 + width.size, height.data, full_data);
        addBytesToBytes(2 + width.size + height.size, data, full_data);
        return full_data;
    }

    public void addBytesToBytes(int offset, byte[] array_in, byte[] array_out) {
        for (int i = 0; i < array_in.length; i++) {
            array_out[offset + i] = array_in[i];
        }
    }

}
