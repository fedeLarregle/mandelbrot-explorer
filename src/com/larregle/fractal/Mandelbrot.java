package com.larregle.fractal;

import com.larregle.utils.Complex;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Mandelbrot {

    private static final int MAX_ITERATIONS = 500;
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final Complex MIN_COMPLEX;
    public static final Complex MAX_COMPLEX;

    public static final int[] colors;
    private static final Mandelbrot instance;

    private float zoom;
    private double moveX;
    private double moveY;

    static {
        instance = new Mandelbrot();
        MIN_COMPLEX = new Complex(-2.5, -1.0);
        MAX_COMPLEX = new Complex(1.0, 1.0);
        colors = new int[MAX_ITERATIONS];
        for (int i = 0; i<MAX_ITERATIONS; i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }
    }

    private Mandelbrot() {
        zoom = 0.09F;
        moveX = 0D;
        moveY = 0D;
    }

    public static Mandelbrot getInstance() { return instance; }

    public BufferedImage generateFractal() throws Exception {
        final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Color color = find(new Complex(mapToReal(col), mapToImaginary(row)));
                image.setRGB(col, row, color.getRGB());
            }
        }

        return image;
    }

    /**
     * Finds a color for a given complex number using the 'Escape time algorithm'
     * @param complex
     * @return the corresponding {@link Color} for a given {@link Complex} number.
     */
    private Color find(Complex complex) {
        int i = 0;
        Complex zc = new Complex();
        while (((zc.getX() * zc.getX()) + (zc.getY() * zc.getY())) < 4.0 && i < MAX_ITERATIONS) {
            double new_x = zc.getX() * zc.getX() - zc.getY() * zc.getY() + complex.getX();
            zc.setY(2.0 * zc.getX() * zc.getY() + complex.getY());
            zc.setX(new_x);
            i++;
        }
        return new Color(i < MAX_ITERATIONS ? colors[i] : 0x000000);
    }

    private double mapToReal(int x) {
        return 1.5 * (x - WIDTH / 2)/ (0.5 * zoom * WIDTH) + moveX;
    }

    private double mapToImaginary(int y) {
        return (y - HEIGHT / 2) / (0.5 * zoom * HEIGHT) + moveY;
    }

    public double getMoveX() {
        return moveX;
    }

    public void setMoveX(double moveX) {
        this.moveX = moveX;
    }

    public double getMoveY() {
        return moveY;
    }

    public void setMoveY(double moveY) {
        this.moveY = moveY;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }
}
