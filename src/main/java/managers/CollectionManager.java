package main.java.managers;

import java.time.ZonedDateTime;
import java.util.*;

import main.java.models.Movie;
import main.java.models.MpaaRating;

public class CollectionManager {
    private TreeSet<Movie> brotherhood = new TreeSet<>();
    private Long lastId;
    private Map<Long, Movie> movieMap = new HashMap<>();
    private ZonedDateTime timeInitialisation;
    private ZonedDateTime timeSave;
    private final DumpManager dumpManager;


    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }
    public Long getLastId() {
        return lastId;
    }
    public ZonedDateTime getTimeInitialisation() {
        return timeInitialisation;
    }
    public ZonedDateTime getTimeSave() {
        return timeSave;
    }
    public TreeSet<Movie> getBrotherhood() {
        return brotherhood;
    }


    public CollectionManager(DumpManager dumpManager) {
        super();
        this.timeInitialisation = null;
        this.timeSave = null;
        this.dumpManager = dumpManager;
        this.lastId = (long) -1;
    }


    public Movie getById(Long id) {
        return movieMap.get(id);
    }
    public boolean isContain(Movie element) {
        return element == null || getById(element.getId()) != null;
    }
    public Long getFreeId() {
        while (getById(++lastId) != null) {}
        return lastId;
    }
    public void makeDecId() {
        lastId -= 1;
    }


    public boolean addElement(Movie movie) {
        if (isContain(movie)) return false;
        movieMap.put(movie.getId(), movie);
        brotherhood.add(movie);
        update();
        return true;
    }

    public boolean update(Movie movie) {
        if (!isContain(movie)) return false;
        brotherhood.remove(getById(movie.getId()));
        movieMap.put(movie.getId(), movie);
        brotherhood.add(movie);
        update();
        return true;
    }

    public boolean removeById(Long id) {
        var movie = getById(id);
        if (movie == null) return false;
        movieMap.remove(movie.getId());
        brotherhood.remove(movie);
        update();
        return true;
    }

    public void update() {
    }

    public boolean init() {
        brotherhood.clear();
        movieMap.clear();
        dumpManager.readCollection(brotherhood);
        timeInitialisation = ZonedDateTime.now();
        for (Movie element: brotherhood)
            if (getById(element.getId()) != null) {
                brotherhood.clear();
                movieMap.clear();
                return false;
            } else {
                if (element.getId()>lastId) lastId = element.getId();
                movieMap.put(element.getId(), element);
            }
        update();
        return true;
    }

    public boolean saveCollection() {
        if (!dumpManager.writeCollectionToCSV(brotherhood)) return false;
        timeSave = ZonedDateTime.now();
        return true;
    }

    public String getInfo() {
        String answer = "";
        answer += "type: TreeSet<Movie>\n";
        answer += "time initialisation: " + getTimeInitialisation().toString() + '\n';
        answer += "time saving: " + ((getTimeSave() == null)?"null":getTimeSave().toString()) + '\n';
        answer += "size: " + brotherhood.size() + "\n";
        return answer;
    }

    @Override
    public String toString() {
        if (brotherhood.isEmpty()) return "Collection is empty";
        List<String> result = new ArrayList<>(){
            {
                for (Movie element: brotherhood) {
                    add(element.toString());
                }
            }
        };
        return String.join("\n-------------\n", result);
    }

    public void clear() {
        brotherhood.clear();
        lastId = null;
        movieMap.clear();
    }

    public Collection<Movie> getElementsGreater(Movie movie) throws CloneNotSupportedException {
        Movie start = brotherhood.ceiling(movie);
        if (start == null) return null;
        Collection<Movie> cloneCollection = new TreeSet<>();
        for (Movie element: brotherhood.tailSet(start)) {
            cloneCollection.add((Movie) element.clone());
        }
        return cloneCollection;
    }

    public int countElementsBiggerMpaaRating(MpaaRating mpaaRating) throws CloneNotSupportedException {
        Collection<Movie> collection = getElementsGreater(new Movie() {
            {
                setMpaaRating(MpaaRating.getMpaaRatingByInt(MpaaRating.getMpaaRatingInt(mpaaRating) + 1));
            }
        });
        if (collection == null) return 0;
        return collection.size();
    }

    public String filterStartsWithName(String name) {
        Collection<Movie> collection = getElementsStartWithName(name);
        if (collection == null || collection.isEmpty()) return "There is no element starts with name";
        return getElementsString(collection);
    }

    public TreeSet<Movie> getElementsStartWithName(String name) {
        TreeSet<Movie> result = new TreeSet<>();
        for (Movie element: brotherhood) {
            if (checkStartWithName(element.getName(), name)) {
                try {
                    result.add((Movie) element.clone());
                } catch (CloneNotSupportedException ignored) {}
            }
        }

        return result;
    }

    private static boolean checkStartWithName(String movie, String name) {
        if (name == null) return false;
        if (name.length() > movie.length()) return false;
        if (movie.substring(0, name.length()).equals(name)) return true;
        return false;
    }

    public String getElementsString(Collection<Movie> collection) {
        if (collection.isEmpty()) return "Collection is empty";
        List<String> result = new ArrayList<>(){
            {
                for (Movie element: collection) {
                    add(element.toString());
                }
            }
        };
        return String.join("\n-------------\n", result);
    }

    public String getAllMpaarating() {
        if (brotherhood.isEmpty()) return "Collection is empty";
        List<String> result = new ArrayList<>(){
            {
                for (Movie element: brotherhood) {
                    add(element.getName() + ": " + element.getMpaaRating().toString());
                }
            }
        };
        return String.join("\n", result);
    }

    public boolean checkElementLessMinimum(Movie movie) {
        return movie.compareTo(brotherhood.first()) < 0;
    }

    public boolean removeGreater(Movie movie) throws CloneNotSupportedException {
        boolean result = false;
        for (Movie element: getElementsGreater(movie)) {
            if (removeById(element.getId())) result = true;
        }
        return result;
    }

    public boolean containPassportId(String passportId) {
        if (passportId == null) return false;
        for (Movie element: brotherhood) {
            String oldPassportId = element.getOperator().getPassportID();
            if (oldPassportId != null) {
                if (oldPassportId.equals(passportId)) return true;
            }
        }
        return false;
    }
}
