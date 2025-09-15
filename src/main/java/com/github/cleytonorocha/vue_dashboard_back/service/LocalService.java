package com.github.cleytonorocha.vue_dashboard_back.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.cleytonorocha.vue_dashboard_back.exception.ItemNotFoundException;
import com.github.cleytonorocha.vue_dashboard_back.model.entity.Local;
import com.github.cleytonorocha.vue_dashboard_back.model.repository.LocalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;

    public Page<Local> findAll(Integer page, Integer linesPerPage, String orderBy, String direction) {
        log.info("Fetching all locals with page: {}, linesPerPage: {}, orderBy: {}, direction: {}", page,
                linesPerPage, orderBy, direction);

        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        log.debug("PageRequest created: {}", pageRequest);

        Page<Local> locals = localRepository.findAll(pageRequest);
        log.debug("Locals fetched: {}", locals.getContent());

        log.info("Returning {} locals", locals.getTotalElements());

        return locals;
    }

    public Local findById(Long id) {
        log.info("Fetching local with id: {}", id);
        return localRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Local with id: {} not found", id);
                    return new ItemNotFoundException();
                });
    }

    public Local save(Local local) {
        log.info("Saving local: {}", local);
        return localRepository.save(local);
    }

    public Local update(Long id, Local local) {
        log.info("Updating local with id: {}", id);
        if (localRepository.existsById(id)) {
            local.setId(id);
            return localRepository.save(local);
        } else {
            log.warn("Local with id: {} not found", id);
            throw new ItemNotFoundException();
        }
    }

    public void deleteById(Long id) {
        log.info("Deleting local with id: {}", id);
        if (localRepository.existsById(id)) {
            localRepository.deleteById(id);
        } else {
            log.warn("Local with id: {} not found", id);
            throw new ItemNotFoundException();
        }
    }
}
