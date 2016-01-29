
package logiikka.omaisuusluokat;

import java.util.*;
import logiikka.valineluokat.*;
/**
 *
 * @author xvixvi
 */
public class Omistus {
    private String nimi;
    private Kasakokoelma hinta;
    private Vari lisaKarkinVari;
    private int arvovalta;
    
    public Omistus(Kasakokoelma h, Vari vari, int arvo) {
        this.hinta = h;
        this.lisaKarkinVari = vari;
        arvovalta = arvo;
    }
    
    public Omistus(Kasakokoelma h, String vari, int arvo) {
        this(h,Vari.valueOf(vari.toLowerCase().trim()),arvo);
    }

    public Omistus(Kasakokoelma h, int vari, int arvo) {
        this(h, Vari.values()[vari], arvo);
    }
    
    public Kasakokoelma getHintaKasat() {
        return hinta;
    }
    
    public int getKasanKoko(int n) {
        if (n<1 || n>5) return 0;
        return hinta.getKasanKoko(n);
    }
    
    public Vari getLisaKarkinVari() {
        return lisaKarkinVari;
    }
    
    public int getArvovalta() {
        return arvovalta;
    }
    
    @Override
    public String toString(){
        //wip
        return "";
    }
}
