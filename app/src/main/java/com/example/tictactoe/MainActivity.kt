package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_play_game.*

class MainActivity : AppCompatActivity() {

    companion object{
        public var IsSinglePlay : Boolean = true

        public var Player1Name : String = "Name1"
        public var Player2Name : String = "Name2"

        public var level = 2

        public var Isx : Boolean = true

    }

    lateinit var opition : Spinner
    lateinit var result : TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        opition = spinner
        result = RoboName

        var opitions = arrayOf("Pupil" , "Spetsalist" , "Expert" , "Master")

        opition.adapter = ArrayAdapter<String>(this ,android.R.layout.simple_list_item_1, opitions)

        opition.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                result.text = "Please Slect an Difficulty"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                level  =position+1
                result.text = opitions.get(position)
            }

        }

        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)

                if(radio.text == "Single Player"){
                    IsSinglePlay = true
                    SecondBoard.setVisibility(View.INVISIBLE)
                    RoboBoard.setVisibility(View.VISIBLE)

                }
                else{
                    IsSinglePlay = false
                    SecondBoard.setVisibility(View.VISIBLE)
                    RoboBoard.setVisibility(View.INVISIBLE)
                }


            })

        radio_groupXO.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(applicationContext,"2 On checked change : ${radio.text}",
                    Toast.LENGTH_SHORT).show()
                if(radio.text == "X") Isx = true
                else Isx = false
            })





        play.setOnClickListener{
            if(IsSinglePlay){
                Player1Name = first_PlayerName.text.toString()
                Player2Name = RoboName.text.toString()
            }
            else{
                Player1Name = first_PlayerName.text.toString()
                Player2Name = second_PlayerName.text.toString()
            }

            intent = Intent(this , PlayGame::class.java)



            startActivity(intent)
        }


    }



}

