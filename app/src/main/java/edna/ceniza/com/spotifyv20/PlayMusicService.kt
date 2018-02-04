package edna.ceniza.com.spotifyv20

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

/**
 * Created by Edna Ceniza on 04/02/2018.
 */
class PlayMusicService: Service(){

    private val PLAY = "play"
    private val PAUSE = "pause-song"
    private val RESUME = "resume-song"
    var currentPause: Int = 0
    var musicDataList: ArrayList<String> = ArrayList()
    var mp: MediaPlayer? = null
    var position = 0

    override fun onBind(p0: Intent?): IBinder ? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int{
        if (intent!!.getAction().equals(PLAY)){
            currentPause = intent!!.getIntExtra(SongAdapter.PAUSED,0)
            musicDataList = intent.getStringArrayListExtra(SongAdapter.LIST)

            if(mp != null){
                mp!!.stop()
                mp!!.release()
                mp = null
            }

            mp = MediaPlayer()
            mp!!.setDataSource(musicDataList[currentPause])
            mp!!.prepare()
            mp!!.start()
        }

        if (intent!!.getAction().equals(PAUSE)){
            position = mp!!.getCurrentPosition()
            mp!!.pause()
        }

        if (intent!!.getAction().equals(RESUME)){
            mp!!.seekTo(position)
            mp!!.start()
        }

        return super.onStartCommand(intent, flags, startId)
    }
}