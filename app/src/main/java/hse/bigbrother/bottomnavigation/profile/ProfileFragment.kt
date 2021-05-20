package hse.bigbrother.bottomnavigation.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hse.bigbrother.BigBrotherApplication
import hse.bigbrother.MainActivity
import hse.bigbrother.R
import hse.bigbrother.bottomnavigation.profile.data.UserInfo
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_load_error.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider

class ProfileFragment : ProfileView, MvpAppCompatFragment() {

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @Inject
    lateinit var presenterProvider: Provider<ProfilePresenter>

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter = presenterProvider.get()

    override fun onAttach(context: Context) {
        (activity?.applicationContext as BigBrotherApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutButton.setOnClickListener { presenter.logoutButtonClicked() }
        reloadButton.setOnClickListener { presenter.reloadButtonClicked() }
    }

    override fun logout() {
        (activity as MainActivity).logout()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showError() {
        loadErrorConstraintLayout.visibility = View.VISIBLE
    }

    override fun hideError() {
        loadErrorConstraintLayout.visibility = View.GONE
    }

    override fun showContent(userInfo: UserInfo) {
        nameTextView.text = userInfo.name
        jobTextView.text = userInfo.job
        contentLinearLayout.visibility = View.VISIBLE
    }

}