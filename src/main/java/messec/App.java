package messec;

import messec.misc.PostTaxCalculator;
import messec.misc.PreTaxCalculator;
import messec.state.Taxer;
import messec.state.Taxers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author jordan messec
 */
public class App
{
    private static final String PROPERTIES_FILE_NAME = "application.properties";
    public static final Properties properties = new Properties();
    static {
        InputStream input = null;
        try {
            input = App.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
            if (input == null) {
                System.out.println("Unable to locate application configuration - " + PROPERTIES_FILE_NAME +
                        " - from within the classpath. Exiting");
                System.exit(1);
            }
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Failed to read application configuration: - " + PROPERTIES_FILE_NAME +
                    " - before start. Exiting\n" + e.getLocalizedMessage());
            System.exit(1);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
    }
    public static final String ALIGNMENT_STRING = "%-30s %.2f%n";
    public static final int PAY_PERIODS_IN_YEAR = 26;

    public App() {}

    private void start() {
        double p1SemiWeeklyGrossPay = Double.parseDouble(properties.getProperty("p1.semiweekly.pay"));
        double p2SemiWeeklyGrossPay = Double.parseDouble(properties.getProperty("p2.semiweekly.pay"));
        double semiWeeklyGrossPay = p1SemiWeeklyGrossPay + p2SemiWeeklyGrossPay;
        String state = properties.getProperty("state");

        PreTaxCalculator preTaxCalculator = new PreTaxCalculator(p1SemiWeeklyGrossPay, p2SemiWeeklyGrossPay);
        PostTaxCalculator postTaxCalculator = new PostTaxCalculator(
                semiWeeklyGrossPay * App.PAY_PERIODS_IN_YEAR);

        Taxer stateTaxer = Taxers.stateTaxer(state, p1SemiWeeklyGrossPay, p2SemiWeeklyGrossPay, preTaxCalculator);
        double stateIncomeTax = stateTaxer.incomeTaxDue();

        Taxer federalTaxer = Taxers.federalTaxer(p1SemiWeeklyGrossPay, p2SemiWeeklyGrossPay, preTaxCalculator);
        double federalIncomeTax = federalTaxer.incomeTaxDue();

        double netYearlyPay = semiWeeklyGrossPay * PAY_PERIODS_IN_YEAR - stateIncomeTax -
                federalIncomeTax - postTaxCalculator.baseMedicareYearly -
                postTaxCalculator.medicareYearlyAdditional - postTaxCalculator.socialSecurityYearly;

        System.out.printf(ALIGNMENT_STRING, "Gross semi-weekly pay", semiWeeklyGrossPay);
        System.out.printf(ALIGNMENT_STRING, "Weekly pre-tax deductions", preTaxCalculator.semiWeeklyPreTaxDeductions());
        System.out.printf(ALIGNMENT_STRING, "Yearly pre-tax deductions", stateTaxer.yearlyPretaxDeductions());
        System.out.printf(ALIGNMENT_STRING, state + " deduction", stateTaxer.getDeduction());
        System.out.printf(ALIGNMENT_STRING, state + " state income tax", stateIncomeTax);
        System.out.printf(ALIGNMENT_STRING, "Federal deduction", federalTaxer.getDeduction());
        System.out.printf(ALIGNMENT_STRING, "Federal income tax", federalIncomeTax);
        System.out.printf(ALIGNMENT_STRING, "Weekly post-tax costs", postTaxCalculator.weeklyPostTaxCosts());
        System.out.printf(ALIGNMENT_STRING, "Yearly post-tax costs",
                postTaxCalculator.weeklyPostTaxCosts() * PAY_PERIODS_IN_YEAR);
        System.out.printf(ALIGNMENT_STRING, "Yearly medicare base tax", postTaxCalculator.baseMedicareYearly);
        System.out.printf(ALIGNMENT_STRING, "Yearly medicare additional tax",
                postTaxCalculator.medicareYearlyAdditional);
        System.out.printf(ALIGNMENT_STRING, "Yearly social security tax", postTaxCalculator.socialSecurityYearly);
                System.out.printf(ALIGNMENT_STRING, "Net yearly pay", netYearlyPay);
    }

    public static void main(String[] args) {
        new App().start();
    }
}
