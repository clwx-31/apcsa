public class FivePlanetTravelCasting
{
    public static void main(String[] args)
    {
        // ---- PROVIDED CODE: data types must not change ----
        int milesToMercury =  48000000;
        int milesToVenus   =  25000000;
        int milesToMars    =  33900000;
        int milesToJupiter = 365000000;
        int milesToSaturn  = 746000000;

        int milesPerHour = 1500000000;
        // ---------------------------------------------------

        // PARTNER B: casting, with NO new variables.
        // (double) on the left operand promotes the right one too, so the
        // division is done in double arithmetic instead of int arithmetic.
        System.out.println("Travel times at " + milesPerHour + " mph:");
        System.out.println("Mercury: " + ((double) milesToMercury / milesPerHour) + " hours");
        System.out.println("Venus:   " + ((double) milesToVenus   / milesPerHour) + " hours");
        System.out.println("Mars:    " + ((double) milesToMars    / milesPerHour) + " hours");
        System.out.println("Jupiter: " + ((double) milesToJupiter / milesPerHour) + " hours");
        System.out.println("Saturn:  " + ((double) milesToSaturn  / milesPerHour) + " hours");
    }
}
 