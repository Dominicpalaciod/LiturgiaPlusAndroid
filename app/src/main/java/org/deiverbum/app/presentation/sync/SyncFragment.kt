package org.deiverbum.app.presentation.sync

import android.graphics.Typeface
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.deiverbum.app.R
import org.deiverbum.app.databinding.FragmentSyncBinding
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.presentation.base.BaseFragment
import org.deiverbum.app.utils.Constants
import org.deiverbum.app.utils.Utils
import org.deiverbum.app.utils.ZoomTextView
import java.util.*

/**
 * <p>
 *     Este fragmento maneja la lógica de Sincronización,
 *     definida del siguiente modo:
 * </p>
 *
 * <p>
 *     1. Cuando se instala por primera vez la aplicación,
 *        desde AcceptanceFragmentDialog <br />
 *    a. Se invocará al método initialSync() de SyncViewModel cuando el usuario
 *       pulse en el botón de aceptación. <br />
 *    b. Seguidamente se observará a getInitialSyncStatus() de SyncViewModel,
 *       si devuelve un valor mayor que 0 se creará una entrada en SharedPreferences
 *       con clave initialSync y valor true.
 * </p>
 *
 * <p>
 *     2. En lo adelante, cada vez que se inicie la aplicación,
 *        se verificará desde el método setConfiguration en HomeFragment
 *        el estado de initialSync en SharedPreferences. <br />
 *        Si es false: <br />
 *      a. Se llamará a initialSync() de SyncViewModel. <br />
 *      b. Se observará a getInitialSyncStatus() y si devuelve un valor mayor que 0
 *         se actualizará la entrada initialSync de SharedPreferences a true.
 * </p>
 *
 * <p>
 *     3. En el repositorio de sincronización existe también un método getFromFirebase
 *        el cual sirve para buscar las fechas de los próximos siete días en Firebase.
 *        Es un método alternativo, para usar en caso de que la llamada a initialSync falle
 *        por algún motivo: el servidor está offline en ese momento, u otro.
 * </p>
 */
@AndroidEntryPoint
class SyncFragment : BaseFragment<FragmentSyncBinding>() {
    private lateinit var syncRequest: SyncRequest
    private val mViewModel: SyncViewModel by viewModels()
    private lateinit var mTextVieww: ZoomTextView
    private var progressBar: ProgressBar? = null


    override fun constructViewBinding(): ViewBinding = FragmentSyncBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        setConfiguration()
        setMenu()
        //mViewModel.loadData(syncRequest)
        //fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect { state ->
                    when (state) {
                        is SyncViewModel.SyncUiState.Loaded -> onLoaded(state.itemState)
                        is SyncViewModel.SyncUiState.Error -> showError(state.message)
                        else -> showLoading()
                    }
                }
            }
        }
    }

    private fun onLoaded(syncItemUiState: SyncItemUiState) {
        syncItemUiState.run {
            getViewBinding().progressBar.visibility = View.GONE
            mTextVieww.text = Utils.fromHtml(allData.dataForView.toString())//.dataForView

        }
    }

    private fun showLoading() {
        mTextVieww.text = Constants.PACIENCIA

    }

    private fun showError(stringRes: String) {
        mTextVieww.text = stringRes
        Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
    }

    private fun setMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        val navController =
                            NavHostFragment.findNavController(requireParentFragment())
                        navController.popBackStack()
                        true
                    }

                    else -> {
                        val navController =
                            NavHostFragment.findNavController(requireParentFragment())
                        NavigationUI.onNavDestinationSelected(menuItem, navController)
                    }
                }

            }
        }, viewLifecycleOwner)
    }

    private fun setConfiguration() {
        mTextVieww = getViewBinding().include.tvZoomable
        progressBar = getViewBinding().progressBar
        val sp =
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
        val fontSize = sp?.getString("font_size", "18")!!.toFloat()
        val fontFamily = String.format(
            Locale("es"),
            "fonts/%s",
            sp.getString("font_name", "robotoslab_regular.ttf")
        )
        val tf = Typeface.createFromAsset(requireActivity().assets, fontFamily)
        mTextVieww.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        mTextVieww.typeface = tf
        //hasInvitatory = sp.getBoolean("invitatorio", false)
        //isVoiceOn = sp.getBoolean("voice", true)
        syncRequest =
            SyncRequest(pickOutDate(), 1, isNightMode(), false)

        //pickOutDate()
    }



    private fun pickOutDate(): Int {
        val bundle = arguments
        val mDate = if (bundle != null && bundle.containsKey("FECHA")) {
            bundle.getInt("FECHA")
        } else {
            Utils.getHoy().toInt()
        }
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        Objects.requireNonNull<ActionBar?>(actionBar).subtitle =
            Utils.getTitleDate(mDate.toString())
        return mDate
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (mActionMode != null) {
            mActionMode!!.finish()
        }
        //binding = null
    }
}

