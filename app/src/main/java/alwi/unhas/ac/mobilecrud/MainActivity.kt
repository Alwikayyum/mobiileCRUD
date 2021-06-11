package alwi.unhas.ac.mobilecrud

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var listBarang = ArrayList<Barang>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            val intent = Intent(this, BarangActivity::class.java)
            startActivity(intent)
        }

        loadData()
    }


    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val dbAdapter = Adapter(this)
        val cursor = dbAdapter.allQuery()

        listBarang.clear()
        if (cursor!!.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                val nama = cursor.getString(cursor.getColumnIndex("Nama"))
                val jenis = cursor.getString(cursor.getColumnIndex("Jenis"))
                val harga = cursor.getString(cursor.getColumnIndex("Harga"))

                listBarang.add(Barang(id, nama, jenis, harga))
            }while (cursor.moveToNext())
        }

        val barangAdapter = BarangAdapter(listBarang)
        Barang.adapter = barangAdapter
    }

    @Suppress("NAME_SHADOWING")
    inner class BarangAdapter(private var barangList: ArrayList<Barang>) :
        BaseAdapter() {

        @SuppressLint("SetTextI18n")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val view: View?
            val vh: ViewHolder

            if (convertView == null){
                view = layoutInflater.inflate(R.layout.barang, parent, false)
                vh = ViewHolder(view)
                view.tag = vh
                Log.i("db", "set tag for ViewHolder, position: $position")
            }else{
                view = convertView
                vh = view.tag as ViewHolder
            }

            val Barang = barangList[position]

            vh.Nama.text = Barang.name
            vh.Jenis.text = Barang.jenis
            vh.Harga.text = "Rp." + Barang.harga

            Barang.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
                updateBarang(Barang)
            }

            return view
        }

        override fun getItem(position: Int): Any {
            return barangList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return barangList.size
        }

    }

    private fun updateBarang(barang: Barang) {
        val intent = Intent(this, BarangActivity::class.java)
        intent.putExtra("MainActId", barang.id)
        intent.putExtra("MainActNama", barang.name)
        intent.putExtra("MainActJenis", barang.jenis)
        intent.putExtra("MainActHarga", barang.harga)
        startActivity(intent)
    }

    private class ViewHolder(view: View?){
        val Nama: TextView
        val Jenis: TextView
        val Harga: TextView

        init {
            this.Nama = view?.findViewById(R.id.Nama) as TextView
            this.Jenis = view.findViewById(R.id.Jenis) as TextView
            this.Harga = view.findViewById(R.id.Harga) as TextView
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}