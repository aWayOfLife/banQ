package com.thetripod.banq;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView bookingId, serviceId, user_name , branchName , cityName , nextHint;
    private String slot , date;
    private Button served , next_customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        updateBranchName();
        next_customer = findViewById(R.id.button_next_customer);
        served = findViewById(R.id.button_served);
        next_customer.setVisibility(View.GONE);
        served.setVisibility(View.GONE);
        toggleVisibility();
        bookingId = findViewById(R.id.bookingID);
        serviceId = findViewById(R.id.serviceID);
        user_name = findViewById(R.id.user_name);
        branchName = findViewById(R.id.city_name);
        cityName = findViewById(R.id.branch_name);
        nextHint = findViewById(R.id.nextHint);
        date = convertTimestampToDate(System.currentTimeMillis());
        slot = getCurentSlot();
        Toast.makeText(MainActivity.this,date+slot, Toast.LENGTH_LONG).show();
        served.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               servedCustomer();
            }
        });


        next_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               nextCustomerDetails();
            }
        });



    }

    public void nextCustomerDetails(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Query query = mDatabase.child("Banker").child(mAuth.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //parse data to recycler view adapter and call notifyDatasetChange()
                BankerDetails bankerDetails = dataSnapshot.getValue(BankerDetails.class);
                final String city = bankerDetails.getCity();
                final String branch = bankerDetails.getBranch();
                Toast.makeText(MainActivity.this,date+slot, Toast.LENGTH_LONG).show();

                //final String date = "01-09-2019";
                //final String slot = "10:00 - 12:00";
                final DatabaseReference mRef = mDatabase.child("bookings").child(city).child(branch).child("Booking_Queue").child(date).child(slot);
                Log.i("QUERY",mRef.toString());
                mRef.limitToFirst(1).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        BookingCurrent bookingCurrent = dataSnapshot.getValue(BookingCurrent.class);
                        Toast.makeText(MainActivity.this,bookingCurrent.getBookingId(), Toast.LENGTH_LONG).show();
                        nextHint.setText("Your next customer is:");
                        bookingId.setText(bookingCurrent.getBookingId());
                        serviceId.setText(bookingCurrent.getServiceId());
                        final Query query1 = mDatabase.child("User").child(bookingCurrent.getUserId());
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                BankerDetails bankerDetails1 = dataSnapshot.getValue(BankerDetails.class);
                                user_name.setText(bankerDetails1.getName());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        final DatabaseReference mRef1 = mDatabase.child("bookings").child(city).child(branch).child("Booking_Current").child(bookingCurrent.getUserId());;
                        bookingCurrent.setStatus("Active");
                        bookingCurrent.setBankerId(mAuth.getCurrentUser().getUid());
                        mRef1.setValue(bookingCurrent);

                        BookingCompleted bookingCompleted = new BookingCompleted(bookingCurrent.getUserId(), mAuth.getCurrentUser().getUid(),
                                bookingCurrent.getBookingId());
                        final DatabaseReference mRef2 = mDatabase.child("bookings").child(city).child(branch).child("Booking_Ongoing").child(date).child(slot).child(mAuth.getCurrentUser().getUid());
                        mRef2.setValue(bookingCompleted);

                        final DatabaseReference mRef3 = mDatabase.child("bookings").child(city).child(branch).child("Booking_Queue").child(date).child(slot).child(bookingCurrent.getBookingTimestamp());
                        mRef3.removeValue();

                        mRef.removeEventListener(this);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        mRef.removeEventListener(this);
                        BookingCurrent bookingCurrent = dataSnapshot.getValue(BookingCurrent.class);
                        Toast.makeText(MainActivity.this,bookingCurrent.getBookingId(), Toast.LENGTH_LONG).show();
                        nextHint.setText("Your next customer is:");
                        bookingId.setText(bookingCurrent.getBookingId());
                        serviceId.setText(bookingCurrent.getServiceId());
                        final Query query1 = mDatabase.child("User").child(bookingCurrent.getUserId());
                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                BankerDetails bankerDetails1 = dataSnapshot.getValue(BankerDetails.class);
                                user_name.setText(bankerDetails1.getName());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        final DatabaseReference mRef1 = mDatabase.child("bookings").child(city).child(branch).child("Booking_Current").child(bookingCurrent.getUserId());;
                        bookingCurrent.setStatus("Active");
                        bookingCurrent.setBankerId(mAuth.getCurrentUser().getUid());
                        mRef1.setValue(bookingCurrent);

                        BookingCompleted bookingCompleted = new BookingCompleted(bookingCurrent.getUserId(), mAuth.getCurrentUser().getUid(),
                                bookingCurrent.getBookingId());
                        final DatabaseReference mRef2 = mDatabase.child("bookings").child(city).child(branch).child("Booking_Ongoing").child(date).child(slot).child(mAuth.getCurrentUser().getUid());
                        mRef2.setValue(bookingCompleted);

                        final DatabaseReference mRef3 = mDatabase.child("bookings").child(city).child(branch).child("Booking_Queue").child(date).child(slot).child(bookingCurrent.getBookingTimestamp());
                        mRef3.removeValue();


                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                query.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    public String getCurentSlot(){
        String exact_time = null;
        int hour = Integer.parseInt(convertTimestampToHour());
        if(hour >= 0 && hour < 12)
            exact_time = "10:00 - 12:00";
        else if (hour >= 12 && hour < 13)
            exact_time = "12:00 - 13:00";
        else if (hour >= 13 && hour < 14)
            exact_time = "13:00 - 14:00";
        else if (hour >= 15 && hour < 16)
            exact_time = "15:00 - 16:00";
        else
            exact_time = "10:00 - 12:00";

        return exact_time;
    }

     public void servedCustomer(){
        final String bankerId= mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
         final Query query = mDatabase.child("Banker").child(mAuth.getCurrentUser().getUid());
         query.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 //parse data to recycler view adapter and call notifyDatasetChange()
                 BankerDetails bankerDetails = dataSnapshot.getValue(BankerDetails.class);
                 final String city = bankerDetails.getCity();
                 final String branch = bankerDetails.getBranch();
                 Toast.makeText(MainActivity.this,bankerDetails.getCity(), Toast.LENGTH_LONG).show();
                 //final String date = "01-09-2019";
                 //final String slot = "10:00 - 12:00";

                 final DatabaseReference mRef = mDatabase.child("bookings").child(city).child(branch).child("Booking_Ongoing").child(date).child(slot).child(bankerId);
                 mRef.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         BookingCompleted bookingCompleted = dataSnapshot.getValue(BookingCompleted.class);
                         final DatabaseReference mRef1 = mDatabase.child("bookings").child(city).child(branch).child("Booking_Current").child(bookingCompleted.getUserId());//thikache? haan
                         mRef1.addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 BookingCurrent bookingCurrent = dataSnapshot.getValue(BookingCurrent.class);
                                 BookingCompleted bookingCompleted = new BookingCompleted(bookingCurrent.getUserId(), bookingCurrent.getBookingId(), bankerId, bookingCurrent.getServiceId(),
                                         bookingCurrent.getSlot(), bookingCurrent.getBranch(), String.valueOf(System.currentTimeMillis()),  bookingCurrent.getBookingTimestamp() );
                                 //final DatabaseReference mRef2 = mDatabase.child("bookings").child(city).child(branch).child("Booking_Completed").child(bookingCurrent.getUserId());
                                 final DatabaseReference mRef2 = mDatabase.child("bookings").child("Booking_Completed").child(bookingCurrent.getUserId());

                                 mRef2.push().setValue(bookingCompleted);

                                 mRef1.removeValue();
                                 mRef.removeValue();
                                 nextHint.setText("Customer Served");
                                 bookingId.setText("");
                                 serviceId.setText("");
                                 user_name.setText("");
                                 mRef1.removeEventListener(this);

                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });
                         mRef.removeEventListener(this);
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });


                 query.removeEventListener(this);
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {}
         });


     }
    public String convertTimestampToDate(Long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    public static String convertTimestampToHour(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hour = sdf.format(new Date());
        return hour;
    }

    public void toggleVisibility(){
        final String bankerId= mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Query query = mDatabase.child("Banker").child(mAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //parse data to recycler view adapter and call notifyDatasetChange()
                BankerDetails bankerDetails = dataSnapshot.getValue(BankerDetails.class);
                final String city = bankerDetails.getCity();
                final String branch = bankerDetails.getBranch();
                final DatabaseReference mRef = mDatabase.child("bookings").child(city).child(branch).child("Booking_Ongoing").child(date).child(slot).child(bankerId);
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        BookingCompleted bookingCompleted = dataSnapshot.getValue(BookingCompleted.class);
                        if(null!=bookingCompleted){
                            served.setVisibility(View.VISIBLE);
                            next_customer.setVisibility(View.GONE);
                        }
                        else{
                            served.setVisibility(View.GONE);
                            next_customer.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void updateBranchName(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final Query query = mDatabase.child("Banker").child(mAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //parse data to recycler view adapter and call notifyDatasetChange()
                BankerDetails bankerDetails = dataSnapshot.getValue(BankerDetails.class);
                final String city = bankerDetails.getCity();
                final String branch = bankerDetails.getBranch();
                cityName.setText(city);
                branchName.setText(branch);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }
}
