package no.ntnu.epsilon_app.ui.changePassword;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import no.ntnu.epsilon_app.R;
import retrofit2.Response;

/**
 * A fragment for changing the user password.
 */
public class ChangePasswordFragment extends Fragment {

    private ChangePasswordViewModel viewModel;
    private EditText oldPwd;
    private EditText newPwd1;
    private EditText newPwd2;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_change_password, container, false);

        viewModel = new ChangePasswordViewModel();
        oldPwd = root.findViewById(R.id.oldPwd);
        newPwd1 = root.findViewById(R.id.newPwd1);
        newPwd2 = root.findViewById(R.id.newPwd2);
        progressBar = root.findViewById(R.id.progressBar);
        final Button changePwdButton = root.findViewById(R.id.changwPwdButton);
        observeChangePasswordData();

        progressBar.setVisibility(View.GONE);
        changePwdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                changePwdButton.setVisibility(View.INVISIBLE);

                new CountDownTimer(1500, 1000) {
                    @Override
                    public void onTick(long l) {
                    }

                    @Override
                    public void onFinish() {

                        String oldPassword = oldPwd.getText().toString();
                        String newPassword1 = newPwd1.getText().toString();
                        String newPassword2 = newPwd2.getText().toString();


                        if (progressBar != null) {
                            progressBar.setVisibility(View.INVISIBLE);
                            changePwdButton.setVisibility(View.VISIBLE);

                            if (validateInput(oldPassword, newPassword1, newPassword2)) {
                                viewModel.getChangePasswordData(oldPassword, newPassword1);
                            }
                        }
                    }
                }.start();
            }
        });
        return root;
    }

    /**
     * Observes the data and handles the result depending on success or error
     */
    private void observeChangePasswordData() {
        viewModel.getChangePasswordData().observe(getViewLifecycleOwner(), new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                if (response.isSuccessful()) {
                    handleSuccess(response.code());
                } else {
                    handleError(response.code());
                }
            }
        });

    }

    /**
     * Handles an error is something goes wrong with the API call
     *
     * @param responseCode - the resposne code from the server
     */
    private void handleError(int responseCode) {
        if (responseCode == 400) {

            Toast.makeText(getContext(), R.string.something_went_wrong_with_password_changing, Toast.LENGTH_SHORT).show();
        } else if (responseCode == 401) {
            oldPwd.requestFocus();
            oldPwd.setError(getText(R.string.old_password_is_wrong));
        } else {
            Toast.makeText(getContext(), R.string.something_went_wrong_with_password_changing, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles success from the API call
     *
     * @param responseCode - the response code from the server.
     */
    private void handleSuccess(int responseCode) {
        if (responseCode == 200) {
            Toast.makeText(getContext(), R.string.password_is_changed, Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_newsfeed);
        }
    }

    /**
     * Validates the input from the user.
     *
     * @param oldPassword  - The password the user wants to change
     * @param newPassword1 - The new password
     * @param newPassword2 - the new password
     * @return boolean- TRUE if the input is validated
     */
    private boolean validateInput(String oldPassword, String newPassword1, String newPassword2) {

        if (!viewModel.isOldPwdValid(oldPassword)) {
            oldPwd.requestFocus();
            oldPwd.setError(getText(R.string.old_password_is_wrong));
            return false;
        } else if (!viewModel.isNewPwdValid(newPassword1)) {
            newPwd1.requestFocus();
            newPwd1.setError(getText(R.string.password_must_be_longer_than_5_characters));
            return false;
        } else if (!viewModel.isNewPwdValid(newPassword2)) {
            newPwd2.requestFocus();
            newPwd2.setError(getText(R.string.password_must_be_longer_than_5_characters));
            return false;
        } else if (!viewModel.isPasswordEqual(newPassword1, newPassword2)) {
            newPwd2.requestFocus();
            newPwd2.setError(getText(R.string.password_must_be_equal));
            return false;
        }
        return true;
    }
}

