package com.site.demo.robot


import java.lang.Math.cos
import java.lang.Math.sin

public class Counter()
{
    private  val  matrixSize=4;

    public fun getPosition(model: RobotModel, i :Int = -1): RobotModel.Joint {
        //определяем количество звеньев для расчета
        var  partNumber=i;
        if(partNumber<0)
        {
            partNumber=model.getJoinItems().size
        }

        //считаем
        var ansMatrix=generateUnitMatrix()
        for(j in 0 until  partNumber)
        {
            var matrix=generateMatrix(model.getJoinItem(j))
            var xMatrix=generateXMatrix(model.getJoinItem(j).ox)
            var yMatrix=generateYMatrix(model.getJoinItem(j).oy)
            var zMatrix=generateZMatrix(model.getJoinItem(j).oz)
            ansMatrix=multiplicationMatrix(ansMatrix, matrix)
            ansMatrix=multiplicationMatrix(ansMatrix, xMatrix)
            ansMatrix=multiplicationMatrix(ansMatrix, yMatrix)
            ansMatrix=multiplicationMatrix(ansMatrix, zMatrix)
        }


        return RobotModel.Joint(partNumber, ansMatrix[0][3], ansMatrix[1][3], ansMatrix[2][3], 0.0, 0.0, 0.0)

    }

    public fun getPosition(model: RobotModel): RobotModel.Joint {
        //определяем количество звеньев для расчета

        var  partNumber=model.getJoinItems().size


        //считаем
        var ansMatrix=generateUnitMatrix()
        for(j in 0 until partNumber)
        {
            var matrix=generateMatrix(model.getJoinItem(j))
            var xMatrix=generateXMatrix(model.getJoinItem(j).ox)
            var yMatrix=generateYMatrix(model.getJoinItem(j).oy)
            var zMatrix=generateZMatrix(model.getJoinItem(j).oz)

            ansMatrix=multiplicationMatrix(ansMatrix, xMatrix)
            ansMatrix=multiplicationMatrix(ansMatrix, yMatrix)
            ansMatrix=multiplicationMatrix(ansMatrix, zMatrix)
            ansMatrix=multiplicationMatrix(ansMatrix, matrix)
        }


        return RobotModel.Joint(partNumber, ansMatrix[0][3], ansMatrix[1][3], ansMatrix[2][3], 0.0, 0.0, 0.0)

    }
    /**
     * Метод генерирующий матрицу сочленения робота
     */
    private  fun generateUnitMatrix(): Array<Array<Double>> {
        var ans=Array(matrixSize,{Array(matrixSize,{0.0})})
        ans[0][0]=1.0; ans[0][1]=0.0; ans[0][2]=0.0; ans[0][3]=0.0;
        ans[1][0]=0.0; ans[1][1]=1.0; ans[1][2]=0.0; ans[1][3]=0.0;
        ans[2][0]=0.0; ans[2][1]=0.0; ans[2][2]=1.0; ans[2][3]=0.0;
        ans[3][0]=0.0; ans[3][1]=0.0; ans[3][2]=0.0; ans[3][3]=1.0;
        return ans
    }

    /**
     * Метод генерирующий матрицу сочленения робота
     */
    private  fun generateMatrix(joint: RobotModel.Joint): Array<Array<Double>> {
        var ans=Array(matrixSize,{Array(matrixSize,{0.0})})
        ans[0][0]=1.0; ans[0][1]=0.0; ans[0][2]=0.0; ans[0][3]=joint.lx;
        ans[1][0]=0.0; ans[1][1]=1.0; ans[1][2]=0.0; ans[1][3]=joint.ly;
        ans[2][0]=0.0; ans[2][1]=0.0; ans[2][2]=1.0; ans[2][3]=joint.lz;
        ans[3][0]=0.0; ans[3][1]=0.0; ans[3][2]=0.0; ans[3][3]=1.0;
        return ans
    }

    /**
     * Метод генерирующий матрицу поворота вокруг oX
     */
    private  fun generateXMatrix(angle :Double): Array<Array<Double>> {
        var ans=Array(matrixSize,{Array(matrixSize,{0.0})})
        ans[0][0]=1.0; ans[0][1]=0.0;         ans[0][2]=0.0;         ans[0][3]=0.0;
        ans[1][0]=0.0; ans[1][1]= Math.cos(angle);  ans[1][2]=-Math.sin(angle); ans[1][3]=0.0;
        ans[2][0]=0.0; ans[2][1]= Math.sin(angle);  ans[2][2]= Math.cos(angle);  ans[2][3]=0.0;
        ans[3][0]=0.0; ans[3][1]=0.0;         ans[3][2]=0.0;         ans[3][3]=1.0;
        return ans
    }

    /**
     * Метод генерирующий матрицу поворота вокруг oY
     */
    private  fun generateYMatrix(angle :Double): Array<Array<Double>> {
        var ans=Array(matrixSize,{Array(matrixSize,{0.0})})
        ans[0][0]= Math.cos(angle);   ans[0][1]=0.0; ans[0][2]= Math.sin(angle);  ans[0][3]=0.0;
        ans[1][0]=0.0;          ans[1][1]=1.0; ans[1][2]=0.0;         ans[1][3]=0.0;
        ans[2][0]=-Math.sin(angle);  ans[2][1]=0.0; ans[2][2]= Math.cos(angle);  ans[2][3]=0.0;
        ans[3][0]=0.0;          ans[3][1]=0.0; ans[3][2]=0.0;         ans[3][3]=1.0;
        return ans
    }
    /**
     * Метод генерирующий матрицу поворота вокруг oZ
     */
    private  fun generateZMatrix(angle :Double): Array<Array<Double>> {
        var ans=Array(matrixSize,{Array(matrixSize,{0.0})})
        ans[0][0]= Math.cos(angle); ans[0][1]=-Math.sin(angle); ans[0][2]=0.0;  ans[0][3]=0.0;
        ans[1][0]= Math.sin(angle); ans[1][1]= Math.cos(angle);  ans[1][2]=0.0;  ans[1][3]=0.0;
        ans[2][0]=0.0;        ans[2][1]=0.0;         ans[2][2]=1.0;  ans[2][3]=0.0;
        ans[3][0]=0.0;        ans[3][1]=0.0;         ans[3][2]=0.0;  ans[3][3]=1.0;
        return ans
    }
    /**
     * Метод перемножения 2 матриц
     */
    private  fun multiplicationMatrix(array1: Array<Array<Double>>, array2: Array<Array<Double>>):Array<Array<Double>>
    {
        var lenth=array1.size;
        var ans=Array(lenth,{Array(lenth,{0.0})})
        for(i in 0 until lenth)
        {
            for(j in 0 until lenth)
            {
                ans[i][j]=getMatrixElementValue(array1, array2, i, j);
            }
        }
        return ans;
    }

    /**
     * Получчить элемент перемножаемых матриц
     */
    private  fun getMatrixElementValue(array1: Array<Array<Double>>, array2: Array<Array<Double>>, i: Int, j: Int): Double {
        var lenth=array1.size;
        var ans=0.0;
        for(a in 0 until lenth)
        {
            ans=ans+array1[i][a]*array2[a][j]
        }
        return ans
    }



}