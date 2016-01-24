
package logiikka;

import java.util.*;
import logiikka.omaisuusluokat.*;
import logiikka.valineluokat.*;

/**
 *
 * @author xvixvi
 */
public class Poyta {
    ArrayList<Pelaaja> pelaajat;
    Kasakokoelma karkkimarkkinat;
    ArrayList<Omaisuus> nakyvatOmistukset; //0 halvimmat, 1 keskitaso ja 2 kalleimmat
    ArrayList<Omaisuus> omistuspakat;

    Poyta(ArrayList<Pelaaja> pelaajat) {
        this.pelaajat = pelaajat;
        karkkimarkkinat = new Kasakokoelma(pelaajat.size());
        alustaOmistuspakat();
        nakyvatOmistukset = new ArrayList<>();
    }

    private void alustaOmistuspakat() {
        omistuspakat = new ArrayList<>();
        //splendorkorttien kopioiminen tekstitiedostoon -> tässä luetaan tekstitiedosto ja luodaan
        //kolme omistuspakkaa
    }

}
