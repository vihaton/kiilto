package logiikka;

import java.io.File;
import java.util.*;
import logiikka.omaisuusluokat.*;
import logiikka.valineluokat.*;

/**
 * Luokka sisältää kaikki pelin elementit.
 *
 * @author xvixvi
 */
public class Poyta {

    private final ArrayList<Pelaaja> pelaajat;
    private final Kasakokoelma karkkimarkkinat;
    private Omaisuus[] omistuspakat;
    private final Random arpoja = new Random();
    private final ArrayList<Merkkihenkilo> merkkihenkilot;

    /**
     * Luo pelipöydän, johon myös pelaajat sisällytetään.
     *
     * @param pelaajat veikkaapa.
     */
    public Poyta(ArrayList<Pelaaja> pelaajat) {
        this.pelaajat = pelaajat;
        karkkimarkkinat = new Kasakokoelma(pelaajat.size());
        merkkihenkilot = new ArrayList<>();

        alustaOmistuspakat();
        Scanner lukija = luoLukija("/omistustentiedot.csv");
        luoOmistukset(lukija);

        lukija = luoLukija("/merkkihenkilot.csv");
        luoMerkkihenkilot(lukija);
        valitsePelinMerkkihenkilot();
    }

    public ArrayList<Pelaaja> getPelaajat() {
        return pelaajat;
    }

    private void alustaOmistuspakat() {
        omistuspakat = new Omaisuus[]{
                new Omaisuus(),
                new Omaisuus(),
                new Omaisuus()
        };
    }

    /**
     * Yrittää luoda lukijan annetusta tiedostopolusta.
     *
     * @param fileNameWithForwardSlash tiedoston nimi siten, että nimen edessä
     * on käyttöjärjestelmästä riippumatta forwardSlash, koska ClassLoader
     * haluaa tiedoston nimen siten käyttöjärjestelmästä riippumatta.
     * @return Scanner lukija, joka lukee parametrina annetusta tiedostosta.
     */
    public Scanner luoLukija(String fileNameWithForwardSlash) {
        Scanner lukija = null;
        try {
            lukija = new Scanner(Poyta.class.getResourceAsStream(fileNameWithForwardSlash));
        } catch (Exception e) {
            System.out.println("lukuongelmia @ Poyta:luoLukija,\n syötteellä " + fileNameWithForwardSlash + "\n" + e);
        }

        return lukija;
    }

    private void luoOmistukset(Scanner lukija) {
        for (int i = 0; i < 3; i++) {
            Omaisuus omistuspakka = omistuspakat[i];
            int omistustenMaara = 40;
            if (i == 1) {
                omistustenMaara = 30;
            }
            if (i == 2) {
                omistustenMaara = 20;
            }

            while (omistustenMaara > 0) {
                omistuspakka.lisaaOmistus(luoOmistus(lukija.nextLine()));
                omistustenMaara--;
            }
        }

        sekoitaOmistuspakat();
    }

    private Omistus luoOmistus(String rivi) {
        String[] palat = rivi.split(",", 9);
        Kasakokoelma hinta = new Kasakokoelma(palat);
        return new Omistus(palat[0], Integer.parseInt(palat[1]), palat[2], hinta);
    }

    public Kasakokoelma getMarkkinat() {
        return karkkimarkkinat;
    }

    /**
     * Veikkaappa.
     *
     * @return Veikkaappa.
     */
    public ArrayList<String> getNakyvienNimet() {
        ArrayList<String> nimet = new ArrayList<>();
        for (Omaisuus o : omistuspakat) {
            nimet.addAll(o.getPaallimmaistenNimet());
        }
        return nimet;
    }

    public Omaisuus[] getOmistuspakat() {
        return omistuspakat;
    }

    /**
     * Sekoittaa omistuspakat.
     */
    public void sekoitaOmistuspakat() {
        for (Omaisuus o : omistuspakat) {
            sekoitaOmistuspakka(o);
        }
    }

