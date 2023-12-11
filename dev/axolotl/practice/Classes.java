package dev.axolotl.practice;

public class Classes {
    enum conditions
    {
        NULL,
        CLEAR,
        CLOUDY,
        RAINING,
        SNOWING,
        ACID_RAIN,
        YOU_SHOULD_PROBABLY_LEAVE_THE_AREA,
        ASHY_SKIES,
        TSUNAMI,
        CLOUDY_WITH_A_CHANCE_OF_MEATBALLS
    }
    public class forecast
    {
        double temperature;
        double visibility;
        double wind_speed;
        double wind_angle;
        double air_quality;
        conditions condition;
        String region_code;
    }
}
