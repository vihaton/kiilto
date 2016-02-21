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
        Vari[] varit = Vari.values();
        for (int i = 0; i < varit.length; i++) {
            if (varit[i] == lisaKarkinVari) {
                return i;
            }
        }
        return -1;
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

    void piirra(Graphics graphics, PiirtoAvustaja pa, int x, int y) {
        graphics.setColor(Color.blue);
        graphics.drawRect(x, y, 100, 125);
        
        pa.piirraNimi(graphics, nimi, x + 42, y + 14);
        pa.piirraArvovalta(graphics, arvovalta, x + 7, y + 7);
        
        piirraOmaisuusvaatimus(graphics, pa, x + 10, y + 30);
    }
    
    private void piirraOmaisuusvaatimus(Graphics graphics, PiirtoAvustaja pa, int x, int y) {
        for (int i = 1; i < 6; i++) {
            pa.asetaVari(graphics, i);
            graphics.drawString("" + getKasanKoko(i), x, y + i * 15);
        }
    }
}
