package messec.state;

import messec.App;
import messec.misc.PreTaxCalculator;

public class FederalTaxer implements Taxer {
    public static final double STANDARD_DEDUCTION = 24000;

    private final double grossIncomeOne;
    private final double grossIncomeTwo;
    private final double grossIncome;
    private final PreTaxCalculator preTaxCalculator;

    private final double[] bracketBreakpoints = {0, 19050, 77400, 165000, 315000, 400000, 600000, Double.MAX_VALUE};
    private final double[] bracketRates = {.10, .12, .22, .24, .32, .35, .37};
    private final double[] bracketTotals = {0, 190.5, 7192.5, 26464.5, 62464.5, 89664.5, 159664.5};

    protected FederalTaxer(
            double grossIncomeOne, double grossIncomeTwo, PreTaxCalculator preTaxCalculator) {
        this.grossIncomeOne = grossIncomeOne;
        this.grossIncomeTwo = grossIncomeTwo;
        this.grossIncome = grossIncomeOne + grossIncomeTwo;
        this.preTaxCalculator = preTaxCalculator;
    }

    @Override
    public double getDeduction() {
        //TODO: Calculate non standard deduction
        return STANDARD_DEDUCTION;
    }

    @Override
    public double incomeTaxDue() {
        return new IncomeTaxCalculator(bracketBreakpoints, bracketRates, bracketTotals)
                .incomeTaxDue(taxableIncome());
    }

    @Override
    public double yearlyPretaxDeductions() {
        return preTaxCalculator.semiWeeklyPreTaxDeductions() * App.PAY_PERIODS_IN_YEAR;
    }

    @Override
    public double taxableIncome() {
        return this.grossIncome * App.PAY_PERIODS_IN_YEAR - yearlyPretaxDeductions() - getDeduction();
    }

}
