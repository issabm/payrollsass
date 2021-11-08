package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Groupe;
import com.issa.payroll.repository.GroupeRepository;
import com.issa.payroll.service.GroupeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Groupe}.
 */
@Service
@Transactional
public class GroupeServiceImpl implements GroupeService {

    private final Logger log = LoggerFactory.getLogger(GroupeServiceImpl.class);

    private final GroupeRepository groupeRepository;

    public GroupeServiceImpl(GroupeRepository groupeRepository) {
        this.groupeRepository = groupeRepository;
    }

    @Override
    public Groupe save(Groupe groupe) {
        log.debug("Request to save Groupe : {}", groupe);
        return groupeRepository.save(groupe);
    }

    @Override
    public Optional<Groupe> partialUpdate(Groupe groupe) {
        log.debug("Request to partially update Groupe : {}", groupe);

        return groupeRepository
            .findById(groupe.getId())
            .map(existingGroupe -> {
                if (groupe.getCode() != null) {
                    existingGroupe.setCode(groupe.getCode());
                }
                if (groupe.getLibAr() != null) {
                    existingGroupe.setLibAr(groupe.getLibAr());
                }
                if (groupe.getLibEn() != null) {
                    existingGroupe.setLibEn(groupe.getLibEn());
                }
                if (groupe.getAddresseAr() != null) {
                    existingGroupe.setAddresseAr(groupe.getAddresseAr());
                }
                if (groupe.getAddresseEn() != null) {
                    existingGroupe.setAddresseEn(groupe.getAddresseEn());
                }
                if (groupe.getTel() != null) {
                    existingGroupe.setTel(groupe.getTel());
                }
                if (groupe.getEmail() != null) {
                    existingGroupe.setEmail(groupe.getEmail());
                }
                if (groupe.getFax() != null) {
                    existingGroupe.setFax(groupe.getFax());
                }
                if (groupe.getUtil() != null) {
                    existingGroupe.setUtil(groupe.getUtil());
                }
                if (groupe.getDateop() != null) {
                    existingGroupe.setDateop(groupe.getDateop());
                }
                if (groupe.getModifiedBy() != null) {
                    existingGroupe.setModifiedBy(groupe.getModifiedBy());
                }
                if (groupe.getOp() != null) {
                    existingGroupe.setOp(groupe.getOp());
                }
                if (groupe.getIsDeleted() != null) {
                    existingGroupe.setIsDeleted(groupe.getIsDeleted());
                }

                return existingGroupe;
            })
            .map(groupeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Groupe> findAll(Pageable pageable) {
        log.debug("Request to get all Groupes");
        return groupeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Groupe> findOne(Long id) {
        log.debug("Request to get Groupe : {}", id);
        return groupeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Groupe : {}", id);
        groupeRepository.deleteById(id);
    }
}
