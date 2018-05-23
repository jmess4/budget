package messec.misc;

import messec.App;

public class PreTaxDeductor {
    double medicalSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.health")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.health"));
    double visionSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.vision")) +
            Double.parseDouble(App.properties.getProperty("p1.semiweekly.vision"));
    double dentalSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.dental")) +
            Double.parseDouble(App.properties.getProperty("p1.semiweekly.dental"));
    double fsaSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.fsa")) +
            Double.parseDouble(App.properties.getProperty("p1.semiweekly.fsa"));
    double addSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.add")) +
            Double.parseDouble(App.properties.getProperty("p1.semiweekly.add"));
    double disabilitySemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.disability")) +
            Double.parseDouble(App.properties.getProperty("p1.semiweekly.disability"));
    double retirementSemiWeekly;

    public PreTaxDeductor(double grossOne, double grossTwo) {
        this.retirementSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.401k")) * .01 *
                grossOne +
                Double.parseDouble(App.properties.getProperty("p2.semiweekly.401k")) * .01 * grossTwo;
    }

    public double getSemiWeeklyPreTaxDeductions() {
        return (double)(medicalSemiWeekly + visionSemiWeekly + dentalSemiWeekly + fsaSemiWeekly + addSemiWeekly +
                disabilitySemiWeekly + retirementSemiWeekly);
    }
}
