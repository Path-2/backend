package ao.path2.core.repository;

import ao.path2.core.models.Email
import org.springframework.data.repository.PagingAndSortingRepository

interface EmailRepository : PagingAndSortingRepository<Email, Long> {
}