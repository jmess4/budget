package messec.state;

import messec.App;
import messec.misc.PreTaxCalculator;

public class California implements Taxer {
    public static final double SALES_TAX = 7.25;
    public static final double STANDARD_DEDUCTION = 8472.00;
    public static final double PERSONAL_TAX_CREDIT = 114.00;

    private final double grossIncomeOne;
    private final double grossIncomeTwo;
    private final double grossIncome;
    private final PreTaxCalculator preTaxCalculator;

    // 2017 tax brackets
    private final double[] bracketBreakpoints = {0, 16030, 38002, 59978, 83258, 105224, 537500, 644998, 1000000};
    private final double[] bracketRates = {.01, .02, .04, .06, .08, .093, .103, .113};
    private final double[] bracketTotals = {0, 160.3, 599.74, 1478.78, 2875.58, 4632.86, 44834.53, 55,906.82};

    protected California(double grossIncomeOne, double grossIncomeTwo, PreTaxCalculator preTaxCalculator) {
        this.grossIncomeOne = grossIncomeOne;
        this.grossIncomeTwo = grossIncomeTwo;
        this.grossIncome = grossIncomeOne + grossIncomeTwo;
        this.preTaxCalculator = preTaxCalculator;
    }

    @Override
    public double getDeduction() {
        //TODO Calculate non standard deductions
        return STANDARD_DEDUCTION;
    }

    @Override
    public double yearlyPretaxDeductions() {
        return preTaxCalculator.semiWeeklyPreTaxDeductions() * App.PAY_PERIODS_IN_YEAR;
    }

    @Override
    public double taxableIncome() {
        return this.grossIncome * App.PAY_PERIODS_IN_YEAR - yearlyPretaxDeductions() - getDeduction();
    }

    @Override
    public double incomeTaxDue() {
        return new IncomeTaxCalculator(bracketBreakpoints, bracketRates, bracketTotals)
                .incomeTaxDue(taxableIncome()) - PERSONAL_TAX_CREDIT;
    }
}
