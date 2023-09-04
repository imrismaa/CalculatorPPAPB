package com.example.calculator

import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentCalc = "" //menyimpan expression
    private var currentOperation = "" //menyimpan operator
    private var result = "" //menyimpan hasil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btnClicked()
    }

    private fun btnClicked() {
        // define btn C
        binding.btnClear.setOnClickListener {
            currentCalc = ""
            currentOperation = ""
            result = ""
            binding.operation.text = ""
            binding.result.text = ""
        }

        //define btn del
        binding.btnDel.setOnClickListener {
            delLastChar()
        }

        // define operator btn
        binding.btnPlus.setOnClickListener {
            inputOperator("+")
        }
        binding.btnMin.setOnClickListener {
            inputOperator("-")
        }
        binding.btnTimes.setOnClickListener {
            inputOperator("×")
        }
        binding.btnDiv.setOnClickListener {
            inputOperator("÷")
        }
        binding.btnMod.setOnClickListener {
            inputOperator("%")
        }
        binding.btnExp.setOnClickListener {
            inputOperator("^")
        }

        // define numb btn
        binding.btn0.setOnClickListener {
            inputOperand("0")
        }
        binding.btn1.setOnClickListener {
            inputOperand("1")
        }
        binding.btn2.setOnClickListener {
            inputOperand("2")
        }
        binding.btn3.setOnClickListener {
            inputOperand("3")
        }
        binding.btn4.setOnClickListener {
            inputOperand("4")
        }
        binding.btn5.setOnClickListener {
            inputOperand("5")
        }
        binding.btn6.setOnClickListener {
            inputOperand("6")
        }
        binding.btn7.setOnClickListener {
            inputOperand("7")
        }
        binding.btn8.setOnClickListener {
            inputOperand("8")
        }
        binding.btn9.setOnClickListener {
            inputOperand("9")
        }
        binding.btnPoint.setOnClickListener{
            inputOperand(".")
        }

        // define =
        binding.btnResult.setOnClickListener {
            calculateResult()
        }
    }

    private fun delLastChar() {
        if (currentCalc.isNotEmpty()) {
            currentCalc = currentCalc.substring(0, currentCalc.length - 1)
            binding.operation.text = currentCalc
        }
    }

    private fun inputOperator(op: String) {
        if (currentCalc.isNotEmpty() && currentOperation.isEmpty()) {
            currentOperation = op
            currentCalc += op
            binding.operation.text = currentCalc
        }
    }

    private fun inputOperand(operand: String) {
        if (currentCalc.isEmpty() && operand == "0") {
            return
        }

        if (currentCalc.isNotEmpty() || operand != "0") {
            val operators = setOf("+", "-", "×", "÷", "%", "^")
            if (operators.contains(currentOperation) || currentOperation.isEmpty()) {
                if (currentOperation.isNotEmpty() && currentCalc.endsWith("0") && operand == "0") {
                    return
                }
                currentCalc += operand
                binding.operation.text = currentCalc
            }
        }
    }



    private fun calculateResult() {
        if (currentCalc.isNotEmpty() && currentOperation.isNotEmpty()) {
            val operands = currentCalc.split(Regex("[-+×÷^%]"))
            val operator = currentOperation

            if (operands.size == 2) {
                val num1 = BigDecimal(operands[0])
                val num2 = BigDecimal(operands[1])
                when (operator) {
                    "+" -> result = (num1 + num2).toString()
                    "-" -> result = (num1 - num2).toString()
                    "×" -> result = (num1 * num2).toString()
                    "÷" -> {
                        if (num2.toInt() == 0) {
                            Toast.makeText(this@MainActivity, "can't divide by zero", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            result = (num1/num2).toString()
                        }
                    }
                    "%" -> result = (num1 % num2).toString()
                    "^" ->
                        result = (Math.pow(num1.toDouble(), num2.toDouble())).toInt().toString()
                }
            }
        }
        binding.result.text = result
        currentCalc = result
        currentOperation = ""
    }
}