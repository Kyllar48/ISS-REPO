package repository.hibernate;

import domain.Code;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.generic.CodeRepo;
import java.util.List;
import java.util.ArrayList;

public class CodeHbmRepo implements CodeRepo {
    private static SessionFactory sessionFactory;

    public CodeHbmRepo() {
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
    public Code Store(Code code) {
        if(Find(code.getId())!=null)
            return code;
        initialize();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(code);
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
    public Code Remove(Long id) {
        return null;
    }

    @Override
    public Code Update(Long id, Code newer) {
        return null;
    }

    @Override
    public Code Find(Long id) {
        initialize();
        Code found = null;
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                found = session.createQuery("from Code where id=:id",Code.class).setParameter("id",id).getSingleResult();
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.err.println("Error hib " + re);
            }
        }
        close();
        return found;
    }

    @Override
    public Iterable<Code> FindAll() {
        initialize();
        List<Code> codes = new ArrayList<>();

        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                codes = session.createQuery("from Code",Code.class).getResultList();
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.err.println("Error hib" + re);
            }
        }

        close();
        return codes;
    }

    @Override
    public Long Count() {
        return null;
    }

    @Override
    public Iterable<Code> GetProgrammersCodes(Long programmerID) {
        initialize();
        List<Code> codes = new ArrayList<>();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                codes = session.createQuery("from Code where programmerID=:pID",Code.class).setParameter("pID",programmerID).getResultList();
                tx.commit();
            }catch (RuntimeException re){
                if(tx!=null)
                    tx.rollback();
                else System.err.println("Error hib" + re);
            }
        }
        close();
        return codes;
    }
}
