import java.time.ZonedDateTime;
import java.util.TreeSet;
public class CollectionMovie {
    private TreeSet<Movie> brotherhood;
    private int lastId;
    private java.time.ZonedDateTime timeInitialisation;

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    public int getLastId() {
        return lastId;
    }

    public ZonedDateTime getTimeInitialisation() {
        return timeInitialisation;
    }

    public void setTimeInitialisation(ZonedDateTime timeInitialisation) {
        this.timeInitialisation = timeInitialisation;
    }

    public CollectionMovie() {
        super();
        timeInitialisation = java.time.ZonedDateTime.now();
        brotherhood = new TreeSet<Movie>();
    }


    public void addElement(String name, Coordinates coordinates, int oscarsCount, Long goldenPalmCount,
                           MovieGenre genre, MpaaRating mpaaRating, Person operator) {
        setLastId(getLastId()+ 1);
        Movie newMovie = new Movie(getLastId(), name, coordinates, java.time.ZonedDateTime.now(), oscarsCount,
                goldenPalmCount, genre, mpaaRating, operator);
        brotherhood.add(newMovie);
    }

    public String getInfo() {
        String answer = "";
        answer += "type: TreeSet<Movie>\n";
        answer += "time initialisation: " + getTimeInitialisation().toString() + '\n';
        answer += "size: " + brotherhood.size() + "\n";
        return answer;
    }
}
