package messec.state;

import messec.App;
import messec.misc.PreTaxDeductor;

public class FederalTaxer implements Taxer {
    public static final double STANDARD_DEDUCTION = 24000;

    private final double grossIncomeOne;
    private final double grossIncomeTwo;
    private final double grossIncome;

    private final double[] bracketBreakpoints = {0, 19050, 77400, 165000, 315000, 400000, 600000, Double.MAX_VALUE};
    private final double[] bracketRates = {.10, .12, .22, .24, .32, .35, .37};
    private final double[] bracketTotals = {0, 190.5, 7192.5, 26464.5, 62464.5, 89664.5, 159664.5};

    protected FederalTaxer(double grossIncomeOne, double grossIncomeTwo) {
        this.grossIncomeOne = grossIncomeOne;
        this.grossIncomeTwo = grossIncomeTwo;
        this.grossIncome = grossIncomeOne + grossIncomeTwo;
    }

    public double getDeduction() {
        //TODO: Calculate non standard deduction
        return STANDARD_DEDUCTION;
    }

    public double calculateTax() {
        double taxPayable = 0;
        for (int i = 0; i < 7; i++) {
            taxPayable += getTaxableIncome() > bracketBreakpoints[i+1] ? 0 :
                    (getTaxableIncome() - bracketBreakpoints[i]) * bracketRates[i] + bracketTotals[i];
        }
        return taxPayable;
    }

    public double getPreTaxDeductionsTotal() {
        final PreTaxDeductor preTaxDeductor = new PreTaxDeductor(this.grossIncomeOne, this.grossIncomeTwo);
        return preTaxDeductor.getSemiWeeklyPreTaxDeductions() * App.WEEKS_IN_YEAR;
    }

    public double getTaxableIncome() {
        return this.grossIncome * App.WEEKS_IN_YEAR - getPreTaxDeductionsTotal() - getDeduction();
    }

}
