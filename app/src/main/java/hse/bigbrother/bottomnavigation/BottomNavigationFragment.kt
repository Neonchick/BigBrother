package hse.bigbrother.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import hse.bigbrother.R
import hse.bigbrother.bottomnavigation.contacts.ContactsFragment
import hse.bigbrother.bottomnavigation.profile.ProfileFragment
import hse.bigbrother.bottomnavigation.scenarios.ScenariosFragment
import kotlinx.android.synthetic.main.fragment_bottom_navigation.*
import moxy.MvpAppCompatFragment

class BottomNavigationFragment : MvpAppCompatFragment() {

    private val contactsFragment = ContactsFragment()

    private val profileFragment = ProfileFragment()

    private val scenariosFragment = ScenariosFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makeCurrentFragment(contactsFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigation_contacts -> makeCurrentFragment(contactsFragment)
                R.id.navigation_scenarios -> makeCurrentFragment(scenariosFragment)
                R.id.navigation_profile -> makeCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: MvpAppCompatFragment) {
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, fragment)
        }
    }

}