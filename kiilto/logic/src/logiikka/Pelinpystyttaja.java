package logiikka;

import logiikka.valineluokat.Kasakokoelma;

import java.util.ArrayList;

/**
 * Created by vili on 12.8.2017.
 *
 * Pelinpystyttäjä vastaa pelaajien ja pöydän generoimisesta. Toiminnallisuus oli aikaisemmin pelivelholla,
 * mutta nyt se on eriytetty selkeys- ja riippuvuussyistä eri moduuliin.
 */
public class Pelinpystyttaja {

    public int kierros;
    public ArrayList<Pelaaja> pelaajat;
    public Poyta poyta;
    public boolean[] onkoPelaajaAI;
    public boolean peliJatkuu;
    public int vuorossaOlevanNro;
    public Pelaaja vuorossaOleva;
    public boolean GUI;
    public int voittoValta = 15;
    public String pelaajanNimi;

    public Pelinpystyttaja(int kuinkaMontaAIta) {
        this(new boolean[kuinkaMontaAIta]);
        //muutetaan luodut pelaajat tekoälyiksi
        for (int i = 0; i < onkoPelaajaAI.length; i++) {
            onkoPelaajaAI[i] = true;
        }
    }

    /**
     * for non GUI games
     * @param onkoPelaajaAI
     */
    public Pelinpystyttaja(boolean[] onkoPelaajaAI) {
        this(onkoPelaajaAI, false, "Pelaaja");
    }

    public Pelinpystyttaja(boolean[] onkoPelaajaAI, boolean GUI, String nimi) {
        pelaajat = new ArrayList<>();
        this.onkoPelaajaAI = onkoPelaajaAI;
        this.pelaajanNimi = nimi;
        luoPelaajat(generoiNimet(onkoPelaajaAI));
        this.poyta = new Poyta(pelaajat);
        this.peliJatkuu = true;
        this.kierros = 1;
        vuorossaOlevanNro = 0;
        vuorossaOleva = pelaajat.get(vuorossaOlevanNro);
        this.GUI = GUI;
    }

    /**
     *
     * @param onkoAI lista, mitkä pelaajat ovat tekoälyjä
     * @return nimet
     */
    public ArrayList<String> generoiNimet(boolean[] onkoAI) {
        ArrayList<String> nimet = new ArrayList<>();
        int tekoalyja = 0;
        for (int i = 0; i < onkoAI.length ; i++) {
            if (onkoAI[i]) {
                tekoalyja++;
                nimet.add("AI " + tekoalyja);
            } else if (!pelaajanNimi.equalsIgnoreCase("pelaajan nimi?")){
                nimet.add(pelaajanNimi);
            } else {
                nimet.add("Pelaaja " + (i - tekoalyja + 1));
            }
        }
        return nimet;
    }

    /**
     * Luo annettujen nimien mukaiset pelaajat.
     *
     * @param nimet pelaajien nimet.
     */
    public void luoPelaajat(ArrayList<String> nimet) {
        for (int i = 0; i < nimet.size(); i++) {
            this.pelaajat.add(new Pelaaja(nimet.get(i)));
        }
    }

    /**
     * Nostaa vuorossa olevalle pelaajalle nallekarkkeja annettujen määrien
     * mukaan. Metodi luottaa, että syöte on tarkastettu hyväksi.
     *
     * @param maarat nostettavien nallekarkkien määrät.
     */
    public void nostaNallekarkkeja(int[] maarat) {
        Kasakokoelma karkit = vuorossaOleva.getKarkit();
        for (int i = 1; i < maarat.length; i++) {
            int m = maarat[i];
            if (m > 0) {
                poyta.getMarkkinat().siirraToiseenKasaan(karkit, i, m);
            }
        }
    }

    /**
     * Suorittaa oston, kohteesta riippuen pöydästä tai vuorossa olevan pelaajan
     * varauksista. Metodi luottaa saavansa hyvän syötteen.
     *
     * @param ostettavanNimi ostettavan omistuksen nimi.
     * @return suoritettiinko osto, eli käytännössä: jos pelaajalla ei ole varaa
     * niin false, muuten true.
     */
    public boolean osta(String ostettavanNimi) {
        if (poyta.getNakyvienNimet().contains(ostettavanNimi)) {
            return poyta.suoritaOsto(vuorossaOleva, Integer.parseInt(ostettavanNimi));
        } else {
            return poyta.suoritaOstoVarauksista(vuorossaOleva, Integer.parseInt(ostettavanNimi));
        }
    }

    /**
     * Metodi siirtää vuoron seuraavalle pelaajalle. Jos joku merkkihenkilö on
     * vaikuttunut pelaajan omaisuudesta, siirretään merkkihenkilö vierailemaan
     * pelaajalle. Jos joku pelaajista on voittaja, ja kierros on lopussa, peli
     * päättyy. Jos vuorossa oleva pelaaja on kierroksen viimeinen, kierrosluku
     * kasvaa ja vuoro siirtyy ensimmäiselle pelaajalle.
     *
     * @return true jos peli jatkuu, false jos peli päättyy
     */
    public boolean seuraavanPelaajanVuoro() {
        poyta.vaikuttikoPelaajaMerkkihenkiloon(vuorossaOleva);

        if (kierroksenViimeinen() && onkoVoittaja()) {
            peliJatkuu = false;
            return false;
        }

        if (kierroksenViimeinen()) {
            kierros++;
            vuorossaOleva = pelaajat.get(0);
            vuorossaOlevanNro = 0;
        } else {
            vuorossaOlevanNro++;
            vuorossaOleva = pelaajat.get(vuorossaOlevanNro);
        }

        return true;
    }

    /**
     * Onko vuorossa oleva pelaaja kierroksen viimeinen.
     *
     * @return -:-
     */
    public boolean kierroksenViimeinen() {
        return pelaajat.get(pelaajat.size() - 1) == vuorossaOleva;
    }

    private boolean onkoVoittaja() {
        for (Pelaaja p : pelaajat) {
            if (p.voittaja(voittoValta)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tiedetään varmaksi, että joku on voittanut, muttei vielä varmuudella
     * kuka.
     */
    public Pelaaja julistaVoittaja() {
        Pelaaja voittaja = pelaajat.get(0);
            for (int i = 1; i < pelaajat.size(); i++) {
            Pelaaja haastaja = pelaajat.get(i);
            if (haastaja.getArvovalta() > voittaja.getArvovalta()) {
                voittaja = haastaja;
            }
        }
            for (Pelaaja pelaaja : pelaajat) {
            if (pelaaja != voittaja && pelaaja.getOmaisuudenKoko() < voittaja.getOmaisuudenKoko() && pelaaja.getArvovalta() == voittaja.getArvovalta()) {
                voittaja = pelaaja;
            }
        }
        return voittaja;
    }


    public boolean onkoPelkkiaTekoalyja() {
        return onkoPelaajaAI[0];
    }

    /**
     * Tekee varauksen vuorossa olevalle pelaajalle. Metodi luottaa syötteen
     * olevan hyvä.
     *
     * @param varattavanNimi varattavan omistuksen nimi.
     * @return suoritettiinko varaus.
     */
    public boolean varaa(String varattavanNimi) {
        return poyta.teeVaraus(vuorossaOleva, Integer.parseInt(varattavanNimi));
    }

    public int kuinkaMontaAIta() {
        int c = 0;
        for (int i = 0; i < onkoPelaajaAI.length; i++) {
            if (onkoPelaajaAI[i])
            c++;
        }
        return c;
    }

    public void uusiPeli() {
    }
}
