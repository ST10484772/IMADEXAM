package com.example.imadexam

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


        // Parallel arrays
        private val songTitles = arrayListOf("Song A", "Song B", "Song C", "Song D")
        private val artistNames = arrayListOf("Artist A", "Artist B", "Artist C", "Artist D")
        private val ratings = arrayListOf(4, 1, 2, 3)
        private val comments = arrayListOf("Rap", "Dance song", "Best Love song", "Memories")

        // UI containers
        private lateinit var mainLayout: LinearLayout
        private lateinit var detailLayout: LinearLayout
        private lateinit var outputText: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // Root layout
            val root = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(30, 30, 30, 30)
            }

            // Main screen layout
            mainLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                visibility = View.VISIBLE
            }

            val btnAdd = Button(this).apply { text = "Add to Playlist" }
            val btnGoDetail = Button(this).apply { text = "Go to Playlist View" }
            val btnExit = Button(this).apply { text = "Exit App" }

            mainLayout.addView(btnAdd)
            mainLayout.addView(btnGoDetail)
            mainLayout.addView(btnExit)

            // Detail screen layout
            detailLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                visibility = View.GONE
            }

            val btnDisplay = Button(this).apply { text = "Display Songs" }
            val btnAverage = Button(this).apply { text = "Calculate Average Rating" }
            val btnBack = Button(this).apply { text = "Return to Home" }

            outputText = TextView(this)

            detailLayout.addView(btnDisplay)
            detailLayout.addView(btnAverage)
            detailLayout.addView(btnBack)
            detailLayout.addView(outputText)

            root.addView(mainLayout)
            root.addView(detailLayout)

            setContentView(root)

            // Button actions
            btnAdd.setOnClickListener { showAddDialog() }
            btnGoDetail.setOnClickListener {
                mainLayout.visibility = View.GONE
                detailLayout.visibility = View.VISIBLE
            }
            btnExit.setOnClickListener { finish() }
            btnBack.setOnClickListener {
                detailLayout.visibility = View.GONE
                mainLayout.visibility = View.VISIBLE
                outputText.text = ""
            }
            btnDisplay.setOnClickListener { displaySongs() }
            btnAverage.setOnClickListener { calculateAverage() }
        }

        private fun showAddDialog() {
            val dialogLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(16, 16, 16, 16)
            }

            val titleInput = EditText(this).apply { hint = "Song Title" }
            val artistInput = EditText(this).apply { hint = "Artist Name" }
            val ratingInput = EditText(this).apply { hint = "Rating (1-5)" }
            val commentInput = EditText(this).apply { hint = "Comment" }

            dialogLayout.addView(titleInput)
            dialogLayout.addView(artistInput)
            dialogLayout.addView(ratingInput)
            dialogLayout.addView(commentInput)

            AlertDialog.Builder(this)
                .setTitle("Add Song")
                .setView(dialogLayout)
                .setPositiveButton("Add") { _, _ ->
                    val title = titleInput.text.toString()
                    val artist = artistInput.text.toString()
                    val rating = ratingInput.text.toString().toIntOrNull()
                    val comment = commentInput.text.toString()

                    if (title.isNotEmpty() && artist.isNotEmpty() && rating in 1..5 && comment.isNotEmpty()) {
                        songTitles.add(title)
                        artistNames.add(artist)
                        ratings.add(rating!!)
                        comments.add(comment)
                        Toast.makeText(this, "Song added!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Invalid input.", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        private fun displaySongs() {
            if (songTitles.isEmpty()) {
                outputText.text = "No songs in the playlist."
                return
            }

            val builder = StringBuilder()
            builder.append("Song Title\tArtist Name\tRating(1-5)\tComments\n")
            for (i in songTitles.indices) {
                builder.append("${songTitles[i]}\t${artistNames[i]}\t${ratings[i]}\t${comments[i]}\n")
            }
            outputText.text = builder.toString()
        }

        private fun calculateAverage() {
            if (ratings.isEmpty()) {
                outputText.text = "No ratings available."
                return
            }

            val average = ratings.sum().toDouble() / ratings.size
            outputText.text = "Average Rating: %.2f".format(average)
        }
    }
