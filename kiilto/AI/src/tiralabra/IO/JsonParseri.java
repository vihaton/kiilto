package tiralabra.IO;

import com.google.gson.Gson;
import tiralabra.tietorakenteet.*;
import tiralabra.tietorakenteet.apurakenteet.Lista;

import java.util.ArrayList;

/**
 * Created by vili on 23.8.2017.
 */
public class JsonParseri {

    private static Gson gson = new Gson();

    public static Peli parsePeli(String json) {
        return gson.fromJson(json, Peli.class);
    }

    public static String jsonifyPeli(Peli peli) {
        return gson.toJson(peli);
    }

    public static Strategia parseStrategia(String json) {
        return gson.fromJson(json, Strategia.class);
    }

    public static String jsonifyStrategia(Strategia strats) {
        return gson.toJson(strats);
    }

    public ArrayList<String> jsonifyPelit(Lista<Peli> pelit) {
        ArrayList<String> rivit = new ArrayList<>();
        for (int i = 0; i < pelit.koko(); i++) {
            rivit.add(jsonifyPeli(pelit.haeIndeksista(i)));
        }
        return rivit;
    }
}
