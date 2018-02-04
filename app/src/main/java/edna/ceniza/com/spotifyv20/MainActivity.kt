package edna.ceniza.com.spotifyv20

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import edna.ceniza.com.spotifyv20.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mRecyclerView: RecyclerView? = null
    var mNestedScrollView: NestedScrollView? = null
    var songList = ArrayList<SongList>()

    companion object {
        val PERMISSION_REQUEST_CODE = 88
    }


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCollapsingToolbar()
        findViews()

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        } else {
            loadData()
        }

        val toolBar = findViewById<Toolbar>(R.id.toolbarWidget) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolBar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        BottomNavigationHelper.disableShiftMode(bottomNavigationView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initCollapsingToolbar() {

        val collapsingToolbar = findViewById<View>(R.id.collapsing) as CollapsingToolbarLayout
        collapsingToolbar.title = " "
        val appBarLayout = findViewById<View>(R.id.appbarlayout) as AppBarLayout
        appBarLayout.setExpanded(true)

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appbarlayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = "Top Hits Philippines"
                    collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    collapsingToolbar.setCollapsedTitleTextColor(Color.TRANSPARENT)
                    isShow = false
                }
            }

        })
    }

    fun findViews() {
        mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        mNestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)
    }

    fun loadData() {
        var songCursor: Cursor? = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null)

        while (songCursor != null && songCursor.moveToNext()) {
            var songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            var songArtist = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            var songAlbum = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
            var songPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))

            songList.add(SongList(
                    songName,
                    songArtist,
                    songAlbum,
                    songPath
            ))
        }
        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val adapter = SongAdapter(songList, applicationContext, this)
        mRecyclerView!!.adapter = adapter
        mRecyclerView!!.addItemDecoration(DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                loadData()
            }
        }
    }
}


