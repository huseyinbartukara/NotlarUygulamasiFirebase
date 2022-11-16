package com.example.notlaruygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detay.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_not_kayit.*

class DetayActivity : AppCompatActivity() {

    private lateinit var not:Notlar
    private lateinit var vt:VeritabaniYardimcisi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detay)

        vt = VeritabaniYardimcisi(this)

        toolbarNotDetay.title = "Not Detay"
        setSupportActionBar(toolbarNotDetay)

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

                            Notlardao().notSil(vt,not.not_id)

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

                Notlardao().notGuncelle(vt,ders_adi,not1.toInt(),not2.toInt(),not.not_id)

                startActivity(Intent(this@DetayActivity,MainActivity::class.java))
                finish()
                return true
            }
            else -> return false
        }
    }



}