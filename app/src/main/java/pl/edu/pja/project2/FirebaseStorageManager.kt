package pl.edu.pja.project2

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.edu.pja.project2.home.AddEventFragment
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
            Log.e("Firebase", "Image Upload success")
            mProgressDialog.dismiss()
            val uploadedURL =
                mStorageRef.child("posts/${date}.png").downloadUrl.addOnCompleteListener {
                    imgUrl = it.result.toString()
                    Log.d("value", "VALUE FIREBASE IMG $imgUrl")
                    _triggerImage.value = imgUrl

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
                Log.d("finish", "READ DB")
                readFireStoreData()
            }
            .addOnFailureListener {
                Log.d("finish", "FAIL READ DB")
                Toast.makeText(context, "record Failed to add ", Toast.LENGTH_SHORT).show()
            }
    }

    fun readFireStoreData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("events")
            .get()
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    elementList.clear()
                    for (document in it.result!!) {
                        elementList.add(
                            arrayListOf(
                                document.data.getValue("author").toString(),
                                document.data.getValue("eventTitle").toString(),
                                document.data.getValue("description").toString(),
                                document.data.getValue("imagePath").toString(),
                                document.data.getValue("data").toString(),
                                document.data.getValue("eventLocalization").toString(),
                                document.id
                            )
                        )
                    }
                    Log.d("value", "TRIGGER FB ${elementList}")
                        _setGoalsData.value = elementList

                    //TODO Fetch data
                }
            }
    }


    companion object {
        var imgUrl = ""
        var elementList = arrayListOf<ArrayList<String>>()
        var _setGoalsData = MutableStateFlow(arrayListOf<ArrayList<String>>())
        val setGoalsData = _setGoalsData.asStateFlow()
        var _triggerImage = MutableStateFlow("")
        val triggerImage = _triggerImage.asStateFlow()
    }

}