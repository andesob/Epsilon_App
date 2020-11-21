package no.ntnu.epsilon_app.ui.changePassword;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import no.ntnu.epsilon_app.LoginActivity;
import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import no.ntnu.epsilon_app.data.Result;
import no.ntnu.epsilon_app.ui.register.RegisterViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        viewModel = new ChangePasswordViewModel();
        oldPwd = root.findViewById(R.id.oldPwd);
        newPwd1 = root.findViewById(R.id.newPwd1);
        newPwd2 = root.findViewById(R.id.newPwd2);
        progressBar = root.findViewById(R.id.progressBar);
        final Button changwPwdButton = root.findViewById(R.id.changwPwdButton);




        progressBar.setVisibility(View.GONE);
        changwPwdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                changwPwdButton.setVisibility(View.INVISIBLE);


                new CountDownTimer(1500, 1000){
                    @Override
                    public void onTick(long l) {
                    }
                    @Override
                    public void onFinish() {

                        String oldPassword = oldPwd.getText().toString();
                        String newPassword1 = newPwd1.getText().toString();
                        String newPassword2 = newPwd2.getText().toString();


                        if(progressBar != null){
                            progressBar.setVisibility(View.INVISIBLE);
                            changwPwdButton.setVisibility(View.VISIBLE);

                            if(validateInput(oldPassword,newPassword1,newPassword2)) {

                                viewModel.getChangePasswordData(oldPassword, newPassword1).observe(getViewLifecycleOwner(), new Observer<Result>() {
                                    @Override
                                    public void onChanged(Result result) {
                                        if (result instanceof Result.Error){
                                            Toast.makeText(getContext(), "Error: kunne ikke endre passordet", Toast.LENGTH_SHORT).show();
                                            System.out.println(result.toString());
                                        }
                                        else if (result instanceof Result.Success){
                                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_newsfeed);
                                            Toast.makeText(getContext(), "Passordet er endret", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(getContext(), "Det oppsto en feil", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }.start();
            }
        });
        return root;
    }

    private void changePassword(String oldPassword, String newPassword) {

        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().changePassword(oldPassword, newPassword);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                    Toast.makeText(getContext(), "Passordet er endret", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Error: kunne ikke endre passordet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Error: kunne ikke endre passordet2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInput(String oldPassword, String newPassword1, String newPassword2) {

        if (!viewModel.isOldPwdValid(oldPassword)){
            oldPwd.requestFocus();
            oldPwd.setError("Please provide the current password");
            return false;
        }
        else if(!viewModel.isNewPwdValid(newPassword1)){
            newPwd1.requestFocus();
            newPwd1.setError("Password must be more than 5 characters");
            return false;
        }
        else if(!viewModel.isNewPwdValid(newPassword2)){
            newPwd2.requestFocus();
            newPwd2.setError("Password must be more than 5 characters");
            return false;
        }
        else if(!viewModel.isPasswordEqual(newPassword1, newPassword2)){
            newPwd2.requestFocus();
            newPwd2.setError("The new password must be equal");
            return false;
        }
        return true;
    }

}

