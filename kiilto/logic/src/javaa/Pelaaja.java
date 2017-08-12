package javaa;

import javaa.omaisuusluokat.Omaisuus;
import javaa.omaisuusluokat.Omistus;
import javaa.omaisuusluokat.Varaus;
import javaa.valineluokat.Kasakokoelma;
import javaa.valineluokat.Merkkihenkilo;

import java.util.*;

/**
 * Simuloi pelaajaa.
 *
 * @author xvixvi
 */
public class Pelaaja {

    private final Kasakokoelma karkit = new Kasakokoelma(0);
    private final Omaisuus omaisuus = new Omaisuus();
    private final String nimi;
    private final ArrayList<Varaus> varaukset = new ArrayList<>();
    private final ArrayList<Merkkihenkilo> merkkihenkilot = new ArrayList<>();

    /**
     * Luo Pelaaja olion.
     *
     * @param nimi pelaajan nimi.
     */
    public Pelaaja(String nimi) {
        this.nimi = nimi;
    }

    /**
     * Lisää omistuksen.
     *
     * @param omistus joka lisätään.
     */
    public void lisaaOmistus(Omistus omistus) {
        omaisuus.lisaaOmistus(omistus);
    }

    public Omaisuus getOmaisuus() {
        return omaisuus;
    }

    /**
     * Lue nimi.
     *
     * @return onko pelaajalla yli kymmenen karkkia.
     */
    public boolean liikaaKarkkeja() {
        return karkit.getKarkkienMaara() > 10;
    }

    public Kasakokoelma getKarkit() {
        return karkit;
    }

    /**
     * Asettaa kaikille kuudelle karkkikasalle uudet, annetut, arvot.
     *
     * @param maarat taulukko, jossa on kaikkien kuuden kasan uudet koot.
     * @return boolean oliko annettu taulukko oikean kokoinen.
     */
    public boolean setKarkit(int[] maarat) {
        if (maarat.length != 6) {
            return false;
        }

        for (int i = 0; i < maarat.length; i++) {
            karkit.kasvataKasaa(i, maarat[i] - karkit.getKasanKoko(i));
        }
        return true;
    }

    public String getNimi() {
        return nimi;
    }

    @Override
    public String toString() {
        return nimi + "\n" + karkit + "\n" + varauksetToString() + omaisuus;
    }

    /**
     * Onko pelaaja voittaja.
     *
     * @param voittoraja mitä tarvitaan voittamiseen.
     * @return onko pelaajalla enemmän arvovaltaa.
     */
    public boolean voittaja(int voittoraja) {
        return getArvovalta() >= voittoraja;
    }

    public int getOmaisuudenKoko() {
        return omaisuus.getKoko();
    }

    public int getArvovalta() {
        return omaisuus.getArvovalta() + getMerkkihenkiloidenArvo();
    }

    /**
     * Palauttaa kuuden kokoisen taulukon, jossa omistuksen omaisuuskorjattu
     * hinta.
     *
     * @param omistus jonka hintaa tarkastellaan.
     * @return int[], jossa omistuksen hinta korjattuna pelaajan omaisuuden
     * vaikutuksella.
     */
    public int[] getHintaOmaisuustulotHuomioituna(Omistus omistus) {
        int[] bonukset = omaisuus.getOmaisuudestaTulevatBonusKarkit();
        int[] hintaOmaisuustulotHuomioituna = new int[6];

        Kasakokoelma hinta = omistus.getHintaKasat();
        for (int i = 0; i < 6; i++) {
            int kasanKoko = hinta.getKasanKoko(i);
            hintaOmaisuustulotHuomioituna[i] = kasanKoko - bonukset[i];
        }
        return hintaOmaisuustulotHuomioituna;
    }

