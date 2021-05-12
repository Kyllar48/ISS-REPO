package repository.hibernate;

import domain.Bug;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.generic.BugRepo;

public class BugHbmRepo implements BugRepo {

    private static SessionFactory sessionFactory;

    public BugHbmRepo() {
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
    public Bug Store(Bug bug) {
        return null;
    }

    @Override
    public Bug Remove(Long id) {
        return null;
    }

    @Override
    public Bug Update(Long id, Bug newer) {
        return null;
    }

    @Override
    public Bug Find(Long id) {
        initialize();
        Bug bug = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                bug = session.createQuery("from Bug where id=:id",Bug.class).setParameter("id",id).getSingleResult();
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.err.println("Error hib" + re);
            }
        }
        close();
        return bug;
    }

    @Override
    public Iterable<Bug> FindAll() {
        return null;
    }

    @Override
    public Long Count() {
        return null;
    }
}
