package ao.path2.ms.compute.routes.config.beans

import ao.path2.ms.compute.routes.service.BestRoute
import com.graphhopper.GraphHopper
import com.graphhopper.config.CHProfile
import com.graphhopper.config.Profile
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.nio.file.Paths

@Configuration
class GraphHopperBean {

    final OSM_FILE = "ms-compute-routes\\target\\classes\\osm-data\\angola.osm.pbf"
    final OSM_PATH = "osm-temp"

    // Cria uma instância do objeto GraphHopper e configura-o
    @Bean
    GraphHopper graphHopper() {
        println new File("osm-data/angola.osm.pbf").getPath()
        println new File("osm-data/angola.osm.pbf").getCanonicalPath()
        println new File("osm-data/angola.osm.pbf").getPath()

        def hopper = new GraphHopper().setOSMFile(OSM_FILE)
                .setGraphHopperLocation(OSM_PATH)
                .setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest").setTurnCosts(false))

        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"))

        return hopper.importOrLoad()
    }

    @Bean
    def bestRouteBean() {
        return new BestRoute(graphHopper())
    }
}
