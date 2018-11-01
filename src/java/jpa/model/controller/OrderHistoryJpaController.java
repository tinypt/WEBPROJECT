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
import jpa.model.Account;
import jpa.model.OrderDetail;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpa.model.OrderHistory;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author GT62VR
 */
public class OrderHistoryJpaController implements Serializable {

    public OrderHistoryJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrderHistory orderHistory) throws RollbackFailureException, Exception {
        if (orderHistory.getOrderDetailList() == null) {
            orderHistory.setOrderDetailList(new ArrayList<OrderDetail>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account accountId = orderHistory.getAccountId();
            if (accountId != null) {
                accountId = em.getReference(accountId.getClass(), accountId.getAccountId());
                orderHistory.setAccountId(accountId);
            }
            List<OrderDetail> attachedOrderDetailList = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailListOrderDetailToAttach : orderHistory.getOrderDetailList()) {
                orderDetailListOrderDetailToAttach = em.getReference(orderDetailListOrderDetailToAttach.getClass(), orderDetailListOrderDetailToAttach.getDetailId());
                attachedOrderDetailList.add(orderDetailListOrderDetailToAttach);
            }
            orderHistory.setOrderDetailList(attachedOrderDetailList);
            em.persist(orderHistory);
            if (accountId != null) {
                accountId.getOrderHistoryList().add(orderHistory);
                accountId = em.merge(accountId);
            }
            for (OrderDetail orderDetailListOrderDetail : orderHistory.getOrderDetailList()) {
                OrderHistory oldHistoryIdOfOrderDetailListOrderDetail = orderDetailListOrderDetail.getHistoryId();
                orderDetailListOrderDetail.setHistoryId(orderHistory);
                orderDetailListOrderDetail = em.merge(orderDetailListOrderDetail);
                if (oldHistoryIdOfOrderDetailListOrderDetail != null) {
                    oldHistoryIdOfOrderDetailListOrderDetail.getOrderDetailList().remove(orderDetailListOrderDetail);
                    oldHistoryIdOfOrderDetailListOrderDetail = em.merge(oldHistoryIdOfOrderDetailListOrderDetail);
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

    public void edit(OrderHistory orderHistory) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            OrderHistory persistentOrderHistory = em.find(OrderHistory.class, orderHistory.getHistoryId());
            Account accountIdOld = persistentOrderHistory.getAccountId();
            Account accountIdNew = orderHistory.getAccountId();
            List<OrderDetail> orderDetailListOld = persistentOrderHistory.getOrderDetailList();
            List<OrderDetail> orderDetailListNew = orderHistory.getOrderDetailList();
            List<String> illegalOrphanMessages = null;
            for (OrderDetail orderDetailListOldOrderDetail : orderDetailListOld) {
                if (!orderDetailListNew.contains(orderDetailListOldOrderDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderDetail " + orderDetailListOldOrderDetail + " since its historyId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (accountIdNew != null) {
                accountIdNew = em.getReference(accountIdNew.getClass(), accountIdNew.getAccountId());
                orderHistory.setAccountId(accountIdNew);
            }
            List<OrderDetail> attachedOrderDetailListNew = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailListNewOrderDetailToAttach : orderDetailListNew) {
                orderDetailListNewOrderDetailToAttach = em.getReference(orderDetailListNewOrderDetailToAttach.getClass(), orderDetailListNewOrderDetailToAttach.getDetailId());
                attachedOrderDetailListNew.add(orderDetailListNewOrderDetailToAttach);
            }
            orderDetailListNew = attachedOrderDetailListNew;
            orderHistory.setOrderDetailList(orderDetailListNew);
            orderHistory = em.merge(orderHistory);
            if (accountIdOld != null && !accountIdOld.equals(accountIdNew)) {
                accountIdOld.getOrderHistoryList().remove(orderHistory);
                accountIdOld = em.merge(accountIdOld);
            }
            if (accountIdNew != null && !accountIdNew.equals(accountIdOld)) {
                accountIdNew.getOrderHistoryList().add(orderHistory);
                accountIdNew = em.merge(accountIdNew);
            }
            for (OrderDetail orderDetailListNewOrderDetail : orderDetailListNew) {
                if (!orderDetailListOld.contains(orderDetailListNewOrderDetail)) {
                    OrderHistory oldHistoryIdOfOrderDetailListNewOrderDetail = orderDetailListNewOrderDetail.getHistoryId();
                    orderDetailListNewOrderDetail.setHistoryId(orderHistory);
                    orderDetailListNewOrderDetail = em.merge(orderDetailListNewOrderDetail);
                    if (oldHistoryIdOfOrderDetailListNewOrderDetail != null && !oldHistoryIdOfOrderDetailListNewOrderDetail.equals(orderHistory)) {
                        oldHistoryIdOfOrderDetailListNewOrderDetail.getOrderDetailList().remove(orderDetailListNewOrderDetail);
                        oldHistoryIdOfOrderDetailListNewOrderDetail = em.merge(oldHistoryIdOfOrderDetailListNewOrderDetail);
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
                Integer id = orderHistory.getHistoryId();
                if (findOrderHistory(id) == null) {
                    throw new NonexistentEntityException("The orderHistory with id " + id + " no longer exists.");
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
            OrderHistory orderHistory;
            try {
                orderHistory = em.getReference(OrderHistory.class, id);
                orderHistory.getHistoryId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderHistory with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<OrderDetail> orderDetailListOrphanCheck = orderHistory.getOrderDetailList();
            for (OrderDetail orderDetailListOrphanCheckOrderDetail : orderDetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This OrderHistory (" + orderHistory + ") cannot be destroyed since the OrderDetail " + orderDetailListOrphanCheckOrderDetail + " in its orderDetailList field has a non-nullable historyId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Account accountId = orderHistory.getAccountId();
            if (accountId != null) {
                accountId.getOrderHistoryList().remove(orderHistory);
                accountId = em.merge(accountId);
            }
            em.remove(orderHistory);
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

    public List<OrderHistory> findOrderHistoryEntities() {
        return findOrderHistoryEntities(true, -1, -1);
    }

    public List<OrderHistory> findOrderHistoryEntities(int maxResults, int firstResult) {
        return findOrderHistoryEntities(false, maxResults, firstResult);
    }

    private List<OrderHistory> findOrderHistoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrderHistory.class));
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

    public OrderHistory findOrderHistory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrderHistory.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderHistoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrderHistory> rt = cq.from(OrderHistory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
