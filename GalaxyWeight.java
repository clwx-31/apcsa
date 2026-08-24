public class GalaxyWeight
{
    public static void main(String[] args)
    {
        // My weight on Earth, in pounds. Change this number to your own weight.
        double earthWeight = 145;

        // Acceleration due to gravity, in m/s^2
        double earthGravity = 9.8;
        double mercuryGravity = 3.59;
        double marsGravity = 3.711;
        double jupiterGravity = 24.79;

        // Weight on another planet = Earth weight scaled by how that planet's
        // gravity compares to Earth's gravity
        double mercuryWeight = earthWeight * (mercuryGravity / earthGravity);
        double marsWeight = earthWeight * (marsGravity / earthGravity);
        double jupiterWeight = earthWeight * (jupiterGravity / earthGravity);

        // The parentheses force the three weights to be added FIRST,
        // and only then divided by 3
        double averageWeight = (mercuryWeight + marsWeight + jupiterWeight) / 3;

        System.out.println("Weight on Earth:   " + earthWeight + " lbs");
        System.out.println("Weight on Mercury: " + mercuryWeight + " lbs");
        System.out.println("Weight on Mars:    " + marsWeight + " lbs");
        System.out.println("Weight on Jupiter: " + jupiterWeight + " lbs");
        System.out.println();
        System.out.println("Average of the three planets: " + averageWeight + " lbs");
    }
}
