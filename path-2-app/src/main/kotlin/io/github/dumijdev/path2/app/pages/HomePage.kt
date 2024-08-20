package io.github.dumijdev.path2.app.pages

import com.vaadin.flow.component.html.Main
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import io.github.dumijdev.path2.app.layouts.LoggedLayout
import io.github.dumijdev.path2.app.components.Map


@Route(value = "/", layout = LoggedLayout::class)
@PageTitle("Path 2 | Página inicial")
class HomePage : Main(){
    init {
        setSizeFull()
        add(Map())
    }
}