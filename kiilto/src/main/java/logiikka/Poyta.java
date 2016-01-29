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

    ArrayList<Pelaaja> pelaajat;
    Kasakokoelma karkkimarkkinat;
    ArrayList<Omaisuus> nakyvatOmistukset; //0 halvimmat, 1 keskitaso ja 2 kalleimmat
    ArrayList<Omaisuus> omistuspakat;

    Poyta(ArrayList<Pelaaja> pelaajat) {
        this.pelaajat = pelaajat;
        karkkimarkkinat = new Kasakokoelma(pelaajat.size());
        alustaOmistuspakat();
        luoOmistukset(luoLukija());
        nakyvatOmistukset = new ArrayList<>();
    }

    private void alustaOmistuspakat() {
        omistuspakat = new ArrayList<>();
        //tässä luetaan tekstitiedosto ja luodaan
        //kolme omistuspakkaa
        for (int i = 0; i < 3; i++) {
            omistuspakat.add(new Omaisuus());
        }
    }

    private Scanner luoLukija() {
        Scanner lukija = null;
        try {
            File f = new File("/home/xvixvi/kiilto/kiilto/src/main/java/aputiedostoja/omistustentiedot.csv");
            lukija = new Scanner(f);
        } catch (Exception e) {
            System.out.println("lukuongelmia @ Poyta:alustaOmistukset" + e);
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

    private Omistus luoOmistus(String rivi) {
        String[] palat = rivi.split(",", 9);
        Kasakokoelma hinta = new Kasakokoelma(palat);
        return new Omistus(palat[0], Integer.parseInt(palat[1]), palat[2], hinta);
    }

    @Override
    public String toString() { //ilman omistuksia
        String s = "Pöydässä pelaajat:\n";
        for (Pelaaja p : pelaajat) {
            s = s.concat(p+"\n");
        }
        s = s.concat("***\n");
        s = s.concat("karkkimarkkinat: " +karkkimarkkinat.toString() + "\n");
        
        return s;
    }
}
