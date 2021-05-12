package repository.hibernate;

import domain.Analyze;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.generic.AnalyzeRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AnalyzeHbmRepo implements AnalyzeRepo {
    private static SessionFactory sessionFactory;

    public AnalyzeHbmRepo() {
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
    public Analyze Store(Analyze analyze) {
        return null;
    }

    @Override
    public Analyze Remove(Long id) {
        return null;
    }

    @Override
    public Analyze Update(Long id, Analyze newer) {
        return null;
    }

    @Override
    public Analyze Find(Long id) {
        return null;
    }

    @Override
    public Iterable<Analyze> FindAll() {
        return null;
    }

    @Override
    public Long Count() {
        return null;
    }

    @Override
    public Collection<Long> GetBugIdsByCodeID(Long codeID) {
        initialize();
        Collection<Long> bugIds = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                bugIds = session.createQuery("from Analyze where codeID=:cID",Analyze.class)
                        .setParameter("cID", codeID).stream().map(a -> a.getBugID()).collect(Collectors.toList());
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.err.println("Error hib" + re);
            }
        }
        close();
        return bugIds;
    }

    @Override
    public List<Long> GetVerifierRegisteredBugIds(Long verifierID) {
        initialize();
        List<Long> bugIDs = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                bugIDs = session.createQuery("from Analyze where verifierID=:vID",Analyze.class)
                        .setParameter("vID",verifierID).stream().map(Analyze::getBugID).collect(Collectors.toList());
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.err.println("Error hib" + re);
            }
        }
        close();
        return bugIDs;
    }
}
