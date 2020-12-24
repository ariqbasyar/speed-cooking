package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.databinding.FragmentAboutBinding
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels.AboutViewModel

@AndroidEntryPoint
class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private val aboutViewModel: AboutViewModel by lazy {
        ViewModelProviders.of(this).get(AboutViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_about,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        aboutViewModel.isFetched.observe(viewLifecycleOwner) { isFetched ->
            binding.progressBar.visibility = booleanToViewVisibility(isFetched)
            binding.textAbout.visibility = booleanToViewVisibility(!isFetched)
        }
        aboutViewModel.aboutMe.observe(viewLifecycleOwner) { aboutMe ->
            binding.textAbout.text = aboutMe
        }
    }

    private fun booleanToViewVisibility(gone: Boolean): Int {
        return if (gone) View.GONE else View.VISIBLE
    }
}
