package logiikka.valineluokat;

import java.awt.Graphics;
import ui.gui.Piirtoavustaja;

/**
 *
 * @author xvixvi
 */
public class Nallekarkkikasa {

    private Vari vari;
    private int koko;

    public Nallekarkkikasa(int vari) {
        this.koko = 0;
        this.vari = Vari.values()[vari];
    }

    public Nallekarkkikasa(int vari, int koko) {
        this.koko = koko;
        this.vari = Vari.values()[vari];
    }

    public Vari getVari() {
        return this.vari;
    }

    public int getKoko() {
        return this.koko;
    }

    public void setKoko(int uk) {
        this.koko = uk;
    }

    void kasvata(int maara) {
        this.koko += maara;
    }

    public void kasvataYhdella() {
        this.koko += 1;
    }

    public void pienennaYhdella() {
        this.koko -= 1;
    }

    public boolean onTyhja() {
        return this.koko < 1;
    }

    @Override
    public String toString() {
        return vari.toString().substring(0, 3).toLowerCase() + ":" + koko;
    }

    /**
     * Piirtää nallekarkkikasan.
     *
     * @param graphics
     * @param pa piirtoavustaja.
     * @param x
     * @param y
     * @param iso piirretäänkö isoja karkkeja?
     */
    public void piirra(Graphics graphics, Piirtoavustaja pa, int x, int y, boolean iso) {
        int d = 22;
        if (iso) {
            d = 42;
        }
        int kerrosSiirtyma = 2;
        if (iso) {
            kerrosSiirtyma++;
        }
        int v = vari.ordinal();
        int reunaVari = v != 5 ? 5 : 1;

        for (int i = 0; i <= koko; i++) {
            pa.asetaVari(graphics, v);
            graphics.fillOval(x, y, d, d);

            pa.asetaVari(graphics, reunaVari);
            graphics.drawOval(x, y, d, d);

            x += kerrosSiirtyma;
            y -= kerrosSiirtyma;
        }
        if (koko == 0) {
            return;
        }

        if (iso) {
            graphics.drawString("" + koko + "kpl", x + d / 8, y + 2 * d / 3);
        } else {
            graphics.drawString("" + koko, x + d / 4, y + 4 * d / 5);
        }
    }
}
