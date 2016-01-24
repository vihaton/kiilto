
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
    
}
