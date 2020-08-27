package com.example.introtokotlinfirebase

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var firebaseDatabase : FirebaseDatabase ?= null
    var databaseReference : DatabaseReference ?= null
    private var mAuth : FirebaseAuth ?= null
    private var currentUser : FirebaseUser ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase!!.getReference("messages")

        mAuth = FirebaseAuth.getInstance()

        //databaseReference!!.setValue("Hey There!!!")

        //create new user
        var email = emailid.text.toString().trim()

        var pwd = password.text.toString().trim()

        create_account_id.setOnClickListener {
            validate(emailid.text.toString(),password.text.toString())
        }







        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value =
                    dataSnapshot.getValue(String::class.java)
                Log.e("valuee",value)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Log.w(FragmentActivity.TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun validate(email: String, pwd: String) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd))
        {
            mAuth!!.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(this) { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        var user : FirebaseUser = mAuth!!.currentUser!!
                        Log.e("user",user.email.toString())
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()

        currentUser = mAuth!!.currentUser
    }
}