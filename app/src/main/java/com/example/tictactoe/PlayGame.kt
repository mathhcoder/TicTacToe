package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_play_game.*

class PlayGame : AppCompatActivity() {

    var mx = IntArray(9) { 2 }


    var firstScore = 0
    var secondScore = 0
    var firstIcon : String = ""
    var secondIcon : String = ""
    var cnt = 1
    public var mfirstName: String = MainActivity.Player1Name
    public var msecondName: String = MainActivity.Player2Name
    public var level :Int = MainActivity.level
    public var firstSide = 1
    public var secondSide = 0
    public var players = 1

    public var btns: Array<TextView?> = arrayOfNulls(9)

    // 0 -> O
    // 1 -> X
    // 2 -> free
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)
        if (MainActivity.IsSinglePlay)
            players = 1
        else players = 2

        if(mfirstName.length == 0) mfirstName = "First_Pl"
        if(msecondName.length == 0) msecondName = "Second_Pl"
        firstPlayerName.text = mfirstName
        secondPlayerName.text = msecondName

        if(MainActivity.Isx){
            firstSide = 1
            secondSide = 0
        }
        else{
            firstSide = 0
            secondSide = 1
        }
        if(firstSide == 1) {
            firstIcon = "X"
            secondIcon = "O"
        }
        else{
            firstIcon = "O"
            secondIcon = "X"
        }


        val clickListener = View.OnClickListener {view ->
            var getId : String  = view.getResources().getResourceName(view.getId())
            var n : Int = getId.length
            var rId:String = getId.substring(n-5..n-1)
            var id = getId[n-1].toInt() - 48


            click(id , true)

        }


        for(i in 0 until 9){
            var id: String = "btn_${i}"
            var resId : Int = getResources().getIdentifier(id , "id" , getPackageName())
            btns[i] = findViewById(resId)

            btns[i]!!.setOnClickListener(clickListener)
            btns[i]!!.setText(" ")

        }







    }

    fun click(id : Int , isplayer : Boolean){

        var chek = Cheker()
        if(chek <= 2){
            return
        }
        if(isplayer == false){
            if(mx[id] == 2){
                mx[id] = secondSide
                btns[id]!!.setText(secondIcon)
                cnt ++
            }
            else{
                Toast.makeText(applicationContext,"Error ${id} has benn clicked olredy" , Toast.LENGTH_LONG).show()
            }

        }
        else{
            if(mx[id]!=2){
                Toast.makeText(applicationContext, "Error this button ${id} has been clikced .\n Please Click another one" , Toast.LENGTH_SHORT).show()
            }
            else{
                if(cnt % 2 == 0 && players == 1) {
                    return
                }
                if(cnt % 2 == 0 ){
                    mx[id] = secondSide
                    btns[id]!!.setText(secondIcon)
                }
                else{


                    mx[id] = firstSide
             //       Toast.makeText(applicationContext , "line 120 " , Toast.LENGTH_SHORT).show()

                    //
                    btns[id]!!.setText(firstIcon)

                }
                cnt ++
                if(cnt % 2 == 0 && players == 1){
                    RoboTurn()
                }


            }

        }
        chek = Cheker()

        if(chek <= 2){

            var st : String = ""
            for(i in 0 until 9){
                st = st + mx[i].toString() + " "
            }

            if(chek == 2){
                Toast.makeText(applicationContext , "Draw" , Toast.LENGTH_SHORT).show()
            }
            else if(chek == firstSide){
                Toast.makeText(applicationContext , "${mfirstName} wins" , Toast.LENGTH_SHORT).show()
                firstScore ++
            }
            else{
                Toast.makeText(applicationContext , "${msecondName} wins" , Toast.LENGTH_SHORT).show()
                secondScore ++
            }

            first_score.text = firstScore.toString()
            second_score.text = secondScore.toString()
            restart.setVisibility(View.VISIBLE)
        }


    }

    fun RoboTurn(){

        var id :Int = 0
        if(level == 1){
            id =  RoboPupil()
        }
        else if(level == 2){
            id = RoboSpecialist()

        }

        click(id , false)


    }

    fun RoboPupil() : Int{
        var arr : IntArray = IntArray(9){0}
        var sz : Int = 0
        for(i in 0 until 9){
            if(mx[i] == 2){
                arr[sz] = i
                sz ++
            }
        }
        return arr[(0..sz).random()]
    }

    fun RoboSpecialist():Int{
        var arr: IntArray = IntArray(9){0}
        var sz = 0
        var dif : IntArray = IntArray(9){0}
        var df = 0
        var tot : IntArray = IntArray(9){0}
        var t = 0
        for(i in 0 until 9){
            if(mx[i] == 2){
                mx[i] = secondSide
                if(Cheker() == secondSide){
                    arr[sz] = i
                    sz ++
                    mx[i] = 2
                }
                mx[i] = firstSide
                if(Cheker() == firstSide){
                    dif[df] = i
                    df ++
                    mx[i] = 2
                }
                mx[i] = 2
                tot[t]= i
                t ++
            }
        }
        if(sz > 0){
            return arr[(0..sz).random()]
        }
        else if(df > 0){
            return dif[(0..df).random()]
        }
        else{
            return tot[(0..t).random()]
        }

    }

    fun RoboExpert(){

    }




    fun Cheker(): Int {
        if (mx[0] == mx[4] && mx[4] == mx[8] && mx[4] != 2) return mx[4]
        if (mx[2] == mx[4] && mx[4] == mx[6] && mx[4] != 2) return mx[4]
        for (i in 0 until 3) {
            if (mx[i] == mx[i + 3] && mx[i + 3] == mx[i + 6] && mx[i] != 2) return mx[i]
            if (mx[3 * i] == mx[3 * i + 1] && mx[i * 3] == mx[i * 3 + 2] && mx[3 * i] != 2) return mx[3 * i]
        }
        var r = 0
        for (i in 0 until 9) {
            if (mx[i] != 2) r++
        }
        if (r == 9) return 2
        return 3
    }

    fun Restart(view: View) {
        for(x in 0 until 9){
            btns[x]!!.setText("")
            mx[x] = 2
            cnt = 1
        }
        restart.setVisibility(View.INVISIBLE)
    }

    fun Back(view: View) {
        var intent : Intent = Intent(this , MainActivity::class.java)
        startActivity(intent)

    }

}
