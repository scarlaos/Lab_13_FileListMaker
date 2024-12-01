import java.util.Scanner;

public class SafeInput
{
    public static int getRangedInt(Scanner in, String prompt, int low, int high)
    {
        int intVal = 0;
        boolean validInput = false;
        do{
            System.out.print(prompt + "[ " + low + " - " + high + " ]: ");
            if(in.hasNextDouble())
            {
                intVal = in.nextInt();
                in.nextLine();
                if(intVal >= low && intVal <= high)
                {
                    validInput = true;
                } else {
                    System.out.println("This number is out of the range, [ " + low + " - " + high + " ], you entered: " + intVal);
                }
            }
        }while(!validInput);
        return intVal;
    }

    public static boolean getYN(Scanner in, String prompt)
    {
        String respondYN = "";
        boolean returnVal = false;
        boolean validInput = false;

        do {
            System.out.println(prompt + ": ");
            respondYN = in.nextLine();

            if(respondYN.equalsIgnoreCase("Y"))
            {
                returnVal = true;
                validInput = true;
            }
            else if(respondYN.equalsIgnoreCase("N"))
            {
                returnVal = false;
                validInput = true;
            }else {
                System.out.println("You must enter Y/N. ");
            }

        }while(!validInput);
        return returnVal;
    }

    public static String getRegExString(Scanner pipe, String prompt, String regExPattern)
    {
        String value = "";
        boolean gotAValue = false;

        do
        {
            // show the prompt
            System.out.print(prompt + ": ");
            // input the data
            value = pipe.nextLine();
            // test to see if it is correct
            if(value.matches(regExPattern))
            {
                // We have a match this String passes!
                gotAValue = true;
            }
            else
            {
                System.out.println("\nInvalid input: " + value);
            }

        }while(!gotAValue);

        return value;
    }
}
