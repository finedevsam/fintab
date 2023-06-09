package com.savitech.fintab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.savitech.fintab.entity.InternalAccount;

public interface InternalAccountRepository extends JpaRepository<InternalAccount, String> {
    Optional<InternalAccount> findFirstByOrderByCreatedAtDesc();

    InternalAccount findInternalAccountByAccountCode(String accountCode);

    InternalAccount findInternalAccountById(String id);

    InternalAccount findInternalAccountByAccountNo(String accountNo);

    boolean existsByAccountCode(String accountCode);

}
