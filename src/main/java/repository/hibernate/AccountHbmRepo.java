package repository.hibernate;

import domain.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.generic.AccountRepo;

public class AccountHbmRepo implements AccountRepo {

    private static SessionFactory sessionFactory;

    public AccountHbmRepo() {
    }

    private void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Error ocurred " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Override
    public Account Store(Account account) {
        return null;
    }

    @Override
    public Account Remove(Long id) {
        return null;
    }

    @Override
    public Account Update(Long id, Account newer) {
        return null;
    }

    @Override
    public Account Find(Long id) {
        return null;
    }

    @Override
    public Iterable<Account> FindAll() {
        return null;
    }

    @Override
    public Long Count() {
        initialize();
        Long no = 0L;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                no = session.createQuery("from Account", Account.class).stream().count();
                tx.commit();
            } catch (RuntimeException e) {
                if (tx != null)
                    tx.rollback();
                else System.err.println("Error DB Hibernate " + e);

            }
        }
        close();
        return no;
    }

    @Override
    public Account FindByCredentials(String username, String password) {
        initialize();
        Account account = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                account = session.createQuery("from Account where username=:username and password=:password", Account.class)
                        .setParameter("username", username).setParameter("password", password).getSingleResult();
                tx.commit();
            } catch (RuntimeException re) {
                if (tx != null)
                    tx.rollback();
                else System.err.println("Error Hibernate" + re);
            }
        }
        close();
        return account;
    }



}
