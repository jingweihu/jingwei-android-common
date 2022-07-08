package link.jingweih.android.identity.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import link.jingweih.android.identity.databinding.FragmentResetPasswordBinding
import link.jingweih.jingwei.core.framework.ui.BaseFragment

class FirebaseResetPasswordFragment : BaseFragment<FragmentResetPasswordBinding>() {

    override fun initView() {

    }

    override fun initObserver() {
        TODO("Not yet implemented")
    }

    override fun createFragmentViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentResetPasswordBinding {
        return FragmentResetPasswordBinding.inflate(inflater, container, false)
    }
}