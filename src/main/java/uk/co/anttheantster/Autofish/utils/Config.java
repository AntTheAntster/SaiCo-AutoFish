package uk.co.anttheantster.Autofish.utils;

public class Config {

    public static Config instance = new Config();

    public boolean autoEatEnabled;
    public boolean gapAlertEnabled;



    public void toggleGapAlert() {
        gapAlertEnabled = !gapAlertEnabled;
    }

    public void toggleAutoEat() { autoEatEnabled = !autoEatEnabled; }
}
