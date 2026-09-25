public class FivePlanetTravelWidening
{
    public static void main(String[] args)
    {
        // ---- PROVIDED CODE: data types must not change ----
        int milesToMercury =  48000000;
        int milesToVenus   =  25000000;
        int milesToMars    =  33900000;
        int milesToJupiter = 365000000;
        int milesToSaturn  = 746000000;

        int milesPerHour = 36000;
        // ---------------------------------------------------

        // PARTNER A: widening, using NEW variables.
        // Assigning an int into a double widens it automatically -- no cast
        // needed, because every int value fits in a double without loss.
        double mercuryMiles = milesToMercury;
        double venusMiles   = milesToVenus;
        double marsMiles    = milesToMars;
        double jupiterMiles = milesToJupiter;
        double saturnMiles  = milesToSaturn;

        double speed = milesPerHour;

        // double / double is real division, so the fraction survives.
        double hoursToMercury = mercuryMiles / speed;
        double hoursToVenus   = venusMiles   / speed;
        double hoursToMars    = marsMiles    / speed;
        double hoursToJupiter = jupiterMiles / speed;
        double hoursToSaturn  = saturnMiles  / speed;

        System.out.println("Travel times at " + milesPerHour + " mph:");
        System.out.println("Mercury: " + hoursToMercury + " hours");
        System.out.println("Venus:   " + hoursToVenus   + " hours");
        System.out.println("Mars:    " + hoursToMars    + " hours");
        System.out.println("Jupiter: " + hoursToJupiter + " hours");
        System.out.println("Saturn:  " + hoursToSaturn  + " hours");
    }
}
