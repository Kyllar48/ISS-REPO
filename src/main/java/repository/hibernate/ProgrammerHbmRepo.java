package repository.hibernate;

import domain.Programmer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.generic.ProgrammerRepo;

public class ProgrammerHbmRepo implements ProgrammerRepo {

    private static SessionFactory sessionFactory;

    public ProgrammerHbmRepo() {
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
    public Programmer Store(Programmer programmer) {
        return null;
    }

    @Override
    public Programmer Remove(Long id) {
        return null;
    }

    @Override
    public Programmer Update(Long id, Programmer newer) {
        return null;
    }

    @Override
    public Programmer Find(Long id) {
        return null;
    }

    @Override
    public Iterable<Programmer> FindAll() {
        return null;
    }

    @Override
    public Long Count() {
        return null;
    }

    @Override
    public Programmer FindByAccountID(Long accountID) {
        initialize();
        Programmer prog = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                prog = session.createQuery("from Programmer where accountID=:a",Programmer.class).setParameter("a",accountID).getSingleResult();
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.err.println("Error hib" + re);
            }
        }
        close();
        return prog;
    }
}
