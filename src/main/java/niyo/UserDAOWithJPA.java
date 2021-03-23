package niyo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserDAOWithJPA implements UserDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Labb");


    @Override
    public void create(User u) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
    }

    @Override
    public User addUser(String id, String username, String password, String firstName, String lastName, String email, String phone) {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        //User u = em.persist(new User (id, username, password, firstName, lastName, email, phone));
        User u = new User(id, username, password, firstName, lastName, email, phone);
        em.persist(u);
        em.getTransaction().commit();
        return u;
    }


    @Override
    public List<User> getByFirstName(String firstName) {

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<User> userList = em.createQuery("from User u WHERE u.firstName LIKE :firstName", User.class)
                .setParameter("firstName", firstName).getResultList();

        em.getTransaction().commit();
        return userList;
    }

    @Override
    public List<User> getByLastname(String lastName) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<User> userList = em.createQuery("from User u WHERE u.lastName LIKE :lastName", User.class)
                .setParameter("lastName", lastName).getResultList();

        em.getTransaction().commit();
        return userList;
    }

    @Override
    public List<User> getByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<User> userList = em.createQuery("from User u WHERE u.username = :username", User.class)
                .setParameter("username", username).getResultList();

        em.getTransaction().commit();
        return userList;
    }

    @Override
    public List<User> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<User> userList = em.createQuery("from User u", User.class).getResultList();
        em.getTransaction().commit();
        return userList;
    }

    @Override
    public boolean updatePassword(String id, String newPassword) {
        boolean passwordUpdated = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.find(User.class, id);
        if (u != null ) {
            u.setPassword(newPassword);
            passwordUpdated = true;
        }
        em.getTransaction().commit();
        return passwordUpdated;
    }

    @Override
    public boolean updatePhone(String id, String newPhone) {
        boolean numberUpdate = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User u = em.find(User.class, id);
        if (u != null) {
            u.setPhone(newPhone);
            numberUpdate = true;
        }
        em.getTransaction().commit();
        return numberUpdate;
    }

    @Override
    public boolean removeUser(String id) {
        boolean removed = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        User u = em.find(User.class, id);
        if (u != null) {
            em.remove(u);
            removed = true;
        }
        em.getTransaction().commit();
        return removed;
    }
}
