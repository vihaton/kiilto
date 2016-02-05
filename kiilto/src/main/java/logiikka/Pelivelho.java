package logiikka;

import java.util.*;
import ui.*;

/**
 *
 * @author xvixvi
 */
public class Pelivelho {

    private TUI tui;
    private ArrayList<Pelaaja> pelaajat;
    private Poyta poyta;
    private int voittoValta;
    private int kierros = 0;

    public Pelivelho() {
        tui = new TUI(new Scanner(System.in));
    }

    public void pelaa() {
//        alustaPeli();
        alustaTestiPeli();
//        tulostaKaikki();
        
        while(eiVoittajaa()) {
            pelaaKierros();
        }
    }

    private void alustaTestiPeli() {
        ArrayList<String> p = new ArrayList<>();
        p.add("homo1"); p.add("homo2");
        p.add("homo3");
        p.add("mr Gandalf");
        luoPelaajat(p);
        voittoValta = 10;
        poyta = new Poyta(pelaajat);
    }
    
    public void alustaPeli() {
        int pm = tui.selvitaPelaajienMaara();
        ArrayList<String> nimet = tui.selvitaPelaajienNimet(pm);
//        voittoValta = tui.selvitaVoittoonTarvittavaValta();
        voittoValta=10;
        
        luoPelaajat(nimet);
        poyta = new Poyta(pelaajat);
    }

    public void luoPelaajat(ArrayList<String> nimet) {
        pelaajat = new ArrayList<>();
        for (int i = 0; i < nimet.size(); i++) {
            this.pelaajat.add(new Pelaaja(nimet.get(i)));
        }
    }
    
    public ArrayList<Pelaaja> getPelaajat() {
        return pelaajat;
    }

    private void tulostaKaikki() {
        System.out.println("");
        System.out.println("Peli on nyt alustettu.");
        System.out.println("Tässä on pelipöydän ääreen ja päälle päätyneet asiat ja olennot:");
        System.out.println("");
        System.out.println(poyta);
        System.out.println("**********'");
        System.out.println("wip");
        //wip
    }

    private boolean eiVoittajaa() {
        for (Pelaaja p : pelaajat) {
            if (p.voittaja(voittoValta)) return false;
        }
        return true;
    }

    private void pelaaKierros() {
        kierros++;
        System.out.println("Kierros numero " + kierros +"\n");
        for (Pelaaja p : pelaajat) {
            pelaaVuoro(p);
        }
    }

    private void pelaaVuoro(Pelaaja p) {
        System.out.println("Pelaajan " + p.nimi + " vuoro.\n");
        System.out.println(poyta);
    }

}
