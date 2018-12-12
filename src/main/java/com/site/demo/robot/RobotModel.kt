package com.site.demo.robot


/**
 *Ообъект описывающий робота.
 */
public  class  RobotModel() {

    /**
     * Объект класса сочлинения робота.
     * Методрасчета взят здесь https://studfiles.net/preview/985240/page:9/
     * И здесь http://boteon.com/blogs/kreativnye-tehnologii/mehanika-robotov-seminar-3.html
     */
    public class Joint(var i: Int, var lx: Double, var ly: Double, var lz: Double,
                       var ox: Double, var oy: Double, var oz: Double) {



    }

    private  var jointItems = ArrayList<Joint>()
    private var jointCount=0;



    /**
     * Добавить сочленение.
     * сочлинения требуется добавлять в порядке их следования.
     */
    public  fun  addJoint(lx: Double, ly: Double, lz: Double,
                          ox: Double, oy: Double, oz: Double)
    {
        var joint = Joint(jointCount, lx, ly, lz, ox, oy, oz);
        jointItems.add(joint);
        jointCount++;
    }

    /**
     * Получить i тое сочленение.
     * сочлинения требуется добавлять в порядке их следования.
     */
    public  fun  getJoinItem(i: Int): Joint
    {
        return jointItems.get(i)
    }

    /**
     * Получить коллекцию сочленений
     */
    public  fun  getJoinItems(): ArrayList<Joint>
    {
        return jointItems
    }

}