
package main.tuilogiikka;

import java.util.ArrayList;
import java.util.Scanner;
import logiikka.Pelaaja;
import logiikka.Poyta;
import main.ui.tui.TUI;
import logiikka.valineluokat.Kasakokoelma;

/**
 * Luokka kiilto -pelin kevyempään pelaamiseen tekstikäyttöliittymän kautta.
 * 
 * @author xvixvi
 */
public class TUIPelivelho {

    private final TUI tui;
    private final ArrayList<Pelaaja> pelaajat;
    private Poyta poyta;
    private int voittoValta;
    private int kierros;
    private Pelaaja vuorossaOleva;

    /**
     * Luo pelivelhon text user interface -peliin.
     */
    public TUIPelivelho() {
        tui = new TUI(new Scanner(System.in));
        pelaajat = new ArrayList<>();
        voittoValta = 15;
        kierros = 1;
        vuorossaOleva = null;
    }
    
    /**
     * Pelaa Text user interface -pelin.
     */
    public void pelaaTUI() {
//        alustaTestiTUIPeli();
        alustaTUIPeli();

        while (onkoVoittaja()) {
            pelaaKierros();
        }
    }
    
    private boolean onkoVoittaja() {
        for (Pelaaja p : pelaajat) {
            if (p.voittaja(voittoValta)) {
                return true;
            }
        }
        return false;
    }

    private void alustaTUIPeli() {
        int pm = tui.selvitaPelaajienMaara();
        ArrayList<String> nimet = tui.selvitaPelaajienNimet(pm);
        luoPelaajat(nimet);

        voittoValta = tui.selvitaVoittoonTarvittavaValta();
    }

    private void alustaTestiTUIPeli() {
        ArrayList<String> p = new ArrayList<>();
        p.add("varakas");
        p.add("homokaks");
        p.add("homo3");
        p.add("mr Gandalf");
        luoPelaajat(p);
        pelaajat.get(0).setKarkit(new int[]{3, 5, 5, 5, 5, 5});
        voittoValta = 5;
        poyta = new Poyta(pelaajat);
    }

    private void luoPelaajat(ArrayList<String> nimet) {
        for (int i = 0; i < nimet.size(); i++) {
            this.pelaajat.add(new Pelaaja(nimet.get(i)));
        }
    }
    private void pelaaKierros() {
        kierros++;
        tui.tulostaKierroksenVaihto(kierros, voittoValta);
        for (Pelaaja p : pelaajat) {
            pelaaVuoro(p);
        }
    }

    private void pelaaVuoro(Pelaaja pelaaja) {
        vuorossaOleva = pelaaja;
        tui.tulostaVuoronAlkuinfot(vuorossaOleva, poyta);

        int valinta = 0;
        valinta = tui.pelaajanToimi(vuorossaOleva.getNimi());

        if (valinta == 1) { // nostetaan nallekarkkeja
            nostaNallekarkkeja();
        } else if (valinta == 2) { // ostetaan omaisuutta
            ostaOmaisuutta();
        } else if (valinta == 3) { // tehdään varaus pöydästä
            teeVaraus();
        }

        if (vuorossaOleva.liikaaKarkkeja()) {
            poistaKarkkeja();
        }
    }

    private void nostaNallekarkkeja() {
        int[] maarat = tui.mitaKarkkejaNostetaan(poyta.getMarkkinat());
        //testataan, halusiko pelaaja tehdä toisen toiminnon
        if (maarat == null) {
            pelaaVuoro(vuorossaOleva);
            return;
        }

        nostaNallekarkkeja(maarat);
    }
    
    private void nostaNallekarkkeja(int[] maarat) {
        Kasakokoelma karkit = vuorossaOleva.getKarkit();
        for (int i = 0; i < maarat.length; i++) {
            int m = maarat[i];
            if (m > 0) {
                poyta.getMarkkinat().siirraToiseenKasaan(karkit, i + 1, m);
            }
        }
    }

    private void ostaOmaisuutta() {
        while (true) {
            int ostonNumero = tui.mikaOmistusOstetaan(poyta.getNakyvienNimet());

            if (ostonNumero == -1) {
                pelaaVuoro(vuorossaOleva);
                return;
            }

            if (ostonNumero == 0) {
                ostaVaraus();
                return;
            } else if (poyta.suoritaOsto(vuorossaOleva, ostonNumero)) {
                break;
            }
        }
    }

    private void ostaVaraus() {
        while (true) {
            int ostettava = tui.ostettavanVarauksenNro(vuorossaOleva);

            if (ostettava == -1) {
                pelaaVuoro(vuorossaOleva);
                return;
            }

            if (poyta.suoritaOstoVarauksista(vuorossaOleva, ostettava)) {
                break;
            }
        }
    }

    private void teeVaraus() {
        while (true) {
            int varauksenNro = tui.mikaOmistusVarataan(poyta.getNakyvienNimet());

            if (varauksenNro == -1) {
                pelaaVuoro(vuorossaOleva);
                return;
            }

            if (poyta.teeVaraus(vuorossaOleva, varauksenNro)) {
                break;
            }
        }
    }

    private void poistaKarkkeja() {
        int[] maarat = tui.mitaKarkkejaPoistetaa(vuorossaOleva);
        
        for (int i = 0; i < maarat.length; i++) {
            int n = maarat[i];
            
            if (n > 0) {
                vuorossaOleva.getKarkit().siirraToiseenKasaan(poyta.getMarkkinat(), i, n);
            }
        }
    }

}
