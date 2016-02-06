
package logiikka.valineluokat;

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
}
