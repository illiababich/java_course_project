package hibernateControllers;

import dsbook.LegalPerson;
import dsbook.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class userHibernate
{
    private static EntityManagerFactory entityManagerFactory = null;
    public userHibernate(EntityManagerFactory entityManagerFactory)
    {
        userHibernate.entityManagerFactory = entityManagerFactory;
    }
    private static EntityManager getEntityManager()
    {
        return entityManagerFactory.createEntityManager();
    }
    public void createUser(User user)
    {
        EntityManager entityManager = null;
        try
        {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch(Exception exception)
        {
            exception.printStackTrace();
        } finally
        {
            if (entityManager != null)
            {
                entityManager.close();
            }
        }
    }

    public void editUser(User user)
    {
        EntityManager entityManager = null;
        try
        {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception exception)
        {
            exception.printStackTrace();
        } finally
        {
            if (entityManager != null)
            {
                entityManager.close();
            }
        }
    }

    public void removeUser(int id)
    {
        EntityManager entityManager = null;
        try
        {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            User user = null;
            try
            {
                user = entityManager.getReference(User.class, id);
                user.getId();
            } catch (Exception exception)
            {
                System.out.println("Deleting user failed due to invalid id!");
            }
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        } catch (Exception exception)
        {
            exception.printStackTrace();
        } finally
        {
            if (entityManager != null)
            {
                entityManager.close();
            }
        }
    }

    public static User getUserByCredentials(String login, String password)
    {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(criteriaBuilder.and((criteriaBuilder.like(root.get("login"), login)), (criteriaBuilder.like(root.get("password"), password))));
        Query query1;
        try
        {
            query1 = entityManager.createQuery(query);
            return (User) query1.getSingleResult();
        } catch (NoResultException exception)
        {
            return null;
        }
    }

    public List<User> getAllUsers()
    {
        return getAllUsers(true);
    }

    public List<User> getAllUsers(boolean all)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(User.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public List<User> getAllPerson()
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(User.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public List<LegalPerson> getAllCompany()
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(LegalPerson.class));
            Query q = em.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public User getUserById(int id)
    {
        EntityManager em = null;
        User user = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            user = em.find(User.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return user;
    }

    public User getUserByLoginData(String login, String psw)
    {
        EntityManager em = getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(cb.like(root.get("login"), login));
        query.select(root).where(cb.like(root.get("psw"), psw));
        Query q;
        try {
            q = em.createQuery(query);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
