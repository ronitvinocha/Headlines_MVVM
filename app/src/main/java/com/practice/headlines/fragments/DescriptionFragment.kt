package com.practice.headlines.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.practice.headlines.MyViewModelFactory
import com.practice.headlines.Myapplication
import com.practice.headlines.R
import com.practice.headlines.databinding.FragmentDescriptionBinding
import com.practice.headlines.model.Articles
import com.practice.headlines.model.DownloadStatus
import com.practice.headlines.util.Status
import com.practice.headlines.util.getParsedDateString
import com.practice.headlines.util.gone
import com.practice.headlines.viewmodel.MainViewModel
import com.squareup.picasso.Picasso

class DescriptionFragment : Fragment() {
    private lateinit var picasso:Picasso
    private lateinit var binding:FragmentDescriptionBinding
    private  val mainViewModel: MainViewModel by activityViewModels{ MyViewModelFactory(myApplication.coreComponent) }
    private lateinit var myApplication:Myapplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myApplication=this.activity?.application as Myapplication
        picasso= myApplication.coreComponent.picaaso()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentDescriptionBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val safeArgs: DescriptionFragmentArgs by navArgs()
        picasso.load(safeArgs.article.urlToImage).into(binding.image)
        binding.newstitle.text=safeArgs.article.title
        safeArgs.article.publishedAt?.let {
            binding.date.text= getParsedDateString(it)
        }?:run{
            binding.date.gone()
        }
        safeArgs.article.content?.let {
            binding.description.text=it
        }?: kotlin.run {
            binding.description.gone()
        }
        safeArgs.article.source?.name?.let {
            binding.source.text=it
        }?: kotlin.run {
            binding.description.gone()
        }
        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }
        if(safeArgs.article.downloaded==DownloadStatus.DOWNLOAD){
            binding.isDownloaded.setBackgroundResource(R.drawable.savebutton)
            binding.savebutton.isEnabled=false
            binding.savebutton.setText("Saved")
        }else{
            binding.isDownloaded.setBackgroundResource(R.drawable.ic_save_description)
            binding.savebutton.isEnabled=true
            binding.savebutton.setText("Save")
        }
        binding.savebutton.setOnClickListener {
            onSaveClick(safeArgs.article)
        }
    }

    fun onSaveClick(article: Articles) {
        mainViewModel.saveArticle(article).observe(viewLifecycleOwner, Observer {
            it?.let {resource->
                when(resource.status){
                    Status.SUCCESS -> {
                        article.downloaded=DownloadStatus.DOWNLOAD
                        binding.isDownloaded.setBackgroundResource(R.drawable.savebutton)
                        binding.savebutton.isEnabled=false
                        binding.savebutton.setText("Saved")
                    }
                    Status.ERROR -> {
                        resource.message?.let { it1 -> Log.d("😀", it1) }
                        article.downloaded=DownloadStatus.NDTDOWNLOAD
                        binding.isDownloaded.setBackgroundResource(R.drawable.savebutton)
                        binding.savebutton.isEnabled=false
                        binding.savebutton.setText("Saved")
                    }
                    Status.LOADING -> {
                        article.downloaded=DownloadStatus.LOADING
                    }
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = DescriptionFragment()
    }
}