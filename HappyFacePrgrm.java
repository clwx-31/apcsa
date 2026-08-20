public class HappyFacePrgrm
{
    public static void main(String[] args)
    {
        // \n makes a blank line, \t pushes the face over one tab stop
        System.out.println("\n\t ****** ");

        // print() leaves the cursor on the same line, so println() finishes the row
        System.out.print("\t*      ");
        System.out.println("*");

        // \" prints a real quotation mark -- these are the eyes
        System.out.print("\t* \"  \" *\n");
        System.out.println("\t*      *");

        System.out.print("\t*  __  *\n");
        System.out.println("\t ****** \n");
    }
}