    /**
     * Tarkistaa pelaajan varallisuuden verrattuna annettuun omistukseen.
     *
     * @param omistus joka aiotaan ostaa.
     * @return onko pelaajalla varaa.
     */
    public boolean onkoVaraa(Omistus omistus) {
        if (omistus == null) {
            return false;
        }
        int[] hintaOmaisuustulotHuomioituna = getHintaOmaisuustulotHuomioituna(omistus);

        int kaytettyKulta = 0;
        int kkk = karkit.getKasanKoko(0); //kulta kasan koko

        //käydään läpi pelaajan karkkikasat, ja vertaillaan niitä oston hintaan pelaajan omaisuus huomioituna
        for (int i = 1; i < 6; i++) {
            int kki = karkit.getKasanKoko(i); //kasan koko i
            int hohi = hintaOmaisuustulotHuomioituna[i];

            //jos pelaajalla ei ole varaa, tarkistetaan kultavarannot
            if (kki < hohi) {
                //kultaa pitää olla tarpeeksi puutteen täyttämiseksi
                //vasemmalla puute, oikealla käytettävissä oleva kulta
                if (hohi - kki <= kkk - kaytettyKulta) {
                    kaytettyKulta += hohi - kki;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Siirtää annetun omistuksen annetusta omaisuudesta tälle pelaajalle, ja
     * ohjaa maksun annettuihin karkkikasoihin. Metodi olettaa, että pelaajalla
     * on varaa suorittaa maksu.
     *
     * @param lahtoOmaisuus omaisuus, josta ostos tehdään.
     * @param o Omistus, joka ostetaan.
     * @param markkinat Karkkimarkkinat, joille maksu kohdennetaan.
     */
    void osta(Omaisuus lahtoOmaisuus, Omistus o, Kasakokoelma markkinat) {
        siirraKarkit(o, markkinat);

        lahtoOmaisuus.siirraOmistus(omaisuus, o);
    }

    /**
     * Lisää merkkihenkilön pelaajan makuuhuoneen kalustoon.
     *
     * @param mh merkkihenkilö.
     */
    public void merkkihenkiloVierailee(Merkkihenkilo mh) {
        merkkihenkilot.add(mh);
    }

    /**
     * Veikkaappa.
     *
     * @return Veikkaappa.
     */
    public int getMerkkihenkiloidenArvo() {
        int summa = 0;
        for (Merkkihenkilo mh : merkkihenkilot) {
            summa += mh.getArvovaltalisa();
        }
        return summa;
    }

    public int getMerkkihenkiloidenMaara() {
        return merkkihenkilot.size();
    }

    /**
     * Tekee varauksista nätin tulostusulostuksen.
     *
     * @return varauksien tekstiesitys.
     */
    public String varauksetToString() {
        String s = "";
        for (int i = 0; i < varaukset.size(); i++) {
            s += varaukset.get(i).toString() + "\n";
        }
        return s;
    }

    public ArrayList<Varaus> getVaraukset() {
        return varaukset;
    }

    public int getVarauksienMaara() {
        return varaukset.size();
    }

    /**
     * Veikkaappa.
     *
     * @return Veikkaappa.
     */
    public ArrayList<String> getVaraustenNimet() {
        ArrayList<String> varaustenNrot = new ArrayList<>();
        for (int i = 0; i < varaukset.size(); i++) {
            varaustenNrot.add(varaukset.get(i).getOmistus().getNimi());
        }
        return varaustenNrot;
    }

    /**
     * Veikkaappa.
     *
     * @param varauksenNumero se just.
     * @return Veikkaappa.
     */
    public Omistus getVaraus(int varauksenNumero) {
        for (int i = 0; i < varaukset.size(); i++) {
            Omistus o = varaukset.get(i).getOmistus();
            if (o.getNimi().equalsIgnoreCase("" + varauksenNumero)) {
                return o;
            }
        }
        return null;
    }

    /**
     * Pelaaja varaa tämän omistuksen.
     *
     * @param o varattava omistus.
     * @return onnistuiko varaus.
     */
    public boolean teeVaraus(Omistus o) {
        if (varaukset.size() >= 3 || o == null) {
            return false;
        }

        varaukset.add(new Varaus(o));
        return true;
    }

    /**
     * Metodi varauksen ostamiseksi. Metodi olettaa, että pelaajalla on varaa
     * ostaa varaus.
     *
     * @param omistus joka ostetaan.
     * @param karkkimarkkinat jonne maksukarkit ohjataan.
     * @return boolean olivatko parametrit ok.
     */
    public boolean ostaVaraus(Omistus omistus, Kasakokoelma karkkimarkkinat) {
        if (varaukset.isEmpty()) {
            return false;
        }

        int i = 0;
        while (i < varaukset.size()) {
            if (varaukset.get(i).getOmistus() == omistus) {
                break;
            }
            i++;
        }

        //jos i on mennyt indekseistä yli, ei varauksissa ole kysyttyä omistusta!
        if (i == varaukset.size()) {
            return false;
        }

        siirraKarkit(omistus, karkkimarkkinat);
        varaukset.remove(i);
        omaisuus.lisaaOmistus(omistus);

        return true;
    }

    /**
     * Siirtää ostettavan omaisuuden hinnan (korjattuna ostavan pelaajan
     * omaisuuden vaikutuksella) mukaisen määrän karkkeja takaisin markkinoille.
     *
     * @param o jonka (omaisuuskorjatun)hinnan mukaan karkit siirretään.
     * @param markkinat karkkikasat, joihin omistuksen hinnan mukainen määrä
     * karkkeja siirretään.
     */
    private void siirraKarkit(Omistus o, Kasakokoelma markkinat) {
        int[] todellinenHinta = getHintaOmaisuustulotHuomioituna(o);

        for (int i = 0; i < todellinenHinta.length; i++) {
            int karkkihinta = todellinenHinta[i];
            if (karkkihinta > 0) {
                int pelaajanKarkkikasanKoko = karkit.getKasanKoko(i);
                //onko pelaajalla tarpeeksi samaa väriä?
                if (karkkihinta > pelaajanKarkkikasanKoko) {
                    //korvataan puuttuvat karkit kultaisilla
                    karkit.siirraToiseenKasaan(markkinat, 0, karkkihinta - pelaajanKarkkikasanKoko);
                    karkit.siirraToiseenKasaan(markkinat, i, pelaajanKarkkikasanKoko);
                } else {
                    //pelaajalla on vaara maksaa karkit samanvärisinä
                    karkit.siirraToiseenKasaan(markkinat, i, karkkihinta);
                }
            }
        }
    }
}
