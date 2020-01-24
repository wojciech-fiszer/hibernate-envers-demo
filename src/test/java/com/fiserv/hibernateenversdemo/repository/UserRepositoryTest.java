package com.fiserv.hibernateenversdemo.repository;

import com.fiserv.hibernateenversdemo.model.Post;
import com.fiserv.hibernateenversdemo.model.User;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldFindUserWithProperRevisionOfPost() {

        User user = new User();
        user.setName("name");

        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        transactionTemplate.executeWithoutResult(transactionStatus -> userRepository.save(user));

        Post post = new Post();
        post.setContent("content");
        post.setUser(user);

        transactionTemplate.executeWithoutResult(transactionStatus -> postRepository.save(post));

        post.setContent("new-content");

        transactionTemplate.executeWithoutResult(transactionStatus -> postRepository.save(post));

        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery auditQuery = auditReader.createQuery().forRevisionsOfEntity(User.class, true, true);
        List<User> userRevisions = auditQuery.getResultList();
        assertFalse(userRevisions.isEmpty());
        assertEquals(2, userRevisions.size());
        assertTrue(userRevisions.get(0).getPosts().isEmpty());
        assertFalse(userRevisions.get(1).getPosts().isEmpty());
        assertEquals(1, userRevisions.get(1).getPosts().size());
        assertEquals("content", userRevisions.get(1).getPosts().iterator().next().getContent());
        Optional<Post> optionalPost = postRepository.findById(post.getId());
        assertTrue(optionalPost.isPresent());
        assertEquals("new-content", optionalPost.get().getContent());
    }
}