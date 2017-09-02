package tiralabra.IO;

import tiralabra.tietorakenteet.Peli;
import tiralabra.tietorakenteet.Strategia;
import tiralabra.tietorakenteet.apurakenteet.Lista;

//näitä en kyllä lähde kirjoittamaan itse
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Kirjaa pelien tulokset ylös.
 */
public class Kirjuri {

    private final SimpleDateFormat dateFormat;
    private String tiedostonNimi = "./AI/logs/pelit_";
    private JsonParseri parseri;

    public Kirjuri() {
        this.dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        this.parseri = new JsonParseri();
    }

    public void kirjaa(Lista<Peli> pelit) {
        String id = "" + pelit.koko() + "kpl_" + dateFormat.format(new Date()) + ".txt";
        try {
            Path pathToFile = Paths.get(tiedostonNimi + id);
            Files.write(pathToFile, formatoiPelitTekstiksi(pelit), Charset.forName("UTF-8"));
            //Files.write(pathToFile, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private Iterable<String> formatoiPelitTekstiksi(Lista<Peli> pelit) {
        int pelaajia = pelit.haeEnsimmainen().pelaajia;
        int peleja = pelit.koko();
        int yhteenvetorivit = 4 + pelaajia; //yhteenvetoon pelaajien määrä + kierrosten määrän ka + voittoarvovallan ka + väli
        int riveja = yhteenvetorivit + (pelaajia + 6) * peleja; //6 * pelaajien määrä rivejä per peli
        String[] rivit = new String[riveja];
        int osoitin = yhteenvetorivit;
        int[] strategioidenVoitot = new int[pelaajia];
        double kierroksia = 0;
        double voittoarvovalta = 0;

        //selvitetään jokaisen pelin yhteenveto
        for (int i = 0; i < peleja; i++) {
            String[] yhteenveto = formatoiPeli(pelit.haeIndeksista(i));
            strategioidenVoitot[voittajanIndeksi(yhteenveto[0])]++;
            kierroksia += Integer.parseInt(yhteenveto[3]);
            voittoarvovalta += Integer.parseInt(yhteenveto[2]);
            rivit[osoitin++] = "\tPeli nro " + (i + 1);
            for (int j = 0; j < yhteenveto.length; j++) {
                rivit[osoitin++] = yhteenveto[j];
            }
        }

        //lisätään ensimmäisille riveille kaikkien pelien yhteenveto
        Strategia[] strats = pelit.haeEnsimmainen().strategiat;
        rivit[0] = "Pelejä " + peleja + "kpl, tekoälyjä " + pelaajia + "kpl";
        for (int i = 0; i < pelaajia; i++) {
            int voittoja = strategioidenVoitot[i];
            String rivi = "Strategia " + strats[i].name() + " \t";
            if (rivi.length() < 20)
                rivi += "\t\t\t";
            rivi += "voitot " + voittoja + "kpl\t" + ((double) voittoja * 100 / peleja) + "%";
            rivit[i + 1] = rivi;
        }
        rivit[pelaajia + 1] = "kierroksia pelissä keskimäärin \t" + kierroksia / peleja;
        rivit[pelaajia + 2] = "voittoarvovalta keskimäärin \t" + voittoarvovalta / peleja;
        rivit[pelaajia + 3] = "*****************************";

        return Arrays.asList(rivit);
    }

    private int voittajanIndeksi(String voittajanNimi) {
        String[] palat = voittajanNimi.split(" ");
        return Integer.parseInt(palat[1]) - 1;
    }

    /**
     *
     * @param peli joka pelattiin
     * @return String[], length == pelaajia + 5
     */
    private String[] formatoiPeli(Peli peli) {
        int pelaajia = peli.pelaajia;
        int riveja = pelaajia + 5; //voittaja + voittostrat + arvovalta + kierroksia + pelaajat + välirivi
        String[] rivit = new String[riveja];

        rivit[0] = peli.voittaja.getNimi();
        rivit[1] = peli.voittoStrategia.name();
        rivit[2] = "" + peli.voittaja.getArvovalta();
        rivit[3] = "" + peli.kierroksia();
        //lisätään pelaajien strategiat
        for (int i = 0; i < pelaajia; i++) {
            int ind = 4 + i;
            if (i < peli.strategiat.length)
                rivit[ind] = peli.strategiat[i].name();
            else //jos strategioita on vähemmän kuin pelaajia
                rivit[ind] = peli.strategiat[peli.strategiat.length - 1].name();
        }
        rivit[rivit.length - 1] = "*******************";
        return rivit;
    }
}
