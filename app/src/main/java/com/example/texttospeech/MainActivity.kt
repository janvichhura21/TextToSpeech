package com.example.texttospeech

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var tts:TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView.setOnClickListener {
            val intent=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something")
            startActivityForResult(intent,100)

        }
        tts= TextToSpeech(applicationContext,TextToSpeech.OnInitListener {
            if (it!=TextToSpeech.ERROR){
                tts.language=Locale.UK
            }
        })

        button.setOnClickListener {
            var toSpeak=editTextTextMultiLine.text.toString()
            if (toSpeak==""){
                Toast.makeText(this, "Enter Text", Toast.LENGTH_SHORT).show()
            }
            if (toSpeak=="4"){
                toSpeak= "correct"
                tts.speak(toSpeak,TextToSpeech.QUEUE_ADD,null)
            }
            if (toSpeak=="something else"){
                toSpeak= "incorrect"
                tts.speak(toSpeak,TextToSpeech.QUEUE_ADD,null)
            }

            else{
                Toast.makeText(this, toSpeak, Toast.LENGTH_SHORT).show()
               tts.speak(toSpeak,TextToSpeech.QUEUE_ADD,null)
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==100||data!=null){
            val res: ArrayList<String> = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
            editTextTextMultiLine.setText(res[0])
        }
    }
}