package com.example.notlaruygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_not_kayit.*

class NotKayitActivity : AppCompatActivity() {

    private lateinit var refNotlar: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_kayit)

        toolbarNotKayit.title = "NotKayit"
        setSupportActionBar(toolbarNotKayit)

        val db = FirebaseDatabase.getInstance()
        refNotlar = db.getReference("notlar")

        buttonKaydet.setOnClickListener {

            val dersAdi = editTextDers.text.toString().trim()
            val not1 = editTextNot1.text.toString().trim()
            val not2 = editTextNot2.text.toString().trim()

            if(TextUtils.isEmpty(dersAdi)){
                Snackbar.make(toolbarNotKayit,"Ders Adi Giriniz!",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(not1)){
                Snackbar.make(toolbarNotKayit,"Not1 Bilgisini Giriniz!",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(not2)){
                Snackbar.make(toolbarNotKayit,"Not2 Bilgisini Giriniz!",Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val not = Notlar("",dersAdi,not1.toInt(),not2.toInt())

            refNotlar.push().setValue(not)




            startActivity(Intent(this@NotKayitActivity,MainActivity::class.java))
            finish()
        }



    }
}