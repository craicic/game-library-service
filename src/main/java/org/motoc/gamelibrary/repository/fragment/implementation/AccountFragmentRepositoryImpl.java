package org.motoc.gamelibrary.repository.fragment.implementation;

import org.motoc.gamelibrary.domain.model.Account;
import org.motoc.gamelibrary.domain.model.Account_;
import org.motoc.gamelibrary.repository.fragment.AccountFragmentRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * It's the account custom repository implementation, made to create / use javax persistence objects, criteria, queryDSL (if needed)
 */
@Repository
public class AccountFragmentRepositoryImpl implements AccountFragmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(AccountFragmentRepositoryImpl.class);

    private final EntityManagerFactory emf;


    @Autowired
    public AccountFragmentRepositoryImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public Account find(Long id) {
        EntityManager em = emf.createEntityManager();


        String query = "SELECT a FROM Account a WHERE a.id = (:id)";


        EntityGraph<Account> graph = em.createEntityGraph(Account.class);
        graph.addSubgraph(Account_.contact);
        graph.addSubgraph(Account_.loans);

        em.getTransaction().begin();

        Account account = em.createQuery(query, Account.class)
                .setParameter("id", id)
                .setHint("javax.persistence.loadgraph", graph)
                .getSingleResult();

        em.getTransaction().commit();
        em.close();

        if (account == null) {
            throw new NotFoundException("Could not find account of id:" + id);
        }


        return account;
    }
}