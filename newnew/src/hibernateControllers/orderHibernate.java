package hibernateControllers;

import dsbook.Book;
import dsbook.Order_table;
import dsbook.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class orderHibernate
{
    private static EntityManagerFactory entityManagerFactory = null;
    public orderHibernate(EntityManagerFactory entityManagerFactory)
    {
        this.entityManagerFactory = entityManagerFactory;
    }

    private static EntityManager getEntityManager()
    {
        return entityManagerFactory.createEntityManager();
    }

    public void createOrder(Order_table order)
    {
        EntityManager entityManager = null;
        try
        {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(order);
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

    public void removeOrderById(int id)
    {
        EntityManager entityManager = null;
        try
        {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Order_table order = null;
            try
            {
                order = entityManager.getReference(Order_table.class, id);
                order.getId();
            } catch (Exception exception)
            {
                System.out.println("Deleting order failed due to invalid id!");
            }
            entityManager.remove(order);
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

    public void editOrder(Order_table order)
    {
        EntityManager em = entityManagerFactory.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.merge(order);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    public List<Order_table> getAllOrders()
    {
        return getAllOrders(true);
    }

    public List<Order_table> getAllOrders(boolean all)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(Order_table.class));
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

    public Order_table getOrderById(int id)
    {
        EntityManager em = null;
        Order_table order = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            order = em.find(Order_table.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such order by given Id");
        }
        return order;
    }
}
