package com.parcel.tools.web.rest
        //import com.github.mustachejava.DefaultMustacheFactory
import com.parcel.tools.constructor.Page
import com.parcel.tools.constructor.bodies.admin.CounterAdmin
import com.parcel.tools.constructor.bodies.counter.Counter
import com.parcel.tools.constructor.bodies.mainpage.MainPage
import com.parcel.tools.constructor.bodies.ws.CounterWs

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

//import org.springframework.web.bind.annotation.ResponseBody
import java.io.IOException
import javax.servlet.http.HttpSession

@Controller
class MainController {


    @RequestMapping("/")
    @Throws(IOException::class)
    internal fun index(model: Model, session: HttpSession): String {
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
    internal fun utils(model: Model, session: HttpSession): String {
        val counter = Counter()
        val page = Page(counter)

        model.addAttribute("page", page)
        return "web/html/utils"
    }

    @RequestMapping("/etools")
    @Throws(IOException::class)
    internal fun ws(model: Model, session: HttpSession): String {
        val counter = CounterWs()
        val page = Page(counter)

        model.addAttribute("page", page)
        return "web/html/ws"
    }

    @RequestMapping("/admin")
    @Throws(IOException::class)
    internal fun admin(model: Model, session: HttpSession): String {
        val counter = CounterAdmin()
        val page = Page(counter)

        model.addAttribute("page", page)
        return "web/html/admin"
    }

    @RequestMapping("/countingManipulators")
    @Throws(IOException::class)
    internal fun countingManipulators(model: Model, session: HttpSession): String {
        val page = Page()
        model.addAttribute("page", page)
        return "web/html/countingManipulators"
    }
}