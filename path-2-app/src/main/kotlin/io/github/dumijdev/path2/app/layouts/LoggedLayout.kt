package io.github.dumijdev.path2.app.layouts

import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.applayout.DrawerToggle
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.orderedlayout.Scroller
import com.vaadin.flow.theme.lumo.LumoUtility
import io.github.dumijdev.path2.app.components.SideBarComponent
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

class LoggedLayout : AppLayout() {

    init {
        val title = H1("Path 2")
        title.style
            .set("font-size", "var(--lumo-font-size-l)")
            .set("margin", "10px")

        val scroller = Scroller(SideBarComponent())
        scroller.className = LumoUtility.Padding.SMALL

        this.addToDrawer(scroller)
        this.addToNavbar(DrawerToggle(), title)
    }
}