package box.challenge.boxdeveloperchallenge;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import box.challenge.boxdeveloperchallenge.model.BoxColor;
import box.challenge.boxdeveloperchallenge.model.BoxSize;
import box.challenge.boxdeveloperchallenge.model.MainActivityError;
import box.challenge.boxdeveloperchallenge.model.MainActivityEvent;
import box.challenge.boxdeveloperchallenge.tools.InputUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.name_input) TextInputLayout nameInput;
    @BindView(R.id.email_input) TextInputLayout emailInput;
    @BindView(R.id.box_size_spinner) Spinner boxSizeSpinner;
    @BindView(R.id.box_size_error_label) TextView boxSizeErrorTextView;
    @BindView(R.id.box_color_spinner) Spinner boxColorSpinner;
    @BindView(R.id.box_color_error_label) TextView boxColorErrorTextView;
    @BindView(R.id.print_name_check_box) CheckBox printNameCheckBox;
    @BindView(R.id.sign_up_button) Button signUpButton;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        connectViewModel();
        initInputs();
        initBoxSize();
        initBoxColor();
        initSignUp();
    }

    private void connectViewModel() {
        viewModel.getMainActivityEvent().observe(this, this::handleEvent);
        viewModel.getMainActivityErrors().observe(this, this::handleError);
    }

    private void handleEvent(MainActivityEvent mainActivityEvent) {
        if (mainActivityEvent == null) {
            return;
        }
        switch (mainActivityEvent) {
            case SIGN_UP_ERROR:
                showErrorDialog();
                break;
            case SUCCESSFUL_SIGN_UP:
                showSuccessfulDialog();
                break;
            case SHOW_PROGRESS_BAR:
                setProgressBarVisible(true);
                break;
            case HIDE_PROGRESS_BAR:
                setProgressBarVisible(false);
                break;
        }
    }

    private void showErrorDialog() {
        showSingleMessageDialog(R.string.error_occurred);
    }

    private void showSuccessfulDialog() {
        showSingleMessageDialog(R.string.success);
    }

    private void showSingleMessageDialog(int messageStringRes) {
        new AlertDialog.Builder(this).setMessage(messageStringRes).show();

    }

    private void setProgressBarVisible(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void handleError(List<MainActivityError> mainActivityErrors) {
        if (mainActivityErrors == null) {
            return;
        }
        for (MainActivityError error : mainActivityErrors) {
            switch (error) {
                case MISSING_NAME:
                    nameInput.setError(getString(R.string.mandatory_field));
                    break;
                case MISSING_EMAIL:
                    emailInput.setError(getString(R.string.mandatory_field));
                    break;
                case WRONG_NAME:
                    nameInput.setError(getString(R.string.invalid_field));
                    break;
                case WRONG_EMAIL:
                    emailInput.setError(getString(R.string.invalid_field));
                    break;
                case WRONG_BOX_SIZE:
                    setBoxSizeError(R.string.wrong_box_size);
                    break;
                case WRONG_BOX_COLOR:
                    setBoxColorError(R.string.wrong_box_color);
                    break;
                case WRONG_SIZE_COLOR_PAIR:
                    setBoxColorError(R.string.wrong_size_color_pair);
                    break;
            }
        }
    }

    private void initInputs() {
        InputUtils.applyTextWatcher(nameInput);
        InputUtils.applyTextWatcher(emailInput);
    }

    private void initBoxSize() {
        boxSizeSpinner.setOnItemSelectedListener(createBoxSizeSelectedListener());
        boxSizeSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.simple_spinner_item, viewModel.getBoxSizes()));
    }

    private AdapterView.OnItemSelectedListener createBoxSizeSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                removeBoxSizeError();
                viewModel.onBoxSizeSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                removeBoxSizeError();
                viewModel.onBoxSizeUnselected();
            }
        };
    }

    private void setBoxSizeError(int errorStringRes) {
        boxSizeErrorTextView.setVisibility(View.VISIBLE);
        boxSizeErrorTextView.setText(errorStringRes);
    }

    private void removeBoxSizeError() {
        boxSizeErrorTextView.setVisibility(View.GONE);
    }

    private void initBoxColor() {
        boxColorSpinner.setOnItemSelectedListener(createBoxColorSelectedListener());
        viewModel.getBoxColors().observe(this, this::applyBoxColors);
    }

    private AdapterView.OnItemSelectedListener createBoxColorSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                removeBoxColorError();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                removeBoxColorError();
            }
        };
    }

    private void setBoxColorError(int errorStringRes) {
        boxColorErrorTextView.setVisibility(View.VISIBLE);
        boxColorErrorTextView.setText(errorStringRes);

    }

    private void removeBoxColorError() {
        boxColorErrorTextView.setVisibility(View.GONE);
    }

    private void applyBoxColors(List<BoxColor> boxColors) {
        if (boxColors == null) {
            return;
        }
        removeBoxColorError();
        boxColorSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.simple_spinner_item, boxColors));
    }

    private void initSignUp() {
        signUpButton.setOnClickListener(view -> signUp());
        viewModel.getIsSignUpEnabled().observe(this, signUpButton::setEnabled);
    }

    private void signUp() {
        removeBoxSizeError();
        removeBoxColorError();
        viewModel.signUp(InputUtils.getInputText(nameInput),
                InputUtils.getInputText(emailInput),
                (BoxSize) boxSizeSpinner.getSelectedItem(),
                (BoxColor) boxColorSpinner.getSelectedItem(),
                printNameCheckBox.isChecked());
    }
}
