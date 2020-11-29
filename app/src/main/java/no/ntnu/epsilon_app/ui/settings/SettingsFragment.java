package no.ntnu.epsilon_app.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.recyclerview.widget.RecyclerView;

import no.ntnu.epsilon_app.MainActivity;
import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.tools.EpsilonFacebookIntent;

public class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);
                final RecyclerView rv = getListView(); // This holds the PreferenceScreen's items
                rv.setPadding(20, 0, 20, 0);
        }

        @Override
public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey);

                Preference changePassword = findPreference("changePassword");
                Preference logout = findPreference("logout");
                Preference facebookLink = findPreference("facebook_link");


                if (changePassword != null) {
                        changePassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                                @Override
                                public boolean onPreferenceClick(Preference preference) {
                                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_change_pwd);
                                        return true;
                                }
                        });
                }

                if (logout != null) {
                        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                                @Override
                                public boolean onPreferenceClick(Preference preference) {
                                        ((MainActivity)requireActivity()).logout();
                                        return true;
                                }
                        });
                }
                if (facebookLink != null) {
                        facebookLink.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                                @Override
                                public boolean onPreferenceClick(Preference preference) {
                                        startActivity(EpsilonFacebookIntent.newFaceBookIntent(requireActivity().getPackageManager()));
                                        return true;
                                }
                        });
                }

        }


        }