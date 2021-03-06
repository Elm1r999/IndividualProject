package elmir.vip.individualproject.ui.share

//import com.google.firebase.Timestamp
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import elmir.vip.individualproject.R
import elmir.vip.individualproject.activities.LoginActivity
import kotlinx.android.synthetic.main.activity_room.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RoomActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val firestore = FirebaseFirestore.getInstance()

    val chatMessages = ArrayList<ChatMessage>()
    var chatRegistration: ListenerRegistration? = null
    var roomId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        checkUser()

        initList()
        setViewListeners()
    }

    private fun setViewListeners() {
        button_send.setOnClickListener {
            sendChatMessage()
        }
    }

    private fun initList() {
        if (user == null)
            return

        list_chat.layoutManager = LinearLayoutManager(this)
        val adapter = ChatAdapter(chatMessages, user.uid)
        list_chat.adapter = adapter
        listenForChatMessages()
    }

    private fun listenForChatMessages() {
        roomId = intent.getStringExtra("INTENT_EXTRA_ROOMID")
        if (roomId == null) {
            finish()
            return
        }

        chatRegistration = firestore.collection("rooms")
            .document(roomId!!)
            .collection("messages")
            .addSnapshotListener { messageSnapshot, exception ->

                if (messageSnapshot == null || messageSnapshot.isEmpty)
                    return@addSnapshotListener

                chatMessages.clear()

                for (messageDocument in messageSnapshot.documents) {
                    chatMessages.add(
                        ChatMessage(
                            messageDocument["text"] as String,
                            messageDocument["user"] as String,
                            messageDocument["timestamp"] as String
                        ))
                }

                chatMessages.sortBy { it.timestamp }
                list_chat.adapter?.notifyDataSetChanged()
            }
    }

    private fun sendChatMessage() {
        val message = edittext_chat.text.toString()
        edittext_chat.setText("")

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date).toString()

        firestore.collection("rooms").document(roomId!!).collection("messages")
            .add(mapOf(
                Pair("text", message),
                Pair("user", user?.uid),
                Pair("timestamp", formatedDate)   //Timestamp.now())
            ))
    }

    private fun checkUser() {
        if (user == null)
            launchLogin()
    }

    private fun launchLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun signOut() {
        auth.signOut()
        launchLogin()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_logout) {
            signOut()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        chatRegistration?.remove()
        super.onDestroy()
    }
}
