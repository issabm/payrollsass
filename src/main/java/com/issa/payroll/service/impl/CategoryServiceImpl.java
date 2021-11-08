package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Category;
import com.issa.payroll.repository.CategoryRepository;
import com.issa.payroll.service.CategoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Category}.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        log.debug("Request to save Category : {}", category);
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> partialUpdate(Category category) {
        log.debug("Request to partially update Category : {}", category);

        return categoryRepository
            .findById(category.getId())
            .map(existingCategory -> {
                if (category.getCode() != null) {
                    existingCategory.setCode(category.getCode());
                }
                if (category.getLibAr() != null) {
                    existingCategory.setLibAr(category.getLibAr());
                }
                if (category.getLibEn() != null) {
                    existingCategory.setLibEn(category.getLibEn());
                }
                if (category.getUtil() != null) {
                    existingCategory.setUtil(category.getUtil());
                }
                if (category.getDateop() != null) {
                    existingCategory.setDateop(category.getDateop());
                }
                if (category.getModifiedBy() != null) {
                    existingCategory.setModifiedBy(category.getModifiedBy());
                }
                if (category.getOp() != null) {
                    existingCategory.setOp(category.getOp());
                }
                if (category.getIsDeleted() != null) {
                    existingCategory.setIsDeleted(category.getIsDeleted());
                }
                if (category.getCreatedDate() != null) {
                    existingCategory.setCreatedDate(category.getCreatedDate());
                }
                if (category.getModifiedDate() != null) {
                    existingCategory.setModifiedDate(category.getModifiedDate());
                }

                return existingCategory;
            })
            .map(categoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}
