package com.example.todolist_v20.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.todolist_v20.objects.PhotoAndImage
import com.example.todolist_v20.objects.Variable
import com.example.todolist_v20.classes.ViewModelMy
import com.example.todolist_v20.dataBase.dbContent.DataBaseManager
import com.example.todolist_v20.dataClass.Data
import com.example.todolist_v20.databinding.FragmentEditBinding
import java.io.File


@SuppressLint("StaticFieldLeak")
lateinit var bindingEditFragment: FragmentEditBinding
private lateinit var filePhoto: File
private lateinit var outputFileUri: Uri
private lateinit var contextEditFragment: Context
private const val FILE_NAME = "photo.jpg"




class EditFragment : Fragment() {
    private val model: ViewModelMy by activityViewModels()

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ it ->
        if (it.resultCode == Activity.RESULT_OK && it.data == null ) {
            bindingEditFragment.imageViewFragmentEdit.setImageURI(outputFileUri)
            Variable.imgURI = outputFileUri.toString()
        }else{
            bindingEditFragment.imageViewFragmentEdit.setImageURI(it.data?.data)
            checkSavePhoto()

            Variable.imgURI = it.data?.data.toString()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("liveFragment", "onAttach")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("liveFragment", "onCreate")
        outputFileUri = Uri.parse("")
        contextEditFragment = activity as AppCompatActivity

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        bindingEditFragment = FragmentEditBinding.inflate(inflater, container, false)
        Log.d("liveFragment", "onCreateView")

        return bindingEditFragment.root
    }


    private fun takeAndSavePhoto(){
        filePhoto = PhotoAndImage.getPhotoFile(FILE_NAME, contextEditFragment)
        outputFileUri = Uri.fromFile(filePhoto)
        PhotoAndImage.takeFullPhoto(outputFileUri,getResult)
    }

    private fun checkSavePhoto(){
        if (outputFileUri != "".toUri()){ PhotoAndImage.deletePhoto(contextEditFragment,
            outputFileUri)}
        if (bindingEditFragment.imageViewFragmentEdit.drawable == null) {
            bindingEditFragment.cardViewImageFragmentEdit.visibility = View.GONE
        }
    }

    private fun tagCheck(button: RadioButton, tag: String){
        if (button.isChecked) {
            Variable.dbTag = tag+ Variable.username
            bindingEditFragment.textViewTagCardEditFragment.text = button.text
        }
    }







    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbManager = DataBaseManager(contextEditFragment)
        dbManager.openDataBase()
        val hideKeyboard = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        Log.d("liveFragment", "onViewCreated")


        bindingEditFragment.textViewTagCardEditFragment.setOnClickListener {

            bindingEditFragment.radioGroupTegEditFragment.clearCheck()
            bindingEditFragment.textViewTagCardEditFragment.text = "Нет фильтра"
            Variable.dbTag = "empty"

            if (bindingEditFragment.scrollTagWindowEditFragment.visibility == View.GONE) {
                bindingEditFragment.scrollTagWindowEditFragment.visibility = View.VISIBLE
            }else{
                bindingEditFragment.scrollTagWindowEditFragment.visibility = View.GONE
            }


        }


        bindingEditFragment.floatingActionButtonAddImageFragmentEdit.setOnClickListener {
            if (bindingEditFragment.cardViewImageFragmentEdit.visibility == View.GONE) {
                takeAndSavePhoto()
                bindingEditFragment.cardViewImageFragmentEdit.visibility = View.VISIBLE
            }else{
                bindingEditFragment.cardViewImageFragmentEdit.visibility = View.GONE
                bindingEditFragment.imageViewFragmentEdit.setImageDrawable(null)
                outputFileUri = Uri.parse("")
            }
        }

        bindingEditFragment.floatingActionButtonAddImageFragmentEdit.setOnLongClickListener {
            if (bindingEditFragment.cardViewImageFragmentEdit.visibility == View.GONE) {

                PhotoAndImage.chooseImageGallery(getResult)
                bindingEditFragment.cardViewImageFragmentEdit.visibility = View.VISIBLE
            }else{
                bindingEditFragment.cardViewImageFragmentEdit.visibility = View.GONE

            }

            true
        }


        bindingEditFragment.apply {

            radioButtonEdritFragmentTagHome.setOnClickListener {
                tagCheck(radioButtonEdritFragmentTagHome, Variable.homeTag)
            }
            radioButtonEdritFragmentTagShop.setOnClickListener {
                tagCheck(radioButtonEdritFragmentTagShop, Variable.shopTag)
            }
            radioButtonEdritFragmentTagBank.setOnClickListener {
                tagCheck(radioButtonEdritFragmentTagBank, Variable.bankTag)
            }
            radioButtonEdritFragmentTagWork.setOnClickListener {
                tagCheck(radioButtonEdritFragmentTagWork, Variable.workTag)
            }
            radioButtonEdritFragmentTagWeekend.setOnClickListener {
                tagCheck(radioButtonEdritFragmentTagWeekend, Variable.weekendTag)
            }
            radioButtonEdritFragmentTagSport.setOnClickListener {
                tagCheck(radioButtonEdritFragmentTagSport, Variable.sportTag)
            }
        }

        bindingEditFragment.editTextEditFragmentSubtitle.setOnClickListener {
            hideKeyboard.hideSoftInputFromWindow(view.windowToken, 0)
        }

        bindingEditFragment.buttonSaveEditFragment.setOnClickListener{

            val title = bindingEditFragment.editTextEditFragmentTitle.text
            val subtitle = bindingEditFragment.editTextEditFragmentSubtitle.text

            if (title.isEmpty()){
                Toast.makeText(contextEditFragment, "Введите заголовок", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(contextEditFragment, "Сохраняем", Toast.LENGTH_SHORT).show()
                hideKeyboard.hideSoftInputFromWindow(view.windowToken, 0)
                Variable.tag = title.toString()

                dbManager.insertToDataBase(title.toString(),subtitle.toString(),
                    Variable.dbTag, Variable.imgURI)

                outputFileUri = Uri.parse("")
                bindingEditFragment.cardViewImageFragmentEdit.visibility = View.GONE
                bindingEditFragment.imageViewFragmentEdit.setImageDrawable(null)
                Variable.dbTag = "empty"
                bindingEditFragment.textViewTagCardEditFragment.text = "Нет фильтра"
                bindingEditFragment.scrollTagWindowEditFragment.visibility = View.GONE
                title.clear()
                subtitle.clear()
                bindingEditFragment.radioGroupTegEditFragment.clearCheck()



            }








            model.plant.value = Data("Info22", "UseCase22")
        }



    }



    override fun onStart() {
        super.onStart()
        Log.d("liveFragment", "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.d("liveFragment", "onResume")


    }

    override fun onPause() {
        super.onPause()
        Log.d("liveFragment", "onPause")

    }

    override fun onStop() {
        super.onStop()
        Log.d("liveFragment", "onStop")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("liveFragment", "onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("liveFragment", "onDestroy")
        checkSavePhoto()

    }


    override fun onDetach() {
        super.onDetach()
        Log.d("liveFragment", "onDetach")

    }

    companion object {
        @JvmStatic
        fun newInstance() = EditFragment()
    }
}