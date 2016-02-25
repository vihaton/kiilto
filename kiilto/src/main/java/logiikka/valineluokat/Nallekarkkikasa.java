package logiikka.valineluokat;

import java.awt.Graphics;
import ui.gui.piirtaminen.Piirtoavustaja;

/**
 * Pelissä liikuteltavan valuutan talletusmuoto.
 * 
 * @author xvixvi
 */
public class Nallekarkkikasa {

    private Vari vari;
    private int koko;

    /**
     * Luo tyhjän kasan väriä i.
     * 
     * @param vari 
     */
    public Nallekarkkikasa(int vari) {
        this.koko = 0;
        this.vari = Vari.values()[vari];
    }

    /**
     * Luo kasan kokoa 'koko' värillä 'vari'.
     * 
     * @param vari the vari.
     * @param koko the koko.
     */
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

    /**
     * Onko kasa tyhjä vai ei.
     * 
     * @return onko kasa tyhjä.
     */
    public boolean onTyhja() {
        return this.koko < 1;
    }

    @Override
    public String toString() {
        return vari.toString().substring(0, 3).toLowerCase() + ":" + koko;
    }
}
