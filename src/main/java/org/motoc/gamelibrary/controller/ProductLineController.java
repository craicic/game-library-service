package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.ProductLineService;
import org.motoc.gamelibrary.dto.ProductLineDto;
import org.motoc.gamelibrary.mapper.ProductLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Defines product line endpoints
 *
 * @author RouzicJ
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProductLineController {

    private static final Logger logger = LoggerFactory.getLogger(ProductLineController.class);

    private final ProductLineService service;

    private final ProductLineMapper mapper;

    @Autowired
    public ProductLineController(ProductLineService service) {
        this.service = service;
        this.mapper = ProductLineMapper.INSTANCE;
    }

    @GetMapping("/admin/product-lines/count")
    Long count() {
        return service.count();
    }

    @GetMapping("/admin/product-lines")
    ProductLineDto findById(Long id) {
        logger.trace("findById(id) called");
        return mapper.productLineToDto(service.findById(id));
    }

    @GetMapping("/admin/product-line/page")
    Page<ProductLineDto> findPage(Pageable pageable) {
        logger.trace("findPage(pageable) called");
        return mapper.pageToPageDto(service.findPage(pageable));
    }

    @PostMapping("/admin/product-line")
    ProductLineDto save(@RequestBody ProductLineDto productLine) {
        logger.trace("save(productLine) called");
        return mapper.productLineToDto(service.save(mapper.dtoToProductLine(productLine)));
    }

    @PutMapping("/admin/product-line/{id}")
    ProductLineDto edit(@RequestBody ProductLineDto productLine,
                        @PathVariable Long id) {
        logger.trace("edit(productLine, id) called");
        return mapper.productLineToDto(service.edit(mapper.dtoToProductLine(productLine), id));
    }

    @DeleteMapping("/admin/product-line/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }

}
