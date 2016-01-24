package ui;

import java.util.*;
import logiikka.*;

/**
 *
 * @author xvixvi
 */
public class TUI {

    Scanner lukija;
    Pelivelho pv;

    public TUI(Scanner sc) {
        lukija = sc;
        
    }

    public int selvitaPelaajienMaara() {
        int pm = -1;

        while (pm == -1) {
        System.out.println("Kuinka monta pelaajaa? (2-4)");
            try {
                pm = Integer.parseInt(lukija.nextLine());
                if (pm < 2 || pm > 4) {
                    throw new Exception();
                }
            } catch (Exception e) {
                pm = -1;
                System.out.println("Huonon syötteen annoit, saisinko paremman?");
                System.out.println("");
            }
        }
        return pm;
    }

    public ArrayList<String> selvitaPelaajienNimet(int pm) {
        ArrayList<String> nimet = new ArrayList<>();
        for (int i = 0; i < pm; i++) {
            System.out.println("Anna pelaaja nro" +i+1 +" nimi:");
            String nimi = lukija.nextLine();
            if (nimi.isEmpty())  break;
            
            nimet.add(nimi);
        }
        return nimet;
    }
}
