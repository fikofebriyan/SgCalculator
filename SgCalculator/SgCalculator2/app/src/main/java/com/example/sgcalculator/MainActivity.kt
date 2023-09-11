package com.example.sgcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import java.time.temporal.TemporalAmount

// when we have any type of Logging, we can use the TAG which is the class name
private const val TAG ="MainActivity"

// set the tip percent to 15% by DEFAULT
private const val INITIAL_TIP_PERCENT = 15

// activity can refer to one screen
class MainActivity : AppCompatActivity() {
    //declare variables

    // lateinit- initialise this property before using it
    //         - cannot use that have nullable types
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var textViewPercent : TextView
    private lateinit var textViewTips: TextView
    private lateinit var textViewTotalAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        textViewPercent = findViewById(R.id.textViewPercent)
        textViewTips = findViewById(R.id.textViewTips)
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount)

        seekBarTip.progress = INITIAL_TIP_PERCENT
        //UPDATE THE LABEL
        textViewPercent.text = "$INITIAL_TIP_PERCENT%"

        // To get notified of changes on the seekbar
        //                                   what should happen when the seekbar change
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {

                // to more easily figure whats going on, progress- current value of the seekbar
                // when there are progress or change on the seat bar, which means the user is scrubbing
                // and print out the current value shown in logcat
                // so basically, when we move the seekbar, you will see at the logcat
                // below between profiler and app quality insights
                // it will see the progress
                Log.i(TAG,"onProgressChanged $progress")

                // update the UI or the progress of the seekbar
                textViewPercent.text = "$progress"
                computeTipandTotal()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        //object - how to create annonymous class

        etBaseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChange $p0")
                computeTipandTotal()
            }

        })



        etBaseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChange $p0")
            }

        })

    }

    private fun computeTipandTotal() {
        // do checking, if not when there are empty string it will crash
       if(etBaseAmount.text.isEmpty()){
           textViewTips.text = ""
           textViewTotalAmount.text = ""

           // return so that the rest of the function get executed
           return
       }

        // 1. Get the value of the base and tip percent
        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress

       // 2. Compute the tip and total
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount

       // 3. Update the UI
       textViewTips.text = "%.2f ".format(tipAmount)
        textViewTotalAmount.text = "%.2f ".format(totalAmount)
    }
}