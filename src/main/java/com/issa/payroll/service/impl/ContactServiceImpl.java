package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Contact;
import com.issa.payroll.repository.ContactRepository;
import com.issa.payroll.service.ContactService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Contact}.
 */
@Service
@Transactional
public class ContactServiceImpl implements ContactService {

    private final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Request to save Contact : {}", contact);
        return contactRepository.save(contact);
    }

    @Override
    public Optional<Contact> partialUpdate(Contact contact) {
        log.debug("Request to partially update Contact : {}", contact);

        return contactRepository
            .findById(contact.getId())
            .map(existingContact -> {
                if (contact.getNomAr() != null) {
                    existingContact.setNomAr(contact.getNomAr());
                }
                if (contact.getPrenomAr() != null) {
                    existingContact.setPrenomAr(contact.getPrenomAr());
                }
                if (contact.getNomEn() != null) {
                    existingContact.setNomEn(contact.getNomEn());
                }
                if (contact.getPrenomEn() != null) {
                    existingContact.setPrenomEn(contact.getPrenomEn());
                }
                if (contact.getEmail() != null) {
                    existingContact.setEmail(contact.getEmail());
                }
                if (contact.getTel() != null) {
                    existingContact.setTel(contact.getTel());
                }
                if (contact.getPhone() != null) {
                    existingContact.setPhone(contact.getPhone());
                }
                if (contact.getAdresse() != null) {
                    existingContact.setAdresse(contact.getAdresse());
                }
                if (contact.getUtil() != null) {
                    existingContact.setUtil(contact.getUtil());
                }
                if (contact.getDateop() != null) {
                    existingContact.setDateop(contact.getDateop());
                }
                if (contact.getModifiedBy() != null) {
                    existingContact.setModifiedBy(contact.getModifiedBy());
                }
                if (contact.getOp() != null) {
                    existingContact.setOp(contact.getOp());
                }
                if (contact.getIsDeleted() != null) {
                    existingContact.setIsDeleted(contact.getIsDeleted());
                }

                return existingContact;
            })
            .map(contactRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Contact> findAll(Pageable pageable) {
        log.debug("Request to get all Contacts");
        return contactRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contact> findOne(Long id) {
        log.debug("Request to get Contact : {}", id);
        return contactRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contact : {}", id);
        contactRepository.deleteById(id);
    }
}
