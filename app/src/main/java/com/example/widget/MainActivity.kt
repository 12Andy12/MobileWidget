package com.example.widget

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException



class MainActivity : AppCompatActivity() {
    val txtSize : Float = 10F
    lateinit var btnUpdate: Button
    lateinit var btnUpdateTable: Button
    lateinit var textView: TextView
    lateinit var doc: Document
    var listOfValuta = ArrayList<ArrayList<String>>()
    lateinit var table : TableLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnUpdate = findViewById(R.id.btnUpdate)

        btnUpdateTable = findViewById(R.id.btnTable)
        table = findViewById(R.id.table)

        btnUpdate.setOnClickListener {
            GlobalScope.launch {
                getWeb()
            }

        }
        btnUpdateTable.setOnClickListener {
            updateTable()
            MegaWidget.listOfData = listOfValuta
        }
    }

    fun setHeaders()
    {
        val tableRow = TableRow(this)
        val textNum = TextView(this)
        textNum.text = "Цифр. код"
        textNum.setTextColor(Color.parseColor("#FF000000"))
        textNum.textSize = txtSize + 3F
        textNum.gravity = Gravity.CENTER
        val textStr = TextView(this)
        textStr.text = "Букв. код"
        textStr.setTextColor(Color.parseColor("#FF000000"))
        textStr.textSize = txtSize + 3F
        textStr.gravity = Gravity.CENTER
        val textCount = TextView(this)
        textCount.text = "Единиц"
        textCount.setTextColor(Color.parseColor("#FF000000"))
        textCount.textSize = txtSize + 3F
        textCount.gravity = Gravity.CENTER
        val textName = TextView(this)
        textName.text = "Валюта"
        textName.setTextColor(Color.parseColor("#FF000000"))
        textName.textSize = txtSize + 3F
        textName.gravity = Gravity.CENTER
        val textPrice = TextView(this)
        textPrice.text = "Курс"
        textPrice.setTextColor(Color.parseColor("#FF000000"))
        textPrice.textSize = txtSize + 3F
        textPrice.gravity = Gravity.CENTER



        tableRow.addView(textNum, TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0F))
        tableRow.addView(textStr, TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0F))
        tableRow.addView(textCount, TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0F))
        tableRow.addView(textName, TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0F))
        tableRow.addView(textPrice, TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0F))
        table.addView(tableRow)
    }

    fun updateTable()
    {
        table.removeAllViews()
        setHeaders()
        for(i in listOfValuta)
        {
            val tableRow = TableRow(this)
            val textNum = TextView(this)
            textNum.text = i[0]
            textNum.setTextColor(Color.parseColor("#FF000000"))
            textNum.textSize = txtSize
            textNum.gravity = Gravity.CENTER
            val textStr = TextView(this)
            textStr.text = i[1]
            textStr.setTextColor(Color.parseColor("#FF000000"))
            textStr.textSize = txtSize
            textStr.gravity = Gravity.CENTER
            val textCount = TextView(this)
            textCount.text = i[2]
            textCount.setTextColor(Color.parseColor("#FF000000"))
            textCount.textSize = txtSize
            textCount.gravity = Gravity.CENTER
            val textName = TextView(this)
            textName.text = i[3]
            textName.setTextColor(Color.parseColor("#FF000000"))
            textName.textSize = txtSize
            textName.gravity = Gravity.CENTER
            val textPrice = TextView(this)
            textPrice.text = i[4]
            textPrice.setTextColor(Color.parseColor("#FF000000"))
            textPrice.textSize = txtSize
            textPrice.gravity = Gravity.CENTER



            tableRow.addView(textNum, TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0F))
            tableRow.addView(textStr, TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0F))
            tableRow.addView(textCount, TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0F))
            tableRow.addView(textName, TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0F))
            tableRow.addView(textPrice, TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1.0F))
            table.addView(tableRow)
        }
    }

    private fun getWeb()
    {
        try{
            listOfValuta = arrayListOf()
            doc = Jsoup.connect("https://www.cbr.ru/currency_base/daily/").get()
            var element = doc.select("table[class=data]").select("tbody").select("tr")

            for(i in 1 until element.size)
            {
                var row = element[i]
                var cols = row.select("td")

                var rowList = ArrayList<String>()

                for(j in cols)
                    rowList.add(j.text().toString())
                listOfValuta.add(rowList)
            }
            println(listOfValuta)
        } catch (e : IOException) {
            e.printStackTrace();
        }
    }
}