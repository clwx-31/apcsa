public class Concatenator
{
    public static void main(String[] args)
    {
        String firstString = "Hello";
        String secondString = "World";

        String firstHalf = firstString.substring(0, firstString.length() / 2);
        String secondHalf = secondString.substring(secondString.length() / 2);
        String concatenatedString = firstHalf + secondHalf;

        System.out.println(concatenatedString);
    }
}