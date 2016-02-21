package logiikka.omaisuusluokat;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;
import logiikka.valineluokat.*;
import ui.gui.PiirtoAvustaja;

/**
 *
 * @author xvixvi
 */
public class Omistus {

    private String nimi;
    private Kasakokoelma hinta;
    private Vari lisaKarkinVari;
    private int arvovalta;

    public Omistus(String n, int arvo, Vari vari, Kasakokoelma h) {
        this.nimi = n;
        this.hinta = h;
        this.lisaKarkinVari = vari;
        arvovalta = arvo;
    }

    public Omistus(String n, int arvo, String vari, Kasakokoelma h) {
        this(n, arvo, Vari.valueOf(vari.toUpperCase()), h);
    }

    public Omistus(String n, int arvo, int vari, Kasakokoelma h) {
        this(n, arvo, Vari.values()[vari], h);
    }

    public Omistus(String n, int arvo, int vari, int[] karkkeja) {
        this(n, arvo, vari, new Kasakokoelma(karkkeja));
    }

    public Kasakokoelma getHintaKasat() {
        return hinta;
    }

    public int getKasanKoko(int n) {
        if (n < 1 || n > 5) {
            return 0;
        }
        return hinta.getKasanKoko(n);
    }

    public Vari getLisaKarkinVari() {
        return lisaKarkinVari;
    }

    public int getLisaKarkinVariNumerona() {
        return lisaKarkinVari.ordinal();
    }

    public int getArvovalta() {
        return arvovalta;
    }

    @Override
    public String toString() {
        return "Omistus nro " + nimi + "\n"
                + arvovalta + ", " + lisaKarkinVari + "\n"
                + hinta.toStringIlmanKultaa();
    }

    public String getNimi() {
        return this.nimi;
    }

    public void piirra(Graphics graphics, PiirtoAvustaja pa, int x, int y) {
        pa.asetaVari(graphics, getLisaKarkinVariNumerona());
        graphics.fillRect(x, y, 100, 25);
        graphics.setColor(Color.black);
        graphics.drawRect(x, y, 100, 125);

        pa.asetaReunavari(graphics, getLisaKarkinVariNumerona());
        pa.piirraNimi(graphics, nimi, x + 42, y + 16);
        pa.piirraArvovalta(graphics, arvovalta, x + 7, y + 5);

        piirraHinta(graphics, pa, x + 10, y + 30);
    }

    private void piirraHinta(Graphics graphics, PiirtoAvustaja pa, int x, int y) {
        for (int i = 1; i < 6; i++) {
            pa.asetaVari(graphics, i);
            graphics.drawString("" + getKasanKoko(i), x, y + i * 15);
        }
    }

    public void piirraPelaajanOmistus(Graphics graphics, PiirtoAvustaja pa, int x, int y, int vari) {
        graphics.setColor(Color.gray);
        graphics.fillRect(x, y, 40, 50);
        pa.asetaVari(graphics, vari);
        graphics.fillRect(x, y, 40, 17);
        graphics.setColor(Color.black);
        graphics.drawRect(x, y, 40, 50);
        pa.asetaReunavari(graphics, vari);
        pa.piirraArvovaltaPieni(graphics, arvovalta, x + 4, y + 3);
    }
}
