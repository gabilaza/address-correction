package com.chill.service;

import com.chill.entity.PostalCode;
import com.chill.exception.postalcode.*;
import com.chill.repository.PostalCodeRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostalCodeService {
    private static final Logger logs = LoggerFactory.getLogger(PostalCodeService.class);

    private final PostalCodeRepository postalCodeRepository;

    public List<PostalCode> getAllPostalCodes() {
        logs.info("Get All PostalCodes");
        return postalCodeRepository.findAll();
    }

    public PostalCode getPostalCodeByName(String name) {
        return postalCodeRepository
                .findByName(name)
                .orElseThrow(() -> new PostalCodeNotFoundException(name));
    }

    public void createPostalCode(PostalCode postalCode) {
        if (Boolean.TRUE.equals(existsPostalCodeById(postalCode.getId()))) {
            throw new PostalCodeExistsException(postalCode.getId());
        }

        postalCodeRepository.save(postalCode);
        logs.info("Created PostalCode id: " + postalCode.getId());
    }

    public void updatePostalCode(PostalCode postalCode) {
        if (Boolean.FALSE.equals(existsPostalCodeById(postalCode.getId()))) {
            throw new PostalCodeNotFoundException(postalCode.getId());
        }

        logs.info("Update PostalCode id: " + postalCode.getId());
        postalCodeRepository.save(postalCode);
    }

    public void deletePostalCode(PostalCode postalCode) {
        if (Boolean.FALSE.equals(existsPostalCodeById(postalCode.getId()))) {
            throw new PostalCodeNotFoundException(postalCode.getId());
        }

        logs.info("Delete PostalCode id: " + postalCode.getId());
        postalCodeRepository.delete(postalCode);
    }

    public Boolean existsPostalCodeById(Integer id) {
        if (id != null) {
            return postalCodeRepository.existsById(id);
        }

        return Boolean.FALSE;
    }

    public Boolean existsPostalCodeByName(String name) {
        if (name != null) {
            return postalCodeRepository.existsByName(name);
        }

        return Boolean.FALSE;
    }
}