/*Fragment() {
    private var mViewModel: SyncViewModelBis? = null
    private var binding: FragmentSyncBinding? = null
    private var mTextView: ZoomTextView? = null
    private var progressBar: ProgressBar? = null
    private var mButton: ExtendedFloatingActionButton? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentSyncBinding.inflate(inflater, container, false)
        inflater.inflate(R.layout.seekbar, container, false)
        val root: View = binding!!.root
        setConfiguration()
        return root
    }

    private fun setConfiguration() {
        mViewModel = ViewModelProvider(this).get(SyncViewModelBis::class.java)
        mTextView = binding!!.include.tvZoomable
        progressBar = binding!!.progressBar
        val prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        val fontSize = prefs.getString("font_size", "18")!!.toFloat()
        val fontFamily = String.format(
            Locale("es"),
            "fonts/%s",
            prefs.getString("font_name", "robotoslab_regular.ttf")
        )
        val tf = Typeface.createFromAsset(requireActivity().assets, fontFamily)
        mTextView!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        mTextView!!.typeface = tf
        mButton = binding!!.include.btnEmail
        mButton!!.visibility = View.GONE
        mButton!!.setOnClickListener { v: View? ->
            mViewModel!!.launchSyncWorker()
            mButton!!.visibility = View.GONE
            observeData()
        }
        if (!isWorkScheduled) {
            progressBar!!.visibility = View.GONE
            mTextView!!.text = Utils.fromHtml(
                SyncStatus().getLastUpdate(
                    isNightMode
                )
            )
            mButton!!.setIconResource(R.drawable.ic_refresh_black_24dp)
            mButton!!.text = Constants.SYNC_LABEL
            mButton!!.visibility = View.VISIBLE
        } else {
            observeData()
        }
    }

    fun observeData() {
        mTextView!!.text = Constants.PACIENCIA
        mViewModel!!.observable.observe(
            viewLifecycleOwner
        ) { data: SyncStatus? ->
            progressBar!!.visibility = View.GONE
            if (data != null) {
                mTextView!!.text = Utils.fromHtml(data.getAll(isNightMode))
            } else {
                mTextView!!.text = Utils.fromHtml(
                    SyncStatus().getLastUpdate(
                        isNightMode
                    )
                )
            }
        }
    }

    private val isWorkScheduled: Boolean
        private get() {
            val instance =
                WorkManager.getInstance(Objects.requireNonNull(requireActivity()).applicationContext)
            val statuses = instance.getWorkInfosByTag("TAG_SYNC_DATA")
            return try {
                var running = false
                val workInfoList = statuses.get()
                for (workInfo in workInfoList) {
                    val state = workInfo.state
                    running =
                        (state == WorkInfo.State.RUNNING) or (state == WorkInfo.State.ENQUEUED)
                }
                running
            } catch (e: ExecutionException) {
                false
            } catch (e: InterruptedException) {
                false
            }
        }
    val isNightMode: Boolean
        get() {
            val nightModeFlags =
                requireActivity().applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            return nightModeFlags == Configuration.UI_MODE_NIGHT_YES
        }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mActionMode != null) {
            mActionMode!!.finish()
        }
        binding = null
    }

    companion object {
        var mActionMode: ActionMode? = null
    }
}*/