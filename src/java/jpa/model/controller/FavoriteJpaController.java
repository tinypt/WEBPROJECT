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
import jpa.model.Favorite;
import jpa.model.Product;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author tinypt
 */
public class FavoriteJpaController implements Serializable {

    public FavoriteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Favorite favorite) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account accountId = favorite.getAccountId();
            if (accountId != null) {
                accountId = em.getReference(accountId.getClass(), accountId.getAccountId());
                favorite.setAccountId(accountId);
            }
            Product productId = favorite.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getProductId());
                favorite.setProductId(productId);
            }
            em.persist(favorite);
            if (accountId != null) {
                accountId.getFavoriteList().add(favorite);
                accountId = em.merge(accountId);
            }
            if (productId != null) {
                productId.getFavoriteList().add(favorite);
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

    public void edit(Favorite favorite) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Favorite persistentFavorite = em.find(Favorite.class, favorite.getFavoriteId());
            Account accountIdOld = persistentFavorite.getAccountId();
            Account accountIdNew = favorite.getAccountId();
            Product productIdOld = persistentFavorite.getProductId();
            Product productIdNew = favorite.getProductId();
            if (accountIdNew != null) {
                accountIdNew = em.getReference(accountIdNew.getClass(), accountIdNew.getAccountId());
                favorite.setAccountId(accountIdNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getProductId());
                favorite.setProductId(productIdNew);
            }
            favorite = em.merge(favorite);
            if (accountIdOld != null && !accountIdOld.equals(accountIdNew)) {
                accountIdOld.getFavoriteList().remove(favorite);
                accountIdOld = em.merge(accountIdOld);
            }
            if (accountIdNew != null && !accountIdNew.equals(accountIdOld)) {
                accountIdNew.getFavoriteList().add(favorite);
                accountIdNew = em.merge(accountIdNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getFavoriteList().remove(favorite);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getFavoriteList().add(favorite);
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
                Integer id = favorite.getFavoriteId();
                if (findFavorite(id) == null) {
                    throw new NonexistentEntityException("The favorite with id " + id + " no longer exists.");
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
            Favorite favorite;
            try {
                favorite = em.getReference(Favorite.class, id);
                favorite.getFavoriteId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The favorite with id " + id + " no longer exists.", enfe);
            }
            Account accountId = favorite.getAccountId();
            if (accountId != null) {
                accountId.getFavoriteList().remove(favorite);
                accountId = em.merge(accountId);
            }
            Product productId = favorite.getProductId();
            if (productId != null) {
                productId.getFavoriteList().remove(favorite);
                productId = em.merge(productId);
            }
            em.remove(favorite);
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

    public List<Favorite> findFavoriteEntities() {
        return findFavoriteEntities(true, -1, -1);
    }

    public List<Favorite> findFavoriteEntities(int maxResults, int firstResult) {
        return findFavoriteEntities(false, maxResults, firstResult);
    }

    private List<Favorite> findFavoriteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Favorite.class));
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

    public Favorite findFavorite(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Favorite.class, id);
        } finally {
            em.close();
        }
    }

    public int getFavoriteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Favorite> rt = cq.from(Favorite.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
