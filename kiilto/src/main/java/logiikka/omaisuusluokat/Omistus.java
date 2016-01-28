
package logiikka.omaisuusluokat;

import java.util.*;
import logiikka.valineluokat.Nallekarkkikasa;
import logiikka.valineluokat.Vari;
/**
 *
 * @author xvixvi
 */
public class Omistus {
    private ArrayList<Nallekarkkikasa> hinta;
    private Vari lisaKarkinVari;
    private int arvovalta;
    
    public Omistus(ArrayList<Nallekarkkikasa> h, Vari vari, int arvo) {
        this.hinta = h;
        this.lisaKarkinVari = vari;
        arvovalta = arvo;
    }
    
    public Omistus(ArrayList<Nallekarkkikasa> h, String vari, int arvo) {
        this(h,Vari.valueOf(vari.toLowerCase().trim()),arvo);
    }

    public Omistus(ArrayList<Nallekarkkikasa> h, int vari, int arvo) {
        this(h, Vari.values()[vari], arvo);
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
