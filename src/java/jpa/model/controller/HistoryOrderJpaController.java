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
import jpa.model.HistoryOrder;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author tinypt
 */
public class HistoryOrderJpaController implements Serializable {

    public HistoryOrderJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistoryOrder historyOrder) throws RollbackFailureException, Exception {
        if (historyOrder.getOrderDetailList() == null) {
            historyOrder.setOrderDetailList(new ArrayList<OrderDetail>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account accountId = historyOrder.getAccountId();
            if (accountId != null) {
                accountId = em.getReference(accountId.getClass(), accountId.getAccountId());
                historyOrder.setAccountId(accountId);
            }
            List<OrderDetail> attachedOrderDetailList = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailListOrderDetailToAttach : historyOrder.getOrderDetailList()) {
                orderDetailListOrderDetailToAttach = em.getReference(orderDetailListOrderDetailToAttach.getClass(), orderDetailListOrderDetailToAttach.getDetailId());
                attachedOrderDetailList.add(orderDetailListOrderDetailToAttach);
            }
            historyOrder.setOrderDetailList(attachedOrderDetailList);
            em.persist(historyOrder);
            if (accountId != null) {
                accountId.getHistoryOrderList().add(historyOrder);
                accountId = em.merge(accountId);
            }
            for (OrderDetail orderDetailListOrderDetail : historyOrder.getOrderDetailList()) {
                HistoryOrder oldHistoryIdOfOrderDetailListOrderDetail = orderDetailListOrderDetail.getHistoryId();
                orderDetailListOrderDetail.setHistoryId(historyOrder);
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

    public void edit(HistoryOrder historyOrder) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            HistoryOrder persistentHistoryOrder = em.find(HistoryOrder.class, historyOrder.getHistoryId());
            Account accountIdOld = persistentHistoryOrder.getAccountId();
            Account accountIdNew = historyOrder.getAccountId();
            List<OrderDetail> orderDetailListOld = persistentHistoryOrder.getOrderDetailList();
            List<OrderDetail> orderDetailListNew = historyOrder.getOrderDetailList();
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
                historyOrder.setAccountId(accountIdNew);
            }
            List<OrderDetail> attachedOrderDetailListNew = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailListNewOrderDetailToAttach : orderDetailListNew) {
                orderDetailListNewOrderDetailToAttach = em.getReference(orderDetailListNewOrderDetailToAttach.getClass(), orderDetailListNewOrderDetailToAttach.getDetailId());
                attachedOrderDetailListNew.add(orderDetailListNewOrderDetailToAttach);
            }
            orderDetailListNew = attachedOrderDetailListNew;
            historyOrder.setOrderDetailList(orderDetailListNew);
            historyOrder = em.merge(historyOrder);
            if (accountIdOld != null && !accountIdOld.equals(accountIdNew)) {
                accountIdOld.getHistoryOrderList().remove(historyOrder);
                accountIdOld = em.merge(accountIdOld);
            }
            if (accountIdNew != null && !accountIdNew.equals(accountIdOld)) {
                accountIdNew.getHistoryOrderList().add(historyOrder);
                accountIdNew = em.merge(accountIdNew);
            }
            for (OrderDetail orderDetailListNewOrderDetail : orderDetailListNew) {
                if (!orderDetailListOld.contains(orderDetailListNewOrderDetail)) {
                    HistoryOrder oldHistoryIdOfOrderDetailListNewOrderDetail = orderDetailListNewOrderDetail.getHistoryId();
                    orderDetailListNewOrderDetail.setHistoryId(historyOrder);
                    orderDetailListNewOrderDetail = em.merge(orderDetailListNewOrderDetail);
                    if (oldHistoryIdOfOrderDetailListNewOrderDetail != null && !oldHistoryIdOfOrderDetailListNewOrderDetail.equals(historyOrder)) {
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
                Integer id = historyOrder.getHistoryId();
                if (findHistoryOrder(id) == null) {
                    throw new NonexistentEntityException("The historyOrder with id " + id + " no longer exists.");
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
            HistoryOrder historyOrder;
            try {
                historyOrder = em.getReference(HistoryOrder.class, id);
                historyOrder.getHistoryId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historyOrder with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<OrderDetail> orderDetailListOrphanCheck = historyOrder.getOrderDetailList();
            for (OrderDetail orderDetailListOrphanCheckOrderDetail : orderDetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This HistoryOrder (" + historyOrder + ") cannot be destroyed since the OrderDetail " + orderDetailListOrphanCheckOrderDetail + " in its orderDetailList field has a non-nullable historyId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Account accountId = historyOrder.getAccountId();
            if (accountId != null) {
                accountId.getHistoryOrderList().remove(historyOrder);
                accountId = em.merge(accountId);
            }
            em.remove(historyOrder);
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

    public List<HistoryOrder> findHistoryOrderEntities() {
        return findHistoryOrderEntities(true, -1, -1);
    }

    public List<HistoryOrder> findHistoryOrderEntities(int maxResults, int firstResult) {
        return findHistoryOrderEntities(false, maxResults, firstResult);
    }

    private List<HistoryOrder> findHistoryOrderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistoryOrder.class));
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

    public HistoryOrder findHistoryOrder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistoryOrder.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoryOrderCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistoryOrder> rt = cq.from(HistoryOrder.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
