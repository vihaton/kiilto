
package logiikka;

import logiikka.omaisuusluokat.*;
import logiikka.valineluokat.*;

/**
 *
 * @author xvixvi
 */
public class Pelaaja {
    Kasakokoelma karkit;
    Omaisuus omaisuus;
    String nimi;

    Pelaaja(String nimi) {
        this.nimi = nimi;
        omaisuus = new Omaisuus();
        karkit = new Kasakokoelma(0);
    }
    
    @Override
    public String toString() { //ilman omistuksia
        return nimi + "\n" + karkit +"\n";
    }
}
