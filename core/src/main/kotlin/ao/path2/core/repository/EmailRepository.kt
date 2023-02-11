package ao.path2.core.repository;

import org.springframework.data.repository.PagingAndSortingRepository

interface EmailRepository : PagingAndSortingRepository<Email, Long> {
}