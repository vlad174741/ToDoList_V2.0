package com.example.todolist_v20.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_v20.classes.EditActivity
import com.example.todolist_v20.R
import com.example.todolist_v20.objects.Variable
import com.example.todolist_v20.dataBase.dbContent.MyIntentConstant
import com.example.todolist_v20.dataClass.DataRcView
import com.example.todolist_v20.databinding.PatternForRecyclerViewBinding
import com.example.todolist_v20.fragments.MainFragment
import com.example.todolist_v20.fragments.dbManager

//private val handler = Handler(Looper.getMainLooper())


class RecyclerViewAdapter(listMain:ArrayList<DataRcView>, private var contextRC: Context)
    :RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderAdapter>() {

    private var listArray = listMain
    private var isEnable = false
    val itemSelectList = mutableListOf<Int>()



    class ViewHolderAdapter(item: View):RecyclerView.ViewHolder(item) {

        var bindingRcView = PatternForRecyclerViewBinding.bind(item)

        fun holderRc(list: DataRcView){
            bindingRcView.textViewSubtitleRcView.text = list.subtitle
            bindingRcView.textViewTitleRcView.text = list.title
            Log.d("rcState", "ViewHolderAdapter/fun holderRc")

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAdapter {
        Log.d("rcState", "onCreateViewHolder")

        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.pattern_for_recycler_view,parent,false)
        return ViewHolderAdapter(inflate)

    }

    override fun onBindViewHolder(holder: ViewHolderAdapter, position: Int) {
        Log.d("rcState", "${Variable.prevPositionRcView}")

        holder.holderRc(listArray[position])
        val pos = holder.adapterPosition
        val itemList = listArray[position]
        holder.bindingRcView.cardRedactItemRc.visibility = View.GONE





        if (!isEnable){
            if(itemSelectList.isEmpty()) {
                holder.bindingRcView.imageViewDelete.setImageResource(R.drawable.ic_delete)
            }

        }else {
            if(itemSelectList.isEmpty()) {


                holder.bindingRcView.imageViewDelete.setImageResource(R.drawable.ic_unchecked)
                holder.bindingRcView.cardRedactItemRc.visibility = View.GONE

            }
        }


        //Обрабочик долгого нажатия на элемент RecyclerView
        // (запускае выбор элементов для множественного удаления)
        holder.itemView.setOnLongClickListener{

            if(isEnable){
                itemSelectList.clear()
                itemList.select = false
                if (itemSelectList.isEmpty()){ isEnable = false }
                Variable.check = 0
                MainFragment().deleteButton()
                notifyDataSetChanged()

            }else {
                Log.d("idItemSelect", "$itemSelectList")
                Variable.check = 1
                MainFragment().deleteButton()
                isEnable = true
                notifyDataSetChanged()
            }
            true
        }

        //Обрабочик нажатия на элемент RecyclerView (открывает окно быстрого просмотра
        // содержимого заметки, а так же в случае множественного удаления
        // выделяет и снимант выделение с элемента)
        holder.itemView.setOnClickListener{
            Log.d("idItemSelect", "$itemSelectList")

            if (!isEnable){

                if(holder.bindingRcView.textViewSubtitleRcView.text.isEmpty()){
                    Toast.makeText(contextRC, "Пусто",Toast.LENGTH_SHORT).show()

                }else {

                    if (holder.bindingRcView.cardRedactItemRc.visibility == View.VISIBLE) {
                        holder.bindingRcView.cardRedactItemRc.visibility = View.GONE
                        Variable.prevPositionRcView = -1

                    } else {
                        if (Variable.prevPositionRcView!=-1) {
                            notifyItemChanged(Variable.prevPositionRcView)
                            holder.bindingRcView.cardRedactItemRc.visibility = View.VISIBLE
                            Variable.prevPositionRcView = pos
                        }else{
                            holder.bindingRcView.cardRedactItemRc.visibility = View.VISIBLE
                            Variable.prevPositionRcView = pos
                        }
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
                        notifyDataSetChanged()
                    }
                } else if (isEnable) {

                    selectItem(itemList, pos)
                    holder.bindingRcView.imageViewDelete.setImageResource(R.drawable.ic_check)
                }
                MainFragment().deleteButton()
            }
        }

        //Кнопка удаления заметки
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
            MainFragment().deleteButton()


        }

        //Кнопка редактирования заметки
        holder.bindingRcView.imageViewAdd.setOnClickListener {

            val editActivity = Intent(contextRC, EditActivity::class.java).apply {

                putExtra(MyIntentConstant.INTENT_TITLE_KEY,itemList.title)
                putExtra(MyIntentConstant.INTENT_SUBTITLE_KEY,itemList.subtitle)
                putExtra(MyIntentConstant.INTENT_TAG_KEY,itemList.tag)
                putExtra(MyIntentConstant.INTENT_URL_KEY,itemList.uri)
                putExtra(MyIntentConstant.INTENT_ID_KEY,itemList.idItem)

            }
            contextRC.startActivity(editActivity)
        }
    }

    //Функция для передачи колличества элементов в списке
    override fun getItemCount(): Int { return listArray.size }

    //Функция для выделения элемента из списка
    private fun selectItem(item: DataRcView, position: Int) {
        isEnable=true
        itemSelectList.add(position)
        item.select = true
    }


    //Функция для снятия выделенных элементов списка
    fun clearItemSelect(){
        itemSelectList.clear()
        if (itemSelectList.isEmpty()){ isEnable = false }
        Variable.check = 0
        MainFragment().deleteButton()
        notifyDataSetChanged()
    }

    //Функция для удаления выделенных элементов списка
    @SuppressLint("NotifyDataSetChanged")
    fun deleteSelectItems() {
        Variable.prevPositionRcView = -1
        for(item in itemSelectList){
            Log.d("idItemSelect", "$item")
            dbManager.removeItemToDb(listArray[item].idItem. toString())
        }
        listArray.removeAll{item -> item.select}
        isEnable = false
        itemSelectList.clear()
        MainFragment().onResume()
        notifyDataSetChanged()
    }

    //Функция для удаления единичного элемента списка
    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(position: Int){
        Variable.prevPositionRcView = -1
        dbManager.removeItemToDb(listArray[position].idItem. toString())
        listArray.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    //Функция для обновления адаптора
    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(listItems: List<DataRcView>){
        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()

    }

    //Функция для вызова диалогового окна удаления элемента
    @SuppressLint("NotifyDataSetChanged")
    fun alertDeleteDialog(position: Int){
        val alertDialog = AlertDialog.Builder(contextRC)
        alertDialog.setTitle("Удалить")
        alertDialog.setMessage("Действительно хотите удалить?")
        alertDialog.setPositiveButton("Удалить"){_,_ ->
            if (itemSelectList.isEmpty()){ deleteItem(position) }
            else{ deleteSelectItems(); Variable.check = 0; MainFragment().deleteButton() }
        }
        alertDialog.setNegativeButton("Нет"){_,_ -> }
        alertDialog.show()
    }
}