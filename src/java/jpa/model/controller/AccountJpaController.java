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
import jpa.model.HistoryOrder;
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
 * @author tinypt
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
        if (account.getHistoryOrderList() == null) {
            account.setHistoryOrderList(new ArrayList<HistoryOrder>());
        }
        if (account.getFavoriteList() == null) {
            account.setFavoriteList(new ArrayList<Favorite>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<HistoryOrder> attachedHistoryOrderList = new ArrayList<HistoryOrder>();
            for (HistoryOrder historyOrderListHistoryOrderToAttach : account.getHistoryOrderList()) {
                historyOrderListHistoryOrderToAttach = em.getReference(historyOrderListHistoryOrderToAttach.getClass(), historyOrderListHistoryOrderToAttach.getHistoryId());
                attachedHistoryOrderList.add(historyOrderListHistoryOrderToAttach);
            }
            account.setHistoryOrderList(attachedHistoryOrderList);
            List<Favorite> attachedFavoriteList = new ArrayList<Favorite>();
            for (Favorite favoriteListFavoriteToAttach : account.getFavoriteList()) {
                favoriteListFavoriteToAttach = em.getReference(favoriteListFavoriteToAttach.getClass(), favoriteListFavoriteToAttach.getFavoriteId());
                attachedFavoriteList.add(favoriteListFavoriteToAttach);
            }
            account.setFavoriteList(attachedFavoriteList);
            em.persist(account);
            for (HistoryOrder historyOrderListHistoryOrder : account.getHistoryOrderList()) {
                Account oldAccountIdOfHistoryOrderListHistoryOrder = historyOrderListHistoryOrder.getAccountId();
                historyOrderListHistoryOrder.setAccountId(account);
                historyOrderListHistoryOrder = em.merge(historyOrderListHistoryOrder);
                if (oldAccountIdOfHistoryOrderListHistoryOrder != null) {
                    oldAccountIdOfHistoryOrderListHistoryOrder.getHistoryOrderList().remove(historyOrderListHistoryOrder);
                    oldAccountIdOfHistoryOrderListHistoryOrder = em.merge(oldAccountIdOfHistoryOrderListHistoryOrder);
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
            List<HistoryOrder> historyOrderListOld = persistentAccount.getHistoryOrderList();
            List<HistoryOrder> historyOrderListNew = account.getHistoryOrderList();
            List<Favorite> favoriteListOld = persistentAccount.getFavoriteList();
            List<Favorite> favoriteListNew = account.getFavoriteList();
            List<String> illegalOrphanMessages = null;
            for (HistoryOrder historyOrderListOldHistoryOrder : historyOrderListOld) {
                if (!historyOrderListNew.contains(historyOrderListOldHistoryOrder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HistoryOrder " + historyOrderListOldHistoryOrder + " since its accountId field is not nullable.");
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
            List<HistoryOrder> attachedHistoryOrderListNew = new ArrayList<HistoryOrder>();
            for (HistoryOrder historyOrderListNewHistoryOrderToAttach : historyOrderListNew) {
                historyOrderListNewHistoryOrderToAttach = em.getReference(historyOrderListNewHistoryOrderToAttach.getClass(), historyOrderListNewHistoryOrderToAttach.getHistoryId());
                attachedHistoryOrderListNew.add(historyOrderListNewHistoryOrderToAttach);
            }
            historyOrderListNew = attachedHistoryOrderListNew;
            account.setHistoryOrderList(historyOrderListNew);
            List<Favorite> attachedFavoriteListNew = new ArrayList<Favorite>();
            for (Favorite favoriteListNewFavoriteToAttach : favoriteListNew) {
                favoriteListNewFavoriteToAttach = em.getReference(favoriteListNewFavoriteToAttach.getClass(), favoriteListNewFavoriteToAttach.getFavoriteId());
                attachedFavoriteListNew.add(favoriteListNewFavoriteToAttach);
            }
            favoriteListNew = attachedFavoriteListNew;
            account.setFavoriteList(favoriteListNew);
            account = em.merge(account);
            for (HistoryOrder historyOrderListNewHistoryOrder : historyOrderListNew) {
                if (!historyOrderListOld.contains(historyOrderListNewHistoryOrder)) {
                    Account oldAccountIdOfHistoryOrderListNewHistoryOrder = historyOrderListNewHistoryOrder.getAccountId();
                    historyOrderListNewHistoryOrder.setAccountId(account);
                    historyOrderListNewHistoryOrder = em.merge(historyOrderListNewHistoryOrder);
                    if (oldAccountIdOfHistoryOrderListNewHistoryOrder != null && !oldAccountIdOfHistoryOrderListNewHistoryOrder.equals(account)) {
                        oldAccountIdOfHistoryOrderListNewHistoryOrder.getHistoryOrderList().remove(historyOrderListNewHistoryOrder);
                        oldAccountIdOfHistoryOrderListNewHistoryOrder = em.merge(oldAccountIdOfHistoryOrderListNewHistoryOrder);
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
            List<HistoryOrder> historyOrderListOrphanCheck = account.getHistoryOrderList();
            for (HistoryOrder historyOrderListOrphanCheckHistoryOrder : historyOrderListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the HistoryOrder " + historyOrderListOrphanCheckHistoryOrder + " in its historyOrderList field has a non-nullable accountId field.");
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
