package hse.bigbrother.bottomnavigation.contacts

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hse.bigbrother.BigBrotherApplication
import hse.bigbrother.R
import hse.bigbrother.bottomnavigation.contacts.recycleview.ContactsAdapter
import hse.bigbrother.ibeacon.PersonContactHandler
import hse.bigbrother.ibeacon.PersonContactListener
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_contacts.progressBar
import kotlinx.android.synthetic.main.layout_load_error.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.altbeacon.beacon.*
import javax.inject.Inject
import javax.inject.Provider

class ContactsFragment : ContactsView, PersonContactListener, MvpAppCompatFragment() {

    @InjectPresenter
    lateinit var presenter: ContactsPresenter

    @Inject
    lateinit var presenterProvider: Provider<ContactsPresenter>

    @ProvidePresenter
    fun providePresenter(): ContactsPresenter = presenterProvider.get()

    @Inject
    lateinit var personContactHandler: PersonContactHandler

    override fun onAttach(context: Context) {
        (activity?.applicationContext as BigBrotherApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transmitterSwitchCompat.isChecked = presenter.isTransmitterOn()
        transmitterSwitchCompat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.onTransmitterSwitchCompatOn()
            } else {
                presenter.onTransmitterSwitchCompatOff()
            }
        }
        if (transmitterSwitchCompat.isChecked) {
            addListener()
        }
        contactsRecycleView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        contactsRecycleView.adapter = ContactsAdapter(presenter.getContactsList())
        reloadButton.setOnClickListener { presenter.reloadButtonClicked() }
    }

    override fun onDestroyView() {
        deleteListener()
        super.onDestroyView()
    }

    override fun addListener() = personContactHandler.addListener(this)

    override fun deleteListener() = personContactHandler.deleteListener(this)

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

    override fun showContent() {
        contentLinearLayout.visibility = View.VISIBLE
    }

    override fun transmitterSwitchCompatOff() {
        transmitterSwitchCompat.isChecked = false
    }

    override fun onRangeChanged(beacons: Collection<Beacon>) {
        contactsRecycleView.adapter?.notifyDataSetChanged()
        contactsRecycleView.smoothScrollToPosition(0)
    }

}