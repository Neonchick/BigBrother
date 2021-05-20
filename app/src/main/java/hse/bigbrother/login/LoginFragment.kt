package hse.bigbrother.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import hse.bigbrother.BigBrotherApplication
import hse.bigbrother.MainActivity
import hse.bigbrother.R
import hse.bigbrother.login.data.LoginInfo
import kotlinx.android.synthetic.main.fragment_login.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import javax.inject.Provider

class LoginFragment : LoginView, MvpAppCompatFragment() {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @Inject
    lateinit var presenterProvider: Provider<LoginPresenter>

    @ProvidePresenter
    fun providePresenter(): LoginPresenter = presenterProvider.get()

    override fun onAttach(context: Context) {
        (activity?.applicationContext as BigBrotherApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener { presenter.loginButtonClick(getLoginInfo()) }
    }

    private fun getLoginInfo(): LoginInfo {
        return LoginInfo(
            username = loginEditText.text.toString(),
            password = passwordEditText.text.toString()
        )
    }

    override fun showError() {
        Toast.makeText(activity?.applicationContext, ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
        hideProgress()
    }

    override fun showWrongData() {
        Toast.makeText(activity?.applicationContext, WRONG_DATA_MESSAGE, Toast.LENGTH_SHORT).show()
        hideProgress()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
        loginButton.isClickable = false
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        loginButton.isClickable = true
    }

    override fun loginSuccess() {
        hideProgress()
        (activity as MainActivity).login()
    }

    companion object {

        private const val ERROR_MESSAGE = "Что-то пошло не так"

        private const val WRONG_DATA_MESSAGE = "Неверный логин или пароль"

    }

}