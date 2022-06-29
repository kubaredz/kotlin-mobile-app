package pl.edu.pja.project2.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import pl.edu.pja.project2.R
import pl.edu.pja.project2.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: FragmentAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()


        onRegisterLoginClick()
    }


    private fun navigateToHomeFragment(name: String) {
        findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToHomeFragment(name))
    }

    private fun onRegisterLoginClick() {
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        binding.btnLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun registerUser() {
        val email = binding.etEmailRegister.text.toString()
        val password = binding.etPasswordRegister.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            //Coroutine - asynchronicznie
            //Dispatcher - wątek
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    //tworzenie za pomocą funkcji z FireBase
                    auth.createUserWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInState()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun loginUser() {
        val email = binding.etEmailLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                            navigateToHomeFragment(email)
                            Log.d("test123", "zalogowany")

                        checkLoggedInState()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun checkLoggedInState() {
        if (auth.currentUser == null) { // not logged in
            binding.tvLoggedIn.text = "You are not logged in"
        } else {
            binding.tvLoggedIn.text = "You are logged in!"
            navigateToHomeFragment(auth.currentUser.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        auth.signOut()
        checkLoggedInState()
    }

    override fun onResume() {
        super.onResume()
//        auth.signOut()
    }
}