package logiikka.valineluokat;

import java.awt.Graphics;
import ui.gui.VarinAsettaja;

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

    void piirraIsosti(Graphics graphics, VarinAsettaja va, int x, int y) {
        int v = vari.ordinal();
        int reunaVari = v != 5 ? 5 : 1;
        
        for (int i = 0; i < koko; i++) {
            va.asetaVari(graphics,v);
            graphics.fillOval(x, y, 42, 42);
            
            va.asetaVari(graphics, reunaVari);
            graphics.drawOval(x, y, 42, 42);
            
            
            x += 3;
            y -= 3;
        }
        graphics.drawString("" + koko + "kpl", x + 5, y + 25);
    }
}