    private void sekoitaOmistuspakka(Omaisuus op) {
        op.sekoita(arpoja);
    }

    public ArrayList<Merkkihenkilo> getMerkkihenkilot() {
        return merkkihenkilot;
    }

    private void luoMerkkihenkilot(Scanner lukija) {
        for (int i = 0; i < 10; i++) {
            Merkkihenkilo m = luoMerkkihenkilo(lukija.nextLine());
            merkkihenkilot.add(m);
        }
    }

    private Merkkihenkilo luoMerkkihenkilo(String rivi) {
        String[] palat = rivi.split(",", 9);
        int[] vaatimus = new int[6];
        for (int i = 3; i < 8; i++) {
            vaatimus[i - 2] = Integer.parseInt(palat[i]);
        }
        return new Merkkihenkilo(palat[0], vaatimus, Integer.parseInt(palat[1]));
    }

    private void valitsePelinMerkkihenkilot() {
        int henkiloita = 5;
        if (pelaajat.size() == 3) {
            henkiloita = 4;
        }
        if (pelaajat.size() == 2) {
            henkiloita = 3;
        }

        while (merkkihenkilot.size() > henkiloita) {
            int poistettava = arpoja.nextInt(merkkihenkilot.size());
            merkkihenkilot.remove(poistettava);
        }
    }

    /**
     * Tarkistetaan, vaikuttuiko joku merkkihenkilö pelaajan toimista.
     *
     * @param vuorossaOleva pelaaja.
     */
    public void vaikuttikoPelaajaMerkkihenkilon(Pelaaja vuorossaOleva) {
        ArrayList<Merkkihenkilo> vaikuttuneet = new ArrayList<>();
        for (Merkkihenkilo mh : merkkihenkilot) {
            if (mh.vaikuttuukoOmaisuudesta(vuorossaOleva.getOmaisuus())) {
                vaikuttuneet.add(mh);
            }
        }

        while (!vaikuttuneet.isEmpty()) {
            Merkkihenkilo mh = vaikuttuneet.remove(0);
            merkkihenkilot.remove(mh);
            vuorossaOleva.merkkihenkiloVierailee(mh);
        }
    }

    @Override
    public String toString() {
        String s = "Pöydässä pelaajat:\n";
        for (Pelaaja p : pelaajat) {
            s = s.concat(p + "\n");
        }

        s = s.concat("Merkkihenkilöt:\n");
        for (int i = 0; i < merkkihenkilot.size(); i++) {
            s = s.concat(merkkihenkilot.get(i).toString() + "\n");
        }

        for (int i = omistuspakat.length - 1; i > -1; i--) {
            s = s.concat("Omistuspakasta " + (i + 1) + ":\n\n");
            Omaisuus oma = omistuspakat[i];
            s = s.concat(oma.paallimmaisetToString() + "\n");
        }
        s = s.concat("***\n");
        s = s.concat("karkkimarkkinat:\n" + karkkimarkkinat.toString() + "\n");
        s = s.concat("***\n");
        return s;
    }

    /**
     * Suorittaa oston.
     *
     * @param pelaaja Joka ostaa omaisuutta pöydältä.
     * @param ostonNumero Joka ostetaan.
     * @return boolean Onnistuiko osto.
     */
    public boolean suoritaOsto(Pelaaja pelaaja, int ostonNumero) {
        Omaisuus omistuspakka;
        if (!this.getNakyvienNimet().contains("" + ostonNumero)) {
            return false;
        }

        if (ostonNumero < 41) {
            omistuspakka = omistuspakat[0];
        } else if (ostonNumero < 71) {
            omistuspakka = omistuspakat[1];
        } else {
            omistuspakka = omistuspakat[2];
        }

        //kysytään pelaajalta, onko hänellä varaa ostaa nimeämänsä omistus
        Omistus o = omistuspakka.getNakyvaOmistus(ostonNumero);
        if (pelaaja.onkoVaraa(o)) {
            pelaaja.osta(omistuspakka, o, karkkimarkkinat);
            return true;
        }
        return false;
    }

