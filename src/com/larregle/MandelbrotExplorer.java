package com.larregle;

import com.larregle.fractal.Mandelbrot;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class MandelbrotExplorer extends JPanel implements KeyListener {

    private final Set<Character> keyPressed;
    private int zoomPower;
    private float xPower;
    private float yPower;

    public MandelbrotExplorer() {
        keyPressed = new HashSet<>();
        zoomPower = 1;
        xPower = 0.01F;
        yPower = 0.01F;
        setPreferredSize(new Dimension(Mandelbrot.WIDTH, Mandelbrot.HEIGHT));
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            g.drawImage(Mandelbrot.getInstance().generateFractal(), 0, 0, null);
            g.setFont(new Font(null, 0, 20));
            g.setColor(Color.WHITE);
            g.drawString("ZOOM: " + Mandelbrot.getInstance().getZoom(), 3 , 18);
            g.drawString("X: " + Mandelbrot.getInstance().getMoveX(), 3 , 38);
            g.drawString("Y: " + Mandelbrot.getInstance().getMoveY(), 3 , 58);
            g.drawString("ZOOM INCREASE POWER: " + zoomPower, 3, 78);
            g.drawString("X INCREASE POWER: " + xPower, 3, 99);
            g.drawString("Y INCREASE POWER: " + yPower, 3, 119);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ADD) {
            if (keyPressed.contains((char) KeyEvent.VK_CONTROL)) {
                zoomPower += 2;
            } else {
                xPower += 0.1;
                yPower += 0.1;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            keyPressed.add((char) KeyEvent.VK_CONTROL);
        } else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
            if (keyPressed.contains((char) KeyEvent.VK_CONTROL)) {
                zoomPower -= 2;
            } else {
                xPower -= 0.1;
                yPower -= 0.1;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Mandelbrot.getInstance().setZoom(Mandelbrot.getInstance().getZoom() + zoomPower);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            Mandelbrot.getInstance().setMoveX(Mandelbrot.getInstance().getMoveX() - xPower);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            Mandelbrot.getInstance().setMoveX(Mandelbrot.getInstance().getMoveX() + xPower);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            Mandelbrot.getInstance().setMoveY(Mandelbrot.getInstance().getMoveY() - yPower);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            Mandelbrot.getInstance().setMoveY(Mandelbrot.getInstance().getMoveY() + yPower);
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (Mandelbrot.getInstance().getZoom() == 1 || Mandelbrot.getInstance().getZoom() - zoomPower <= 1) {
                Mandelbrot.getInstance().setZoom(Mandelbrot.getInstance().getZoom() - 0.01F);
            } else {
                Mandelbrot.getInstance().setZoom(Mandelbrot.getInstance().getZoom() - zoomPower);
            }
        }  else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        paintComponent(getGraphics());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            keyPressed.remove((char)KeyEvent.VK_CONTROL);
        }
    }
}
