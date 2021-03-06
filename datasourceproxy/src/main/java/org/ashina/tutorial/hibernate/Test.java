package org.ashina.tutorial.hibernate;

import org.ashina.tutorial.hibernate.entity.Post;
import org.ashina.tutorial.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class Test {

    public static void main(String[] args) {
        Session session = HibernateUtils.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        Post post = new Post(1, "abc");
        try {
            transaction.begin();
            session.merge(post);
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
