package messec.state;

public interface Taxer {

    double getDeduction();

    double getPreTaxDeductionsTotal();

    double getTaxableIncome();

    double calculateTax();
}
