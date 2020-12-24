package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.stopwatch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.databinding.StopWatchFragmentBinding
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels.DetailRecipeViewModel

const val CHANNEL_ID = "stopwatch_notification_id"
const val NOTIFICATION_ID = 420

@AndroidEntryPoint
class StopWatchFragment: Fragment() {

    private val detailRecipeViewModel: DetailRecipeViewModel by activityViewModels()
    private lateinit var binding: StopWatchFragmentBinding
    private lateinit var elapsedTime: ElapsedTime
    private lateinit var stopWatchServiceIntent: Intent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.stop_watch_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        stopWatchServiceIntent = Intent(context, StopWatchService::class.java)
        detailRecipeViewModel.durations().observe(viewLifecycleOwner) {
            val bestDuration  = it.firstOrNull()
            if (bestDuration != null) {
                StopWatchService.bestDuration = bestDuration.recipeDuration
            }
        }
        binding.startButton.setOnClickListener {
            activity?.startService(stopWatchServiceIntent)
        }
        binding.pauseButton.setOnClickListener {
            activity?.stopService(stopWatchServiceIntent)
        }
        binding.resetButton.setOnClickListener {
            activity?.stopService(stopWatchServiceIntent)
            binding.elapsedTimeText.text = getString(R.string.init_stopwatch)
        }
        binding.saveButton.setOnClickListener {
            detailRecipeViewModel.saveDuration(elapsedTime.getElapsedTime())
            fragmentManager!!.popBackStack()
        }
    }

    private fun render(intent: Intent) {
        elapsedTime = intent.extras!!.getParcelable(DURATION_KEY)!!
        binding.elapsedTimeText.text = elapsedTime.toString()
    }

    private val broadcastReceiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null && intent.extras != null) {
                render(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activity!!.registerReceiver(
            broadcastReceiver,
            IntentFilter(StopWatchService::class.java.simpleName)
        )
    }

    override fun onStop() {
        activity!!.unregisterReceiver(broadcastReceiver)
        super.onStop()
    }

    override fun onDestroy() {
        activity!!.stopService(stopWatchServiceIntent)
        super.onDestroy()
    }
}