package com.hibernate.dao.mapping.cache.firstLevel;

import com.hibernate.model.simple.SimpleStudent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FetchFromCache {

    private static Logger logger = LoggerFactory.getLogger(FetchFromCache.class);

    public static void main(String[] args) throws InterruptedException {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(SimpleStudent.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
        Session newSession = null;

        try {
            Transaction tx = session.beginTransaction();

            // Get student with id
            // https://www.digitalocean.com/community/tutorials/hibernate-session-get-vs-load-difference-with-examples
            SimpleStudent student = session.load(SimpleStudent.class, 1);
            logger.trace("1 :: " + student);

            // waiting for you to change the data in database
            Thread.sleep(10000);

            // Fetch same data again, check logs that no query fired (show cached values)
            SimpleStudent student2 = session.load(SimpleStudent.class, 1);
            logger.trace("2 :: " + student2);

            // Create new session
            newSession = factory.openSession();

            logger.trace("New session opened ...");
            // Get student with id = 1, notice the logs for query (show updated values)
            SimpleStudent student3 = newSession.load(SimpleStudent.class, 1);
            logger.trace("3 :: " + student3);

            tx.commit();
        } finally {
            // add clean up code
            session.close();
            newSession.close();
            factory.close();
        }
    }
}
