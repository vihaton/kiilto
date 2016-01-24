package logiikka;

import java.util.*;
import ui.*;

/**
 *
 * @author xvixvi
 */
public class Pelivelho {

    TUI tui;
    ArrayList<Pelaaja> pelaajat;
    Poyta poyta;

    public Pelivelho() {
        tui = new TUI(new Scanner(System.in));
    }

    public void pelaa() {
        alustaPeli();

    }

    public void alustaPeli() {
        int pm = tui.selvitaPelaajienMaara();
        ArrayList<String> nimet = tui.selvitaPelaajienNimet(pm);
        
        luoPelaajat(nimet);
        poyta = new Poyta(pelaajat);
    }

    private void luoPelaajat(ArrayList<String> nimet) {
        pelaajat = new ArrayList<>();
        for (int i = 0; i < nimet.size(); i++) {
            this.pelaajat.add(new Pelaaja(nimet.get(i)));
        }
    }
}
