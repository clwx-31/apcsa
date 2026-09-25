public class CompoundOperators
{
  public static void main(String[] args)
  {
    int numPeople = 0;
    double totalYears = 0;
    int gradYear = 13;  // K plus grades 1-12: a finished senior year is 13 years of school.

    double years = 11.5;  // I will soon be halfway through my junior year.

    totalYears += years;
    numPeople++;
    System.out.println("I have " + (gradYear - years) + " years left until I graduate.");

    years = 11.5;
    totalYears += years;
    numPeople++;

    years = 7.5;
    totalYears += years;
    numPeople++;

    years = 13;
    totalYears += years;
    numPeople++;

    System.out.println("People counted: " + numPeople);
    System.out.println("Total years in school: " + totalYears);

    System.out.println("Average years in school: " + (totalYears / numPeople));
    System.out.println("Total days in school: " + (totalYears * 180));
    System.out.println("Average days in school: " + (totalYears * 180 / numPeople));
  }
}
