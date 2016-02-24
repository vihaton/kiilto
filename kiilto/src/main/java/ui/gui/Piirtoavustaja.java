package ui.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author xvixvi
 */
public class Piirtoavustaja {

    /**
     * Asettaa grafiikkojen värin karkkikasan mukaiseksi. 0 kultainen, 1
     * valkoinen, 2 sininen, 3 vihreä, 4 punainen ja 5 musta.
     *
     * @param g graphics.
     * @param i minkä karkkikasan väri halutaan.
     */
    public void asetaVari(Graphics g, int i) {
        g.setColor(getVari(i));
    }

    void asetaNappulanVari(JComponent komponentti, int i) {
        komponentti.setBackground(getVari(i));
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

    private Color getVari(int i) {
        if (i == 0) {
            return Color.yellow;
        }
        if (i == 1) {
            return Color.white;
        }
        if (i == 2) {
            return Color.blue;
        }
        if (i == 3) {
            return Color.green;
        }
        if (i == 4) {
            return Color.red;
        }
        return Color.black;
    }
}
