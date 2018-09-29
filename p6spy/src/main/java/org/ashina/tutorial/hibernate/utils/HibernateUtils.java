package org.ashina.tutorial.hibernate.utils;

import org.ashina.tutorial.hibernate.entity.Post;
import org.ashina.tutorial.hibernate.entity.PostDetail;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // STEP 1: Build the StandardServiceRegistry
            Map<String, Object> settings = new HashMap<>();
            settings.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
            settings.put(Environment.URL, "jdbc:p6spy:postgresql://localhost:5432/blog?useUnicode=yes&characterEncoding=UTF-8&useSSL=false");
            settings.put(Environment.USER, "postgres");
            settings.put(Environment.PASS, "1234");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(settings)
                    .build();

            // STEP 2: Build Metadata
            Metadata metadata = new MetadataSources(standardRegistry)
                    .addAnnotatedClass(Post.class)
                    .addAnnotatedClass(PostDetail.class)
                    .getMetadataBuilder()
                    .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                    .build();

            // STEP 3: Build SessionFactory
            sessionFactory = metadata.getSessionFactoryBuilder()
                    .build();
        }

        return sessionFactory;
    }

}
