package niyo.repository;

import niyo.UserDAOWithJPA;

public class Service {

    UserDAOWithJPA database;

    public Service(UserDAOWithJPA database) {
        this.database = database;
    }

    public void handle(String url) {
        var database = new UserDAOWithJPA();

        database.getAll();
    }
}
