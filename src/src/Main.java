import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main extends JPanel {
    public final JFrame frame;
    public final int width;
    public final int height;
    public final boolean[][] cell;

    public static void main(String[] args) {
        new Main(15, 15, 5);
    }

    public Main(int width, int height, int nbLivingCells) {
        this.width = width;
        this.height = height;
        cell = new boolean[width][height];

        genereteRandmCells(nbLivingCells);

        frame = new JFrame("Game Of Life");
        frame.setSize(720, 480);
        frame.setUndecorated(true);
        frame.setResizable(true);

        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);

        frame.setVisible(true);

        run();
    }

    private void genereteRandmCells(int x) {
        Random random = new Random();
        List<String> availaible = new ArrayList<>();

        while (x > 0) {
            int rx = random.nextInt(width);
            int ry = random.nextInt(height);

            if (availaible.contains(rx + "-" + ry)) continue;

            availaible.add(rx + "-" + ry);

            cell[rx][ry] = true;

            x--;
        }
    }


    protected void paintComponent(Graphics g) {
        int xOffset = 720 / width;
        int yOffset = 480 / height;

        g.setColor(Color.BLACK);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (cell[x][y])
                    g.fillRect(x * xOffset, y * yOffset, xOffset, yOffset);
            }
        }
    }

    public void run() {
        long nanoSegonds = System.nanoTime();
        long lastTime = System.currentTimeMillis();
        double tick = 1000000000.0 / 20.0;
        int tps = 0;
        int fps = 0;
        while (true) {
            if (System.nanoTime() - nanoSegonds > tick) {
                nanoSegonds += tick;
                tps++;
                update();
            } else {
                fps++;
                frame.repaint();
            }
            if (System.currentTimeMillis() - lastTime >= 1000) {
                lastTime = System.currentTimeMillis();
                System.out.println(tps + " TPS - " + fps + " FPS");
                tps = 0;
                fps = 0;

                System.gc();
            }

        }
    }

    public void update() {
    }
}
