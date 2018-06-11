package messec.misc;

import messec.App;

public class PreTaxCalculator {
    final double grossOne;
    final double grossTwo;

    double medicalSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.health")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.health"));
    double visionSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.vision")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.vision"));
    double dentalSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.dental")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.dental"));
    double fsaSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.fsa")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.fsa"));
    double addSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.add")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.add"));
    double disabilitySemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.disability")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.disability"));
    double retirementSemiWeekly;

    public PreTaxCalculator(double grossOne, double grossTwo) {
        this.retirementSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.401k")) *
                grossOne +
                Double.parseDouble(App.properties.getProperty("p2.semiweekly.401k")) * grossTwo;
        this.grossOne = grossOne;
        this.grossTwo = grossTwo;
    }

    public double semiWeeklyPreTaxDeductions() {
        return (double)(medicalSemiWeekly + visionSemiWeekly + dentalSemiWeekly + fsaSemiWeekly + addSemiWeekly +
                disabilitySemiWeekly + retirementSemiWeekly);
    }

    public double esppDeduction() {
        return (grossOne + grossTwo) * .10 * App.PAY_PERIODS_IN_YEAR;
    }

    public double esppValue() {
        return esppDeduction() / .85;
    }
}
