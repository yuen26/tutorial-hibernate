package org.ashina.tutorial.hibernate.utils;

import net.ttddyy.dsproxy.listener.logging.CommonsLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.ashina.tutorial.hibernate.entity.Post;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // STEP 1: Build the StandardServiceRegistry
            Map<String, Object> settings = new HashMap<>();
            settings.put(Environment.DATASOURCE, getDataSource());
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(settings)
                    .build();

            // STEP 2: Build Metadata
            Metadata metadata = new MetadataSources(standardRegistry)
                    .addAnnotatedClass(Post.class)
                    .getMetadataBuilder()
                    .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                    .build();

            // STEP 3: Build SessionFactory
            sessionFactory = metadata.getSessionFactoryBuilder()
                    .build();
        }

        return sessionFactory;
    }

    private static DataSource getDataSource() {
        // Create DataSource
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/blog?useUnicode=yes&characterEncoding=UTF-8&useSSL=false");
        dataSource.setUser("postgres");
        dataSource.setPassword("1234");

        // Create ProxyDataSource
        return ProxyDataSourceBuilder.create(dataSource)
                .logQueryByCommons(CommonsLogLevel.INFO)
                .name("ProxyDataSource")
                .countQuery()
                .multiline()
                .build();
    }

}
