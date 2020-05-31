package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.model.ProductLine;
import org.motoc.gamelibrary.repository.ProductLineRepository;
import org.motoc.gamelibrary.repository.ProductLineRepositoryCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Perform business logic on the web entity ProductLine
 *
 * @author RouzicJ
 */
@Service
@Transactional
public class ProductLineService extends SimpleCrudMethodsImpl<ProductLine, JpaRepository<ProductLine, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(ProductLineService.class);

    private final ProductLineRepository productLineRepository;

    private final ProductLineRepositoryCustom productLineRepositoryCustom;

    @Autowired
    public ProductLineService(JpaRepository<ProductLine, Long> genericRepository, ProductLineRepository productLineRepository, ProductLineRepositoryCustom productLineRepositoryCustom) {
        super(genericRepository, ProductLine.class);
        this.productLineRepository = productLineRepository;
        this.productLineRepositoryCustom = productLineRepositoryCustom;
    }

    // Methods

    /**
     * Calls the DAO to edit a product line by id
     */
    public ProductLine edit(ProductLine productLine, Long id) {
        return productLineRepository.findById(id)
                .map(productLineFromPersistence -> {
                    productLineFromPersistence.setName(productLine.getName());
                    logger.debug("Found product line of id={} : {}", id, productLineFromPersistence);
                    return productLineRepository.save(productLineFromPersistence);
                })
                .orElseGet(() -> {
                    productLine.setId(id);
                    logger.debug("No product line of id={} found. Set theme : {}", id, productLine);
                    return productLineRepository.save(productLine);
                });
    }

    /**
     * Calls the DAO to delete a product line by id
     */
    public void remove(Long id) {
        logger.debug("deleting (if exist) productLine of id=" + id);
        productLineRepositoryCustom.remove(id);
    }
}
