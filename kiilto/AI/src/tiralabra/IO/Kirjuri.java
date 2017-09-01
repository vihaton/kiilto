package tiralabra.IO;

import tiralabra.tietorakenteet.Peli;
import tiralabra.tietorakenteet.apurakenteet.Lista;

//näitä en kyllä lähde kirjoittamaan itse
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        this.dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss");
        this.parseri = new JsonParseri();
    }

    public void kirjaa(Lista<Peli> pelit) {
        String id = "" + pelit.koko() + "kpl_" + dateFormat.format(new Date()) + ".txt";
        try {
            Path pathToFile = Paths.get(tiedostonNimi + id);
            Files.write(pathToFile, formatoiPelit(pelit), Charset.forName("UTF-8"));
            //Files.write(pathToFile, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private Iterable<String> formatoiPelit(Lista<Peli> pelit) {
        int riveja = (pelit.haeEnsimmainen().pelaajia + 7) * pelit.koko();
        String[] rivit = new String[riveja];
        int osoitin = 0;
        for (int i = 0; i < pelit.koko(); i++) {
            String[] yhteenveto = formatoiPeli(pelit.haeIndeksista(i));
            rivit[osoitin++] = "\tPeli nro " + (i + 1);
            for (int j = 0; j < yhteenveto.length; j++) {
                rivit[osoitin++] = yhteenveto[j];
            }
        }
        return Arrays.asList(rivit);
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
