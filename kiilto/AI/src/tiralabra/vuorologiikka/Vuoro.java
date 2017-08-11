package tiralabra.vuorologiikka;

/**
 * Created by vili on 11.8.2017.
 */
public class Vuoro {

    public VuoronToiminto toiminto;
    public int[] mitaNallekarkkejaNostetaan;
    public String varattavanOmaisuudenNimi;
    public String ostettavanOmaisuudenNimi;

    public Vuoro(VuoronToiminto toiminto) {
        this.toiminto = toiminto;
    }

}
