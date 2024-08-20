package io.github.dumijdev.path2.app.layouts

import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode.CENTER as CENTER1

class UnLoggedLayout : VerticalLayout() {
    init {
        setSizeFull()

        alignItems = CENTER
        justifyContentMode = CENTER1

        style.setBackgroundColor("blue")
    }
}