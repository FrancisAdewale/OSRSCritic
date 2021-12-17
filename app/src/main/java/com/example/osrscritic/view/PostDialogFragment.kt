package com.example.osrscritic.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.DialogFragment
import android.widget.LinearLayout

import android.widget.TextView

import android.widget.EditText

import android.view.Gravity
import androidx.core.app.ActivityCompat.recreate
import com.example.osrscritic.DisplayUserActivity
import com.example.osrscritic.viewmodel.DisplayUserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import org.koin.androidx.viewmodel.ext.android.viewModel


class PostDialogFragment: DialogFragment() {

    private val displayUserViewModel : DisplayUserViewModel by viewModel()
    val currentUser = FirebaseAuth.getInstance().currentUser
    lateinit var critiquing: String
    val postsAdapter = PostsAdapter()

    companion object {
        const val KEY: String = "KEY2"

        fun newInstance(text: String): PostDialogFragment {
            val args = Bundle()
            val postDialogFragment = PostDialogFragment()
            args.putString(KEY, text)
            postDialogFragment.arguments = args
            return postDialogFragment

        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        critiquing = arguments?.getString(KEY)!!

        Log.d("*******PostDIALOG", critiquing)
        val builder = AlertDialog.Builder(activity)

        val layout = LinearLayout(activity)
        val parms = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = parms
        layout.gravity = Gravity.CLIP_VERTICAL
        layout.setPadding(2, 2, 2, 2)

        val et = EditText(activity)
        var etString = ""

        et.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {

                Log.d("etText", et.text.toString())

                etString = et.text.toString()
            }

        })
        layout.addView(
            et,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
        //param = arguments?.getString(KEY)!!
        builder.setTitle("New Post")
        builder.setView(layout).setPositiveButton("Done", object: DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {

                postsAdapter.setHasStableIds(true)
                
                activity?.let {
                    displayUserViewModel.displayUserPostsLiveData.observe(it, {
                        val post : MutableMap<String, Any> = mutableMapOf()
                        //val posts : MutableList<String> = mutableListOf()
                        //posts.add(etString)
                        post["critic"] = currentUser?.email!!
                        post["posts"] = etString

                            it.document(critiquing).collection("posts")
                                .document(currentUser?.email!!).set(post)

//                        it.document(critiquing).collection("posts")
//                            .document(currentUser?.email!!).update("posts",
//                                FieldValue.arrayUnion(etString))
                    })
                }

                activity!!.finish()
                activity!!.overridePendingTransition( 0, 0);
                startActivity(activity!!.intent)
                postsAdapter.postsMutableList.clear()
                activity!!.overridePendingTransition( 0, 0);

            }

        })

        builder.setNegativeButton("Cancel", object:DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                dismiss()
            }

        })

        return builder.create()
    }

}