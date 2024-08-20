package io.github.dumijdev.path2.app.components

import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.sidenav.SideNav
import com.vaadin.flow.component.sidenav.SideNavItem

class SideBarComponent : SideNav() {
    init {
        addItem(SideNavItem("Home", "/", VaadinIcon.HOME.create()))
        addItem(SideNavItem("Profile", "/me", VaadinIcon.USER.create()))
        addItem(SideNavItem("Paths", "/paths", VaadinIcon.ROAD.create()))
        addItem(SideNavItem("Stops", "/stops", VaadinIcon.STOP.create()))
    }
}