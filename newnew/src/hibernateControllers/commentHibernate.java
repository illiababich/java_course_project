package hibernateControllers;

import dsbook.Comment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class commentHibernate
{
    private EntityManagerFactory emf = null;

    public commentHibernate(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comment task)
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(task);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void edit(Comment task)
    {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(task);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

//    public void remove(int id)
//    {
//        EntityManager em = null;
//        try
//        {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Comment task = null;
//            try
//            {
//                task = em.getReference(Comment.class, id);
//                task.getId();
//            } catch (Exception e) {
//                System.out.println("No such user by given Id");
//            }
//            for (Comment t : task.getReply())
//            {
//                remove(t.getId());
//            }
//            task.getReply().clear();
//            em.merge(task);
//            em.remove(task);
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }

    public List<Comment> getAllTasks() {
        return getAllTasks(true, -1, -1);
    }

    public List<Comment> getAllTasks(boolean all, int resMax, int resFirst)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Comment.class));
            Query q = em.createQuery(query);

            if (!all)
            {
                q.setMaxResults(resMax);
                q.setFirstResult(resFirst);
            }
            return q.getResultList();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
        return null;
    }

    public Comment getTaskById(int id)
    {
        EntityManager em;
        Comment task = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            task = em.find(Comment.class, id);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            System.out.println("No such user by given Id");
        }
        return task;
    }
}
