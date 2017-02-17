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

    public void setInstruments(Map<Instrument, Double> instruments) {
        this.instruments = instruments;
    }

    public Instrument getPivot() {

        return pivot;
    }

    public void setPivot(Instrument pivot) {
        this.pivot = pivot;
    }

    public Basket(Instrument pivot, Map<Instrument, Double> instruments) {
        this.pivot = pivot;
        this.instruments = instruments;
    }
    public Basket() {
        this.instruments = new HashMap<>();
    }


}
