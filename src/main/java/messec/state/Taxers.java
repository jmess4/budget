package messec.state;

public class Taxers {
    public static Taxer stateTaxer(String state, double g1, double g2) {
        switch (state) {
            case "CA" : return new California(g1, g2);
            case "CO"   : return null;
            default           : System.out.println(state + " taxation method not yet implemented.");
                System.exit(1);
        }
        return null;
    }

    public static FederalTaxer federalTaxer(double g1, double g2) {
        return new FederalTaxer(g1, g2);
    }
}
