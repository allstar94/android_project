package com.fastcampus.push_alarm

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {


//    대부분 기업에서는 FCM의 알림메세지보다는 데이터 메세지를 사용하는데
//    알림 메세지는 서버 구현없이 앱 내에서 활용하고
//    데이터 메세지는 알림 자체를 커스텀 할 수 있어서 대부분 회사에서는 데이터 메세지를 활용한다.
    private val resultTextView: TextView by lazy{
        findViewById(R.id.resultTextView)
    }

//    lateinit var inputValue : String
//    val x : Int by lazy { inputValue.length }
//    inputValue = "Initialized!"
//    println(x)

//    이 코드의 경우, lateinit 으로 설정 되어 있기 때문에, 아직 그 길이를 알 수가 없다.
//    무조건 inputValue가 제대로 값을 가지게 된 후에 x를 활용할 예정이라면, 이 때 할수 이용할 수 있는 문법이 by lazy다.
//    x는 변수가 '처음 사용된 순간'인 4번째 줄의 출력문에서 inputValue.length로 초기화된다.
//
//    x를 사용하기 전에 inputValue 친구만 초기화를 해 준다면,
//    어디에서 호출하든 특별히 추가적인 선언문을 작성할 필요가 없는 것이다.
//
//    lateinit vs by lazy
//    두 문법의 가장 큰 차이점은 lateinit은 var로만, by lazy는 val로만 선언된다는 점이다.
//    이렇기 때문에 초기화 이후에 같이 변할 수 있는 변수에는 lateinit을,
//    처음 초기화 된 직후부터 계속 read-only로만 쓰이는 변수에는 by lazy를 사용하는 것이 좋다.

    private val firebaseToken: TextView by lazy{
        findViewById(R.id.firebaaseTokenTextView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebase()
        updateResult()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        setIntent(intent)
        updateResult(true)
    }

    private fun initFirebase() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseToken.text = task.result
                }
            }
    }

    @SuppressLint("SetTextI18n")
    private fun updateResult(isNewIntent: Boolean = false) {
        resultTextView.text = (intent.getStringExtra("notificationType") ?: "앱 런처") +
                if (isNewIntent) {
                    "(으)로 갱신했습니다."
                } else {
                    "(으)로 실행했습니다."
                }
    }
}