package com.example.notlaruygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_detay.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_not_kayit.*

class DetayActivity : AppCompatActivity() {

    private lateinit var not:Notlar
    private lateinit var refNotlar : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detay)



        toolbarNotDetay.title = "Not Detay"
        setSupportActionBar(toolbarNotDetay)

        val db = FirebaseDatabase.getInstance()
        refNotlar = db.getReference("notlar")

        not = intent.getSerializableExtra("nesne") as Notlar

        editTextDersDetay.setText(not.ders_adi)
        editTextNot1Detay.setText((not.not1).toString())
        editTextNot2Detay.setText((not.not2).toString())


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_sil -> {
                Snackbar.make(toolbarNotDetay,"Silinsin mi?",Snackbar.LENGTH_SHORT)
                        .setAction("Evet"){

                            refNotlar.child(not.not_id!!).removeValue()

                            startActivity(Intent(this@DetayActivity,MainActivity::class.java))
                        finish()
                }.show()
                return true
            }
            R.id.action_duzenle -> {

                val ders_adi = editTextDersDetay.text.toString().trim()
                val not1 = editTextNot1Detay.text.toString().trim()
                val not2 = editTextNot2Detay.text.toString().trim()


                if(TextUtils.isEmpty(ders_adi)){
                    Snackbar.make(toolbarNotDetay,"Ders Adi Giriniz",Snackbar.LENGTH_SHORT).show()
                    return false
                }

                if(TextUtils.isEmpty(not1)){
                    Snackbar.make(toolbarNotDetay,"Not1 Değerini Giriniz",Snackbar.LENGTH_SHORT).show()
                    return false
                }

                if(TextUtils.isEmpty(not2)){
                    Snackbar.make(toolbarNotDetay,"Not2 Değerini Giriniz",Snackbar.LENGTH_SHORT).show()
                    return false
                }

                val bilgiler = HashMap<String,Any>()
                bilgiler.put("ders_adi",ders_adi)
                bilgiler.put("not1",not1.toInt())
                bilgiler.put("not2",not2.toInt())

                refNotlar.child(not.not_id!!).updateChildren(bilgiler)

                startActivity(Intent(this@DetayActivity,MainActivity::class.java))
                finish()
                return true
            }
            else -> return false
        }
    }



}