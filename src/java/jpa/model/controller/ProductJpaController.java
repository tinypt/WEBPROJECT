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
import jpa.model.OrderDetail;
import jpa.model.Product;
import jpa.model.controller.exceptions.IllegalOrphanException;
import jpa.model.controller.exceptions.NonexistentEntityException;
import jpa.model.controller.exceptions.RollbackFailureException;

/**
 *
 * @author Hong
 */
public class ProductJpaController implements Serializable {

    public ProductJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) throws RollbackFailureException, Exception {
        if (product.getFavouriteList() == null) {
            product.setFavouriteList(new ArrayList<Favourite>());
        }
        if (product.getOrderDetailList() == null) {
            product.setOrderDetailList(new ArrayList<OrderDetail>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Favourite> attachedFavouriteList = new ArrayList<Favourite>();
            for (Favourite favouriteListFavouriteToAttach : product.getFavouriteList()) {
                favouriteListFavouriteToAttach = em.getReference(favouriteListFavouriteToAttach.getClass(), favouriteListFavouriteToAttach.getFavouriteId());
                attachedFavouriteList.add(favouriteListFavouriteToAttach);
            }
            product.setFavouriteList(attachedFavouriteList);
            List<OrderDetail> attachedOrderDetailList = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailListOrderDetailToAttach : product.getOrderDetailList()) {
                orderDetailListOrderDetailToAttach = em.getReference(orderDetailListOrderDetailToAttach.getClass(), orderDetailListOrderDetailToAttach.getDetailId());
                attachedOrderDetailList.add(orderDetailListOrderDetailToAttach);
            }
            product.setOrderDetailList(attachedOrderDetailList);
            em.persist(product);
            for (Favourite favouriteListFavourite : product.getFavouriteList()) {
                Product oldProductIdOfFavouriteListFavourite = favouriteListFavourite.getProductId();
                favouriteListFavourite.setProductId(product);
                favouriteListFavourite = em.merge(favouriteListFavourite);
                if (oldProductIdOfFavouriteListFavourite != null) {
                    oldProductIdOfFavouriteListFavourite.getFavouriteList().remove(favouriteListFavourite);
                    oldProductIdOfFavouriteListFavourite = em.merge(oldProductIdOfFavouriteListFavourite);
                }
            }
            for (OrderDetail orderDetailListOrderDetail : product.getOrderDetailList()) {
                Product oldProductIdOfOrderDetailListOrderDetail = orderDetailListOrderDetail.getProductId();
                orderDetailListOrderDetail.setProductId(product);
                orderDetailListOrderDetail = em.merge(orderDetailListOrderDetail);
                if (oldProductIdOfOrderDetailListOrderDetail != null) {
                    oldProductIdOfOrderDetailListOrderDetail.getOrderDetailList().remove(orderDetailListOrderDetail);
                    oldProductIdOfOrderDetailListOrderDetail = em.merge(oldProductIdOfOrderDetailListOrderDetail);
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

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product persistentProduct = em.find(Product.class, product.getProductId());
            List<Favourite> favouriteListOld = persistentProduct.getFavouriteList();
            List<Favourite> favouriteListNew = product.getFavouriteList();
            List<OrderDetail> orderDetailListOld = persistentProduct.getOrderDetailList();
            List<OrderDetail> orderDetailListNew = product.getOrderDetailList();
            List<String> illegalOrphanMessages = null;
            for (Favourite favouriteListOldFavourite : favouriteListOld) {
                if (!favouriteListNew.contains(favouriteListOldFavourite)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Favourite " + favouriteListOldFavourite + " since its productId field is not nullable.");
                }
            }
            for (OrderDetail orderDetailListOldOrderDetail : orderDetailListOld) {
                if (!orderDetailListNew.contains(orderDetailListOldOrderDetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderDetail " + orderDetailListOldOrderDetail + " since its productId field is not nullable.");
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
            product.setFavouriteList(favouriteListNew);
            List<OrderDetail> attachedOrderDetailListNew = new ArrayList<OrderDetail>();
            for (OrderDetail orderDetailListNewOrderDetailToAttach : orderDetailListNew) {
                orderDetailListNewOrderDetailToAttach = em.getReference(orderDetailListNewOrderDetailToAttach.getClass(), orderDetailListNewOrderDetailToAttach.getDetailId());
                attachedOrderDetailListNew.add(orderDetailListNewOrderDetailToAttach);
            }
            orderDetailListNew = attachedOrderDetailListNew;
            product.setOrderDetailList(orderDetailListNew);
            product = em.merge(product);
            for (Favourite favouriteListNewFavourite : favouriteListNew) {
                if (!favouriteListOld.contains(favouriteListNewFavourite)) {
                    Product oldProductIdOfFavouriteListNewFavourite = favouriteListNewFavourite.getProductId();
                    favouriteListNewFavourite.setProductId(product);
                    favouriteListNewFavourite = em.merge(favouriteListNewFavourite);
                    if (oldProductIdOfFavouriteListNewFavourite != null && !oldProductIdOfFavouriteListNewFavourite.equals(product)) {
                        oldProductIdOfFavouriteListNewFavourite.getFavouriteList().remove(favouriteListNewFavourite);
                        oldProductIdOfFavouriteListNewFavourite = em.merge(oldProductIdOfFavouriteListNewFavourite);
                    }
                }
            }
            for (OrderDetail orderDetailListNewOrderDetail : orderDetailListNew) {
                if (!orderDetailListOld.contains(orderDetailListNewOrderDetail)) {
                    Product oldProductIdOfOrderDetailListNewOrderDetail = orderDetailListNewOrderDetail.getProductId();
                    orderDetailListNewOrderDetail.setProductId(product);
                    orderDetailListNewOrderDetail = em.merge(orderDetailListNewOrderDetail);
                    if (oldProductIdOfOrderDetailListNewOrderDetail != null && !oldProductIdOfOrderDetailListNewOrderDetail.equals(product)) {
                        oldProductIdOfOrderDetailListNewOrderDetail.getOrderDetailList().remove(orderDetailListNewOrderDetail);
                        oldProductIdOfOrderDetailListNewOrderDetail = em.merge(oldProductIdOfOrderDetailListNewOrderDetail);
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
                Integer id = product.getProductId();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProductId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Favourite> favouriteListOrphanCheck = product.getFavouriteList();
            for (Favourite favouriteListOrphanCheckFavourite : favouriteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Favourite " + favouriteListOrphanCheckFavourite + " in its favouriteList field has a non-nullable productId field.");
            }
            List<OrderDetail> orderDetailListOrphanCheck = product.getOrderDetailList();
            for (OrderDetail orderDetailListOrphanCheckOrderDetail : orderDetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the OrderDetail " + orderDetailListOrphanCheckOrderDetail + " in its orderDetailList field has a non-nullable productId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(product);
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

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Product.class));
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

    public Product findProduct(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }
    
    public List <Product> findByProductName(String productName) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Product.findByProductName");
            query.setParameter("productName", "%"+productName+"%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List <Product> findByProductType(String type) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createNamedQuery("Product.findByType");
            query.setParameter("type", type);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Product> rt = cq.from(Product.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
