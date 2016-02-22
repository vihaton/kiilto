package ui.gui;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author xvixvi
 */
public class PiirtoAvustaja {

    /**
     * Asettaa grafiikkojen värin karkkikasan mukaiseksi. 0 kultainen, 1
     * valkoinen, 2 sininen, 3 vihreä, 4 punainen ja 5 musta.
     *
     * @param g graphics.
     * @param i minkä karkkikasan väri halutaan.
     */
    public void asetaVari(Graphics g, int i) {
        if (i == 0) {
            g.setColor(Color.yellow);
        }
        if (i == 1) {
            g.setColor(Color.white);
        }
        if (i == 2) {
            g.setColor(Color.blue);
        }
        if (i == 3) {
            g.setColor(Color.green);
        }
        if (i == 4) {
            g.setColor(Color.red);
        }
        if (i == 5) {
            g.setColor(Color.black);
        }
    }

    public void asetaReunavari(Graphics g, int i) {
        int reunaVari = i != 5 ? 5 : 1;
        asetaVari(g, reunaVari);
    }

    public void piirraNimi(Graphics graphics, String nimi, int x, int y) {
        graphics.drawString("nimi: " + nimi, x, y);
    }

    public void piirraArvovalta(Graphics graphics, int arvovalta, int x, int y) {
        graphics.drawOval(x, y, 17, 16);
        graphics.drawString("" + arvovalta, x + 5, y + 13);
    }

    public void piirraArvovaltaPieni(Graphics graphics, int arvovalta, int x, int y) {
        graphics.drawOval(x, y, 13, 12);
        graphics.drawString("" + arvovalta, x + 3, y + 11);
    }
}
