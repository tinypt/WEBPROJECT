/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpa.model.OrderHistory;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Account;
import jpa.model.Favorite;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author GT62VR
 */
public class AccountJpaController implements Serializable {

    public AccountJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Account account) throws RollbackFailureException, Exception {
        if (account.getOrderHistoryList() == null) {
            account.setOrderHistoryList(new ArrayList<OrderHistory>());
        }
        if (account.getFavoriteList() == null) {
            account.setFavoriteList(new ArrayList<Favorite>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<OrderHistory> attachedOrderHistoryList = new ArrayList<OrderHistory>();
            for (OrderHistory orderHistoryListOrderHistoryToAttach : account.getOrderHistoryList()) {
                orderHistoryListOrderHistoryToAttach = em.getReference(orderHistoryListOrderHistoryToAttach.getClass(), orderHistoryListOrderHistoryToAttach.getHistoryId());
                attachedOrderHistoryList.add(orderHistoryListOrderHistoryToAttach);
            }
            account.setOrderHistoryList(attachedOrderHistoryList);
            List<Favorite> attachedFavoriteList = new ArrayList<Favorite>();
            for (Favorite favoriteListFavoriteToAttach : account.getFavoriteList()) {
                favoriteListFavoriteToAttach = em.getReference(favoriteListFavoriteToAttach.getClass(), favoriteListFavoriteToAttach.getFavoriteId());
                attachedFavoriteList.add(favoriteListFavoriteToAttach);
            }
            account.setFavoriteList(attachedFavoriteList);
            em.persist(account);
            for (OrderHistory orderHistoryListOrderHistory : account.getOrderHistoryList()) {
                Account oldAccountIdOfOrderHistoryListOrderHistory = orderHistoryListOrderHistory.getAccountId();
                orderHistoryListOrderHistory.setAccountId(account);
                orderHistoryListOrderHistory = em.merge(orderHistoryListOrderHistory);
                if (oldAccountIdOfOrderHistoryListOrderHistory != null) {
                    oldAccountIdOfOrderHistoryListOrderHistory.getOrderHistoryList().remove(orderHistoryListOrderHistory);
                    oldAccountIdOfOrderHistoryListOrderHistory = em.merge(oldAccountIdOfOrderHistoryListOrderHistory);
                }
            }
            for (Favorite favoriteListFavorite : account.getFavoriteList()) {
                Account oldAccountIdOfFavoriteListFavorite = favoriteListFavorite.getAccountId();
                favoriteListFavorite.setAccountId(account);
                favoriteListFavorite = em.merge(favoriteListFavorite);
                if (oldAccountIdOfFavoriteListFavorite != null) {
                    oldAccountIdOfFavoriteListFavorite.getFavoriteList().remove(favoriteListFavorite);
                    oldAccountIdOfFavoriteListFavorite = em.merge(oldAccountIdOfFavoriteListFavorite);
                }
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

    public void edit(Account account) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account persistentAccount = em.find(Account.class, account.getAccountId());
            List<OrderHistory> orderHistoryListOld = persistentAccount.getOrderHistoryList();
            List<OrderHistory> orderHistoryListNew = account.getOrderHistoryList();
            List<Favorite> favoriteListOld = persistentAccount.getFavoriteList();
            List<Favorite> favoriteListNew = account.getFavoriteList();
            List<String> illegalOrphanMessages = null;
            for (OrderHistory orderHistoryListOldOrderHistory : orderHistoryListOld) {
                if (!orderHistoryListNew.contains(orderHistoryListOldOrderHistory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderHistory " + orderHistoryListOldOrderHistory + " since its accountId field is not nullable.");
                }
            }
            for (Favorite favoriteListOldFavorite : favoriteListOld) {
                if (!favoriteListNew.contains(favoriteListOldFavorite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Favorite " + favoriteListOldFavorite + " since its accountId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<OrderHistory> attachedOrderHistoryListNew = new ArrayList<OrderHistory>();
            for (OrderHistory orderHistoryListNewOrderHistoryToAttach : orderHistoryListNew) {
                orderHistoryListNewOrderHistoryToAttach = em.getReference(orderHistoryListNewOrderHistoryToAttach.getClass(), orderHistoryListNewOrderHistoryToAttach.getHistoryId());
                attachedOrderHistoryListNew.add(orderHistoryListNewOrderHistoryToAttach);
            }
            orderHistoryListNew = attachedOrderHistoryListNew;
            account.setOrderHistoryList(orderHistoryListNew);
            List<Favorite> attachedFavoriteListNew = new ArrayList<Favorite>();
            for (Favorite favoriteListNewFavoriteToAttach : favoriteListNew) {
                favoriteListNewFavoriteToAttach = em.getReference(favoriteListNewFavoriteToAttach.getClass(), favoriteListNewFavoriteToAttach.getFavoriteId());
                attachedFavoriteListNew.add(favoriteListNewFavoriteToAttach);
            }
            favoriteListNew = attachedFavoriteListNew;
            account.setFavoriteList(favoriteListNew);
            account = em.merge(account);
            for (OrderHistory orderHistoryListNewOrderHistory : orderHistoryListNew) {
                if (!orderHistoryListOld.contains(orderHistoryListNewOrderHistory)) {
                    Account oldAccountIdOfOrderHistoryListNewOrderHistory = orderHistoryListNewOrderHistory.getAccountId();
                    orderHistoryListNewOrderHistory.setAccountId(account);
                    orderHistoryListNewOrderHistory = em.merge(orderHistoryListNewOrderHistory);
                    if (oldAccountIdOfOrderHistoryListNewOrderHistory != null && !oldAccountIdOfOrderHistoryListNewOrderHistory.equals(account)) {
                        oldAccountIdOfOrderHistoryListNewOrderHistory.getOrderHistoryList().remove(orderHistoryListNewOrderHistory);
                        oldAccountIdOfOrderHistoryListNewOrderHistory = em.merge(oldAccountIdOfOrderHistoryListNewOrderHistory);
                    }
                }
            }
            for (Favorite favoriteListNewFavorite : favoriteListNew) {
                if (!favoriteListOld.contains(favoriteListNewFavorite)) {
                    Account oldAccountIdOfFavoriteListNewFavorite = favoriteListNewFavorite.getAccountId();
                    favoriteListNewFavorite.setAccountId(account);
                    favoriteListNewFavorite = em.merge(favoriteListNewFavorite);
                    if (oldAccountIdOfFavoriteListNewFavorite != null && !oldAccountIdOfFavoriteListNewFavorite.equals(account)) {
                        oldAccountIdOfFavoriteListNewFavorite.getFavoriteList().remove(favoriteListNewFavorite);
                        oldAccountIdOfFavoriteListNewFavorite = em.merge(oldAccountIdOfFavoriteListNewFavorite);
                    }
                }
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
                Integer id = account.getAccountId();
                if (findAccount(id) == null) {
                    throw new NonexistentEntityException("The account with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account account;
            try {
                account = em.getReference(Account.class, id);
                account.getAccountId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The account with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<OrderHistory> orderHistoryListOrphanCheck = account.getOrderHistoryList();
            for (OrderHistory orderHistoryListOrphanCheckOrderHistory : orderHistoryListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the OrderHistory " + orderHistoryListOrphanCheckOrderHistory + " in its orderHistoryList field has a non-nullable accountId field.");
            }
            List<Favorite> favoriteListOrphanCheck = account.getFavoriteList();
            for (Favorite favoriteListOrphanCheckFavorite : favoriteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Favorite " + favoriteListOrphanCheckFavorite + " in its favoriteList field has a non-nullable accountId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(account);
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

    public List<Account> findAccountEntities() {
        return findAccountEntities(true, -1, -1);
    }

    public List<Account> findAccountEntities(int maxResults, int firstResult) {
        return findAccountEntities(false, maxResults, firstResult);
    }

    private List<Account> findAccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Account.class));
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

    public Account findAccount(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Account.class, id);
        } finally {
            em.close();
        }
    }

    public Account findAccountbyUserName(String username) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Account.findByUsername");
            query.setParameter("username", username);
            return (Account) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    public int getAccountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Account> rt = cq.from(Account.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
