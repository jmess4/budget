package messec.state;

import messec.misc.PreTaxCalculator;

public class Taxers {
    public static Taxer stateTaxer(String state, double g1, double g2, PreTaxCalculator pre) {
        switch (state) {
            case "CA" : return new California(g1, g2, pre);
            case "CO"   : return null;
            default           : System.out.println(state + " taxation method not yet implemented.");
                System.exit(1);
        }
        return null;
    }

    public static FederalTaxer federalTaxer(double g1, double g2, PreTaxCalculator pre) {
        return new FederalTaxer(g1, g2, pre);
    }
}
