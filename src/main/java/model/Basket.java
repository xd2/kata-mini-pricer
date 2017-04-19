package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xavier on 14/02/2017.
 */
public class Basket {
    Instrument pivot;
    Map<Instrument,Double> instruments;

    public Map<Instrument, Double> getInstruments() {
        return instruments;
    }


    public Instrument getPivot() {

        return pivot;
    }

    public Basket(Instrument pivot, Map<Instrument, Double> instruments) {
        this.pivot = pivot;
        this.instruments = instruments;
    }


}
