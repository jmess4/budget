package messec.state;

public class IncomeTaxCalculator {

    private final double[] bracketBreakpoints;
    private final double[] bracketRates;
    private final double[] bracketTotals;

    public IncomeTaxCalculator(double[] bracketBreakpoints, double[] bracketRates, double[] bracketTotals) {
        assert bracketBreakpoints.length - bracketRates.length == 1;
        assert bracketBreakpoints.length - bracketTotals.length == 1;
        this.bracketBreakpoints = bracketBreakpoints;
        this.bracketRates = bracketRates;
        this.bracketTotals = bracketTotals;
    }

    public double incomeTaxDue(double taxableIncome) {
        for (int i = 0; i < this.bracketRates.length; i++) {
            if (taxableIncome < bracketBreakpoints[i + 1]) {
                return (taxableIncome - this.bracketBreakpoints[i]) * this.bracketRates[i] + this.bracketTotals[i];
            }
//            taxPayable += taxableIncome > this.bracketBreakpoints[i+1] ? 0 :
//                    (taxableIncome - this.bracketBreakpoints[i]) * this.bracketRates[i] + this.bracketTotals[i];
        }
        throw new IllegalStateException("Did not find applicable tax bracket");
    }

}
