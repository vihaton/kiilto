
package logiikka.valineluokat;

import logiikka.omaisuusluokat.*;

/**
 *
 * @author xvixvi
 * 
 * Merkkihenkilot tulevat pelaajien luokse kyläilemään, jos pelaajalla
 * tarpeeksi monipuolisesti omaisuutta. Merkkihenkilön vierailu
 * antaa pelaajalle lisää arvovaltaa, ja ehkä kupan.
 */
public class Merkkihenkilo {
    
    private String nimi;
    private int[] omaisuusvaatimus;
    private int arvovaltalisa;
    
    /**
     * 
     * @param ov int[] jossa omaisuusvaatimus (kul,val,sin,vih,pun,mus).
     * @param arvo Merkkihenkilön vierailun pelaajalle antama lisäarvovalta.
     */
    public Merkkihenkilo(String nimi, int[] ov, int arvo) {
        if (ov.length != 6) throw new IllegalArgumentException("taulukko ei ole koko != 6.");
        this.omaisuusvaatimus = ov;
        arvovaltalisa = arvo;
        this.nimi = nimi;
    }
    
    /**
     * Konstruktori, joka luo arvovaltalisäykseltään 3 arvoisen
     * merkkihenkilön.
     * 
     * @param omaisuusvaatimus kuuden kokoisessa taulukossa. 
     */
    public Merkkihenkilo(String nimi, int[] ov) {
        this(nimi, ov, 3);
    }
    
    public Merkkihenkilo(int[] ov) {
        this("homo", ov, 3);
    }
    
    public int getArvo() {
        return arvovaltalisa;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    /**
     * Metodi merkkihenkilön mielenliikkeiden selvittämiseksi.
     * 
     * @param omaisuus omaisuus jolla yritetään houkutella merkkihenkilö kylään.
     * @return vaikuttuuko merkkihenkilö.
     */
    public boolean vaikuttuukoOmaisuudesta(Omaisuus o) {
        int[] omaisuusBonukset = o.getOmaisuudestaTulevatBonusKarkit();
        
        for (int i = 0; i < omaisuusBonukset.length; i++) {
            if (omaisuusBonukset[i] < omaisuusvaatimus[i]) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "olen " + nimi + ", arvovallaltani " + arvovaltalisa + "ja vaadin kauppiaalta\n"
                + omaisuusvaatimus;
        
    }
}
