package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.domain.model.Publisher;
import org.motoc.gamelibrary.repository.fragment.PublisherFragmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data.sql / JPA
 */
public interface PublisherRepository extends JpaRepository<Publisher, Long>, PublisherFragmentRepository {

    Page<Publisher> findByLowerCaseNameContaining(String keyword,
                                                  Pageable pageable);
}
