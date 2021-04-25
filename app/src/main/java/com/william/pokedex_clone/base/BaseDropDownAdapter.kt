package com.william.pokedex_clone.base

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import com.william.pokedex_clone.R

class BaseDropDownAdapter : ArrayAdapter<Any?> {

    constructor(context: Context, resource: Int, datas: List<Any?>) : super(context, resource, datas)

    constructor(context: Context, resource: Int, datas: Array<Any?>) : super(
        context,
        resource,
        datas
    )

//    override fun getView(
//        position: Int,
//        convertView: View?,
//        parent: ViewGroup
//    ): View {
//
//        val data = getItem(position)
//
//        var v = convertView
//        if (v == null) {
//            v = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
//        }
//
//        v?.findViewById<CheckedTextView>(android.R.id.text1)?.text = data?.toString()
//
//        return v!!
//    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val data = getItem(position)

        var v = convertView
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        }

        v?.findViewById<CheckedTextView>(android.R.id.text1)?.text = data?.toString()

        return v!!
    }
}