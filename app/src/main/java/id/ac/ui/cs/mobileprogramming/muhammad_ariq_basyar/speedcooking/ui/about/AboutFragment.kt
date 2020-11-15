package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R

@AndroidEntryPoint
class AboutFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }
}