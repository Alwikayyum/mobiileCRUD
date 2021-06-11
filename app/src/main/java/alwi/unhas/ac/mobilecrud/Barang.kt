package alwi.unhas.ac.mobilecrud

import android.widget.AdapterView

class Barang(id: Int, name: String, jenis: String, harga: String) {
    lateinit var onItemClickListener: AdapterView.OnItemClickListener
    var id: Int? = id
    var name: String? = name
    var jenis: String? = jenis
    var harga: String? = harga


}