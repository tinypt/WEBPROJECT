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
import jpa.model.OrderDetail;
import jpa.model.Orders;
import jpa.model.Product;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Hong
 */
public class OrderDetailJpaController implements Serializable {

    public OrderDetailJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrderDetail orderDetail) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orders orderId = orderDetail.getOrderId();
            if (orderId != null) {
                orderId = em.getReference(orderId.getClass(), orderId.getOrderId());
                orderDetail.setOrderId(orderId);
            }
            Product productId = orderDetail.getProductId();
            if (productId != null) {
                productId = em.getReference(productId.getClass(), productId.getProductId());
                orderDetail.setProductId(productId);
            }
            em.persist(orderDetail);
            if (orderId != null) {
                orderId.getOrderDetailList().add(orderDetail);
                orderId = em.merge(orderId);
            }
            if (productId != null) {
                productId.getOrderDetailList().add(orderDetail);
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

    public void edit(OrderDetail orderDetail) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            OrderDetail persistentOrderDetail = em.find(OrderDetail.class, orderDetail.getDetailId());
            Orders orderIdOld = persistentOrderDetail.getOrderId();
            Orders orderIdNew = orderDetail.getOrderId();
            Product productIdOld = persistentOrderDetail.getProductId();
            Product productIdNew = orderDetail.getProductId();
            if (orderIdNew != null) {
                orderIdNew = em.getReference(orderIdNew.getClass(), orderIdNew.getOrderId());
                orderDetail.setOrderId(orderIdNew);
            }
            if (productIdNew != null) {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getProductId());
                orderDetail.setProductId(productIdNew);
            }
            orderDetail = em.merge(orderDetail);
            if (orderIdOld != null && !orderIdOld.equals(orderIdNew)) {
                orderIdOld.getOrderDetailList().remove(orderDetail);
                orderIdOld = em.merge(orderIdOld);
            }
            if (orderIdNew != null && !orderIdNew.equals(orderIdOld)) {
                orderIdNew.getOrderDetailList().add(orderDetail);
                orderIdNew = em.merge(orderIdNew);
            }
            if (productIdOld != null && !productIdOld.equals(productIdNew)) {
                productIdOld.getOrderDetailList().remove(orderDetail);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld)) {
                productIdNew.getOrderDetailList().add(orderDetail);
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
                Integer id = orderDetail.getDetailId();
                if (findOrderDetail(id) == null) {
                    throw new NonexistentEntityException("The orderDetail with id " + id + " no longer exists.");
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
            OrderDetail orderDetail;
            try {
                orderDetail = em.getReference(OrderDetail.class, id);
                orderDetail.getDetailId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderDetail with id " + id + " no longer exists.", enfe);
            }
            Orders orderId = orderDetail.getOrderId();
            if (orderId != null) {
                orderId.getOrderDetailList().remove(orderDetail);
                orderId = em.merge(orderId);
            }
            Product productId = orderDetail.getProductId();
            if (productId != null) {
                productId.getOrderDetailList().remove(orderDetail);
                productId = em.merge(productId);
            }
            em.remove(orderDetail);
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

    public List<OrderDetail> findOrderDetailEntities() {
        return findOrderDetailEntities(true, -1, -1);
    }

    public List<OrderDetail> findOrderDetailEntities(int maxResults, int firstResult) {
        return findOrderDetailEntities(false, maxResults, firstResult);
    }

    private List<OrderDetail> findOrderDetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrderDetail.class));
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

    public OrderDetail findOrderDetail(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrderDetail.class, id);
        } finally {
            em.close();
        }
    }
    
    public OrderDetail findOrderDetailByOrderID(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrderDetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderDetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrderDetail> rt = cq.from(OrderDetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
