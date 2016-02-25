package ui.gui.piirtaminen;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * Auttaa piirtämään useasti toistuvia elementtejä.
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

    /**
     * Asettaa komponentin taustaväriksi indeksin osoittaman karkin värin.
     *
     * @param komponentti jonka väri pitää asettaa.
     * @param i minkä karkin väri pistetään.
     */
    public void asetaNappulanVari(JComponent komponentti, int i) {
        komponentti.setBackground(getVari(i));
    }

    /**
     * Asetetaan reunan väri erottumaan muusta väristä.
     *
     * @param g grafiikat.
     * @param i mikä on pääväri.
     */
    public void asetaReunavari(Graphics g, int i) {
        int reunaVari = i != 5 ? 5 : 1;
        asetaVari(g, reunaVari);
    }

    /**
     * Piirtää annetun nimen.
     *
     * @param graphics millä piirretään.
     * @param nimi nimi.
     * @param x koord.
     * @param y koord.
     */
    public void piirraNimi(Graphics graphics, String nimi, int x, int y) {
        graphics.drawString("nimi: " + nimi, x, y);
    }

    /**
     * Piirtää arvovallan.
     *
     * @param graphics g.
     * @param arvovalta se just.
     * @param x minne.
     * @param y sinne.
     */
    public void piirraArvovalta(Graphics graphics, int arvovalta, int x, int y) {
        graphics.drawOval(x, y, 17, 16);
        graphics.drawString("" + arvovalta, x + 5, y + 13);
    }

    /**
     * Piirtää pienen arvovaltapalluran.
     *
     * @param graphics g.
     * @param arvovalta a.
     * @param x x.
     * @param y y.
     */
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
