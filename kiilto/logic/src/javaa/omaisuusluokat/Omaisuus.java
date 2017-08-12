package javaa.omaisuusluokat;

import java.util.*;

/**
 * Kuvaa omistusten joukkoa, joka kuuluu jollekin taholle.
 *
 * @author xvixvi
 */
public class Omaisuus {

    private final ArrayList<Omistus> omistukset;

    /**
     * Luo tyhjän omaisuuden.
     */
    public Omaisuus() {
        omistukset = new ArrayList<>();
    }

    /**
     * Lisää omistuksen omaisuuteen.
     *
     * @param o omistus.
     */
    public void lisaaOmistus(Omistus o) {
        omistukset.add(o);
    }

    /**
     * Veikkaapa.
     *
     * @return Veikkaapa.
     */
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

    /**
     * Veikkaapa.
     *
     * @param i Veikkaapa.
     * @return Veikkaapa.
     */
    public Omistus getOmistusIndeksista(int i) {
        return omistukset.get(i);
    }

    /**
     * Veikkaapa.
     *
     * @param i Veikkaapa.
     * @return Veikkaapa.
     */
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

    /**
     * Onko omaisuudessa yhtään omistusta.
     *
     * @return onko persaukinen kommunistiluuseri.
     */
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

    /**
     * Sekoittaa omaisuuden suht' satunnaiseen järjestykseen.
     *
     * @param arpoja tuo satunnaisuuden.
     */
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

    /**
     * Palauttaa näkyvien omistusten tekstiesityksen.
     *
     * @return no ne omistukset.
     */
    public String paallimmaisetToString() {
        String s = "";
        int raja = omistukset.size() < 4 ? omistukset.size() : 4;
        for (int i = 0; i < raja; i++) {
            s = s.concat(omistukset.get(i).toString() + "\n\n");
        }
        return s;
    }

    /**
     * Veikkaapa.
     *
     * @return Veikkaapa.
     */
    public ArrayList<String> getPaallimmaistenNimet() {
        ArrayList<String> nimet = new ArrayList<>();
        int raja = omistukset.size() < 4 ? omistukset.size() : 4;
        for (int i = 0; i < raja; i++) {
            nimet.add(omistukset.get(i).getNimi());
        }
        return nimet;
    }

    /**
     * Veikkaapa.
     *
     * @return Veikkaapa.
     */
    public ArrayList<Omistus> getPaallimmaiset() {
        ArrayList<Omistus> paallimmaiset = new ArrayList<>();
        int raja = omistukset.size() < 4 ? omistukset.size() : 4;
        for (int i = 0; i < raja; i++) {
            paallimmaiset.add(omistukset.get(i));
        }
        return paallimmaiset;
    }

    /**
     * Veikkaapa.
     *
     * @param numero Veikkaapa.
     * @return Veikkaapa.
     */
    public Omistus getNakyvaOmistus(int numero) {
        ArrayList<Omistus> nakyvat = this.getPaallimmaiset();
        for (Omistus o : nakyvat) {
            if (numero == Integer.parseInt(o.getNimi())) {
                return o;
            }
        }
        return null;
    }

    /**
     * Siirtää omistuksen tästä omaisuudesta annettuun omaisuuteen.
     *
     * @param kohdeOmaisuus minne siirretään.
     * @param o mikä siirretään.
     */
    public void siirraOmistus(Omaisuus kohdeOmaisuus, Omistus o) {
        omistukset.remove(o);
        kohdeOmaisuus.lisaaOmistus(o);
    }

    /**
     * Poistaa annetun omistuksen tästä omaisuudesta.
     *
     * @param o omistus.
     * @return onnistuiko.
     */
    public boolean poistaOmistus(Omistus o) {
        if (o == null || !omistukset.contains(o)) {
            return false;
        }
        return omistukset.remove(o);
    }
}
