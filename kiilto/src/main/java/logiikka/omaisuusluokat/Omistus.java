
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
        if (n<1 || n>5) return 0;
        return hinta.getKasanKoko(n);
    }
    
    public Vari getLisaKarkinVari() {
        return lisaKarkinVari;
    }
    
    public int getLisaKarkinVariNumerona() {
        Vari[] varit = Vari.values();
        for (int i = 0; i < varit.length; i++) {
            if (varit[i]==lisaKarkinVari) return i;
        }
        return -1;
    }
    
    public int getArvovalta() {
        return arvovalta;
    }
    
    @Override
    public String toString(){
        return "Omistus nro " + nimi + "\n"
                + arvovalta + ", " + lisaKarkinVari + "\n" 
                + hinta.toStringIlmanKultaa();
    }

    String getNimi() {
        return this.nimi;
    }
}
