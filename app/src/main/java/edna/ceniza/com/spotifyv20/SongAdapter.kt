package edna.ceniza.com.spotifyv20

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

/**
 * Created by Edna Ceniza on 14/01/2018.
 */
class SongAdapter (val songList: ArrayList<SongList>, val context: Context, val mainActivity: MainActivity): RecyclerView.Adapter<SongAdapter.ViewHolder>(){

    var currentSong:Int = 0
    var view: View? = null
    var mContext = context
    val msongList: ArrayList<String> = ArrayList()
    companion object {
        val LIST = "list"
        val PAUSED = "pause"
        val PLAY = "play"
    }

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        val mLinearLayout = itemView.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout
        val songTitle = itemView.findViewById<TextView>(R.id.txtsong_title) as TextView
        val songArtist = itemView.findViewById<TextView>(R.id.txtsong_artist) as TextView
        val songAlbum = itemView.findViewById<TextView>(R.id.txtsong_album) as TextView
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val song: SongList = songList[position]
        holder?.songTitle?.text = song.song_title
        holder?.songArtist?.text = song.song_artist
        holder?.songAlbum?.text = song.song_album

        holder?.mLinearLayout?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View){
                currentSong = position
                holder?.songTitle?.setTextColor(Color.parseColor("#1DB954"))
                for(i in 0 until songList.size){
                    msongList.add(songList[i].song_path)
                }

                try{
                    val fragment = SongFragment.newInstance(song.song_title,song.song_artist)
                    mainActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_play, fragment)
                            .commit()
                }catch (e: Exception) {
                    Toast.makeText(mainActivity, "Error!", Toast.LENGTH_SHORT)
                }

                var songIntent = Intent(mContext, PlayMusicService::class.java)
                songIntent.putStringArrayListExtra(LIST, msongList)
                songIntent.setAction(PLAY)
                songIntent.putExtra(PAUSED, position)
                mContext.startService(songIntent)
            }
        })

    }


}