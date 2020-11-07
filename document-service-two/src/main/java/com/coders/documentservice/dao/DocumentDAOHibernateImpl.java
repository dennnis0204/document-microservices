package com.coders.documentservice.dao;

import com.coders.documentservice.entity.Document;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class DocumentDAOHibernateImpl implements DocumentDAO {

    // define field for entityManager
    private EntityManager entityManager;

    // set up constructor injection
    public DocumentDAOHibernateImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public List<Document> findAll() {
        // get current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        //create a query
        Query<Document> query =
                currentSession.createQuery("from Document", Document.class);

        // execute query and get result list
        List<Document> allRecords = query.getResultList();

        // return the results
        return allRecords;
    }

    @Override
    public void save(Document document) {

        // get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        // save employee
        currentSession.saveOrUpdate(document);

    }

    @Override
    public List<Document> findAllByUsername(String username) {

        // get current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);

        //create a query
        Query<Document> query =
                currentSession.createQuery("from Document where user=:username", Document.class);
        query.setParameter("username", username);

        // execute query and get result list
        List<Document> usersRecords = query.getResultList();

        // return the results
        return usersRecords;
    }
}
