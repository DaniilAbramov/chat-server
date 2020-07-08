package services;

public interface Observable {
    void addObserver(Observer o);
    void deletedObserver(Observer o);
    void notifyObservers(String message);
}
