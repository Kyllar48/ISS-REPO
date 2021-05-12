package repository.hibernate;

import domain.Verifier;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.generic.VerifierRepo;

public class VerifierHbmRepo implements VerifierRepo {

    private static SessionFactory sessionFactory;

    public VerifierHbmRepo() {
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
    public Verifier Store(Verifier verifier) {
        return null;
    }

    @Override
    public Verifier Remove(Long id) {
        return null;
    }

    @Override
    public Verifier Update(Long id, Verifier newer) {
        return null;
    }

    @Override
    public Verifier Find(Long id) {
        return null;
    }

    @Override
    public Iterable<Verifier> FindAll() {
        return null;
    }

    @Override
    public Long Count() {
        return null;
    }

    @Override
    public Verifier FindByAccountID(Long accountID) {
        initialize();
        Verifier verifier = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                verifier = session.createQuery("from Verifier where accountID=:a",Verifier.class)
                        .setParameter("a",accountID).getSingleResult();
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.err.println("Err Hibernate" + re);
            }
        }
        close();
        return verifier;
    }
}
