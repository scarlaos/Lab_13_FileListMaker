import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class Main {
    private static boolean modified = false;
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        ArrayList<String> myArrList = new ArrayList<>();
        loadListFromFile(myArrList);
        String input = "";

        while(true){
            printMenu();
            input = in.nextLine().trim().toUpperCase();

            switch(input){
                case "A":
                    aChoice(in,myArrList);
                    break;
                case "D":
                    dChoice(in,myArrList);
                    break;
                case "I":
                    iChoice(in,myArrList);
                    break;
                case "V":
                    vChoice(myArrList);
                    break;
                case "M":
                    mChoice(in, myArrList);
                    break;
                case "S":
                    sChoice(myArrList);
                    break;
                case "O":
                    oChoice(in,myArrList);
                    break;
                case "C":
                    cChoice(in, myArrList);
                    break;
                case "N":
                    nChoice(in,myArrList);
                    break;
                case "Q":
                    qChoice(in, myArrList);
                    return;
                default:
                    System.out.println("Invalid choice. Try again: ");
                    break;
            }
        }
    }


    private static void printMenu()
    {
        System.out.println("List options: ");
        System.out.println("| A - Adds an item to the list | D - Deletes an item | I - Insert an item | V - Prints the list | M - Moves an item | S - Saves list | O - Opens a list | C - Clears list | N - new saved list | Q - To quit altering the list |");
        System.out.println("Enter your choice: ");
    }
    private static void aChoice(Scanner in, ArrayList<String> myArrList){
        System.out.println("Type the item you would like to add: ");
        String addedItem = in.nextLine().trim();
        myArrList.add(addedItem);
        modified = true;
        System.out.println(addedItem + " is now in the list.");
    }
    private static void dChoice(Scanner in, ArrayList<String> myArrList){

        int indexDelete = SafeInput.getRangedInt(in,"Type the number you would like to delete",1,myArrList.size());
        indexDelete -= 1;


        if(indexDelete >= 0 && indexDelete < myArrList.size()){
            String deletedItem = myArrList.remove(indexDelete);
            modified = true;
            System.out.println(deletedItem + " has been removed from the list.");
        } else {
            System.out.println("Invalid location. Nothing deleted.");
        }
    }
    private static void iChoice(Scanner in, ArrayList<String> myArrList){

        System.out.println("Enter the item you would like to inset: ");
        String itemInsert = in.nextLine().trim();
        System.out.println("What index would like this item to be inserted? Enter the index: ");
        int index = in.nextInt();
        in.nextLine();

        if(index >= 0 && index <= myArrList.size()){
            myArrList.add(index, itemInsert);
            System.out.println(itemInsert + " has been inserted at index: " + index);
        }else{
            System.out.println("Invalid index. Item not inserted.");
        }
    }
    private static void vChoice(ArrayList<String>myArrList){
        if(myArrList.isEmpty()){
            System.out.println("List is empty");
        }else{
            System.out.println("List: ");
            for(int i = 0; i < myArrList.size(); i++){
                System.out.println((i+1) + ". " + myArrList.get(i));
            }
        }
    }
    private static void qChoice(Scanner in,ArrayList<String> myArrList){
        if(modified) {
            boolean save = SafeInput.getYN(in, "You have unsaved changes. Would you like to save the list before quitting? (Y/N): ");
            if (save) {
                sChoice(myArrList);
            }
        }
        System.out.println("Goodbye!");
    }
    private static void mChoice(Scanner in,ArrayList<String>myArrList){
        System.out.println("Enter the index of the item you would like to move: ");
        int indexMove = SafeInput.getRangedInt(in, "Type the number of item to move",1,myArrList.size()) - 1;

        if(indexMove >=0 && indexMove < myArrList.size()) {
            String itemToMove = myArrList.get(indexMove);
            System.out.println("You are moving: " + itemToMove);
            System.out.println("Enter the new position for this item: ");
            int newIndex = SafeInput.getRangedInt(in, "Type the new index for the item", 1,myArrList.size());

            if(newIndex -1 ==indexMove){
                System.out.println("Item is already in this position.");
                return;
            }
            if(newIndex > indexMove){
                myArrList.remove(indexMove);
                myArrList.add(newIndex-1,itemToMove);
            }else{
                myArrList.remove(indexMove);
                myArrList.add(newIndex-1,itemToMove);
            }
            System.out.println(itemToMove + " has been moved to index: " + (newIndex -1));
        }else{
            System.out.println("Invalid index. Nothing moved");
        }
    }
    private static void loadListFromFile(ArrayList<String>myArrList){
        try(BufferedReader reader = new BufferedReader(new FileReader("list.txt"))){
            String line;
            while((line = reader.readLine()) !=null){
                myArrList.add(line);
            }
            System.out.println("List has been loaded from 'list.txt'");
        }catch(IOException e){
            System.out.println("Error loading the list: " + e.getMessage());
        }
    }
    private static void sChoice(ArrayList<String>myArrList){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("list.txt"))){
            for(String item : myArrList){
                writer.write(item);
                writer.newLine();
            }
            System.out.println("List has been saved to 'list.txt'");
        }catch (IOException e){
            System.out.println("Error saving list: " + e.getMessage());
        }
    }
    private static void oChoice(Scanner in, ArrayList<String>myArrList){
        System.out.println("Enter the name of the file you want to open with .txt extension: ");
        String fileName = in.nextLine().trim();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            myArrList.clear();
            String line;
            while((line = reader.readLine()) !=null){
                myArrList.add(line);
            }
            System.out.println("List has been loaded from: " + fileName);
        }catch(IOException e){
            System.out.println("Error opening the file: " + e.getMessage());
        }
    }
    private static void cChoice(Scanner in, ArrayList<String>myArrList){
        boolean confirmation = SafeInput.getYN(in, "Are you sure you want to clear the entire list? Y for yes N for no");
        if(confirmation){
            myArrList.clear();
            System.out.println("The list has been cleared");
        }else{
            System.out.println("The list could not be cleared");
        }
    }
    private static void nChoice(Scanner in,ArrayList<String>myArrList){
        System.out.println("Enter a name for the new list file with .txt extension:");
        String newFileName = in.nextLine().trim();

        ArrayList<String>newList = new ArrayList<>();
        String addItem;
        boolean addMoreItems = true;
        while(addMoreItems){
            System.out.println("Enter an item you would like to add or type done to quit:");
            addItem = in.nextLine().trim();

            if(addItem.equalsIgnoreCase("done")){
                addMoreItems = false;
            }else{
                newList.add(addItem);
            }
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(newFileName))){
            for(String item : newList){
                writer.write(item);
                writer.newLine();;
            }
            System.out.println("New list has been saved to " + newFileName);
        }catch(IOException e){
            System.out.println("Error in saving new list: " + e.getMessage());
        }
    }
}
