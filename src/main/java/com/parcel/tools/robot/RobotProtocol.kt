package com.parcel.tools.robot



/**
 * Класс занимающийся разбором протокола "РОБОТ"
 */
class RobotProtocol()
{
    /**
     * Получить модель робота из формулы.
     */
    public fun getRobotModelFromFormul(manipulatorFormul: String): RobotModel
    {
        val parts = manipulatorFormul.split("us".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val robot = RobotModel()
        for (p in parts) {
            if(p.isNotEmpty()) {//!!!лишнее время на проверку, достаточно пропустить первый элемент массива
                val lx = getValue(p, "lx")
                val ly = getValue(p, "ly")
                val lz = getValue(p, "lz")
                val ox = getValue(p, "ox")
                val oy = getValue(p, "oy")
                val oz = getValue(p, "oz")
                robot.addJoint(lx, ly, lz, ox, oy, oz)
            }
        }
        return robot;


    }

    /**
     * Преобразовать модель робота в формулу
     */
    public  fun setRobotModelToFormul(robotModel: RobotModel):String
    {
        var joints=robotModel.getJoinItems();
        var formul="";
        var i=0;
        for (j in joints)
        {
            formul=formul+"us"+i+"lx"+j.lx+"ly"+j.ly+"z"+j.lz+"ox"+j.ox+"oy"+j.oy+"oz"+j.oz;
            i++
        }
        return  formul;
    }
    /**
     * Преобразовать модель робота в формулу
     */
    public  fun setRobotModelToFormul(joint: RobotModel.Joint):String
    {
        var formul="";
        formul=formul+"us"+0+"lx"+joint.lx+"ly"+joint.ly+"z"+joint.lz+"ox"+joint.ox+"oy"+joint.oy+"oz"+joint.oz;
        return  formul;
    }
    /**
     * Получает значение из протокола, помеченное маркером
     */
    private  fun getValue(line: String, marker: String): Double
    {
        val l = line.split(marker.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        var ans = ""
        for (i in 0 until l.length) {
            val c = l[i]
            if (c in '0'..'9' || c=='.'  || c=='-') {
                ans += c
            } else
                return java.lang.Double.parseDouble(ans)
        }

        return if(ans.isNotEmpty())
            java.lang.Double.parseDouble(ans)
        else
            0.0

    }


}