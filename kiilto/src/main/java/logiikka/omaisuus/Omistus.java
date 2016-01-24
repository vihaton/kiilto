
package logiikka.omaisuus;

import java.util.*;
import fi.lipasto1.logiikka.Nallekarkkikasa;
import fi.lipasto1.logiikka.Vari;
/**
 *
 * @author xvixvi
 */
public class Omistus {
    private ArrayList<Nallekarkkikasa> hinta;
    private Vari lisaKarkinVari;
    private int arvovalta;
    
    public Omistus(ArrayList<Nallekarkkikasa> h, String vari, int arvo) {
        this.hinta = h;
        this.lisaKarkinVari = Vari.valueOf(vari.toLowerCase().trim());
        arvovalta = arvo;
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
    
    public int getArvovalta() {
        return arvovalta;
    }
}
