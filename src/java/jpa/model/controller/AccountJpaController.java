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
import jpa.model.Favourite;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.Account;
import jpa.model.Orders;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Hong
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
        if (account.getFavouriteList() == null) {
            account.setFavouriteList(new ArrayList<Favourite>());
        }
        if (account.getOrdersList() == null) {
            account.setOrdersList(new ArrayList<Orders>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Favourite> attachedFavouriteList = new ArrayList<Favourite>();
            for (Favourite favouriteListFavouriteToAttach : account.getFavouriteList()) {
                favouriteListFavouriteToAttach = em.getReference(favouriteListFavouriteToAttach.getClass(), favouriteListFavouriteToAttach.getFavouriteId());
                attachedFavouriteList.add(favouriteListFavouriteToAttach);
            }
            account.setFavouriteList(attachedFavouriteList);
            List<Orders> attachedOrdersList = new ArrayList<Orders>();
            for (Orders ordersListOrdersToAttach : account.getOrdersList()) {
                ordersListOrdersToAttach = em.getReference(ordersListOrdersToAttach.getClass(), ordersListOrdersToAttach.getOrderId());
                attachedOrdersList.add(ordersListOrdersToAttach);
            }
            account.setOrdersList(attachedOrdersList);
            em.persist(account);
            for (Favourite favouriteListFavourite : account.getFavouriteList()) {
                Account oldAccountIdOfFavouriteListFavourite = favouriteListFavourite.getAccountId();
                favouriteListFavourite.setAccountId(account);
                favouriteListFavourite = em.merge(favouriteListFavourite);
                if (oldAccountIdOfFavouriteListFavourite != null) {
                    oldAccountIdOfFavouriteListFavourite.getFavouriteList().remove(favouriteListFavourite);
                    oldAccountIdOfFavouriteListFavourite = em.merge(oldAccountIdOfFavouriteListFavourite);
                }
            }
            for (Orders ordersListOrders : account.getOrdersList()) {
                Account oldAccountIdOfOrdersListOrders = ordersListOrders.getAccountId();
                ordersListOrders.setAccountId(account);
                ordersListOrders = em.merge(ordersListOrders);
                if (oldAccountIdOfOrdersListOrders != null) {
                    oldAccountIdOfOrdersListOrders.getOrdersList().remove(ordersListOrders);
                    oldAccountIdOfOrdersListOrders = em.merge(oldAccountIdOfOrdersListOrders);
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
            List<Favourite> favouriteListOld = persistentAccount.getFavouriteList();
            List<Favourite> favouriteListNew = account.getFavouriteList();
            List<Orders> ordersListOld = persistentAccount.getOrdersList();
            List<Orders> ordersListNew = account.getOrdersList();
            List<String> illegalOrphanMessages = null;
            for (Favourite favouriteListOldFavourite : favouriteListOld) {
                if (!favouriteListNew.contains(favouriteListOldFavourite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Favourite " + favouriteListOldFavourite + " since its accountId field is not nullable.");
                }
            }
            for (Orders ordersListOldOrders : ordersListOld) {
                if (!ordersListNew.contains(ordersListOldOrders)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orders " + ordersListOldOrders + " since its accountId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Favourite> attachedFavouriteListNew = new ArrayList<Favourite>();
            for (Favourite favouriteListNewFavouriteToAttach : favouriteListNew) {
                favouriteListNewFavouriteToAttach = em.getReference(favouriteListNewFavouriteToAttach.getClass(), favouriteListNewFavouriteToAttach.getFavouriteId());
                attachedFavouriteListNew.add(favouriteListNewFavouriteToAttach);
            }
            favouriteListNew = attachedFavouriteListNew;
            account.setFavouriteList(favouriteListNew);
            List<Orders> attachedOrdersListNew = new ArrayList<Orders>();
            for (Orders ordersListNewOrdersToAttach : ordersListNew) {
                ordersListNewOrdersToAttach = em.getReference(ordersListNewOrdersToAttach.getClass(), ordersListNewOrdersToAttach.getOrderId());
                attachedOrdersListNew.add(ordersListNewOrdersToAttach);
            }
            ordersListNew = attachedOrdersListNew;
            account.setOrdersList(ordersListNew);
            account = em.merge(account);
            for (Favourite favouriteListNewFavourite : favouriteListNew) {
                if (!favouriteListOld.contains(favouriteListNewFavourite)) {
                    Account oldAccountIdOfFavouriteListNewFavourite = favouriteListNewFavourite.getAccountId();
                    favouriteListNewFavourite.setAccountId(account);
                    favouriteListNewFavourite = em.merge(favouriteListNewFavourite);
                    if (oldAccountIdOfFavouriteListNewFavourite != null && !oldAccountIdOfFavouriteListNewFavourite.equals(account)) {
                        oldAccountIdOfFavouriteListNewFavourite.getFavouriteList().remove(favouriteListNewFavourite);
                        oldAccountIdOfFavouriteListNewFavourite = em.merge(oldAccountIdOfFavouriteListNewFavourite);
                    }
                }
            }
            for (Orders ordersListNewOrders : ordersListNew) {
                if (!ordersListOld.contains(ordersListNewOrders)) {
                    Account oldAccountIdOfOrdersListNewOrders = ordersListNewOrders.getAccountId();
                    ordersListNewOrders.setAccountId(account);
                    ordersListNewOrders = em.merge(ordersListNewOrders);
                    if (oldAccountIdOfOrdersListNewOrders != null && !oldAccountIdOfOrdersListNewOrders.equals(account)) {
                        oldAccountIdOfOrdersListNewOrders.getOrdersList().remove(ordersListNewOrders);
                        oldAccountIdOfOrdersListNewOrders = em.merge(oldAccountIdOfOrdersListNewOrders);
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
            List<Favourite> favouriteListOrphanCheck = account.getFavouriteList();
            for (Favourite favouriteListOrphanCheckFavourite : favouriteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Favourite " + favouriteListOrphanCheckFavourite + " in its favouriteList field has a non-nullable accountId field.");
            }
            List<Orders> ordersListOrphanCheck = account.getOrdersList();
            for (Orders ordersListOrphanCheckOrders : ordersListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Orders " + ordersListOrphanCheckOrders + " in its ordersList field has a non-nullable accountId field.");
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
