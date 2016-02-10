package logiikka;

import java.io.File;
import java.util.*;
import logiikka.omaisuusluokat.*;
import logiikka.valineluokat.*;

/**
 *
 * @author xvixvi
 */
public class Poyta {

    private ArrayList<Pelaaja> pelaajat;
    private Kasakokoelma karkkimarkkinat;
    private ArrayList<Omaisuus> omistuspakat;
    private Random arpoja = new Random();

    Poyta(ArrayList<Pelaaja> pelaajat) {
        this.pelaajat = pelaajat;
        karkkimarkkinat = new Kasakokoelma(pelaajat.size());
        alustaOmistuspakat();
        luoOmistukset(luoLukija());
        sekoitaOmistuspakat();
    }

    private void alustaOmistuspakat() {
        omistuspakat = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            omistuspakat.add(new Omaisuus());
        }
    }

    public Scanner luoLukija() {
        Scanner lukija = null;
        try {
            File f = new File("/home/xvixvi/kiilto/kiilto/src/main/java/aputiedostoja/omistustentiedot.csv");
            lukija = new Scanner(f);
        } catch (Exception e) {
            System.out.println("lukuongelmia @ Poyta:luoLukija" + e);
        }
        
        return lukija;
    }

    private void luoOmistukset(Scanner lukija) {
        for (int i = 0; i < 3; i++) {
            Omaisuus omistuspakka = omistuspakat.get(i);
            int j = 40;
            if (i == 1) j = 30;
            if (i == 2) j = 20;
            
            while (j>0) {
                omistuspakka.lisaaOmistus(luoOmistus(lukija.nextLine()));
                j--;
            }
        }
    }

    public Omistus luoOmistus(String rivi) {
        String[] palat = rivi.split(",", 9);
        Kasakokoelma hinta = new Kasakokoelma(palat);
        return new Omistus(palat[0], Integer.parseInt(palat[1]), palat[2], hinta);
    }

    public ArrayList<Omaisuus> getOmistuspakat() {
        return omistuspakat;
    }
    
    public void sekoitaOmistuspakat() {
        for (Omaisuus o : omistuspakat) {
            sekoitaOmistuspakka(o);
        }
    }
    
    public void sekoitaOmistuspakka(Omaisuus op) {
        op.sekoita(arpoja);
    }
    
    @Override
    public String toString() {
        String s = "Pöydässä pelaajat:\n";
        for (Pelaaja p : pelaajat) {
            s = s.concat(p+"\n");
        }
        for (int i = omistuspakat.size()-1; i > -1; i--) {
            s = s.concat("Omistuspakasta " + (i+1) +":\n\n");
            Omaisuus oma = omistuspakat.get(i);
            s = s.concat(oma.paallimmaisetToString()+"\n");
        }
        s = s.concat("***\n");
        s = s.concat("karkkimarkkinat:\n" +karkkimarkkinat.toString() + "\n");
        s = s.concat("***\n\n");
        return s;
    }

    Kasakokoelma getMarkkinat() {
        return karkkimarkkinat;
    }
    
    public ArrayList<String> getNakyvienNimet() {
        ArrayList<String> nimet = new ArrayList<>();
        for (Omaisuus o : omistuspakat) {
            nimet.addAll(o.getPaallimmaistenNimet());
        }
        return nimet;
    }

    boolean suoritaOsto(Pelaaja pelaaja, int ostonNumero) {
        Omaisuus omistuspakka;
        if (!this.getNakyvienNimet().contains(""+ostonNumero)) return false;

        if (ostonNumero < 41) omistuspakka=omistuspakat.get(0);
        else if (ostonNumero < 71) omistuspakka=omistuspakat.get(1);
        else omistuspakka=omistuspakat.get(2);
        
        //kysytään pelaajalta, onko hänellä varaa ostaa nimeämänsä omistus
        Omistus o = omistuspakka.getNakyvaOmistus(ostonNumero);
        if (pelaaja.onkoVaraa(o)) {
            pelaaja.osta(omistuspakka, o, karkkimarkkinat);
            return true;
        }
        return false;
    }
}
