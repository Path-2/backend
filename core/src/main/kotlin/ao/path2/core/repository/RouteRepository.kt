package ao.path2.core.repository;

import ao.path2.core.models.Route
import org.springframework.data.repository.PagingAndSortingRepository

interface RouteRepository : PagingAndSortingRepository<Route, Long> {
}