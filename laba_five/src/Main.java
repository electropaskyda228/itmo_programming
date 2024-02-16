import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

public class Main {

    private static CollectionMovie collectionMovie = new CollectionMovie();

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        boolean work = true;
        while (work){
            System.out.print("> ");
            String line = scanner.nextLine();
            switch (line) {
                case "help":
                    printCommands("resources/commands.txt");
                    break;
                case "info":
                    showInfo();
                    break;
                case "add":
                    addElement();
                    break;
                case "exit":
                    work = false;
                    break;
            }
        }
    }

    private static void printCommands(String file_name) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file_name));
            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void addElement() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the coordinates x y: ");
        String[] coordsString = scanner.nextLine().split(" ");
        Coordinates coords = new Coordinates(Float.parseFloat(coordsString[0]), Double.parseDouble(coordsString[1]));

        System.out.print("Enter the count of oscars: ");
        int oscarsCount = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter the count of golden palm: ");
        Long goldenPalmCount = Long.parseLong(scanner.nextLine());

        System.out.print("Enter the genre (action / musical / adventure / science fiction): ");
        MovieGenre genre = MovieGenre.getFromString(scanner.nextLine());

        System.out.print("Enter the mpaa rating (g / pg / pg 13 / r / nc 17): ");
        MpaaRating mpaaRating = MpaaRating.getFromString(scanner.nextLine());

        System.out.print("Enter the operator's name: ");
        String nameOperator = scanner.nextLine();

        System.out.print("Enter the operator's birthday (month day year): ");
        String[] birthdayString = scanner.nextLine().split(" ");
        int year = Integer.parseInt(birthdayString[2]);
        int month = Integer.parseInt(birthdayString[0]);
        int day = Integer.parseInt(birthdayString[1]);
        java.util.Date birthday = new java.util.Date(year, month, day);

        System.out.print("Enter the operator's weight: ");
        Integer weight = scanner.nextInt();

        System.out.print("Enter the operator's passportID: ");
        String passportID = scanner.nextLine();

        System.out.print("Enter the operator's nationality (India / Vatican / Japan): ");
        Country nationality = Country.getFromString(scanner.nextLine());

        collectionMovie.addElement(name, coords, oscarsCount, goldenPalmCount, genre, mpaaRating,
                new Person(nameOperator, birthday, weight, passportID, nationality));
    }

    private static void showInfo() {
        System.out.println(collectionMovie.getInfo());
    }
}