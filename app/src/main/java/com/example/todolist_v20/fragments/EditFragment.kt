package com.example.todolist_v20.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import com.example.todolist_v20.classes.ViewModelMy
import com.example.todolist_v20.dataBase.dbContent.DataBaseManager
import com.example.todolist_v20.dataClass.Data
import com.example.todolist_v20.databinding.FragmentEditBinding
import com.example.todolist_v20.objects.PhotoAndImage
import com.example.todolist_v20.objects.Tags
import com.example.todolist_v20.objects.Variable


@SuppressLint("StaticFieldLeak")
lateinit var bindingEditFragment: FragmentEditBinding
private lateinit var contextEditFragment: Context






class EditFragment : Fragment() {
    private val model: ViewModelMy by activityViewModels()

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK && it.data == null ) {
            bindingEditFragment.imageViewFragmentEdit.setImageURI(PhotoAndImage.uri)
            checkImage()
            PhotoAndImage.sendMessageGallery(contextEditFragment, PhotoAndImage.filePhoto)
            Variable.imgURI = PhotoAndImage.uri.toString()
        }else{
            bindingEditFragment.imageViewFragmentEdit.setImageURI(it.data?.data)
            checkImage()
            checkSavePhoto()
            if (PhotoAndImage.uri == "".toUri() && it.data?.data == null){
                Variable.imgURI = "empty"
            }else {
                Variable.imgURI = it.data?.data.toString()
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("liveFragment", "onAttach")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("liveFragment", "onCreate")
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
        PhotoAndImage.takeFullPhoto(getResult, PhotoAndImage.FILE_NAME, contextEditFragment)
    }

    private fun checkSavePhoto(){
        if (PhotoAndImage.uri != "".toUri()){
            PhotoAndImage.deletePhoto(contextEditFragment, PhotoAndImage.uri)
            PhotoAndImage.sendMessageGallery(contextEditFragment, PhotoAndImage.filePhoto)
            PhotoAndImage.uri = Uri.parse("")

        }
        if (bindingEditFragment.imageViewFragmentEdit.drawable == null) {
            bindingEditFragment.cardViewImageFragmentEdit.visibility = View.GONE
        }
    }


    private fun checkImage() {
        bindingEditFragment.apply {
            if (imageViewFragmentEdit.drawable != null) {
                cardViewImageFragmentEdit.visibility = View.VISIBLE
            }else{
                cardViewImageFragmentEdit.visibility = View.GONE
            }
        }
    }

    private fun checkTag(button: RadioButton, tag: String){
        Tags.tagSelectEdit(button, tag, bindingEditFragment.textViewTagCardEditFragment)
    }

    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(contextEditFragment)) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), 2909
                )
            } else {
                Log.d("idDir", "12")

            }
        } else {
            Log.d("idDir", "11")

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            2909 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    takeAndSavePhoto()
                } else {
                    Log.e("idDir", "Denied")
                }
                return
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbManager = DataBaseManager(contextEditFragment)
        dbManager.openDataBase()
        val hideKeyboard = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        Log.d("liveFragment", "onViewCreated")


        bindingEditFragment.apply {

            textViewTagCardEditFragment.setOnClickListener {

                radioGroupTegEditFragment.clearCheck()
                textViewTagCardEditFragment.text = "Нет фильтра"
                Tags.dbTag = "empty"

                if (scrollTagWindowEditFragment.visibility == View.GONE) {
                    scrollTagWindowEditFragment.visibility = View.VISIBLE
                }else{
                    scrollTagWindowEditFragment.visibility = View.GONE
                }
            }


            floatingActionButtonAddImageFragmentEdit.setOnClickListener {
                if (cardViewImageFragmentEdit.visibility == View.GONE) {
                    checkPermission()
                }else{
                    cardViewImageFragmentEdit.visibility = View.GONE
                    imageViewFragmentEdit.setImageDrawable(null)
                    checkSavePhoto()
                    PhotoAndImage.uri = Uri.parse("")
                    Variable.imgURI = "empty"
                }
            }




            floatingActionButtonAddImageFragmentEdit.setOnLongClickListener {
                if (cardViewImageFragmentEdit.visibility == View.GONE) {
                    PhotoAndImage.chooseImageGallery(getResult)
                }else{
                    cardViewImageFragmentEdit.visibility = View.GONE
                    imageViewFragmentEdit.setImageDrawable(null)
                    Variable.imgURI = "empty"
                }
                true
            }

            radioButtonEdritFragmentTagHome.setOnClickListener {
                checkTag(radioButtonEdritFragmentTagHome, Tags.homeTag)
            }
            radioButtonEdritFragmentTagShop.setOnClickListener {
                checkTag(radioButtonEdritFragmentTagShop, Tags.shopTag)
            }
            radioButtonEdritFragmentTagBank.setOnClickListener {
                checkTag(radioButtonEdritFragmentTagBank, Tags.bankTag)
            }
            radioButtonEdritFragmentTagWork.setOnClickListener {
                checkTag(radioButtonEdritFragmentTagWork, Tags.workTag)
            }
            radioButtonEdritFragmentTagWeekend.setOnClickListener {
                checkTag(radioButtonEdritFragmentTagWeekend, Tags.weekendTag)
            }
            radioButtonEdritFragmentTagSport.setOnClickListener {
                checkTag(radioButtonEdritFragmentTagSport, Tags.sportTag)
            }

        //Нажатие на editText для скрытия клавиатуры
        editTextEditFragmentSubtitle.setOnClickListener {
            hideKeyboard.hideSoftInputFromWindow(view.windowToken, 0)
        }

        //Кнопка для сохранения заметки
        buttonSaveEditFragment.setOnClickListener{

            val title = editTextEditFragmentTitle.text
            val subtitle = editTextEditFragmentSubtitle.text

            if (title.isEmpty()){
                Toast.makeText(contextEditFragment, "Введите заголовок", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(contextEditFragment, "Сохраняем", Toast.LENGTH_SHORT).show()
                hideKeyboard.hideSoftInputFromWindow(view.windowToken, 0)

                dbManager.insertToDataBase(
                    title.toString(),subtitle.toString(), Tags.dbTag, Variable.imgURI
                )

                PhotoAndImage.uri = Uri.parse("")
                Variable.imgURI = "empty"
                Tags.dbTag = "empty"
                title.clear()
                subtitle.clear()

                cardViewImageFragmentEdit.visibility = View.GONE
                imageViewFragmentEdit.setImageDrawable(null)
                textViewTagCardEditFragment.text = "Нет фильтра"
                scrollTagWindowEditFragment.visibility = View.GONE
                radioGroupTegEditFragment.clearCheck()

            }
            model.plant.value = Data("Info22", "UseCase22")
        }
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