package com.example.todolist_v20.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_v20.EditActivity
import com.example.todolist_v20.R
import com.example.todolist_v20.Variable
import com.example.todolist_v20.dataBase.dbContent.MyIntentConstant
import com.example.todolist_v20.dataClass.DataRcView
import com.example.todolist_v20.databinding.PatternForRecyclerViewBinding
import com.example.todolist_v20.fragments.MainFragment
import com.example.todolist_v20.fragments.dbManager

private val handler = Handler(Looper.getMainLooper())


class RecyclerViewAdapter(listMain:ArrayList<DataRcView>, private var contextRC: Context):RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderAdapter>() {

    private var listArray = listMain


    val itemSelectList = mutableListOf<Int>()
    private var isEnable = false


    class ViewHolderAdapter(item: View):RecyclerView.ViewHolder(item) {
        var bindingRcView = PatternForRecyclerViewBinding.bind(item)

        fun holderRc(list: DataRcView){
            bindingRcView.textViewSubtitleRcView.text = list.subtitle
            bindingRcView.textViewTitleRcView.text = list.title


        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAdapter {

        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.pattern_for_recycler_view,parent,false)
        return ViewHolderAdapter(inflate)

    }

    override fun onBindViewHolder(holder: ViewHolderAdapter, position: Int) {

        holder.holderRc(listArray[position])
        val pos = holder.adapterPosition
        val itemList = listArray[position]
        holder.bindingRcView.cardRedactItemRc.visibility = View.GONE


        if (!isEnable){
            holder.bindingRcView.imageViewDelete.setImageResource(R.drawable.ic_delete)

        }else {
            holder.bindingRcView.imageViewDelete.setImageResource(R.drawable.ic_unchecked)
            holder.bindingRcView.cardRedactItemRc.visibility = View.GONE
        }



        holder.itemView.setOnLongClickListener{
            Variable.tag = ""

            if(isEnable){

                itemSelectList.clear()
                itemList.select = false
                if (itemSelectList.isEmpty()){ isEnable = false }
                Variable.check = 0
                MainFragment().onResume()
                notifyDataSetChanged()

            }else {
                Log.d("idItemSelect", "$itemSelectList")
                Variable.check = 1
                MainFragment().onResume()
                isEnable = true
                notifyDataSetChanged()
            }
            true

        }

        holder.itemView.setOnClickListener{
            Log.d("idItemSelect", "$itemSelectList")

            if (!isEnable){

                if(holder.bindingRcView.textViewSubtitleRcView.text.isEmpty()){
                    Toast.makeText(contextRC, "Пусто",Toast.LENGTH_SHORT).show()

                }else {

                    if (holder.bindingRcView.cardRedactItemRc.visibility == View.VISIBLE) {
                        holder.bindingRcView.cardRedactItemRc.visibility = View.GONE
                    } else {
                        holder.bindingRcView.cardRedactItemRc.visibility = View.VISIBLE

                    }
                }
            }else {
                if (itemSelectList.contains(position)) {

                    itemSelectList.remove(pos)
                    holder.bindingRcView.imageViewDelete.setImageResource(R.drawable.ic_unchecked)
                    itemList.select = false
                    if (itemSelectList.isEmpty()) {
                        isEnable = false
                        Variable.check = 0
                        MainFragment().onResume()
                        notifyDataSetChanged()
                    }


                } else if (isEnable) {
                    selectItem(itemList, pos)
                    holder.bindingRcView.imageViewDelete.setImageResource(R.drawable.ic_check)

                }
            }

        }

        holder.bindingRcView.imageViewDelete.setOnClickListener{

            if(!isEnable){
                alertDeleteDialog(pos)
            }else{
                if (itemSelectList.contains(position)){

                    itemSelectList.remove(pos)
                    holder.bindingRcView.imageViewDelete.setImageResource(R.drawable.ic_unchecked)
                    itemList.select = false
                    if (itemSelectList.isEmpty()){
                        isEnable = false
                        Variable.check = 0
                        MainFragment().onResume()
                        notifyDataSetChanged()
                    }


                }else if (isEnable){ selectItem(itemList, pos)
                    holder.bindingRcView.imageViewDelete.setImageResource(R.drawable.ic_check)

                }
            }

        }

        holder.bindingRcView.imageViewAdd.setOnClickListener {

            var editActivity = Intent(contextRC, EditActivity::class.java).apply {

                putExtra(MyIntentConstant.INTENT_TITLE_KEY,itemList.title)
                putExtra(MyIntentConstant.INTENT_SUBTITLE_KEY,itemList.subtitle)
                putExtra(MyIntentConstant.INTENT_ID_KEY,itemList.idItem)

            }
            contextRC.startActivity(editActivity)


        }



    }

    override fun getItemCount(): Int {

        return listArray.size
    }


    private fun selectItem(item: DataRcView, position: Int) {
        isEnable=true
        itemSelectList.add(position)
        item.select = true

    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteSelectItems() {

        for(item in itemSelectList){
            Log.d("idItemSelect", "$item")
            dbManager.removeItemToDb(listArray[item].idItem. toString())
        }
        listArray.removeAll{item -> item.select}
        isEnable = false
        itemSelectList.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(position: Int){


        dbManager.removeItemToDb(listArray[position].idItem. toString())
        listArray.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(listItems: List<DataRcView>){

        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    fun alertDeleteDialog(position: Int){
        val alertDialog = AlertDialog.Builder(contextRC)
        alertDialog.setTitle("Удалить")
        alertDialog.setMessage("Действительно хотите удалить?")
        alertDialog.setPositiveButton("Удалить"){_,_ ->
            if (itemSelectList.isEmpty()){ deleteItem(position)
                handler.postDelayed({notifyDataSetChanged()},1000) }
            else{ deleteSelectItems(); Variable.check = 0; MainFragment().onResume() }
        }
        alertDialog.setNegativeButton("Нет"){_,_ -> }
        alertDialog.show()
    }
}