    /**
     * Suorittaa oston varauksista.
     *
     * @param pelaaja joka yrittää lunastaa varauksensa.
     * @param varauksenNumero varaus, jota lunastetaan.
     * @return Boolean onnistuiko yritys.
     */
    public boolean suoritaOstoVarauksista(Pelaaja pelaaja, int varauksenNumero) {
        if (!pelaaja.getVaraustenNimet().contains("" + varauksenNumero)) {
            return false;
        }

        Omistus o = pelaaja.getVaraus(varauksenNumero);
        if (pelaaja.onkoVaraa(o)) {
            return pelaaja.ostaVaraus(o, karkkimarkkinat);
        }
        return false;
    }

    /**
     * Tekee varauksen.
     *
     * @param pelaaja joka varaa.
     * @param varauksenNro varattavan varauksen numero (eli nimi).
     * @return boolean, onnistuiko varaaminen.
     */
    public boolean teeVaraus(Pelaaja pelaaja, int varauksenNro) {
        if (pelaaja == null) {
            return false;
        }
        if (pelaaja.getVarauksienMaara() >= 3) {
            return false;
        }
        int pakka = 0;
        if (varauksenNro > 40) {
            pakka = 1;
        }
        if (varauksenNro > 70) {
            pakka = 2;
        }
        Omaisuus omistusPakka = omistuspakat[pakka];

        Omistus varattava = omistusPakka.getNakyvaOmistus(varauksenNro);
        if (varattava == null) {
            return false;
        }
        if (!pelaaja.teeVaraus(varattava)) {
            return false;
        }
        omistusPakka.poistaOmistus(varattava);
        annaPelaajalleKulta(pelaaja);

        return true;
    }

    /**
     * Jos karkkimarkkinoilla on kultaa, pelaaja saa yhden kultakarkin!
     *
     * @param pelaaja Pelaaja, jolle karkki annetaan, jos sellainen löytyy.
     */
    private void annaPelaajalleKulta(Pelaaja pelaaja) {
        if (karkkimarkkinat.getKasanKoko(0) > 0) {
            karkkimarkkinat.siirraToiseenKasaan(pelaaja.getKarkit(), 0, 1);
        }
    }

    /**
     * Luo pelin testausta varten pelitilanteen, jossa pelaajilla on omaisuutta,
     * varauksia ym.
     *
     * @param n mitä isompi n, sitä pidemmällä peli on. Ykkönen(1) on pienin
     * mahdollinen syöte, ja se antaa jo suht hyvän startin. Viitosella(5)
     * omistukset saattavat loppua kesken.
     * @return kuinka monta kierrosta simulaatio suurin piirtein vastaa.
     */
    public int luoTestattavaPelitilanne(int n) {
        int kierros = 0;
        for (int j = 0; j < n; j++) {
            kierros += 6;
            for (Pelaaja pelaaja : pelaajat) {

                for (int i = 0; i < 6; i++) {
                    karkkimarkkinat.siirraToiseenKasaan(pelaaja.getKarkit(), i, 1);
                }

                ArrayList<String> nakyvat = omistuspakat[1].getPaallimmaistenNimet();
                for (int i = 1; i < 2; i++) {
                    teeVaraus(pelaaja, Integer.parseInt(nakyvat.get(i)));
                }

                nakyvat = pelaaja.getVaraustenNimet();
                nakyvat.addAll(omistuspakat[0].getPaallimmaistenNimet());
                for (String nakyva : nakyvat) {
                    suoritaOsto(pelaaja, Integer.parseInt(nakyva));
                }
                vaikuttikoPelaajaMerkkihenkilon(pelaaja);
            }
        }
        return kierros;
    }
}
