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

		def best = bestRoute.compute(Point.from(-8.85214, 13.27589), Point.from(-8.8211, 13.2173))

		println best
	}

}
