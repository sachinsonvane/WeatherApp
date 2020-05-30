package com.cname.weatherapp.view
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.cname.weatherapp.model.City
import com.cname.weatherapp.R;

class CityListAdapter(val context: Context, var cityList:List<City>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.custome_spinner, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.cityNameTxt.text = cityList.get(position).name

        return view
    }

    override fun getItem(position: Int): Any? {
        return cityList[position];
    }

    override fun getCount(): Int {
        return cityList.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val cityNameTxt: TextView

        init {
            cityNameTxt = row?.findViewById(R.id.spinnerCityNameTxt) as TextView
        }
    }
}

