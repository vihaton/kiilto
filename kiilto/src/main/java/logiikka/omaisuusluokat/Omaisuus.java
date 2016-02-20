package logiikka.omaisuusluokat;

import java.awt.Graphics;
import java.util.*;
import logiikka.valineluokat.*;
import ui.gui.VarinAsettaja;

/**
 *
 * @author xvixvi
 */
public class Omaisuus {

    private ArrayList<Omistus> omistukset;

    public Omaisuus() {
        omistukset = new ArrayList<>();
    }

    public void lisaaOmistus(Omistus o) {
        omistukset.add(o);
    }

    public int getArvovalta() {
        int arvovalta = 0;
        for (Omistus o : omistukset) {
            arvovalta += o.getArvovalta();
        }
        return arvovalta;
    }

    /**
     * Palauttaa omaisuudesta tulevat bonukset kuuden kokoisessa taulukossa
     * (kul,val,sin,vih,pun,mus).
     *
     * @return int[] bonukset, koko = 6
     */
    public int[] getOmaisuudestaTulevatBonusKarkit() {
        int[] bonukset = new int[6];
        for (Omistus o : omistukset) {
            bonukset[o.getLisaKarkinVariNumerona()]++;
        }
        return bonukset;
    }

    public int getKoko() {
        return omistukset.size();
    }

    public Omistus getOmistusIndeksista(int i) {
        return omistukset.get(i);
    }

    public String getOmistuksenNimiIndeksista(int i) {
        return omistukset.get(i).getNimi();
    }

    public Omistus getEkaOmistus() {
        return omistukset.get(0);
    }

    public Omistus getVikaOmistus() {
        return omistukset.get(omistukset.size() - 1);
    }

    public int getOmaisuudenKoko() {
        return omistukset.size();
    }

    public boolean onkoPA() {
        return omistukset.isEmpty();
    }

    @Override
    public String toString() {
        if (this.onkoPA()) {
            return "olen köyhä, minulla ei ole omaisuutta :´(" + "\n";
        }

        String s = "";
        for (Omistus o : omistukset) {
            s = s.concat(o.toString() + "\n");
        }
        return s;
    }

    public void sekoita(Random arpoja) {
        for (int i = 0; i < omistukset.size(); i++) {
            for (int j = omistukset.size(); j > 0; j--) {
                //siirretään omistus o indeksistä i satunnaiseen indeksiin r.
                int r = arpoja.nextInt(omistukset.size());
                Omistus o = omistukset.remove(i);
                omistukset.add(r, o);
            }
        }
    }

    public String paallimmaisetToString() {
        String s = "";
        int raja = omistukset.size() < 4 ? omistukset.size() : 4;
        for (int i = 0; i < raja; i++) {
            s = s.concat(omistukset.get(i).toString() + "\n\n");
        }
        return s;
    }

    public ArrayList<String> getPaallimmaistenNimet() {
        ArrayList<String> nimet = new ArrayList<>();
        int raja = omistukset.size() < 4 ? omistukset.size() : 4;
        for (int i = 0; i < raja; i++) {
            nimet.add(omistukset.get(i).getNimi());
        }
        return nimet;
    }

    public ArrayList<Omistus> getPaallimmaiset() {
        ArrayList<Omistus> paallimmaiset = new ArrayList<>();
        int raja = omistukset.size() < 4 ? omistukset.size() : 4;
        for (int i = 0; i < raja; i++) {
            paallimmaiset.add(omistukset.get(i));
        }
        return paallimmaiset;
    }

    public Omistus getNakyvaOmistus(int numero) {
        ArrayList<Omistus> nakyvat = this.getPaallimmaiset();
        for (Omistus o : nakyvat) {
            if (numero == Integer.parseInt(o.getNimi())) {
                return o;
            }
        }
        return null;
    }

    public void siirraOmistus(Omaisuus kohdeOmaisuus, Omistus o) {
        omistukset.remove(o);
        kohdeOmaisuus.lisaaOmistus(o);
    }

    public boolean poistaOmistus(Omistus o) {
        if (o == null || !omistukset.contains(o)) {
            return false;
        }
        return omistukset.remove(o);
    }

    public void piirraNakyvatOmistukset(Graphics graphics, VarinAsettaja va, int x, int y) {
        ArrayList<Omistus> paallimmaiset = getPaallimmaiset();
        
        for (Omistus omistus : paallimmaiset) {
            omistus.piirra(graphics, va, x, y);
            x += 110;
        }
    }
}
