package link.jingweih.jingwei.core.framework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<VM : ViewBinding?> : BottomSheetDialogFragment() {
    private var _binding: VM? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = createFragmentViewBinding(inflater, container)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    protected abstract fun initView()

    protected abstract fun initObserver()

    protected abstract fun createFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VM

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    val binding get() = (_binding!! as VM)
}