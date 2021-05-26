package repository.hibernate;

import domain.Bug;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.generic.BugRepo;

import java.util.Comparator;

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
        if(Find(bug.getId())!=null)
            return bug;
        initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(bug);
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.err.println("Error hib " + re);
            }
        }
        close();
        return null;
    }

    @Override
    public Bug Remove(Long id) {
        Bug toDelete = Find(id);
        if(toDelete == null)
            return null;
        initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.delete(toDelete);
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.err.println("Error hib " + re);
            }
        }
        close();
        return toDelete;
    }

    @Override
    public Bug Update(Long id, Bug newer) {
        if(Find(id)==null)
            return newer;
        initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                newer.setId(id);
                session.update(newer);
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.err.println("Error hib " + re);
            }
        }
        close();
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
                else System.err.println("Error hib " + re);
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

    @Override
    public Long GetMaxID() {
        initialize();
        Long no = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                no = session.createQuery("from Bug",Bug.class).stream().max(new Comparator<Bug>() {
                    @Override
                    public int compare(Bug o1, Bug o2) {
                        return o1.getId().compareTo(o2.getId());
                    }
                }).get().getId();
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.err.println("Error hib " + re);
            }
        }
        close();
        return no;
    }
}
