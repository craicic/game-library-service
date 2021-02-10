package org.motoc.gamelibrary.repository.jpa;

import org.motoc.gamelibrary.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository takes advantage of Spring data / JPA
 *
 * @author RouzicJ
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
