package main.java.managers;

import main.java.models.Movie;
import main.java.models.MpaaRating;
import main.java.models.User;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class CollectionManager {
    private final ConcurrentSkipListSet<Movie> brotherhood = new ConcurrentSkipListSet<>();
    private ConcurrentHashMap<Long, Movie> movieMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, User> movieUser = new ConcurrentHashMap<>();
    private ZonedDateTime timeInitialisation = null;
    private ZonedDateTime timeSave = null;


    public ZonedDateTime getTimeInitialisation() {
        return timeInitialisation;
    }
    public ZonedDateTime getTimeSave() {
        return timeSave;
    }
    public ConcurrentSkipListSet<Movie> getBrotherhood() {
        return brotherhood;
    }


    private CollectionManager() {
    }

    private static class CollectionManagerHolder {
        private static final CollectionManager HOLDER_INSTANCE = new CollectionManager();
    }

    public static CollectionManager getInstance() {
        return CollectionManagerHolder.HOLDER_INSTANCE;
    }


    public Movie getById(Long id) {
        return movieMap.get(id);
    }
    public boolean isContain(Movie element) {
        return element == null || getById(element.getId()) != null;
    }


    public boolean addElement(Movie movie) {
        if (isContain(movie)) return false;
        movieMap.put(movie.getId(), movie);
        brotherhood.add(movie);
        update();
        return true;
    }

    public void addUser(User user, long id) {
        movieUser.put(id, user);
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
        timeInitialisation = ZonedDateTime.now();
        Collection<Movie> tmp = DumpManager.getInstance().getCollection();
        if (tmp == null) return false;
        brotherhood.addAll(tmp);
        movieMap = (ConcurrentHashMap<Long, Movie>) brotherhood.stream().collect(Collectors.toConcurrentMap(Movie::getId, T -> T));
        update();
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
        List<String> result = brotherhood.stream().map(Movie::toString).collect(Collectors.toList());
        return String.join("\n-------------\n", result);
    }

    public void clear() {
        brotherhood.clear();
        movieMap.clear();
    }

    public Collection<Movie> getElementsGreater(Movie movie) {
        Movie start = brotherhood.ceiling(movie);
        if (start == null) return null;
        return new HashSet<>(brotherhood.tailSet(start));
    }

    public int countElementsBiggerMpaaRating(MpaaRating mpaaRating) {
        if (mpaaRating.equals(MpaaRating.NC_17)) return 0;
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

    public Collection<Movie> getElementsStartWithName(String name) {
        return brotherhood.stream().filter(i -> checkStartWithName(i.getName(), name)).collect(Collectors.toSet());
    }

    private static boolean checkStartWithName(String movie, String name) {
        if (name == null) return false;
        if (name.length() > movie.length()) return false;
        return movie.startsWith(name);
    }

    public String getElementsString(Collection<Movie> collection) {
        if (collection.isEmpty()) return "Collection is empty";
        List<String> result = brotherhood.stream().map(Movie::toString).collect(Collectors.toList());
        return String.join("\n-------------\n", result);
    }

    public String getAllMpaarating() {
        if (brotherhood.isEmpty()) return "Collection is empty";
        List<String> result = brotherhood.stream().map(i -> i.getName() + ": " + i.getMpaaRating().toString()).collect(Collectors.toList());
        return String.join("\n", result);
    }

    public boolean checkElementLessMinimum(Movie movie) {
        return movie.compareTo(brotherhood.first()) < 0;
    }

    public boolean removeGreater(Movie movie) {
        return getElementsGreater(movie).stream().anyMatch(i -> removeById(i.getId()));
    }

    public boolean containPassportId(String passportId) {
        if (passportId == null) return false;
        return brotherhood.stream().map(i -> i.getOperator().getPassportID()).filter(Objects::nonNull).anyMatch(i -> i.equals(passportId));
    }
}
