
package logiikka;

import java.util.*;
/**
 *
 * @author xvixvi
 */
public class Omistus {
    ArrayList<Nallekarkkikasa> hinta;
    Vari lisaKarkinVari;
    int pistearvo;
    
    public Omistus(ArrayList<Nallekarkkikasa> h, String vari, int arvo) {
        this.hinta = h;
        this.lisaKarkinVari = Vari.valueOf(vari.toLowerCase().trim());
        pistearvo = arvo;
    }

    public Omistus(ArrayList<Nallekarkkikasa> h, int vari, int arvo) {
        this(h, Vari.values()[vari].toString(), arvo);
    }
    
    public ArrayList<Nallekarkkikasa> getHinta() {
        return hinta;
    }
    
    public Vari getLisaKarkinVari() {
        return lisaKarkinVari;
    }
    
    public int getPistearvo() {
        return pistearvo;
    }
}
