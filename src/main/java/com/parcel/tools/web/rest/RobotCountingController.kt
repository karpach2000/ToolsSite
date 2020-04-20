package com.parcel.tools.web.rest

import com.parcel.tools.robot.Counter
import com.parcel.tools.robot.RobotProtocol
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.io.IOException

@Controller
class RobotCountingController {
    @RequestMapping( "/getFinalLinkOrientation")
    @ResponseBody
    @Throws(IOException::class)
    internal fun getFinalLinkOrientation(@RequestParam("manipulatorFormul") manipulatorFormul: String): String {
        val robotProtocol = RobotProtocol()
        val robot = robotProtocol.getRobotModelFromFormul(manipulatorFormul)
        val counter = Counter()
        val joint = counter.getPosition(robot)
        return robotProtocol.setRobotModelToFormul(joint)

    }
}