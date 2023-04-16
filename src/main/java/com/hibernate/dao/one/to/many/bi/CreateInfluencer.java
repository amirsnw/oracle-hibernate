package com.hibernate.dao.one.to.many.bi;

import com.hibernate.model.one.to.many.Follower;
import com.hibernate.model.one.to.many.Influencer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateInfluencer {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Influencer.class)
								.addAnnotatedClass(Follower.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		try {			
			
			// create the objects
			Influencer tempInfluencer =
					new Influencer("Amir", "Khalighi", "amirsnw@gmail.com");
			
			// start a transaction
			session.beginTransaction();
			
			// save the influencer
			System.out.println("Saving influencer: " + tempInfluencer);
			session.save(tempInfluencer);					
			
			// commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}
		finally {
			factory.close();
		}
	}

}





