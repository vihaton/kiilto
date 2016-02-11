
package logiikka.omaisuusluokat;

import logiikka.valineluokat.Kasakokoelma;

/**
 *
 * @author xvixvi
 * 
 * Luokka kuvaa omistuksia, jotka pelaaja on varannut
 */
public class Varaus {
    private Omistus omistus;
    
    public Varaus(Omistus o) {
        omistus = o;
    }
    
    public Kasakokoelma getHintaKasat() {
        return omistus.getHintaKasat();
    }
    
    public Omistus getOmistus() {
        return omistus;
    }
    
    
}
