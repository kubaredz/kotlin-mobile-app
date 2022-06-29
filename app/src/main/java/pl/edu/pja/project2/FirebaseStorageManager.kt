package pl.edu.pja.project2

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import pl.edu.pja.project2.home.HomeFragment
import java.util.*
import kotlin.collections.ArrayList

class FirebaseStorageManager {
    private val mStorageRef = FirebaseStorage.getInstance().reference
    private lateinit var mProgressDialog: ProgressDialog

    fun uploadImage(context: Context, imageFileUri: Uri) {
        mProgressDialog = ProgressDialog(context)
        mProgressDialog.setMessage("Please wait, image being upload")
        mProgressDialog.show()
        val date = Date()
        val uploadTask = mStorageRef.child("posts/${date}.png").putFile(imageFileUri)
        uploadTask.addOnSuccessListener {
            Log.e("Frebase", "Image Upload success")
            mProgressDialog.dismiss()
            val uploadedURL =
                mStorageRef.child("posts/${date}.png").downloadUrl.addOnCompleteListener {
                    imgUrl = it.result.toString()
                }

            Log.e("Firebase", "Uploaded $uploadedURL")
        }.addOnFailureListener {
            Log.e("Frebase", "Image Upload fail")
            mProgressDialog.dismiss()
        }
    }

    fun saveFireStore(
        eventTitle: String,
        description: String,
        imagePath: String,
        data: String,
        author: String,
        eventLocalization: String,
        context: Context
    ) {
        val db = FirebaseFirestore.getInstance()
        val event: MutableMap<String, Any> = HashMap()
        event["eventTitle"] = eventTitle
        event["description"] = description
        event["imagePath"] = imagePath
        event["data"] = data
        event["author"] = author
        event["eventLocalization"] = eventLocalization

        db.collection("events")
            .add(event)
            .addOnSuccessListener {
                Toast.makeText(context, "record added successfully ", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "record Failed to add ", Toast.LENGTH_SHORT).show()
            }
//        readFireStoreData()
    }

    fun readFireStoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("events")
            .get()
            .addOnCompleteListener {

//                val result: StringBuffer = StringBuffer()

                if (it.isSuccessful) {
                    for (document in it.result!!) {
//                        result.append(document.data.getValue("author")).append(" ")
//                            .append(document.data.getValue("eventTitle")).append("\n\n")
//                            .append(document.data.getValue("description")).append("\n\n")
//                            .append(document.data.getValue("imagePath")).append("\n\n")
//                            .append(document.data.getValue("data")).append("\n\n")
//                        Log.d("result", result.toString())
                        elementList.add(
                            arrayListOf(
                                document.data.getValue("author").toString(),
                                document.data.getValue("eventTitle").toString(),
                                document.data.getValue("description").toString(),
                                document.data.getValue("imagePath").toString(),
                                document.data.getValue("data").toString(),
                                document.data.getValue("eventLocalization").toString()
                            )
                        )
                        Log.d("value", "W PETLI ${elementList}")

                    }
                    Log.d("value", "${elementList}")
                    HomeFragment().setGoalsData.value++
                    //TODO Fetch data
                }
            }

    }


    companion object {
        var imgUrl = ""
        var elementList = arrayListOf<ArrayList<String>>()
    }

}