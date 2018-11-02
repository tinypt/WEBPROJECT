/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import jpa.model.Account;
import jpa.model.Favourite;
import jpa.model.Product;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Hong
 */
public class FavouriteJpaController implements Serializable {

    public FavouriteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Favourite favourite) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account accountId = favourite.getAccountId();
            if (accountId != null) {
                accountId = em.getReference(accountId.getClass(), accountId.getAccountId());
                favourite.setAccountId(accountId);
            }
            Product productId = favourite.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getProductId());
                favourite.setProductId(productId);
            }
            em.persist(favourite);
            if (accountId != null) {
                accountId.getFavouriteList().add(favourite);
                accountId = em.merge(accountId);
            }
            if (productId != null) {
                productId.getFavouriteList().add(favourite);
                productId = em.merge(productId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Favourite favourite) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Favourite persistentFavourite = em.find(Favourite.class, favourite.getFavouriteId());
            Account accountIdOld = persistentFavourite.getAccountId();
            Account accountIdNew = favourite.getAccountId();
            Product productIdOld = persistentFavourite.getProductId();
            Product productIdNew = favourite.getProductId();
            if (accountIdNew != null) {
                accountIdNew = em.getReference(accountIdNew.getClass(), accountIdNew.getAccountId());
                favourite.setAccountId(accountIdNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getProductId());
                favourite.setProductId(productIdNew);
            }
            favourite = em.merge(favourite);
            if (accountIdOld != null && !accountIdOld.equals(accountIdNew)) {
                accountIdOld.getFavouriteList().remove(favourite);
                accountIdOld = em.merge(accountIdOld);
            }
            if (accountIdNew != null && !accountIdNew.equals(accountIdOld)) {
                accountIdNew.getFavouriteList().add(favourite);
                accountIdNew = em.merge(accountIdNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getFavouriteList().remove(favourite);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getFavouriteList().add(favourite);
                productIdNew = em.merge(productIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = favourite.getFavouriteId();
                if (findFavourite(id) == null) {
                    throw new NonexistentEntityException("The favourite with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Favourite favourite;
            try {
                favourite = em.getReference(Favourite.class, id);
                favourite.getFavouriteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The favourite with id " + id + " no longer exists.", enfe);
            }
            Account accountId = favourite.getAccountId();
            if (accountId != null) {
                accountId.getFavouriteList().remove(favourite);
                accountId = em.merge(accountId);
            }
            Product productId = favourite.getProductId();
            if (productId != null) {
                productId.getFavouriteList().remove(favourite);
                productId = em.merge(productId);
            }
            em.remove(favourite);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Favourite> findFavouriteEntities() {
        return findFavouriteEntities(true, -1, -1);
    }

    public List<Favourite> findFavouriteEntities(int maxResults, int firstResult) {
        return findFavouriteEntities(false, maxResults, firstResult);
    }

    private List<Favourite> findFavouriteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Favourite.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Favourite findFavourite(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Favourite.class, id);
        } finally {
            em.close();
        }
    }

    public int getFavouriteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Favourite> rt = cq.from(Favourite.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
