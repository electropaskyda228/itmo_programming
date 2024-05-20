package main.java.models;

import main.java.utility.Console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

public class Question {
    public static class QuestionBreak extends Exception {}

    private static Queue<String> fileInfomation = null;

    public static String getNextLine() {
        if (fileInfomation == null || fileInfomation.isEmpty()) return "";
        return fileInfomation.poll();
    }

    public static void setFileInfomation(List<String> commands) {
        fileInfomation = new LinkedList<>();
        fileInfomation.addAll(commands);
    }

    public static int getFileInformationSize() {
        return (fileInfomation == null) ? 0 : fileInfomation.size();
    }

    public static void setDefaultFileInformation() {
        fileInfomation = null;
    }

    public static Movie questionToMovie(Console console, long id) throws QuestionBreak {
        try {
            if (fileInfomation == null)  console.println("Creating a new Movie");

            String name = questionToMovieName(console);
            if (name == null) return null;

            var coordinates = questionToCoordinates(console);
            if (coordinates == null) return null;

            int oscarsCount = questionToOscarsCount(console);
            if (oscarsCount == -1) return null;

            Long goldenPalmCount = questionToGoldenPalm(console);
            if (goldenPalmCount == null) return null;

            var movieGenre = questionToGenre(console);
            if (movieGenre == null) return null;

            var mpaaRating = questionToMpaaRating(console);
            if (mpaaRating == null) return null;

            var operator = questionToOperator(console);
            if (operator == null) return null;

            return new Movie(id, name, coordinates, ZonedDateTime.now(), oscarsCount, goldenPalmCount, movieGenre,
                    mpaaRating, operator);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static Coordinates questionToCoordinates(Console console) throws QuestionBreak{
        try {
            Integer x;
            while (true) {
                String line;
                if (fileInfomation == null) {
                    console.print("coordinate x: ");
                    line = console.readln().trim();
                } else {
                    line = getNextLine().trim();
                }
                if (line.equals("exit")) throw new QuestionBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Integer.parseInt(line);
                        if (x > 238) {
                            console.println("X can't be bigger than 238");
                            continue;
                        }
                        break;
                    } catch(NumberFormatException ignored) { }
                }
            }
            Double y;
            while (true) {
                String line;
                if (fileInfomation == null) {
                    console.print("coordinate y: ");
                    line = console.readln().trim();
                } else {
                    line = getNextLine().trim();
                }
                if (line.equals("exit")) throw new QuestionBreak();
                if (!line.isEmpty()) {
                    try {
                        y = Double.parseDouble(line);
                        if (y > 112) {
                            console.println("Y can't be bigger than 112");
                            continue;
                        }
                        break;
                    } catch(NumberFormatException ignored) { }
                }
            }
            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static Person questionToOperator(Console console) throws QuestionBreak {
        try {
            String name = questionToOperatorName(console);
            if (name == null) return null;

            Date birthday = questionToOperatorBirthday(console);
            if (birthday == null) return null;

            Integer weight = questionToOperatorWeight(console);
            if (weight == null) return null;

            String passportID = questionToOperatorPassport(console);

            Country nationality = questionToOperatorNationality(console);
            if (nationality == null) return null;

            return new Person(name, birthday, weight, passportID, nationality);

        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static MovieGenre questionToGenre(Console console) throws QuestionBreak {
        try {
            while (true) {
                String genreString;
                if (fileInfomation == null) {
                    console.print("movie's genre (action|musical|adventure|science fiction): ");
                    genreString = console.readln();
                } else {
                    genreString = getNextLine();
                }
                genreString = genreString.replace(" ", "").toLowerCase();
                if (genreString.equals("exit")) throw new QuestionBreak();
                if (genreString.equals("action") | genreString.equals("musical") | genreString.equals("adventure") |
                        genreString.equals("sciencefiction")) return MovieGenre.getFromString(genreString);
                console.println("You haven't chosen the right version");
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static MpaaRating questionToMpaaRating(Console console) throws QuestionBreak {
        try {
            while (true) {
                String ratingString;
                if (fileInfomation == null) {
                    console.print("mpaa rating (G|PG|PG 13|R|NC 17): ");
                    ratingString = console.readln();
                } else {
                    ratingString = getNextLine();
                }
                ratingString = ratingString.replace(" ", "").toLowerCase();
                if (ratingString.equals("exit")) throw new QuestionBreak();
                if (ratingString.equals("g") | ratingString.equals("pg") | ratingString.equals("pg13") |
                        ratingString.equals("r") |
                        ratingString.equals("nc17")) return MpaaRating.getFromString(ratingString);
                console.println("You haven't chosen the right version");
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static String questionToMovieName(Console console) throws QuestionBreak {
        try {
            while (true) {
                String name;
                if (fileInfomation == null) {
                    console.print("name: ");
                    name = console.readln().trim();
                } else {
                    name = getNextLine().trim();
                }
                if (name.equals("exit")) throw new QuestionBreak();
                if (!name.isEmpty()) return name;
                console.println("Name can't be empty");
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static int questionToOscarsCount(Console console) throws QuestionBreak {
        try {
            while (true) {
                String oscarsCountString;
                if (fileInfomation == null) {
                    console.print("oscar's count: ");
                    oscarsCountString = console.readln().trim();
                } else {
                    oscarsCountString = getNextLine().trim();
                }
                if (oscarsCountString.equals("exit")) throw new QuestionBreak();
                if (oscarsCountString.isEmpty()) continue;
                try {
                    int oscarsCount = Integer.parseInt(oscarsCountString);
                    if (oscarsCount > 0) return oscarsCount;
                    console.println("Oscar's count should be bigger than zero");
                } catch (NumberFormatException exception) {
                    console.printError("Oscar's count should be an integer");
                }
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return -1;
        }
    }

    public static Long questionToGoldenPalm(Console console) throws QuestionBreak {
        try {
            while (true) {
                String goldenPalmCountString;
                if (fileInfomation == null) {
                    console.print("golden palm's count: ");
                    goldenPalmCountString = console.readln().trim();
                } else {
                    goldenPalmCountString = getNextLine().trim();
                }
                if (goldenPalmCountString.equals("exit")) throw new QuestionBreak();
                if (goldenPalmCountString.isEmpty()) continue;
                try {
                    Long goldenPalmCount = Long.parseLong(goldenPalmCountString);
                    if (goldenPalmCount > 0) return goldenPalmCount;
                    console.println("Golden palm's count should be bigger than zero");
                } catch (NumberFormatException exception) {
                    console.printError("Golden palm's count should be an integer");
                }
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static String questionToOperatorName(Console console) throws QuestionBreak {
        try {
            while (true) {
                String name;
                if (fileInfomation == null) {
                    console.print("operator's name: ");
                    name = console.readln().trim();
                } else {
                    name = getNextLine().trim();
                }
                if (name.equals("exit")) throw new QuestionBreak();
                if (!name.isEmpty()) return name;
                console.println("Name can't be empty");
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static Date questionToOperatorBirthday(Console console) throws QuestionBreak {
        try {
            while (true) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                String dateString;
                if (fileInfomation == null) {
                    console.print("operator's birthday (format: dd-MM-yyyy): ");
                    dateString = console.readln().trim();
                } else {
                    dateString = getNextLine().trim();
                }
                if (dateString.equals("exit")) throw  new QuestionBreak();
                try {
                    return formatter.parse(dateString);
                } catch (ParseException e) {
                    console.println("The wrong format");
                }
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static Integer questionToOperatorWeight(Console console) throws QuestionBreak {
        try {
            while (true) {
                String line;
                if (fileInfomation == null) {
                    console.print("operator's weight: ");
                    line = console.readln().trim();
                } else {
                    line = getNextLine().trim();
                }
                if (line.equals("exit")) throw new QuestionBreak();
                String result = Person.checkWeightString(line);
                if (result.equals("yes")) return Integer.parseInt(line);
                else console.println(result);
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static String questionToOperatorPassport(Console console) throws QuestionBreak {
        try {
            while (true) {
                String passport;
                if (fileInfomation == null) {
                    console.print("operstor's passport id (if you don't know, enter 'nothing'): ");
                    passport = console.readln().trim();
                } else {
                    passport = getNextLine().trim();
                }
                if (passport.equals("exit")) throw new QuestionBreak();
                else if (passport.isEmpty()) console.println("Passport can't be empty");
                else if (passport.equalsIgnoreCase("nothing")) return null;
                else if (passport.length() < 7) console.println("Passport's length should be bigger than seven");
                else if (passport.length() > 27) console.println("Passport's length should be less than 28");
                else return passport;
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static Country questionToOperatorNationality(Console console) throws QuestionBreak {
        try {
            while (true) {
                String nationalityString;
                if (fileInfomation == null) {
                    console.print("operator's nationality (India|Vatican|Japan): ");
                    nationalityString = console.readln();
                } else {
                    nationalityString = getNextLine();
                }
                nationalityString = nationalityString.replace(" ", "").toLowerCase();
                if (nationalityString.equals("exit")) throw new QuestionBreak();
                try {
                    return Country.getFromString(nationalityString);
                } catch (IllegalStateException exception) {
                    console.println("You haven't chosen the right version");
                }
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }

    public static User questionToUser(Console console) throws QuestionBreak {
        try {
            while (true) {
                String name;
                if (fileInfomation == null) {
                    console.print("user's name: ");
                    name = console.readln().trim();
                } else {
                    name = getNextLine().trim();
                }
                if (name.equals("exit")) throw new QuestionBreak();
                if (name.isEmpty()) {
                    console.println("Name can't be empty");
                    continue;
                }
                String password;
                if (fileInfomation == null) {
                    console.print("password: ");
                    password = console.readln().trim();
                } else {
                    password = getNextLine().trim();
                }
                if (password.equals("exit")) throw new QuestionBreak();
                if (password.isEmpty() || password.length() > 27) {
                    console.println("Password's length should be between 1 and 27");
                    continue;
                }
                return new User(name, password);
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Reading error");
            return null;
        }
    }
}
