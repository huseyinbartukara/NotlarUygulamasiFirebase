package com.example.notlaruygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var notlarListe:ArrayList<Notlar>
    private lateinit var adapter: NotlarAdapter
    private lateinit var refNotlar : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Notlar Uygulaması"

        setSupportActionBar(toolbar)

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this@MainActivity)

        val db = FirebaseDatabase.getInstance()
        refNotlar = db.getReference("notlar")

        notlarListe = ArrayList()

        adapter = NotlarAdapter(this,notlarListe)

        rv.adapter = adapter

        tumNotlar()

        fab.setOnClickListener {

            startActivity(Intent(this@MainActivity,NotKayitActivity::class.java))

        }


    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun tumNotlar(){
        refNotlar.addValueEventListener(object  :ValueEventListener{

            override fun onDataChange(d: DataSnapshot) {
                notlarListe.clear()
                var toplam = 0

                for(c in d.children){
                    val not = c.getValue(Notlar::class.java)

                    if(not != null){
                        not.not_id = c.key
                        notlarListe.add(not)
                        toplam = toplam + (not.not1!! + not.not2!!)/2
                    }
                }
                adapter.notifyDataSetChanged()

                if(toplam != 0){
                    toolbar.subtitle = "Ortalama : ${toplam/notlarListe.size}"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}