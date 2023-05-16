package com.example.calculatorui2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.Expression

class MainActivity : AppCompatActivity() {
    private var tvInput:TextView?=null
    var lastDigit:Boolean=false
    var lastDecimal:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput=findViewById(R.id.tvExpression)

    }
    fun onClick(view:View){
        tvInput?.append((view as Button).text)
        lastDigit=true
    }
    fun allCLear(view:View){
        tvInput?.text=""
        lastDigit=false
        lastDecimal=false
    }
    fun onClickDecimal(view:View){
        if(lastDigit&& (!lastDecimal)){
            tvInput?.append(".")
            lastDigit=false
            lastDecimal=true
        }
    }
    fun onClickOperator(view: View){
       tvInput?.text?.let {
           if(lastDigit && !onOperatorAdded(it.toString())){
               tvInput?.append((view as Button).text)
               lastDecimal=false
               lastDigit=false
           }
       }
    }
    fun backSpace(view: View){
        var result=tvInput?.text.toString()
        if(result.isNotEmpty()){
            tvInput?.text=result.substring(0,result.length-1)
        }
    }
    fun onEqual(view: View){
        if(lastDigit){
            var tvValue=tvInput?.text.toString()
            var prefix=""
            try{
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue=tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    var splitText=tvValue.split("-")
                    var one = splitText[0]
                    var two = splitText[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text=decimalZero((one.toDouble() - two.toDouble()).toString())
                }else  if(tvValue.contains("+")){
                    var splitText=tvValue.split("+")
                    var one = splitText[0]
                    var two = splitText[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text=decimalZero((one.toDouble() + two.toDouble()).toString())
                }else  if(tvValue.contains("X")){
                    var splitText=tvValue.split("X")
                    var one = splitText[0]
                    var two = splitText[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text=decimalZero((one.toDouble() * two.toDouble()).toString())
                } else  if(tvValue.contains("/")){
                    var splitText=tvValue.split("/")
                    var one = splitText[0]
                    var two = splitText[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text=decimalZero((one.toDouble() /two.toDouble()).toString())
                }else  if(tvValue.contains("%")){
                    var splitText=tvValue.split("%")
                    var one = splitText[0]
                    var two = splitText[1]
                    if(prefix.isNotEmpty()){
                        one=prefix+one
                    }
                    tvInput?.text=decimalZero((one.toDouble() % two.toDouble()).toString())
                }

            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }

    }
    private fun decimalZero(result:String) :String{
        var value=result
        if(result.contains(".0")){
           value=result.substring(0,result.length-2)
        }
        return value
    }
    private fun onOperatorAdded(value:String):Boolean{

        return if(value.startsWith("-")){
            false
        }else{
            value.contains("+")||
                    value.contains("x")||
                    value.contains("/")||
                    value.contains("-")||
                    value.contains("%")
        }
    }
}