package ru.stqa.pft.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.mantis.model.User;
import ru.stqa.pft.mantis.model.Users;

import java.util.List;
import java.util.Properties;

public class DbHelper {
  private final SessionFactory sessionFactory;

  public DbHelper(Properties properties) {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public Users getUsersFromBD() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<User> result = session.createQuery( "from User").list();
    session.getTransaction().commit();
    session.close();
    return new Users(result);
  }
}
