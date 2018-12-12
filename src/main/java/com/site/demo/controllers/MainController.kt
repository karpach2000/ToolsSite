package com.site.demo.controllers
        //import com.github.mustachejava.DefaultMustacheFactory
import com.site.demo.constructor.Page
import com.site.demo.constructor.bodies.counter.Counter
import com.site.demo.constructor.bodies.mainpage.MainPage

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

//import org.springframework.web.bind.annotation.ResponseBody
import java.io.IOException
import java.io.StringWriter

@Controller
class MainController {


    @RequestMapping("/")
    @Throws(IOException::class)
    internal fun index(model: Model): String {
        /*
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("index.mustache");

        StringWriter writer = new StringWriter();
        m.execute(writer, page).flush();
        String html = writer.toString();
        */
        val mainPage = MainPage()
        val page = Page(mainPage)
        model.addAttribute("page", page)
        return "web/html/index"
    }

    @RequestMapping("/utils")
    @Throws(IOException::class)
    internal fun utils(model: Model): String {
        val counter = Counter()
        val page = Page(counter)

        model.addAttribute("page", page)
        return "web/html/utils"
    }

    @RequestMapping("/countingManipulators")
    @Throws(IOException::class)
    internal fun countingManipulators(model: Model): String {
        val page = Page()
        model.addAttribute("page", page)
        return "web/html/countingManipulators"
    }
}