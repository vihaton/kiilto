package tiralabra.IO;

import tiralabra.tietorakenteet.Peli;
import tiralabra.tietorakenteet.apurakenteet.Lista;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        ArrayList<String> rivit = parseri.jsonifyPelit(pelit);
        try {
            Path pathToFile = Paths.get(tiedostonNimi + id);
            Files.write(pathToFile, rivit, Charset.forName("UTF-8"));
            //Files.write(pathToFile, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
