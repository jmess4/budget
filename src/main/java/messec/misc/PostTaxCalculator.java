package messec.misc;

import messec.App;

public class PostTaxCalculator {
    private final static double SOCIAL_SECURITY_TAX_RATE = .062;
    private final static double SOCIAL_SECURITY_CAP = 128700;
    private final static double MEDICARE_BASE_TAX_RATE = .0145;
    private final static double MEDICARE_ADDITIONAL_RATE = .009;
    private final static double JOINT_MEDICARE_CUTOFF = 250000;

    private final double grossYearlyIncome;

    public final double suppLifeSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.life")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.life"));
    public final double dependentLifeSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.dependentlife")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.dependentlife"));
    public final double criticalIllnessSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.criticalillness")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.criticalillness"));
    public final double caVolDisability = Double.parseDouble(App.properties.getProperty("p1.semiweekly.cavoldisability")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.cavoldisability"));
    public final double socialSecurityYearly;
    public final double baseMedicareYearly;
    public final double medicareYearlyAdditional;

    public PostTaxCalculator(double grossYearlyIncome) {
        this.grossYearlyIncome = grossYearlyIncome;
        this.socialSecurityYearly = grossYearlyIncome > SOCIAL_SECURITY_CAP ?
                SOCIAL_SECURITY_TAX_RATE * SOCIAL_SECURITY_CAP :
                grossYearlyIncome * SOCIAL_SECURITY_TAX_RATE;
        this.baseMedicareYearly = grossYearlyIncome * MEDICARE_BASE_TAX_RATE;
        this.medicareYearlyAdditional = grossYearlyIncome > JOINT_MEDICARE_CUTOFF ?
                (grossYearlyIncome - JOINT_MEDICARE_CUTOFF) * MEDICARE_ADDITIONAL_RATE :
                0;
    }

    public double weeklyPostTaxCosts() {
        return (double) (suppLifeSemiWeekly + dependentLifeSemiWeekly + criticalIllnessSemiWeekly + caVolDisability);
    }

}
