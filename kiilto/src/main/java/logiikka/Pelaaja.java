
package logiikka;

import logiikka.omaisuusluokat.*;
import logiikka.valineluokat.*;

/**
 *
 * @author xvixvi
 */
public class Pelaaja {
    private Kasakokoelma karkit;
    private Omaisuus omaisuus;
    private String nimi;

    Pelaaja(String nimi) {
        this.nimi = nimi;
        omaisuus = new Omaisuus();
        karkit = new Kasakokoelma(0);
    }
    
    public Kasakokoelma getKarkit() {
        return karkit;
    }
    
    String getNimi() {
        return nimi;
    }
    
    @Override
    public String toString() { //ilman omistuksia
        return nimi + "\n" + karkit +"\n" + omaisuus;
    }

    public boolean voittaja(int voittoraja) {
        if (omaisuus.getArvovalta() >= voittoraja) {
            return true;
        }
        return false;
    }

}
