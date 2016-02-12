
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
    
    private int[] omaisuusvaatimus;
    private int arvovaltalisa;
    
    public Merkkihenkilo(int[] ov, int arvo) {
        if (ov.length != 6) throw new IllegalArgumentException("taulukko ei ole koko != 6.");
        this.omaisuusvaatimus = ov;
        arvovaltalisa = arvo;
    }
    
    /**
     * Konstruktori, joka luo arvovaltalisäykseltään 3 arvoisen
     * merkkihenkilön.
     * 
     * @param omaisuusvaatimus kuuden kokoisessa taulukossa. 
     */
    public Merkkihenkilo(int[] ov) {
        this(ov, 3);
    }
    
    public int getArvo() {
        return arvovaltalisa;
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

}
