package hibernateControllers;

import dsbook.Book;
import dsbook.Genre;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class bookHibernate
{
    private EntityManagerFactory entityManagerFactory = null;
    public bookHibernate(EntityManagerFactory entityManagerFactory)
    {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager()
    {
        return entityManagerFactory.createEntityManager();
    }

    public void createBook(Book book)
    {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(book);
            entityManager.getTransaction().commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (entityManager != null)
            {
                entityManager.close();
            }
        }
    }

    public void deleteBook(int id)
    {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Book book = null;
            try {
                book = entityManager.getReference(Book.class, id);
                book.getId();
            } catch (Exception e) {
                System.out.println("No such book by given Id");
            }
            entityManager.remove(book);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public Book getBookById(int id)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Book book = null;
        try {
            entityManager.getTransaction().begin();
            book = entityManager.getReference(Book.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return book;
    }

    public List<Book> getAllAvailableBooks(boolean isAvailable)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);
            if (isAvailable)
                query.select(root).where(criteriaBuilder.equal(root.get("available"), true));
            Query q = entityManager.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return new ArrayList<>();
    }

    public List<Book> getFilteredBooks(String title, String author, int priceFrom, int priceTo, int pagesFrom, int pagesTo)
    {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Book> query = cb.createQuery(Book.class);
            Root<Book> root = query.from(Book.class);
            query.select(root).where(cb.and(cb.like(root.get("bookTitle"), "%" + (title == null ? ("") : (title)) + "%")), cb.like(root.get("authors"), "%" + (author == null ? ("") : (author)) + "%"), cb.between(root.get("price"), priceFrom != -1 ? (priceFrom) : (0), priceTo != -1 ? (priceTo) : (1000)), cb.between(root.get("pageNum"), pagesFrom != -1 ? (pagesFrom) : (0), pagesTo != -1 ? (pagesTo) : (10000)));
            Query q = em.createQuery(query);
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

    public void editBook(Book book) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(book);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }
}
