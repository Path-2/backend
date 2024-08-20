package io.github.dumijdev.path2.app.components

import com.vaadin.flow.component.html.Div
import org.vaadin.addons.componentfactory.leaflet.LeafletMap
import org.vaadin.addons.componentfactory.leaflet.layer.map.options.DefaultMapOptions
import org.vaadin.addons.componentfactory.leaflet.layer.map.options.MapOptions
import org.vaadin.addons.componentfactory.leaflet.types.LatLng

class Map : Div() {
    init {
        val options: MapOptions = DefaultMapOptions()
        options.center = LatLng(-8.827635, 13.216592)
        options.zoom = 15
        val leafletMap = LeafletMap(options)
        leafletMap.setBaseUrl("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png")
        setSizeFull()
        add(leafletMap)
    }
}