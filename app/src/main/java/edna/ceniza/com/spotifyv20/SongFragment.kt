package edna.ceniza.com.spotifyv20

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by Edna Ceniza on 14/01/2018.
 */
private val PAUSE = "pause-song"
private val RESUME = "resume-song"

class SongFragment: Fragment (){
    private var mTitle: TextView? = null
    private var mArtist: TextView? = null
    private var mPause: ImageView? = null
    private var mResume: ImageView? = null

    companion object {
        fun newInstance(song: String, artist: String): SongFragment {
            val args = Bundle()
            args.putString(Key.TITLE, song)
            args.putString(Key.ARTIST, artist)
            val fragment = SongFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_part, container, false)
        mTitle = rootView!!.findViewById(R.id.txtsong_title) as TextView
        mArtist = rootView!!.findViewById(R.id.txtsong_artist) as TextView
        mPause = rootView!!.findViewById(R.id.pause_icon) as ImageView
        mResume = rootView!!.findViewById(R.id.play_icon) as ImageView
        val song = arguments.getString(Key.TITLE)
        val artist = arguments.getString(Key.ARTIST)

        if (song != null && artist != null){
            mTitle!!.text = song
            mArtist!!.text = artist

        }
        mPause!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?){
                mPause!!.visibility = View.INVISIBLE
                mResume!!.visibility = View.VISIBLE
                var songIntent = Intent(context, PlayMusicService::class.java).apply {
                    action = PAUSE
                }
                context.startService(songIntent)
            }
        })
        mResume!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?){
                mPause!!.visibility = View.VISIBLE
                mResume!!.visibility = View.INVISIBLE
                var songIntent = Intent(context, PlayMusicService::class.java).apply{
                   action = RESUME
                }
                context.startService(songIntent)
            }
        })
        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}