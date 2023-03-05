package ao.path2.ms.compute.routes

import ao.path2.ms.compute.routes.models.Point
import ao.path2.ms.compute.routes.service.BestRoute
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MsComputeRoutesApplication {

	static BestRoute bestRoute

	MsComputeRoutesApplication(BestRoute best) {
		bestRoute = best
	}

	static void main(String[] args) {
		SpringApplication.run(MsComputeRoutesApplication, args)

		bestRoute.compute(Point.from(-8.831769, 13.219112), Point.from(-8.957064, 13.148580))
	}

}
