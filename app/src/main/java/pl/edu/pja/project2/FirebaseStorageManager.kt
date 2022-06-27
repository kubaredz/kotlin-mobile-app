package pl.edu.pja.project2

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

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
            val uploadedURL = mStorageRef.child("posts/${date}.png").downloadUrl
            //TODO Nie loguje logów w konsoli
            Log.e("Firebase", "Uploaded $uploadedURL")
        }.addOnFailureListener {
            Log.e("Frebase", "Image Upload fail")
            mProgressDialog.dismiss()
        }
    }

    fun saveFireStore(eventTitle: String, description: String, imagePath: String, data: String, author: String, context: Context) {
        val db = FirebaseFirestore.getInstance()
        val event: MutableMap<String, Any> = HashMap()
        event["eventTitle"] = eventTitle
        event["description"] = description
        event["imagePath"] = imagePath
        event["data"] = data
        event["author"] = author

        db.collection("events")
            .add(event)
            .addOnSuccessListener {
                Toast.makeText(context, "record added successfully ", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "record Failed to add ", Toast.LENGTH_SHORT).show()
            }
        readFireStoreData()
    }

    fun readFireStoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener {

                val result: StringBuffer = StringBuffer()

                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        result.append(document.data.getValue("firstName")).append(" ")
                            .append(document.data.getValue("lastName")).append("\n\n")
                    }
                 //TODO Fetch data
                }
            }

    }
}