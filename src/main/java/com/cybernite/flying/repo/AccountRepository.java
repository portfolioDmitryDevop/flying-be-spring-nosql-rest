package com.cybernite.flying.repo;

import com.cybernite.flying.entities.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
