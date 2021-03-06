package no.ntnu.epsilon_app.ui.calendar;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import no.ntnu.epsilon_app.MainActivity;
import no.ntnu.epsilon_app.R;
import no.ntnu.epsilon_app.api.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarAddFragment extends BottomSheetDialogFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int count = 0;
    private String latitudeLongitude;
    private String addressToLatLong;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(CalendarAddFragment.STYLE_NORMAL, R.style.SomeStyle);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_add, container, false);

        final EditText editTitle = view.findViewById(R.id.calendarEditTitle);
        final EditText editDescription = view.findViewById(R.id.calendarEditDescription);

        final TimePicker startTimePicker = view.findViewById(R.id.setStartTime);
        startTimePicker.setIs24HourView(true);
        final TimePicker endTimePicker = view.findViewById(R.id.setEndTime);
        endTimePicker.setIs24HourView(true);
        final DatePicker startDatePicker = view.findViewById(R.id.setStartDate);
        final DatePicker endDatePicker = view.findViewById(R.id.setEndDate);
        final EditText editAddressText = view.findViewById(R.id.editAdress);

        final Button editAddressButton = view.findViewById(R.id.addressConfirmButton);
        editAddressButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Sets the selected position
             * @param v View
             */
            @Override
            public void onClick(View v) {
                addressToLatLong = editAddressText.getText().toString().trim();
                List<Address> addressList = null;
                Geocoder geocoder = new Geocoder(getContext());

                try {
                    addressList = geocoder.getFromLocationName(addressToLatLong, 1);
                } catch (IOException e) {
                }
                if (addressList != null && addressList.size() != 0) {
                    Address address = addressList.get(0);
                    mMap.clear();
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    latitudeLongitude = latLng.latitude + (",") + latLng.longitude;
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                } else {
                    Toast.makeText(getActivity(), "Addresse ikke funnet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final ImageView nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                setPage(count);
            }
        });
        final ImageView backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count -= 1;
                setPage(count);
            }
        });

        final ImageView nextButtonTwo = view.findViewById(R.id.nextButtonTwo);
        nextButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                setPage(count);
            }
        });

        final ImageView backButtonTwo = view.findViewById(R.id.backButtonTwo);
        backButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count -= 1;
                setPage(count);
            }
        });

        final Button addButton = view.findViewById(R.id.addEventButton);
        addButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Adds a calendar event to the database using the paramaters set
             * @param v View
             */
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String description = editDescription.getText().toString().trim();
                String startMin = String.format("%02d", startTimePicker.getMinute());
                String startHour = String.format("%02d", startTimePicker.getHour());
                String endMin = String.format("%02d", endTimePicker.getMinute());
                String endHour = String.format("%02d", endTimePicker.getHour());

                String startTime = startDatePicker.getYear() + "," + startDatePicker.getMonth() +
                        "," + startDatePicker.getDayOfMonth() + "," + startHour + "," + startMin;
                String endTime = endDatePicker.getYear() + "," + endDatePicker.getMonth() +
                        "," + endDatePicker.getDayOfMonth() + "," + endHour + "," + endMin;

                if (LocalDate.of(startDatePicker.getYear(), startDatePicker.getMonth() + 1,
                        startDatePicker.getDayOfMonth()).isAfter(LocalDate.of(endDatePicker.getYear(), endDatePicker.getMonth() + 1,
                        endDatePicker.getDayOfMonth()))) {
                    count = 0;
                    setPage(count);
                    Toast.makeText(getActivity(), "Startdato er satt til etter sluttdato", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (LocalTime.of(startTimePicker.getHour(), startTimePicker.getMinute()).isAfter(LocalTime.of(endTimePicker.getHour(), endTimePicker.getMinute()))) {
                    count = 0;
                    setPage(count);
                    Toast.makeText(getActivity(), "Starttid er satt til etter slutt-tid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (title.isEmpty()) {
                    editTitle.setError("Vennligst tast inn en tittel");
                    editTitle.requestFocus();
                    return;
                }
                if (description.isEmpty()) {
                    editDescription.setError("Vennligst tast inn en beskrivelse");
                    editDescription.requestFocus();
                    return;
                }
                if ((addressToLatLong == null || addressToLatLong.isEmpty()) || (latitudeLongitude == null || latitudeLongitude.isEmpty())) {
                    count = 1;
                    setPage(count);
                    Toast.makeText(getActivity(), "Lokasjon ikke satt", Toast.LENGTH_SHORT).show();
                    return;
                }
                addEvent(title, description, latitudeLongitude, startTime, endTime, addressToLatLong);

            }

        });

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    private void addEvent(String title, String description, String latLng, String startTime, String endTime, String addressToLatLong) {


        Call<ResponseBody> call = RetrofitClientInstance.getSINGLETON().getAPI().addCalendarItem(title, description,
                latLng, startTime, endTime, addressToLatLong);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dismiss();
                if (response.isSuccessful()) {

                } else {
                    ((MainActivity) getActivity()).goToSplashScreen();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }


    /**
     * Controls what widgets are going to be shown when adding a calendar item
     *
     * @param page the page to be shown
     */
    private void setPage(int page) {
        ConstraintLayout addCalendarPageOne = getView().findViewById(R.id.addCalendarPageOne);
        ConstraintLayout addCalendarPageTwo = getView().findViewById(R.id.addCalendarPageTwo);
        ConstraintLayout addCalendarPageThree = getView().findViewById(R.id.addCalendarPageThree);

        switch (page) {
            case 0:
                addCalendarPageOne.setVisibility(View.VISIBLE);
                addCalendarPageTwo.setVisibility(View.GONE);
                addCalendarPageThree.setVisibility(View.GONE);
                break;
            case 1:
                addCalendarPageOne.setVisibility(View.GONE);
                addCalendarPageTwo.setVisibility(View.VISIBLE);
                addCalendarPageThree.setVisibility(View.GONE);
                break;
            case 2:
                addCalendarPageOne.setVisibility(View.GONE);
                addCalendarPageTwo.setVisibility(View.GONE);
                addCalendarPageThree.setVisibility(View.VISIBLE);
                break;
            default:
        }
    }

    /**
     * Sets default position and marker for map
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng ntnuAalesund = new LatLng(62.472117, 6.235394);
        mMap.addMarker(new MarkerOptions().position(ntnuAalesund));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ntnuAalesund, 15f));

    }

}