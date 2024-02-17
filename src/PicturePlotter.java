import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PicturePlotter {
    private BufferedImage image;
    private String filename = "../picture.txt";

    public void plot(int x, int y, int count, int color) {
        Color colour = new Color(color);
        image.setRGB(x, y, colour.getRGB());
    }

    private void readFromPictureFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.filename));
        String[] dimensions = reader.readLine().split(" ");
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int y = 0; // y coordinate of the current line
        String line;

        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            if (line.equals("-1")) {
                break;
            }
            String[] pairs = line.split(" ");
            int x = 0; // x coordinate within the current line
            for (int i = 0; i < pairs.length; i += 2) {
                int count = Integer.parseInt(pairs[i]);
                int color = Integer.parseInt(pairs[i + 1]);

                for (int j = 0; j < count; j++) {
                    plot(x++, y, count, color);
                }
            }
            y++; // move to the next line
        }

        reader.close();

    }

    public void displayImage() {
        try {
            readFromPictureFile();
            JFrame frame = new JFrame();
            frame.setSize(image.getWidth(), image.getHeight());
            JLabel label = new JLabel(new ImageIcon(image));
            frame.add(label);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}