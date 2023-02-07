package com.codelab.forage.data

import androidx.room.RoomDatabase
import com.codelab.forage.model.Forageable

/**
 * Room database to persist data for the Forage app.
 * This database stores a [Forageable] entity
 */
// TODO: create the database with all necessary annotations, methods, variables, etc.
abstract class ForageDatabase : RoomDatabase()