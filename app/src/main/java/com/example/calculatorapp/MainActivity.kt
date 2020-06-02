package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*

private const val State_operation="pendingOperation"
private const val State_operand1="operand1"
private const val State_operand1_state="Stateoperand1"

class MainActivity : AppCompatActivity() {
//    private lateinit var result:EditText
//    private lateinit var newnumber:EditText
//    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    //creating variables to hold the operands

    private var operand1:Double? = null
//    private var operand2:Double=0.0
    private var pendingOperation = "="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        result=findViewById(R.id.result)
//        newnumber=findViewById(R.id.newnumber)
//
//        //designating ids for the buttons
//        val button0:Button=findViewById(R.id.button0)
//        val button1:Button=findViewById(R.id.button1)
//        val button2:Button=findViewById(R.id.button2)
//        val button3:Button=findViewById(R.id.button3)
//        val button4:Button=findViewById(R.id.button4)
//        val button5:Button=findViewById(R.id.button5)
//        val button6:Button=findViewById(R.id.button6)
//        val button7:Button=findViewById(R.id.button7)
//        val button8:Button=findViewById(R.id.button8)
//        val button9:Button=findViewById(R.id.button9)
//        val buttondecimal:Button=findViewById(R.id.buttondecimal)
//
//        //operative buttons
//        val buttonequals=findViewById<Button>(R.id.buttonequals)    //both the methods can be used to assign local variables
//        val buttondivide=findViewById<Button>(R.id.buttondivide)
//        val buttonmultiply=findViewById<Button>(R.id.buttonmultiply)
//        val buttonplus=findViewById<Button>(R.id.buttonplus)
//        val buttonminus=findViewById<Button>(R.id.buttonminus)

        //Listener to listen the number and decimal button
        val listener= View.OnClickListener { v->
            val b=v as Button
            newnumber.append(b.text)
        }

        //listener set to the number and decimal button
        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttondecimal.setOnClickListener(listener)

        //listener to listen the operation buttons

        val opListener =View.OnClickListener { v->
            val op=(v as Button).text.toString()
            try {
                val value=newnumber.text.toString().toDouble()
                performOperation(value,op)
            }catch (e:NumberFormatException){
                newnumber.setText("")
            }
            pendingOperation=op
            operation.text =op
        }

        buttonequals.setOnClickListener(opListener)
        buttonplus.setOnClickListener(opListener)
        buttonminus.setOnClickListener(opListener)
        buttondivide.setOnClickListener(opListener)
        buttonmultiply.setOnClickListener(opListener)

        buttonNeg.setOnClickListener { v->
            val value=newnumber.text.toString()
            if(value.isEmpty())
            {
                newnumber.setText("-")
            }
            else
            {
                try {
                    var temp=value.toDouble()
                    temp *=-1
                    newnumber.setText(temp.toString())
                }catch (e: NumberFormatException){
                    newnumber.setText("")
                }
            }
        }
        }
    //function to do the neccessary operation
    private fun performOperation(value: Double, op: String) {
        if(operand1 == null)
            operand1 = value
        else
        {
            when(pendingOperation)
            {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.0){
                    Double.NaN
                } else{
                    operand1!! / value
                }
                "*" -> operand1= operand1!! * value
                "+" -> operand1= operand1!! + value
                "-" -> operand1=  operand1!!-value
            }
        }
        result.setText(operand1.toString())
        newnumber.setText("")
        operation.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(operand1 != null) {
            outState.putDouble(State_operand1, operand1!!)
            outState.putBoolean(State_operand1_state,true)
        }
        outState.putString(State_operation,pendingOperation)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if(savedInstanceState.getBoolean(State_operand1_state)) {
            savedInstanceState.getDouble(State_operand1)
        }else {
            null
        }

        pendingOperation= savedInstanceState.getString(State_operation).toString()
        operation.setText(pendingOperation)

    }
}
