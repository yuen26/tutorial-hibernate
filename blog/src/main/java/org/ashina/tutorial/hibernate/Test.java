package org.ashina.tutorial.hibernate;

import org.ashina.tutorial.hibernate.entity.Post;
import org.ashina.tutorial.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.NoResultException;

public class Test {

    public static void main(String[] args) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();

            Query query = session.createQuery("from Post p where p.id = 111");

            try {
                Post post1 = (Post) query.getSingleResult();
                System.out.println(post1);
            } catch (NoResultException e) {
                System.out.println("Post not found");
            }

            Post post2 = (Post) query.uniqueResult();
            if (post2 == null) {
                System.out.println("Post not found");
            } else {
                System.out.println(post2);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.getStatus() == TransactionStatus.ACTIVE
                    || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                transaction.rollback();
            }
        }
    }

}
