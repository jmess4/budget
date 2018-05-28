package messec.state;

public interface Taxer {

    double getDeduction();

    double yearlyPretaxDeductions();

    double taxableIncome();

    double incomeTaxDue();
}
