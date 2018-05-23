package messec.state;

import messec.App;
import messec.misc.PreTaxDeductor;

public class California implements Taxer {
    public static final double SALES_TAX = 7.25;
    public static final double STANDARD_DEDUCTION = 8472.00;
    public static final double PERSONAL_TAX_CREDIT = 114.00;

    private final double grossIncomeOne;
    private final double grossIncomeTwo;
    private final double grossIncome;

    // 2017 tax brackets
    private final double[] bracketBreakpoints = {0, 16030, 38002, 59978, 83258, 105224, 537500, 644998, 1000000};
    private final double[] bracketRates = {.01, .02, .04, .06, .08, .093, .0103, .0113};
    private final double[] bracketTotals = {0, 160.3, 599.74, 1478.78, 2875.58, 4632.86, 44834.53, 55,906.82};

    protected California(double grossIncomeOne, double grossIncomeTwo) {
        this.grossIncomeOne = grossIncomeOne;
        this.grossIncomeTwo = grossIncomeTwo;
        this.grossIncome = grossIncomeOne + grossIncomeTwo;
    }

    @Override
    public double getDeduction() {
        //TODO Calculate non standard deductions
        return STANDARD_DEDUCTION;
    }

    @Override
    public double getPreTaxDeductionsTotal() {
        final PreTaxDeductor preTaxDeductor = new PreTaxDeductor(this.grossIncomeOne, this.grossIncomeTwo);
        return preTaxDeductor.getSemiWeeklyPreTaxDeductions() * App.WEEKS_IN_YEAR;
    }

    @Override
    public double getTaxableIncome() {
        return this.grossIncome * App.WEEKS_IN_YEAR - getPreTaxDeductionsTotal() - getDeduction();
    }

    @Override
    public double calculateTax() {
        double taxPayable = 0;
        for (int i = 0; i < 8; i++) {
            taxPayable += getTaxableIncome() > bracketBreakpoints[i+1] ? 0 :
                    (getTaxableIncome() - bracketBreakpoints[i]) * bracketRates[i] + bracketTotals[i];
        }
        return taxPayable - PERSONAL_TAX_CREDIT;
    }
}
