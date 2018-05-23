package messec.misc;

import messec.App;

public class PostTaxDeductor {
    double suppLifeSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.life")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.life"));
    double dependentLifeSemiWeekly = Double.parseDouble(App.properties.getProperty("p1.semiweekly.dependentlife")) +
            Double.parseDouble(App.properties.getProperty("p2.semiweekly.dependentlife"));

    public PostTaxDeductor(){}

    public double getPostTaxCosts() {
        return (double) (suppLifeSemiWeekly + dependentLifeSemiWeekly);
    }

}
