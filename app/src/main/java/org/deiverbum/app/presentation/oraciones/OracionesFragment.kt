package org.deiverbum.app.presentation.oraciones

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.deiverbum.app.R
import org.deiverbum.app.databinding.FragmentOracionesBinding
import org.deiverbum.app.presentation.base.BaseHomeFragment
import org.deiverbum.app.presentation.oraciones.adapter.OracionItem
import org.deiverbum.app.presentation.oraciones.adapter.OracionesAdapter


@AndroidEntryPoint
class OracionesFragment : BaseHomeFragment<FragmentOracionesBinding>() {
    private lateinit var mAdapter: OracionesAdapter
    private lateinit var mList: List<OracionItem>

    override fun constructViewBinding(): ViewBinding = FragmentOracionesBinding.inflate(layoutInflater)

    override fun init(viewBinding: ViewBinding) {
        prepareItems()
        initUi()
    }

    private fun initUi() {
        getViewBinding().recyclerView.run {
            //layoutManager = GridLayoutManager(context,3)
            //setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)

            adapter = mAdapter
        }
    }

    private fun prepareItems() {
        //val colorGrupo1 = ContextCompat.getColor(requireActivity(), R.color.color_fondo_grupo1)
        //val colorGrupo2 = ContextCompat.getColor(requireActivity(), R.color.color_fondo_grupo2)
        mList = ArrayList()

        mList = listOf(
            OracionItem(1, "Misterios Gloriosos", "Domingos y Miércoles", R.id.nav_rosario, "raw/rosario.json"),
            OracionItem(2, "Misterios Gozosos", "Lunes y Sábados", R.id.nav_rosario, "raw/rosario.json"),
            OracionItem(3, "Misterios Dolorosos", "Martes y Viernes", R.id.nav_rosario, "raw/rosario.json"),
            OracionItem(4, "Misterios Luminosos", "Jueves", R.id.nav_rosario, "raw/rosario.json"),
            OracionItem(5, "Letanías Lauretanas", "Solamente las Letanías", R.id.nav_letanias, "raw/letanias.json"),
            OracionItem(6, "Ángelus", "Recuerda la Encarnación de Cristo", R.id.nav_angelus, "raw/angelus.json"),
            OracionItem(7, "Regina Coeli", "En lugar del Àngelus, en el tiempo de Pascua", R.id.nav_regina, "raw/regina.json"),
            OracionItem(8, "Via Crucis 2003", "Con meditaciones de Juan Pablo II", R.id.nav_viacrucis2003, "raw/viacrucis2003.json"),
            OracionItem(9, "Via Crucis 2005", "Con meditaciones de Joseph Ratzinger", R.id.nav_viacrucis2005, "raw/viacrucis2005.json")



        )
        mAdapter= OracionesAdapter(mList)
    }
}